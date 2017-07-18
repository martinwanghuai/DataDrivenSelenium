package toy;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;
import org.testng.collections.Lists;

import com.cgg.model.CRMMapping;
import com.cgg.model.CRMMappingDeserializer;
import com.cgg.model.DataDictionary;
import com.cgg.model.CRMMapping.Mapping;
import com.cgg.model.TestCase;
import com.cgg.pl.funnel.RequestPayloadChecker;
import com.google.common.collect.Maps;

import io.netty.handler.codec.http.HttpResponse;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.filters.RequestFilter;
import net.lightbody.bmp.proxy.CaptureType;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;
import utility.Checker;
import utility.Constant;
import utility.IOUtils;
import utility.WebDriverUtils;


public class NetworkTester {

	@Test
	public void test() throws Exception {
		String payloadStr = IOUtils.loadFile("./jsonStr.txt");
		HashMap<String, Object> actualFields = RequestPayloadChecker.createHashMapFromJsonString(payloadStr);
		HashMap<String, String> result = Maps.newHashMap();
		this.convertHashMap(actualFields, result);
		Iterator<String> ite = result.keySet().iterator();
		while(ite.hasNext()){
			String key = ite.next();
			String value = result.get(key);
			System.out.println(key+":" + value);
		}
	}
	
	private void convertHashMap(HashMap<String, Object> src, HashMap<String, String> result){
		
		Iterator<String> ite = src.keySet().iterator();
		while(ite.hasNext()){
			String key = ite.next();
			Object value = src.get(key);
			if(value instanceof String){
				result.put(key, (String)value);
//				System.out.println(key + ":" + value);
			}else if(value instanceof HashMap){
				this.convertHashMap((HashMap)value, result);
			}
		}
	}
	
	@Test
	public void testCRM() throws Exception {

		String url = "https://api.github.com/repos/compareasiagroup/usercity_crm_submit/contents/function/personal_loan_sg.json";
		CRMMapping crmMapping = (CRMMapping) IOUtils.loadJsonObjects(
				WebDriverUtils.callRESTAPI(url, "martinwanghuai", "1829979328b875a5a7acf149f5a8ee9f7b87240c"), CRMMapping.class, new CRMMappingDeserializer());
		
		List<Mapping> crmMappings = Lists.newArrayList();
		crmMappings.addAll(crmMapping.getLeads().getMappings());
		crmMappings.addAll(crmMapping.getProducts().getMappings());
		crmMappings.addAll(crmMapping.getApplications().getMappings());
		crmMappings.addAll(crmMapping.getProviders().getMappings());
		crmMappings.addAll(crmMapping.getApplicants().getMappings());
		
		List<String> expectedCRMFields = Lists.newArrayList();
		for(Mapping map: crmMappings){
			String value = map.getStrs().get(1);
			String temp = value.substring(value.lastIndexOf("/") + "/".length());
			expectedCRMFields.add(temp);
			System.out.println(temp);
		}
	}
	
	@Test
	public void testDataDictionary() throws Exception {

		try {
			String url = "https://api.github.com/repos/compareasiagroup/data-dictionary/contents/Data-Dictionary.pipe";
			List<String> results = Lists.newArrayList(WebDriverUtils.callRESTAPI(url, "martinwanghuai", "1829979328b875a5a7acf149f5a8ee9f7b87240c").split("\\r?\\n"));
			Map<String, DataDictionary> dataDictionary = Maps.newHashMap();
			for(int i = 1; i < results.size(); i ++){
				DataDictionary data = new DataDictionary(Lists.newArrayList(results.get(i).split("\\|")));
				dataDictionary.put(data.getNameDescription(), data);
			}
			
			Map<String, String> dataTypes = Maps.newHashMap();
			dataDictionary.forEach((s, data) -> dataTypes.put(s, data.getCRMDataType()));
			for(Entry<String, String> types: dataTypes.entrySet()){
				if(Checker.isBlank(types.getValue())){
					System.out.println(types.getKey());	
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	int counter = 1;
	
//	@Test(invocationCount = 3)
	@Test
	public void testBrowserMobProxy() throws Exception {

		BrowserMobProxy proxy = new BrowserMobProxyServer();
		proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
		proxy.enableHarCaptureTypes(CaptureType.REQUEST_HEADERS);
		proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT);
		proxy.enableHarCaptureTypes(CaptureType.RESPONSE_HEADERS);
		proxy.enableHarCaptureTypes(CaptureType.RESPONSE_CONTENT);
		TestCase testCase = new TestCase();
		testCase.setTestCaseName("Test21PLAppForm_HK");
		proxy.addRequestFilter(new RequestFilter() {
        	
			@Override
			public HttpResponse filterRequest(io.netty.handler.codec.http.HttpRequest request,
					HttpMessageContents contents, HttpMessageInfo messageInfo) {
				
				RequestPayloadChecker checker = new RequestPayloadChecker(null, testCase);
				return checker.checkRequestPayload_funnel_appForm(request, contents, messageInfo);
			}
       	});


        proxy.start();
        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);
        
        System.setProperty("webdriver.chrome.driver", Constant.USRDIR + Constant.PATH_TO_CHROME_DRIVER);
		WebDriver driver = new ChromeDriver(capabilities);
		
        this.userJourney(driver);
        
        proxy.newHar("test");
        Har har = proxy.getHar();
       
        har.writeTo(new File("./baidu.json"));
	}
	
	private void userJourney(WebDriver driver1){
		
		WebDriverUtils driver = new WebDriverUtils(driver1);
//		driver.openURL("http://www.baidu.com");
		driver.openURL("http://singapore.staging.compareglobal.co.uk/personal-loan/apply-now?loanAmount=10000&loanTime=3&loanTimeUnit=year&cggId=39848&product=UOB%20Personal%20Loan%20-%20Existing%20CashPlus%20Customers&apiRefForCheckout=http%3A%2F%2Fsingapore.staging.compareglobal.co.uk%2Fapi%2Fpersonal-loan%2Fv2%2Floans%2FpersonalInstalment%3FsortBy%3Dmrp%252B%26sortBy%3Dtrp%252B%26sortBy%3Dapr%252B%26wantToBorrow%3D10000%26wantToBorrowTime%3D3%26wantToBorrowTimeUnit%3Dyear%26provider%3DUOBX%26lang%3Den%26pageSize%3D15#/1");
		// Step 1
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

		by = By.xpath("(//button[@class='continue-button']|//a[@class='cgg-button-next'])");
		;
		driver.clickButton(by);
		driver.explicitWait(3000);
		
		// Step 2
		by = By.xpath("(//button[@class='continue-button']|//a[@class='cgg-button-next'])");;
		driver.clickButton(by);
		driver.explicitWait(3000);
		
		
		driver.closeAllWins();
	}

}
