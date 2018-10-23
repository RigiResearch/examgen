package examgen;

import com.beust.jcommander.JCommander;
import com.rigiresearch.examgen.Application;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.hamcrest.text.IsEmptyString.isEmptyString;

class ApplicationTest {

    private Application application;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    public void setUpStreams() {
        application = new Application();
        JCommander jc = JCommander.newBuilder()
                .addObject(application)
                .build();
        jc.setProgramName("java -jar examgen.jar");
        application.setJc(jc);

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
        String[] args = new String[]{};
        application.parse(args);
        assertThat(errContent.toString(), containsString("Error with input parameters"));
    }

    @Test
    void whenStartedWithExtraParametersThenErrorMessageShouldSayTooManyParameters() {
        String[] args = new String[]{
                "-i", "input.yaml",
                "-o", "output.tex",
                "-x",
        };
        application.parse(args);
        assertThat(errContent.toString(), containsString("Unknown parameter(s) [-x]"));
    }

    @Test
    void whenStartedWithInputAndOutputThenOtherShouldBeCalled() {
        String[] args = new String[]{
                "-i", "input.yaml",
                "-o", "output.tex",
        };
        application.parse(args);
        assertThat(errContent.toString(), isEmptyString());
        assertThat(outContent.toString(), isEmptyString());
    }
}