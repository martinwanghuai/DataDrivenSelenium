package testCases;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.cgg.pl.funnel.RequestPayloadChecker;

import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.filters.RequestFilter;
import net.lightbody.bmp.proxy.CaptureType;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;
import utility.Checker;
import utility.Constant;

public class NewsletterTesting_withoutFramework {

	WebDriver driver;
	
	public void checkNewsLetterEmail(HashMap<String, Object> actualFields){
		
		Assert.assertEquals(actualFields.get("dm_consent"), "true");
		System.out.println("dm_consent checking passed");
		Assert.assertEquals(actualFields.get("tc_consent"), "true");
		System.out.println("tc_consent checking passed");
		Assert.assertEquals((String)actualFields.get("email") , "automated.qa@compareglobalgroup.com");
		System.out.println("email checking passed");
		Assert.assertEquals((String)actualFields.get("language") , "DA");
		System.out.println("language checking passed");
		Assert.assertEquals((String)actualFields.get("locale") , "DK");
		System.out.println("locale checking passed");
		Assert.assertEquals((String)actualFields.get("source_url") , "https://www.samlino.dk/");
		System.out.println("source_url checking passed");
		Assert.assertEquals((String)actualFields.get("vertical") , "subscriber");
		System.out.println("vertical checking passed");
	}
	
	private WebDriver setupWebDriverWithHTTPTrafficInterceptor(){

		//1. setup HTTP traffic interceptor
		BrowserMobProxyServer proxy = new BrowserMobProxyServer();
		proxy.enableHarCaptureTypes(CaptureType.REQUEST_HEADERS, CaptureType.RESPONSE_HEADERS, CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
		proxy.addRequestFilter(new RequestFilter(){
			
			@Override
			public io.netty.handler.codec.http.HttpResponse filterRequest(io.netty.handler.codec.http.HttpRequest request,
					HttpMessageContents contents, HttpMessageInfo messageInfo) {
				
				if(messageInfo.getOriginalUrl().contains("execute-api.eu-central-1.amazonaws.com")){					
					NewsletterTesting_withoutFramework instance = new NewsletterTesting_withoutFramework();
					if(!Checker.isBlank(contents.getTextContents())){
						System.out.println("Payload text:" + contents.getTextContents());
						HashMap<String, Object> actualFields = RequestPayloadChecker.createHashMapFromJsonString(contents.getTextContents());					
						instance.checkNewsLetterEmail(actualFields);	
					}
				}
				return null;
			}
		});
		proxy.start();
		Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);
		
		//2. Setup web driver
		System.setProperty("webdriver.chrome.driver", Constant.USRDIR + Constant.PATH_TO_CHROME_DRIVER);
		WebDriver driver = new ChromeDriver(capabilities);		
		return driver;
	}
	
	@BeforeTest
	public void setup(){
		
		driver = this.setupWebDriverWithHTTPTrafficInterceptor();		
	}
	
	@Test
	public void testNewsletter(){
		
		try{
			driver.get("https://www.samlino.dk/");
			
			WebElement elem = driver.findElement(By.xpath("//form[@name='newsletterForm']/descendant::input[@name='email']"));
			elem.sendKeys("automated.qa@compareglobalgroup.com");
			Thread.sleep(3000);
			
			elem = driver.findElement(By.xpath("//form[@name='newsletterForm']/descendant::input[@id='newsletter-terms--']"));
			elem.click();
			Thread.sleep(3000);
			
			elem = driver.findElement(By.xpath("//form[@name='newsletterForm']/descendant::input[@type='submit']"));
			elem.click();	
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@AfterTest
	public void shutdown(){
		
		try{
			Thread.sleep(30000);	
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			driver.quit();
		}
	}
	
}
