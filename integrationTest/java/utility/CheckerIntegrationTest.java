package utility;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

import java.util.List;

import org.mockito.Mock;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import utility.Checker;
import utility.WebDriverUtils;


public class CheckerIntegrationTest {

	WebDriverUtils driver;
	Checker checker;
	
	@Mock WebDriver instance;
	
	@BeforeTest
	public void setup(){
	
		driver = new WebDriverUtils(instance);
		checker = new Checker(driver);
	}

	@Test
	public void shouldFindMissingTranslationsInPage(){
		
		String[] input = new String[] {
				"personal-loan.landing.product-know-how.item-1.title.debtconsolidation",
				"members.the",
				"policy..",
				"(\"moneyhero\").in",
				"info@moneyhero.com.hk",
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
				"og.site_name", 
				"pl.funnel.config.stepInfo[1].templateOption.template[0].data.mobileHint",
				"widget.tab-pane.item.retailPurchase",
				"og.provider.provider-details.台灣樂天.about.title", 
				"widget.result-summary.text.pl.widget.menu.existingCustomerLoan"};
		
		String[] expected = new String[] {
				"personal-loan.landing.product-know-how.item-1.title.debtconsolidation",
				"members.the",
				"policy..",
				"(\"moneyhero\").in",
				"<title>komplettbank.page.title</title>",
				"content=\"og.provider.provider-details.title\">",
				"Sinsgsaver.com.sg</title>",
//				"og.site_name",
				"pl.funnel.config.stepInfo[1].templateOption.template[0].data.mobileHint",
				"widget.tab-pane.item.retailPurchase",
				"og.provider.provider-details.台灣樂天.about.title",
				"widget.result-summary.text.pl.widget.menu.existingCustomerLoan"};

		final StringBuilder sb = new StringBuilder();
		for(final String str: input){
			sb.append(str).append(" ");
		}
		
		final String pageSrc = sb.toString();
		List<String> matchedKeys = driver.matchStringPatternsInPageSource(pageSrc, Checker.MISSING_KEY_PATTERN);
		matchedKeys = checker.cleanupMatchedKeys(matchedKeys);
		assertThat(matchedKeys, containsInAnyOrder(expected));
		
		
	}
}
