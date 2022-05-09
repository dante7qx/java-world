package org.java.world.mongo;

import org.bson.Document;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MongoTextSearchTests extends MongoBaseTest {

	private static MongoCollection<Document> collection = null;

	@Before
	public void initCollection() {
		collection = db.getCollection("restaurants");
		collection.createIndex(Indexes.text("name"));
	}

	/**
	 * 全文检索对每一个词建立一个索引，指明该词在文章中出现的次数和位置，
	 * 当用户查询时，检索程序就根据事先建立的索引进行查找，并将查找的结果反馈给用户的检索方式。
	 * 
	 */
	@Test
	public void textSearch() {
		long matchCount = collection.count(Filters.text("Leche"));
		log.info("文本匹配数量: " + matchCount);

		collection.find(Filters.text("Con Leche")).projection(Projections.metaTextScore("score"))
				.sort(Sorts.metaTextScore("score")).forEach(new Block<Document>() {
			        @Override
			        public void apply(final Document document) {
			            System.out.println(document.toJson());
			        }
			    });
	}

}
