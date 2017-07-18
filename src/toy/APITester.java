package toy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.cgg.model.PLAPIJsonObjectDeserializer;
import com.cgg.model.PLAPIJsonObjects;
import com.cgg.model.PLJsonObject;
import com.cgg.model.PLJsonObjectDeserializer;
import com.cgg.model.PLJsonObjects;
import com.cgg.model.PLJsonObject.Interest;
import com.cgg.pl.category.Category;
import com.cgg.pl.category.PersonalLoan;
import com.google.common.collect.Sets;

import utility.IOUtils;
import utility.WebDriverUtils;

public class APITester {

	WebDriverUtils driver;
	
	@BeforeClass
	public void setup(){
		driver = new WebDriverUtils();
		
	}
	
	@Test(invocationCount = 100)
	public void testPLMessages(){
		
		driver.openURL("https://beta.comparaja.pt/credito-pessoal/prestacao-mensal/resultados");
		WebElement pl = driver.findElement(By.id("UNBC0001"));
		WebElement salary = pl.findElement(By.xpath("./descendant::a[@class='cgg-secondary cgg-product-description clickable ng-binding ng-scope']"));
		Assert.assertEquals(salary.getText(), "Unibanco - Crédito Pessoal Testing");
	}
	
	@Test(invocationCount = 100)
	public void testCCChanges(){
		
		driver.openURL("http://hongkong.qa.compareglobal.co.uk/en/credit-card/best-deals");
		WebElement cc = driver.findElement(By.id("DBSX0001"));
		WebElement salary = cc.findElement(By.xpath("./descendant::p[@class='cgg-primary cgg-primary-tablet-portrait ng-binding ng-scope']"));
		Assert.assertEquals(salary.getText(), "1,000,000");
	}
	
	@Test
	public void testHKAPI() throws Exception{
		
		final String url = "http://www.moneyhero.com.hk/api/personal-loan/v2/loans/lendingCompanies?sortBy=mrp%2B&sortBy=trp%2B&sortBy=mrt%2B&wantToBorrow=100000&wantToBorrowTime=24&wantToBorrowTimeUnit=month&lang=en&pageSize=2000";
		String jsonStr = driver.callRESTAPI(url);
		PLAPIJsonObjects result = (PLAPIJsonObjects)IOUtils.loadJsonObjects(jsonStr, PLAPIJsonObjects.class, new PLAPIJsonObjectDeserializer(Category.LendingCompanies_HK, "HK"));
		System.out.println(jsonStr);
	}
	
	@Test
	public void testMXAPI() throws Exception {

		final String url = "http://mexico-staging.compareglobal.co.uk/api/personal-loan/v2/loans/quickLoan?sortBy=trp%2B&sortBy=rpt%2B&sortBy=tip%2B&wantToBorrow=1999&wantToBorrowTimeUnit=day&lang=es-MX&pageSize=15";
		String jsonStr = driver.callRESTAPI(url);
		PLAPIJsonObjects result = (PLAPIJsonObjects) IOUtils.loadJsonObjects(jsonStr, PLAPIJsonObjects.class,
				new PLAPIJsonObjectDeserializer(Category.QuickLoan_MX, "MX"));
		System.out.println(jsonStr);
	}
	
	@Test(invocationCount = 100)
	public void testSGCreditLineAPI() throws Exception {
		
		String API = "https://www.singsaver.com.sg/api/personal-loan/v2/loans/creditLine?sortBy=lar%2B&sortBy=mmr%2B&sortBy=afe%2B&wantToBorrow=15001&wantToBorrowTime=1&wantToBorrowTimeUnit=year&lang=en&pageSize=2000";
		String jsonStr = WebDriverUtils.callRESTAPI(API);
		PLAPIJsonObjects results = (PLAPIJsonObjects)IOUtils.loadJsonObjects(jsonStr, PLAPIJsonObjects.class, new PLAPIJsonObjectDeserializer(Category.CreditLine_SG, "SG"));
		List<PersonalLoan> actualResults = (List<PersonalLoan>)Stream.concat(results.getFeaturedProductsForAPI().stream(), results.getProductsForAPI().stream()).collect(Collectors.toList());
		Assert.assertEquals(actualResults.size(), 7);
	}
	
