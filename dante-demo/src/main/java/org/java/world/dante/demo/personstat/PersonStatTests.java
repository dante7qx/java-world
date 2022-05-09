package org.java.world.dante.demo.personstat;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.alibaba.fastjson.JSONObject;
import com.ccb.publicservice.outbound.RestOutboundExecutor;
import com.ccb.publicservice.outbound.pojo.ResponseMessage;
import com.ccb.publicservice.outbound.pojo.TransKey;
import com.ccb.publicservice.outbound.pojo.WorkKeyNegoCpa;
import com.dean.ccbft.es.entity.DCSSecException;

public class PersonStatTests {
	private static final String PIC_DIR = "/Users/dante/Documents/Project/java-world/javaworld/dante-demo/pic/";

	private static final String APP_ID = "2008129192_app_20200918094723";
	private static final String APP_SECRET = "5bebdd97d5c74da18ce26b40159192cf";
	private static final String ADDRESS = "https://service.ccb.com";
	private static final String PROTOCOL = "https";
	private static final String BUSSINESS_ID = "123456"; // 请求惟一标识
	private static final String PDE = "pde123"; // 密钥因子保护口令，4—32 字节
	private static final String ECRP = "ps2"; // 加密算法，固定值 sm4
	private static final String keyFactor = "1234567890098765";	// 加密因子
	private static final String USER_ID = "default"; // 用户ID，固定值 default
	private static final String PUB_KEY = "3NVYvzUUyxBqTuMtDYSbWPyDKxhXhfSrRTOUvR+NqUhIFfRL0bEgJ4dOpaJK47T6gs9XMob4MKxY81FwRVlwVQ==";
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
		transKey.setCipherKey(keyFactor);

		return RestOutboundExecutor.getTransKey(url, USER_ID, PDE, PUB_KEY, transKey, options);
	}
	
	/**
	 * 人数统计
	 * 
	 * @throws DCSSecException
	 */
	@Test
	public void analysis() throws DCSSecException {
		String uri = "/product/fileupload";
		WorkKeyNegoCpa workKeyNegoCpa = getTransKey();

		Map<String, String> requestHeader = new HashMap<>();
		requestHeader.put("C-App-Id", APP_ID);
		requestHeader.put("C-Tenancy-Id", C_TENANCY_ID);
		requestHeader.put("C-Business-Id", BUSSINESS_ID);
		requestHeader.put("C-Dynamic-Password", workKeyNegoCpa.getDynamicPassword());

		Map<String, String> requestParam = new HashMap<>();
		requestParam.put("fileType", "01");
		Map<String, String> secretParam = new HashMap<>();
		secretParam.put("ecrp", ECRP);

	    Map<String, String> fileMap = new HashMap<>();
	    fileMap.put("file", PIC_DIR.concat("stat.jpg"));
	    
		Map<String, String> options = new HashMap<>();
		options.put("protocol", PROTOCOL);
		
		ResponseMessage responseMessage = RestOutboundExecutor.callService(
					ADDRESS, 
					uri, 
					APP_ID, 
					APP_SECRET,
					BUSSINESS_ID, PDE,
					keyFactor, 
					secretParam, 
					requestParam, 
					requestHeader, 
					fileMap,
					options, 
					null);
		String body = responseMessage.getResponseBody();
        JSONObject json = JSONObject.parseObject(body);
        JSONObject jsonObject = (JSONObject)json.get("C-Response-Body");
        System.out.println("统计人数为："+ jsonObject.get("peoplecount"));
	}
}
