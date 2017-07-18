package com.cgg.model;

import java.util.List;

import com.cgg.pl.category.AppFormStep;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;

import pages.FunnelStep;
import utility.Checker;
import utility.Constant;
import utility.ExcelUtils;


public class TestingStep extends TestObject{

	private String stepNo;
	private String stepName;
	private String strbyMethod;
	private String strObjIdentifier;
	private String strParam1;
	private String strAction;
	private String strDataId;
	private String strFunction;
	private String strArgs;
	private String strParam2;
	private String beforePopUp;
	private String URL;
	private TestCase testCase;
	private AppFormStep appFormStep;
	private FunnelStep funnelStep;
	
	public String getBeforePopUp() {
		return beforePopUp;
	}

	public void setBeforePopUp(String beforePopUp) {
		this.beforePopUp = beforePopUp;
	}

	public TestingStep(){
		
		super();
	}
	
	public TestingStep(String stepNo, String stepName, String strExecuted,
			String strbyMethod, String strObjIdentifier, String strParam1,
			String strAction, String strDataId, String strFunction,
			String strArgs, String strParam2, String URL, TestCase testCase) {
		
		super(strExecuted);
		this.stepNo = stepNo;
		this.stepName = stepName;
		this.strbyMethod = strbyMethod;
		this.strObjIdentifier = strObjIdentifier;
		this.strParam1 = strParam1;
		this.strAction = strAction;
		this.strDataId = strDataId;
		this.strFunction = strFunction;
		this.strArgs = strArgs;
		this.strParam2 = strParam2;
		this.URL = URL;
		this.testCase = testCase;
	}
	

