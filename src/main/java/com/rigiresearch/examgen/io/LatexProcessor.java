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
package com.rigiresearch.examgen.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.SequenceInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Class to process latex files.
 * @author Miguel Jimenez (miguel@uvic.ca)
 * @date 2017-09-15
 * @version $Id$
 * @since 0.0.1
 */
@Accessors(fluent = true)
@AllArgsConstructor
@Getter
public final class LatexProcessor {

    /**
     * The folder to which the PDF files are saved.
     */
    private final File output;

    /**
     * Invokes pdflatex to process the generated tex files.
     */
    public void process() {
        this.output.mkdir();
        final Function<String, String> execute = (command) -> {
            final File parent = this.output.getParentFile();
            final StringBuilder output = new StringBuilder();
            System.out.printf("Working directory: %s\n", parent);
            System.out.printf("Executing command: %s\n", command);
            try {
                String line;
                final String[] cmd = {"/bin/sh", "-c", command};
                List<String> pairs = System.getenv().keySet()
                    .stream()
                    .map(k -> String.format("%s=%s", k, System.getenv(k)))
                    .collect(Collectors.toList());
                final String[] env = pairs.toArray(new String[]{});
                final Process p = Runtime.getRuntime().exec(cmd, env, parent);
                final BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                        new SequenceInputStream(
                            p.getInputStream(),
                            p.getErrorStream()
                        ),
                            StandardCharsets.UTF_8
                    )
                );
                while ((line = in.readLine()) != null) {
                    output.append(String.format("\n%s", line));
                }
                in.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return output.toString();
        };
        Stream.of(
            "find . -name \"*.tex\" -exec pdflatex -output-directory=%s {} \\;",
            "find . -name \"*.tex\" -exec pdflatex -output-directory=%s {} \\;",
            "find %s -type f ! -name '*.pdf' -delete"
        ).map(input -> String.format(input, this.output.getName()))
         .forEach(command -> System.out.println(execute.apply(command)));
    }

}
