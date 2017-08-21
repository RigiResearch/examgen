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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * A compound block of text.
 * @author Miguel Jimenez (miguel@uvic.ca)
 * @date 2017-08-13
 * @version $Id$
 * @since 0.0.1
 */
@Accessors(fluent = true)
@AllArgsConstructor
@Getter
public final class CompoundText implements TextSegment {

    /**
     * The segments composing this text.
     */
    private final List<TextSegment> segments;

    /*
     * (non-Javadoc)
     * @see com.rigiresearch.examgen.model.TextSegment#text()
     */
    @Override
    public String text() {
        return this.segments.stream()
            .map(segment -> segment.text().trim())
            .collect(Collectors.joining());
    }

    /*
     * (non-Javadoc)
     * @see com.rigiresearch.examgen.model.TextSegment#styles()
     */
    @Override
    public List<Style> styles() {
        return Arrays.asList(Style.NEW_LINE);
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return this.segments.stream()
            .map(segment -> {
                StringBuilder builder = new StringBuilder();
                builder.append(segment.toString());
                builder.append(
                    segment.styles().contains(Style.NEW_LINE) ? "\n" : " "
                );
                return builder.toString();
            })
            .collect(Collectors.joining());
    }

}
