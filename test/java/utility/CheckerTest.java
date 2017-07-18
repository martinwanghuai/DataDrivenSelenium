package utility;

import java.util.Date;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.collections.Lists;

public class CheckerTest {

	WebDriverUtils driver;
	Checker checker;
	
	@BeforeTest
	public void setup(){
	
		driver = new WebDriverUtils();
		checker = new Checker(driver);
	}
	
	@Test
	public void shouldIgnoreJSKeywords(){
		
		List<String> matchedKeys = Lists.newArrayList("vendor.js", "bat.js");
		Assert.assertEquals(checker.cleanupMatchedKeys(matchedKeys).size(), 0);
	}
	
	@Test
	public void shouldIgnoreHttpKeywords(){
		
		List<String> matchedKeys = Lists.newArrayList("href=\"http://www.moneyhero.com.hk/en/blog", "href=\"https://www.moneyhero.com.hk/en/blog");
		Assert.assertEquals(checker.cleanupMatchedKeys(matchedKeys).size(), 0);
	}
	
	@Test
	public void shouldIgnoreStringsInExclusionList(){
		
		List<String> matchedKeys = Lists.newArrayList("moneyhero.com.hk",
				"hutchgo.com", "moneyhero.com.hk's", "topcompare.be",
				"compareeuropegroup.com", "voorwaarden.het", "m.a.x","www.moneyhero.com.hk", "www.hsbc.com.hk","citigroup.com",
				"moneyhero.com.hk\"",
				"hutchgo.com's");
		Assert.assertEquals(checker.cleanupMatchedKeys(matchedKeys).size(), 0);
	}
	
	@Test
	public void shouldKeepValidMissingKeys(){
		
		List<String> matchedKeys = Lists.newArrayList("ab.cd",
				"hutchgo.com", "moneyhero.com.hk's", "topcompare.be",
				"compareeuropegroup.com", "voorwaarden.het", "m.a.x","www.moneyhero.com.hk", "www.hsbc.com.hk","citigroup.com");
		Assert.assertEquals(checker.cleanupMatchedKeys(matchedKeys).size(), 1);
		Assert.assertEquals(checker.cleanupMatchedKeys(matchedKeys).get(0), "ab.cd");
	}
	
	@Test
	public void shouldSkipImages(){
		
		List<String> matchedKeys = Lists.newArrayList("a.png",
				"a.icon", "a.ico");
		Assert.assertEquals(checker.cleanupMatchedKeys(matchedKeys).size(), 0);
	}
	
	@Test
	public void shouldSkipCSSs(){
		
		List<String> matchedKeys = Lists.newArrayList("a.css");
		Assert.assertEquals(checker.cleanupMatchedKeys(matchedKeys).size(), 0);
	}
	
	@Test
	public void shouldSkipNumbers(){
		
		List<String> matchedKeys = Lists.newArrayList("336.03", "4.500!*", "40.000.", "2.000.000", "50.000)", "35.000*");
		List<String> cleanupMatchedKeys = checker.cleanupMatchedKeys(matchedKeys);
		Assert.assertEquals(cleanupMatchedKeys.size(), 0);
	}
	
	@Test
	public void shouldSkipSpecialCases(){
		
		List<String> matchedKeys = Lists.newArrayList("partners.>", "everyday.\">", "policy..", "og.site_name");
		List<String> cleanupMatchedKeys = checker.cleanupMatchedKeys(matchedKeys);
		Assert.assertEquals(cleanupMatchedKeys.size(), 1);
		Assert.assertEquals(cleanupMatchedKeys.get(0), "policy..");
	}
	
	@Test
	public void shouldValidateLink(){
		
		final String startUrl = "http://hongkong.";
		List<String> invalidLinks = Lists.newArrayList("", "http://hk.funnel");
		for(final String invalidateLink: invalidLinks){
			Assert.assertEquals(false, checker.isValidLinkWithinSameDomain(invalidateLink, startUrl));
		}
		
		List<String> validLinks = Lists.newArrayList("http://hongkong.funnel", "https://hongkong.funnel", "http://hongkong.blog", "http://hongkong.css");
		for(final String validateLink: validLinks){
			Assert.assertEquals(true, checker.isValidLinkWithinSameDomain(validateLink, startUrl));
		}
	}
	
	@Test
	public void shouldCoverAllKeysByRegularExpression(){
		
		try{
			ExcelUtils util = new ExcelUtils(Constant.USRDIR + Constant.PATH_TESTDATA + "FindKey.xlsx", "Plain text of all the keys");
			List<String> keys = util.getColumnData("Plain text of all the keys");
			List<String> missedKeys = Lists.newArrayList();
			
			for(String key: keys){
				if(!Checker.isBlank(key)){
					
					if(key.contains("=")){
						key = key.substring(0, key.indexOf("="));
					}
					
					key = key.trim();					
					
					List<String> missingKey = driver.matchStringPatternsInPageSource(key, Checker.MISSING_KEY_PATTERN);
					if(missingKey==null || missingKey.size() == 0){
						missedKeys.add(key);
					}
				}
			}
			
			System.out.println("Missed the following keys:");
			for(String missedKey: missedKeys){
				System.out.println(missedKey);
			}
			
			Assert.assertEquals(missedKeys.size(), 0);
	
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	//@Test
	public void shouldFindJSErrorInPage(){
		
		driver = new WebDriverUtils(WebDriverUtils.Type.Chrome, true);
		driver.openURL("http://singapore.preview.compareglobal.co.uk/");
		driver.explicitWait(3*1000);
		
		LogEntries logEntries = driver.getWebDriver_existing().manage().logs().get(LogType.BROWSER);
		for (LogEntry entry : logEntries) {
			System.out.println(new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage());
		}
		
	}
}
