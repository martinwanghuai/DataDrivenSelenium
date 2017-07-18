package toy;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import pages.HomePage;
import utility.Checker;
import utility.MathUtils;
import utility.WebDriverUtils;

public class UserJourneysForCIAB implements Runnable{

	final WebDriverUtils driver;
	
	public UserJourneysForCIAB(final WebDriverUtils driver){
		
		this.driver = driver;
	}
	
	
	public void userJourney6(){
		
		driver.openURL("http://hongkong.qa.compareglobal.co.uk/en/credit-card/best-deals");
		WebElement cc = driver.findElement(By.id("DBSX0001"));
		WebElement salary = cc.findElement(By.xpath("./descendant::p[@class='cgg-primary cgg-primary-tablet-portrait ng-binding ng-scope']"));
		System.out.println(salary.getText());
	}
	
	public void userJourney1() throws Exception{
		
		driver.openURL("http://singapore.staging.compareglobal.co.uk/personal-loan/personal-instalment");
		
		for(int i = 0; i < 10; i ++){
			int loanAmount = MathUtils.getRandomValue(10000, 20000);
			int loanTenure = MathUtils.getRandomValue(1, 48);
			
			//loan amount
			By by = By.xpath("//input[@ng-model='options.position']");
			driver.fillin_textbox(by, loanAmount + "");
			driver.explicitWait(1000);
			
			//loan tenure
			by = By.xpath("//input[@ng-model='options.inputfield.input']");
			driver.fillin_textbox(by, loanTenure + "");
			driver.explicitWait(2000);
			
			/*Thread.sleep(3*1000);
			List<ResultObject> objs = ResultObject.getResultObjectsFromUI(driver, By.id("listData.cggId"));
			for(ResultObject obj: objs){
				System.out.println(obj);
			}*/
		}
		
	}
	
	public void userJourney2(){
		
		driver.openURL("https://www.samlino.no/forbrukslan/funnel#/");
		By by = By.xpath("//div[@class='cgg-table-cell']");
		List<WebElement> elems = driver.findElements(by);
		driver.clickButton(elems.get(0));
		
		by = By.xpath("//input[@name='inputName']");
		elems = driver.findElements(by);
		
		driver.fillin_textbox(elems.get(0), "4000");;
		
		driver.fillin_textbox(elems.get(2), "4");;
		
		by = By.className("continue-button");
	}
	
	public void userJourney3(ConcurrentLinkedDeque<Double> data){
		
		final WebDriverUtils driver = new WebDriverUtils();
		driver.openURL("https://www.topcompare.be/nl/persoonlijke-lening/persoonlijke-lening/results");
		By by = By.xpath("//a[@class='cgg-button-action cgg-hidden-xs ng-binding']");
		driver.implicitWait(by);
		List<WebElement> elems = driver.findElements(by);
		
		long start = System.currentTimeMillis();
		gotoProvider(driver, elems.get(2));
		long end = System.currentTimeMillis();
		long duration = (end-start)/1000;
		data.add((double)duration);
		
		System.out.println("TopCompare:" + (end -start)/1000 + " secs.");
		MathUtils.printDescriptiveStatistic(data);
		
		driver.shutDown();
	}

	private void gotoProvider(WebDriverUtils driver, WebElement elem) {
		
		driver.clickButton(elem);
		driver.explicitWait(2000);
		
		By by = By.id("NextTab1");
//		driver.implicitWait(by);
		
		driver.switchToNextTab();
		System.out.println(driver.getWindowTitle());
		
		driver.waitForElementToBeClickable(driver.findElement(by));
	}
	
	public void userJourney4(ConcurrentLinkedDeque<Double> data){
		
		final WebDriverUtils driver = new WebDriverUtils();
		driver.openURL("http://www.spaargids.be/sparen/vergelijk/goedkope-lening.html");
		By by = By.xpath("//a[@class='list_button margtop10']");
		driver.implicitWait(by);
		List<WebElement> elems = driver.findElements(by);
	
		long start = System.currentTimeMillis();
		this.gotoProvider(driver, elems.get(0));
		long end = System.currentTimeMillis();
		long duration = (end-start)/1000;
		data.add((double)duration);

		System.out.println("spaargids:" + (end -start)/1000 + " secs.");
		MathUtils.printDescriptiveStatistic(data);
		driver.shutDown();
	}
	
	static int failedRun = 0;
	static int totalRun = 0;
	public void userJourney5(final WebDriverUtils.Type browser_type){

		final WebDriverUtils driver = new WebDriverUtils(browser_type, false);
		driver.openURL("https://www.moneyhero.com.hk/zh/personal-loan/personal-instalment");
		totalRun ++;
		By by = By.xpath("//a[@class='cgg-button-action cgg-hidden-xs ng-binding ng-scope']");
		driver.explicitWait(3000);
		List<WebElement> elems = driver.getWebDriver_existing().findElements(by);
		if(elems.size() == 0){
			failedRun ++;
			driver.explicitWait(6000*1000);
		}
		System.out.println("cannot load elements:" + failedRun + "("+ totalRun+")");
		
		driver.shutDown();
	}
	
