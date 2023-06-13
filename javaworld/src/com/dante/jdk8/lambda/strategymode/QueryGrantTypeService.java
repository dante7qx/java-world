package com.dante.jdk8.lambda.strategymode;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import cn.hutool.core.lang.Console;

/**
 * 根据优惠券(资源)类型resourceType和编码resourceId查询派发方式grantType
 * 
 * @author dante
 *
 */
public class QueryGrantTypeService {

	private static Map<String, Function<String, String>> grantTypeMap = new HashMap<>();

	/**
	 * 初始化业务分派逻辑,代替了if-else部分 
	 * key: 优惠券类型 
	 * value: lambda表达式,最终会获得该优惠券的发放方式
	 */
	static {
		grantTypeMap.put("红包", GrantTypeSerive::redPaper);
		grantTypeMap.put("购物券", GrantTypeSerive::shopping);
		grantTypeMap.put("qq会员", GrantTypeSerive::QQVip);
	}

	public String getResult(String resourceType, String resourceId) {
		// Controller根据 优惠券类型resourceType、编码resourceId 去查询 发放方式grantType
		Function<String, String> result = getGrantTypeMap().get(resourceType);
		if (result != null) {
			// 传入resourceId 执行这段表达式获得String型的grantType
			return result.apply(resourceId);
		}
		return "查询不到该优惠券的发放方式";
	}

	public Map<String, Function<String, String>> getGrantTypeMap() {
		return grantTypeMap;
	}
	
	public static void main(String[] args) {
		QueryGrantTypeService queryGrantTypeService = new QueryGrantTypeService();
		String result = queryGrantTypeService.getResult("购物券", "X10012");
		Console.log(result);
	}
}
