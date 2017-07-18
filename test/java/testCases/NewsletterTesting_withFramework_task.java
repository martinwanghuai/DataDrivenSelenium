package testCases;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.cgg.pl.funnel.RequestPayloadChecker;

import net.lightbody.bmp.filters.RequestFilter;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;
import utility.Checker;
import utility.WebDriverUtils;

public class NewsletterTesting_withFramework_task {

WebDriverUtils driver;
	
	public void checkNewsLetterEmail(HashMap<String, Object> actualFields){
		//logic to check newsletter
	}
	
	@BeforeTest
	public void setup(){
		
		driver = new WebDriverUtils();
		
		RequestFilter filter = new RequestFilter(){
			
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
		};
			
		driver.setRequestFilter(filter);
	}
	
	@Test
	public void testNewsletter(){
		//interact with homepage Newsletter
		
	}
	
	@AfterTest
	public void shutdown(){
		
		try{
			Thread.sleep(30000);	
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			driver.shutDown();
		}
	}
	
}
