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
package com.rigiresearch.examgen.model;

import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * An open-ended question.
 * @author Miguel Jimenez (miguel@uvic.ca)
 * @date 2017-08-13
 * @version $Id$
 * @since 0.0.1
 */
@Accessors(fluent = true)
@AllArgsConstructor
@Getter
public final class OpenEnded implements Question {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 5439416765980874621L;

    /**
     * This question's statement.
     */
    private final TextSegment statement;

    /**
     * The answer to this question.
     */
    private final String answer;

    /**
     * The number of points assigned to this question.
     */
    private final int points;

    /**
     * Expected vertical length of the answer on a printed sheet.
     */
    private final String expectedLength;

    /* (non-Javadoc)
     * @see com.rigiresearch.quizgen.Question#statement()
     */
    @Override
    public TextSegment header() {
        return this.statement;
    }

    /* (non-Javadoc)
     * @see com.rigiresearch.quizgen.Question#body()
     */
    @Override
    public List<TextSegment> body() {
        return Collections.emptyList();
    }

    /* (non-Javadoc)
     * @see com.rigiresearch.quizgen.Question#children()
     */
    @Override
    public List<Question> children() {
        return Collections.emptyList();
    }

    /* (non-Javadoc)
     * @see com.rigiresearch.examgen.model.Question#scrambled(long)
     */
    @Override
    public Question scrambled(final long seed) {
        return new OpenEnded(
            this.statement,
            this.answer,
            this.points,
            this.expectedLength
        );
    }

}
