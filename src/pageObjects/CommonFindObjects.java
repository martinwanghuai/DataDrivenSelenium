package pageObjects;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;


import com.cgg.model.AppFormConfig;
import com.cgg.model.AppFormConfig.Route;
import com.cgg.model.FunnelConfig;
import com.cgg.model.FunnelConfig.StepInfo;
import com.cgg.model.MortgageAPIJsonObjects;
import com.cgg.model.MortgageJsonObject;
import com.cgg.model.PLAPIJsonObjects;
import com.cgg.model.PLJsonObjects;
import com.cgg.model.PersonalLoanConfig;
import com.cgg.model.TestCase;
import com.cgg.model.TestingStep;
import com.cgg.model.UserCityRecord;
import com.cgg.pl.category.AlfrescoSetup;
import com.cgg.pl.category.AppFormStep;
import com.cgg.pl.category.Category;
import com.cgg.pl.category.PersonalLoan;
import com.cgg.pl.category.ResultSource;
import com.cgg.pl.funnel.RequestPayloadChecker;
import com.cgg.pl.funnel.SimpleFunnelChecker;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;

import pages.FunnelStep;
import pages.HomePage;
import utility.Checker;
import utility.Constant;
import utility.IOUtils;
import utility.Log;
import utility.ReflectionUtils;
import utility.WebDriverUtils;

/**
 * 
 */
public class CommonFindObjects extends BaseClass {
	private static WebElement element = null;
	private static String strlinkText = null;
	private static Actions actions = null;
	
	private static JavascriptExecutor js = null;

	
	public CommonFindObjects(){
		super();

	}
	
	public CommonFindObjects(WebDriverUtils driver) {
		super(driver);
		if(driver != null){
			actions = new Actions(driver.getWebDriver_existing());
			js = (JavascriptExecutor) driver;
		}
	}

	/*---------------****byLinkTextMethod****---------------*/
	public static WebElement byLinkTextMethod(final TestingStep testingStep) throws Exception {

		final String varValue = testingStep.getStrObjIdentifier();
		try {
			element = driverUtils.findElement(By.linkText(varValue));
			if (element != null)
				strlinkText = element.getText();
			loggerMethodSucess(strlinkText);
		} catch (Exception e) {
			loggerMethodFailure(varValue, e);
			throw (e);
		}
		return element;
	}

	/*---------------****byIdMethod****---------------*/
	public static WebElement byIdMethod(final TestingStep testingStep) throws Exception {

		String varValue = testingStep.getStrObjIdentifier();
		try {
			element = driverUtils.findElement(By.id(varValue));
			loggerMethodSucess(varValue);
		} catch (Exception e) {
			loggerMethodFailure(varValue, e);
			throw (e);
		}
		return element;
	}

	/*---------------****byClassNameMethod****---------------*/
	public static WebElement byClassNameMethod(final TestingStep testingStep) throws Exception {

		final String varValue = testingStep.getStrObjIdentifier();
		try {
			element = driverUtils.findElement(By.className(varValue));
			loggerMethodSucess(varValue);
		} catch (Exception e) {
			loggerMethodFailure(varValue, e);
			throw (e);
		}
		return element;
	}

	public static WebElement byXpathMethod(String varValue) throws Exception {
		try {
			element = driverUtils.findElement(By.xpath("//" + varValue));
			loggerMethodSucess(varValue);
		} catch (Exception e) {
			loggerMethodFailure("Object is not found on Page", e);
			throw (e);
		}
		return element;
	}

	/*---------------****byXpathMethod****---------------*/
	public static WebElement byXpathMethod(final TestingStep testingStep) throws Exception {

		return byXpathMethod(testingStep.getStrObjIdentifier());
	}

	/*---------------****byCssPathMethod****---------------*/
	public static WebElement byCssPathMethod(final TestingStep testingStep) throws Exception {

		String varValue = testingStep.getStrObjIdentifier();
		try {
			element = driverUtils.findElement(By.cssSelector(varValue));
			loggerMethodSucess(varValue);
		} catch (Exception e) {
			loggerMethodFailure(varValue, e);
			throw (e);
		}
		return element;
	}

	/*---------------****byNameMethod****---------------*/
	public static WebElement byNameMethod(final TestingStep testingStep) throws Exception {

		String varValue = testingStep.getStrObjIdentifier();
		try {
			element = driverUtils.findElement(By.name(varValue));
			loggerMethodSucess(varValue);
		} catch (Exception e) {
			loggerMethodFailure(varValue, e);
			throw (e);
		}
		return element;
	}

	/*---------------****byPartialLinkTextMethod****---------------*/
	public static WebElement byPartialLinkTextMethod(final TestingStep testingStep) throws Exception {

		String varValue = testingStep.getStrObjIdentifier();
		try {
			element = driverUtils.findElement(By.partialLinkText(varValue));
			loggerMethodSucess(varValue);
		} catch (Exception e) {
			loggerMethodFailure(varValue, e);
			throw (e);
		}
		return element;
	}

	/*---------------****byTagNameMethod****---------------*/
	public static WebElement byTagNameMethod(final TestingStep testingStep) throws Exception {

		final String varValue = testingStep.getStrObjIdentifier();
		try {
			element = driverUtils.findElement(By.tagName(varValue));
			loggerMethodSucess(varValue);
		} catch (Exception e) {
			loggerMethodFailure(varValue, e);
			throw (e);
		}
		return element;
	}

	/*---------------****closeLatestBrowser****---------------*/
	public static void closeLatestBrowser(String beforePopup) throws Exception {
		try {
			// Switch to new window opened
			for (String winHandle : driverUtils.getWebDriver_existing().getWindowHandles()) {
				driverUtils.getWebDriver_existing().switchTo().window(winHandle);

			}
			// Perform the actions on new window
			driverUtils.getWebDriver_existing().close(); // this will close new opened window
			Thread.sleep(500);
			// switch back to main window using this code
			driverUtils.getWebDriver_existing().switchTo().window(beforePopup);
			loggerMethodSucess(beforePopup);
		} catch (Exception e) {
			loggerMethodFailure(beforePopup, e);
			throw (e);
		}
	}

	/*---------------****closeLatestBrowser****---------------*/
	public static void switchToLatestBrowser(String beforePopup) throws Exception {
		try {
			// Switch to new window opened
			for (String winHandle : driverUtils.getWebDriver_existing().getWindowHandles()) {
				driverUtils.getWebDriver_existing().switchTo().window(winHandle);
			}
			loggerMethodSucess(beforePopup);
		} catch (Exception e) {
			loggerMethodFailure(beforePopup, e);
			throw (e);
		}
	}

	/*---------------****closeLatestBrowser****---------------*/
	public static void switchToHomeBrowser(String beforePopup) throws Exception {
		try {
			driverUtils.getWebDriver_existing().switchTo().window(beforePopup);
			loggerMethodSucess(beforePopup);
		} catch (Exception e) {
			loggerMethodFailure(beforePopup, e);
			throw (e);
		}
	}

	/*---------------****closeLatestBrowser****---------------*/
	public static void closeLatestBrowser(final TestingStep testingStep) throws Exception {

		closeLatestBrowser(testingStep.getBeforePopUp());
	}

	/*---------------****closeLatestBrowser****---------------*/
	public static void switchToLatestBrowser(final TestingStep testingStep) throws Exception {

		switchToLatestBrowser(testingStep.getBeforePopUp());
	}

	/*---------------****closeLatestBrowser****---------------*/
	public static void switchToHomeBrowser(final TestingStep testingStep) throws Exception {

		switchToHomeBrowser(testingStep.getBeforePopUp());
	}

	/*---------------****threadSleep****---------------*/
	public static void threadSleep(final TestingStep testingStep) throws Exception {

		threadSleep(testingStep.getStrArgs());

	}

	public static void threadSleep(String varValue) throws Exception {
		try {
			Thread.sleep(Integer.parseInt(varValue.replace(".0", "")));
			loggerMethodSucess("threadSleep");
		} catch (Exception e) {
			loggerMethodFailure("threadSleep", e);
			throw (e);
		}
	}

	/*---------------****waitForElement****---------------*/
	public static List<WebElement> waitForElement(final TestingStep testingStep) throws Exception {

		final String varValue = loadExcelInputs(testingStep);
		return waitForElement(varValue);

	}

