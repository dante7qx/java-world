package org.java.world.mongo;

import java.util.Arrays;

import org.bson.Document;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MongoAggregationTests extends MongoBaseTest {

	private static MongoCollection<Document> collection = null;
	
	Block<Document> printBlock = new Block<Document>() {
		@Override
		public void apply(final Document document) {
			log.info("输出结果 -> {}", document.toJson());
		}
	};
	
	@Before
	public void initCollection() {
		collection = db.getCollection("restaurants");
	}

	/**
	 * 先获取 categories 中匹配 Coffee 的记录，然后再按 stars 计算总和
	 */
	@Test
	public void matchAndSum() {
		collection.aggregate(
				Arrays.asList(
					Aggregates.match(Filters.eq("categories", "Coffee")),
					Aggregates.group("$stars", Accumulators.sum("count", 1))
				))
		.forEach(printBlock);
	}
	
	/**
	 * 只取出 name 字段，并且计算 categories 中第一个元素
	 * 
	 * { "name" : "Amarcord Pizzeria", "firstCategory" : "Pizzeria" }
	 * { "name" : "Blue Coffee Bar", "firstCategory" : "Coffee" }
	 * { "name" : "Café Con Leche", "firstCategory" : "Bakery" }
	 */
	@Test
	public void project() {
		collection.aggregate(
			      Arrays.asList(
			          Aggregates.project(
			              Projections.fields(
			                    Projections.excludeId(),
			                    Projections.include("name"),
			                    Projections.computed(
			                            "firstCategory",
			                            new Document("$arrayElemAt", Arrays.asList("$categories", 0))
			                    )
			              )
			          )
			      )
			).forEach(printBlock);
	}

}
