package org.java.world.dante.demo;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.StrUtil;

public class SignAddDate {

	public static void main(String[] args) {
		try {
            // 打开PDF文件
            PDDocument document = PDDocument.load(new File("input.pdf"));

            // 创建一个自定义的PDFTextStripper来提取文本位置信息
            CustomPDFTextStripper stripper = new CustomPDFTextStripper();

            // 遍历每一页
            for (PDPage page : document.getPages()) {
                stripper.resetHasSignatureText(); // 重置标志

                // 提取页面的文本信息
                stripper.setSortByPosition(true);
                stripper.setStartPage(document.getPages().indexOf(page) + 1);
                stripper.setEndPage(document.getPages().indexOf(page) + 1);
                stripper.getText(document);

                if (stripper.hasSignatureText()) {
                    PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true);
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.TIMES_ROMAN, 10); // 设置字体和字号
                    contentStream.newLineAtOffset(110, 68); // 设置文本的坐标
                    contentStream.showText(DateUtil.format(LocalDateTime.now(), DatePattern.NORM_DATETIME_MINUTE_PATTERN));
                    contentStream.endText();
                    contentStream.close();
                    
                    PDPageContentStream contentStream1 = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true);
                    contentStream1.beginText();
                    contentStream1.setFont(PDType1Font.TIMES_ROMAN, 10); // 设置字体和字号
                    contentStream1.newLineAtOffset(235, 68); // 设置文本的坐标
                    contentStream1.showText(DateUtil.format(LocalDateTime.now(), DatePattern.NORM_DATETIME_MINUTE_PATTERN));
                    contentStream1.endText();
                    contentStream1.close();
                }
            }

            // 保存修改后的PDF文件
            document.save("output.pdf");
            document.close();

            System.out.println("PDF文件修改完成。");
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	private static class CustomPDFTextStripper extends PDFTextStripper {
        public CustomPDFTextStripper() throws IOException {
			super();
			// TODO Auto-generated constructor stub
		}

		private boolean hasSignatureText;

        public void resetHasSignatureText() {
            hasSignatureText = false;
        }

        @Override
        protected void writeString(String text, List<TextPosition> textPositions) throws IOException {
            // 根据文本内容进行处理
            if (StrUtil.contains(text, "签名")) {
            	Console.log("===> {}", text);
                hasSignatureText = true;
            }
            super.writeString(text, textPositions);
        }

        public boolean hasSignatureText() {
            return hasSignatureText;
        }
    }
}
