package examgen;

import com.rigiresearch.examgen.Application;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

class ApplicationTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    void whenStartedWithoutAllParametersThenErrorMessageShouldSayParametersProblem() {
        Application.main(new String[]{});
        assertThat(errContent.toString(), containsString("Error with input parameters"));
    }

    @Test
    void whenStartedWithExtraParametersThenErrorMessageShouldSayTooManyParameters() {
        Application.main(new String[]{
                "-i", "input.yaml",
                "-o", "output.tex",
                "-x",
        });
        assertThat(errContent.toString(), containsString("Unknown parameter(s) [-x]"));
    }
}