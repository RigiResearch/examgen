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

import lombok.AllArgsConstructor;

/**
 * A text segment.
 * @author Miguel Jimenez (miguel@uvic.ca)
 * @date 2017-08-13
 * @version $Id$
 * @since 0.0.1
 */
public interface TextSegment {

    /**
     * This segment's plain text.
     * @return this segment's text
     */
    public String text();

    /**
     * Whether this segment represents a piece of code.
     * @return whether this segment is code or not
     */
    public boolean code();

    /**
     * Whether this segment should be appended in the same line or in a new one.
     * @return whether this text is appended inline or not
     */
    public boolean inline();

    /**
     * A simple text segment.
     * @author Miguel Jimenez (miguel@uvic.ca)
     * @date 2017-08-19
     * @version $Id$
     * @since 0.0.1
     */
    @AllArgsConstructor
    public final class Simple implements TextSegment {

        /**
         * This segment's value.
         */
        private final String text;

        /**
         * Whether this text should be formatted as code.
         */
        private final boolean code;

        /**
         * Whether this text should be included in the current line or a new one.
         */
        private final boolean inline;

        /**
         * Instantiates a text segment assuming that it does not represent code
         * and it should be formatted in a new line.
         * @param text
         */
        public Simple(final String text) {
            this(text, false, false);
        }

        /* (non-Javadoc)
         * @see com.rigiresearch.quizgen.TextSegment#text()
         */
        @Override
        public String text() {
            return this.text;
        }

        /* (non-Javadoc)
         * @see com.rigiresearch.quizgen.TextSegment#code()
         */
        @Override
        public boolean code() {
            return this.code;
        }

        /* (non-Javadoc)
         * @see com.rigiresearch.quizgen.TextSegment#inline()
         */
        @Override
        public boolean inline() {
            return this.inline;
        }

        /*
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return this.text;
        }

    }

}
