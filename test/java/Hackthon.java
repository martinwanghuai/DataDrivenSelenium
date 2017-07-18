import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import utility.Constant;
import utility.WebDriverUtils;

import com.beust.jcommander.internal.Maps;


public class Hackthon {
	
	public static final String HOME_URL = "http://www2.repuve.gob.mx:8080/ciudadania/servletconsulta";
	public static final By ID_BOX = By.xpath("//input[@name='placa']");
	public static final By VALIDATION_TEXT = By.xpath("//input[@name='captcha']");
	public static final By NEXT_BUTTON = By.xpath("(//a[contains(@class,'more-link')])[1]");
	public static final By RESULT_ROW = By.xpath("//div[@class='bg1']/descendant::tr");
	public static final By IMG = By.xpath("//img[@alt='Captcha']");
	public static final long WAIT_TIME = 6000;
	
	@Test
	public void hackGovPage(){
		
		WebDriverUtils driver = new WebDriverUtils();
		
		gotoResultPage(driver);
		
		Map<String, String> userInfo = this.extractInfo(driver);
		
//		System.out.println("User Info:" + userInfo);
		
		driver.shutDown();
	}

	private Map<String, String> extractInfo(WebDriverUtils driver){
		
		Map<String, String> userInfo = Maps.newHashMap();
		for(WebElement elem: driver.findElements(RESULT_ROW)){
			List<WebElement> elems = elem.findElements(By.xpath(".//td"));
			if(elems.size() > 1){
				String key = elems.get(0).findElement(By.xpath(".//span")).getText();
				String value = elems.get(1).findElement(By.xpath(".|.//span")).getText();
				userInfo.put(key, value);
				System.out.println(key + value);
			}else{
				System.out.println("Incomplete data:");
			}
		}
		
		return userInfo;
	}
	
	private void gotoResultPage(WebDriverUtils driver) {
		driver.openURL(HOME_URL);
		
		driver.fillin_textbox(ID_BOX, "MTT8499");
		
		driver.explicitWait(WAIT_TIME);
		
//		driver.fillin_textbox(VALIDATION_TEXT, "MTT8499");
		this.takePicForWebElement(driver.getWebDriver_existing(), driver.findElement(IMG), "img.png");
		
		driver.clickButton(NEXT_BUTTON);
	}
	
	private void takePicForWebElement(WebDriver driver, WebElement elem, String fileName){
		
		try {
			File screenshot = ((TakesScreenshot) driver)
					.getScreenshotAs(OutputType.FILE);
			
			BufferedImage  fullImg = ImageIO.read(screenshot);

			// Get the location of element on the page
			Point point = elem.getLocation();

			// Get width and height of the element
			int eleWidth = elem.getSize().getWidth();
			int eleHeight = elem.getSize().getHeight();

			// Crop the entire page screenshot to get only element screenshot
			BufferedImage eleScreenshot= fullImg.getSubimage(point.getX(), point.getY(),
			    eleWidth, eleHeight);
			ImageIO.write(eleScreenshot, "png", screenshot);

			// Copy the element screenshot to disk
			File screenshotLocation = new File(Constant.USRDIR
					+ Constant.PATH_SCREENSHOT + fileName);
			FileUtils.copyFile(screenshot, screenshotLocation);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
}
