package pages;

import org.openqa.selenium.By;

import utility.Constant;
import utility.WebDriverUtils;

public class HomePage {

	private static By PLFunnel = By.xpath("//a[@ga-category='personal-loan'][1]");	
	private static By CIFunnel = By.xpath("//a[@ga-category='car-insurance'][1]");	
	private static By BBFunnel = By.xpath("//a[@ga-category='broadband'][1]");
	private static By CCFunnel = By.xpath("(//a[@ga-category='credit-card'])[2] | //a[@ga-category='credit-card']");
	
	WebDriverUtils driver;
	
	public HomePage(WebDriverUtils driver){
		
		this.driver = driver;
	}
	
	public void goToPLFunnelPage(){
		
		driver.implicitWait(PLFunnel, Constant.IMPLICITWAIT_MILLIS, 1);
		driver.clickButton(PLFunnel);
		driver.explicitWait();
	}
	
	public void goToCIFunnelPage(){
		
		driver.implicitWait(CIFunnel, Constant.IMPLICITWAIT_MILLIS, 1);
		driver.clickButton(CIFunnel);
		driver.explicitWait();
	}
	
	public void goToBBFunnelPage(){
		
		driver.implicitWait(BBFunnel, Constant.IMPLICITWAIT_MILLIS, 1);
		driver.clickButton(BBFunnel);
		driver.explicitWait();
	}
	
	public void goToCCFunnelPage(){
		
		driver.implicitWait(CCFunnel, Constant.IMPLICITWAIT_MILLIS, 1);
		driver.clickButton(CCFunnel);
		driver.explicitWait();
	}
}
