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
import com.rigiresearch.examgen.latex.LatexProcessor;
import com.rigiresearch.examgen.model.ClosedEnded;
import com.rigiresearch.examgen.model.ClosedEnded.Option;
import com.rigiresearch.examgen.model.CompoundText;
import com.rigiresearch.examgen.model.Examination;
import com.rigiresearch.examgen.model.Examination.Parameter;
import com.rigiresearch.examgen.model.OpenEnded;
import com.rigiresearch.examgen.model.TextSegment;
import com.rigiresearch.examgen.templates.WritableExamination;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import javax.measure.DecimalMeasure;
import javax.measure.unit.Unit;

/**
 * Main program.
 * @author Miguel Jimenez (miguel@uvic.ca)
 * @date 2017-08-12
 * @version $Id$
 * @since 0.0.1
 */
public final class Application {

    @SuppressWarnings("serial")
    public static void main(final String[] args) throws Exception {
        final long seed = 1234;
        final Examination examination = new Examination(
            new HashMap<Parameter, Object>(){{
                put(Parameter.COURSE, "Fundaments of Programming with Engineering Applications");
                put(Parameter.COURSE_REFERENCE_NUMBER, "CSC 111");
                put(Parameter.TERM, "Fall 2017");
                put(Parameter.TIME_LIMIT, "20 Minutes");
                put(Parameter.TITLE, "Quiz 1");
                put(
                    Parameter.SECTIONS,
                    Arrays.asList(
                        "B01", "B02", "B03", "B04", "B05", "B06", "B07", "B08",
                        "B09", "B10", "B11", "B12", "B13", "B14", "B15"
                    )
                );
            }},
            Arrays.asList(
                new ClosedEnded(
                    text("Which of the options below is a syntactically correct comment in C?"),
                    2,
                    Arrays.asList(
                        new Option(false, text("\\ This is a comment")),
                        new Option(false, text("*/ This is a comment /*")),
                        new Option(true, text("/* This is a comment */")),
                        new Option(false, text("\\# This is a comment"))
                    )
                ),
                new OpenEnded(
                    text("What is the difference between a multi-line comment and a single-line comment?"),
                    "A single line comment allows to comment out only one line of text, whereas the multiline comment encloses multiple lines of text.",
                    2,
                    DecimalMeasure.valueOf(2.0, Unit.valueOf("cm"))
                ),
                new ClosedEnded(
                    compound(
                        text("Which of the options below is"),
                        bold("NOT"),
                        text("a valid variable identifier in C?")
                    ),
                    2,
                    Arrays.asList(
                        new Option(true, text("201709")),
                        new Option(false, text("\\_function")),
                        new Option(false, text("main")),
                        new Option(false, text("include"))
                    )
                ),
                new ClosedEnded(
                    compound(
                        text("Which of the options below is"),
                        bold("NOT"),
                        text("a valid variable identifier in C?")
                    ),
                    2,
                    Arrays.asList(
                        new Option(true, text("for")),
                        new Option(false, text("identifier")),
                        new Option(false, text("var0123")),
                        new Option(false, text("\\_0123"))
                    )
                ),
                new ClosedEnded(
                    compound(
                        text("Which of the options below is"),
                        bold("NOT"),
                        text("a valid C statement?")
                    ),
                    2,
                    Arrays.asList(
                        new Option(false, icode("printf(\"hello world!\" /* hello world! */);")),
                        new Option(false, icode("int main = 0;")),
                        new Option(true, icode("function = void main(void) {  }")),
                        new Option(false, icode("int main(void) {  }"))
                    )
                ),
                new ClosedEnded(
                    compound(
                        text("Read the code below. How many function names there are?"),
                        code(
                            String.join(
                                "\n",
                                "#include <stdio.h>",
                                "#include <stdlib.h>",
                                "",
                                "int main(void) {",
                                "   const double maxfahr = 100.0;",
                                "   const double minfahr = 0.0;",
                                "   double fahr = minfahr;",
                                "   double cels;",
                                "   while (fahr <= maxfahr) {",
                                "       cels = (fahr - 32.0) * 5.0 / 9.0;",
                                "       printf(\"%6.1f degs F = %6.1f degs C\\n\", fahr, cels);",
                                "       fahr = fahr + 10;",
                                "   } /*while*/",
                                "   return EXIT_SUCCESS;",
                                "} /*main*/"
                            )
                        )
                    ),
                    2,
                    Arrays.asList(
                        new Option(false, text("0")),
                        new Option(false, text("1")),
                        new Option(true, text("2")),
                        new Option(false, text("3"))
                    )
                ),
                new ClosedEnded(
                    text("What is the name of the function a standard C program must contain?"),
                    2,
                    Arrays.asList(
                        new Option(false, text("function")),
                        new Option(false, text("stdio")),
                        new Option(false, text("stdlib")),
                        new Option(true, text("main"))
                    )
                ),
                new ClosedEnded(
                    text("Which of the characters below is used to end statements in c?"),
                    2,
                    Arrays.asList(
                        new Option(true, text("Semicolon (;)")),
                        new Option(false, text("Colon (:)")),
                        new Option(false, text("Comma (,)")),
                        new Option(false, text("None of the above"))
                    )
                ),
                new ClosedEnded(
                    text("Which of the options below illustrates the structure of a C function?"),
                    2,
                    Arrays.asList(
                        new Option(
                            true,
                            text(
                                String.join(
                                    "\n",
                                    "<return type> <function name> ( <parameter list> ) \\{",
                                    "   <declarations \\& statements>",
                                    "\\}"
                                )
                            )
                        ),
                        new Option(
                            false,
                            text(
                                String.join(
                                    "\n",
                                    "BEGIN <function name>:",
                                    "   <declarations \\& statements>",
                                    "END"
                                )
                            )
                        ),
                        new Option(
                            false,
                            text(
                                String.join(
                                    "\n",
                                    "<function name>:<return type> ( <parameter list> ) \\{",
                                    "   <declarations \\& statements>",
                                    "\\}"
                                )
                            )
                        ),
                        new Option(
                            false,
                            text(
                                String.join(
                                    "\n",
                                    "<function name> ( <parameter list> ) \\{",
                                    "   <declarations \\& statements>",
                                    "\\}"
                                )
                            )
                        )
                    )
                ),
                new ClosedEnded(
                    compound(
                        text("Identify the syntax error in the following C program:"),
                        code(
                            String.join(
                                "\n",
                                "#include <stdio.h>",
                                "#include <stdlib.h>",
                                "",
                                "void printText (void) {",
                                "   printf(\"Eureka\\n\")",
                                "} /* printText */",
                                "",
                                "int main(void) {",
                                "   printText();",
                                "   printText();",
                                "   printText();",
                                "   return EXIT_SUCCESS;",
                                "} /*main*/"
                            )
                        )
                    ),
                    2,
                    Arrays.asList(
                        new Option(false, text("Return statement is unnecessary")),
                        new Option(false, text("Quotes are missing")),
                        new Option(true, text("Missing semicolon")),
                        new Option(false, text("Comments are missing"))
                    )
                ),
                new ClosedEnded(
                    compound(
                        text("Identify the syntax error of the following C program:"),
                        code(
                            String.join(
                                "\n",
                                "#include <stdio.h>",
                                "#include <stdlib.h>",
                                "",
                                "int main(void) {",
                                "   printf(Welcome to CSC 111\\n\\n);",
                                "   printf(Enjoy the beautiful weather out there!\\n\\n);",
                                "   printf(There are no passengers on Spaceship Earth. Everybody's crew.\\n);",
                                "   return EXIT_SUCCESS;",
                                "} /*main*/"
                            )
                        )
                    ),
                    2,
                    Arrays.asList(
                        new Option(false, text("Return statement is unnecessary")),
                        new Option(true, text("Quotes are missing")),
                        new Option(false, text("Missing semicolon")),
                        new Option(false, text("Comments are missing"))
                    )
                ),
                new ClosedEnded(
                    text("Which of the options below is ignored by the C compiler?"),
                    2,
                    Arrays.asList(
                        new Option(false, icode("stdlib.h")),
                        new Option(false, text("Anything within a printf invocation")),
                        new Option(false, icode("stdio.h")),
                        new Option(true, text("Comments"))
                    )
                ),
                new ClosedEnded(
                    text("Which of the following special symbols is allowed in a variable name in the programming language C?"),
                    2,
                    Arrays.asList(
                        new Option(true, text("\\_ (underscore)")),
                        new Option(false, text("- (hypen)")),
                        new Option(false, text(". (period)")),
                        new Option(false, text("| (pipeline)"))
                    )
                ),
                new ClosedEnded(
                    text("What punctuation is used to signal the beginning and end of code blocks?"),
                    2,
                    Arrays.asList(
                        new Option(false, icode("[ ]")),
                        new Option(true, icode("{ }")),
                        new Option(false, icode("( )")),
                        new Option(false, icode("BEGIN END"))
                    )
                ),
                new ClosedEnded(
                    text("Upon successful completion, what expression should a C program return to the operating system?"),
                    2,
                    Arrays.asList(
                        new Option(false, text("true")),
                        new Option(false, text("false")),
                        new Option(true, text("EXIT\\_SUCCESS")),
                        new Option(false, text("EXIT\\_FAILURE"))
                    )
                ),
                new ClosedEnded(
                    text("Which type of code is produced by the Build process of the application development cycle?"),
                    2,
                    Arrays.asList(
                        new Option(false, text("Object code")),
                        new Option(true, text("Machine code")),
                        new Option(false, text("Source code")),
                        new Option(false, text("None of the above"))
                    )
                )
            )
        );
        File output = new File("/Users/miguel/Dropbox/PhD/TA/CS111-Fall2017/quizzes/Q1/");
        examination.variants(seed, 10)
            .stream()
            .map(e -> new WritableExamination(e, WritableExamination.Target.LATEX_QUIZ))
            .forEach(w -> {
                try {
                    w.write(output);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        new LatexProcessor(new File(output, "examinations")).process();
        new LatexProcessor(new File(output, "solutions")).process();
    }

    public static TextSegment text(final String contents) {
        return new TextSegment.Simple(contents);
    }

    public static TextSegment bold(final String contents) {
        return new TextSegment.Simple(contents, Arrays.asList(TextSegment.Style.BOLD));
    }

    public static TextSegment icode(final String contents) {
        return new TextSegment.Simple(contents, Arrays.asList(TextSegment.Style.INLINE_CODE));
    }

    public static TextSegment code(final String contents) {
        return new TextSegment.Simple(contents, Arrays.asList(TextSegment.Style.CODE));
    }

    public static TextSegment compound(final TextSegment... segments) {
        return new CompoundText(Arrays.asList(segments));
    }

}
