package com.st.demo;

import fmath.conversion.ConvertFromMathMLToWord;

/**
 * created by tlfu on 2018/6/16
 */
public class Mathml2Word {
    public static void main(String[] args) {
        String path=System.getProperty("user.dir")+"/mathml.docx";
        System.out.println(path);
//        String mathMl="<math>  \n" +
//                "      <mrow>  \n" +
//                "        <msup><mi>a</mi><mn>2</mn></msup>  \n" +
//                "        <mo>+</mo>  \n" +
//                "        <msup><mi>b</mi><mn>2</mn></msup>  \n" +
//                "        <mo>=</mo>  \n" +
//                "        <msup><mi>c</mi><mn>2</mn></msup>  \n" +
//                "      </mrow>  \n" +
//                "    </math>  ";
        String mathMl = "<math>  <mfrac>    <mrow>      <mi mathvariant=\"normal\">d</mi>    </mrow>    <mrow>      <mrow>        <mi mathvariant=\"normal\">d</mi>      </mrow>      <mi>x</mi>    </mrow>  </mfrac>  <msup>    <mi>x</mi>    <mi>n</mi>  </msup>  <mo>=</mo>  <mi>n</mi>  <msup>    <mi>x</mi>    <mrow>      <mi>n</mi>      <mo>&#x2212;</mo>      <mn>1</mn>    </mrow>  </msup>  <mrow data-mjx-texclass=\"INNER\">    <mo data-mjx-texclass=\"OPEN\" fence=\"true\" stretchy=\"true\" symmetric=\"true\"></mo>    <mtable columnspacing=\"1em\" rowspacing=\"4pt\">      <mtr>        <mtd>          <mi>a</mi>          <mo>&#x22A5;</mo>          <mi>&#x3B1;</mi>        </mtd>      </mtr>      <mtr>        <mtd>          <mi>b</mi>          <mo>&#x22A5;</mo>          <mi>&#x3B1;</mi>        </mtd>      </mtr>    </mtable>    <mo data-mjx-texclass=\"CLOSE\">}</mo>  </mrow>  <mo stretchy=\"false\">&#x21D2;</mo>  <mi>a</mi>  <mo>&#x2225;</mo>  <mi>b</mi></math>";
        ConvertFromMathMLToWord.writeWordDocFromMathML(path,mathMl);
    }
}
