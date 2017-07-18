package utility;

import static utility.Constant.EXPLICITWAIT_MILLIS;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.collections.Lists;

import com.cgg.model.TestCase;
import com.cgg.pl.category.AlfrescoSetup;
import com.cgg.pl.funnel.RequestPayloadChecker;

import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.filters.RequestFilter;
import net.lightbody.bmp.proxy.CaptureType;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;
import pageObjects.BaseClass;

/**
 * 
 * @author martin.wang
 *
 */
public class WebDriverUtils {

	private WebDriver driver = null;
	private TestCase testCase = null;
	private BrowserMobProxyServer proxy = null;
	
	public String getBaseWindow(){
		
		List<String> wins = Lists.newArrayList(driver.getWindowHandles());
		return wins.size() > 0 ? wins.get(0): null;
	}
	
	public WebDriverUtils(final WebDriverUtils.Type browser_type, final boolean maximizeWin){
		
		driver = this.getWebDriver_new(browser_type, maximizeWin);
	}
	
	public WebDriverUtils(final boolean maximizeWin){

		this(Constant.DEFAULT_BROWSER, maximizeWin);
	}
	
	public WebDriverUtils(){

		this(Constant.DEFAULT_BROWSER, true);
	}
	
	public WebDriverUtils( final int width, final int height){
	
		this(Constant.DEFAULT_BROWSER, false);
		driver.manage().window().setSize(new Dimension(width, height));
	}
	
	public WebDriverUtils(final WebDriver driver){
		
		this.driver = driver;
	}

	public enum Type {
		FireFox, Safari, Chrome, InternetExplorer, HtmlUnit, PhantomJS, Opera
	}

	public WebDriver getWebDriver_existing() {
		
		return driver;
	}

	public boolean isBlankPage() {

		By by = By.xpath("//body");
		WebElement elem = driver.findElement(by);
		return this.isBlankPage(elem);
	}
	
	public boolean isBlankPage(WebElement elem, final By by){
		
		try{
			return this.isBlankPage(elem);
		}catch(StaleElementReferenceException e){
			elem = driver.findElement(by);
			return this.isBlankPage(elem);
		}
	}
	
	public boolean isBlankPage(final WebElement htmlBody) {
		
		return Checker.isBlank(htmlBody.getText());
	}

	public boolean textPresentInPage(WebElement elem, final By by, final String text){
		
		try{
			return elem.getText().toLowerCase().contains(text.toLowerCase());	
		}catch(StaleElementReferenceException e){
			elem = driver.findElement(by);
			return elem.getText().toLowerCase().contains(text.toLowerCase());
		}
	}
	
	public List<String> matchStringPatternsInWebElement(WebElement elem, final By by, final String pattern, final boolean isCheckOuterHTML){
		
		try{
			return isCheckOuterHTML ? this.matchStringPatternsInOuterHTMLOfWebElement(elem, pattern):this.matchStringPatternsInTextOfWebElement(elem, pattern); 
		}catch(StaleElementReferenceException e){
			elem = driver.findElement(by);
			return isCheckOuterHTML ? this.matchStringPatternsInOuterHTMLOfWebElement(elem, pattern):this.matchStringPatternsInTextOfWebElement(elem, pattern);
		}
	}
	
	public List<String> matchStringPatternsInTextOfWebElement(WebElement elem, final String pattern){
		
		return this.matchStringPatternsInPageSource(elem.getText(), pattern); 
	}
	
	public List<String> matchStringPatternsInOuterHTMLOfWebElement(WebElement elem, final String pattern){
		
		return this.matchStringPatternsInPageSource(elem.getAttribute("outerHTML"), pattern);
	}
	
	public List<String> matchStringPatternsInPageSource(final String pageSrc, final String pattern){
		
		List<String> matchedList = Lists.newArrayList();
		String utf8String = IOUtils.toUTF8String(pageSrc);
		if (!Checker.isBlank(utf8String)) {
			String[] strList = utf8String.split("\\s");
			boolean found = false;
			for (String str : strList) {
				found = str.trim().matches(pattern);
				if (found) {
					matchedList.add(str.trim());
				}
			}
		}
		return matchedList;
	}
	