	/*---------------****waitForElement****---------------*/
	public static List<WebElement> waitForElement(String varValue) throws Exception {
		String[] strElem = null;
		List<WebElement> myDynamicElement = null;
		try {
			if (varValue.contains(";;")) {
				strElem = varValue.split(";;");
				Wait<WebDriver> varwait = new FluentWait<WebDriver>(driverUtils.getWebDriver_existing())
						.withTimeout(Integer.parseInt(strElem[1]), TimeUnit.SECONDS).pollingEvery(5, TimeUnit.SECONDS)
						.ignoring(NoSuchElementException.class);

				myDynamicElement = varwait
						.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//" + strElem[0])));

			} else {
				Wait<WebDriver> varwait = new FluentWait<WebDriver>(driverUtils.getWebDriver_existing()).withTimeout(30, TimeUnit.SECONDS)
						.pollingEvery(5, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);

				myDynamicElement = varwait
						.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//" + varValue)));

			}

			loggerMethodSucess("waitForElement");
			return myDynamicElement;
		} catch (Exception e) {
			loggerMethodFailure("waitForElement", e);
			throw (e);
		}
	}

	/*---------------****getAllElements****---------------*/
	public static List<WebElement> getAllElements(String varValueParent, String varValueChild) throws Exception {
		List<WebElement> descriptionEle = null;
		try {
			WebElement parentEle = byXpathMethod(varValueParent);		
			descriptionEle = parentEle.findElements(By.tagName(varValueChild));
			loggerMethodSucess(varValueParent + varValueChild);
		} catch (Exception e) {
			loggerMethodFailure(varValueParent + varValueChild, e);
			throw (e);
		}
		return descriptionEle;
	}

	/*---------------****getParentElements****---------------*/
	public static List<WebElement> getParentElements(String varValueParent) throws Exception {

		List<WebElement> parentEle = null;
		try {
			parentEle = driverUtils.findElements(By.xpath("//" + varValueParent));
			if (parentEle.size() != 0) {
				loggerMethodSucess(varValueParent);
			}

			return parentEle;

		} catch (Exception e) {
			loggerMethodFailure(varValueParent, e);
			throw (e);
		}
	}

	/*---------------****getChildElementsandClickByIndex****---------------*/
	public static WebElement getChildElementsandClickByIndex(String varValue) throws Exception {
		List<WebElement> descriptionEle = null;
		WebElement elem = null;
		int iSize = 0;
		try {
			if (varValue.contains(";;")) {
				String[] spropertyVal = varValue.split(";;");
				String varValueParent = spropertyVal[0];
				int elemIndex = Integer.parseInt(spropertyVal[1]);
				elem = getChildElementsandClickByIndex(varValueParent, elemIndex);
			} else {
				loggerMethodFailure("getChildElementsandClickByIndex :: PASS THE Correct PARAMETERS In Excel",
						new Exception("Wrong Parameters"));
			}
			return elem;
		} catch (Exception e) {
			loggerMethodFailure("getChildElementsandClickByIndex || " + iSize, e);
			throw (e);
		}
	}

	private static WebElement getChildElementsandClickByIndex(final String varValueParent, final int elemIndex)
			throws Exception {

		WebElement elem = null;
		List<WebElement> descriptionEle = CommonFindObjects.getParentElements(varValueParent);
		int iSize = descriptionEle.size();
		if (iSize != 0) {
			elem = descriptionEle.get(elemIndex);
			elem.click();
			Thread.sleep(500);
			loggerMethodSucess("getChildElementsandClickByIndex  || " + varValueParent + " Element is clicked.");
		} else {
			throw new Exception("Element not Found .Size is 0");
		}
		return elem;
	}

	public static WebElement getChildElementsandClickByIndex(final TestingStep testingStep) throws Exception {

		final String varValue = loadExcelInputs(testingStep);
		return getChildElementsandClickByIndex(varValue);
	}

	/*---------------****getMenuItemClickIndex****---------------*/
	public static void getMenuItemClickIndex(final TestingStep testingStep) throws Exception {

		final String varValue = loadExcelInputs(testingStep);

		List<WebElement> descriptionEle = null;
		WebElement elem = null;
		String strElem = null;
		String[] spropertyVal = null;
		String varValueP = null;
		String varValueC = null;
		int iSize = 0;
		int elemIndexP = 0;
		int elemIndexC = 0;
		try {
			if (varValue.contains(";;")) {
				spropertyVal = varValue.split(";;");
				varValueP = spropertyVal[0];
				elemIndexP = Integer.parseInt(spropertyVal[1]);
				varValueC = spropertyVal[2];
				elemIndexC = Integer.parseInt(spropertyVal[3]);

				actions.moveToElement(driverUtils.findElements(By.xpath("//" + varValueP)).get(elemIndexP)).click().build()
						.perform();

				actions.moveToElement(driverUtils.findElements(By.xpath("//" + varValueC)).get(elemIndexC)).click().build()
						.perform();
				loggerMethodSucess("getMenuItemClickIndex  || "
						+ driverUtils.findElements(By.xpath("//" + varValueC)).get(elemIndexC).getText()
						+ "Element is clicked.");
			} else {
				loggerMethodFailure(
						"getMenuItemClickIndex :: PASS THE Correct Div for Child and Parent with 0 or Index specified",
						new Exception("Wrong Parameters"));
			}
		} catch (Exception e) {
			loggerMethodFailure("getMenuItemClickIndex || " + iSize, e);
			throw (e);
		}
	}

	/*---------------****sortToCheck****---------------*/
	public static WebElement sortToCheck(String args) throws Exception {
		List<Integer> iTR = new ArrayList<Integer>();
		List<Double> iLFR = new ArrayList<Double>();
		List<Double> iMR = new ArrayList<Double>();
		List<Integer> iTRUnSorted = new ArrayList<Integer>();
		List<Double> iLFRUnSorted = new ArrayList<Double>();
		List<Double> iMRUnSorted = new ArrayList<Double>();
		/*
		 * No of Element to be ignored should come here
		 * 
		 */
		String[] strArray = getElementListOnPagePL("");
		int countj = 7;
		for (int count = 0; count <= strArray.length; count++) {
			iLFRUnSorted.add(Double.parseDouble(strArray[countj]));
			countj = countj + 1;
			iTRUnSorted.add(Integer.parseInt(strArray[countj]));

			countj = countj + 1;
			iMRUnSorted.add(Double.parseDouble(strArray[countj]));

			countj = countj + 1;
			count = countj;

		}
		
		System.out.println(iLFRUnSorted + "," + iTRUnSorted + "," + iMRUnSorted);
		Collections.sort(iLFRUnSorted);
		Collections.sort(iLFRUnSorted);
		Collections.sort(iLFRUnSorted);

		getChildElementsandClickByIndex("h4[@class='sort-menu__text'];;0");
		waitForElement("h3[@class='result-info__header']");
		strArray = getElementListOnPagePL("");
		countj = 7;
		int count = 0;
		for (; count <= strArray.length; count++) {
			iLFR.add(Double.parseDouble(strArray[countj]));
			countj = countj + 1;
			countj = countj + 1;
			countj = countj + 1;
			count = countj;

		}
		System.out.println(iLFR + "," + iLFRUnSorted);

		boolean b = iLFR.equals(iLFRUnSorted);

		System.out.println(b);

		getChildElementsandClickByIndex("h4[@class='sort-menu__text'];;1");

		waitForElement("h3[@class='result-info__header']");
		strArray = getElementListOnPagePL("");
		countj = 7;
		count = 0;
		for (; count <= strArray.length; count++) {
			countj = countj + 1;
			iTR.add(Integer.parseInt(strArray[countj]));

			countj = countj + 1;

			countj = countj + 1;
			count = countj;

		}
		System.out.println(iTR + "," + iTRUnSorted);

		b = iTR.equals(iTRUnSorted);

		if (b == false) {
			Collections.reverse(iLFRUnSorted);
			b = iTR.equals(iTRUnSorted);
		}

		System.out.println(b);

		getChildElementsandClickByIndex("h4[@class='sort-menu__text'];;2");
		waitForElement("h3[@class='result-info__header']");
		strArray = getElementListOnPagePL("");
		countj = 7;
		count = 0;
		for (; count <= strArray.length; count++) {
			countj = countj + 1;

			countj = countj + 1;
			iMR.add(Double.parseDouble(strArray[countj]));

			countj = countj + 1;
			count = countj;

		}
		System.out.println(iMR + "," + iMRUnSorted);

		b = iMR.equals(iMRUnSorted);

		System.out.println(b);
		return element;
	}

