package com.rigiresearch.examgen.io;
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
import com.rigiresearch.examgen.model.ClosedEnded;
import com.rigiresearch.examgen.model.ClosedEnded.Option;
import com.rigiresearch.examgen.model.CompoundQuestion;
import com.rigiresearch.examgen.model.CompoundText;
import com.rigiresearch.examgen.model.Examination;
import com.rigiresearch.examgen.model.Examination.Parameter;
import com.rigiresearch.examgen.model.OpenEnded;
import com.rigiresearch.examgen.model.Question;
import com.rigiresearch.examgen.model.TextSegment;
import com.rigiresearch.examgen.model.TextSegment.Style;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.yaml.snakeyaml.Yaml;

/**
 * A simple (and optimistic) parser implementation to read examinations from a
 * YAML file.
 * @author Miguel Jimenez (miguel@uvic.ca)
 * @date 2017-09-15
 * @version $Id$
 * @since 0.0.1
 */
@SuppressWarnings("unchecked")
public class ExaminationParser {

    /**
     * Parses a list of examinations from a YAML file.
     * @param file The YAML file
     * @return The examinations parsed
     * @throws FileNotFoundException If the YAML file is not found
     */
    public List<Examination> examinations(File file) throws FileNotFoundException {
        List<Examination> exams = new ArrayList<>();
        new Yaml().loadAll(new FileInputStream(file))
            .forEach(data -> {
                Map<String, Object> values = (Map<String, Object>) data;
                exams.add(
                    new Examination(
                        parameters(values.get("parameters")),
                        questions(values.get("questions"))
                    )
                );
            });
        return exams;
    }

    /**
     * Parses the examination parameters.
     * @param obj The input object
     * @return a map
     */
    private Map<Parameter, Object> parameters(Object obj) {
        Map<Parameter, Object> params = new HashMap<>();
        ((List<Map<String, Object>>) obj).stream()
            .forEach(map -> {
                String key = map.keySet().stream().findFirst().get();
                Object value = map.get(key);
                params.put(Parameter.valueOf(key), value);
            });
        return params;
    }

    /**
     * Parses the examination questions.
     * @param obj The input object
     * @return a list of questions
     */
    private List<Question> questions(Object obj) {
        List<Question> questions = new ArrayList<>();
        ((List<Map<String, Object>>) obj).stream()
            .forEach(map -> {
                Question question = null;
                switch ((String) map.get("type")) {
                    case "open-ended":
                        question = ExaminationParser.this.openEnded(map);
                        break;
                    case "closed-ended":
                        question = closedEnded(map);
                        break;
                    case "compound":
                        question = compound(map);
                        break;
                    default:
                        throw new IllegalArgumentException(
                            String.format(
                                "Unknown question type %s",
                                map.get("type")
                            )
                        );
                }
                questions.add(question);
            });
        return questions;
    }

    /**
     * Parses an open-ended question.
     * @param data The question data
     * @return a question
     */
    private Question openEnded(Map<String, Object> data) {
        return new OpenEnded(
            this.textSegment(data.get("statement")),
            this.textSegment(data.get("answer")),
            (Integer) data.get("points"),
            data.get("length").toString()
        );
    }

    /**
     * Parses a closed-ended question.
     * @param data The question data
     * @return a question
     */
    private Question closedEnded(Map<String, Object> data) {
        List<Option> options = new ArrayList<>();
        ((List<Map<String, Object>>) data.get("options")).stream()
            .forEach(map -> {
                options.add(
                    new Option(
                        (boolean) map.get("correct"),
                        this.textSegment(map.get("statement"))
                    )
                );
            });
        return new ClosedEnded(
            this.textSegment(data.get("statement")),
            (Integer) data.get("points"),
            options
        );
    }

    /**
     * Parses a compound question.
     * @param data The question data
     * @return a question
     */
    private Question compound(Map<String, Object> data) {
        List<Question> questions = questions(data.get("children"));
        return new CompoundQuestion(
            this.textSegment(data.get("statement")),
            questions
        );
    }

    /**
     * Parses a text segment.
     * @param data The segment data
     * @return a segment
     */
    private TextSegment textSegment(Object obj) {
        List<TextSegment> segments = this._textSegments(obj.toString());
        if (segments.size() == 1) {
            return segments.get(0);
        } else {
            return new CompoundText(segments);
        }
    }

    /**
     * Parses a text into a sequential list of text segments.
     * TODO make it recursive
     * @param text The input text
     * @return a sequence of text segments
     */
    private List<TextSegment> _textSegments(String text) {
        List<TextSegment> segments = new ArrayList<>();
        Pattern pattern = Pattern.compile(
            "(?<before>[.\\s\\S]*)<(?<style>[a-zA-Z_]+)>(?<text>[.\\s\\S]*)<\\/\\k<style>>(?<after>[.\\s\\S]*)",
            Pattern.MULTILINE
        );
        Matcher matcher = null;
        while ((matcher = pattern.matcher(text)).find()) {
            text = matcher.group("text");
            final String before = matcher.group("before");
            final String after = matcher.group("after");
            segments.addAll(_textSegments(before));
            segments.add(
                new TextSegment.Simple(
                    text,
                    Arrays.asList(
                        Style.valueOf(matcher.group("style").toUpperCase())
                    )
                )
            );
            segments.addAll(_textSegments(after));
        }
        if (segments.isEmpty())
            segments.add(new TextSegment.Simple(text));
        return segments;
    }
}
