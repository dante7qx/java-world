package org.dante.shiro.session;

import java.io.Serializable;

import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.dante.shiro.utils.JedisUtils;
import org.dante.shiro.utils.SerializableUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

public class RedisSessionDao extends CachingSessionDAO {

	private final static Logger logger = LoggerFactory.getLogger(RedisSessionDao.class);

	private JedisUtils jedisUtils = JedisUtils.getInstance();
	private Jedis jedis = jedisUtils.getJedis("172.16.239.129", 6379);

	@Override
	protected void doUpdate(Session session) {
		if (session instanceof ValidatingSession && !((ValidatingSession) session).isValid()) {
			throw new InvalidSessionException("会话超时");
		}
		logger.info("Redis -> 更新Session[" + session.getId() + ", CurrentUser: " + session.getAttribute("currentUser") + "]");
	}

	@Override
	protected void doDelete(Session session) {
		logger.info("Redis -> 删除Session[" + session.getId() + "]");
		jedis.del(session.getId().toString());
	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = generateSessionId(session);
		assignSessionId(session, sessionId);
		try {
			jedis.set(sessionId.toString(), new String(SerializableUtils.serialize(session)));
			logger.info("Redis -> 创建Session[" + sessionId + "]");
		} catch (Exception e) {
			logger.error("创建session失败", e);
		}
		return session.getId();
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		String sessionStr = jedis.get(sessionId.toString());
		if(sessionStr == null) {
			return null;
		}
		Session session = null;
		try {
			session = (Session) SerializableUtils.deserialize(sessionStr);
			if (session instanceof ValidatingSession && !((ValidatingSession) session).isValid()) {
				throw new InvalidSessionException("会话超时");
			}
			logger.info("Redis -> 读取Session[" + sessionId + "]");
		} catch (Exception e) {
			logger.error("获取session失败", e);
		}
		return session;
	}

}
