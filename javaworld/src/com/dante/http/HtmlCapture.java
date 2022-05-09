package com.dante.http;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.TableRow;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class HtmlCapture {
	
	public String captureHtml(String strURL) throws Exception {
	    URL url = new URL(strURL);  
	    HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();  
//	    httpConn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
	    
	    httpConn.setRequestProperty(
	    		"User-Agent",
	    		"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:28.0)");
	    
	    InputStreamReader input = new InputStreamReader(httpConn  
	            .getInputStream(), "utf-8");  
	    BufferedReader bufReader = new BufferedReader(input);  
	    String line = "";  
	    StringBuilder contentBuf = new StringBuilder();  
	    while ((line = bufReader.readLine()) != null) {  
	        contentBuf.append(line);  
	    }  
	    String buf = contentBuf.toString(); 
	    this.parseHttpHtml(buf);
	    return buf;
	}
	
	public List<Capture> captureFile() throws Exception {
		String fileName = "/Users/dante/Documents/Project/javaworld/webapp/capture/dzdp.html";
		return parseHtml((this.readAsText(fileName, "utf-8")));
	}
	
	private CaptureDetail parseHttpHtml(String webContent) throws ParserException {
		String tel = "无";
		Parser parser = Parser.createParser(webContent, "utf-8");
		NodeFilter filter = new AndFilter(
				new OrFilter(new TagNameFilter("span"),new TagNameFilter("img")), new OrFilter(new HasAttributeFilter("itemprop", "street-address"), new OrFilter(new HasAttributeFilter("itemprop", "tel"), new HasAttributeFilter("itemprop", "photo")))); 
		NodeList nodes = parser.extractAllNodesThatMatch(filter);
		
		NodeList as = nodes.extractAllNodesThatMatch(new AndFilter(new TagNameFilter("span"),new HasAttributeFilter("itemprop", "street-address")));
		NodeList ts = nodes.extractAllNodesThatMatch(new AndFilter(new TagNameFilter("span"),new HasAttributeFilter("itemprop", "tel")));
		NodeList is = nodes.extractAllNodesThatMatch(new AndFilter(new TagNameFilter("img"),new HasAttributeFilter("itemprop", "photo")));
		
		CaptureDetail detail = new CaptureDetail();
		detail.setAddress(as.elementAt(0).getFirstChild().getText().trim());
		
		Node telNode1 = ts.elementAt(0);
		Node telNode2 = ts.elementAt(1);
		if(telNode1 != null) {
			tel = telNode1.getFirstChild().getText().trim();
		}
		if(telNode2 != null) {
			tel += "  " + telNode2.getFirstChild().getText().trim();
		}
		detail.setTel(tel);
		
		Node imgNode = is.elementAt(0);
		if(imgNode != null) {
			String imgTxt = imgNode.getText();
		    int hrefStart = imgTxt.indexOf("src=");
		    int hrefEnd = imgTxt.lastIndexOf("title");
		    String imgUrl = imgTxt.substring(hrefStart + 5, hrefEnd - 2);
		    detail.setImgUrl(imgUrl);
		} else {
			System.out.println(detail.getAddress() + " --> 未找到图片！");
		}
		return detail;
	}
	
	private List<Capture> parseHtml(String content) throws Exception {
		List<Capture> list = new ArrayList<Capture>();
		Parser parser = Parser.createParser(content, "utf-8");
		NodeList nodeList = parser.extractAllNodesThatMatch(new TagNameFilter("tr")); 
		for (int i = 0; i < nodeList.size(); i++) { 
		    TableRow tr = (TableRow)nodeList.elementAt(i); 
		    Node rank = tr.childAt(1).getFirstChild().getFirstChild();
		    
		    Node shop = tr.childAt(3).getFirstChild();
		    Node shopName = shop.getFirstChild().getFirstChild();
		    String href = shop.getText();
		    int hrefStart = shop.getText().indexOf("href=");
		    int hrefEnd = shop.getText().lastIndexOf("\"");
		    String url = href.substring(hrefStart + 6, hrefEnd);
		    CaptureDetail captureDetail = this.parseHttpHtml(this.captureHtml(url));
		    
		    Node mainRegionName = tr.childAt(5).getFirstChild().getFirstChild();
		    Node refinedScore1 = tr.childAt(7).getFirstChild().getFirstChild();
		    Node refinedScore2 = tr.childAt(9).getFirstChild().getFirstChild();
		    Node refinedScore3 = tr.childAt(11).getFirstChild().getFirstChild();
		    Node avgPrice = tr.childAt(13).getFirstChild().getFirstChild();
		    
		    Capture capture = new Capture();
		    capture.setRank(rank.getText().trim());
		    capture.setShopName(shopName.getText().trim());
		    capture.setMainRegionName(mainRegionName.getText().trim());
		    capture.setRefinedScore1(refinedScore1.getText().trim());
		    capture.setRefinedScore2(refinedScore2.getText().trim());
		    capture.setRefinedScore3(refinedScore3.getText().trim());
		    capture.setAvgPrice(avgPrice.getText().trim());
		    capture.setAddress(captureDetail.getAddress());
		    capture.setTel(captureDetail.getImgUrl());
		    list.add(capture);
		    
		    HttpImgCapture.exportImg(captureDetail.getImgUrl(), rank.getText().trim() + ".jpg");
		    
		    System.out.println(rank.getText().trim() + "==" + shopName.getText().trim() + "==" + mainRegionName.getText().trim()
		    		+ "==" + refinedScore1.getText().trim() + "==" + refinedScore2.getText().trim() + "==" + refinedScore3.getText().trim()
		    		+ "==" + avgPrice.getText().trim() + "==" + captureDetail.getAddress() + "==" + captureDetail.getTel());
		} 
		return list;
	}
	
	private String readAsText(String fileName, String encoding) {
		StringBuffer buf = new StringBuffer();
		
		try {
			File file = new File(fileName);
			InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
			BufferedReader ins = new BufferedReader(read);
			String dataline = "";
			while(null != (dataline = ins.readLine())) {
				buf.append(dataline);
			}
			
			ins.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return buf.toString();
	}
	
	private boolean exportCsv(File file, List<Capture> captures){
		boolean isSucess = false;
		FileOutputStream out = null;
		OutputStreamWriter osw = null;
		BufferedWriter bw = null;
		try {
			out = new FileOutputStream(file);
			osw = new OutputStreamWriter(out);
			bw = new BufferedWriter(osw);
			bw.append("排名,商户名,商区,口味分,环境分,服务分,人均消费,地址,电话").append("\r");
			if (captures != null && !captures.isEmpty()) {
				int i = 0;
				int size = captures.size() - 1;
				for (Capture capture : captures) {
					bw.append(capture.getRank()).append(",")
						.append(capture.getShopName()).append(",")
						.append(capture.getMainRegionName()).append(",")
						.append(capture.getRefinedScore1()).append(",")
						.append(capture.getRefinedScore2()).append(",")
						.append(capture.getRefinedScore3()).append(",")
						.append(capture.getAvgPrice()).append(",")
						.append(capture.getAddress()).append(",")
						.append(capture.getTel());
					if(i < size) {
						bw.append("\r");
					}
					i++;
				}
			}
			isSucess = true;
		} catch (Exception e) {
			isSucess = false;
		} finally {
			if (bw != null) {
				try {
					bw.close();
					bw = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (osw != null) {
				try {
					osw.close();
					osw = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
					out = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return isSucess;
    }
	
	private void exportExcel() {
		FileOutputStream fileOut = null;
		try {
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("大众点评");
			HSSFRow row = sheet.createRow(2);
			HSSFCell cell = row.createCell(2);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("222211111");
			
		    fileOut = new FileOutputStream("/Users/dante/Documents/Project/javaworld/webapp/capture/dianping.xls");    
            wb.write(fileOut);    
            
            wb.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(fileOut != null){    
                try {    
                    fileOut.close();    
                } catch (IOException e) {    
                    // TODO Auto-generated catch block    
                    e.printStackTrace();    
                }    
            }
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		HtmlCapture hc = new HtmlCapture();
//		hc.captureHtml("http://www.dianping.com/shop/22006893");
//		hc.captureHtml("http://www.dianping.com/shop/22378360");
//		hc.captureHtml("http://www.dianping.com/shop/5260542");
//		hc.captureHtml("http://www.dianping.com/shop/5179760");
//		hc.exportCsv(new File("/Users/dante/Documents/Project/javaworld/webapp/capture/dianping.csv"), hc.captureFile());
//		hc.captureFile();
		
//		hc.exportExcel();
	}
	
}
