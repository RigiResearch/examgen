import com.rigiresearch.examgen.ClosedEnded;
import com.rigiresearch.examgen.CompoundQuestion;
import com.rigiresearch.examgen.CompoundText;
import com.rigiresearch.examgen.Examination;
import com.rigiresearch.examgen.OpenEnded;
import com.rigiresearch.examgen.TextSegment;
import java.util.Arrays;
import java.util.HashMap;

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

/**
 * Main program.
 * @author Miguel Jimenez (miguel@uvic.ca)
 * @date 2017-08-12
 * @version $Id$
 * @since 0.0.1
 */
public final class Application {

    @SuppressWarnings("serial")
    public static void main(final String[] args) {
        new Examination(
            new CompoundText(
                Arrays.asList(
                    new TextSegment.Simple("Quiz 1"),
                    new TextSegment.Simple("CSC 111"),
                    new TextSegment.Simple("Fall 2017")
                )
            ),
            new TextSegment.Simple("Quiz 1"),
            new HashMap<String, String>(){{
                put("V Number", "");
                put("Name", "");
                put("Lab Section", "B01");
                put("Grade", "");
            }},
            Arrays.asList(
                new CompoundQuestion(
                    new TextSegment.Simple("This is a group of questions"),
                    Arrays.asList(
                        new ClosedEnded(
                            new TextSegment.Simple("This is a question with options"),
                            Arrays.asList(
                                new ClosedEnded.Option(
                                    true,
                                    new TextSegment.Simple("This is an option")
                                ),
                                new ClosedEnded.Option(
                                    false,
                                    new TextSegment.Simple("This is another option")
                                )
                            )
                        ),
                        new OpenEnded(
                            new TextSegment.Simple("Is this an open question?")
                        )
                    )
                )
            )
        );
    }

}