	public boolean textPresentInPage(final String text) {

		final By by = By.xpath("//body");
		return getHowManyByPresntInPage(by, false) > 0 ? driver.findElement(by)
				.getText().toLowerCase().contains(text.toLowerCase()) : false;
	}

	public boolean refreshingAndCheckTextPresentedInPage(
			final String textToFound) {

		boolean textPresented = false;
		int counter = 0;
		final int loop_max = 20;
		while (!textPresented && counter < loop_max) {
			// try 20 times to wait for system auto trigger rule to show out in
			// UI
			driver.navigate().refresh();
			explicitWait();
			textPresented = textPresentInPage(textToFound);
			counter++;
		}
		return textPresented;
	}

	public void explicitWait() {

		explicitWait(EXPLICITWAIT_MILLIS);
	}

	public void explicitWait(final long wait_millis) {

		try {
			Thread.sleep(wait_millis);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
	}

	public List<WebElement> findElements(final By by) {

		try{
			return driver.findElements(by);	
		}catch(NoSuchElementException e){
			this.implicitWait(by);
			return driver.findElements(by);
		}
	}
	
	public WebElement findElement(final By by){
		
		try{
			return driver.findElement(by);	
		}catch(NoSuchElementException e){
			this.implicitWait(by);			
			return driver.findElement(by);
		}
	}
	
	public String getCurrentUrl(){
		
		return driver.getCurrentUrl();
	}

	private int getHowManyByPresntInPage(final By by) {

		final int size = driver.findElements(by).size();

		if (size == 0 && Constant.PRINT_ELEMENT_NOT_FOUND_MSG) {
			System.out.println("warning: cannot find web element:"
					+ by.toString());
		}

		return size;
	}

	public int getHowManyByPresntInPage(final By by, final boolean hasToFindIt) {

		if (hasToFindIt) {
			implicitWait(by);
		}

		return getHowManyByPresntInPage(by);
	}
	
	public void implicitWait(final By by, final int timeOut_secs, final int pollDuration_sec){
		
		try{
			Wait<WebDriver> varwait = new FluentWait<WebDriver>(driver).withTimeout(timeOut_secs, TimeUnit.SECONDS)
					.pollingEvery(pollDuration_sec, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
			varwait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void implicitWait(final By by) {
		
		this.implicitWait(by, 20, 1);
	}
	
	public void waitForElementToBeClickable(final WebElement elem, final int implicitWait_Millis){
		
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(implicitWait_Millis,
						TimeUnit.MILLISECONDS)
				.pollingEvery(300, TimeUnit.MILLISECONDS)
				.ignoring(Exception.class);

		wait.until(ExpectedConditions.elementToBeClickable(elem));
		
	}

	public void waitForElementToBeClickable(final WebElement elem) {

		this.waitForElementToBeClickable(elem, Constant.IMPLICITWAIT_MILLIS);
	}


	public void waitForPageLoad() {
		final ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(final WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript(
						"return document.readyState").equals("complete");
			}
		};

		try{
			final WebDriverWait wait = new WebDriverWait(driver,
					Constant.WAIT_AJAXELEMENT_MILLIS / 1000);
			wait.until(pageLoadCondition);
	
		}catch(WebDriverException e){
			//wait 20 secs if waiting for doc.body failed
			final WebDriverWait wait = new WebDriverWait(driver,
					20);
			wait.until(pageLoadCondition);
		}
	}

	public WebDriver getWebDriver_new(final boolean maximizeWin) {

		return getWebDriver_new(Constant.DEFAULT_BROWSER, maximizeWin);
	}

	public WebDriver getWebDriver_new() {

		return getWebDriver_new(true);
	}

	public WebDriver getWebDriver_new(final FirefoxProfile profile) {

		driver = new FirefoxDriver(profile);
		return driver;
	}

	public WebDriver getWebDriver_new(final Type browser_type,
			final boolean maximizeWin) {

		try{
			DesiredCapabilities capabilities = null;
			if(Constant.ENABLE_BROWSER_MOB){
				proxy = new BrowserMobProxyServer();
				proxy.enableHarCaptureTypes(CaptureType.REQUEST_HEADERS, CaptureType.RESPONSE_HEADERS, CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
				proxy.start();
				Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
				capabilities = new DesiredCapabilities();
				capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);
			}
			
			switch (browser_type) {
			case FireFox:
				driver = capabilities == null ? new FirefoxDriver(): new FirefoxDriver(capabilities);
				break;
			case Safari:
				System.setProperty("webdriver.safari.driver", Constant.USRDIR + Constant.PATH_TO_SAFARI_DRIVER);
				driver = new SafariDriver();
				break;
			case Chrome:
				//Check out JS error
				System.setProperty("webdriver.chrome.driver", Constant.USRDIR + Constant.PATH_TO_CHROME_DRIVER);
				driver = capabilities == null ? new ChromeDriver(): new ChromeDriver(capabilities);
				break;
			case InternetExplorer:
				System.setProperty("webdriver.ie.driver", Constant.USRDIR + Constant.PATH_TO_IE_DRIVER);
				driver = new InternetExplorerDriver();
				break;
			case HtmlUnit:
				driver = new HtmlUnitDriver();
				break;
			case Opera:
				DesiredCapabilities capabilities1 = DesiredCapabilities.opera();
				System.setProperty("webdriver.opera.driver", Constant.USRDIR + Constant.PATH_TO_OPERA_DRIVER);
				driver = new OperaDriver(capabilities1);
				break;
			}

			driver.manage()
					.timeouts()
					.implicitlyWait(Constant.IMPLICITWAIT_MILLIS,
							TimeUnit.MILLISECONDS);
			if (maximizeWin) {
				driver.get("http://www.google.com");
				this.maxWindow(driver);
			}
	
		}catch(Exception e){
			
		}
		
		new BaseClass(this);
		return driver;
	}

	public String getWindowHandle(){
		
		return driver.getWindowHandle();
	}
	
	public void switchToNextTab() {

		switchToPopUpWin();
	}

	public void switchToPreviousTab() {

		switchToParentWin();
	}

	public void switchToPopUpWin() {

		List<String> wins = Lists.newArrayList(driver.getWindowHandles());
		driver.switchTo().window(wins.get(wins.size() - 1));
	}

	public boolean hasPopUpWin() {

		return driver.getWindowHandles().size() > 1;
	}

	public void switchToParentWin() {

		List<String> wins = Lists.newArrayList(driver.getWindowHandles());
		int index = wins.size() == 1 ? 0 : wins.size() - 2;
		driver.switchTo().window(wins.get(index));
	}

	private void switchToWin(final String win){
	
		driver.switchTo().window(win);
	}
	
	public void switchToCurrentWin() {
		
		final String currentWin = driver.getWindowHandle();
		switchToWin(currentWin);
	}
	
	public void switchToBaseWin() {
		
		List<String> wins = Lists.newArrayList(driver.getWindowHandles());
		if(wins.size() > 0){
			driver.switchTo().window(wins.get(0));	
		}
	}

	public void switchToFrame(final String frameID) {

		final By by = By.id(frameID);
		switchToFrame(by);
	}

	public void switchToFrame(final By frame) {

		driver.switchTo().defaultContent();
		this.implicitWait(frame);
		final WebElement we = driver.findElement(frame);
		driver.switchTo().frame(we);
	}

	public void switchToNestedFrame(final ArrayList<By> bys) {

		driver.switchTo().defaultContent();
		int size = -1;
		for (final By by : bys) {
			size = getHowManyByPresntInPage(by, false);
			if (size == 1) {
				final WebElement we = driver.findElement(by);
				driver.switchTo().frame(we);
			}
		}
	}

	public void closeWindow(){
		
		this.acceptAlertIfPresent();// cannot close window because of confirmation alerts; 
		 
		((JavascriptExecutor)driver)
         .executeScript( "window.onbeforeunload = function(e){};" );// Close HTML page with buttons like "Leave Page" and "Stay on Page";
		
		driver.close();
	}
	
	public void closeAllWins() {

		this.shutDown();
	}

	public void closeAllPopUpWins() {

		if(driver.getWindowHandles().size() > 1){
			List<String> wins = Lists.newArrayList(driver.getWindowHandles());
			for(int i = 1; i < wins.size(); i ++){
				final String currentWin = wins.get(i);
				driver.switchTo().window(currentWin);
				driver.close();
			}
			
			final String currentWin = wins.get(0);
			driver.switchTo().window(currentWin);
		}
	}

	public boolean isElementPresent(final By by) {

		try {
			driver.findElement(by);
			return true;
		} catch (final Exception e) {
			return false;
		}
	}
	
	public boolean isElementPresent(final By by, final WebElement elem){
		
		try {
			elem.findElement(by);
			return true;
		} catch (final Exception e) {
			return false;
		}
	}

	public void mouseOver(final By by) {

		implicitWait(by);
		highlightElement(by);

		final WebElement we = driver.findElement(by);
		final Actions action = new Actions(driver);
		action.moveToElement(we).build().perform();
	}

	public void check_checkbox(final By by) {

		implicitWait(by);
		final WebElement we = driver.findElement(by);
		this.check_checkbox(we);
	}
	
	public void check_checkbox(final WebElement elem){
		
		highlightElement(elem);
		if(!elem.isSelected()){
			elem.click();
		}
	}

	public void uncheck_checkbox(final WebElement elem){
		
		highlightElement(elem);
		if(elem.isSelected()){
			elem.click();
		}
	}

	public void uncheck_checkbox(final By by) {

		implicitWait(by);
		final WebElement we = driver.findElement(by);
		this.uncheck_checkbox(we);
	}

	public void fillin_textbox(final WebElement elem, final String str) {

		highlightElement(elem);
		elem.sendKeys(Keys.chord(Keys.CONTROL, "a"), str);
	}

	public void fillin_textbox(final By by, final String str) {

		implicitWait(by);
		fillin_textbox(driver.findElement(by), str);
	}
	
	public void append_textbox(final By by, final String str) {

		implicitWait(by);
		highlightElement(by);
		driver.findElement(by).sendKeys(str);
	}

	public void select_selectorByText(final By by, final String str) {

		implicitWait(by);
		highlightElement(by);
		new Select(driver.findElement(by)).selectByVisibleText(str);
	}

	public void select_selectorByPartialTexts(final String name_selector,
			final String str) {

		final By by = By.xpath("//select[@name='" + name_selector
				+ "']/option[contains(text(),'" + str + "')]");
		implicitWait(by);
		highlightElement(by);
		driver.findElement(by).click();
	}
	
	public String getSelectorValue(final By by, final int index){

		String value = "";
		if(this.isElementPresent(by)){

			Select selector = new Select(driver.findElement(by));
			List<WebElement> options = selector.getOptions().stream().filter(option -> option.isEnabled()).collect(Collectors.toList());
			if(index < options.size()){
				value = options.get(index).getAttribute("value");
			}else{
				System.out.println("index larger than size:");
			}
		}else{
			System.out.println("Web element not exist:" + by);
		}

		return value;
	}
	
	public void select_selectorByIndex(final By by, final int index) {

		implicitWait(by);
		highlightElement(by);
		if(this.isElementPresent(by)){

			Select selector = new Select(driver.findElement(by));
			List<WebElement> options = selector.getOptions().stream().filter(option -> option.isEnabled()).collect(Collectors.toList());
			if(index < options.size()){
//				selector.selectByIndex(index);	
				options.get(index).click();
			}else{
				System.out.println("index larger than size:");
			}
		}else{
			System.out.println("Web element not exist:" + by);
		}
		
	}

	public void select_selectorByValue(final By by, final String value) {

		implicitWait(by);
		select_selectorByValue(driver.findElement(by), value);
	}

	public void select_selectorByValue(final WebElement elem, final String value) {

		By by = By.xpath(".//option[@value='" + value + "']");
		if(this.isElementPresent(by, elem)){
			highlightElement(elem);
			new Select(elem).selectByValue(value);	
		}
	}

	public void checkRadio(final By by) {

		clickButton(by);
	}

	public void clickButton(final By by) {

		clickButton(driver.findElement(by));
	}
	
	public void clickButton(WebElement elem, final By by){
		try{
			this.clickButton(elem);	
		}catch(StaleElementReferenceException e){
			elem = this.findElement(by);
			this.clickButton(elem);
		}
		
	}

	public void clickButton(final WebElement elem) {

		highlightElement(elem);
		try {
			elem.click();
		} catch (Exception e) {
			// fail since element outside the browser view
			this.clickHiddenElement(elem);
		}
	}

	public String getTableRowText(final By by_table, final int rowIndex) {

		String rowText = "";
		final int size = driver.findElements(by_table).size();
		if (size == 1) {
			final By by = By.tagName("tr");
			highlightElement(by);
			rowText = driver.findElement(by_table).findElements(by)
					.get(rowIndex).getText();
		}
		return rowText;
	}

	public int getTableRowCount(final By by_rows) {

		final int size = driver.findElements(by_rows).size();
		if (size >= 1) {
			highlightElement(by_rows);
		}
		return size;
	}

	public int getTableColumnCount(final By by_columns) {

		final int size = driver.findElements(by_columns).size();
		if (size >= 1) {
			highlightElement(by_columns);
		}
		return size;
	}

	public void clickLink(final By by) {

		clickButton(by);
	}

	public void clickLink(final WebElement elem) {

		clickButton(elem);
	}

	public String getTextWithoutChecking(final By by) {

		final int size = driver.findElements(by).size();
		if (size >= 1) {
			highlightElement(by);
			return driver.findElement(by).getText();
		}
		return "";
	}

	public String getText(final By by) {

		implicitWait(by);
		highlightElement(by);
		return driver.findElement(by).getText();
	}

	public String getAttributeValue(final By by, final String attr) {

		String result = "";
		final int size = getHowManyByPresntInPage(by, false);
		if (size > 0) {
			result = getAttributeValue(driver.findElement(by), attr);
		}

		return result;
	}

	public String getWindowTitle() {

		return driver.getTitle();
	}
	
	public String getAttributeValue(WebElement elem, final By by, final String attr) {

		try{
			return this.getAttributeValue(elem, attr);	
		}catch(StaleElementReferenceException e){
			elem = driver.findElement(by);
			return this.getAttributeValue(elem, attr);
		}
		
	}
	
	public WebElement getWebElement(final By by, int index){
		
		List<WebElement> links = this.findElements(by);
		WebElement result = null;
		try{
			result = links.get(index);
		}catch(StaleElementReferenceException e1){
			links = this.findElements(by);
			if(index < links.size()){
				try{
					result = links.get(index);	
				}catch(StaleElementReferenceException e2){
					this.explicitWait();
					links = this.findElements(by);
					if(index < links.size()){
						result = links.get(index);
					}
				}
				
			}
		}
		
		return result;
	}
	
	public String getAttributeValue(final By by, int index, final String attr){

		WebElement link = this.getWebElement(by, index);
		return this.getAttributeValue(link, attr);
	}

	public String getAttributeValue(final WebElement elem, final String attr) {

		highlightElement(elem);
		String value = "";
		try{
			value = elem.getAttribute(attr);	
		}catch(Exception e){
			this.waitForElementToBeClickable(elem);
			value = elem.getAttribute(attr);
		}
		return value;			
	}

	public String closeAlertAndGetItsText() {

		final boolean isAccept = true;
		String alertText = "Cannot get the pop up alert text, pls check it@WebDriverUtils.closeAlertAndGetItsText";

		try {
			final Alert javascriptAlert = driver.switchTo().alert();
			alertText = javascriptAlert.getText(); // Get text on alert box

			if (isAccept) {
				javascriptAlert.accept(); // click OK
			} else {
				javascriptAlert.dismiss();// click cancel
			}

		} catch (final Exception e) {
			System.out.println(e.getStackTrace());
		}
		return alertText;
	}

	public boolean isAlertPresent() {

		boolean present = false;
		try {
			// 1. Solution 1: work
			driver.switchTo().alert();
			present = true;

			// 2. Solution 2: not work
			/*
			 * driver.getTitle(); present = true;
			 */
		} catch (final Exception e) {
			present = false;
		}
		return present;
	}

	public void acceptAlertIfPresent() {

		final boolean exist = isAlertPresent();
		if (exist) {
			acceptAlert();
		}
	}

	public void acceptAlert() {

		driver.switchTo().alert().accept();
	}

	public void waitAlertAndAccept() {

		boolean flag = false;
		final int loop_max = 10;
		int counter = 0;
		do {
			explicitWait(1000);
			refreshWindow();
			flag = isAlertPresent();
			counter++;
		} while (!flag && counter < loop_max);

		if (flag) {
			acceptAlert();
		}
	}

	public void refreshWindow() {

		driver.navigate().refresh();
	}

	public void mouseUp(final By by) {

		final WebElement we = driver.findElement(by);
		final Locatable hoverItem = (Locatable) we;
		final Mouse mouse = ((HasInputDevices) driver).getMouse();
		mouse.mouseUp(hoverItem.getCoordinates());
	}

	public void mouseDown(final By by) {

		final WebElement we = driver.findElement(by);
		final Locatable hoverItem = (Locatable) we;
		final Mouse mouse = ((HasInputDevices) driver).getMouse();
		mouse.mouseDown(hoverItem.getCoordinates());
	}

	public void uploadFile(final By by, final String path) {

		driver.findElement(by).sendKeys(path);
	}

	public void clickAt(final By by, final int xOffset, final int yOffset) {

		final Actions builder = new Actions(driver);
		final WebElement toElement = driver.findElement(by);
		builder.moveToElement(toElement, xOffset, yOffset).build().perform();
	}

	public void clickAt(final By by, final String xOffset, final String yOffset) {

		clickAt(by, Integer.parseInt(xOffset), Integer.parseInt(yOffset));
	}

	private void mouseDownAt(final By by, final int xOffset, final int yOffset) {

		final Actions builder = new Actions(driver);
		final WebElement toElement = driver.findElement(by);
		builder.keyDown(Keys.CONTROL).click(toElement)
				.moveByOffset(xOffset, yOffset).click().build().perform();
	}

	public void clickHiddenElement(final By by) {

		this.clickHiddenElement(driver.findElement(by));
	}

	public void clickHiddenElement(final WebElement elem) {

		final JavascriptExecutor js = (JavascriptExecutor) driver;
		try{
			js.executeScript("arguments[0].click()", elem);			
		}catch(Exception e){
			this.waitForElementToBeClickable(elem);
			js.executeScript("arguments[0].click()", elem);	
		}

	}

	public void dragAndDrop(final By src, final By target) {

		final Actions builder = new Actions(driver);
		final WebElement srcEle = driver.findElement(src);
		final WebElement destEle = driver.findElement(target);
		// builder.keyDown(Keys.CONTROL).click(srcEle).click(destEle).keyUp(Keys.CONTROL);
		// //Method 1

		builder.clickAndHold(srcEle).moveToElement(destEle).release(destEle)
				.build().perform(); // Method 2

		// builder.dragAndDrop(srcEle, destEle).build().perform(); //Method 3
	}

	public void getFocus(final By target) {

		final WebElement targetEle = driver.findElement(target);
		if (targetEle.getTagName().equals("input")) {
			targetEle.sendKeys("");
		} else {
			final Actions builder = new Actions(driver);
			builder.moveToElement(targetEle).perform();
		}
	}

	public void getFocus(final WebElement targetEle) {

		if (targetEle.getTagName().equals("input")) {
			targetEle.sendKeys("");
		} else {
			final Actions builder = new Actions(driver);
			builder.moveToElement(targetEle).perform();
		}
	}

	public void scrollWindowToElement(final WebElement elem) {

		final JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", elem);
	}

	public void scrollWindowToElement(final By target) {

		this.scrollWindowToElement(driver.findElement(target));
	}

	public void scrollWindowToElementAt(final By target, final int xOffset) {

		final Point hoverItem = driver.findElement(target).getLocation();
		final JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0," + (hoverItem.getX() + xOffset)
				+ ");");
	}

	public void maxWindow(final WebDriver driver) {

		driver.manage().window().maximize();
	}

	public void highlightElement(final By by) {

		highlightElement(driver.findElement(by));
	}
	
	public void highlightElement(WebElement elem, final By by) {

		try{
			highlightElement(elem);
		}catch(StaleElementReferenceException e){
			elem = driver.findElement(by);
			highlightElement(elem);
		}
	}

	public void highlightElement(final WebElement elem) {

		try {
			if (Constant.ENABLE_HIGHLIGHTER) {
				moveToElementAndHighlight(elem);
			}
		} catch (Exception e) {
			this.waitForElementToBeClickable(elem);
			moveToElementAndHighlight(elem);
		}
	}
	
	public void moveToElement(final WebElement elem){
		
		new Actions(driver).moveToElement(elem).perform();
	}

	private void moveToElementAndHighlight(WebElement elem, final By by) {
		
		try{
			this.moveToElementAndHighlight(elem);
		}catch(StaleElementReferenceException e){
			elem = driver.findElement(by);
			this.moveToElementAndHighlight(elem);
		}
	}

	public void triggerOnChangeEvent(final WebElement elem) {
		
		this.scrollWindowToElement(elem);
		final JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].onchange();", elem);
	}
	
	private void moveToElementAndHighlight(final WebElement elem) {
		
		this.scrollWindowToElement(elem);
		final JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript(
				"arguments[0].setAttribute('style', arguments[1]);",
				elem, "color: red; border: 3px solid red;");
		explicitWait(Constant.HIGHLIGHT_ELEMENT_MILLIS);
		js.executeScript(
				"arguments[0].setAttribute('style', arguments[1]);",
				elem, "");
	}

	public void openURL(final String url) {

		if(driver == null){
			driver = this.getWebDriver_new();
		}
		
		driver.get(url);
	}

	public void takeScreenShot(final String destFile) {

		final TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		final File scrFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

		try {
			FileUtils.copyFile(scrFile, new File(destFile));
		} catch (final IOException ioe) {
			throw new RuntimeException(ioe);
		}
		closeAllPopUpWins();
	}

	public void shutDown(){

		if(driver != null){
			driver.quit(); //close every window	
		}
	}
	
	public void deleteAllCookies(){
		
		if(driver != null){
			driver.manage().deleteAllCookies();//this not work at all	
		}
	}
	
	public String getResponseHtml(final String urlStr){
		
		String result = "";
		try{
			HttpResponse response = getHttpResponse(urlStr);
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity, HTTP.UTF_8);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;	
	}
	
	public int checkUrl(final String urlStr){

		try{
			HttpResponse urlresp = getHttpResponse(urlStr);
			return urlresp.getStatusLine().getStatusCode();	
		}catch(Exception e){
			return 404;
		}
	}

	public static HttpResponse getHttpResponse(final String urlStr) throws Exception{
		
		HttpResponse response = null;
	
		URL url = new URL(urlStr);
		URI uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), null);
		HttpClient client = HttpClientBuilder.create().build();
		response = client.execute(new HttpGet(uri.toURL().toString()));
	
