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
import com.rigiresearch.examgen.model.Section
import com.rigiresearch.examgen.model.TextSegment
import java.util.List

import static com.rigiresearch.examgen.model.Examination.Parameter.COURSE
import static com.rigiresearch.examgen.model.Examination.Parameter.COURSE_REFERENCE_NUMBER
import static com.rigiresearch.examgen.model.Examination.Parameter.DATE
import static com.rigiresearch.examgen.model.Examination.Parameter.INSTRUCTIONS
import static com.rigiresearch.examgen.model.Examination.Parameter.INSTRUCTORS
import static com.rigiresearch.examgen.model.Examination.Parameter.SECTIONS
import static com.rigiresearch.examgen.model.Examination.Parameter.TERM
import static com.rigiresearch.examgen.model.Examination.Parameter.TIME_LIMIT
import static com.rigiresearch.examgen.model.Examination.Parameter.TITLE
import com.rigiresearch.examgen.model.TrueFalse

/**
 * A Latex template implementation.
 * @author Miguel Jimenez (miguel@uvic.ca)
 * @date 2017-09-14
 * @version $Id$
 * @since 0.0.1
 */
class LatexMidterm implements Template {

    override render(Examination e, boolean printSolutions) '''
        «val section = e.parameters.get(SECTIONS) as Section»
        \documentclass[10pt,addpoints«IF printSolutions»,answers«ENDIF»]{exam}

        % packages configuration
        «packages»

        % choices configuration
        «choices»

        % True-False question format
        «trueFalse»

        % listings configuration
        «listings»

        % parameters
        \newcommand{\institution}{University of Victoria}
        \newcommand{\course}{«e.parameters.get(COURSE)»}
        \newcommand{\coursenumber}{«e.parameters.get(COURSE_REFERENCE_NUMBER)»}
        \newcommand{\sections}{«section.name»}
        \newcommand{\term}{«e.parameters.get(TERM)»}
        \newcommand{\instructors}{«e.parameters.get(INSTRUCTORS)»}
        \newcommand{\timelimit}{«e.parameters.get(TIME_LIMIT)»}
        \newcommand{\examtitle}{«e.parameters.get(TITLE)»}
        \newcommand{\examdate}{«e.parameters.get(DATE)»}
        \newcommand{\examversion}{«section.name»}

        % page configuration
        \pagestyle{head}
        \firstpageheader{}{}{}
        \runningheader{\scriptsize \coursenumber{}}{\scriptsize \examtitle\ - Page \examversion{}-\thepage\ of \numpages}{\scriptsize \examdate}
        \runningheadrule

        \begin{document}
        % title
        \begin{center}
            {\LARGE\bfseries
                \term\\
                \examtitle\\
                \institution\\
            }
            \vspace{0.2cm}
            {\large \examdate}
        \end{center}

        % student information
        \vspace{0.5cm}
        \noindent
        \renewcommand{\arraystretch}{3}
        \begin{tabularx}{\textwidth}{|l|X|}
            \hline
            \textbf{Last Name} & \\
            \hline
            \textbf{First Name} & \\
            \hline
            \textbf{Course Section} & \sections{} \\
            \hline
            \textbf{UVic Student Number} & \textbf{V00} \\
            \hline
        \end{tabularx}

        % course information
        \vspace{0.5cm}
        \noindent
        \renewcommand{\arraystretch}{1.2}
        \begin{tabularx}{\textwidth}{|l|X|}
            \hline
            \textbf{Course} & \coursenumber{} - \course \\
            \hline
            \textbf{Instructors} & \instructors \\
            \hline
            \textbf{Duration} & \timelimit \\
            \hline
        \end{tabularx}
        \renewcommand{\arraystretch}{1}

        «IF e.parameters.get(INSTRUCTIONS) !== null»
            \vspace{0.5cm}
            % exam instructions
            \vspace{0.5cm}
            \noindent
            {\large\bfseries Instructions}
            \begin{itemize}[noitemsep]
                «FOR i : e.parameters.get(INSTRUCTIONS) as List<TextSegment>»
                    \item «i.render»
                «ENDFOR»
            \end{itemize}
        «ENDIF»
        \clearpage
        \noindent
        {\large\bfseries Questions}
        \vspace{0.5cm}
        \begin{questions}
        \bracketedpoints
        \marksnotpoints
        «FOR q : e.questions SEPARATOR "\n"»
        «q.render(printSolutions)»
        «ENDFOR»
        \end{questions}
        \end{document}
    '''

