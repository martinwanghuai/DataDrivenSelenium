package com.cgg.pl.funnel;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import com.cgg.model.CRMMapping;
import com.cgg.model.CRMMapping.Mapping;
import com.cgg.model.CRMMappingDeserializer;
import com.cgg.model.DataDictionary;
import com.cgg.model.TestCase;
import com.cgg.model.TestingStep;
import com.cgg.pl.category.AlfrescoSetup;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import io.netty.handler.codec.http.HttpResponse;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;
import pageObjects.CommonFindObjects;
import pages.FunnelStep;
import utility.Checker;
import utility.Constant;
import utility.IOUtils;
import utility.WebDriverUtils;

public class RequestPayloadChecker extends SimpleFunnelChecker {

	Logger logger = null;
	Map<String, DataDictionary> dataDictionary = Maps.newHashMap();
	TestCase testCase = null;
	CRMMapping crmMapping = null;
	
	private static List<String> BLACKLIST = Lists.newArrayList("productCode", "providerCode", "productDescription", "providerName","CGG_Source");
	
	public RequestPayloadChecker(final WebDriverUtils driver, final TestCase testCase){
		
		super(driver);
		logger = Logger.getLogger(RequestPayloadChecker.class);
		this.testCase = testCase;
		if(Constant.ENABLE_BROWSER_MOB){
			String url = "https://api.github.com/repos/compareasiagroup/usercity_crm_submit/contents/function/personal_loan_pt.json";
			String vertical = AlfrescoSetup.getVertical(testCase.getTestCaseName());
			String country = AlfrescoSetup.fromURL(testCase.getUrl()).name().toLowerCase();
			
			if(vertical.equalsIgnoreCase("cc")){
				url = "https://api.github.com/repos/compareasiagroup/usercity_crm_submit/contents/function/credit_card_" + country + ".json";	
			}else if(vertical.equalsIgnoreCase("pl") || vertical.equalsIgnoreCase("mg")){
				url = "https://api.github.com/repos/compareasiagroup/usercity_crm_submit/contents/function/personal_loan_" + country + ".json";
			}else if(vertical.equalsIgnoreCase("bb")){
				url = "https://api.github.com/repos/compareasiagroup/usercity_crm_submit/contents/function/broadband_" + country + ".json";
			}else if(vertical.equalsIgnoreCase("ti")){
				url = "https://api.github.com/repos/compareasiagroup/usercity_crm_submit/contents/function/travel_insurance_" + country+ ".json";
			}
			
			crmMapping = (CRMMapping) IOUtils.loadJsonObjects(
					WebDriverUtils.callRESTAPI(url, "martinwanghuai", "1829979328b875a5a7acf149f5a8ee9f7b87240c"), CRMMapping.class, new CRMMappingDeserializer());
			
			url = "https://api.github.com/repos/compareasiagroup/data-dictionary/contents/Data-Dictionary.pipe";
			List<String> results = Lists.newArrayList(WebDriverUtils.callRESTAPI(url, "martinwanghuai", "1829979328b875a5a7acf149f5a8ee9f7b87240c").split("\\r?\\n"));
			for(int i = 1; i < results.size(); i ++){
				DataDictionary data = new DataDictionary(Lists.newArrayList(results.get(i).split("\\|")));
				dataDictionary.put(data.getNameDescription(), data);
			}	
		}
	}
	
	@Override
	public boolean checkFunnelStep(FunnelStep step) {

		return false;
	}

	@Override
	public boolean isEnabled() {

		return Constant.ENABLE_FUNNEL_TESTER;
	}

	@Override
	public boolean checkCurrentPage() {
		return false;
	}

