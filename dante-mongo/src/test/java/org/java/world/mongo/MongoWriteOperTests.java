package org.java.world.mongo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.BulkWriteOptions;
import com.mongodb.client.model.DeleteOneModel;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.InsertOneModel;
import com.mongodb.client.model.ReplaceOneModel;
import com.mongodb.client.model.UpdateOneModel;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;

public class MongoWriteOperTests extends MongoBaseTest {
	
	private static MongoCollection<Document> collection = null;
	
	@Before
	public void initCollection() {
		collection = db.getCollection("restaurants");
	}

	@Test
	public void insert() {
		Document document = new Document("name", "Café Con Leche")
	               .append("contact", new Document("phone", "228-555-0149")
	                                       .append("email", "cafeconleche@example.com")
	                                       .append("location",Arrays.asList(-73.92502, 40.8279556)))
	               .append("stars", 3)
	               .append("categories", Arrays.asList("Bakery", "Coffee", "Pastries"));
		collection.insertOne(document);
	}
	
	@Test
	public void batchInsert() {
		Document doc1 = new Document("name", "Amarcord Pizzeria")
	               .append("contact", new Document("phone", "264-555-0193")
	                                       .append("email", "amarcord.pizzeria@example.net")
	                                       .append("location",Arrays.asList(-73.88502, 40.749556)))
	               .append("stars", 2)
	               .append("categories", Arrays.asList("Pizzeria", "Italian", "Pasta"));


		Document doc2 = new Document("name", "Blue Coffee Bar")
		               .append("contact", new Document("phone", "604-555-0102")
		                                       .append("email", "bluecoffeebar@example.com")
		                                       .append("location",Arrays.asList(-73.97902, 40.8479556)))
		               .append("stars", 5)
		               .append("categories", Arrays.asList("Coffee", "Pastries"));
	
		List<Document> documents = new ArrayList<Document>();
		documents.add(doc1);
		documents.add(doc2);
		collection.insertMany(documents);
	}
	
	@Test
	public void update() {
		collection.updateOne(Filters.eq("_id", new ObjectId("5a05534acf4d156f44f563be")),
				Updates.combine(Updates.set("stars", 1), Updates.set("contact.phone", "228-555-9999"), Updates.currentDate("lastModified")));
		collection.updateMany(
				Filters.eq("stars", 0),
				Updates.combine(Updates.set("stars", 100), Updates.currentDate("lastModified")));
	}
	
	@Test
	public void replaceOne() {
		collection.replaceOne(
				Filters.eq("_id", new ObjectId("5a055557cf4d156f6a777b2c")),
                new Document("name", "胡大饭庄")
                        .append("contact", "800-800-800")
                        .append("categories", Arrays.asList("麻小", "秋葵", "剁椒鱼头")),
                new UpdateOptions().upsert(true).bypassDocumentValidation(true));

		collection.replaceOne(
				Filters.eq("_id", 1),
                new Document("name", "胡大饭庄")
                        .append("contact", "800-800-800")
                        .append("categories", Arrays.asList("麻小", "秋葵", "剁椒鱼头")),
                new UpdateOptions().upsert(true).bypassDocumentValidation(true));
	}
	
	@Test
	public void delete() {
		collection.deleteOne(Filters.eq("_id", 1));
		collection.deleteMany(Filters.lt("stars", 10));
	}
	
	/**
	 * 2.6 版本后使用
	 * 
	 * 1. 有序批量操作按顺序执行所有操作，并在第一次写入错误时出错。
	 * 2. 无序批量操作执行所有操作并报告任何错误。 无序批量操作不保证执行顺序
	 * 
	 */
	@Test
	public void bulkWriteOrder() {
		// 1. Ordered bulk operation - order is guaranteed
		collection.bulkWrite(
		  Arrays.asList(new InsertOneModel<>(new Document("_id", 4)),
		                new InsertOneModel<>(new Document("_id", 5)),
		                new InsertOneModel<>(new Document("_id", 6)),
		                new UpdateOneModel<>(new Document("_id", 1),
		                                     new Document("$set", new Document("x", 2))),
		                new DeleteOneModel<>(new Document("_id", 2)),
		                new ReplaceOneModel<>(new Document("_id", 3),
		                                      new Document("_id", 3).append("x", 4))));
	}
	
	@Test
	public void bulkWriteUnOrder() {
		 // 2. Unordered bulk operation - no guarantee of order of operation
		collection.bulkWrite(
		  Arrays.asList(new InsertOneModel<>(new Document("_id", 4)),
		                new InsertOneModel<>(new Document("_id", 5)),
		                new InsertOneModel<>(new Document("_id", 6)),
		                new UpdateOneModel<>(new Document("_id", 1),
		                                     new Document("$set", new Document("x", 2))),
		                new DeleteOneModel<>(new Document("_id", 2)),
		                new ReplaceOneModel<>(new Document("_id", 3),
		                                      new Document("_id", 3).append("x", 4))),
		  new BulkWriteOptions().ordered(false));
	}

}
