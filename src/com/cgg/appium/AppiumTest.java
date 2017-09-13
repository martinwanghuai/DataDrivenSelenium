package com.cgg.appium;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class AppiumTest {
	WebDriver driver;

	@BeforeClass
	public void setUp() throws MalformedURLException {

	}

	@Test(enabled = true)
	public void testMoneyHero() throws Exception {

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "BROWSER");
		capabilities.setCapability(MobileCapabilityType.VERSION, "4.4.2");
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Emulator");
		capabilities.setCapability(MobileCapabilityType.PLATFORM, "Android");
		capabilities.setCapability(MobileCapabilityType.APP_PACKAGE, "com.android.chrome");
		capabilities.setCapability(MobileCapabilityType.APP_ACTIVITY, "com.google.android.apps.chrome.ChromeTabbedActivity");
		driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		driver.get("http://www.moneyhero.com.hk/en/");
	}
	
	@Test(enabled = false)
	public void testCal() throws Exception {
		
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("BROWSER_NAME", "Android");
		capabilities.setCapability("VERSION", "4.4.2");
		capabilities.setCapability("deviceName", "Emulator");
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("appPackage", "com.android.calculator2");
		capabilities.setCapability("appActivity", "com.android.calculator2.Calculator");
		driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		
		WebElement three = driver.findElement(By.name("3"));
		three.click();
		WebElement five = driver.findElement(By.name("5"));
		five.click();
		WebElement plus = driver.findElement(By.name("+"));
		plus.click();
		WebElement four = driver.findElement(By.name("4"));
		four.click();
		WebElement zero = driver.findElement(By.name("0"));
		zero.click();
		WebElement equalTo = driver.findElement(By.name("="));
		equalTo.click();

		WebElement results = driver.findElement(By.tagName("EditText"));

		assert results.getText().equals("75") : "Actual value is : " + results.getText()
				+ " did not match with expected value: 75";

	}

	@AfterClass
	public void teardown() {

		driver.quit();
	}
}
