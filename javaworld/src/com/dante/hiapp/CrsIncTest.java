package com.dante.hiapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import HNA.Security.MD5Utils;
import HNA.Security.RSAUtils;
import HNA.Security.SecurityUtils;

public class CrsIncTest {
	
	private static XmlMapper xmlMapper = new XmlMapper();
	static {
		xmlMapper.setPropertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE);
	}
	
	public static void main(String[] args) {
		String urlStr = "http://app.hk01.hnahotels.com:7054/CRSHiAppService.ashx";//服务端的URL地址
		SendPostMessage postMessage = new SendPostMessage();
		String responseXml = null;
									
		//请求参数模板
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><HnaRequest><RsaKey>$RsaKey$</RsaKey><RsaCode><![CDATA[$RsaCode$]]></RsaCode><Code><![CDATA[$Code$]]></Code><CodePwd><![CDATA[$CodePwd$]]></CodePwd><RouteCode>$RouteCode$</RouteCode><MessageFormatType>$MessageFormatType$</MessageFormatType><CompressDataType>$CompressDataType$</CompressDataType><Paramter>$Paramter$</Paramter></HnaRequest>";
//		String rasStrVal = "<LoadResortInfoQuery><ChainCode/><HotelCode>ALL</HotelCode></LoadResortInfoQuery>";//请求的业务XML参数
		String rasStrVal = "<LoadAvailabilityQuery><ChainCode>HNA</ChainCode><HotelCode>tlbj</HotelCode><CorporateID/><StartDate>2017-11-10</StartDate><EndDate>2017-11-30</EndDate></LoadAvailabilityQuery>";
//		String rasStrVal = "<LoadRoomNumQuery><ChainCode>HNA</ChainCode><HotelCode>tlbj</HotelCode><RoomTypeCode/><RateCode/><StartDate>2017-11-03</StartDate><EndDate>2017-11-05</EndDate></LoadRoomNumQuery>";
		String jtfChannelCode = "HiApp"; // 用户名
		String jtfChannelPwd = "1234,qwer"; // 密码
		try {
			//获取私钥文件
			InputStream resourceAsStream = CrsIncTest.class.getResourceAsStream("private_hiapp.xml");
			String rasPrivatekey = readFileByLines(resourceAsStream);
			//生成签名码
			String rsaStr = RSAUtils.sign(rasStrVal, rasPrivatekey);
			//用户名与密码(MD5)自定义加密
			String code = SecurityUtils.Encode(jtfChannelCode);
			String codepwd = SecurityUtils.Encode(MD5Utils.GetMD5Code(jtfChannelPwd).toLowerCase());
			//路由编号
			String route = "1002";
			//生成最终请求XML
			xml = xml.replace("$RsaKey$", rsaStr).replace("$RsaCode$", jtfChannelCode).replace("$Code$", code).replace("$CodePwd$", codepwd).replace("$RouteCode$", route).replace("$MessageFormatType$", "0").replace("$CompressDataType$", "0").replace("$Paramter$", rasStrVal);
			//对请求参数XML进行HTML标签编码
			xml = SecurityUtils.HtmlEncode(xml);
			//发送请求服务端
			responseXml = postMessage.send(urlStr, xml);
			//对返回内容进行 HTML标签的解码 
			responseXml = SecurityUtils.HtmlDecode(responseXml).replaceAll("\uFEFF", "");
//					.replaceAll("<ArrayOfResortInfoEntity>", "").replaceAll("</ArrayOfResortInfoEntity>", "");
			System.out.println(responseXml);
			// 解析返回的XML
//			JavaType javaType = getCollectionType(HnaResponse.class, ArrayOfResortInfoEntity.class);
//			HnaResponse<ArrayOfResortInfoEntity> hnaResponse = xmlMapper.readValue(responseXml, javaType);
//			System.out.println("*******************************************************");
//			System.out.println(hnaResponse);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
	public static String readFileByLines(InputStream resourceAsStream) throws IOException {
		StringBuffer buffer = new StringBuffer();
		BufferedReader in = new BufferedReader(new InputStreamReader(resourceAsStream, "UTF-8"));
		String line = in.readLine();
		while (line != null && line.trim().length() > 0) {
			buffer.append(line);
			line = in.readLine();
		}
		resourceAsStream.close();
		in.close();
		return buffer.toString();
    }
	
	public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
		return xmlMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
	}
	
}
