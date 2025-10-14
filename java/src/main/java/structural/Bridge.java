package structural;

// Implementation
interface Renderer {

    void render(String shapeName);
}

class OpenGLRenderer implements Renderer {

    @Override
    public void render(String shapeName) {
        System.out.println("Rendering " + shapeName + " with OpenGL");
    }
}

class DirectXRenderer implements Renderer {

    @Override
    public void render(String shapeName) {
        System.out.println("Rendering " + shapeName + " with DirectX");
    }
}

// Abstraction
abstract class Shape {

    protected Renderer renderer;

    public Shape(Renderer renderer) {
        this.renderer = renderer;
    }

    public abstract void draw();
}

class Circle extends Shape {

    public Circle(Renderer renderer) {
        super(renderer);
    }

    @Override
    public void draw() {
        renderer.render("Circle");
    }
}

class Square extends Shape {

    public Square(Renderer renderer) {
        super(renderer);
    }

    @Override
    public void draw() {
        renderer.render("Square");
    }
}

@SuppressWarnings("unused")
final class BridgeExample {

    private BridgeExample() {
    }

    public static void example() {
        Shape circle = new Circle(new OpenGLRenderer());
        Shape square = new Square(new DirectXRenderer());

        circle.draw(); // Rendering Circle with OpenGL
        square.draw(); // Rendering Square with DirectX
    }
}
