package org.java.world.mongo;

import org.java.world.mongo.factory.MongoConnectorFactory;
import org.junit.After;
import org.junit.Before;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import lombok.extern.slf4j.Slf4j;

/**
 * 参考： https://mongodb.github.io/mongo-java-driver/3.4/driver/tutorials/perform-write-operations/
 * 
 * @author dante
 *
 */
@Slf4j
public class MongoBaseTest {

	public MongoClient mongoClient;
	public MongoDatabase db;

	@Before
	public void init() {
		log.info("************** 获取连接，使用数据库 docker-mongo **************");
		mongoClient = MongoConnectorFactory.getInstance();
		db = mongoClient.getDatabase("docker-mongo");
	}
	
	@After
	public void close() {
		MongoConnectorFactory.close(mongoClient);
		log.info("************** 关闭连接 **************");
	}
}