	@Test(invocationCount = 100)
	public void testFIRevolvingLoanAPI() throws Exception {
		
		String API = "http://finland-staging.compareglobal.co.uk/api/personal-loan/v2/loans/revolvingLoan?sortBy=mrp%2B&sortBy=apr%2B&sortBy=mrt%2B&wantToBorrow=2699&wantToBorrowTime=30&wantToBorrowTimeUnit=month&lang=fi&pageSize=2000";
		String jsonStr = WebDriverUtils.callRESTAPI(API);
		PLAPIJsonObjects results = (PLAPIJsonObjects)IOUtils.loadJsonObjects(jsonStr, PLAPIJsonObjects.class, new PLAPIJsonObjectDeserializer(Category.CreditLine_SG, "SG"));
		List<PersonalLoan>  actualResults = (List<PersonalLoan>)Stream.concat(results.getFeaturedProductsForAPI().stream(), results.getProductsForAPI().stream()).collect(Collectors.toList());
		int indexFor44632 = -1, indexFor44527 = -1;
		for(int i = 0; i < actualResults.size(); i ++ ){
			PersonalLoan pl = actualResults.get(i);
			if(pl.getID().equalsIgnoreCase("44632")){
				indexFor44632 = i;
			}
			if(pl.getID().equalsIgnoreCase("44527")){
				indexFor44527 = i;
			}
			
			if(indexFor44632 >= 0 && indexFor44527 >= 0){
				break;
			}
		}
		
		Assert.assertTrue(indexFor44527 < indexFor44632);
	}

	@Test
	public void testAfrescoOverview() throws Exception {
		
		String url = "http://asia-cms.compareglobal.co.uk/alfresco/service/api/login?u=martin.wang&pw=12345qwert";
		String xmlStr = driver.callRESTAPI(url);
		
		String ticket = xmlStr.substring(xmlStr.indexOf("<ticket>") + "<ticket>".length(), xmlStr.indexOf("</ticket>"));
	    System.out.println(ticket);
	    
	    url = "http://asia-cms.compareglobal.co.uk/alfresco/s/cmis?alf_ticket=" + ticket;
	    String jsonStr = driver.callRESTAPI(url);
	    System.out.println(jsonStr);
	}
	
	@Test
	public void testAfrescoTypes() throws Exception {
		
		String url = "http://asia-cms.compareglobal.co.uk/alfresco/service/api/login?u=martin.wang&pw=12345qwert";
		String xmlStr = driver.callRESTAPI(url);
		
		String ticket = xmlStr.substring(xmlStr.indexOf("<ticket>") + "<ticket>".length(), xmlStr.indexOf("</ticket>"));
	    System.out.println(ticket);
	    
	    url = "http://asia-cms.compareglobal.co.uk/alfresco/s/cmis/types?alf_ticket=" + ticket;//children: immediate children; descendants: all childrens
	    String jsonStr = driver.callRESTAPI(url);
	    System.out.println(jsonStr);
	}
	
	@Test
	public void testAfrescoAPI() throws Exception{
		
		String url = "http://asia-cms.compareglobal.co.uk/alfresco/service/api/login?u=martin.wang&pw=12345qwert";
		String xmlStr = driver.callRESTAPI(url);
		
		String ticket = xmlStr.substring(xmlStr.indexOf("<ticket>") + "<ticket>".length(), xmlStr.indexOf("</ticket>"));
	    System.out.println(ticket);
	    
	    url = "http://asia-cms.compareglobal.co.uk/alfresco/api/-default-/public/cmis/versions/1.1/browser/root/sites/mexico-staging/documentLibrary/dataFiles/MX_PL_DATA.json?alf_ticket=" + ticket;
	    String jsonStr = driver.callRESTAPI(url);
	    PLJsonObjects results = (PLJsonObjects)IOUtils.loadJsonObjects(jsonStr, PLJsonObjects.class, new PLJsonObjectDeserializer());
	    
	    Set<Double> boundaryValues_loanAmount = Sets.newHashSet();
	    Set<Double> boundaryValues_loanTenure = Sets.newHashSet();
	    
	    List<PLJsonObject> jsonObjects = results.getJsonObjects();
	    
	    for(PLJsonObject obj: jsonObjects){
	    	List<Interest> interests = obj.getInterests();
    		for(Interest interest: interests){
    			boundaryValues_loanAmount.add(interest.getLoanAmountMin());
    			boundaryValues_loanAmount.add(interest.getLoanAmountMax());
    			boundaryValues_loanTenure.add(interest.getLoanTenureMin());
    			boundaryValues_loanTenure.add(interest.getLoanTenureMax());
    		}
	    }
	    
	    List<Double> sortedLoanTenures = new ArrayList<Double>(boundaryValues_loanTenure);
	    Collections.sort(sortedLoanTenures);
	    List<Double> sortedLoanAmounts = new ArrayList<Double>(boundaryValues_loanAmount);
	    Collections.sort(sortedLoanAmounts);
	    
	    System.out.println("Loan Tenure:");
	    for(Double tenure: sortedLoanTenures){
	    	System.out.println(tenure);
	    }
	    
	    System.out.println("Loan Amount:");
	    for(Double amount: sortedLoanAmounts){
	    	System.out.println(amount);
	    }
	}
}
