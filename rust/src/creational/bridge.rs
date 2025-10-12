// Implementation side (Renderer)
trait Renderer {
    fn render(&self, shape_name: &str);
}

struct OpenGLRenderer;
struct DirectXRenderer;

impl Renderer for OpenGLRenderer {
    fn render(&self, shape_name: &str) {
        println!("Rendering {} using OpenGL", shape_name);
    }
}

impl Renderer for DirectXRenderer {
    fn render(&self, shape_name: &str) {
        println!("Rendering {} using DirectX", shape_name);
    }
}

// Abstraction side (Shape)
trait Shape {
    fn draw(&self);
}

// Concrete shape Circle
struct Circle {
    renderer: Box<dyn Renderer>,
}

impl Circle {
    fn new(renderer: Box<dyn Renderer>) -> Self {
        Circle { renderer }
    }
}

impl Shape for Circle {
    fn draw(&self) {
        self.renderer.render("Circle");
    }
}

// Concrete shape Square
struct Square {
    renderer: Box<dyn Renderer>,
}

impl Square {
    fn new(renderer: Box<dyn Renderer>) -> Self {
        Square { renderer }
    }
}

impl Shape for Square {
    fn draw(&self) {
        self.renderer.render("Square");
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn example() {
        let opengl = Box::new(OpenGLRenderer);
        let directx = Box::new(DirectXRenderer);

        let circle = Circle::new(opengl);
        let square = Square::new(directx);

        circle.draw(); // Rendering Circle using OpenGL
        square.draw(); // Rendering Square using DirectX
    }
}
