package com.dante.qrcode;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * 二维码QrCodeServlet
 */
@WebServlet("/QrCodeServlet")
public class QrCodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Logger log = Logger.getLogger(QrCodeServlet.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletOutputStream out = null;
		try {
//			String url = request.getParameter("url");
			String url = "weixin://wap/pay?appid%3Dwx3c642b1089615a45%26noncestr%3D5a99158e0c52f9e7d290906c9d08268d%26package%3DWAP%26prepayid%3Dwx2015092114305826020ce97e0543633352%26sign%3D3BA94BE580255EBFAE1B030E2C06F7AD%26timeStamp%3D1442817058";
//			              weixin://wap/pay?appid%3Dwxf5b5e87a6a0fde94%26noncestr%3D123%26package%3D123%26prepayid%3Dwx20141203201153d7bac0d2e10889028866%26sign%3D6AF4B69CCC30926F85770F900D098D64%26timestamp%3D1417511263
			
			BufferedImage image = QrCodeUtil.createImage(url, "/Users/dante/Documents/Project/java-world/javaworld/javaworld/src/com/dante/qrcode/yezi.png", true);
//			BufferedImage image = QrCodeUtil.createImage(url, null, false);
			response.setContentType("images/jpg");
			response.setCharacterEncoding("utf-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			out = response.getOutputStream();
			
			ByteArrayOutputStream bs = new ByteArrayOutputStream(); 
			ImageOutputStream imOut;
			imOut = ImageIO.createImageOutputStream(bs); 
			ImageIO.write(image, "jpg",imOut); 
			
			out.write(bs.toByteArray());
			 
			out.flush();
			out.close();
		} catch (Exception e) {
			log.error("生成二维码错误！", e);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
