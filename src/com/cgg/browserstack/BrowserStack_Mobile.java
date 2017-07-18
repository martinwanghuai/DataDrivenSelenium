package com.cgg.browserstack;

import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class BrowserStack_Mobile {

	public static final String USERNAME = "wanghuai1";
	  public static final String AUTOMATE_KEY = "ghW3N6SAJyRxmfcpppDo";
	  public static final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";

	  public static void main(String[] args) throws Exception {

	    DesiredCapabilities caps = new DesiredCapabilities();
	    caps.setCapability("browser", "chrome");
	    caps.setCapability("browserstack.debug", "true");
	    caps.setCapability("build", "First build");
	    caps.setCapability("realMobile", "true");
//	    caps.setCapability("device", "iPhone 7");
	    caps.setCapability("device", "Samsung Galaxy S7");
	    caps.setCapability("browserstack.appium_version", "1.6.3");

	    WebDriver driver = new RemoteWebDriver(new URL(URL), caps);
//	    System.setProperty("webdriver.chrome.driver", Constant.USRDIR + Constant.PATH_TO_CHROME_DRIVER);
//	    WebDriver driver = new ChromeDriver(caps);
	    driver.get("https://www.moneyhero.com.hk/en/personal-loan");
	    
	    WebElement element = driver.findElement(By.xpath("//a[@ga-action='Loan Widget Buttons']"));

	    element.click();

	    System.out.println(driver.getTitle());
	    driver.quit();

	  }
}
