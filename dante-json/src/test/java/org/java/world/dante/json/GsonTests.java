package org.java.world.dante.json;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import org.java.world.dante.json.vo.BaseResp;
import org.java.world.dante.json.vo.Country;
import org.java.world.dante.json.vo.Province;
import org.java.world.dante.json.vo.Result;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GsonTests {
	
	private static Gson gson = new Gson();
	protected BaseResp<List<Country>> baseResp;

	@Before
	public void init() {
		baseResp = new BaseResp<>();
		baseResp.setSuccess(1);
		Result<List<Country>> t = new Result<>();
		List<Country> countrys = Arrays.asList(
				new Country("CN", "中国", Arrays.asList(new Province("北京"), new Province("天津"), new Province("成都"))),
				new Country("US", "美国", Arrays.asList(new Province("纽约"), new Province("洛杉矶"))));
		t.setT(countrys);
		baseResp.setResult(t);
	}
	
	@Test
	public void testBeanToJson() {
		try {
			long startTime = System.currentTimeMillis();
			System.out.println(gson.toJson(baseResp));
			long endTime = System.currentTimeMillis();
			System.out.println("程序运行时间：" + (endTime - startTime) + "ms"); // 输出程序运行时间
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testJsonToBean() {
		try {
			String json = "{\"success\":1,\"result\":{\"t\":[{\"code\":\"CN\",\"name\":\"中国\",\"provinces\":[{\"name\":\"北京\"},{\"name\":\"天津\"},{\"name\":\"成都\"}]},{\"code\":\"US\",\"name\":\"美国\",\"provinces\":[{\"name\":\"纽约\"},{\"name\":\"洛杉矶\"}]}]}}";
			long startTime = System.currentTimeMillis();
			Type jsonType = new TypeToken<BaseResp<List<Country>>>() {}.getType();  
			BaseResp<List<Country>> resp = gson.fromJson(json, jsonType);
			System.out.println(resp);
			long endTime = System.currentTimeMillis();
			System.out.println("程序运行时间：" + (endTime - startTime) + "ms"); // 输出程序运行时间
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
