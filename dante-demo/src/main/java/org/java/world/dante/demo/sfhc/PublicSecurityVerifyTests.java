package org.java.world.dante.demo.sfhc;

import java.io.File;
import java.io.FileInputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.alibaba.fastjson.JSONObject;
import com.ccb.publicservice.outbound.RestOutboundExecutor;
import com.ccb.publicservice.outbound.pojo.ResponseMessage;
import com.ccb.publicservice.outbound.pojo.TransKey;
import com.ccb.publicservice.outbound.pojo.WorkKeyNegoCpa;
import com.dean.ccbft.es.entity.DCSSecException;

public class PublicSecurityVerifyTests {

	private static final String APP_ID = "您的APP_ID";
	private static final String APP_SECRET = "您的APP_SECRET";
	private static final String ADDRESS = "平台地址";
	private static final String PROTOCOL = "请求协议。测试环境：http 生产环境：https";
	private static final String BUSSINESS_ID = "123456"; // 请求惟一标识
	private static final String PDE = "pde123"; // 密钥因子保护口令，4—32 字节
	private static final String ECRP = "sm4"; // 加密算法，固定值 sm4
	private static final String SGN = "HmacSHA1"; // 签名算法，固定值 HmacSHA1
	private static final String USER_ID = "default"; // 用户ID，固定值 default
	private static final String PUB_KEY = "3NVYvzUUyxBqTuMtDYSbWPyDKxhXhfSrRTOUvR+NqUhIFfRL0bEgJ4dOpaJK47T6gs9XMob4MKxY81FwRV1wVQ=="; // 加密公钥，固定值
	private static final String C_TENANCY_ID = "psp-001"; // 服务提供方租户ID，固定值 psp-001

	/**
	 * 获取协商密钥因子和动态口令
	 * 
	 * @return
	 * @throws DCSSecException
	 */
	private static WorkKeyNegoCpa getTransKey() throws DCSSecException {
		String url = ADDRESS + "/psp/getTransKey";
		Map<String, String> options = new HashMap<>();
		options.put("protocol", PROTOCOL);
		TransKey transKey = new TransKey();
		transKey.setAppSecret(APP_SECRET);
		transKey.setEncAlg(ECRP);

		return RestOutboundExecutor.getTransKey(url, USER_ID, PDE, PUB_KEY, transKey, options);
	}

	/**
	 * 证件两项人像核查
	 * 
	 * @throws Exception
	 */
	@Test
	public void testPersonStat() throws Exception {
		String uri = "/product/fileupload";
		WorkKeyNegoCpa workKeyNegoCpa = getTransKey();

		Map<String, String> requestHeader = new HashMap<>();
		requestHeader.put("C-App-Id", APP_ID);
		requestHeader.put("C-Tenancy-Id", C_TENANCY_ID);
		requestHeader.put("C-Business-Id", BUSSINESS_ID);
		requestHeader.put("C-Dynamic-Password", workKeyNegoCpa.getDynamicPassword());

		Map<String, String> requestParam = null;

		Map<String, String> secretParam = new HashMap<>();
		secretParam.put("ecrp", ECRP);
		secretParam.put("sgn", SGN);

		JSONObject requestBody = new JSONObject();
		requestBody.put("Stm_chnl_ID", "您的系统渠道编号");

		File file = new File("face.jpg");
		FileInputStream fis = new FileInputStream(file);
		byte[] faceImgBytes = new byte[(int) file.length()];
		fis.read(faceImgBytes);

		requestBody.put("base64_Pic_Txn_Inf", Base64.getEncoder().encodeToString(faceImgBytes));

		Map<String, String> options = new HashMap<>();
		options.put("protocol", PROTOCOL);
		
		ResponseMessage responseMessage = RestOutboundExecutor.callService(
					ADDRESS, 
					uri, 
					APP_ID, 
					USER_ID, 
					APP_SECRET, 
					BUSSINESS_ID, 
					PDE,
					workKeyNegoCpa.getCipherKey(), 
					secretParam, 
					requestParam, 
					requestHeader, 
					options, 
					requestBody);
		fis.close();
		System.out.println(responseMessage);
	}
	
}
