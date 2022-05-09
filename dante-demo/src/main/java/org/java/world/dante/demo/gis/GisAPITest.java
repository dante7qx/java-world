package org.java.world.dante.demo.gis;

import java.util.HashMap;
import java.util.Map;

import com.ccb.publicservice.outbound.RestOutboundExecutor;
import com.ccb.publicservice.outbound.pojo.TransKey;
import com.ccb.publicservice.outbound.pojo.WorkKeyNegoCpa;
import com.dean.ccbft.es.entity.DCSSecException;

public class GisAPITest {
	private static final String APP_ID = "你的APP_ID";
	private static final String APP_SECRET = "你的APP_SECRET";
	private static final String ADDRESS = "平台地址";
	private static final String PROTOCOL = "请求协。测试环境：http 生产环境：https";
	private static final String BUSSINESS_ID= "123456"; // 请求惟一标识
	private static final String PDE = "pde123";	// 密钥因子保护口令，4—32 字节
	private static final String ECRP = "sm4";	// 加密算法，固定值 sm4
	private static final String SGN = "HmacSHA1";	// 签名算法，固定值 HmacSHA1
	private static final String USER_ID = "default";	// 用户ID，固定值 default
	private static final String PUB_KEY = "3NVYvzUUyxBqTuMtDYSbWPyDKxhXhfSrRTOUvR+NqUhIFfRL0bEgJ4dOpaJK47T6gs9XMob4MKxY81FwRV1wVQ==";	// 加密公钥，固定值
	private static final String C_TENANCY_ID = "psp-001";	// 服务提供方租户ID，固定值 psp-001
	private static final String ACS_STM_ID = "22";	// 接入系统编号，固定值 22
	private static final String ACSSTM_ECRPTKEY_CNTNT = "440470513";	// 接入系统密钥，固定值
	
	
	/**
	 * 获取协商密钥
	 * 
	 * @return
	 * @throws DCSSecException
	 */
	private static WorkKeyNegoCpa getTransKey() throws DCSSecException {
		String url = ADDRESS + "/psp/gettransKey";
		Map<String, String> options = new HashMap<>();
		options.put("protocol", PROTOCOL);
		
		TransKey transKey = new TransKey();
		transKey.setAppSecret(APP_SECRET);
		transKey.setEncAlg(ECRP);
		
		WorkKeyNegoCpa workKeyNegoCpa = RestOutboundExecutor.getTransKey(url, USER_ID, PDE, PUB_KEY, transKey, options);
		return workKeyNegoCpa;
	}
	
	
}
