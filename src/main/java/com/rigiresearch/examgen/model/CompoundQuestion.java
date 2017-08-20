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
 * A question composed of several sub-questions.
 * @author Miguel Jimenez (miguel@uvic.ca)
 * @date 2017-08-19
 * @version $Id$
 * @since 0.0.1
 */
@Accessors(fluent = true)
@AllArgsConstructor
@Getter
public final class CompoundQuestion implements Question {

    /**
     * This question's segment.
     */
    private final TextSegment statement;

    /**
     * This question's sub-questions.
     */
    private final List<Question> children;

    /* (non-Javadoc)
     * @see com.rigiresearch.quizgen.Question#header()
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
        return this.children.stream()
            .map(child -> {
                final List<TextSegment> segments = new ArrayList<>();
                segments.add(child.header());
                segments.addAll(child.body());
                return new CompoundText(segments);
            })
            .collect(Collectors.toList());
    }

    /* (non-Javadoc)
     * @see com.rigiresearch.quizgen.Question#children()
     */
    @Override
    public List<Question> children() {
        return this.children;
    }

    /* (non-Javadoc)
     * @see com.rigiresearch.examgen.model.Question#scrambled(long)
     */
    @Override
    public Question scrambled(final long seed) {
        final List<Question> scrambledChildren = this.children.stream()
            .map(question -> question.scrambled(seed))
            .collect(Collectors.toList());
        Collections.shuffle(
            scrambledChildren,
            new Random(seed)
        );
        return new CompoundQuestion(
            this.statement,
            scrambledChildren
        );
    }

}