	public void userJourney7(){
		
		final WebDriverUtils driver = new WebDriverUtils();
		driver.openURL("https://www.comparaguru.com/prestamos-personales/opciones#/step/1");
		
		//Step 1
		By by = By.xpath("//*[@ng-switch-when='classNameAndText']");
		List<WebElement> elems = driver.findElements(by);
		driver.clickButton(elems.get(1));
		
		//Step 2
		by = By.xpath("//*[@ng-switch-when='cgg-borrow-money']/descendant::input[not(@view-number-model-string)]");
		elems = driver.findElements(by);
		driver.fillin_textbox(elems.get(0), "20000");
		driver.fillin_textbox(elems.get(1), "15");
		
		driver.clickButton(By.xpath("//button[@class='continue-button']"));
		
		//Step 3
		by = By.xpath("//div[@ng-switch-when='cgg-tile-multiple']/descendant::div[@class='cgg-table-cell']");
		elems = driver.findElements(by);
		driver.clickButton(elems.get(0));
		driver.clickButton(By.xpath("//button[@class='continue-button']"));
		
		//Step 4
		elems = driver.findElements(By.xpath("(//div[@ng-switch-when='cgg-tile-multiple'])[1]"));
		
		by = By.xpath("//div[@ng-switch-when='cgg-tile-multiple']/descendant::div[@class='cgg-table-cell']");
		elems = driver.findElements(by);
		driver.clickButton(elems.get(0));
		
	}
	
	public void userJourney8(){
		
		final WebDriverUtils driver = new WebDriverUtils();
		driver.openURL("http://denmark-preview.compareglobal.co.uk/forbrugslaan/online-laan/resultater");
		driver.explicitWait(3000);
		
		By by = By.xpath("//div[@class='dropdown purpose-dropdown purpose-dropdown-toggle ng-binding']");
		driver.clickButton(by);
		driver.explicitWait(3000);
		
		Checker checker = new Checker(driver);
		List<String> missingKeys = checker.findMissingTranslationsInPage();
		for(String missingKey: missingKeys){
			System.out.println(missingKey);
		}
	}
	
	public void userJourney10(){
		
		final WebDriverUtils driver = new WebDriverUtils();
		driver.openURL(
				"http://mexico-staging.compareglobal.co.uk/prestamos-personales/prestamos-inmediatos/resultados");
		driver.explicitWait(3000);
		
		By by = By.xpath("//div[@class='cgg-filter-panel__container ng-scope']/p[@class='cgg-more-details']/span[@ng-hide]");
		List<WebElement> elems = driver.findElements(by);
		elems.get(0).click();
		
		by = By.xpath("//div[@class='cgg-col-xs-12' and .//descendant::label[contains(text(),'Vivus')]]/descendant::span");
		elems = driver.findElements(by);
		elems.get(0).click();
	}
	
	public void userJourney9(){
		
		final WebDriverUtils driver = new WebDriverUtils();
		driver.openURL(
				"http://singapore.staging.compareglobal.co.uk/personal-loan/apply-now?loanAmount=10000&loanTime=3&loanTimeUnit=year&cggId=39848&product=UOB%20Personal%20Loan%20-%20Existing%20CashPlus%20Customers&apiRefForCheckout=http%3A%2F%2Fsingapore.staging.compareglobal.co.uk%2Fapi%2Fpersonal-loan%2Fv2%2Floans%2FpersonalInstalment%3FsortBy%3Dmrp%252B%26sortBy%3Dtrp%252B%26sortBy%3Dapr%252B%26wantToBorrow%3D10000%26wantToBorrowTime%3D3%26wantToBorrowTimeUnit%3Dyear%26provider%3DUOBX%26lang%3Den%26pageSize%3D15#/1");
		driver.explicitWait(3000);
		
		//Step 1
		By by = By.xpath("//cgg-dropdown/descendant::select[contains(@class,'selection-option')]");
		driver.select_selectorByIndex(by, 1);
		
		by = By.xpath("//cgg-input-text/descendant::input");
		driver.fillin_textbox(by, "abc");

		by = By.xpath("//input[@type='email']");
		driver.fillin_textbox(by, "test@test.com");
		
		by = By.xpath("(//cgg-input-text)[3]/descendant::input");
		driver.fillin_textbox(by, "99999999");
		
		by = By.xpath("(//cgg-input-text)[4]/descendant::input");
		driver.fillin_textbox(by, "99999999");
		 
		by = By.xpath("(//button[@class='continue-button']|//a[@class='cgg-button-next'])");;
		driver.clickButton(by);
		
		//Step 2
		by = By.xpath("(//button[@class='continue-button']|//a[@class='cgg-button-next'])");;
		driver.clickButton(by);
		
		//Step 3
		by = By.xpath("//cgg-datepicker");
		driver.clickButton(by);
		
		by = By.xpath("//div[@class='flatpickr-days']/span[@class='flatpickr-day selected']");
		driver.clickButton(by);
	}
	
	@Override
	public void run(){
		
		this.userJourney10();
//		this.userJourney9();
		/*while(true){
			this.userJourney3(data1);	
			this.userJourney4(data2);	
//			this.userJourney5(WebDriverUtils.Type.Chrome);
//			this.userJourney6();
			this.userJourney8();
		}*/
	}
	
	public static ConcurrentLinkedDeque<Double> data1 = new ConcurrentLinkedDeque<Double>();
	public static ConcurrentLinkedDeque<Double> data2 = new ConcurrentLinkedDeque<Double>();
	
	public static void main(String[] args){
		
		UserJourneysForCIAB ins = new UserJourneysForCIAB(new WebDriverUtils());
		for(int i = 0; i < 1; i ++){
			Thread thread = new Thread(ins);
			thread.start();
		}
	}
}