	public static String[] getElementListOnPagePL(String varValueParent) throws Exception {
		List<WebElement> parentEle = null;
		String strcompanyNames = "::";
		String[] strCompanyNameArray = null;
		String elemText = "::";
		String[] strArray = null;
		try {
			List<WebElement> companyNames = driverUtils.findElements(By.xpath("//div//img"));
			List<WebElement> elemValues = driverUtils.findElements(By.className("result-info__header"));
			for (WebElement element : companyNames) {
				String strCompanyAttr = element.getAttribute("data-company");
				if (strCompanyAttr != null)
					strcompanyNames = strcompanyNames + strCompanyAttr + "::";
			}
			strCompanyNameArray = strcompanyNames.split("::");
			System.out.println(strcompanyNames);
			for (WebElement element : elemValues) {
				elemText = elemText + element.getText() + "::";
			}

			if (elemText.contains("HK$"))
				elemText = elemText.replace("HK$", "");
			if (elemText.contains("SGD"))
				elemText = elemText.replace("SGD", "");
			if (elemText.contains("%"))
				elemText = elemText.replace("%", "");
			if (elemText.contains("."))
				elemText = elemText.replace(",", "");

			elemText = replaceNewLineChar(elemText);
			elemText = elemText.replaceAll("n", "").replaceAll(" ", "").trim();
			strArray = elemText.split("::");
			System.out.println(elemText);

		} catch (Exception e) {
			loggerMethodFailure(varValueParent + varValueParent, e);
			throw (e);
		}
		return strArray;

	}

	public static WebElement sortToCheck(final TestingStep testingStep) throws Exception {

		return sortToCheck(testingStep.getStrArgs());
	}

	/*---------------****replaceNewLineChar****---------------*/
	public static String replaceNewLineChar(String str) {
		try {
			if (!str.isEmpty()) {
				return str.replaceAll("\n", "\\n").replaceAll("\n\r", "\\n").replaceAll(System.lineSeparator(), "\\n");
			}
			return str;
		} catch (Exception e) {
			return str;
		}
	}

	/*---------------****clickandVerifyFooter****---------------*/
	public static void clickandCheckChildObjects(final TestingStep testingStep) throws Exception {

		final String varValue = testingStep.getStrArgs();
		List<WebElement> descriptionEle = null;
		Exception excep = new Exception("Element not listed in switch Case.");
		int i = 0;
		int menuItem = 0;
		String URL = null;
		String strLink = null;
		String bodyText = null;
		String strActual = null;
		String strExpected = null;
		String alertText = null;
		int indexofString = 0;
		try {
			Boolean flag = false;
			String[] spropertyVal = varValue.split(";;");
			i = 0;
			for (; i < Integer.parseInt(spropertyVal[2]); i++) {
				URL = "";
				strLink = "";
				bodyText = "";
				strActual = "";
				strExpected = "";
				alertText = "";
				descriptionEle = CommonFindObjects.getAllElements(spropertyVal[0], spropertyVal[1]);
				strLink = descriptionEle.get(i).getAttribute("href");
				String strClassElem = descriptionEle.get(i).getAttribute("class");
				String strText = descriptionEle.get(i).getText();
				System.out.println(strLink + strText + strClassElem + flag + strLink.length());
				if (spropertyVal.length == 4) {
					if (strLink.contains(Constant.URL) && spropertyVal[3].toUpperCase().equals("HOME")) {
						strText = "Home";
					}
					if (strLink.contains("/credit-card") && spropertyVal[3].toUpperCase().equals("HEADCC")) {
						menuItem = 1;
					}
					if (spropertyVal[3].toUpperCase().equals("HEADPL")) {
						menuItem = 2;
					}
					if (spropertyVal[3].toUpperCase().equals("HEADBLOG")) {
						menuItem = 3;
					}
					if (spropertyVal[3].toUpperCase().equals("HEADHELP")) {
						menuItem = 4;
					}
					if (strLink.contains("/credit-card") && spropertyVal[3].toUpperCase().equals("HEADCC") && i == 0) {
						strText = "Credit Cards";
					}
					if (strLink.contains("credit-card/best-deals") && (spropertyVal[3].toUpperCase().equals("BODY")
							|| spropertyVal[3].toUpperCase().equals("HEADCC"))) {
						strText = "Credit Card Comparison";
					}
					if (strLink.contains("credit-card/guide") && (spropertyVal[3].toUpperCase().equals("HEADCC")
							|| spropertyVal[3].toUpperCase().equals("BODY"))) {
						strText = "Credit Card Guide";
					}
					if (strLink.contains("credit-card/faq") && spropertyVal[3].toUpperCase().equals("HEADCC")) {
						strText = "Credit Card FAQs";
					}
					if (strLink.contains("credit-card/glossary") && spropertyVal[3].toUpperCase().equals("HEADCC")) {
						strText = "Credit Card Glossary";
					}
					if (strLink.contains("credit-card/banks") && spropertyVal[3].toUpperCase().equals("HEADCC")) {
						strText = "Banks";
					}
					if (strLink.contains("/personal-loan") && spropertyVal[3].toUpperCase().equals("HEADPL")
							&& i == 0) {
						strText = "Personal Loans";
					}
					if (strLink.contains("personal-loan/instalment") && (spropertyVal[3].toUpperCase().equals("BODY")
							|| spropertyVal[3].toUpperCase().equals("HEADPL"))) {
						strText = "Personal Loan Comparison";
					}
					if (strLink.contains("personal-loan/guide") && (spropertyVal[3].toUpperCase().equals("BODY")
							|| spropertyVal[3].toUpperCase().equals("HEADPL"))) {
						strText = "Personal Loan Guide";
					}
					if (strLink.contains("personal-loan/faq") && (spropertyVal[3].toUpperCase().equals("BODY")
							|| spropertyVal[3].toUpperCase().equals("HEADPL"))) {
						strText = "Personal Loan FAQs";
					}
					if (strLink.contains("personal-loan/glossary") && (spropertyVal[3].toUpperCase().equals("BODY")
							|| spropertyVal[3].toUpperCase().equals("HEADPL"))) {
						strText = "Personal Loan Glossary";
					}
					if (strLink.contains("personal-loan/providers") && (spropertyVal[3].toUpperCase().equals("BODY")
							|| spropertyVal[3].toUpperCase().equals("HEADPL"))) {
						strText = "Providers";
					}
					if (strLink.contains("/help") && (spropertyVal[3].toUpperCase().equals("BODY")
							|| spropertyVal[3].toUpperCase().equals("HEADHELP"))) {
						strText = "Help";
					}
					if (strLink.contains("/contact-us") && (spropertyVal[3].toUpperCase().equals("BODY")
							|| spropertyVal[3].toUpperCase().equals("HEADHELP"))) {
						strText = "Contact Us";
					}
					if (strLink.contains("/about-us") && (spropertyVal[3].toUpperCase().equals("BODY")
							|| spropertyVal[3].toUpperCase().equals("HEADHELP"))) {
						strText = "About Us";
					}
					if (strLink.contains("/product-guide") && (spropertyVal[3].toUpperCase().equals("BODY")
							|| spropertyVal[3].toUpperCase().equals("HEADHELP"))) {
						strText = "Product Guide";
					}
					if (strLink.contains("blog") && (spropertyVal[3].toUpperCase().equals("BODY")
							|| spropertyVal[3].toUpperCase().equals("BLOG")
							|| spropertyVal[3].toUpperCase().equals("HEADBLOG"))) {
						strText = "Blog";
					}
					if (strLink.contains("blog/") && spropertyVal[3].toUpperCase().equals("BLOG")) {
						strText = "Blog Body";
					}
				}

				if (strClassElem.contains("socialmedia") || strClassElem.contains("fb") || strLink.contains("facebook")
						|| strLink.contains("google")) {
					descriptionEle.get(i).click();
					Thread.sleep(2000);
					CommonFindObjects.switchToLatestBrowser(testingStep.getBeforePopUp());
					URL = driverUtils.getCurrentUrl();
					CommonFindObjects.switchToHomeBrowser(testingStep.getBeforePopUp());
					if (URL.contains("blog")) {
						Assert.assertEquals(URL, strLink + "/");
					} else {
						indexofString = strLink.indexOf("com/");
						strLink = strLink.substring(0, indexofString);
						Assert.assertEquals(URL.contains(strLink), true);
					}
					flag = true;
					CommonFindObjects.closeLatestBrowser(testingStep.getBeforePopUp());
				}
				if (spropertyVal[0].contains("flag")) {
					descriptionEle.get(i).click();
					Thread.sleep(1000);
					URL = driverUtils.getCurrentUrl();
					Assert.assertEquals(URL, strLink);
					driverUtils.getWebDriver_existing().get(Constant.URL);
					flag = true;
				}
				if (strLink != null && !strLink.equals("javascript:void(0);") && flag == false) {
					// to Expand menu list
					if (menuItem != 0) {
						actions.moveToElement(
								driverUtils.findElements(By.xpath("//a[@class='navi-block__url js-navi-block__url']"))
										.get(menuItem))
								.click().build().perform();
					}
					// Skip the click in Case of Blog as it's already done in
					// above if case.
					if (menuItem != 3) {
						descriptionEle.get(i).click();
					}
					Thread.sleep(3000);
					URL = driverUtils.getCurrentUrl();
					bodyText = driverUtils.findElement(By.tagName("body")).getText().toUpperCase();
					switch (strText.trim()) {
					case "Credit Cards":
						strExpected = "What kind of credit card do you need?".toUpperCase();
						break;
					case "Credit Card Comparison":
						strExpected = "The Best Credit Card Deals".toUpperCase();
						break;
					case "Credit Card Guide":
						strExpected = "Guide to Credit Cards in Singapore".toUpperCase();
						break;
					case "Credit Card FAQs":
						strExpected = "FAQs About Credit Cards in".toUpperCase();
						break;
					case "Banks":
						strExpected = "Our Credit Card Partners".toUpperCase();
						break;
					case "Credit Card Glossary":
						strExpected = "Credit Card Glossary of Terms".toUpperCase();
						break;
					case "Personal Loans":
						strExpected = "Need a personal loan right this minute?".toUpperCase();
						break;
					case "Personal Loan Comparison":
						strExpected = "Achieve Your Goals with the Help of a Personal Loan".toUpperCase();
						break;
					case "Personal Loan Guide":
						strExpected = "SingSaver.com.sg’s Guide to Personal Loans".toUpperCase();
						break;
					case "Personal Loan FAQs":
						strExpected = "FAQs About Personal Loans in Singapore".toUpperCase();
						break;
					case "Personal Loan Glossary":
						strExpected = "Personal Loans Glossary of Terms".toUpperCase();
						break;
					case "Providers":
						strExpected = "our personal loan providers".toUpperCase();
						break;
					case "Blog":
						Thread.sleep(1000);
						bodyText = driverUtils.findElement(By.tagName("body")).getText().toUpperCase();
						strExpected = "Ways to Save".toUpperCase();
						strLink = strLink + "/";
						if (isAlertPresent())
							alertText = closeAlertAndGetItsText();
						break;
					case "Blog Body":
						Thread.sleep(1000);
						bodyText = driverUtils.findElement(By.tagName("body")).getText().toUpperCase();
						strExpected = "Ways to Save".toUpperCase();
						if (isAlertPresent())
							alertText = closeAlertAndGetItsText();
						break;
					case "Help":
						strExpected = "Help Hub".toUpperCase();
						break;
					case "Contact Us":
						strExpected = "Do you have any questions? Send us a message!".toUpperCase();
						break;
					case "About Us":
						strExpected = "Our Mission".toUpperCase();
						break;
					case "Product Guide":
						strExpected = "How to find the credit card for you?".toUpperCase();
						break;
					case "Guide":
						strExpected = "comprehensive guides to financial products".toUpperCase();
						break;
					case "Affiliates":
						strExpected = "Affiliate Program".toUpperCase();
						break;
					case "Press":
						strExpected = "For any interview requests or questions".toUpperCase();
						break;
					case "Careers":
						strExpected = "Why join us?".toUpperCase();
						break;
					case "Privacy Policy":
						strExpected = "Information we collect".toUpperCase();
						break;
					case "Sitemap":
						strExpected = "Aloitus".toUpperCase();
						break;
					case "Terms and Conditions":
						strExpected = "terms and conditions stated here".toUpperCase();
						break;
					case "Terms of Use":
						strExpected = "terms and conditions stated here".toUpperCase();
						break;
					case "Home":
						strExpected = "Financial Comparison Site".toUpperCase();
						break;
					default:
						loggerMethodFailure("Deafult Case for Switch.Inner Text Not matching" + varValue, excep);
						flag = true;
						break;
					}
					if (bodyText != null || bodyText != "") {
						indexofString = bodyText.indexOf(strExpected);
						strActual = bodyText.substring(indexofString, indexofString + strExpected.length());
					}
					Assert.assertEquals(URL, strLink);
					Assert.assertEquals(strActual, strExpected);
					driverUtils.getWebDriver_existing().get(Constant.URL);
					Thread.sleep(1000);
				}
				if (URL != "" || URL != null)
					loggerMethodSucess("clickandVerifyObjects || " + "\n" + "|||||||Actual: " + URL + "||" + strActual
							+ "\n" + "|||||||" + "Expected :" + strLink + "||" + strExpected);
				else
					loggerMethodSucess("clickandVerifyObjects || " + "This is not a valid link");
			}
		} catch (Exception e) {
			loggerMethodFailure("clickandVerifyFooter || " + "\n" + "|||||||Actual: " + URL + "||" + strActual + "\n"
					+ "|||||||" + "Expected :" + strLink + "||" + strExpected, e);
			throw (e);
		}
	}

