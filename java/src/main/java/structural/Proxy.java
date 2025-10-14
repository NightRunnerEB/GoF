package structural;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

interface Report {

    String generate(String id) throws Exception;
}

// Real remote implementation (possibly slow or unreliable)
final class RemoteReport implements Report {

    private final HttpClient http = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(2))
            .build();
    private final String baseUrl;

    RemoteReport(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public String generate(String id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/reports/" + id))
                .timeout(Duration.ofSeconds(5))
                .GET()
                .build();
        HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("HTTP " + response.statusCode());
        }
        return response.body();
    }
}

// Policies live in the proxy while keeping the Report interface
final class ReportProxy implements Report {

    private final Report real;
    private final Map<String, String> cache = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final RateLimiter limiter = new RateLimiter(5, Duration.ofSeconds(1)); // 5 rps
    private final Authorizer auth;

    ReportProxy(Report real, Authorizer auth) {
        this.real = real;
        this.auth = auth;
        // Simple TTL cache: wipe entries every 60s (demo only)
        scheduler.scheduleAtFixedRate(cache::clear, 60, 60, TimeUnit.SECONDS);
    }

    @Override
    public String generate(String id) throws Exception {
        // Access control
        if (!auth.canView(id)) {
            throw new SecurityException("forbidden: " + id);
        }

        // Cache lookup
        String cached = cache.get(id);
        if (cached != null) {
            return cached;
        }

        // Rate limiting
        limiter.acquire();

        // Exponential backoff retries
        int attempts = 0;
        long backoff = 100;
        while (true) {
            try {
                String result = real.generate(id);
                cache.put(id, result);
                Metrics.count("report.ok");
                return result;
            } catch (Exception e) {
                attempts++;
                Metrics.count("report.err");
                if (attempts >= 3) {
                    throw e;
                }
                scheduler.schedule(() -> {
                }, backoff, TimeUnit.MILLISECONDS).get();
                backoff *= 2;
            }
        }
    }
}

// Simple authorizer/metrics stubs for the example
final class Authorizer {

    boolean canView(String id) {
        return !id.startsWith("secret-");
    }
}

final class Metrics {

    static void count(String key) {
        /* send to stats */
    }
}

final class RateLimiter {

    private final int max;
    private final Duration window;
    private int used = 0;
    private long windowStart = System.nanoTime();

    RateLimiter(int max, Duration window) {
        this.max = max;
        this.window = window;
    }

    synchronized void acquire() {
        long now = System.nanoTime();
        if (now - windowStart > window.toNanos()) {
            used = 0;
            windowStart = now;
        }
        if (used >= max) {
            throw new RuntimeException("rate limited");
        }
        used++;
    }
}

@SuppressWarnings("unused")
final class ProxyExample {

    private ProxyExample() {
    }

    public static void example() throws Exception {
        Report real = new RemoteReport("https://api.example.com");
        Report report = new ReportProxy(real, new Authorizer());

        System.out.println(report.generate("123"));       // loads and caches
        System.out.println(report.generate("123"));       // served from cache
        System.out.println(report.generate("secret-42")); // triggers forbidden
    }
}
