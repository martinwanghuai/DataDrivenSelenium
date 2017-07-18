package testCases;

import java.util.List;

import org.apache.log4j.xml.DOMConfigurator;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.cgg.model.TestCase;
import com.cgg.model.TestingStep;
import com.google.common.base.Predicates;
import com.google.common.collect.FluentIterable;

import appModules.CommonFindMethods;
import pageObjects.BaseClass;
import utility.Checker;
import utility.Constant;
import utility.Log;
import utility.Utils;
import utility.WebDriverUtils;

public class TestDriver {

	public WebDriverUtils driver;
	private static String sTestCaseName;
	private static String userCountry = "";
	private static String userComponent = "";
	private static String userURL = "";
	
	@Parameters({"country", "vertical", "url"})
	@BeforeTest
	public void beforeTestPage(@Optional String country, @Optional String vertical, @Optional String url) throws Exception {

		DOMConfigurator.configure("log4j.xml");

		if(Constant.ENABLE_USER_INPUT){
			userCountry = Checker.isBlank(country) ? "ID": country; 
			userComponent = Checker.isBlank(vertical) ? "PLFunnel" : vertical;//PLAPI, PLFunnel, PLResultPage, CIFunnel, BBFunnel, PLAppForm, MortgageAPI, Newsletter
			
			setupEnvironment();
			
			userURL = Checker.isBlank(url)
					//For DK
//					? "https://staging.samlino.dk/"
//					? "https://staging.samlino.dk/laan/funnel#/"
//					? "https://staging.samlino.dk/bredbaand"
//					? "https://staging.samlino.dk/mobilabonnement"
//					? "https://staging.samlino.dk/bredbaand/ordreflow?&address=Solsortvej%20123%201.%202000%20Frederiksberg&postcode=2000&city=Frederiksberg&kvh=01470730_123&kvhx=01470730_123__1____&vwoversion=broadband&addressId=0a3f50a2-cb4b-32b8-e044-0003ba298018&cggId=TELM0003&product=Telmore#/1"
							
					//For SG
//					? "https://staging.singsaver.com.sg/"
//					? "https://staging.singsaver.com.sg/credit-card/funnel#/step/1"
//					? "https://staging.singsaver.com.sg/personal-loan/funnel#/step/1"
//					? "https://staging.singsaver.com.sg/personal-loan/apply-now?loanAmount=10000&loanTime=3&loanTimeUnit=year&queryParams=undefined&generic=null&cggId=39848&product=UOB%20Personal%20Loan%20-%20Existing%20CashPlus%20Customers&apiRefForCheckout=http%3A%2F%2Fsingapore-customercloud-qa.ap-northeast-1.elasticbeanstalk.com%2Fapi%2Fpersonal-loan%2Fv2%2Floans%2FpersonalInstalment%3FsortBy%3Dmrp%252B%26sortBy%3Dtrp%252B%26sortBy%3Dapr%252B%26wantToBorrow%3D10000%26wantToBorrowTime%3D3%26wantToBorrowTimeUnit%3Dyear%26lang%3Den%26pageSize%3D15"
//					? "https://staging.singsaver.com.sg/credit-card/application-form?cggId=OCBC0008&product=OCBC%20Voyage%20Card&apiRefForCheckout=%2Fapi%2Fcredit-card%2Fv2%2Fcards%2FbestDeals%3FcomingFromFunnel%3D1%26age%3D30%26monthlyIncome%3D5000%26residencyStatus%3Dsingaporean%26bankRelationship%3DUOBX%26sortBy%3Dral-%26sortBy%3Drao-%26sortBy%3Dracf%252B%26lang%3Den%26pageSize%3D10"
					
					//For PT
//					? "http://portugal-staging.compareglobal.co.uk/tv-net-voz"
//					? "http://portugal-staging.compareglobal.co.uk/credito-pessoal/opcoes#/"
//					? "http://portugal-staging.compareglobal.co.uk/cartao-de-credito/opcoes#/"
//					? "http://portugal-staging.compareglobal.co.uk/credito-habitacao/opcoes#/"
//					? "http://portugal-staging.compareglobal.co.uk/tv-net-voz/adesao?&cggId=NOWO1024&product=NOWO&address=Rua%20da%20Espinheira%20ROMARIZ&postcode=3700-891&doorNum=15&cannotFindAddress=false&premiumPackages=pfc&addSim=false#/1"
//					? "http://portugal-staging.compareglobal.co.uk/credito-pessoal/aplica-ya?loanAmount=5000&loanTime=36&loanTimeUnit=month&queryParams=undefined&generic=true&cggId=BMTP0003&product=Montepio%20-%20Cr%C3%A9dito%20Energias%20Renov%C3%A1veis&apiRefForCheckout=%2Fapi%2Fpersonal-loan%2Fv2%2Floans%2Frenewables%3FsortBy%3Dmrp%252B%26sortBy%3Dtrp%252B%26sortBy%3Dapr%252B%26wantToBorrow%3D5000%26wantToBorrowTime%3D36%26wantToBorrowTimeUnit%3Dmonth%26lang%3Dpt%26pageSize%3D15"
					
					//For MX
//					? "http://mexico-staging.compareglobal.co.uk/prestamos-personales/opciones#/"
//					? "http://mexico-staging.compareglobal.co.uk/seguro-de-autos/informacion-auto#/step/1"
//					? "http://mexico-staging.compareglobal.co.uk/tarjetas-de-credito/aplicar?cggId=BANA0019&product=CitiBanamex%20Rewards&apiRefForCheckout=%2Fapi%2Fcredit-card%2Fv2%2Fcards%2FbestDeals%3FcomingFromFunnel%3D1%26age%3D33%26monthlyIncome%3D555555%26ees_eval%3Demployee%26ownCreditCard%3Dyes%26activeCreditCards%3D2%26activeCardBanks%3DAMEX%26activeMortgage%3Dyes%26haveCarLoan%3Dyes%26havePersonalLoan%3Dyes%26havePayLate%3Dno%26haveStopCredit%3Dno%26monthlySpend%3DlessThan20%26bankRelationship%3DAMEX%26bankRelationship%3DBANA%26%26lang%3Des%26pageSize%3D15#/1"
				    		
					//For FI
//					? "http://finland-staging.compareglobal.co.uk/laina/funnel"
//					? "http://finland-staging.compareglobal.co.uk/luottokortti/funnel"

					//For ID
					? "http://indonesia-staging.compareglobal.co.uk/id/kta/funnel#/"
//					? "http://indonesia-staging.compareglobal.co.uk/id/kartu-kredit/funnel"
					
					//For HK
//					? "http://hongkong.preview.compareglobal.co.uk/en/personal-loan/collector"
//					? "http://hongkong.preview.compareglobal.co.uk/en/credit-card/funnel#?category=bestDeals"
//					? "http://hongkong.preview.compareglobal.co.uk/en/mortgage/funnel#/"
//					? "http://hongkong.preview.compareglobal.co.uk/zh/personal-loan/apply-now?loanAmount=100000&loanTime=24&loanTimeUnit=month&queryParams=undefined&generic=null&cggId=41404&product=Cashing%E3%80%8C%E6%98%93%E3%80%8D%E7%A7%81%E4%BA%BA%E8%B2%B8%E6%AC%BE&apiRefForCheckout=%2Fapi%2Fpersonal-loan%2Fv2%2Floans%2FlendingCompanies%3FsortBy%3Dapr%252B%26sortBy%3Dmrp%252B%26sortBy%3Dtrp%252B%26wantToBorrow%3D100000%26wantToBorrowTime%3D24%26wantToBorrowTimeUnit%3Dmonth%26lang%3Dzh%26pageSize%3D2000"
//					? "http://hongkong.preview.compareglobal.co.uk/en/travel-insurance/result?comingFromFunnel=1&category=single&relationship=myself&myselfBirthdate=%7B%22type%22:%22myself%22,%22date%22:%221990-02-01%22%7D&destination=Afghanistan&startDate=2017-04-13&endDate=2017-04-13&extras=waterSports"
							
					//For TW

					//For NO
//					? "http://norway.staging.compareglobal.co.uk/forbrukslan/funnel"
//					? "http://norway.staging.compareglobal.co.uk/kredittkort/funnel"
							
					//For MY
//					? "http://malaysia-staging.compareglobal.co.uk/personal-loan/funnel#/?category=all"
//					? "http://malaysia-staging.compareglobal.co.uk/credit-card/funnel"
//					? "http://malaysia-staging.compareglobal.co.uk/broadband"
							
					//For BE
							
					//For TH
//					? "http://thailand-preview.compareglobal.co.uk/th/%E0%B8%AA%E0%B8%B4%E0%B8%99%E0%B9%80%E0%B8%8A%E0%B8%B7%E0%B9%88%E0%B8%AD%E0%B8%AA%E0%B9%88%E0%B8%A7%E0%B8%99%E0%B8%9A%E0%B8%B8%E0%B8%84%E0%B8%84%E0%B8%A5/%E0%B8%84%E0%B8%B3%E0%B8%96%E0%B8%B2%E0%B8%A1%E0%B8%A5%E0%B8%B9%E0%B8%81%E0%B8%84%E0%B9%89%E0%B8%B2#/"
//					? "http://thailand-preview.compareglobal.co.uk/th/%E0%B8%9A%E0%B8%B1%E0%B8%95%E0%B8%A3%E0%B9%80%E0%B8%84%E0%B8%A3%E0%B8%94%E0%B8%B4%E0%B8%95/%E0%B8%84%E0%B8%B3%E0%B8%96%E0%B8%B2%E0%B8%A1%E0%B8%A5%E0%B8%B9%E0%B8%81%E0%B8%84%E0%B9%89%E0%B8%B2#/"
							
					//For PH
//					? "http://philippines-staging.compareglobal.co.uk/personal-loan/funnel#/"
//					? "http://philippines-preview.compareglobal.co.uk/credit-card/funnel#/?category=bestDeals"
//					? "http://philippines-preview.compareglobal.co.uk/broadband"
							
					: url;
			
			System.out.println("country:" + country + ";vertical:" + vertical + ";url:" + url);
		}
				
		if(!userComponent.equals("PLAPI") && !userComponent.equals("MortgageAPI")){
			driver = new WebDriverUtils();
		}
	}

