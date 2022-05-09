package com.dante.audio;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * Servlet implementation class AudioPlayerServlet
 */
@WebServlet("/AudioPlayerServlet")
public class AudioPlayerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final Logger log = Logger.getLogger(AudioPlayerServlet.class);
			
    public AudioPlayerServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletOutputStream out = null;
		try {
			response.setContentType("audio/mpeg");
			response.setCharacterEncoding("utf-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			out = response.getOutputStream();
			
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File("/Users/dante/Documents/Project/java-world/javaworld/javaworld/webapp/audio/water.mp3")));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int c = bis.read();// 读取bis流中的下一个字节

			while (c != -1) {
				baos.write(c);
				c = bis.read();
			}
			out.write(baos.toByteArray());
			bis.close();
			out.flush();
			out.close();
		} catch (Exception e) {
			log.error("读取音频文件失败", e);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
