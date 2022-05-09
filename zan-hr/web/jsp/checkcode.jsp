<%@ page import="java.awt.image.BufferedImage"%>
<%@ page import="java.io.ByteArrayOutputStream"%>
<%@ page import="java.io.IOException"%>
<%@ page import="javax.imageio.ImageIO"%>
<%@ page import="javax.annotation.Resource"%>
<%@ page import="javax.servlet.ServletOutputStream"%>
<%@ page import="javax.servlet.http.HttpServletRequest"%>
<%@ page import="javax.servlet.http.HttpServletResponse"%>
<%@ page import="com.alibaba.citrus.turbine.util.Function"%>
<%@ page import="com.sun.image.codec.jpeg.JPEGCodec"%>
<%@ page import="com.sun.image.codec.jpeg.JPEGImageEncoder"%>

<%
    ByteArrayOutputStream bo = new ByteArrayOutputStream();
    String captchaId = request.getSession(true).getId();
    BufferedImage challenge = com.epolleo.webx.jcaptcha.JCaptchaService
        .getInstance().getImageChallengeForID(captchaId);
    if (false) {
        JPEGImageEncoder jpegEncoder = JPEGCodec.createJPEGEncoder(bo);
        jpegEncoder.encode(challenge);
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
    } else {
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/png");
        ImageIO.write(challenge, "png", bo);
    }
    ServletOutputStream ros = response.getOutputStream();
    ros.write(bo.toByteArray());
    out.clear();
    out = pageContext.pushBody();
    ros.flush();
    ros.close();
%>