	public void setupEnvironment() {

		if(!userComponent.toLowerCase().contains("appform") && !userComponent.toLowerCase().contains("funnel") && !userComponent.toLowerCase().contains("newsletter")){
			Constant.ENABLE_BROWSER_MOB = false;
			Constant.ENABLE_FUNNEL_TESTER = false;
		}
	}
	 
	@DataProvider(name = "DP")
	public static Object[][] loadTestCases() throws Exception {

		final String sheetName = "Login";
		List<TestCase> testCases = TestCase.bulkMapExcelRowToTestCase(sheetName);
		
		List<TestCase> countrySpecificTestCases = FluentIterable.from(testCases)
				.filter(Predicates.and(TestCase.IS_SPECIFIED_COUNTRY(userCountry), TestCase.IS_SPECIFIED_COMPONENT(userComponent))).toList();
		
		final Object[][] results = new Object[countrySpecificTestCases.size()][];
		for (int i = 0; i < countrySpecificTestCases.size(); i++) {
			TestCase testCase = countrySpecificTestCases.get(i);
			updateURLs(testCase);
			results[i] = (new Object[] { countrySpecificTestCases.get(i) });
		}
		return results;
	}

	public static void updateURLs(TestCase testCase) {
		if(!Checker.isBlank(userURL)){
			testCase.setUrl(userURL);
			for(TestingStep step: testCase.getTestingSteps()){
				step.setURL(userURL);
			}
		}
	}
	