	/*---------------****getChildElementsClickAll****---------------*/
	public static WebElement getChildElementsClickAll(final TestingStep testingStep) throws Exception {

		final String varValue = loadExcelInputs(testingStep);

		List<WebElement> descriptionEle = null;
		WebElement elem = null;
		String strElem = null;
		String varValueflag = "";
		int iSize = 0;
		try {
			if (varValue.contains(";;")) {
				String[] spropertyVal = varValue.split(";;");
				String varValueParent = spropertyVal[0];
				String varValueBody = spropertyVal[1];
				descriptionEle = CommonFindObjects.getParentElements(varValueParent);
				iSize = descriptionEle.size();
				if (iSize != 0) {
					int i = 0;
					for (; i < iSize; i++) {
						descriptionEle = CommonFindObjects.getParentElements(varValueParent);
						elem = descriptionEle.get(i);
						strElem = elem.getText();
						elem.click();
						waitForElement(varValueBody);

						loggerMethodSucess("getChildElementsClickAll  || " + strElem + "Element is clicked.");
					}

				} else {
					throw new Exception("Element not Found .Size is 0");
				}
			} else {
				loggerMethodFailure("getChildElementsClickAll :: PASS THE Correct PARAMETERS In Excel",
						new Exception("Wrong Parameters"));
			}
			return elem;
		} catch (Exception e) {
			loggerMethodFailure("getChildElementsClickAll || " + iSize, e);
			throw (e);
		}
	}

	/*---------------****getChildElementsClickByIndexCheck****---------------*/
	public static WebElement getChildElementsClickByIndexCheck(final TestingStep testingStep) throws Exception {

		final String varValue = loadExcelInputs(testingStep);

		List<WebElement> descriptionEle = null;
		WebElement elem = null;
		String strElem = null;
		String varValueflag = "";
		int iSize = 0;
		try {
			if (varValue.contains(";;")) {
				String[] spropertyVal = varValue.split(";;");
				String varValueParent = spropertyVal[0];
				int varValueIndex = Integer.parseInt(spropertyVal[1]);
				String varValueBody = spropertyVal[2];
				descriptionEle = CommonFindObjects.getParentElements(varValueParent);
				iSize = descriptionEle.size();
				if (iSize != 0) {

					descriptionEle = CommonFindObjects.getParentElements(varValueParent);
					elem = descriptionEle.get(varValueIndex);
					strElem = elem.getText();
					elem.click();
					waitForElement(varValueBody);
					Thread.sleep(500);
					loggerMethodSucess("getChildElementsClickByIndexCheck  || " + strElem + "Element is clicked.");

				} else {
					throw new Exception("Element not Found .Size is 0");
				}
			} else {
				loggerMethodFailure("getChildElementsClickByIndexCheck :: PASS THE Correct PARAMETERS In Excel",
						new Exception("Wrong Parameters"));
			}
			return elem;
		} catch (Exception e) {
			loggerMethodFailure("getChildElementsClickByIndexCheck || " + iSize, e);
			throw (e);
		}
	}

