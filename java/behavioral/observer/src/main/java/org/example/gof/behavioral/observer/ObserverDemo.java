package org.example.gof.behavioral.observer;

public final class ObserverDemo {
    private ObserverDemo() {
    }

    public static void main(String[] args) {
        StockTicker ticker = new StockTicker();
        ticker.subscribe(new HistoryLogger());
        ticker.subscribe(update -> System.out.printf("ALERT: %s -> %.2f%n", update.symbol(), update.price()));

        ticker.publish("MSFT", 421.3);
        ticker.publish("GOOGL", 176.5);
    }
}
