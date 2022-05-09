package org.java.world.mongo.factory;

import java.util.Arrays;
import java.util.List;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

/**
 * MongoDB 连接工厂类
 * 
 * @author dante
 *
 */
public abstract class MongoConnectorFactory {
	
	private static MongoClient instance;
	
	public static MongoClient getInstance() {
		return getInstance("localhost", 27017);
	}
	
	public static MongoClient getInstance(String host, int port) {
		if(instance == null) {
			instance = new MongoClient(host, port);
		}
		return instance;
	}
	
	public static MongoClient getInstance(String host, int port, String database, String user, String password) {
		if(instance == null) {
			MongoCredential credential = MongoCredential.createCredential(user, database, password.toCharArray());
			instance = new MongoClient(new ServerAddress(host, port), Arrays.asList(credential));
		}
		return instance;
	}
	
	public static MongoClient getDBInstance(String host, int port, String database, String user, String password) {
		if(instance == null) {
			MongoClientURI uri = new MongoClientURI("mongodb://".concat(user).concat(":").concat(password).concat("@").concat(host).concat("/?authSource=").concat(database));
			instance = new MongoClient(uri);
		}
		return instance;
	}
	
	public static MongoClient getInstance(List<ServerAddress> servers) {
		if(servers == null || servers.isEmpty()) {
			return null;
		}
		if(instance == null) {
			instance = new MongoClient(servers);
		}
		return instance;
	}
	
	public static void close(MongoClient client) {
		if(client != null) {
			client.close();
		}
	}
	
}
