package org.java.world.dante.selenium;

import java.time.Duration;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * https://www.selenium.dev/zh-cn/documentation/
 * 
 * @author dante
 *
 */

public class InstallDriversTest {
	
	@Test
    @Disabled("Do not run in CI")
    public void chromeSession() {
        WebDriverManager.chromedriver().setup();

        WebDriver driver = new ChromeDriver();
        
        driver.quit();
    }

    @Test
    @Disabled("Do not run in CI")
    public void edgeSession() {
        WebDriverManager.edgedriver().setup();

        WebDriver driver = new EdgeDriver();

        driver.quit();
    }

    @Test
    @Disabled("Do not run in CI")
    public void firefoxSession() {
        WebDriverManager.firefoxdriver().setup();

        WebDriver driver = new FirefoxDriver();

        driver.quit();
    }

    @Test
    @Disabled("Do not run in CI")
    public void ieSession() {
        WebDriverManager.iedriver().setup();

        WebDriver driver = new InternetExplorerDriver();

        driver.quit();
    }
    
    @Test
    public void eightComponents() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.selenium.dev/selenium/web/web-form.html");

        String title = driver.getTitle();
        System.out.println("Web form ==> " + title);

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        WebElement textBox = driver.findElement(By.name("my-text"));
        WebElement submitButton = driver.findElement(By.cssSelector("button"));

        textBox.sendKeys("Selenium");
        submitButton.click();

        WebElement message = driver.findElement(By.id("message"));
        String value = message.getText();
        
        System.out.println("Received! ==> " + value);

        driver.quit();
        
    }
    
    /**
     * https://blog.csdn.net/bcfdsagbfcisbg/article/details/121741801
     * 
     * @throws InterruptedException
     */
    @Test
    public void gansuGov() throws InterruptedException {
//    	String url = "http://kjt.gansu.gov.cn/common/search/8b0e378b0f424ed2b381455e389c24b3?_isAgg=true&_isJson=true&_pageSize=20&_template=index&_rangeTimeGte=&_channelName=&page=1";
    	String url = "https://rst.gansu.gov.cn/rst/c113772/tzgg_list.shtml";
    	ChromeOptions options = new ChromeOptions();
//    	options.addArguments("--disable-extensions");
        options.addArguments("--disable-gpu");
        options.setExperimentalOption("detach", Boolean.TRUE);
        options.setExperimentalOption("useAutomationExtension", Boolean.FALSE);
//        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
//        options.addArguments("--disable-blink-features=AutomationControlled");
        
        ChromeDriver driver = new ChromeDriver(options);
        
        
        
//        driver.executeCdpCommand("Network.enable", Maps.newHashMap());
//        Map<String, Object> headers = new HashMap<>();
//        headers.put("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.517 Safari/537.36");
//        headers.put("Accept-Encoding", "gzip, deflate, br");
//        Map<String, Object> params = new HashMap<>();
//        params.put("headers", headers);
//        driver.executeCdpCommand("Network.setExtraHTTPHeaders", params);
//        Map<String, Object> source = new HashMap<>();
//        source.put("source", "Object.defineProperty(navigator, 'webdriver', {get: () => undefined})");
//        driver.executeCdpCommand("Page.addScriptToEvaluateOnNewDocument", source);
        
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

    	
	    driver.get(url);
	    
	    System.out.println(driver.getPageSource());
	    
	    
	    Set<Cookie> cookies = driver.manage().getCookies();
        System.out.println(cookies.toString());
        String cookie = cookies.stream().map(c -> c.getName().concat("=").concat(c.getValue())).collect(Collectors.joining("; "));
        System.out.println(cookie);
        
        Thread.sleep(10000L);
        
        /*
        HttpRequest req = HttpUtil.createGet(url);
		Map<String, String> headers = new HashMap<>();
		headers.put("Cookie", cookie);
		headers.put("Accept-Encoding", "gzip, deflate");
		headers.put("Accept-Language", "zh-CN,zh;q=0.9");
		headers.put("Host", "kjt.gansu.gov.cn");
		headers.put("Proxy-Connection", "keep-alive");
		headers.put("Referer", "http://kjt.gansu.gov.cn/common/search/8b0e378b0f424ed2b381455e389c24b3?_isAgg=true&_isJson=true&_pageSize=20&_template=index&_rangeTimeGte=&_channelName=&page=1");
		headers.put("Upgrade-Insecure-Requests", "1");
		headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Safari/537.36");
		req.addHeaders(headers);
		HttpResponse resp = req.execute();
		int status = resp.getStatus();
		System.out.println(status);
		*/
//	    driver.quit();
    }

}
