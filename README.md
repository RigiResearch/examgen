# Exam generator

This project generates examinations along with their solutions from a [YAML](http://www.yaml.org/) input file. The resulting files are rendered according to a specific template; so far, only Latex is supported.

#### Benefits
- Quizzes and midterms can be managed through version control, improving them every term/semester.
- From one single source file, the generator can target several output formats, including paper exams (e.g., Latex) and online exams (e.g., moodle).
- The generator allows randomizing and scrambling questions and choices. Each class section gets a different set of questions, in different order.


## Instructions

#### Build the application from the sources

First, clone or download this repository and then package the application artefacts using [Maven](https://maven.apache.org/):

```bash
git clone https://github.com/amelia/examgen; cd examgen
mvn package
```

Then, run the application:

```bash
# this command only shows the application menu
java -jar target/examgen.jar --help
```

#### Specify an examination using the YAML DSL

Every exam has a set of parameters and a set of questions. Currently, the only template supported expects the parameters: course, course reference number, term, time limit, exam title, and class sections. The following listing exemplifies how to specify these parameters:

```yaml
parameters:
  - COURSE: Fundaments of Programming with Engineering Applications
  - COURSE_REFERENCE_NUMBER: CSC 111
  - TERM: Fall 2017
  - TIME_LIMIT: 20 Minutes
  - TITLE: Quiz 1
  - SECTIONS:
    - name: B01
      TA: John Doe
      students: 30
    - name: B02
      TA: Jane Doe
      students: 30
```

The list of questions contains questions of type open-close, closed-ended, and compound questions (i.e., a question composed of other questions). The following listings show how to describe each type of questions supported:

```yaml
- type: closed-ended
  statement: Who created the C programming language?
  points: 10
  options:
    - {correct: true, statement: Dennis Ritchie}
    - {correct: false, statement: Lionel Ritchie}
    - {correct: false, statement: Steve Jobs}
    - {correct: false, statement: Michael Bublé}
```

In the case of an open-ended question, the length represents the length of the answer box.

```yaml
- type: open-ended
  statement: What is the difference between a multi-line and a single-line comment?
  answer: A single line comment allows to comment out only one line of text, whereas the multiline comment encloses multiple lines of text.
  length: 2cm
  points: 10
```

```yaml
- type: compound
  statement: Consider a function countArguments, which counts the number of arguments of a program.
  children:
    - type: closed-ended
      statement: The return value of the countArguments is
      points: 5
      options:
        - {correct: false, statement: double}
        - {correct: true, statement: int}
        - {correct: false, statement: char}
        - {correct: false, statement: float}
    - type: open-ended
      statement: Provide one use case for this function.
      answer: To validate the number of expected arguments.
      length: 1.5cm
      points: 10
```

Every question statement can be either a simple text (e.g., this is an statement, or 'this is an statement') or it can contain style tags. Style tags give text segments a specific formatting; the following are the supported style tags:

- **bold**: Bold text
- **code**: The text is formatted as code according to the target language
- **custom**: Custom styling based on the target language (e.g., latex). The text will be formatted as it is
- **inherit**: No additional style is applied (this is the default style)
- **inline_code**: The text is formatted as inline code according to the target language
- **italic**: Italic text
- **new_line**: A new line is added before the text

The following question exemplifies the use of text styles:

```yaml
- type: closed-ended
    statement: |
      <bold>Read the code below</bold>. How many function names there are?
      <code>#include <stdio.h>
      #include <stdlib.h>

      int main(void) {
        const double maxfahr = 100.0;
        const double minfahr = 0.0;
        double fahr = minfahr;
        double cels;
        while (fahr <= maxfahr) {
          cels = (fahr - 32.0) * 5.0 / 9.0;
          printf("%6.1f degs F = %6.1f degs C\n", fahr, cels);
          fahr = fahr + 10;
        } /*while*/
        return EXIT_SUCCESS;
      } /*main*/
      </code>
    points: 2
    options:
        - {correct: false, statement: <inline_code>null</inline_code>}
        - {correct: false, statement: 1}
        - {correct: true, statement: 2}
        - {correct: false, statement: 3}
```

Notice that only sequential tags are currently supported, recursive tags will end up in a bad text formatting.

In the last example, the question statement is formatted as a YAML multiple-line string using the pipe character. You can learn more about YAML types [here](https://learnxinyminutes.com/docs/yaml/).

Beware of character escaping when creating an examination. The following characters are considered special in YAML, and you will have to escape them in order to use them: `[ ] { } : > |`. You may quote text to avoid escaping characters.

A complete specification looks like the following listing:

```yaml
parameters:
  - COURSE: Fundaments of Programming with Engineering Applications
  - COURSE_REFERENCE_NUMBER: CSC 111
  - TERM: Fall 2017
  - TIME_LIMIT: 20 Minutes
  - TITLE: Quiz 1
  - SECTIONS:
    - name: B01
      TA: John Doe
      students: 30
    - name: B02
      TA: Jane Doe
      students: 30
questions:
  - type: open-ended
    statement: What is the difference between a multi-line and a single-line comment?
    answer: A single line comment allows to comment out only one line of text, whereas the multiline comment encloses multiple lines of text.
    length: 2cm
    points: 10
  - type: closed-ended
      statement: |
        <bold>Read the code below</bold>. How many function names there are?
        <code>#include <stdio.h>
        #include <stdlib.h>

        int main(void) {
          const double maxfahr = 100.0;
          const double minfahr = 0.0;
          double fahr = minfahr;
          double cels;
          while (fahr <= maxfahr) {
            cels = (fahr - 32.0) * 5.0 / 9.0;
            printf("%6.1f degs F = %6.1f degs C\n", fahr, cels);
            fahr = fahr + 10;
          } /*while*/
          return EXIT_SUCCESS;
        } /*main*/
        </code>
      points: 2
      options:
          - {correct: false, statement: <inline_code>null</inline_code>}
          - {correct: false, statement: 1}
          - {correct: true, statement: 2}
          - {correct: false, statement: 3}
```

#### Generate the examinations and solutions

The following is the help menu from the application:

```bash
The following options are required: [--output | -o], [--input | -i]
Usage: <main class> [options]
  Options:
  * --input, -i
      The YAML file
  * --output, -o
      The output directory
    --seed, -s
      The seed to scramble questions and options
      Default: 0
    --limit, -l
      The number of questions to include in an examination
      Default: 0
    --help, -h
      Shows this message
      Default: false
    --process, -p
      Invoke latex after generation (requires pdflatex in the environment. 
      Won't work on Windows!)
      Default: false
```

An example of usage would be:

```bash
java -jar target/examgen.jar -o ./quiz -i exam.yaml -l 10 -s 1234
```

The current implementation of the YAML parser is optimistic and will fail miserably if something is not right. A suggested troubleshooting procedure would be:
1. Be sure that all special characters are escaped or placed within quotes
2. Be sure that all expected attributes are specified
3. Panic


## Questions?

If you have any questions about this project, or something doesn't work as expected, please [submit an issue here](https://github.com/jachinte/examgen/issues).