	public static void openURL(final TestingStep testingStep){
		
		driverUtils.openURL(testingStep.getStrArgs());
	}
	
	/*---------------****getChildElementsCheckNewTab****---------------*/
	public static void getChildElementsCheckNewTab(final TestingStep testingStep) throws Exception {

		final String varValue = loadExcelInputs(testingStep);

		List<WebElement> descriptionEle = null;
		List<WebElement> descriptionAttr = null;
		List<WebElement> popupAttr = null;
		String[] spropertyVal = null;
		String varValueParent = null;
		String varValueSize = null;
		String varValueAttr = "data-company";
		String strCurrentURL = "";
		String strOpenedURL = "";
		String strBodytext = "";
		int iSize = 0;
		int iSizeimplicit = 0;

		boolean validLink = true;
		try {
			if (varValue.contains(";;")) {
				spropertyVal = varValue.split(";;");
				varValueParent = spropertyVal[0];
				if (spropertyVal.length > 1)
					varValueSize = spropertyVal[1];
				if (spropertyVal.length > 2)
					varValueAttr = spropertyVal[2];
			}

			descriptionEle = CommonFindObjects.getParentElements(varValueParent);
			iSizeimplicit = descriptionEle.size();
			iSize = Integer.parseInt(varValueSize);
			int max = (iSize < iSizeimplicit) ? iSize : iSizeimplicit;

			try {
				if (driverUtils.getCurrentUrl().contains(".sg") || driverUtils.getCurrentUrl().contains(".tw")
						|| driverUtils.getCurrentUrl().contains(".fi")) {
					driverUtils.findElement(By.xpath("//div[@class='countContainer']/i")).click();
				} else if (driverUtils.getCurrentUrl().contains(".hk")) {
					driverUtils.findElement(By.xpath("//div[@title='關閉']")).click();
				}

			} catch (Exception e) {
				loggerMethodSucess("getChildElementsCheckNewTab || Popup is not present");
			}

			if (max == 0)
				loggerMethodSucess("getChildElementsCheckNewTab || " + "\n" + "||||||| :" + "NO APPLY NOW BUTTON.");
			else {
				int i = 0;
				System.out.println(max);
				for (; i < max; i++) {
					descriptionEle = getParentElements(varValueParent);
					descriptionAttr = (varValueAttr.contains("@")) ? getParentElements(varValueAttr) : null;
					String strRefURL = (descriptionEle.size() != 0 && !varValueAttr.contains("@"))
							? descriptionEle.get(i).getAttribute(varValueAttr)
							: descriptionAttr.get(i).getAttribute("src");
					strRefURL = (strRefURL != null && !varValueAttr.contains("@"))
							? strRefURL.trim().replaceAll(" ", "")
							: strRefURL.replaceAll("(?ius).*/(.*?):?(.jpg|.png)", "$1").substring(0, 4);

					if (descriptionEle.get(i).isDisplayed()) {
						js.executeScript("arguments[0].scrollIntoView();", descriptionEle.get(i));
						js.executeScript("arguments[0].click()", descriptionEle.get(i));
						strCurrentURL = driverUtils.getCurrentUrl();
						switchToLatestBrowser(testingStep.getBeforePopUp());
						strOpenedURL = driverUtils.getCurrentUrl();
						strBodytext = driverUtils.findElement(By.tagName("body")).getText().toUpperCase();
						if (!strOpenedURL.equals(strCurrentURL)) {
							closeLatestBrowser(testingStep.getBeforePopUp());
							switchToLatestBrowser(testingStep.getBeforePopUp());
							String strOpenedURL1 = driverUtils.getCurrentUrl();
							if (strOpenedURL1.contains("https://www.citibank.com.sg/")) {
								closeLatestBrowser(testingStep.getBeforePopUp());
							}
						} else if (strCurrentURL.contains(".hk") || strCurrentURL.contains("hongkong")) {
							popupAttr = driverUtils.findElements(By.xpath("//div[@class='description-text']/img"));
							strOpenedURL = popupAttr.get(0).getAttribute("src");
							strOpenedURL = strOpenedURL.replaceAll("(?ius).*/(.*?):?(.jpg|.png)", "$1").substring(0, 4);
							strOpenedURL = (strOpenedURL.toUpperCase().contains("AMER")) ? "AMERICANEXPRESS"
									: strOpenedURL;
							driverUtils.findElement(By.xpath("//button[@ng-click='cancel()']")).click();
						} else if (strCurrentURL.contains(".comparaguru")) {

							driverUtils.findElement(By.id("firstname")).sendKeys("test");
							if (driverUtils.findElement(By.id("email")).getAttribute("value").equals(""))
								driverUtils.findElement(By.id("email")).sendKeys("test@test.co");
							if (driverUtils.findElement(By.id("phone")).getAttribute("value").equals(""))
								driverUtils.findElement(By.id("phone")).sendKeys("9999999999");
							popupAttr = driverUtils.findElements(By.xpath("//div[@id='modal-header-logo']/img"));
							strOpenedURL = popupAttr.get(0).getAttribute("src");
							strOpenedURL = strOpenedURL.replaceAll("(?ius).*/(.*?):?(.jpg|.png)", "$1");
							driverUtils.findElement(By.xpath("//div[@class='cag-modal__content--button-submit']")).click();
							switchToLatestBrowser(testingStep.getBeforePopUp());
							strBodytext = driverUtils.findElement(By.tagName("body")).getText().toUpperCase();
							closeLatestBrowser(testingStep.getBeforePopUp());
							Thread.sleep(1500);
						}
						strRefURL = (strRefURL.toUpperCase().contains("POSB")) ? "POSB" : strRefURL;
						strRefURL = (strRefURL.toUpperCase().contains("AMEX")
								&& !strCurrentURL.contains(".comparaguru")) ? "AMERICANEXPRESS" : strRefURL;
						strRefURL = (strRefURL.toUpperCase().contains("SC")) ? "SC" : strRefURL;
						strRefURL = (strRefURL.toUpperCase().contains("BEA")) ? "BEA" : strRefURL;
						strRefURL = (strRefURL.toUpperCase().contains("DBS")) ? "DBS" : strRefURL;
						strRefURL = (strRefURL.toUpperCase().contains("HANG")) ? "HANGSENG" : strRefURL;
						strRefURL = (strRefURL.toUpperCase().contains("CCB")) ? "CCB" : strRefURL;
						strRefURL = (strRefURL.toUpperCase().contains("TC")) ? "TC" : strRefURL;
						strRefURL = (strRefURL.toUpperCase().contains("4644")) ? "ANZ" : strRefURL;
						strRefURL = (strRefURL.toUpperCase().contains("ANZ")) ? "ANZ" : strRefURL;
						strRefURL = (strRefURL.toUpperCase().contains("AMER")) ? "AMERICANEXPRESS" : strRefURL;

					}

					if ((strOpenedURL.toLowerCase().contains(strRefURL.toLowerCase())) && validLink == true
							&& strBodytext != "" && strBodytext != null
							&& !strBodytext.toLowerCase().contains("page not found")) {
						loggerMethodSucess("getChildElementsCheckNewTab || " + "\n" + "|||||||Actual: " + strOpenedURL
								+ "\n" + "CONTAINS" + "\n" + "|||||||" + "Expected :" + strRefURL);
					} else {

						loggerMethodFailure("getChildElementsCheckNewTab || " + "\n" + "|||||||Actual: " + strOpenedURL
								+ "\n" + "DOES NOT CONTAINS" + "\n" + "|||||||" + "Expected :" + strRefURL + "||\n"
								+ strBodytext, new Exception("URL && Body Text is not same as expected"));

						throw (new Exception("URL && Body Text is not same as expected"));
					}
					js.executeScript("scroll(0, 0);");
				}
			}
		} catch (Exception e) {
			loggerMethodFailure("getChildElementsCheckNewTab || ", e);
			e.printStackTrace();
			throw (e);

		}
	}

	/*---------------****goToUrl****---------------*/
	public static void goToHomeURL(final TestingStep testingStep) throws Exception {

		final String varValue = testingStep.getStrArgs();
		try {
			driverUtils.getWebDriver_existing().get(Constant.URL);
			Thread.sleep(500);
			loggerMethodSucess("goToHomeURL || " + varValue + "Sucess.");
		} catch (Exception e) {
			loggerMethodFailure("goToHomeURL || " + varValue, e);
			throw (e);
		}
	}

