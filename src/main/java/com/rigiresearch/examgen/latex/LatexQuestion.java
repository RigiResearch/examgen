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
package com.rigiresearch.examgen.latex;

import com.rigiresearch.examgen.model.Question;
import com.rigiresearch.examgen.model.TextSegment;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;

/**
 * A question decorator to format latex code.
 * @author Miguel Jimenez (miguel@uvic.ca)
 * @date 2017-08-13
 * @version $Id$
 * @since 0.0.1
 */
@AllArgsConstructor
public final class LatexQuestion implements Question {

    /**
     * The decorated question.
     */
    private final Question origin;

    /* (non-Javadoc)
     * @see com.rigiresearch.quizgen.Question#statement()
     */
    @Override
    public TextSegment header() {
        return this.origin.header();
    }

    /* (non-Javadoc)
     * @see com.rigiresearch.quizgen.Question#body()
     */
    @Override
    public List<TextSegment> body() {
        return this.origin.body();
    }

    /* (non-Javadoc)
     * @see com.rigiresearch.quizgen.Question#children()
     */
    @Override
    public List<Question> children() {
        return this.origin.children();
    }

    /**
     * Format this question as LateX code.
     * @return a String representation of this question.
     */
    public String asLatex() {
        return String.format(
            "%s\n%s",
            new LatexTextSegment(this.origin.header())
                .asLatex(),
            this.origin.body()
                .stream()
                .map(text -> new LatexTextSegment(text))
                .map(text -> text.asLatex())
                .collect(Collectors.joining("\n"))
        );
    }

}
