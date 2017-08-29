package utility;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumTester {

	@Test
	public void test1() throws Exception {

		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/driver/chromedriver");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("http://dev.vlvdev.com/");

		By by = By.xpath("(//a[@title='Men'])[1]");
		driver.findElement(by).click();
		Thread.sleep(10000);
		driver.quit();
		
/*		WebDriverUtils utils = new WebDriverUtils();
		utils.openURL("http://dev.vlvdev.com");
		By by = By.xpath("(//a[@title='Men'])[1]");
		utils.findElement(by).click();
		utils.explicitWait(3000);
		utils.quit();
*/	}
}
