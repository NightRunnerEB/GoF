interface Visitor {
    void visitText(Text text);
    void visitImage(Image image);
}

interface Element {
    void accept(Visitor visitor);
}

class Text implements Element {
    String content = "Hello World";

    @Override
    public void accept(Visitor visitor) {
        visitor.visitText(this);
    }
}

class Image implements Element {
    String path = "photo.png";

    @Override
    public void accept(Visitor visitor) {
        visitor.visitImage(this);
    }
}

class RenderVisitor implements Visitor {

    @Override
    public void visitText(Text text) {
        System.out.println("Render text: " + text.content);
    }

    @Override
    public void visitImage(Image image) {
        System.out.println("Render image: " + image.path);
    }
}

class ExportVisitor implements Visitor {

    @Override
    public void visitText(Text text) {
        System.out.println("<p>" + text.content + "</p>");
    }

    @Override
    public void visitImage(Image image) {
        System.out.println("<img src='" + image.path + "'/>");
    }
}

public final class VisitorExample {

    private VisitorExample() {
    }

    public static void demo() {
        Element[] doc = { new Text(), new Image() };

        Visitor renderer = new RenderVisitor();
        Visitor exporter = new ExportVisitor();

        for (Element element : doc) {
            element.accept(renderer);
        }
        for (Element element : doc) {
            element.accept(exporter);
        }
    }
}
