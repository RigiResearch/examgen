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
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;

/**
 * A text segment.
 * @author Miguel Jimenez (miguel@uvic.ca)
 * @date 2017-08-13
 * @version $Id$
 * @since 0.0.1
 */
public interface TextSegment extends Serializable {

    /**
     * Text formatting styles.
     * @author Miguel Jimenez (miguel@uvic.ca)
     * @date 2017-08-21
     * @version $Id$
     * @since 0.0.1
     */
    public enum Style {
        /**
         * Bold text.
         */
        BOLD,

        /**
         * The text is formatted as code according to the target language.
         */
        CODE,

        /**
         * Custom styling based on the target language (e.g., latex). The text
         * will be formatted as it is.
         */
        CUSTOM,

        /**
         * No additional style is applied.
         */
        INHERIT,

        /**
         * The text is formatted as inline code according to the target language.
         */
        INLINE_CODE,

        /**
         * Italic text.
         */
        ITALIC,

        /**
         * A new line is added before the text.
         */
        NEW_LINE,
    }

    /**
     * This segment's plain text.
     * @return this segment's text
     */
    public String text();

    /**
     * Describes this segment's formatting styles.
     * @return a list of styles
     */
    public List<Style> styles();

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
         * Serial version UID.
         */
        private static final long serialVersionUID = 7350376731414050519L;

        /**
         * This segment's value.
         */
        private final String text;

        /**
         * This segment's formatting styles.
         */
        private final List<Style> styles;

        /**
         * Instantiates a text segment assuming inherited style.
         * @param text the text value
         */
        public Simple(final String text) {
            this(text, Arrays.asList(Style.INHERIT));
        }

        /* (non-Javadoc)
         * @see com.rigiresearch.quizgen.TextSegment#text()
         */
        @Override
        public String text() {
            return this.text;
        }

        /*
         * (non-Javadoc)
         * @see com.rigiresearch.examgen.model.TextSegment#styles()
         */
        @Override
        public List<Style> styles() {
            return this.styles;
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
