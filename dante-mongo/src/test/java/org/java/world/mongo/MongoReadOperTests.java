package org.java.world.mongo;

import org.bson.Document;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Sorts;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MongoReadOperTests extends MongoBaseTest {

	private static MongoCollection<Document> collection = null;

	Block<Document> printBlock = new Block<Document>() {
		@Override
		public void apply(final Document document) {
			log.info(document.toJson());
		}
	};

	@Before
	public void initCollection() {
		collection = db.getCollection("restaurants");
	}

	@Test
	public void read() {
		// 第一种写法
		collection.find().forEach(printBlock);
		// 第二种写法
		collection.find(new Document()).forEach(printBlock);
		collection.find().map(d -> d.get("name").toString()).forEach(new Block<String>() {
			@Override
			public void apply(String name) {
				log.info("name -> {}", name);
			}
		});
		// sort、skip、limit
		collection.find().sort(Sorts.descending("stars")).skip(1).limit(1).forEach(printBlock);
		// 多条件查询
		collection.find(and(gte("stars", 80), lt("stars", 120), eq("categories", "Italian"))).forEach(printBlock);
	}

	@Test
	public void readAfterProjection() {
		// 第一中写法
		collection.find(and(gte("stars", 80), lt("stars", 120), eq("categories", "Pizzeria")))
				.projection(new Document("name", 1).append("stars", 1).append("categories", 1).append("_id", 0))
				.forEach(printBlock);
		// 第二种写法
		collection.find(and(gte("stars", 80), lt("stars", 120), eq("categories", "Pizzeria")))
				.projection(fields(include("name", "stars", "categories"), excludeId())).forEach(printBlock);
	}

}
