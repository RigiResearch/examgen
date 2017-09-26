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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

/**
 * A written examination.
 * @author Miguel Jimenez (miguel@uvic.ca)
 * @date 2017-08-20
 * @version $Id$
 * @since 0.0.1
 */
@Accessors(fluent = true)
@Getter
@RequiredArgsConstructor
public final class Examination implements Serializable {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 8470495506838570825L;

    /**
     * Parameters regarding this exam.
     * Parameters related to the course can be removed if course is modeled as
     * a separate entity.
     * @author Miguel Jimenez (miguel@uvic.ca)
     * @date 2017-08-21
     * @version $Id$
     * @since 0.0.1
     */
    public enum Parameter {
        /**
         * The course to which this exam belongs.
         */
        COURSE,

        /**
         * The course identifier number or CRN.
         */
        COURSE_ID,

        /**
         * The course identifier.
         */
        COURSE_REFERENCE_NUMBER,

        /**
         * The expected date in which this exam takes place.
         */
        DATE,

        /**
         * The course instructors.
         */
        INSTRUCTORS,

        /**
         * This examination's instructions.
         */
        INSTRUCTIONS,

        /**
         * The specific course sections (a list), if any.
         */
        SECTIONS,

        /**
         * The corresponding term.
         */
        TERM,

        /**
         * The time allocated to this exam.
         */
        TIME_LIMIT,

        /**
         * This exam's title.
         */
        TITLE,
    }

    /**
     * Parameters composing the document header. Values are expected.
     */
    private final Map<Parameter, Object> parameters;

    /**
     * This exam's set of questions.
     */
    private final List<Question> questions;

    /**
     * Scramble this exam.
     * @param seed the seed for the random number generator
     * @return a scrambled version of this exam
     */
    public Examination scrambled(final long seed) {
        final List<Question> scrambledQuestions = this.questions.stream()
            .map(question -> question.scrambled(seed))
            .collect(Collectors.toList());
        Collections.shuffle(
            scrambledQuestions,
            new Random(seed)
        );
        return new Examination(
            this.parameters,
            scrambledQuestions
        );
    }

    public List<Examination> variants(final long seed,
        final int questionsLimit) throws Exception {
        final List<Examination> quizzes = new ArrayList<>();
        @SuppressWarnings("unchecked")
        final List<String> sections =
            (List<String>) this.parameters.get(Parameter.SECTIONS);
        long _seed = seed;
        for (int i = 0; i < sections.size(); i++) {
            parameters.put(Parameter.SECTIONS, sections.get(i));
            quizzes.add(
                new Examination(
                    new HashMap<>(parameters),
                    this.randomisedQuestions(_seed, questionsLimit)
                ).scrambled(_seed)
            );
            // Use consecutive to randomize results & still control the output
            _seed++;
        }
        return quizzes;
    }

    private List<Question> randomisedQuestions(final long seed,
        final int questionsLimit) throws Exception {
        if (this.questions.size() < questionsLimit)
            throw new IllegalArgumentException("questions limit > questions");
        final List<Question> copy = new ArrayList<>(this.questions);
        Collections.shuffle(
            copy,
            new Random(seed)
        );
        return copy.subList(0, questionsLimit);
    }
}
