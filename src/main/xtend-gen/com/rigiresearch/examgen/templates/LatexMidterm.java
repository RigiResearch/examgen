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
package com.rigiresearch.examgen.templates;

import com.rigiresearch.examgen.model.ClosedEnded;
import com.rigiresearch.examgen.model.CompoundQuestion;
import com.rigiresearch.examgen.model.CompoundText;
import com.rigiresearch.examgen.model.Examination;
import com.rigiresearch.examgen.model.OpenEnded;
import com.rigiresearch.examgen.model.Question;
import com.rigiresearch.examgen.model.Section;
import com.rigiresearch.examgen.model.TextSegment;
import com.rigiresearch.examgen.templates.Template;
import java.util.List;
import javax.annotation.Generated;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

/**
 * A Latex template implementation.
 * @author Miguel Jimenez (miguel@uvic.ca)
 * @date 2017-09-14
 * @version $Id$
 * @since 0.0.1
 */
@SuppressWarnings("all")
@Generated("org.eclipse.xtend.core.compiler.XtendGenerator")
public class LatexMidterm implements Template {
  @Override
  public CharSequence render(final Examination e, final boolean printSolutions) {
    StringConcatenation _builder = new StringConcatenation();
    Object _get = e.parameters().get(Examination.Parameter.SECTIONS);
    final Section section = ((Section) _get);
    _builder.newLineIfNotEmpty();
    _builder.append("\\documentclass[10pt,addpoints");
    {
      if (printSolutions) {
        _builder.append(",answers");
      }
    }
    _builder.append("]{exam}");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("% packages configuration");
    _builder.newLine();
    CharSequence _packages = this.packages();
    _builder.append(_packages);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("% choices configuration");
    _builder.newLine();
    CharSequence _choices = this.choices();
    _builder.append(_choices);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("% listings configuration");
    _builder.newLine();
    CharSequence _listings = this.listings();
    _builder.append(_listings);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("% parameters");
    _builder.newLine();
    _builder.append("\\newcommand{\\institution}{University of Victoria}");
    _builder.newLine();
    _builder.append("\\newcommand{\\course}{");
    Object _get_1 = e.parameters().get(Examination.Parameter.COURSE);
    _builder.append(_get_1);
    _builder.append("}");
    _builder.newLineIfNotEmpty();
    _builder.append("\\newcommand{\\coursenumber}{");
    Object _get_2 = e.parameters().get(Examination.Parameter.COURSE_REFERENCE_NUMBER);
    _builder.append(_get_2);
    _builder.append("}");
    _builder.newLineIfNotEmpty();
    _builder.append("\\newcommand{\\sections}{");
    String _name = section.name();
    _builder.append(_name);
    _builder.append("}");
    _builder.newLineIfNotEmpty();
    _builder.append("\\newcommand{\\term}{");
    Object _get_3 = e.parameters().get(Examination.Parameter.TERM);
    _builder.append(_get_3);
    _builder.append("}");
    _builder.newLineIfNotEmpty();
    _builder.append("\\newcommand{\\instructors}{");
    Object _get_4 = e.parameters().get(Examination.Parameter.INSTRUCTORS);
    _builder.append(_get_4);
    _builder.append("}");
    _builder.newLineIfNotEmpty();
    _builder.append("\\newcommand{\\timelimit}{");
    Object _get_5 = e.parameters().get(Examination.Parameter.TIME_LIMIT);
    _builder.append(_get_5);
    _builder.append("}");
    _builder.newLineIfNotEmpty();
    _builder.append("\\newcommand{\\examtitle}{");
    Object _get_6 = e.parameters().get(Examination.Parameter.TITLE);
    _builder.append(_get_6);
    _builder.append("}");
    _builder.newLineIfNotEmpty();
    _builder.append("\\newcommand{\\examdate}{");
    Object _get_7 = e.parameters().get(Examination.Parameter.DATE);
    _builder.append(_get_7);
    _builder.append("}");
    _builder.newLineIfNotEmpty();
    _builder.append("\\newcommand{\\examversion}{");
    String _name_1 = section.name();
    _builder.append(_name_1);
    _builder.append("}");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("% page configuration");
    _builder.newLine();
    _builder.append("\\pagestyle{head}");
    _builder.newLine();
    _builder.append("\\firstpageheader{}{}{}");
    _builder.newLine();
    _builder.append("\\runningheader{\\scriptsize \\coursenumber{}}{\\scriptsize \\examtitle\\ - Page \\examversion{}-\\thepage\\ of \\numpages}{\\scriptsize \\examdate}");
    _builder.newLine();
    _builder.append("\\runningheadrule");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\\begin{document}");
    _builder.newLine();
    _builder.append("% title");
    _builder.newLine();
    _builder.append("\\begin{center}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("{\\LARGE\\bfseries");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\\term\\\\");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\\examtitle\\\\");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\\institution\\\\");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\\vspace{0.2cm}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("{\\large \\examdate}");
    _builder.newLine();
    _builder.append("\\end{center}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("% student information");
    _builder.newLine();
    _builder.append("\\vspace{0.5cm}");
    _builder.newLine();
    _builder.append("\\noindent");
    _builder.newLine();
    _builder.append("\\renewcommand{\\arraystretch}{3}");
    _builder.newLine();
    _builder.append("\\begin{tabularx}{\\textwidth}{|l|X|}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\\hline");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\\textbf{Last Name} & \\\\");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\\hline");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\\textbf{First Name} & \\\\");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\\hline");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\\textbf{Course Section} & \\hspace{0.5cm} A01 \\hspace{0.5cm} A02 \\hspace{0.5cm} A03 \\hspace{0.5cm} A04 \\hspace{0.5cm} (circle your section) \\\\");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\\hline");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\\textbf{UVic Student Number} & \\textbf{V00} \\\\");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\\hline");
    _builder.newLine();
    _builder.append("\\end{tabularx}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("% course information");
    _builder.newLine();
    _builder.append("\\vspace{0.5cm}");
    _builder.newLine();
    _builder.append("\\noindent");
    _builder.newLine();
    _builder.append("\\renewcommand{\\arraystretch}{1.2}");
    _builder.newLine();
    _builder.append("\\begin{tabularx}{\\textwidth}{|l|X|}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\\hline");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\\textbf{Course} & \\coursenumber{} - \\course \\\\");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\\hline");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\\textbf{Instructors} & \\instructors \\\\");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\\hline");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\\textbf{Duration} & \\timelimit \\\\");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\\hline");
    _builder.newLine();
    _builder.append("\\end{tabularx}");
    _builder.newLine();
    _builder.append("\\renewcommand{\\arraystretch}{1}");
    _builder.newLine();
    _builder.newLine();
    {
      Object _get_8 = e.parameters().get(Examination.Parameter.INSTRUCTIONS);
      boolean _tripleNotEquals = (_get_8 != null);
      if (_tripleNotEquals) {
        _builder.append("\\vspace{0.5cm}");
        _builder.newLine();
        _builder.append("% exam instructions");
        _builder.newLine();
        _builder.append("\\vspace{0.5cm}");
        _builder.newLine();
        _builder.append("\\noindent");
        _builder.newLine();
        _builder.append("{\\large\\bfseries Instructions}");
        _builder.newLine();
        _builder.append("\\begin{itemize}[noitemsep]");
        _builder.newLine();
        {
          Object _get_9 = e.parameters().get(Examination.Parameter.INSTRUCTIONS);
          for(final TextSegment i : ((List<TextSegment>) _get_9)) {
            _builder.append("    ");
            _builder.append("\\item ");
            CharSequence _render = this.render(i);
            _builder.append(_render, "    ");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("\\end{itemize}");
        _builder.newLine();
      }
    }
    _builder.append("% \\clearpage");
    _builder.newLine();
    _builder.append("\\vspace{0.5cm}");
    _builder.newLine();
    _builder.append("\\noindent");
    _builder.newLine();
    _builder.append("{\\large\\bfseries Questions}");
    _builder.newLine();
    _builder.append("\\vspace{0.5cm}");
    _builder.newLine();
    _builder.append("\\begin{questions}");
    _builder.newLine();
    _builder.append("\\bracketedpoints");
    _builder.newLine();
    _builder.append("\\marksnotpoints");
    _builder.newLine();
    {
      List<Question> _questions = e.questions();
      boolean _hasElements = false;
      for(final Question q : _questions) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate("\n", "");
        }
        CharSequence _render_1 = this.render(q, printSolutions);
        _builder.append(_render_1);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\\end{questions}");
    _builder.newLine();
    _builder.append("\\end{document}");
    _builder.newLine();
    return _builder;
  }
  
  @Override
  public CharSequence render(final Question question, final boolean printSolutions) {
    CharSequence _switchResult = null;
    boolean _matched = false;
    if (question instanceof OpenEnded) {
      _matched=true;
      _switchResult = this.render(((OpenEnded)question), false, printSolutions);
    }
    if (!_matched) {
      if (question instanceof ClosedEnded) {
        _matched=true;
        _switchResult = this.render(((ClosedEnded)question), false, printSolutions);
      }
    }
    if (!_matched) {
      if (question instanceof CompoundQuestion) {
        _matched=true;
        _switchResult = this.render(((CompoundQuestion)question), printSolutions);
      }
    }
    return _switchResult;
  }
  
  @Override
  public CharSequence render(final TextSegment segment) {
    CharSequence _switchResult = null;
    boolean _matched = false;
    if (segment instanceof TextSegment.Simple) {
      _matched=true;
      _switchResult = this.styled(segment);
    }
    if (!_matched) {
      if (segment instanceof CompoundText) {
        _matched=true;
        final Function1<TextSegment, CharSequence> _function = (TextSegment it) -> {
          return this.styled(it);
        };
        _switchResult = IterableExtensions.join(ListExtensions.<TextSegment, CharSequence>map(((CompoundText)segment).segments(), _function));
      }
    }
    return _switchResult;
  }
  
  /**
   * Applies styles to a rendered text segment.
   */
  public CharSequence styled(final TextSegment segment) {
    CharSequence result = segment.text();
    List<TextSegment.Style> _styles = segment.styles();
    for (final TextSegment.Style style : _styles) {
      result = this.styled(result, style);
    }
    CharSequence _xifexpression = null;
    boolean _contains = segment.styles().contains(TextSegment.Style.NEW_LINE);
    if (_contains) {
      _xifexpression = ("\n" + result);
    } else {
      _xifexpression = result;
    }
    return _xifexpression;
  }
  
  /**
   * Applies the given style to a rendered text.
   */
  public CharSequence styled(final CharSequence text, final TextSegment.Style style) {
    CharSequence _switchResult = null;
    if (style != null) {
      switch (style) {
        case BOLD:
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("\\textbf{");
          String _escaped = this.escaped(text);
          _builder.append(_escaped);
          _builder.append("}");
          _switchResult = _builder;
          break;
        case CODE:
          StringConcatenation _builder_1 = new StringConcatenation();
          _builder_1.append("\\vspace{0.3cm}");
          _builder_1.newLine();
          _builder_1.append("\\begin{lstlisting}");
          _builder_1.newLine();
          _builder_1.append(text);
          _builder_1.newLineIfNotEmpty();
          _builder_1.append("\\end{lstlisting}");
          _builder_1.newLine();
          _switchResult = _builder_1;
          break;
        case INLINE_CODE:
          StringConcatenation _builder_2 = new StringConcatenation();
          _builder_2.append("\\lstinline!");
          String _scapedInline = this.scapedInline(text);
          _builder_2.append(_scapedInline);
          _builder_2.append("!");
          _switchResult = _builder_2;
          break;
        case ITALIC:
          StringConcatenation _builder_3 = new StringConcatenation();
          _builder_3.append("\\textit{");
          String _escaped_1 = this.escaped(text);
          _builder_3.append(_escaped_1);
          _builder_3.append("}");
          _switchResult = _builder_3;
          break;
        case CUSTOM:
          _switchResult = text;
          break;
        case INHERIT:
          _switchResult = this.escaped(text);
          break;
        case NEW_LINE:
          StringConcatenation _builder_4 = new StringConcatenation();
          _builder_4.append("\\n");
          String _escaped_2 = this.escaped(text);
          _builder_4.append(_escaped_2);
          _switchResult = _builder_4;
          break;
        default:
          break;
      }
    }
    return _switchResult;
  }
  
  /**
   * Escapes special Latex characters
   */
  public String escaped(final CharSequence text) {
    return text.toString().replace("\\", "\\textbackslash").replace("~", "\\textasciitilde").replace("^", "\\textasciicircum").replace("#", "\\#").replace("&", "\\&").replace("%", "\\%").replace("{", "\\{").replace("}", "\\}").replace("$", "\\$").replace("_", "\\_");
  }
  
  public String scapedInline(final CharSequence text) {
    return text.toString().replace("\\", "\\\\").replace("&", "\\&").replace("%", "\\%").replace("{", "\\{").replace("}", "\\}");
  }
  
  /**
   * Renders an open-ended question.
   */
  public CharSequence render(final OpenEnded question, final boolean child, final boolean printSolutions) {
    StringConcatenation _builder = new StringConcatenation();
    {
      if ((!child)) {
        _builder.append("\\question[");
        int _points = question.points();
        _builder.append(_points);
        _builder.append("]");
      }
    }
    _builder.newLineIfNotEmpty();
    CharSequence _render = this.render(question.statement());
    _builder.append(_render);
    _builder.newLineIfNotEmpty();
    {
      if (printSolutions) {
        _builder.append("\\begin{solution}");
        _builder.newLine();
        _builder.append("    ");
        CharSequence _render_1 = this.render(question.answer());
        _builder.append(_render_1, "    ");
        _builder.newLineIfNotEmpty();
        _builder.append("\\end{solution}");
        _builder.newLine();
      } else {
        _builder.append("\\makeemptybox{");
        String _expectedLength = question.expectedLength();
        _builder.append(_expectedLength);
        _builder.append("}");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  /**
   * Renders a closed-ended question.
   */
  public CharSequence render(final ClosedEnded question, final boolean child, final boolean printSolutions) {
    StringConcatenation _builder = new StringConcatenation();
    {
      if ((!child)) {
        _builder.append("\\question[");
        int _points = question.points();
        _builder.append(_points);
        _builder.append("]");
      }
    }
    _builder.newLineIfNotEmpty();
    CharSequence _render = this.render(question.statement());
    _builder.append(_render);
    _builder.newLineIfNotEmpty();
    _builder.append("\\begin{items}");
    _builder.newLine();
    {
      List<ClosedEnded.Option> _options = question.options();
      for(final ClosedEnded.Option option : _options) {
        _builder.append("    ");
        {
          boolean _answer = option.answer();
          if (_answer) {
            _builder.append("\\item*");
          } else {
            _builder.append("\\item");
          }
        }
        _builder.append(" ");
        CharSequence _render_1 = this.render(option.statement());
        _builder.append(_render_1, "    ");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\\end{items}");
    _builder.newLine();
    return _builder;
  }
  
  /**
   * Renders a compound question.
   */
  public CharSequence render(final CompoundQuestion question, final boolean printSolutions) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("\\question[");
    int _points = question.points();
    _builder.append(_points);
    _builder.append("]");
    _builder.newLineIfNotEmpty();
    CharSequence _render = this.render(question.statement());
    _builder.append(_render);
    _builder.newLineIfNotEmpty();
    _builder.append("\\noaddpoints % to omit double points count");
    _builder.newLine();
    _builder.append("\\begin{parts}");
    _builder.newLine();
    {
      List<Question> _children = question.children();
      boolean _hasElements = false;
      for(final Question child : _children) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate("\n", "    ");
        }
        _builder.append("    ");
        _builder.append("\\part[");
        int _points_1 = child.points();
        _builder.append(_points_1, "    ");
        _builder.append("]{}");
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        CharSequence _switchResult = null;
        boolean _matched = false;
        if (child instanceof OpenEnded) {
          _matched=true;
          _switchResult = this.render(((OpenEnded)child), true, printSolutions);
        }
        if (!_matched) {
          if (child instanceof ClosedEnded) {
            _matched=true;
            _switchResult = this.render(((ClosedEnded)child), true, printSolutions);
          }
        }
        _builder.append(_switchResult, "    ");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\\end{parts}");
    _builder.newLine();
    _builder.append("\\addpoints");
    _builder.newLine();
    return _builder;
  }
  
  /**
   * Renders the packages to configure the Latex document.
   */
  public CharSequence packages() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("% general");
    _builder.newLine();
    _builder.append("\\usepackage[utf8]{inputenc}");
    _builder.newLine();
    _builder.append("\\usepackage[margin=1in]{geometry}");
    _builder.newLine();
    _builder.append("% math");
    _builder.newLine();
    _builder.append("\\usepackage{amsmath, amssymb}");
    _builder.newLine();
    _builder.append("% tables");
    _builder.newLine();
    _builder.append("\\usepackage{tabularx}");
    _builder.newLine();
    _builder.append("\\usepackage{multicol}");
    _builder.newLine();
    _builder.append("% listings");
    _builder.newLine();
    _builder.append("\\usepackage{color}");
    _builder.newLine();
    _builder.append("\\usepackage[scaled=0.85]{sourcecodepro}");
    _builder.newLine();
    _builder.append("\\usepackage{listings}");
    _builder.newLine();
    _builder.append("% horizontal list of options");
    _builder.newLine();
    _builder.append("\\usepackage{environ}");
    _builder.newLine();
    _builder.append("\\usepackage[normalem]{ulem}");
    _builder.newLine();
    _builder.append("\\usepackage{etoolbox}");
    _builder.newLine();
    _builder.append("\\usepackage[export]{adjustbox}");
    _builder.newLine();
    _builder.append("\\usepackage{enumitem}");
    _builder.newLine();
    return _builder;
  }
  
  /**
   * Configures the Latex listings.
   */
  public CharSequence listings() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("\\definecolor{keywords}{RGB}{127,0,85}");
    _builder.newLine();
    _builder.append("\\definecolor{comments}{RGB}{63,127,95}");
    _builder.newLine();
    _builder.append("\\definecolor{strings}{RGB}{42,0,255}");
    _builder.newLine();
    _builder.append("\\definecolor{frame}{RGB}{150,150,150}");
    _builder.newLine();
    _builder.append("\\definecolor{numbers}{RGB}{100,100,100}");
    _builder.newLine();
    _builder.append("\\lstdefinestyle{code}{");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("language=C,");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("tabsize=4,");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("captionpos=b,");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("showspaces=false,");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("showtabs=false,");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("breaklines=true,");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("showstringspaces=false,");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("breakatwhitespace=true,");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("escapeinside={(*@}{@*)},");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("commentstyle=\\color{comments},");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("keywordstyle=\\bfseries\\color{keywords},");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("stringstyle=\\color{strings},");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("basicstyle=\\small\\ttfamily,");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("frame=lines,");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("rulecolor=\\color{frame},");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("xleftmargin=2em,");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("framexleftmargin=1.5em,");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("numbers=left,");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("numbersep=10pt,");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("numberstyle=\\scriptsize\\ttfamily\\color{numbers}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.append("\\lstset{style=code}");
    _builder.newLine();
    return _builder;
  }
  
  /**
   * Configuration to display choices horizontally.
   */
  public CharSequence choices() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("\\renewcommand{\\questionshook}{%");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\\setlength{\\itemsep}{0.5\\baselineskip}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\\setlength{\\topsep}{0pt}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\\setlength\\partopsep{0pt} ");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\\setlength\\parsep{5pt}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\\makeatletter");
    _builder.newLine();
    _builder.append("\\newlength\\choiceitemwidth");
    _builder.newLine();
    _builder.append("\\newif\\ifshowsolution \\showsolutiontrue");
    _builder.newLine();
    _builder.append("\\newcounter{choiceitem}%");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\\def\\thechoiceitem{\\Alph{choiceitem}}%");
    _builder.newLine();
    _builder.append("\\setlength{\\fboxsep}{0pt}");
    _builder.newLine();
    _builder.append("\\def\\makechoicelabel#1{#1\\uline{\\bfseries \\thechoiceitem.}\\else\\thechoiceitem.\\fi\\space}");
    _builder.newLine();
    _builder.append("%\\def\\makechoicelabel#1{#1\\uline{\\thechoiceitem.}\\else\\thechoiceitem.\\fi\\space} %underline the answer item label if we want to print the answer");
    _builder.newLine();
    _builder.append("%\\def\\makechoicelabel#1{#1\\framebox[1.25em][l]{\\thechoiceitem.}\\else\\makebox[1.25em][l]{\\thechoiceitem.}\\fi} %underline the answer item label if we want to print the answer");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\\def\\choice@mesureitem#1{\\cr\\stepcounter{choiceitem}\\makechoicelabel#1}%");
    _builder.newLine();
    _builder.newLine();
    _builder.append("%measure the choices, this is the first time we need to parse the \\BODY");
    _builder.newLine();
    _builder.append("\\def\\choicemesureitem{\\@ifstar");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("{\\choice@mesureitem\\ifprintanswers \\xappto\\theanswer{\\thechoiceitem}\\ignorespaces}%");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("{\\choice@mesureitem\\iffalse}}%");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\\def\\choice@blockitem#1{%");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\\ifnum\\value{choiceitem}>0\\hfill\\fi");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\\egroup\\hskip0pt");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\\hbox to \\choiceitemwidth\\bgroup\\hss\\refstepcounter{choiceitem}\\makechoicelabel#1}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\\def\\choiceblockitem{\\@ifstar");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("{\\choice@blockitem\\ifprintanswers\\ignorespaces}%");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("{\\choice@blockitem\\iffalse}}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\\def\\choice@paraitem#1{%");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\\par\\noindent\\refstepcounter{choiceitem}\\makechoicelabel#1\\hangindent=1.25em\\hangafter=1\\relax}% only the first line need indent");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\\def\\choiceparaitem{\\@ifstar");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("{\\choice@paraitem\\ifprintanswers\\ignorespaces}%");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("{\\choice@paraitem\\iffalse}}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\\newdimen\\qanswd");
    _builder.newLine();
    _builder.append("\\newdimen\\qanswdtmp");
    _builder.newLine();
    _builder.append("\\newbox\\qimgbox");
    _builder.newLine();
    _builder.append("\\NewEnviron{items}[1][]{%");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\\def\\theanswer{}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\\begingroup");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\\let\\item\\choicemesureitem");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\\setcounter{choiceitem}{0}%");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\\settowidth{\\global\\choiceitemwidth}{\\vbox{\\halign{##\\hfil\\cr\\BODY\\crcr}}}%");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\\endgroup");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\\setbox\\qimgbox\\hbox{#1}%");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\\setlist[trivlist]{nosep}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\\trivlist\\item\\relax%");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\\qanswd=\\linewidth%");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\\advance\\qanswd-\\wd\\qimgbox%");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("% handle large images (leaving less than 30% space)");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\\qanswdtmp=0.3\\linewidth%");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\\ifnum\\qanswd<\\qanswdtmp%");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("%\\strut\\hfill% uncomment to right-align large images");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\\unhbox\\qimgbox%");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("%\\hfill\\strut% uncomment this too to center them");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\\par%");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\\qanswd=\\linewidth%");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\\setbox\\qimgbox\\hbox{}%");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\\fi%");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("% end of handling for large images");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\\begin{minipage}[t]{\\qanswd}");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\\trivlist\\item\\relax%");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\\parindent0pt%  ");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\\setcounter{choiceitem}{0}%");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\\ifdim\\choiceitemwidth<0.25\\columnwidth");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\\choiceitemwidth=0.25\\columnwidth");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\\let\\item\\choiceblockitem");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\\bgroup\\BODY\\hfill\\egroup");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\\else\\ifdim\\choiceitemwidth<0.5\\columnwidth");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\\choiceitemwidth=0.5\\columnwidth");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\\let\\item\\choiceblockitem");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\\bgroup\\BODY\\hfill\\egroup");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\\else % \\choiceitemwidth > 0.5\\columnwidth");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\\let\\item\\choiceparaitem");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\\BODY");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\\fi\\fi");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\\endtrivlist");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\\end{minipage}%");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\\adjustbox{valign=t}{\\unhbox\\qimgbox}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\\endtrivlist");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.append("\\makeatother");
    _builder.newLine();
    return _builder;
  }
}