		return response;
	}
	
	public static String callRESTAPI(final String url, final String userName, final String pwd){
		
		String result = "";
		try{
			URL obj = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
			conn.setRequestProperty("Accept", "application/vnd.github.v3.raw+json");
			conn.setDoOutput(true);
			
			String userpass = userName + ":" + pwd;
			String basicAuth = "Basic "
					+ new String(new org.apache.commons.codec.binary.Base64().encode(userpass.getBytes()));
			conn.setRequestProperty("Authorization", basicAuth);
			result = org.apache.commons.io.IOUtils.toString(conn.getInputStream(), "UTF-8");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result; 
	}
	
	public static String callRESTAPI(final String url) {

		String result = "";
		try {
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(Constant.IMPLICITWAIT_MILLIS).build();
			HttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
			HttpResponse response = client.execute(new HttpGet(url));

			BufferedReader bs = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = bs.readLine()) != null) {
				sb.append(line).append("\n");
			}
			result = sb.toString();
		} catch (Exception e) {
			
		}

		return result;
	}
	
	public String getUserCityData(final String urlStr){
		
		List<NameValuePair> params = Lists.newArrayList();
		params.add(new BasicNameValuePair("Usercity-Auth-Token", "c1550bd0-e187-4bce-9044-846eb89cad8f"));
		return this.getBasicRESTAPI(urlStr, params);
	}
	
	public String getRESTAPI(final String urlStr, final String userName, final String pwd) {

		String auth = Base64.getEncoder().encodeToString((userName + ":" + pwd).getBytes());
		String value = "Basic " + auth;
		
		List<NameValuePair> params = Lists.newArrayList();
		params.add(new BasicNameValuePair("Authorization", value));
		
		return this.getBasicRESTAPI(urlStr, params);
	}
	
	public String getBasicRESTAPI(final String urlStr, final List<NameValuePair> params) {

		String result = "";
		try {
			URL url = new URL(urlStr);
			
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			for(NameValuePair pair: params){
				connection.setRequestProperty(pair.getName(), pair.getValue());	
			}
			
			InputStream content = (InputStream) connection.getInputStream();
			BufferedReader bs = new BufferedReader(new InputStreamReader(content));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = bs.readLine()) != null) {
				sb.append(line).append("\n");
			}
			result = sb.toString();
		} catch (Exception e) {
			
		}

		return result;
	}
	
	public boolean findErrorInPage(final String urlStr){
		
		this.openURL(urlStr);
		return this.findElement(By.xpath("//head/meta[@property='og:title']")).getAttribute("outerHTML")
				.contains("og.error.title");
	}
	
	public LogEntries getJavaScriptErrorsInPage(){
		
		return driver.manage().logs().get(LogType.BROWSER);
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public TestCase getTestCase() {
		return testCase;
	}

	public void setRequestFilter(RequestFilter filter){
		
		if(Constant.ENABLE_BROWSER_MOB && proxy != null){
			proxy.addRequestFilter(filter);
		}
	}
	
	public static RequestFilter NEWSLETTER_FILTER(final TestCase testCase){
		
		return new RequestFilter() {
	    	
			@Override
			public io.netty.handler.codec.http.HttpResponse filterRequest(io.netty.handler.codec.http.HttpRequest request,
					HttpMessageContents contents, HttpMessageInfo messageInfo) {
				
				RequestPayloadChecker checker = new RequestPayloadChecker(null, testCase);
				return checker.checkRequestPayload_newsletter(request, contents, messageInfo);
			}
	   	};
	} 
	
	public static RequestFilter FUNNEL_APPFORM_FILTER(final TestCase testCase){
		
		return new RequestFilter() {
	    	
			@Override
			public io.netty.handler.codec.http.HttpResponse filterRequest(io.netty.handler.codec.http.HttpRequest request,
					HttpMessageContents contents, HttpMessageInfo messageInfo) {
				
				RequestPayloadChecker checker = new RequestPayloadChecker(null, testCase);
				return checker.checkRequestPayload_funnel_appForm(request, contents, messageInfo);
			}
	   	};
	} 
	
	public void setTestCase(TestCase testCase) {
		
		this.testCase = testCase;
//		this.setRequestFilter(FUNNEL_APPFORM_FILTER(this.testCase));		
	}
}
