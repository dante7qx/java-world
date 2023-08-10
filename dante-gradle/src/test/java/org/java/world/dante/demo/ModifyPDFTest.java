package org.java.world.dante.demo;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.StrUtil;

public class ModifyPDFTest {

	final String currentDirectory = System.getProperty("user.dir") + "/files/pdf/";

	public static void main(String[] args) {
		ModifyPDFTest mp = new ModifyPDFTest();
		
//		mp.addTextToPDF();
//		mp.addTextToPDFWithSignature();
		mp.addUnderSign();
	}

	// 文本添加到PDF的每一页的右下角
	private void addTextToPDF() {
		try {
			// 打开现有的PDF文件
			PDDocument document = PDDocument.load(new File(currentDirectory + "input.pdf"));

			// 遍历每一页
			for (PDPage page : document.getPages()) {

				// 获取页面的大小
				PDRectangle pageSize = page.getMediaBox();

				// 创建一个新的内容流
				PDPageContentStream contentStream = new PDPageContentStream(document, page,
						PDPageContentStream.AppendMode.APPEND, true, true);

				// 设置文本字体和大小
				contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

				// 设置文本位置（右下角）
				float x = pageSize.getWidth() - 200; // 调整文本位置的x坐标
				float y = 50; // 调整文本位置的y坐标

				// 设置文本位置
				contentStream.beginText();
				contentStream.newLineAtOffset(x, y);
				// 添加文本
				contentStream.showText("Hello - " + DateUtil.now());
				// 结束文本操作
				contentStream.endText();

				// 关闭内容流
				contentStream.close();
			}

			// 保存修改后的PDF文件
			document.save(currentDirectory + "output.pdf");

			// 关闭PDF文档
			document.close();

			System.out.println("PDF modified successfully.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 文本添加到有签名图片的PDF页面的右下方
	private void addTextToPDFWithSignature() {
		try {
			// 加载PDF文档
			PDDocument document = PDDocument.load(new File(currentDirectory + "input.pdf"));

			// 遍历每一页
			for (PDPage page : document.getPages()) {
				// 检查页面是否有签名图片
				if (hasSignatureImage(page)) {
					// 获取页面的大小
					PDRectangle pageSize = page.getMediaBox();

					// 创建内容流
					PDPageContentStream contentStream = new PDPageContentStream(document, page,
							PDPageContentStream.AppendMode.APPEND, true, true);

					// 设置字体和字体大小
					contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

					// 设置文本位置（签名图片右下方）
					float x = pageSize.getWidth() - 100; // 调整文本位置的x坐标
					float y = getSignatureImageYPosition(page) - 20; // 调整文本位置的y坐标

					// 添加文本
					contentStream.beginText();
					contentStream.newLineAtOffset(x, y);
					contentStream.showText("Hello");
					contentStream.endText();

					// 关闭内容流
					contentStream.close();
				}
			}

			// 保存修改后的PDF文档
			document.save(new File("output.pdf"));

			// 关闭文档
			document.close();

			System.out.println("文本已成功添加到有签名图片的PDF页面的右下方。");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 检查页面是否有签名图片
	private static boolean hasSignatureImage(PDPage page) throws IOException {
		PDResources resources = page.getResources();
		for (COSName xObjectName : resources.getXObjectNames()) {
			PDXObject xObject = resources.getXObject(xObjectName);
			if (xObject instanceof PDImageXObject) {
				return true;
			}
		}
		return false;
	}

	// 获取签名图片的Y坐标（假设签名图片位于页面底部）
	private static float getSignatureImageYPosition(PDPage page) throws IOException {
		PDRectangle pageSize = page.getMediaBox();
		PDResources resources = page.getResources();
		for (COSName xObjectName : resources.getXObjectNames()) {
			PDXObject xObject = resources.getXObject(xObjectName);
			if (xObject instanceof PDImageXObject) {
				PDImageXObject image = (PDImageXObject) xObject;
				BufferedImage bufferedImage = image.getImage();
				float imageHeight = bufferedImage.getHeight();
				float yPosition = pageSize.getLowerLeftY() + imageHeight;
				return yPosition;
			}
		}
		return 0; // 如果未找到签名图片，则返回默认的Y坐标（可以根据需要进行调整）
	}
	
	private void addUnderSign() {
		try {
            // 打开PDF文件
            PDDocument document = PDDocument.load(new File(currentDirectory + "input.pdf"));

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
                	List<Rectangle2D.Float> signaturePositions = stripper.getSignaturePositions();
                	
                	for (Rectangle2D.Float position : signaturePositions) {
                        float x = position.x;
                        float y = position.y - 10; // 在签名文本的正下方，向上偏移一定距离
                        Console.log("x -> {}, y -> {}", x, y);
                    }
                	
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
            document.save(currentDirectory + "output.pdf");
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
		private List<Rectangle2D.Float> signaturePositions;

        public void resetHasSignatureText() {
            hasSignatureText = false;
            signaturePositions = new ArrayList<>();
        }

        @Override
        protected void writeString(String text, List<TextPosition> textPositions) throws IOException {
            // 根据文本内容进行处理
            if (StrUtil.contains(text, "签名")) {
            	Console.log("===> {}", text);
                hasSignatureText = true;
                for (TextPosition position : textPositions) {
                    signaturePositions.add(new Rectangle2D.Float(position.getX(), position.getY(), position.getWidth(), position.getHeight()));
                }
            }
            super.writeString(text, textPositions);
        }

        public boolean hasSignatureText() {
            return hasSignatureText;
        }
        
        public List<Rectangle2D.Float> getSignaturePositions() {
            return signaturePositions;
        }
    }

}
