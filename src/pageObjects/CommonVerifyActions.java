package pageObjects;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.cgg.model.TestingStep;

import utility.Log;
import utility.WebDriverUtils;

public class CommonVerifyActions extends BaseClass {
	static Exception valueNotMatching = new Exception("VALUE is not Matching");

	public CommonVerifyActions(WebDriverUtils driver) {
		super(driver);
	}

	public static boolean performActionOnElement(WebElement elem, final TestingStep testingStep) throws Exception {

		String action = testingStep.getStrAction();
		final String strData = testingStep.getStrDataId();
//		final WebDriverUtils driverUtils = new WebDriverUtils(driver);
		try {
			boolean val = false;
			String attr = "";
			Select switchdropdown = null;
			switch (action.toUpperCase()) {
			case "CLICK":
				driverUtils.clickButton(elem);
				val = true;
				break;
			case "CLICKJS":
				driverUtils.clickButton(elem);
				val = true;
				break;
			case "CLEAR":
				elem.clear();
				val = true;
				break;
			case "DISPLAYED":
				elem.isDisplayed();
				val = true;
				break;
			case "SUBMIT":
				elem.submit();
				val = true;
				break;
			case "EQUALS":
				Boolean i = elem.equals(strData);
				if (i != null)
					val = true;
				break;
			case "GETATTRIBUTE":
				attr = elem.getAttribute(strData);
				if (attr != null)
					val = true;
				break;
			case "GETCLASS":
				attr = elem.getClass().toString();
				if (attr != null)
					val = true;
				break;
			case "GETTEXT":
				attr = elem.getText().toString();
				if (attr != null)
					val = true;
				break;
			case "SENDKEYS":
				elem.sendKeys(strData);
				val = true;
				break;
			case "SENDKEYSCTRLA":
				elem.sendKeys(Keys.chord(Keys.CONTROL, "a"), strData);
				val = true;
				break;
			case "SELECTBYINDEX":
				switchdropdown = new Select(elem);
				switchdropdown.selectByIndex(Integer.parseInt(strData));
				val = true;
				break;
			case "SELECTBYVALUE":
				switchdropdown = new Select(elem);
				switchdropdown.selectByValue(strData);
				val = true;
				break;
			default:
				val = true;
				action = "Default Case No action to Perform";
				break;
			}
			if (val)
				loggerMethodSucess(elem, action);
			return val;
		} catch (Exception e) {
			loggerMethodFailure(elem, action, e);
			throw (e);
		}
	}

	public static Boolean verifyElement(WebElement elem, final TestingStep testingStep) throws Exception {
		
		final String action = testingStep.getStrFunction();
		final String strtoCompare = testingStep.getStrDataId();
		String[] sSplitVal = null;
		boolean val = false;
		String attr = "";
		try {

			switch (action.toUpperCase()) {

			case "VERIFYGETTEXT":
				attr = elem.getText().toString();
				if (attr.trim().toUpperCase().equals(strtoCompare.trim().toUpperCase()))
					val = true;
				else
					val = false;
				break;
			case "VERIFYCONTAINSTEXT":
				attr = elem.getText().toString();
				if (attr.trim().toUpperCase().contains(strtoCompare.trim().toUpperCase()))
					val = true;
				else
					val = false;
				break;
			case "VERIFYOPTIONALTEXT":
				attr = elem.getText().toString();
				sSplitVal=strtoCompare.split(";;");
				if (attr.trim().toUpperCase().equals(sSplitVal[0].trim().toUpperCase())||attr.trim().toUpperCase().equals(sSplitVal[1].trim().toUpperCase()))
					val = true;
				break;
			default:
				val = false;
				break;
			}
			if (val) {
				loggerMethodSucess(elem,
						"Expected Value:" + attr + " EQUALS/CONTAINS Actual Value:" + strtoCompare + "  VERIFYGETTEXT :: ");
			} else {
				loggerMethodFailure(elem,
						"Expected Value:" + attr + " NOT EQUALS Actual Value:" + strtoCompare + "  VERIFYGETTEXT :: ",
						valueNotMatching);
				throw new Exception(valueNotMatching);
				
			}
			return val;

		} catch (Exception e) {
			loggerMethodFailure(elem, attr, e);
			throw (e);
		}

	}

	public static void loggerMethodSucess(WebElement elem, String action) throws Exception {
		Log.info("\n" + action + " is done on" + elem);
	}

	public static void loggerMethodFailure(WebElement elem, String action, Exception e) throws Exception {
		Log.error("\n" + action + " is not done on" + elem + ". Failed to Perform Action" + e.getMessage());
		// Utils.takeScreenshot(driver,action + " is not done on"+". Failed to
		// Perform Action");
	}
}
