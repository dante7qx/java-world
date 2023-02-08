package org.java.world.dante.json;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import org.java.world.dante.json.vo.UserVO;
import org.junit.Before;
import org.junit.Test;

import cn.hutool.core.date.StopWatch;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 开源的 JSON 类库比较
 * 
 * http://www.open-open.com/lib/view/open1397870197828.html
 * 
 * @author dante
 *
 */
@Slf4j
public class PerformanceComparisonTests {

	ObjectMapper mapper = new ObjectMapper();
	Gson gson = new Gson();
	List<UserVO> users = new ArrayList<UserVO>();
	int count = 5000;

	@Before
	public void init() {
		for (int i = 0; i < count; i++) {
			UserVO user = new UserVO("男");
			user.setId(i);
			user.setName("张三" + i);
			user.setBirthday(Date.from(Instant.now()));
			users.add(user);
		}
	}

	@Test
	public void testSerializationAndDeserialization() throws IOException {
		log.info("===================测试对象数目：{}====================", count);
		log.info("===================序列化用时====================");
		// 测试jackson用时
		long start2 = new Date().getTime();
		mapper.writeValueAsString(users);
		long end2 = new Date().getTime();
		log.info("jackson转换共用时：" + (end2 - start2) + "ms");

		// 测试gson用时
		long start = new Date().getTime();
		gson.toJson(users);
		long end = new Date().getTime();
		log.info("gson转换共用时：" + (end - start) + "ms");

		// 测试fastjson用时
		long start1 = new Date().getTime();
		JSON.toJSONString(users);
		long end1 = new Date().getTime();
		log.info("fastjson转换共用时：" + (end1 - start1) + "ms");
		
		// 测试fastjson用时
		long start21 = new Date().getTime();
		com.alibaba.fastjson2.JSON.toJSONString(users);
		long end21 = new Date().getTime();
		log.info("fastjson2转换共用时：" + (end21 - start21) + "ms");
		
		// 测试Hutool JSONUtil用时
		long start3 = new Date().getTime();
		JSONConfig jsonConfig = new JSONConfig();
		jsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
		JSONUtil.toJsonStr(users, jsonConfig);
		long end3 = new Date().getTime();
		log.info("Hutool JSONUtil转换共用时：" + (end3 - start3) + "ms");
		
		String json = mapper.writeValueAsString(users);
//		log.info(json);
//		log.info(hutoolJson);
		
		log.info("===================反序列化用时====================");
		// 测试jackson用时
		long s1 = new Date().getTime();
//		mapper.readValue(json, new TypeReference<List<UserVO>>() {});
		mapper.readValue(json, List.class);
		long e1 = new Date().getTime();
		log.info("jackson转换共用时：" + (e1 - s1) + "ms");

		// 测试gson用时
		long s2 = new Date().getTime();
//		gson.fromJson(json, new TypeToken<List<UserVO>>() {}.getType());
		gson.fromJson(json, List.class);
		long e2 = new Date().getTime();
		log.info("gson转换共用时：" + (e2 - s2) + "ms");

		// 测试fastjson用时
		long s3 = new Date().getTime();
//		JSON.parseObject(json, new com.alibaba.fastjson.TypeReference<List<UserVO>>() {}.getType());
		JSON.parseObject(json, List.class);
		long e3 = new Date().getTime();
		log.info("fastjson转换共用时：" + (e3 - s3) + "ms");
		
		// 测试fastjson2用时
		long s4 = new Date().getTime();
//				JSON.parseObject(json, new com.alibaba.fastjson.TypeReference<List<UserVO>>() {}.getType());
		com.alibaba.fastjson2.JSON.parseObject(json, List.class);
		long e4 = new Date().getTime();
		log.info("fastjson2转换共用时：" + (e4 - s4) + "ms");
		
		// 测试Hutool JSONUtil用时
		StopWatch stopWatch = new StopWatch("Hutool JSONUtil");
		stopWatch.start();
		JSONUtil.parseArray(json, jsonConfig);
		stopWatch.stop();
		log.info("Hutool JSONUtil转换共用时：{}ms", stopWatch.getTotalTimeMillis());
		log.info("==================================================");
	}
	
}
