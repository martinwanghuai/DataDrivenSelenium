package toy;


import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.testng.annotations.*;
import static org.testng.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
public class HKPLTest1 {
	 private WebDriver driver;
	  private String baseUrl;
	  private boolean acceptNextAlert = true;
	  private StringBuffer verificationErrors = new StringBuffer();

	  @BeforeClass(alwaysRun = true)
	  public void setUp() throws Exception {
	    driver = new FirefoxDriver();
	    baseUrl = "https://www.singsaver.com.sg/";
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  }

	  @Test
	  public void testHKPLTest1() throws Exception {
	    driver.get("https://www.moneyhero.com.hk/en");
	    driver.findElement(By.xpath("//a[contains(text(),'Personal Loans')]")).click();
	    driver.findElement(By.xpath("//button[@id='dropdownMenu1']")).click();
	    driver.findElement(By.xpath("//a[contains(text(),'Debt Consolidation')]")).click();
	    driver.findElement(By.xpath("//button[@id='dropdownMenu1']")).click();
	    driver.findElement(By.xpath("//a[contains(text(),'Lending Companies')]")).click();
	    driver.findElement(By.xpath("//button[@id='dropdownMenu1']")).click();
	    driver.findElement(By.xpath("//a[contains(text(),'Credit Line')]")).click();
	    driver.findElement(By.xpath("(//input[@type='text'])[2]")).clear();
	    driver.findElement(By.xpath("(//input[@type='text'])[2]")).sendKeys("12");
	    driver.findElement(By.xpath("//div[4]/div[2]/div/span")).click();
	    driver.findElement(By.xpath("//div[4]/div[2]/div/span")).click();
	    driver.findElement(By.xpath("//a[contains(text(),'Apply Now')]")).click();
	    // ERROR: Caught exception [ERROR: Unsupported command [waitForPopUp | _blank | 30000]]
	    // ERROR: Caught exception [ERROR: Unsupported command [waitForPopUp | _blank | 30000]]
	    driver.findElement(By.xpath("//div[@id='41485']/div/div/div[2]/div/p")).click();
	    driver.findElement(By.xpath("//div[@id='41485']/div/div/div[2]/div/p/span")).click();
	    driver.findElement(By.xpath("//div[@id='41509']/div/div/div[2]/div/p/span[2]")).click();
	    driver.findElement(By.xpath("//div[@id='41509']/div/div/div[2]/div/p/span")).click();
	    driver.findElement(By.xpath("//div[@id='41501']/div/div/div[2]/div/p/span[2]")).click();
	    driver.findElement(By.xpath("//div[@id='41501']/div/div/div[2]/div/p/span")).click();
	    driver.findElement(By.xpath("//div[@id='41505']/div/div/div[2]/div/p/span[2]")).click();
	    driver.findElement(By.xpath("//div[@id='41505']/div/div/div[2]/div/p/span")).click();
	    driver.findElement(By.xpath("//div[4]/div/div[2]/div/span")).click();
	  }

	  @AfterClass(alwaysRun = true)
	  public void tearDown() throws Exception {
	    driver.quit();
	    String verificationErrorString = verificationErrors.toString();
	    if (!"".equals(verificationErrorString)) {
	      fail(verificationErrorString);
	    }
	  }

	  private boolean isElementPresent(By by) {
	    try {
	      driver.findElement(by);
	      return true;
	    } catch (NoSuchElementException e) {
	      return false;
	    }
	  }

	  private boolean isAlertPresent() {
	    try {
	      driver.switchTo().alert();
	      return true;
	    } catch (NoAlertPresentException e) {
	      return false;
	    }
	  }

	  private String closeAlertAndGetItsText() {
	    try {
	      Alert alert = driver.switchTo().alert();
	      String alertText = alert.getText();
	      if (acceptNextAlert) {
	        alert.accept();
	      } else {
	        alert.dismiss();
	      }
	      return alertText;
	    } finally {
	      acceptNextAlert = true;
	    }
	  }
}
