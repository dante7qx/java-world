package com.dante.jdair;

import java.io.FileInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.axis.client.Service;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hnas.esb.entity.GenerateReqMsg;
import com.hnas.esb.entity.Parameter;
import com.hnas.event.ESBServiceSoapStub;

public class JdAirSms {
	
	public void send(String mobile, String content) {
		// 以下参数测试与正式不一样， 全部需配置化以便于维护修改 。以下获取参数的类由业务系 统自行设置
		String soapUrl = "http://esb.hnair.net:8888/webservice/Projects_HnaESBService_initial_ESBService";
		// 路由编号，由EAI分配提供，短信接口固定为EAI_SmsInformService
		String messageType = "EAI_SmsInformService";
		// 用户名，由EAI分配提供，如：ESBUser
		String userId = "JDMobileApp";
		// 用户密码，由EAI分配提供，如：123456
		String userPwd = "JDApp0724";
		// 用户私钥文件路径，由EAI分配提供，如：D:\\HNASEAI\\HnaESB\\RSAKey\\ESBUser_PrivateKey.dat
		String privateKeyPath = "/Users/dante/Documents/Project/javaworld/src/com/dante/jdair/JDMobileApp_PrivateKey.dat";

		// 定义接口参数List
		List<Parameter> parameter = new ArrayList<Parameter>();
		/*
		 * 定义接口参数，并加入参数列 表中 参数实例化的构造函数为：Parameter(参数名, 参数值) 具体参数个数根据各业务接口
		 * 的说明文档灵活设定 以下以人力资源管理系统的组 织机构查询接口为例
		 */
		// 参数1：短信服务AppName参数，由短信平台分配提供， 必填参数
		parameter.add(new Parameter("AppName", "CPASys"));
		// 参数2：短信服务SignString参数，由短信平台分配提供， 必填参数
		parameter.add(new Parameter("SignString", "cpa+128-864"));
		// 参数3：短信服务SubServiceCode参数，由短信平台分配提供， 必填参数
		parameter.add(new Parameter("SubServiceCode", "CPASms"));
		// 参数4：短信服务UserName参数，由短信平台分配提供， 必填参数
		parameter.add(new Parameter("UserName", "CPAUser"));
		// 参数5：手机号码，必填参数，多个 号码用半角,号隔开，群发时号码最多为50个
		parameter.add(new Parameter("Mobile", mobile));
		// 参数6：短信内容，必填参数
		parameter.add(new Parameter("Content", content));

		try {
			// 实例化ESB服务,ESBServiceSoapStub为接口代理类类名，包含在EAI SDK中
//					ESBServiceSoapStub saopObject = new ESBServiceSoapStub(new URL(soapUrl), new Service());
			
			ESBServiceSoapStub saopObject = new ESBServiceSoapStub(new URL(soapUrl), new Service());
			
			
			// 使用EAI SDK的API生成Webservice请求消息
			String message = GenerateReqMsg.generate(messageType, userId,userPwd, parameter, privateKeyPath);
			// 调用接口，获取返回值
			String response = saopObject.getDataFromInnerESB(message);
			// 获取到返回值后，根据EAI返回值结果格式及业务接口文 档的业务数据格式进行解析及后续业务操作
			if (response.contains("-1")) {
				System.out.println("fail");
			} else {
				System.out.println("success");
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}
	
	public void getSmsMobile() {
		int count = 0;
		try {
			HSSFWorkbook xwb = new HSSFWorkbook(
					new FileInputStream(
							"/Users/dante/Documents/Project/javaworld/src/com/dante/jdair/七夕.xls"));
			HSSFSheet sheet = xwb.getSheetAt(0);
			int firstRow = sheet.getFirstRowNum();
			int rowSize = sheet.getPhysicalNumberOfRows();
			HSSFRow row = null;
			HSSFCell mobileCell = null;
			String mobile = "";
			StringBuffer mobileBuf = new StringBuffer();
			
			for (int i = firstRow+1; i <= rowSize; i++) {
				row = sheet.getRow(i);
				if(row == null) {
					break;
				}
				mobileCell = row.getCell(1);
				mobile = mobileCell.getStringCellValue().substring(1);
				if(i % 50 == 0) {
					count++;
					mobileBuf.append(",").append(mobile);
//					System.out.println(mobileBuf.toString());
					// 发送短信
//					this.send(mobileBuf.toString(), "敢爱就要任性，首都航空飞电商城为您准备浪漫，”七夕“您准备好了吗？http://dwz.cn/1huajw");
					mobileBuf = new StringBuffer();
				} else {
					if(mobileBuf.length() > 0) {
						mobileBuf.append(",");
					}
					mobileBuf.append(mobile);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println(count);
	}
	
	public static void main(String[] args) {
		JdAirSms sms = new JdAirSms();
//		sms.send("18211011254,13810257339", "敢爱就要任性，首都航空飞电商城为您准备浪漫，”七夕“您准备好了吗？http://dwz.cn/1huajw");
		sms.getSmsMobile();
	}
}