    override render(Question question, boolean printSolutions) {
        switch (question) {
            OpenEnded: question.render(false, printSolutions)
            ClosedEnded: question.render(false, printSolutions)
            TrueFalse: question.render(false, printSolutions)
            CompoundQuestion: question.render(printSolutions)
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
            "\n" + result
        else
            result
    }

    /**
     * Applies the given style to a rendered text.
     */
    def styled(CharSequence text, TextSegment.Style style) {
        switch (style) {
            case BOLD: '''\textbf{«text.escaped»}'''
            case CODE: '''
            \vspace{0.3cm}
            \begin{lstlisting}
            «text»
            \end{lstlisting}
            '''
            case INLINE_CODE: '''\lstinline!«text.scapedInline»!'''
            case ITALIC: '''\textit{«text.escaped»}'''
            case CUSTOM: text
            case INHERIT: text.escaped
            case NEW_LINE: '''\n«text.escaped»'''
        }
    }

    /**
     * Escapes special Latex characters
     */
    def escaped(CharSequence text) {
        text.toString
            .replace("\\", "\\textbackslash")
            .replace("~", "\\textasciitilde")
            .replace("^", "\\textasciicircum")
            .replace("#", "\\#")
            .replace("&", "\\&")
            .replace("%", "\\%")
            .replace("{", "\\{")
            .replace("}", "\\}")
            .replace("$", "\\$")
            .replace("_", "\\_")
    }

    def scapedInline(CharSequence text) {
        text.toString
            .replace("\\", "\\\\")
            .replace("&", "\\&")
            .replace("%", "\\%")
            .replace("{", "\\{")
            .replace("}", "\\}")
    }

    /**
     * Renders an open-ended question.
     */
    def render(OpenEnded question, boolean child, boolean printSolutions) '''
        «IF !child»\question[«question.points»]«ENDIF»
        «question.statement.render»
        «IF printSolutions»
            \begin{solution}
                «question.answer.render»
            \end{solution}
        «ELSE»
            \makeemptybox{«question.expectedLength»}
        «ENDIF»
    '''

    /**
     * Renders a closed-ended question.
     */
    def render(ClosedEnded question, boolean child, boolean printSolutions) '''
        «IF !child»\question[«question.points»]«ENDIF»
        «question.statement.render»
        \begin{items}
            «FOR option : question.options»
                «IF option.answer»\item*«ELSE»\item«ENDIF» «option.statement.render»
            «ENDFOR»
        \end{items}
    '''

    /**
     * Renders a True-False question.
     */
    def render(TrueFalse question, boolean child, boolean printSolutions) '''
        «IF !child»\question[«question.points»]«ENDIF»
        \TFQuestion{«IF question.answer»T«ELSE»F«ENDIF»}{«question.statement.render»}
    '''

    /**
     * Renders a compound question.
     */
    def render(CompoundQuestion question, boolean printSolutions) '''
        \question[«question.points»]
        «question.statement.render»
        \noaddpoints % to omit double points count
        \pointsinmargin\pointformat{} % deactivate points for children
        \begin{parts}
            «FOR child : question.children SEPARATOR "\n"»
                \part[«child.points»]{}
                «
                    switch (child) {
                        OpenEnded: child.render(true, printSolutions)
                        ClosedEnded: child.render(true, printSolutions)
                        TrueFalse: child.render(true, printSolutions)
                    }
                »
            «ENDFOR»
        \end{parts}
        \nopointsinmargin\pointformat{[\thepoints]} % activate points again
        \addpoints
    '''

    /**
     * Renders the packages to configure the Latex document.
     */
    def packages() '''
    % general
    \usepackage[utf8]{inputenc}
    \usepackage[margin=1in]{geometry}
    % math
    \usepackage{amsmath, amssymb}
    % tables
    \usepackage{tabularx}
    \usepackage{multicol}
    % listings
    \usepackage{color}
    \usepackage[scaled=0.85]{sourcecodepro}
    \usepackage{listings}
    % horizontal list of options
    \usepackage{environ}
    \usepackage[normalem]{ulem}
    \usepackage{etoolbox}
    \usepackage[export]{adjustbox}
    \usepackage{enumitem}
    '''

    /**
     * Configures the Latex listings.
     */
    def listings() '''
    \definecolor{keywords}{RGB}{127,0,85}
    \definecolor{comments}{RGB}{63,127,95}
    \definecolor{strings}{RGB}{42,0,255}
    \definecolor{frame}{RGB}{150,150,150}
    \definecolor{numbers}{RGB}{100,100,100}
    \lstdefinestyle{code}{
        language=C,
        tabsize=4,
        captionpos=b,
        showspaces=false,
        showtabs=false,
        breaklines=true,
        showstringspaces=false,
        breakatwhitespace=true,
        escapeinside={(*@}{@*)},
        commentstyle=\color{comments},
        keywordstyle=\bfseries\color{keywords},
        stringstyle=\color{strings},
        basicstyle=\small\ttfamily,
        frame=lines,
        rulecolor=\color{frame},
        xleftmargin=2em,
        framexleftmargin=1.5em,
        numbers=left,
        numbersep=10pt,
        numberstyle=\scriptsize\ttfamily\color{numbers}
    }
    \lstset{style=code}
    '''

    def trueFalse() '''
    \newcommand*{\TrueFalse}[1]{%
    \ifprintanswers
        \ifthenelse{\equal{#1}{T}}{%
            \textbf{TRUE}\hspace*{14pt}False
        }{
            True\hspace*{14pt}\textbf{FALSE}
        }
    \else
        {True}\hspace*{20pt}False
    \fi
    } 
    %% The following code is based on an answer by Gonzalo Medina
    %% https://tex.stackexchange.com/a/13106/39194
    \newlength\TFlengthA
    \newlength\TFlengthB
    \settowidth\TFlengthA{\hspace*{1.16in}}
    \newcommand\TFQuestion[2]{%
        \setlength\TFlengthB{\linewidth}
        \addtolength\TFlengthB{-\TFlengthA}
        \parbox[t]{\TFlengthA}{\TrueFalse{#1}}\parbox[t]{\TFlengthB}{#2}}
    '''

    /**
     * Configuration to display choices horizontally.
     */
    def choices() '''
    \renewcommand{\questionshook}{%
        \setlength{\itemsep}{0.5\baselineskip}
        \setlength{\topsep}{0pt}
        \setlength\partopsep{0pt} 
        \setlength\parsep{5pt}
    }
    
    \makeatletter
    \newlength\choiceitemwidth
    \newif\ifshowsolution \showsolutiontrue
    \newcounter{choiceitem}%
    
    \def\thechoiceitem{\Alph{choiceitem}}%
    \setlength{\fboxsep}{0pt}
    \def\makechoicelabel#1{#1\uline{\bfseries \thechoiceitem.}\else\thechoiceitem.\fi\space}
    %\def\makechoicelabel#1{#1\uline{\thechoiceitem.}\else\thechoiceitem.\fi\space} %underline the answer item label if we want to print the answer
    %\def\makechoicelabel#1{#1\framebox[1.25em][l]{\thechoiceitem.}\else\makebox[1.25em][l]{\thechoiceitem.}\fi} %underline the answer item label if we want to print the answer
    
    \def\choice@mesureitem#1{\cr\stepcounter{choiceitem}\makechoicelabel#1}%
    
    %measure the choices, this is the first time we need to parse the \BODY
    \def\choicemesureitem{\@ifstar
        {\choice@mesureitem\ifprintanswers \xappto\theanswer{\thechoiceitem}\ignorespaces}%
        {\choice@mesureitem\iffalse}}%
    
    \def\choice@blockitem#1{%
        \ifnum\value{choiceitem}>0\hfill\fi
        \egroup\hskip0pt
        \hbox to \choiceitemwidth\bgroup\hss\refstepcounter{choiceitem}\makechoicelabel#1}
    
    \def\choiceblockitem{\@ifstar
        {\choice@blockitem\ifprintanswers\ignorespaces}%
        {\choice@blockitem\iffalse}}
    
    \def\choice@paraitem#1{%
        \par\noindent\refstepcounter{choiceitem}\makechoicelabel#1\hangindent=1.25em\hangafter=1\relax}% only the first line need indent
    
    \def\choiceparaitem{\@ifstar
        {\choice@paraitem\ifprintanswers\ignorespaces}%
        {\choice@paraitem\iffalse}}
    
    \newdimen\qanswd
    \newdimen\qanswdtmp
    \newbox\qimgbox
    \NewEnviron{items}[1][]{%
        \def\theanswer{}
        \begingroup
        \let\item\choicemesureitem
        \setcounter{choiceitem}{0}%
        \settowidth{\global\choiceitemwidth}{\vbox{\halign{##\hfil\cr\BODY\crcr}}}%
        \endgroup
        \setbox\qimgbox\hbox{#1}%
        \setlist[trivlist]{nosep}
        \trivlist\item\relax%
        \qanswd=\linewidth%
        \advance\qanswd-\wd\qimgbox%
        % handle large images (leaving less than 30% space)
        \qanswdtmp=0.3\linewidth%
        \ifnum\qanswd<\qanswdtmp%
        %\strut\hfill% uncomment to right-align large images
        \unhbox\qimgbox%
        %\hfill\strut% uncomment this too to center them
        \par%
        \qanswd=\linewidth%
        \setbox\qimgbox\hbox{}%
        \fi%
        % end of handling for large images
        \begin{minipage}[t]{\qanswd}
            \trivlist\item\relax%
            \parindent0pt%  
            \setcounter{choiceitem}{0}%
            \ifdim\choiceitemwidth<0.25\columnwidth
            \choiceitemwidth=0.25\columnwidth
            \let\item\choiceblockitem
            \bgroup\BODY\hfill\egroup
            \else\ifdim\choiceitemwidth<0.5\columnwidth
            \choiceitemwidth=0.5\columnwidth
            \let\item\choiceblockitem
            \bgroup\BODY\hfill\egroup
            \else % \choiceitemwidth > 0.5\columnwidth
            \let\item\choiceparaitem
            \BODY
            \fi\fi
            \endtrivlist
        \end{minipage}%
        \adjustbox{valign=t}{\unhbox\qimgbox}
        \endtrivlist
    }
    \makeatother
    '''

}