	public void setStepNo(String stepNo) {
		this.stepNo = stepNo;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public void setStrbyMethod(String strbyMethod) {
		this.strbyMethod = strbyMethod;
	}

	public void setStrObjIdentifier(String strObjIdentifier) {
		this.strObjIdentifier = strObjIdentifier;
	}

	public void setStrParam1(String strParam1) {
		this.strParam1 = strParam1;
	}

	public void setStrAction(String strAction) {
		this.strAction = strAction;
	}

	public void setStrDataId(String strDataId) {
		this.strDataId = strDataId;
	}

	public void setStrFunction(String strFunction) {
		this.strFunction = strFunction;
	}

	public void setStrArgs(String strArgs) {
		this.strArgs = strArgs;
	}

	public void setStrParam2(String strParam2) {
		this.strParam2 = strParam2;
	}

	public String getStepNo() {
		return stepNo;
	}

	public String getStepName() {
		return stepName;
	}

	public String getStrbyMethod() {
		return strbyMethod;
	}

	public String getStrObjIdentifier() {
		return strObjIdentifier;
	}

	public String getStrParam1() {
		return strParam1;
	}

	public String getStrAction() {
		return strAction;
	}

	public String getStrDataId() {
		return strDataId;
	}

	public String getStrFunction() {
		return strFunction;
	}

	public String getStrArgs() {
		return strArgs;
	}

	public String getStrParam2() {
		return strParam2;
	}
	
	public static List<TestingStep> bulkMapExcelRowToTestingStep(final TestCase testCase, final String URL) throws Exception{

		ExcelUtils util = new ExcelUtils(Constant.USRDIR + Constant.PATH_TESTDATA + Constant.FILE_TESTDATA, testCase.getTestCaseSheetNameBaseOnActionSheet());
		List<List<String>> rawRows = util.getAllRows();

		List<List<TestingStep>> testingSteps = FluentIterable.from(rawRows)
				.transform(new Function<List<String>, List<TestingStep>>() {

					@Override
					public List<TestingStep> apply(final List<String> rawRow) {

						List<TestingStep> result = null;
						final String stepNo = rawRow.get(Constant.COL_STEPNO);
						if(Checker.isBlank(stepNo)){
							return result;
						}
						
						final String strExecuted = rawRow.get(Constant.COL_EXCELFLAG_TESTINGSTEP);
						int executeTime = 0;
						try{
							if(!strExecuted.equalsIgnoreCase("N")){
								executeTime = strExecuted.equalsIgnoreCase("Y")? 1: Integer.parseInt(strExecuted);
							}
							
							if(executeTime > 0){
						
								final String stepName = rawRow.get(Constant.COL_STEPNAME);
								
								String strbyMethod = rawRow.get(Constant.COL_SCREEN_POINTER);
								
								String strObjIdentifier = rawRow.get(Constant.COL_OBJECT_IDENTIFIER);
								
								String strDataId = rawRow.get(Constant.COL_DATAIDENTIFIER);
								
								final String strFunction = rawRow.get(Constant.COL_ACTION);
								
								final String strArgs = rawRow.get(Constant.COL_ARGS);

								List<String> srowObjectData = Lists.newArrayList();
								List<String> scolData = Lists.newArrayList();
								
								if(!Checker.isBlank(strDataId)){
									scolData = util.locateColumnData(Constant.USRDIR + Constant.PATH_TESTDATA + Constant.FILE_DATASHEET, strbyMethod, strObjIdentifier, strDataId, strArgs);
								}
								
								if(!Checker.isBlank(strObjIdentifier)){
									srowObjectData = util.locateRowData(Constant.USRDIR + Constant.PATH_TESTDATA + Constant.FILE_OBJECTSHEET, strbyMethod, strObjIdentifier);
								}
								
								strbyMethod = srowObjectData.size() > 0 ? srowObjectData.get(0): "";
								
								strObjIdentifier = srowObjectData.size() > 0 ? srowObjectData.get(1): "";
								
								String strParam1 = srowObjectData.size() > 0 ? srowObjectData.get(2): "";
								
								String strAction = srowObjectData.size() > 0 ? srowObjectData.get(3): "";
								
								strDataId = scolData.size() > 0 ? scolData.get(0): "";

								final String strParam2 = "";

								result = Lists.newArrayList();
								for(int i = 0; i < executeTime; i ++){
									result.add(new TestingStep(stepNo, stepName, strExecuted,
											strbyMethod, strObjIdentifier, strParam1,
											strAction, strDataId, strFunction,
											strArgs, strParam2, URL, testCase));
								}
							}	
						}catch(Exception e){
							e.printStackTrace();
						}
						
						return result;
					}}).filter(Predicates.notNull()).toList();
		
		List<TestingStep> result = Lists.newArrayList();
		for(List<TestingStep> testingStepList: testingSteps){
			result.addAll(testingStepList);
		}
		return result;
	}
	
	public static Predicate<TestingStep> FILTER_EXECUTED = new Predicate<TestingStep>(){
		
		@Override
		public boolean apply(final TestingStep testCase){
			return !testCase.getStrExecuted().equalsIgnoreCase("N");
		}
	};

	@Override
	public String toString() {
		return "TestingStep [stepNo=" + stepNo + ", stepName=" + stepName
				+ ", strbyMethod=" + strbyMethod + ", strObjIdentifier="
				+ strObjIdentifier + ", strParam1=" + strParam1
				+ ", strAction=" + strAction + ", strDataId=" + strDataId
				+ ", strFunction=" + strFunction + ", strArgs=" + strArgs
				+ ", strParam2=" + strParam2 + "]";
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public TestCase getTestCase() {
		return testCase;
	}

	public void setTestCase(TestCase testCase) {
		this.testCase = testCase;
	}

	public AppFormStep getAppFormStep() {
		return appFormStep;
	}

	public void setAppFormStep(AppFormStep appFormStep) {
		this.appFormStep = appFormStep;
	}

	public FunnelStep getFunnelStep() {
		return funnelStep;
	}

	public void setFunnelStep(FunnelStep funnelStep) {
		this.funnelStep = funnelStep;
	}

	/*@Override
	public String toString() {
		return "TestingStep [stepNo=" + stepNo + ", stepName=" + stepName + "]";
	}*/

	
}