	/*---------------****checkValidLinks****---------------*/
	public static boolean checkValidLinks(final TestingStep testingStep) throws Exception {
		final String varValue = loadExcelInputs(testingStep);
		return checkValidLinks(varValue);
	}

	public static boolean checkValidLinks(String varValue) throws Exception {

		try {
			boolean isValid = false;
			List<WebElement> descriptionEles = getParentElements(varValue);
			if (descriptionEles == null || descriptionEles.size() == 0) {
				System.out.println("<a> tag do not contain href attribute and value");
				System.out.println("----------XXXX-----------XXXX----------XXXX-----------XXXX----------");
				System.out.println();
			}

			for (int i = 0; i < descriptionEles.size(); i++) {
				String strLinks = descriptionEles.get(i).getAttribute("href");
				isValid = getResponseCode(strLinks);
				if (isValid) {
					System.out.println((isValid ? "Valid Link:" : "Broken Link ------> ") + strLinks);
				}

			}
			return isValid;
		} catch (Exception e) {
			Log.error("Exception in checkValidLinks");
			throw (e);
		}
	}

	/*---------------****getResponseCode****---------------*/
	// Function to get response code of link URL.
	// Link URL Is valid If found response code = 200. //Link URL Is Invalid If
	// found response code = 404 or 505.
	public static boolean getResponseCode(String chkurl) {
		boolean validResponse = false;
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpResponse urlresp = client.execute(new HttpGet(chkurl));
			int resp_Code = urlresp.getStatusLine().getStatusCode();
			System.out.println("Response Code Is : " + resp_Code);
			System.out.println("URL Is : " + chkurl);
			if ((resp_Code == 404) || (resp_Code == 505) || (resp_Code == 500)) {
				validResponse = false;
			} else {
				validResponse = true;
			}
		} catch (Exception e) {
		}
		return validResponse;
	}

	/*---------------****isAlertPresent****---------------*/
	public static boolean isAlertPresent() {
		try {
			driverUtils.getWebDriver_existing().switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	/*---------------****closeAlertAndGetItsText****---------------*/
	public static String closeAlertAndGetItsText() {
		boolean acceptNextAlert = true;
		try {
			Alert alert = driverUtils.getWebDriver_existing().switchTo().alert();
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

	/*---------------****loggerMethodSucess****---------------*/
	public static void loggerMethodSucess(String varValue) {
		Log.info("\n" + varValue + " exists. ");
	}

	/*---------------****loggerMethodFailure****---------------*/
	public static void loggerMethodFailure(String varValue, Exception e) {
		Log.error("\n" + varValue + " does not exists. " + e.getMessage());
	}

	public static void loggerMethodFailure(String msg) {
		Log.error("\n" + msg);
	}

	/*---------------****clickChildElementsCheckPopupWin****---------------*/
	public static void clickChildElementsCheckPopupWin(final TestingStep testingStep) throws Exception {

		final String varValue = loadExcelInputs(testingStep);

		List<WebElement> descriptionEle = null;
		String strElem = null;
		int iSize = 0;
		String[] spropertyVal = null;
		String varValueParent = null;
		String varValueSize = null;
		try {
			if (varValue.contains(";;")) {
				spropertyVal = varValue.split(";;");
				varValueParent = spropertyVal[0];
				if (spropertyVal.length > 1)
					varValueSize = spropertyVal[1];
			}
			descriptionEle = driverUtils.findElements(By.xpath("//" + varValueParent));

			if (varValueSize == null) {
				iSize = descriptionEle.size();
			} else {
				iSize = Integer.parseInt(varValueSize) > descriptionEle.size() ? descriptionEle.size()
						: Integer.parseInt(varValueSize);
			}
			if (iSize > 0) {
				final Checker checker = new Checker(driverUtils);
				for (int i = 0; i < iSize; i++) {

					final WebElement applyBtnUrl = descriptionEle.get(i);

					driverUtils.clickButton(applyBtnUrl);

					driverUtils.switchToPopUpWin();

					driverUtils.closeAllPopUpWins();

					loggerMethodSucess("clickChildElementsCheckPopupWin  || " + strElem + "Element is clicked.");
				}
			}

		} catch (Exception e) {
			loggerMethodFailure("clickChildElementsCheckPopupWin || " + iSize, e);
			throw (e);
		}
	}

	
	private static int index = 0;
	private static int resultCounter = 0;

	private static List<String> visited = Lists.newArrayList();

	public static void checkAppForm(final TestingStep testingStep) throws Exception {
		
		PersonalLoanConfig instance = PersonalLoanConfig.fromUrl(testingStep.getURL());
		AppFormConfig appFormConfig = instance.loadAppFormObject(driverUtils);
		System.out.println("Loaded app form config.");
	
		List<Class<?>> clzList = ReflectionUtils.getAllSubClassesInPackage("com.cgg.pl.funnel", SimpleFunnelChecker.class);
		//2. navigate towards funnel page
		int stepNo = 1;
		AppFormStep appForm = new AppFormStep(driverUtils, appFormConfig);
		testingStep.setAppFormStep(appForm);
		do {
			// 3. Check funnel step
			Route route = appForm.getCurrentAppFormStep();
			
			if (stepNo > 1) {
				int counter = 0;
				while (!driverUtils.getCurrentUrl().endsWith(route.getRoute()) && counter < Constant.MAX_REPEAT_TIME) {
					driverUtils.explicitWait(2 * 3000);
					counter++;
				}
			}
			
			runFunnelChecker(testingStep, clzList, appForm);
			
			// 4. fill-in data in current step
			boolean needClickNextBtn = appForm.visitCurrentStep();

			runFunnelChecker(testingStep, clzList, appForm);

			if (needClickNextBtn || driverUtils.isElementPresent(FunnelStep.NEXT_BUTTON)) {
				driverUtils.implicitWait(FunnelStep.NEXT_BUTTON);
				driverUtils.clickButton(FunnelStep.NEXT_BUTTON);
			}

			appForm.setupNextStep();
			driverUtils.explicitWait(3000);
			if (!Checker.isBlank(route.getRoute())) {
				stepNo++;
			}
		} while (!appForm.noMoreSteps());

		driverUtils.explicitWait(6000);

		// 5. check usercity data
		
		checkUserCityData(testingStep, appForm.getUserDataMap());
	}

	public static void runFunnelChecker(final TestingStep testingStep, List<Class<?>> clzList, AppFormStep appForm) {
		
		for (Class<?> clz : clzList) {
			
			SimpleFunnelChecker checker = null;
			if(clz.getName().contains("RequestPayloadChecker")){
				checker = ((SimpleFunnelChecker) ReflectionUtils.invokeConstructor(clz,WebDriverUtils.class, driverUtils, TestCase.class, testingStep.getTestCase()));
			}else{
				 checker = ((SimpleFunnelChecker) ReflectionUtils.invokeConstructor(clz,
						WebDriverUtils.class, driverUtils));
			}
				
			if(checker != null && checker.isEnabled()){
				checker.checkFunnelStep(appForm);	
			}
		}
	}
	
	public static void runFunnelChecker(final TestingStep testingStep, List<Class<?>> clzList, FunnelStep step) {
		
		for (Class<?> clz : clzList) {
			
			SimpleFunnelChecker checker = null;
			if(clz.getName().contains("RequestPayloadChecker")){
				checker = ((SimpleFunnelChecker) ReflectionUtils.invokeConstructor(clz,WebDriverUtils.class, driverUtils, TestCase.class, testingStep.getTestCase()));
			}else{
				checker = ((SimpleFunnelChecker) ReflectionUtils.invokeConstructor(clz,
						WebDriverUtils.class, driverUtils));
			}
			
			if(checker != null && checker.isEnabled()){
				checker.checkFunnelStep(step);	
			}
		}
	}
	
	public static void checkNewsletterResult(final TestingStep testingStep) throws Exception {
		//Wait for 30 secs to waiting for payload checking in RequestPayloadChecker.java
		threadSleep("30000");
	}
	
	public static void checkUserCityData(final TestingStep testingStep, final HashMultimap<String, String> expectedResults) throws Exception {
		
		checkUserCityData(testingStep, expectedResults, null, 1);
	}
	
	public static void checkUserCityData(final TestingStep testingStep, final HashMultimap<String, String> expectedResults, String userCityEventName, int index) throws Exception {
		
		System.out.println("[UserCityChecking]Expected UserCity data:" + expectedResults);
		Iterator<String> ite = expectedResults.keys().iterator();
		String email = "automated.qa@compareglobalgroup.com";
		while(ite.hasNext()){
			String next = ite.next();
			if(next.toLowerCase().contains("email")){
				email = Lists.newArrayList(expectedResults.get(next)).get(0);
				break;
			}
		}
		
		AlfrescoSetup country = AlfrescoSetup.fromURL(driverUtils.getCurrentUrl());
		
		String urlStr = "https://usercityapidev.compareglobal.co.uk/" + country + "/search/events?email=" + email;
		String jsonStr = driverUtils.getUserCityData(urlStr);
		
		HashMap<String, Object> actualResults = RequestPayloadChecker.createHashMapFromJsonString(jsonStr);
		System.out.println("[UserCityChecking]Actual UserCity data:" + actualResults);
		
		RequestPayloadChecker.checkFieldValue(RequestPayloadChecker.convertHashMap(actualResults), expectedResults);
	}
	
	public static void checkUserCityData(final TestingStep testingStep) throws Exception {
		
		AlfrescoSetup country = AlfrescoSetup.fromURL(driverUtils.getCurrentUrl());
		
		HashMultimap<String, String> expectedResults = CommonFindObjects.getExpectedPayloadForTestCase(testingStep);
		System.out.println("[UserCityChecking]Expected UserCity data:" + expectedResults);

		Iterator<String> ite = expectedResults.keys().iterator();
		String email = "automated.qa@compareglobalgroup.com";
		while(ite.hasNext()){
			String next = ite.next();
			if(next.toLowerCase().contains("email")){
				email = Lists.newArrayList(expectedResults.get(next)).get(0);
				break;
			}
		}
		
		String urlStr = "https://usercityapidev.compareglobal.co.uk/" + country + "/search/events?email=" + email;
		String jsonStr = driverUtils.getUserCityData(urlStr);
		
		UserCityRecord records = (UserCityRecord)IOUtils.loadJsonObjects(jsonStr, UserCityRecord.class);
				
		HashMap<String, Object> actualResults = RequestPayloadChecker.createHashMapFromJsonString(IOUtils.convertJsonObjectToString(records.results[0]));
		System.out.println("[UserCityChecking]Actual UserCity data:" + actualResults);
		
		
		RequestPayloadChecker.checkFieldValue(RequestPayloadChecker.convertHashMap(actualResults), expectedResults);
	}
	
	public static HashMultimap<String, String> getExpectedPayloadForTestCase(TestingStep testingStep){
		
		HashMultimap<String, String> expectedResults = HashMultimap.create();
		String[] args = testingStep.getStrArgs().split(";");
		for(String arg: args){
			int index = arg.indexOf(":");
			expectedResults.put(arg.substring(0, index), arg.substring(index + ":".length()));
		}
		return expectedResults;
	}	
	
	public static void checkFunnelResult(final TestingStep testingStep) throws Exception {
		
		//1. load funnel config 
		PersonalLoanConfig instance = PersonalLoanConfig.fromUrl(testingStep.getURL());
		FunnelConfig plFunnelObject = instance.loadPLFunnelObject(driverUtils);
		
		List<Class<?>> clzList = ReflectionUtils.getAllSubClassesInPackage("com.cgg.pl.funnel", SimpleFunnelChecker.class);
		
		//2. navigate towards funnel page
		int stepNo = 1;
		FunnelStep step = new FunnelStep(driverUtils, plFunnelObject);
		testingStep.setFunnelStep(step);
		do{
			//3. Check funnel step
			StepInfo stepInfo = step.getCurrentStep();
			
			/*if(stepNo > 1 && !driverUtils.getCurrentUrl().endsWith(stepInfo.getLink().replace("#", "/"))){
				 throw new Exception("cannot land on step:" + stepInfo.getLink());
			}*/

			//4. fill-in data in current step
			step.visitCurrentStep();
			
			runFunnelChecker(testingStep, clzList, step);
			
			if (driverUtils.isElementPresent(FunnelStep.NEXT_BUTTON)){
				driverUtils.implicitWait(FunnelStep.NEXT_BUTTON);
				driverUtils.clickButton(FunnelStep.NEXT_BUTTON);
			}
			step.setupNextStep();
			driverUtils.explicitWait();
			if(!Checker.isBlank(stepInfo.getLink())){
				stepNo ++;	
			}
		}while(!step.noMoreSteps());
		
		driverUtils.explicitWait(6000);
		
		runFunnelChecker(testingStep, clzList, step);
		
		//5. Handle with Facebook login
		handleWithCustomerLogin();
		
		//5. Check url: category, emi_fmin, loan amount, loan tenure, loan time unit
		HashMultimap<String, String> userData = step.getUserDataMap();
		List<String> urlSettings = plFunnelObject.getFunnel().getConfig().getDestinationURLSetting();
//		checkUrlParams(driverUtils, urlSettings, userData);
		
		//6. Check UserCity data
		checkUserCityData(testingStep, step.getUserDataMap(), "postFromFunnel", 0);
	}

	public static void handleWithCustomerLogin(){
		
		String str = driverUtils.getResponseHtml(driverUtils.getCurrentUrl());
		int index = str.indexOf("\"authenticated\":");
		if(index > -1){
			int index1 = str.indexOf("\"authenticatedMethod\"");
			boolean flag = Boolean.parseBoolean(str.substring(index + "\"authenticated\":".length(),index1).trim().replace(",", ""));
			if(!flag){
				By by = By.xpath("//*[@ng-switch-when='cgg-signup-facebook']/descendant::a[@class='link-muted']");
				if(driverUtils.isElementPresent(by)){
					WebElement elem = driverUtils.findElement(by);
					driverUtils.clickButton(elem);
					driverUtils.explicitWait();
				}
			}
		}
	}
		
	private static void checkUrlParams(WebDriverUtils driverUtils, List<String> urlSettings, HashMultimap<String, String> userData) {
		
		try{
			String url = driverUtils.getCurrentUrl();
			System.out.println("[CheckURL]actual url:" + url);
			List<NameValuePair> params = URLEncodedUtils.parse(new URI(url), "UTF-8");
			for(NameValuePair param: params){
				if(!param.getName().equalsIgnoreCase("comingFromFunnel") && !userData.containsKey(param.getName())){
					Assert.fail("redundant param in url:" + param.getName());
				}
			}
			for(String key: urlSettings){
				if(userData.containsKey(key)){
					Iterator<String> ite = userData.get(key).iterator();
					while(ite.hasNext()){
						String value = ite.next();
						System.out.println("[CheckURL] expected:" + key+"="+value);
						Assert.assertTrue(url.contains(key+"="+value));
					}
				}
			}	
		}catch(Exception e){
			
		}
		
	}

	private static void checkPersonalLoanResult(final TestingStep testingStep, final ResultSource resultSource) throws Exception {
		
		//Generate test inputs
		PersonalLoanConfig instance = PersonalLoanConfig.fromInputs(loadExcelInputs(testingStep), testingStep.getURL(), visited);
		instance.genInputsForPLResult(visited, resultSource);
		
		if(resultSource == ResultSource.PL_API || resultSource == ResultSource.PL_UI){
			final String debugInfo = "checkResultPage: " + (index++) + "  ||  category:" + instance.getCategory().getID()
					+ "; loan amount:" + instance.getLoanAmount() + "; loan tenure in months:"
					+ instance.getLoanTenure_Month();
			loggerMethodSucess(debugInfo);
			System.out.println(debugInfo);
			
			//Get expected result from Json file
			PLJsonObjects plJsonObjects = instance.getPlJsonObjects();
	 		List<PersonalLoan> expectedResults = plJsonObjects.getResultObjectsFromJsonFile(instance);
	 		
	 		logResults("Expected Result:", expectedResults);

	 		if(resultSource == ResultSource.PL_UI){
				instance = handleUIInputs(driverUtils, instance);
				checkTotalRecords(driverUtils, expectedResults);
				driverUtils.getWebDriver_existing().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			}

			// Get actual result
	 		if(resultSource == ResultSource.PL_UI){
	 			expandMoreDetailsTab(driverUtils);
	 			List<PersonalLoan> actualResults = plJsonObjects.getResultObjectsFromUI(instance.getCategory(), driverUtils, By.xpath(
	 					"//div[contains(@class,'cgg-list-item cgg-list-item__')]"));
	 			logResults("Actual Result:", actualResults);

	 			Assert.assertTrue(expectedResults.equals(actualResults));
	 		}else if(resultSource == ResultSource.PL_API){
	 			PLAPIJsonObjects results = plJsonObjects.getResultObjectsFromAPI(instance);
	 			List<PersonalLoan> actualResults = (List<PersonalLoan>)Stream.concat(results.getFeaturedProductsForAPI().stream(), results.getProductsForAPI().stream()).collect(Collectors.toList());
	 			logResults("Actual Result:", actualResults);
	 			Assert.assertTrue(expectedResults.equals(actualResults));
	 		}			
		}else if(resultSource == ResultSource.MORTGAGE_API || resultSource == ResultSource.MORTGAGE_UI){

			final String debugInfo = "checkResultPage: " + (index++) + "  ||  category:" + instance.getCategory().getID()
					+ "; property value:" + instance.getLoanAmount() + "; borrow percentage:" + instance.getBorrowPercentage() + "% ;loan tenure in years:"
					+ instance.getLoanTenure_Year();
			loggerMethodSucess(debugInfo);
			System.out.println(debugInfo);
			MortgageJsonObject mgJsonObject = instance.getMgJsonObjects();
			List<PersonalLoan> expectedResults = mgJsonObject.getResultObjectsFromJsonFile(instance);
			logResults("Expected Result:", (List<PersonalLoan>)(List<?>)expectedResults);
			
			if(resultSource == ResultSource.MORTGAGE_UI){
				instance = handleUIInputs(driverUtils, instance);
				checkTotalRecords(driverUtils, expectedResults);
				driverUtils.getWebDriver_existing().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			}

			// Get actual result
/*	 		if(resultSource == ResultSource.MORTGAGE_UI){
	 			expandMoreDetailsTab(driverUtils);
	 			List<PersonalLoan> actualResults = mgJsonObject.getResultObjectsFromUI(instance.getCategory(), driverUtils, By.xpath(
	 					"//div[contains(@class,'cgg-list-item cgg-list-item__')]"));
	 			logResults("Actual Result:", actualResults);

	 			Assert.assertTrue(expectedResults.equals(actualResults));
	 		}else */if(resultSource == ResultSource.MORTGAGE_API){
	 			MortgageAPIJsonObjects results = mgJsonObject.getMortgagesFromAPI(instance);
	 			
	 			String clzName = "com.cgg.pl.category." + instance.getCategory().name();
	 			if(!ReflectionUtils.peekClass(clzName)){
	 				clzName = "com.cgg.pl.purposeCategory." + instance.getCategory().name();
	 			}
	 			Class clazz = ReflectionUtils.loadClass(clzName);
	 			
				List<PersonalLoan> actualResults = Stream
						.concat(Lists.newArrayList(results.getFeaturedProducts()).stream(),
								Lists.newArrayList(results.getProducts()).stream())
						.collect(Collectors.toList()).stream()
						.map(p -> (PersonalLoan) ReflectionUtils.invokeConstructor(clazz, MortgageAPIJsonObjects.Product.class, p))
						.collect(Collectors.toList());
	 			logResults("Actual Result:", actualResults);
//	 			Assert.assertTrue(expectedResults.equals(actualResults));
	 		}
		}
	}

	private static void logResults(final String msgPrefix, List<PersonalLoan> expectedResults) {
		
		if(!Checker.isEmpty(expectedResults)){
			StringBuilder sb = new StringBuilder();
			sb.append(msgPrefix + expectedResults.size() + "\n");
			for (PersonalLoan obj : expectedResults) {
				sb.append(obj.toString()).append("\n");
			}
			loggerMethodSucess(sb.toString());
			System.out.println(sb.toString());	
		}
	}

	private static void expandMoreDetailsTab(WebDriverUtils driverUtils) {
		By by = By.xpath("//button[@class='cgg-grid-selector__show-more-button cgg-button__show-more ng-binding']");
		while (driverUtils.findElements(by).size() > 0) {
			driverUtils.clickButton(by);
			driverUtils.explicitWait(3000);
		}
	}
	
	public static void checkMortgageAPIResult(final TestingStep testingStep) throws Exception {

		checkPersonalLoanResult(testingStep, ResultSource.MORTGAGE_API);
	}
	
	public static void checkPersonalLoanAPIResult(final TestingStep testingStep) throws Exception {
		
		checkPersonalLoanResult(testingStep, ResultSource.PL_API);
	}
	
	public static void checkPersonalLoanResultPage(final TestingStep testingStep) throws Exception {

		checkPersonalLoanResult(testingStep, ResultSource.PL_UI);
	}

	private static void checkTotalRecords(WebDriverUtils driverUtils, List<PersonalLoan> expectedResults) {
		expectedResults.forEach(e -> {
			if (!e.isSponsoredProduct()) {
				resultCounter++;
			}
		});

		driverUtils.explicitWait(3000);
		resultCounter = 0; // reset
	}

	private static String loadExcelInputs(final TestingStep testingStep) {
		return (!Checker.isBlank(testingStep.getStrArgs())
				&& Checker.isBlank(testingStep.getStrObjIdentifier())) ? testingStep.getStrArgs()
						: testingStep.getStrObjIdentifier();
	}

	private static PersonalLoanConfig handleUIInputs(WebDriverUtils driverUtils, PersonalLoanConfig instance) {
		
		// category
		By by = By.xpath("//li[@value='" + instance.getCategory().getJsonText() + "']/a");
		driverUtils.clickButton(by);
		driverUtils.explicitWait(3000);

		// loan amount
		by = By.xpath("//input[@ng-model='options.input']");
		if(driverUtils.isElementPresent(by)){
			driverUtils.fillin_textbox(by, instance.getLoanAmount() + "");
		}else{
			by = By.xpath("//select[contains(@class,'selection-option ng-pristine')]");
			WebElement elem = driverUtils.findElements(by).get(0);
			driverUtils.select_selectorByValue(elem, instance.getLoanAmount() + "");
		}
		driverUtils.explicitWait(3000);

		// loan tenure
		by = By.xpath("//input[@ng-model='options.inputfield.input']");
		if(driverUtils.isElementPresent(by)){
			driverUtils.fillin_textbox(by, ""); // trigger onchange() of loan
			// amount
			int loanTenure = (TestCase.isSGURL(instance.getUrl()) || TestCase.isNOURL(instance.getUrl()))
					? instance.getLoanTenure_Month() / 12 : instance.getLoanTenure_Month(); // SG:
			loanTenure = loanTenure == 0 ? 1 : loanTenure; // round up if loan
			// tenure = 0
			int loanTenure_Month = (TestCase.isSGURL(instance.getUrl()) || TestCase.isNOURL(instance.getUrl()))
					? loanTenure * 12 : instance.getLoanTenure_Month(); // SG:
			instance.setLoanTenure_Month(loanTenure_Month);
			driverUtils.fillin_textbox(by, loanTenure + "");
		}else{
			by = By.xpath("//select[contains(@class,'selection-option ng-pristine')]");
			List<WebElement> elems = driverUtils.findElements(by);
			WebElement elem = elems.get(0);
			driverUtils.select_selectorByValue(elem, instance.getLoanTenure_Month() + "");
		}

		driverUtils.explicitWait(3000);
		return instance;
	}

	private static boolean isSpecificalCategory(Category category) {

		return category.equals(Category.QuickLoan_DK) || category.equals(Category.PersonalInstalment_PT)
				|| category.equals(Category.ConsolidatedCredit_PT) || category.equals(Category.QuickLoan_PT)
				|| category.equals(Category.SpecialisedCredit_PT) || category.equals(Category.BalanceTransfer_SG);
	}
	
	public static void gotoPLFunnelPage(final TestingStep testingStep){
		
		HomePage pl = new HomePage(driverUtils);
		pl.goToPLFunnelPage();
	}
	
	public static void gotoCCFunnelPage(final TestingStep testingStep){
		
		HomePage pl = new HomePage(driverUtils);
		pl.goToCCFunnelPage();
	}
	
	public static void gotoCIFunnelPage(final TestingStep testingStep){
		
		HomePage pl = new HomePage(driverUtils);
		pl.goToCIFunnelPage();
	}
	
	public static void gotoBBFunnelPage(final TestingStep testingStep){
		
		HomePage pl = new HomePage(driverUtils);
		pl.goToBBFunnelPage();
	}
}
