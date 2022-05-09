package org.java.world.mongo;

import org.bson.Document;
import org.junit.Test;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ValidationLevel;
import com.mongodb.client.model.ValidationOptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MongoCollectionTests extends MongoBaseTest {
	
	private static final String COLLECTION_NAME = "javamongo";
	
	@Test
	public void create() {
		log.info("************** 创建集合javamongo ***************");
		db.createCollection(COLLECTION_NAME);
	}
	
	@Test
	public void createWithOptions() {
		log.info("************** 创建集合javamongo ***************");
		CreateCollectionOptions options = new CreateCollectionOptions().capped(true).sizeInBytes(0x100000);
		db.createCollection(COLLECTION_NAME, options);
	}
	
	@Test
	public void createWithValidation() {
		// 当文档contacts中存在 email、phone时，新增、更新操作才会应用到此文档中
		// http://m.blog.csdn.net/u013066244/article/details/73799927
		ValidationOptions collOptions = new ValidationOptions().validator(
		        Filters.or(Filters.exists("email"), Filters.exists("phone")))
				.validationLevel(ValidationLevel.MODERATE);
		db.createCollection("contacts",
		        new CreateCollectionOptions().validationOptions(collOptions));
	}
	
	@Test
	public void drop() {
		log.info("************** 删除集合javamongo ***************");
		MongoCollection<Document> collection = db.getCollection(COLLECTION_NAME);
		if(collection != null) {
			log.info("当前集合 {}。", collection.toString());
			collection.drop();
		}
	}
	
	@Test
	public void query() {
		MongoIterable<String> listCollectionNames = db.listCollectionNames();
		for (String name : listCollectionNames) {
			log.info("集合 -> {}.", name);
		}
	}
}