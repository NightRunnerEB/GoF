package structural;

import java.util.HashMap;
import java.util.Map;

// Flyweight keeps intrinsic state shared across clients
class Glyph {

    private final char symbol;
    private final String font;

    public Glyph(char symbol, String font) {
        this.symbol = symbol;
        this.font = font;
    }

    public void draw(int x, int y) {
        System.out.printf("Draw '%c' in %s at (%d,%d)%n", symbol, font, x, y);
    }
}

// Flyweight factory returns existing instances when possible
class GlyphFactory {

    private final Map<String, Glyph> pool = new HashMap<>();

    public Glyph getGlyph(char symbol, String font) {
        String key = symbol + font;
        return pool.computeIfAbsent(key, k -> new Glyph(symbol, font));
    }

    public int poolSize() {
        return pool.size();
    }
}

@SuppressWarnings("unused")
final class FlyweightExample {

    private FlyweightExample() {
    }

    public static void example() {
        GlyphFactory factory = new GlyphFactory();

        // Draw the text "Hello"
        String text = "Hello";
        int x = 0;
        for (char c : text.toCharArray()) {
            Glyph glyph = factory.getGlyph(c, "Arial");
            glyph.draw(x, 10); // coordinates are the extrinsic state
            x += 10;
        }

        // Only four unique flyweights (H, e, l, o) should be created
        System.out.println("Unique flyweights: " + factory.poolSize());
    }
}
