package appModules;

import java.lang.reflect.Method;

import org.openqa.selenium.WebElement;

import com.cgg.model.TestingStep;

import pageObjects.BaseClass;
import pageObjects.CommonFindObjects;
import pageObjects.CommonVerifyActions;
import utility.Checker;
import utility.Log;
import utility.ReflectionUtils;
import utility.WebDriverUtils;

public class CommonFindMethods extends BaseClass {
	
	public CommonFindMethods(WebDriverUtils driver) {
		
		super(driver);
	}

	public static void executeMethod(final TestingStep testingStep) throws Exception {
		
		WebElement elem = null;

		try {
			loggerMethodStart(testingStep.getStepNo() + "::" + testingStep.getStepName());
			String strbyMethod = Checker.isBlank(testingStep.getStrbyMethod()) ? testingStep
					.getStrFunction() : testingStep.getStrbyMethod(); 
					
			CommonFindObjects obj = new CommonFindObjects();
			Class<?> clz = ReflectionUtils.loadClass(obj.getClass().getName()/*"pageObjects.CommonFindObjects"*/);
			
			if(ReflectionUtils.containMethod(clz, strbyMethod)){
				
				Method m = clz.getDeclaredMethod(strbyMethod, TestingStep.class);
				Object returnObj =  m.invoke(clz, testingStep);
				if(returnObj != null && returnObj instanceof WebElement){
					elem = (WebElement)returnObj;
				}
			}else{
				System.out.println("Cannot find:" + strbyMethod + "() in pageObjects.CommonFindObjects");
			}
			
			if(elem != null){
				boolean valOfAction = testingStep.getStrFunction().toUpperCase().contains("VERIFY") ? 
						CommonVerifyActions.verifyElement(elem, testingStep) : 
							CommonVerifyActions.performActionOnElement(elem, testingStep);  
			}
			
			loggerMethodSucess(elem, testingStep.getStepNo() + "::" + testingStep.getStepName());
			loggerMethodEnd(testingStep.getStepNo() + "::" + testingStep.getStepName());
		} catch (Exception e) {
			loggerMethodFailure(elem, testingStep.getStepNo() + "::" + testingStep.getStepName());
			throw (e);
		}
	}
	
	public static void loggerMethodSucess(WebElement varValue, String strStep) throws Exception {
		Log.info("\n" + strStep + " " + " IS SET TO PASSED");
	}

	public static void loggerMethodStart(String strStep) throws Exception {
		Log.info("\n" + "***********************START OF STEP***********************  " + strStep);
	}

	public static void loggerMethodEnd(String strStep) throws Exception {
		Log.info("\n" + "***********************END OF STEP***********************    " + strStep);
	}

	public static void loggerMethodFailure(WebElement varValue, String strStep) throws Exception {
		Log.error("\n" + strStep + " " + " . IS SET TO FAILED");
//		Utils.takeScreenshot(driver, strStep.replace("::", ""));
	}

}
