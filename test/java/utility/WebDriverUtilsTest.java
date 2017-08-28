package utility;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;



public class WebDriverUtilsTest {
	
	private final static String PATTERN = Checker.MISSING_KEY_PATTERN;
	
	private WebDriverUtils instance;
	
	@BeforeTest
	public void setup(){
		
		instance = new WebDriverUtils(); 
	}
	
	@Test
	public void shouldReportURLError(){
		
		List<String> urls = Lists.newArrayList(
				"http://beta-staging.moneyhero.com.hk/themes/cgg-confidence-mix/assets/img/favicon.ico");
		
		for(final String url: urls){
			Assert.assertEquals(instance.checkUrl(url), 404);	
		}
	}
	
	@Test
	public void shouldFoundUntranslatedChineseKeys(){
		
		String input = "og.provider.provider-details.台灣樂天.about.title";
//		String input = "og.provider.provider-details.about.title";
		final String pageSrc = input;
		List<String> matchedKeys = instance.matchStringPatternsInPageSource(pageSrc, PATTERN);
		Assert.assertEquals(matchedKeys.size(), 1);
		Assert.assertEquals(matchedKeys.get(0), input);
	}
	
	@Test(enabled=false)
	public void shouldMatchWithMissingKeyPattern(){
		
		String[] input = new String[] {
				"personal-loan.landing.product-know-how.item-1.title.debtconsolidation",
				"members.the",
				"policy..",
				"(\"moneyhero\").in",
				"info@moneyhero.com.hk",
				"�8.162",
				"eur8,000.00",
				"hk$0.5",
				"$2.78", 
				"everyday.\">",
				"0470/30.43.26",
				"3.57%",
				"partners.>",
				"<title>komplettbank.page.title</title>",
				"content=\"og.provider.provider-details.title\">",
				"Sinsgsaver.com.sg</title>",
				"og.site_name"};
		
		String[] expected = new String[] {
				"personal-loan.landing.product-know-how.item-1.title.debtconsolidation",
				"members.the",
				"policy..",
				"(\"moneyhero\").in",
				"everyday.\">",
				"partners.>",
				"<title>komplettbank.page.title</title>",
				"content=\"og.provider.provider-details.title\">",
				"Sinsgsaver.com.sg</title>",
				"og.site_name"};
		
		final StringBuilder sb = new StringBuilder();
		for(final String str: input){
			sb.append(str).append(" ");
		}
		
		final String pageSrc = sb.toString();
		List<String> matchedKeys = instance.matchStringPatternsInPageSource(pageSrc, PATTERN);
		
		assertThat(matchedKeys, containsInAnyOrder(expected));
	}
	
	@Test
	public void shouldFindErrorInPage(){
		
		String urlStr = "http://beta-staging.moneyhero.com.hk/en/credit-card/airmiles";
		Assert.assertTrue(instance.findErrorInPage(urlStr));
	}
	
	@Test
	public void shouldPrintChineseUrl() throws Exception {
		
		String urlStr = "http://beta-staging.money101.com.tw/聯絡我們";
		instance.openURL(urlStr);
		
		System.out.println(IOUtils.toUTF8String(instance.getCurrentUrl()));
	}
	
	@Test
	public void shouldSaveChineseUrl() throws Exception {
		
		String urlStr = "http://beta-staging.money101.com.tw/聯絡我們";
		instance.openURL(urlStr);
		
		final String saveFile = System.getProperty("user.dir") +"/src/Test/Test.txt";
		IOUtils.saveIntoFile(IOUtils.toUTF8String(instance.getCurrentUrl()), saveFile);
		
		final List<String> strs = IOUtils.loadExcelFile(saveFile);
		Assert.assertEquals(strs.size(), 1);
		String actual = strs.get(0);
		Assert.assertEquals(actual, urlStr);
	}
	
	@Test
	public void shouldGetCurrentUrl() throws Exception{
		
		HttpResponse response = instance
				.getHttpResponse("http://hongkong.preview.compareglobal.co.uk/personal-loan");
		
		Header[] headers = response.getHeaders("Location");
		if(headers.length > 0 ){
			for(Header header: headers){
				System.out.println("Location:" + header.getValue());
			}	
		}
	}
	
	@Test
	public void shouldGetResponseText(){
		
		String responseHtml = instance.getResponseHtml("http://beta-staging.money101.com.tw/聯絡我們");
		if(responseHtml.contains("assets.ciab.compareglobal.co.uk")){
			System.out.println("CIAB");
		}else{
			System.out.println("LIBRIS");
		}
		
	}
}
