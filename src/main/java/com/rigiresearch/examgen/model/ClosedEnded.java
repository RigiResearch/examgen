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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * A question with a limited set of possible answers.
 * @author Miguel Jimenez (miguel@uvic.ca)
 * @date 2017-08-13
 * @version $Id$
 * @since 0.0.1
 */
@Accessors(fluent = true)
@AllArgsConstructor
@Getter
public final class ClosedEnded implements Question {

    /**
     * A question's possible answer.
     * @author Miguel Jimenez (miguel@uvic.ca)
     * @date 2017-08-13
     * @version $Id$
     * @since 0.0.1
     */
    @Accessors(fluent = true)
    @AllArgsConstructor
    @Getter
    public static final class Option {

        /**
         * Whether this option represents an answer.
         */
        private final boolean answer;

        /**
         * This option's statement.
         */
        private final TextSegment statement;

    }

    /**
     * This question's statement.
     */
    private final TextSegment statement;

    /**
     * The number of points assigned to this question.
     */
    private final int points;

    /**
     * List of possible answers.
     */
    private final List<Option> options;

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
        return this.options.stream()
            .map(option -> option.statement())
            .collect(Collectors.toList());
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
        final List<Option> scrambledOptions = new ArrayList<>(this.options);
        Collections.shuffle(
            scrambledOptions,
            new Random(seed)
        );
        return new ClosedEnded(
            this.statement,
            this.points,
            scrambledOptions
        );
    }

}
