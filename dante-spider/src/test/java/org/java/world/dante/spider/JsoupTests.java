package org.java.world.dante.spider;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 获取页面（http://en.wikipedia.org/）包含（#mp-itn b a）选择器的所有元素，并打印这些元素的 title , herf 属性
 * 
 * @author dante
 *
 */
public class JsoupTests {
	public static void main(String[] args) throws IOException {
		parseWiki();
		parseBaidu();
	}
	
	private static void parseBaidu() throws IOException {
		Document doc = Jsoup.connect("https://www.baidu.com/").get();
	    Elements newsHeadlines = doc.select("a[href]");
	    for (Element headline : newsHeadlines) {
	        System.out.println("href: " +headline.absUrl("href") );
	    }
	}
	
	private static void parseWiki() throws IOException {
		Document doc = Jsoup.connect("http://en.wikipedia.org/").get();
		log(doc.title());

		Elements newsHeadlines = doc.select("#mp-itn b a");
		for (Element headline : newsHeadlines) {
			log("%s\n\t%s", headline.attr("title"), headline.absUrl("href"));
		}
	}

	private static void log(String msg, String... vals) {
		System.out.println(String.format(msg, vals));
	}
}
