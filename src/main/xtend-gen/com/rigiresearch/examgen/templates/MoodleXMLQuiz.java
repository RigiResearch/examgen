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
import com.rigiresearch.examgen.model.TextSegment;
import com.rigiresearch.examgen.model.TrueFalse;
import com.rigiresearch.examgen.templates.Template;
import java.util.List;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

/**
 * A Moodle XML Quiz template implementation.
 * @author Prashanti Angara (pangara@uvic.ca)
 * @date 2018-08-21
 * @version $Id$
 * @since 0.0.1
 */
@SuppressWarnings("all")
public class MoodleXMLQuiz implements Template {
  @Override
  public CharSequence render(final Examination e, final boolean printSolutions) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    _builder.newLine();
    _builder.append("<quiz>");
    _builder.newLine();
    _builder.append("<question type=\"category\">");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("<category>");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("<text>$course$/");
    Object _get = e.parameters().get(Examination.Parameter.TITLE);
    _builder.append(_get, "    ");
    _builder.append("</text>       ");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    _builder.append("</category>");
    _builder.newLine();
    _builder.append("</question>");
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
        CharSequence _render = this.render(q, false);
        _builder.append(_render);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("</quiz>");
    _builder.newLine();
    _builder.newLine();
    return _builder;
  }
  
  @Override
  public CharSequence render(final Question question, final boolean printSolutions) {
    CharSequence _switchResult = null;
    boolean _matched = false;
    if (question instanceof OpenEnded) {
      _matched=true;
      _switchResult = this.render(((OpenEnded)question), false, false);
    }
    if (!_matched) {
      if (question instanceof ClosedEnded) {
        _matched=true;
        _switchResult = this.render(((ClosedEnded)question), false, false);
      }
    }
    if (!_matched) {
      if (question instanceof TrueFalse) {
        _matched=true;
        _switchResult = this.render(((TrueFalse)question), false, false);
      }
    }
    if (!_matched) {
      if (question instanceof CompoundQuestion) {
        _matched=true;
        _switchResult = this.render(question, false);
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
      _xifexpression = ("<br/>" + result);
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
          _builder.append("<strong>");
          String _escaped = this.escaped(text);
          _builder.append(_escaped);
          _builder.append("</strong>");
          _switchResult = _builder;
          break;
        case CODE:
          StringConcatenation _builder_1 = new StringConcatenation();
          _builder_1.append("<code>");
          _builder_1.newLine();
          String _escaped_1 = this.escaped(text);
          _builder_1.append(_escaped_1);
          _builder_1.newLineIfNotEmpty();
          _builder_1.append("</code>");
          _builder_1.newLine();
          _switchResult = _builder_1;
          break;
        case INLINE_CODE:
          StringConcatenation _builder_2 = new StringConcatenation();
          _builder_2.append("<code>");
          String _escaped_2 = this.escaped(text);
          _builder_2.append(_escaped_2);
          _builder_2.append("</code>");
          _switchResult = _builder_2;
          break;
        case ITALIC:
          StringConcatenation _builder_3 = new StringConcatenation();
          _builder_3.append("<i>");
          String _escaped_3 = this.escaped(text);
          _builder_3.append(_escaped_3);
          _builder_3.append("</i>");
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
          _builder_4.append("<br/>");
          String _escaped_4 = this.escaped(text);
          _builder_4.append(_escaped_4);
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
    return text.toString().replace("\'", "&apos;").replace("&", "&amp;").replace(">", "&gt;").replace("<", "&lt;");
  }
  
  /**
   * Renders an open-ended question.
   */
  public CharSequence render(final OpenEnded question, final boolean child, final boolean printSolutions) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<question type=\"shortanswer\">");
    _builder.newLine();
    _builder.append("<name>");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("<text>Short Answer</text>");
    _builder.newLine();
    _builder.append("</name>");
    _builder.newLine();
    _builder.append("<questiontext format=\"html\">");
    _builder.newLine();
    _builder.append("<text><![CDATA[<p><pre>");
    CharSequence _render = this.render(question.statement());
    _builder.append(_render);
    _builder.append("</pre><br></p>]]></text>");
    _builder.newLineIfNotEmpty();
    _builder.append("</questiontext>");
    _builder.newLine();
    CharSequence _feedback = this.feedback();
    _builder.append(_feedback);
    _builder.newLineIfNotEmpty();
    _builder.append("<defaultgrade>");
    int _points = question.points();
    _builder.append(_points);
    _builder.append("</defaultgrade>");
    _builder.newLineIfNotEmpty();
    _builder.append("<answer fraction=\"100\" format=\"html\">");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("<text><![CDATA[<p><pre>");
    CharSequence _render_1 = this.render(question.answer());
    _builder.append(_render_1, "  ");
    _builder.append("</pre><br></p>]]></text>");
    _builder.newLineIfNotEmpty();
    _builder.append("</answer>");
    _builder.newLine();
    _builder.append("</question>");
    _builder.newLine();
    return _builder;
  }
  
  public boolean isMultiChoice(final ClosedEnded question) {
    int multichoice = 0;
    List<ClosedEnded.Option> _options = question.options();
    for (final ClosedEnded.Option option : _options) {
      if ((option.answer() && (multichoice < 2))) {
        int _multichoice = multichoice;
        multichoice = (_multichoice + 1);
      } else {
        return true;
      }
    }
    return false;
  }
  
  /**
   * Renders a closed-ended question.
   */
  public CharSequence render(final ClosedEnded question, final boolean child, final boolean printSolutions) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<question type=\"multichoice\">");
    _builder.newLine();
    _builder.append("<name>");
    _builder.newLine();
    _builder.append("<text>Multiple Choice</text>");
    _builder.newLine();
    _builder.append("</name>");
    _builder.newLine();
    _builder.append("<questiontext format=\"html\">");
    _builder.newLine();
    _builder.append("<text><![CDATA[<p><pre>");
    CharSequence _render = this.render(question.statement());
    _builder.append(_render);
    _builder.append("</pre><br></p>]]></text>");
    _builder.newLineIfNotEmpty();
    _builder.append("</questiontext>");
    _builder.newLine();
    CharSequence _feedback = this.feedback();
    _builder.append(_feedback);
    _builder.newLineIfNotEmpty();
    _builder.append("<defaultgrade>");
    int _points = question.points();
    _builder.append(_points);
    _builder.append("</defaultgrade>");
    _builder.newLineIfNotEmpty();
    _builder.append("<answernumbering>abc</answernumbering>");
    _builder.newLine();
    _builder.append("<single>");
    {
      boolean _isMultiChoice = this.isMultiChoice(question);
      if (_isMultiChoice) {
        _builder.append("false");
      } else {
        _builder.append("true");
      }
    }
    _builder.append("</single>");
    _builder.newLineIfNotEmpty();
    {
      List<ClosedEnded.Option> _options = question.options();
      for(final ClosedEnded.Option option : _options) {
        _builder.append("<answer fraction=");
        {
          boolean _answer = option.answer();
          if (_answer) {
            _builder.append("\"100\"");
          } else {
            _builder.append("\"0\"");
          }
        }
        _builder.append(" format=\"html\">");
        _builder.newLineIfNotEmpty();
        _builder.append("  ");
        _builder.append("<text><![CDATA[<p><pre>");
        CharSequence _render_1 = this.render(option.statement());
        _builder.append(_render_1, "  ");
        _builder.append("</pre><br></p>]]></text>");
        _builder.newLineIfNotEmpty();
        _builder.append("  ");
        _builder.append("<feedback format=\"html\">");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("<text><![CDATA[<p><pre><br></pre><br></p>]]></text>");
        _builder.newLine();
        _builder.append("  ");
        _builder.append("</feedback>");
        _builder.newLine();
        _builder.append("</answer>");
        _builder.newLine();
      }
    }
    _builder.append("</question>");
    _builder.newLine();
    return _builder;
  }
  
  /**
   * Renders a True-False question.
   */
  public CharSequence render(final TrueFalse question, final boolean child, final boolean printSolutions) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<question type=\"truefalse\">");
    _builder.newLine();
    _builder.append("<name>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<text>True False</text>");
    _builder.newLine();
    _builder.append("</name>");
    _builder.newLine();
    _builder.append("<questiontext format=\"html\">");
    _builder.newLine();
    _builder.append("<text><![CDATA[<p>");
    CharSequence _render = this.render(question.statement());
    _builder.append(_render);
    _builder.append("<br></p>]]></text>");
    _builder.newLineIfNotEmpty();
    _builder.append("</questiontext>");
    _builder.newLine();
    _builder.append("<generalfeedback format=\"html\">");
    _builder.newLine();
    _builder.append("<text></text>");
    _builder.newLine();
    _builder.append("</generalfeedback>");
    _builder.newLine();
    _builder.append("<penalty>1</penalty>");
    _builder.newLine();
    _builder.append("<hidden>0</hidden>");
    _builder.newLine();
    _builder.append("<defaultgrade>");
    int _points = question.points();
    _builder.append(_points);
    _builder.append("</defaultgrade>");
    _builder.newLineIfNotEmpty();
    _builder.append("<answer fraction=");
    {
      boolean _answer = question.answer();
      boolean _equals = (_answer == true);
      if (_equals) {
        _builder.append("\"100\"");
      } else {
        _builder.append("\"0\"");
      }
    }
    _builder.append(" format=\"moodle_auto_format\">");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    _builder.append("<text>true</text>");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("<feedback format=\"html\">");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("<text><![CDATA[<p><pre><br></pre><br></p>]]></text>");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("</feedback>");
    _builder.newLine();
    _builder.append("</answer>");
    _builder.newLine();
    _builder.append("<answer fraction=");
    {
      boolean _answer_1 = question.answer();
      boolean _equals_1 = (_answer_1 == false);
      if (_equals_1) {
        _builder.append("\"100\"");
      } else {
        _builder.append("\"0\"");
      }
    }
    _builder.append(" format=\"moodle_auto_format\">");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    _builder.append("<text>false</text>");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("<feedback format=\"html\">");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("<text><![CDATA[<p><pre><br></pre><br></p>]]></text>");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("</feedback>");
    _builder.newLine();
    _builder.append("</answer>");
    _builder.newLine();
    _builder.append("</question>");
    _builder.newLine();
    return _builder;
  }
  
  /**
   * Default feedback for a question.
   */
  public CharSequence feedback() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<correctfeedback format=\"html\">");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("<text>Your answer is correct.</text>");
    _builder.newLine();
    _builder.append("</correctfeedback>");
    _builder.newLine();
    _builder.append("<partiallycorrectfeedback format=\"html\">");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("<text>Your answer is partially correct.</text>");
    _builder.newLine();
    _builder.append("</partiallycorrectfeedback>");
    _builder.newLine();
    _builder.append("<incorrectfeedback format=\"html\">");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("<text>Your answer is incorrect.</text>");
    _builder.newLine();
    _builder.append("</incorrectfeedback>");
    _builder.newLine();
    return _builder;
  }
}
