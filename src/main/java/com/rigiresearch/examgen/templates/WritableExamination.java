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
package com.rigiresearch.examgen.templates;

import com.rigiresearch.examgen.model.Examination;
import com.rigiresearch.examgen.model.Examination.Parameter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * An {@link Examination} that can be written to a file.
 * @author Miguel Jimenez (miguel@uvic.ca)
 * @date 2017-09-14
 * @version $Id$
 * @since 0.0.1
 */
@Accessors(fluent = true)
@AllArgsConstructor
@Getter
public final class WritableExamination {

    /**
     * The supported target notations.
     * @author Miguel Jimenez (miguel@uvic.ca)
     * @date 2017-09-14
     * @version $Id$
     * @since 0.0.1
     */
    @Accessors(fluent = true)
    @AllArgsConstructor
    @Getter
    public enum Target {
        LATEX_QUIZ(LatexQuiz.class),
        LATEX_MIDTERM(LatexMidterm.class);

        /**
         * The templates implementation.
         */
        private final Class<? extends Template> clazz;
    }

    /**
     * The decorated examination.
     */
    private final Examination origin;

    /**
     * The target notation.
     */
    private final Target target;

    /**
     * Writes the examination to a file.
     * @param directory The parent directory
     * @throws IOException if an I/O error occurs writing to or creating the
     *  file
     */
    public void write(final File directory) throws IOException {
        File examination = new File(
            String.join(File.separator, directory.getAbsolutePath(), "examinations")
        );
        File solutions = new File(
            String.join(File.separator, directory.getAbsolutePath(), "solutions")
        );
        directory.mkdir();
        examination.mkdir();
        solutions.mkdir();
        final String name = String.format(
            "%s.tex",
            this.origin.parameters().get(Parameter.SECTIONS)
        );
        try {
            Files.write(
                Paths.get(new File(examination, name).getAbsolutePath()),
                this.target.clazz
                    .newInstance()
                    .render(this.origin, false)
                    .toString()
                    .getBytes()
            );
            Files.write(
                Paths.get(new File(solutions, name).getAbsolutePath()),
                this.target.clazz
                    .newInstance()
                    .render(this.origin, true)
                    .toString()
                    .getBytes()
            );
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
