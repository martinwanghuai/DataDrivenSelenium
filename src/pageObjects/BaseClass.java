package pageObjects;

import org.openqa.selenium.WebDriver;

import utility.WebDriverUtils;

public class BaseClass {
	public static WebDriverUtils driverUtils;
	public static boolean bResult = true;

	public BaseClass(){
		super();
	}
	
	public  BaseClass(WebDriverUtils driver){
		
		BaseClass.driverUtils = driver;
	}

}
