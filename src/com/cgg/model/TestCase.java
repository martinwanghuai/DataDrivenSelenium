package com.cgg.model;

import java.util.List;

import org.testng.collections.Lists;

import com.cgg.pl.category.Category;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.HashMultimap;

import pages.FunnelStep;
import utility.Checker;
import utility.Constant;
import utility.ExcelUtils;

public class TestCase extends TestObject{

	private String testCaseName;
	private String userName;
	private String password;
	private String browser;
	private String url;
	private String actionSheet;
	private String strExecuted;
	
	private List<TestingStep> testingSteps;

	public TestCase(){
		super();
	}

	public TestCase(String testCaseName, String userName, String password,
			String browser, String url, String actionSheet, String strExecuted) {
		super(strExecuted);
		this.testCaseName = testCaseName;
		this.userName = userName;
		this.password = password;
		this.browser = browser;
		this.url = url;
		this.actionSheet = actionSheet;
		this.strExecuted = strExecuted;
	}

	public String getStrExecuted() {
		return strExecuted;
	}

	public void setTestCaseName(String testCaseName) {
		this.testCaseName = testCaseName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setActionSheet(String actionSheet) {
		this.actionSheet = actionSheet;
	}

	public void setStrExecuted(String strExecuted) {
		this.strExecuted = strExecuted;
	}

	public String getTestCaseName() {
		return testCaseName;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public String getBrowser() {
		return browser;
	}

	public String getUrl() {
		return url;
	}

	public String getActionSheet() {
		return actionSheet;
	}

	public TestingStep getTestingStep(int index){
		
		return index < testingSteps.size() ? testingSteps.get(index) : null;
	}
	
	public List<TestingStep> getTestingSteps() {
		return testingSteps;
	}

	public void setTestingSteps(List<TestingStep> testingSteps) {
		this.testingSteps = testingSteps;
	}

	public static List<TestCase> bulkMapExcelRowToTestCase(final String sheetName) throws Exception {

		List<List<TestCase>> testCases = Lists.newArrayList();
		ExcelUtils util = new ExcelUtils(Constant.USRDIR + Constant.PATH_TESTDATA + Constant.FILE_TESTDATA, sheetName);
		List<List<String>> testCaseList = util.getAllRows();

		testCases = FluentIterable.from(testCaseList).transform(new Function<List<String>, List<TestCase>>() {
			@Override
			public List<TestCase> apply(final List<String> row) {

				try {
					final String testCaseName = row.get(Constant.COL_TESTCASENAME);
					final String strExecuted = row.get(Constant.COL_EXCELFLAG_TESTCASE);
					int executeTime = 0;

					if (!strExecuted.equalsIgnoreCase("N")) {
						executeTime = strExecuted.equalsIgnoreCase("Y") ? 1 : Integer.parseInt(strExecuted);
					}

					List<TestCase> testCases = null;
					if (!Checker.isBlank(testCaseName) && executeTime > 0) {
						testCases = Lists.newArrayList();
						for(int i = 0; i < executeTime; i ++){
							final String userName = row.get(Constant.COL_USERNAME);
							final String password = row.get(Constant.COL_PASSWORD);
							final String browser = row.get(Constant.COL_BROWSER);
							final String url = row.get(Constant.COL_PARAM1);
							final String actionSheet = row.get(Constant.COL_PARAM2);

							TestCase testCase = new TestCase(testCaseName, userName, password, browser, url, actionSheet,
									strExecuted);

							System.out.println("loading test steps for " + testCase.getTestCaseName());
							List<TestingStep> testingSteps = TestingStep
									.bulkMapExcelRowToTestingStep(testCase, testCase.getUrl());

							testCase.setTestingSteps(
									Lists.newArrayList(Collections2.filter(testingSteps, TestingStep.FILTER_EXECUTED)));
							
							testCases.add(testCase);
						}
					}
					
					return testCases;

				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}

			}

		}).filter(Predicates.notNull()).toList();

		List<TestCase> result = Lists.newArrayList();
		for(List<TestCase> testCaseList1: testCases){
			result.addAll(testCaseList1);
		}
		return result;
	}
	
	public String getTestCaseSheetNameBaseOnActionSheet() {
		
		return !Checker.isBlank(this.getActionSheet())? this.getActionSheet():this.getTestCaseName();
	}

	@Override
	public String toString() {
		return "TestCase [testCaseName=" + testCaseName + ", browser="
				+ browser + ", url=" + url + "]";
	}
	
	public static Predicate<TestCase> FILTER_EXECUTED = new Predicate<TestCase>(){
		
		@Override
		public boolean apply(final TestCase testCase){
			return !testCase.getStrExecuted().equalsIgnoreCase("N");
		}
	};
	
	public static Predicate<TestCase> IS_SPECIFIED_COUNTRY(final String country){
		
		return new Predicate<TestCase>(){
			
			@Override
			public boolean apply(TestCase testCase){
				
				if(Checker.isBlank(country)){
					return true;
				}
				
				final String url = testCase.getUrl().toLowerCase();
				switch(country.toUpperCase()){
				
				case "HK":
					return isHKURL(url); 
				case "SG":
					return isSGURL(url);
				case "TW":
					return isTWURL(url);
				case "NO":
					return isNOURL(url);
				case "BE":
					return isBEURL(url);
				case "MX":
					return isMXURL(url);
				case "DK":
					return isDKURL(url);
				case "FI":
					return isFIURL(url);
				case "PT":
					return isPTURL(url);
				case "MY":
					return isMYURL(url);
				case "TH":
					return isTHURL(url);
				case "PH":
					return isPHURL(url);
				case "ID":
					return isIDURL(url);
				default:
					return true;
				}
			}

		};
	}

	/**
	 * 
	 * @param componentName: can be either "PLResultPage" or "PLWidgets"
	 * @return
	 */
	public static Predicate<TestCase> IS_SPECIFIED_COMPONENT(final String componentName){
		
		return new Predicate<TestCase>(){
			
			@Override
			public boolean apply(TestCase testCase){
				
				if(Checker.isBlank(componentName)){
					return true;
				}
				return testCase.getTestCaseName().toLowerCase().contains(componentName.toLowerCase());
			}
		};
	}
	
	public static Predicate<Category> IS_SPECIFIED_CATEGORY(final List<String> categoryTypes) {

		return new Predicate<Category>() {

			@Override
			public boolean apply(Category obj) {

				return categoryTypes.contains(obj.getID()) ? true : false;
			}
		};
	};

	public HashMultimap<String, String> getUserInputsFromTestingStep(){
		
		HashMultimap<String, String> userInputs = null;
		FunnelStep step  = null;
		for(TestingStep testingStep: this.testingSteps){
			if(testingStep.getAppFormStep()!= null){
				step  = testingStep.getAppFormStep();
			}else if(testingStep.getFunnelStep() != null){
				step = testingStep.getFunnelStep();
			}
			
			if(step != null){
				userInputs = step.getUserDataMap();
				break;
			}
		}
		
		return userInputs;
	}
	
	public static boolean isFIURL(final String url) {
		return url.toLowerCase().contains("vertaaensin.fi") || url.toLowerCase().contains("finland");
	}
	
	public static boolean isDKURL(final String url) {
		return url.toLowerCase().contains("samlino.dk") || url.toLowerCase().contains("denmark");
	}

	
	public static boolean isMXURL(final String url) {
		return url.toLowerCase().contains("comparaguru") || url.toLowerCase().contains("mexico");
	}

	public static boolean isBEURL(final String url) {
		return url.toLowerCase().contains("topcompare") || url.toLowerCase().contains("belgium") || url.toLowerCase().contains("ciab");
	}

	public static  boolean isNOURL(final String url) {
		return url.toLowerCase().contains("samlino.no") || url.toLowerCase().contains("norway");
	}

	public static  boolean isTWURL(final String url) {
		return url.toLowerCase().contains("money101") || url.toLowerCase().contains("taiwan");
	}

	public static  boolean isSGURL(final String url) {
		return url.toLowerCase().contains("singsaver") || url.toLowerCase().contains("singapore");
	}

	public static  boolean isHKURL(final String url) {
		return url.toLowerCase().contains("moneyhero")|| url.toLowerCase().contains("hongkong");
	}
	
	public static boolean isPTURL(final String url){
		return url.toLowerCase().contains("comparaja.pt")|| url.toLowerCase().contains("portugal");
	}
	
	public static boolean isMYURL(final String url){
		return url.toLowerCase().contains("comparehero.my")|| url.toLowerCase().contains("malaysia");
	}
	
	public static boolean isTHURL(final String url){
		return url.toLowerCase().contains("moneyguru.co.th")|| url.toLowerCase().contains("thailand");
	}
	
	public static boolean isPHURL(final String url){
		return url.toLowerCase().contains("moneymax.ph")|| url.toLowerCase().contains("philippines");
	}
	
	public static boolean isIDURL(final String url){
		return url.toLowerCase().contains("halomoney.co.id")|| url.toLowerCase().contains("indonesia");
	}
}
