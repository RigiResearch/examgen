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
package com.rigiresearch.examgen.templates

import com.rigiresearch.examgen.model.ClosedEnded
import com.rigiresearch.examgen.model.CompoundQuestion
import com.rigiresearch.examgen.model.CompoundText
import com.rigiresearch.examgen.model.Examination
import com.rigiresearch.examgen.model.OpenEnded
import com.rigiresearch.examgen.model.Question
import com.rigiresearch.examgen.model.TextSegment
import static com.rigiresearch.examgen.model.Examination.Parameter.TITLE
import com.rigiresearch.examgen.model.TrueFalse

/**
 * A Moodle XML Quiz template implementation.
 * @author Prashanti Angara (pangara@uvic.ca)
 * @date 2018-08-21
 * @version $Id$
 * @since 0.0.1
 */
class MoodleXMLQuiz implements Template {

    override render(Examination e, boolean printSolutions) '''
        <?xml version="1.0" encoding="UTF-8"?>
        <quiz>
        <question type="category">
          <category>
            <text>$course$/«e.parameters.get(TITLE)»</text>       
          </category>
        </question>
        «FOR q : e.questions SEPARATOR "\n"»
          «q.render(false)»
        «ENDFOR»
        </quiz>
        
    '''

    override render(Question question, boolean printSolutions) {
        switch (question) {
            OpenEnded: question.render(false, false)
            ClosedEnded: question.render(false, false)
            TrueFalse: question.render(false, false)
   //         CompoundQuestion: question.render(false)
        }
    }

    override render(TextSegment segment) {
        switch (segment) {
            TextSegment.Simple: segment.styled
            CompoundText: segment.segments.map[it.styled].join
        }
    }

    /**
     * Applies styles to a rendered text segment.
     */
    def styled(TextSegment segment) {
        var CharSequence result = segment.text
        for (style : segment.styles) {
            result = result.styled(style)
        }
        return if (segment.styles.contains(TextSegment.Style.NEW_LINE))
            "<br/>" + result
        else
            result
    }

    /**
     * Applies the given style to a rendered text.
     */
    def styled(CharSequence text, TextSegment.Style style) {
        switch (style) {
            case BOLD: '''<strong>«text.escaped»</strong>'''
            case CODE: '''
            <code>
            «text.escaped»
            </code>
            '''
            case INLINE_CODE: '''<code>«text.escaped»</code>'''
            case ITALIC: '''<i>«text.escaped»</i>'''
            case CUSTOM: text
            case INHERIT: text.escaped
            case NEW_LINE: '''<br/>«text.escaped»'''
        }
    }

    /**
     * Escapes special Latex characters
     */
    def escaped(CharSequence text) {
        text.toString
            .replace("\'", "&apos;")
            .replace("&", "&amp;")
            .replace (">", "&gt;")
            .replace("<","&lt;")
    }

    /**
     * Renders an open-ended question.
     */
    def render(OpenEnded question, boolean child, boolean printSolutions) '''
		<question type="shortanswer">
		<name>
		  <text>Short Answer</text>
		</name>
		<questiontext format="html">
		<text><![CDATA[«question.statement.render»]]></text>
		</questiontext>
		«feedback»
		<defaultgrade>«question.points»</defaultgrade>
		<answer fraction="100" format="html">
		  <text><![CDATA[«question.answer.render»]]></text>
		</answer>
		</question>
    '''

     
    def isMultiChoice(ClosedEnded question) {
    	var multichoice = 0
    	for (option : question.options){
    		if (option.answer){
    			multichoice+=1;
    		}
    		if (multichoice > 1) {
    			return true
    		}
    	}
    	return false
    }
    	
    /**
     * Renders a closed-ended question.
     */

    def render(ClosedEnded question, boolean child, boolean printSolutions) '''
		<question type="multichoice">
		<name>
		<text>Multiple Choice</text>
		</name>
		<questiontext format="html">
		<text><![CDATA[«question.statement.render»]]></text>
		</questiontext>
		«feedback»
		<defaultgrade>«question.points»</defaultgrade>
		<answernumbering>abc</answernumbering>
		<single>«IF question.isMultiChoice»false«ELSE»true«ENDIF»</single>
		«FOR option : question.options»
		<answer fraction=«IF option.answer»"100"«ELSE»"0"«ENDIF» format="html">
		  <text><![CDATA[«option.statement.render»]]></text>
		</answer>
		«ENDFOR»
		</question>
    '''

    /**
     * Renders a True-False question.
     */
    def render(TrueFalse question, boolean child, boolean printSolutions) '''
		<question type="truefalse">
		<name>
			<text>True False</text>
		</name>
		<questiontext format="html">
		<text><![CDATA[«question.statement.render»]]></text>
		</questiontext>
		<penalty>1</penalty>
		<hidden>0</hidden>
		<defaultgrade>«question.points»</defaultgrade>
		<answer fraction=«IF question.answer==true»"100"«ELSE»"0"«ENDIF» format="moodle_auto_format">
		  <text>true</text>
		</answer>
		<answer fraction=«IF question.answer==false»"100"«ELSE»"0"«ENDIF» format="moodle_auto_format">
		  <text>false</text>
		</answer>
		</question>
    '''
    
//    /**
//     * Renders a compound question.
//     */
//    def render(CompoundQuestion question, boolean printSolutions) '''
//  
//        «FOR child : question.children»
//            «question.statement.render»
//            «
//                switch (child) {
//                    OpenEnded: child.render(true, printSolutions)
//                    ClosedEnded: child.render(true, printSolutions)
//                    TrueFalse: child.render(true, printSolutions)
//                }
//            »
//        «ENDFOR»
//
//    '''

	/**
	 * Default feedback for a question.
	 */
	def feedback() '''
	<correctfeedback format="html">
	  <text>Your answer is correct.</text>
	</correctfeedback>
	<partiallycorrectfeedback format="html">
	  <text>Your answer is partially correct.</text>
	</partiallycorrectfeedback>
	<incorrectfeedback format="html">
	  <text>Your answer is incorrect.</text>
	</incorrectfeedback>
	'''
