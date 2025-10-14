package behavioral;

interface Visitor {

    void visitText(Text t);

    void visitImage(Image i);
}

interface Element {

    void accept(Visitor v);
}

class Text implements Element {

    String content = "Hello World";

    @Override
    public void accept(Visitor v) {
        v.visitText(this);
    }
}

class Image implements Element {

    String path = "photo.png";

    @Override
    public void accept(Visitor v) {
        v.visitImage(this);
    }
}

// --- Concrete visitors ---
class RenderVisitor implements Visitor {

    @Override
    public void visitText(Text t) {
        System.out.println("Render text: " + t.content);
    }

    @Override
    public void visitImage(Image i) {
        System.out.println("Render image: " + i.path);
    }
}

class ExportVisitor implements Visitor {

    @Override
    public void visitText(Text t) {
        System.out.println("<p>" + t.content + "</p>");
    }

    @Override
    public void visitImage(Image i) {
        System.out.println("<img src='" + i.path + "'/>");
    }
}

@SuppressWarnings("unused")
final class VisitorExample {

    private VisitorExample() {
    }

    public static void example() {
        Text text = new Text();
        Image image = new Image();

        Visitor renderer = new RenderVisitor();
        text.accept(renderer);
        image.accept(renderer);

        Visitor exporter = new ExportVisitor();
        text.accept(exporter);
        image.accept(exporter);
    }
}
