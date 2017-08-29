package utility;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class SeleniumTester {

	@Test
	public void test1() throws Exception {

/*		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/driver/chromedriver");
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--kiosk");
		WebDriver driver = new ChromeDriver(chromeOptions);
		driver.get("http://dev.vlvdev.com/");

		By by = By.xpath("(//a[@title='Men'])[1]");
		driver.findElement(by).click();
		Thread.sleep(10000);
		driver.quit();*/
		
		WebDriverUtils utils = new WebDriverUtils();
		utils.openURL("http://dev.vlvdev.com");
		By by = By.xpath("(//a[@title='Men'])[1]");
		utils.clickLink(by);
		utils.explicitWait();
		utils.shutDown();
	}
}
