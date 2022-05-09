package org.dante.shiro.test;

import org.dante.shiro.utils.JedisUtils;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

public class JedisTest {
	private final static Logger logger = LoggerFactory.getLogger(JedisTest.class);
	
	private JedisUtils jedisUtils = JedisUtils.getInstance();
	private Jedis jedis = null;
	
	@Before
	public void setUp() {
		jedis = jedisUtils.getJedis("172.16.239.129", 6379);
	}

	@Test
	public void testGetKey() {
		String sessionVal = jedis.get("sessionId");
		logger.info(sessionVal);
		jedisUtils.closeJedis(jedis);
	}

	@Test
	public void testSetKey() {
		String testKey = "testKey";
		jedis.set(testKey, testKey);
		logger.info(jedis.get(testKey));
		jedisUtils.closeJedis(jedis);
	}
	
	@Test
	public void testDelKey() {
		String testKey = "testKey";
		Long returnNum = jedis.del(testKey);
		logger.info("returnNum=" + returnNum);
		jedisUtils.closeJedis(jedis);
	}
	
}
