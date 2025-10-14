package behavioral;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import org.junit.jupiter.api.Test;

final class VisitorTest {

    @Test
    void visitorsProduceDifferentRepresentations() {
        Text text = new Text();
        Image image = new Image();

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(buffer, true));
        try {
            Visitor render = new RenderVisitor();
            text.accept(render);
            image.accept(render);

            Visitor export = new ExportVisitor();
            text.accept(export);
            image.accept(export);
        } finally {
            System.setOut(original);
        }

        List<String> lines = buffer.toString().lines().toList();
        assertThat(lines).containsExactly(
            "Render text: Hello World",
            "Render image: photo.png",
            "<p>Hello World</p>",
            "<img src='photo.png'/>"
        );
    }
}