	@Test(dataProvider = "DP")
	public void runTestCase(final TestCase testCase) throws Exception {
		
		Log.startTestCase(testCase.getTestCaseName());
		
		if (!testCase.getStrExecuted().equalsIgnoreCase("N")) {
			
			int executionTime = 0;
			boolean hasFailedTestingStep = false;
			if(driver != null){
				driver.setTestCase(testCase);
				if(testCase.getTestCaseName().toLowerCase().contains("appform") || testCase.getTestCaseName().toLowerCase().contains("funnel")){
					driver.setRequestFilter(driver.FUNNEL_APPFORM_FILTER(testCase));
				}else if(testCase.getTestCaseName().toLowerCase().contains("newsletter")){
					driver.setRequestFilter(driver.NEWSLETTER_FILTER(testCase));
				}
			}
			do{
				if(executionTime > 0 && driver != null){
					driver.closeAllWins();
					driver = new WebDriverUtils();
				}
				System.out.println("running:" + testCase);
				Utils.openTestCase(driver, testCase);
				hasFailedTestingStep = runTestingSteps(testCase, driver != null ? driver.getBaseWindow():"", 0, false);
				executionTime ++;
			}while(executionTime < Constant.MAX_REPEAT_TIME && hasFailedTestingStep);
			
			if(executionTime == Constant.MAX_REPEAT_TIME && hasFailedTestingStep){
				Assert.fail("Test case failed:" + testCase);
			}
			Utils.closeTestCase(driver, testCase);
		}
	}
	
	private boolean runTestingSteps(final TestCase testCase,
			final String beforePopUp, int i, boolean hasFailedTestingStep) {
		
		try{
			for(; i < testCase.getTestingSteps().size(); i ++){
				TestingStep testingStep = testCase.getTestingStep(i);
				testingStep.setBeforePopUp(beforePopUp);
				this.runTestingStep(testingStep);
			}	
			return hasFailedTestingStep ? true: false;
		}catch(Exception e){
			e.printStackTrace();
			return true;
		}
	}

	private void runTestingStep(final TestingStep testingStep) throws Exception {

		try {
			System.out.println("running:" + testingStep);
			if (!testingStep.getStrExecuted().toUpperCase().equals("N")) {
				CommonFindMethods.executeMethod(testingStep);
			}
			if (!BaseClass.bResult == true) {
				throw new Exception("Testing step failed:" + testingStep);
			}
		} catch (Exception e) {
			if(driver != null){
				Utils.takeScreenshot(driver.getWebDriver_existing(), sTestCaseName);	
			}
			Log.error(e.getMessage());
			throw (e);
		}
	}

	@AfterTest
	public void afterTestPage() {

		Log.endTestCase(sTestCaseName);
		if(driver != null){
			driver.closeAllWins();	
		}
	}
}
