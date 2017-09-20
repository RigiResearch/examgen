package com.rigiresearch.examgen;
/**
 * Copyright 2017 University of Victoria
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 */
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.rigiresearch.examgen.io.ExaminationParser;
import com.rigiresearch.examgen.io.LatexProcessor;
import com.rigiresearch.examgen.templates.WritableExamination;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Main program.
 * @author Miguel Jimenez (miguel@uvic.ca)
 * @date 2017-08-12
 * @version $Id$
 * @since 0.0.1
 */
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public final class Application implements Runnable {

    @Parameter
    private List<String> parameters = new ArrayList<>();

    @Parameter(
        names = {"--input", "-i"},
        description = "The YAML file",
        required = true,
        order = 1
    )
    private String input;

    @Parameter(
        names = {"--output", "-o"},
        description = "The output directory",
        required = true,
        order = 2
    )
    private String output;

    @Parameter(
        names = {"--seed", "-s"},
        description = "The seed to scramble questions and options",
        order = 3
    )
    private long seed;

    @Parameter(
        names = {"--limit", "-l"},
        description = "The number of questions to include in an examination",
        order = 4
    )
    private int limit;

    @Parameter(
        names = {"--process", "-p"},
        description = "Invoke latex after generation (requires pdflatex in the environment. Won't work on Windows!)"
    )
    private boolean process = false;

    @Parameter(
        names = {"--help", "-h"},
        description = "Shows this message"
    )
    private boolean help = false;

    /**
     * Runs the exam generator
     * @param args The application input arguments
     */
    public static void main(final String[] args) {
        Application app = new Application();
        try {
            JCommander jc = JCommander.newBuilder()
                .addObject(app)
                .build();
            jc.setProgramName("java -jar examgen.jar");
            jc.parse(args);
            if (app.help) {
                jc.usage();
                return;
            }
        } catch (ParameterException e) {
            System.err.println(e.getMessage());
            new JCommander(new Application()).usage();
            System.exit(-1);
        }
        app.run();
    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        final AtomicInteger i = new AtomicInteger(0);
        try {
            new ExaminationParser()
                .examinations(new File(this.input))
                .stream()
                .forEach(examination -> {
                    File outputDir = new File(
                        String.format(
                            "%s%s",
                            this.output,
                            i.get() > 0 ? i.get() : ""
                        )
                    );
                    try {
                        examination.variants(this.seed, this.limit)
                            .stream()
                            .map(e -> new WritableExamination(e, WritableExamination.Target.LATEX_QUIZ))
                            .forEach(w -> {
                                try {
                                    w.write(outputDir);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    if (this.process) {
                        new LatexProcessor(
                            new File(String.join(File.separator, this.output, "examinations", "PDF"))
                        ).process();
                        new LatexProcessor(
                            new File(String.join(File.separator, this.output, "solutions", "PDF"))
                        ).process();
                    }
                    i.incrementAndGet();
                });
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }
}