	public HttpResponse checkRequestPayload_funnel_appForm(io.netty.handler.codec.http.HttpRequest request,
			HttpMessageContents contents, HttpMessageInfo messageInfo){
		
		try{
			
			if (isInterestedRequest(contents, messageInfo)) {
				System.out.println(contents.getTextContents());
				HashMap<String, Object> actualFields = RequestPayloadChecker.createHashMapFromJsonString(contents.getTextContents());
				this.checkFieldAgainstDataDictionary(actualFields, 1, 3);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}

	public HttpResponse checkRequestPayload_newsletter(io.netty.handler.codec.http.HttpRequest request,
			HttpMessageContents contents, HttpMessageInfo messageInfo){
		
		try{
			if (isInterestedRequest(contents, messageInfo)) {
				System.out.println("Received payload: " + contents.getTextContents());
				HashMap<String, Object> actualResults = RequestPayloadChecker.createHashMapFromJsonString(contents.getTextContents());
				HashMultimap<String, String> expectedResults = this.getExpectedPayloadForTestCase(testCase);
				this.checkFieldValue(RequestPayloadChecker.convertHashMap(actualResults), expectedResults);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	private HashMultimap<String, String> getExpectedPayloadForTestCase(TestCase testCase){
		
		TestingStep step = testCase.getTestingStep(testCase.getTestingSteps().size() - 2);
		System.out.println("[UserCityChecking]Expected payload:" + step.getStrArgs());
		return CommonFindObjects.getExpectedPayloadForTestCase(step);
	}
	
	public boolean isInterestedRequest(HttpMessageContents contents, HttpMessageInfo messageInfo) {
		
		return (messageInfo.getOriginalUrl().contains("execute-api.ap-northeast-1.amazonaws.com") 
				|| messageInfo.getOriginalUrl().contains("execute-api.eu-central-1.amazonaws.com")
				|| messageInfo.getOriginalUrl().contains("execute-api.us-west-1.amazonaws.com")
				|| messageInfo.getOriginalUrl().contains("execute-api.ap-southeast-1.amazonaws.com")
				//for PT-Live
				|| messageInfo.getOriginalUrl().contains("usercityapidev.compareglobal.co.uk"))
				&& !Checker.isBlank(contents.getTextContents());
	}
	
	public static void checkFieldValue(HashMultimap<String, Object> actualResults, HashMultimap<String, String> expectedResults){
		
		Iterator<String> expectedFields = expectedResults.keySet().iterator();
		while(expectedFields.hasNext()){
			String expectedField = expectedFields.next();
			Set<String> expectedValues = expectedResults.get(expectedField);
			if(!actualResults.containsKey(expectedField)){
				System.out.println("[UserCityChecking]Field not exist in UserCity: " + expectedField + "=" + expectedValues);				
			}else{
				HashSet<String> actualValues = (HashSet<String>)(HashSet<?>)Sets.newHashSet(actualResults.get(expectedField));
				if(!Sets.symmetricDifference( expectedValues, actualValues).isEmpty()){
					System.out.println("[UserCityChecking]Field value not match for " + expectedField + ": expected (" +  expectedValues + "); actual (" + actualValues + ")");	
				}else{
					System.out.println("[UserCityChecking]Field value checking passed:" + expectedField);
				}
			}
		}
	}
	
	private void checkFieldAgainstDataDictionary(Map<String, Object> map_actualResult, int currentLevel, int levelUpperBound){

		if(currentLevel <= levelUpperBound){
			
			boolean everythingGood = true;
			
			//1. check payload key against data dict. and CRM mapping
			/*everythingGood = checkPayloadAgainstDataDictAndCRMMapping(map_actualResult, currentLevel, levelUpperBound,
					everythingGood);*/
			
			//2. check payload value against user input
			HashMultimap<String, String> userInputs_expectedResult = testCase.getUserInputsFromTestingStep();
			Iterator<String> keys_expectedResult = userInputs_expectedResult.keys().iterator();
			
			while(keys_expectedResult.hasNext()){
				String key_expectedResult =  keys_expectedResult.next();
				Set<String>	expectedValues = userInputs_expectedResult.get(key_expectedResult);				
				if(!map_actualResult.containsKey(key_expectedResult)){
					everythingGood = false;
					System.out.println("[" + this.getClass().getSimpleName() + "] Field not exist in payload:" + key_expectedResult + "=" + expectedValues);
				}else {
					HashSet<String> actualValues = (HashSet<String>)(HashSet<?>) Sets.newHashSet(map_actualResult.get(key_expectedResult));
					
					if(!Sets.symmetricDifference(expectedValues, actualValues).isEmpty()){
						everythingGood = false;
						System.out.println("[" + this.getClass().getSimpleName() + "] Field value not match for:" 
								+ key_expectedResult + ", payload(" + actualValues +"), user inputs (" + expectedValues + ")");
					}else{
						System.out.println("[" + this.getClass().getSimpleName() + "] Field value checking passed:" 
								+ key_expectedResult);
					}
				}	
			}

			if(everythingGood){
				System.out.println("[" + this.getClass().getSimpleName() + "] No inconsistent payload field found");
			}
		}
	}

	public boolean checkPayloadAgainstDataDictAndCRMMapping(Map<String, Object> map_actualResult, int currentLevel,
			int levelUpperBound, boolean everythingGood) {
		List<String> expectedCRMFields = null;
		if(crmMapping!= null){
			List<Mapping> crmMappings = Lists.newArrayList();
			crmMappings.addAll(crmMapping.getLeads().getMappings());
			crmMappings.addAll(crmMapping.getProducts().getMappings());
			crmMappings.addAll(crmMapping.getApplications().getMappings());
			crmMappings.addAll(crmMapping.getProviders().getMappings());
			crmMappings.addAll(crmMapping.getApplicants().getMappings());
			
			expectedCRMFields = crmMappings.stream().map(e -> e.getStrs().get(1).substring(e.getStrs().get(1).lastIndexOf("/")+ "/".length())).collect(Collectors.toList());
		}
									
		List<String> expectedDataDictionaryKeys = dataDictionary.values().stream().map(data -> data.getFieldNameUsedInJSON()).collect(Collectors.toList());
		Iterator<String> keys_actualResult = map_actualResult.keySet().iterator();
		

		while(keys_actualResult.hasNext()){
			String key_actualResult = keys_actualResult.next();
			System.out.println("Check payload field:" + key_actualResult);
			
			
			Object value_actualResult = map_actualResult.get(key_actualResult);
			if(value_actualResult instanceof String){
				//1 check payload against data dictionary
				if(!expectedDataDictionaryKeys.contains(key_actualResult)){
					if(!BLACKLIST.contains(key_actualResult) && !key_actualResult.endsWith("Display")){
						everythingGood = false;
						System.out.println("[" + this.getClass().getSimpleName() + "]Cannot find key of request payload in data dictionary:" + key_actualResult);	
					}
				}									
				
				//2. check payload against CRM mapping
				if(expectedCRMFields != null){
					if(!expectedCRMFields.contains(key_actualResult)){
						if(!BLACKLIST.contains(key_actualResult) && !key_actualResult.endsWith("Display")){
							everythingGood = false;
							System.out.println("[" + this.getClass().getSimpleName() + "]Cannot find key of request payload in CRM Mapping json file:" + key_actualResult);
						}	
					}	
				}
			}else{
				currentLevel ++;
				this.checkFieldAgainstDataDictionary((Map<String, Object>)value_actualResult, currentLevel, levelUpperBound);
			}
		}
		return everythingGood;
	}
	
	public static HashMap<String, Object> createHashMapFromJsonString(String json) {

		JsonParser parser = new JsonParser();
		JsonObject object = (JsonObject) parser.parse(json);
	    Set<Map.Entry<String, JsonElement>> set = object.entrySet();
	    Iterator<Map.Entry<String, JsonElement>> iterator = set.iterator();
	    HashMap<String, Object> map = new HashMap<String, Object>();
	    
	    while (iterator.hasNext()) {
	        Map.Entry<String, JsonElement> entry = iterator.next();
	        String key = entry.getKey();
	        JsonElement value = entry.getValue();

	        if (null != value) {
	            if (!value.isJsonPrimitive()) {
	                if (value.isJsonObject()) {

	                    map.put(key, createHashMapFromJsonString(value.toString()));
	                } else if (value.isJsonArray() && value.toString().contains(":")) {

	                    List<HashMap<String, Object>> list = new ArrayList<>();
	                    JsonArray array = value.getAsJsonArray();
	                    if (null != array) {
	                        for (JsonElement element : array) {
	                            list.add(createHashMapFromJsonString(element.toString()));
	                        }
	                        map.put(key, list);
	                    }
	                } else if (value.isJsonArray() && !value.toString().contains(":")) {
	                    map.put(key, value.getAsJsonArray());
	                }
	            } else {
	                map.put(key, value.getAsString());
	            }
	        }
	    }
	    
	    HashMap<String, Object> result = Maps.newHashMap();
		convertHashMap(map, result);
		return result;
	}
	
	private static void convertHashMap(HashMap<String, Object> src, HashMap<String, Object> result){
		
		Iterator<String> ite = src.keySet().iterator();
		while(ite.hasNext()){
			String key = ite.next();
			Object value = src.get(key);
			if(value instanceof String){
				result.put(key, (String)value);
			}else if(value instanceof HashMap){
				convertHashMap((HashMap)value, result);
			}else if(value instanceof ArrayList){
				ArrayList<Object> item = (ArrayList<Object>)value;
				for(Object str: item){
					convertHashMap((HashMap<String, Object>)str, result);
				}
			}
		}
	}
	
	public static HashMultimap<String, Object> convertHashMap(HashMap<String, Object> src){
		
		Iterator<String> ite = src.keySet().iterator();
		
		HashMultimap<String, Object> result = HashMultimap.create();
		while(ite.hasNext()){
			String key = ite.next();
			Object value = src.get(key);
			if(value instanceof String){
				String temp = (String)value;
				if(temp.contains(",")){
					for(String subValue: temp.split(",")){
						result.put(key, subValue);
					}
				}else{
					result.put(key, value);
				}
			}else{
				result.put(key, value);
			}
		}
		
		return result;
	}
}
