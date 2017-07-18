package com.cgg.model;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.openqa.selenium.By;

import com.cgg.model.PLJsonObject.Interest;
import com.cgg.pl.category.Category;
import com.cgg.pl.category.PersonalLoan;
import com.cgg.pl.category.SortingKeyword;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

import utility.Checker;
import utility.IOUtils;
import utility.MathUtils;
import utility.ReflectionUtils;
import utility.WebDriverUtils;

public class PLJsonObjects {

	private HashMap<PersonalLoanConfig, PLJsonObjects> cache = Maps.newHashMap();
	private List<PLJsonObject> JsonObjects;
	private List<Double> loanAmounts;
	private List<Double> loanTenures;

	public List<PLJsonObject> getJsonObjects() {
		return JsonObjects;
	}

	public void setJsonObjects(List<PLJsonObject> jsonObjects) {
		this.JsonObjects = jsonObjects;
	}

	public List<PersonalLoan> getResultObjectsFromUI(Category category, WebDriverUtils driver, By by) {

		List<PersonalLoan> result = null;
		try {
			String packName = category.getClass().getPackage().getName();
			if(!ReflectionUtils.peekClass(packName + "." + category.name())){
				packName = "com.cgg.pl.purposeCategory";
			}
			Class<?> clz = ReflectionUtils.loadClass(packName + "." + category.name());
			Method m = ReflectionUtils.getMethod(clz, "getPersonalLoansFromUI");
			result = (List<PersonalLoan>) m.invoke(clz, driver, by);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private Function<PLJsonObject, PersonalLoan> mapPLJsonObjectsToPersonalLoans(final PersonalLoanConfig instance) {

		Function<PLJsonObject, PersonalLoan> result = null;
		try {
			String packName = instance.getCategory().getClass().getPackage().getName();
			if(!ReflectionUtils.peekClass(packName + "." + instance.getCategory().name())){
				packName = "com.cgg.pl.purposeCategory";
			}
			Class<?> clz = ReflectionUtils.loadClass(packName + "." + instance.getProductCategory().name());
			Method m = ReflectionUtils.getMethod(clz, "getPersonalLoansFromJsonObjects");
			result = (Function<PLJsonObject, PersonalLoan>) m.invoke(clz, instance);
		} catch (Exception e) {

		}

		return result;
	}
	
	public static void sortPersonalLoans(final Category category, List<PersonalLoan> results) {

		try {
			String packName = category.getClass().getPackage().getName();
			if(!ReflectionUtils.peekClass(packName + "." + category.name())){
				packName = "com.cgg.pl.purposeCategory";
			}
			Class<?> clz = ReflectionUtils.loadClass(packName + "." + category.name());
			Method m = ReflectionUtils.getMethod(clz, "sortPersonalLoans");
			m.invoke(clz.newInstance(), results, category.getDefaultSortingKeyword());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static List<SortingKeyword> getSortingKeywords(final Category category) {

		List<SortingKeyword> keywords = Lists.newArrayList();
		try {
			String packName = category.getClass().getPackage().getName();
			if(!ReflectionUtils.peekClass(packName + "." + category.name())){
				packName = "com.cgg.pl.purposeCategory";
			}
			Class<?> clz = ReflectionUtils.loadClass(packName + "." + category.name());
			Method m = ReflectionUtils.getMethod(clz, "getSortingKeywords");
			keywords = (List<SortingKeyword>)m.invoke(clz.newInstance(), category.getDefaultSortingKeyword());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return keywords;
	}

	
	public PLAPIJsonObjects getResultObjectsFromAPI(final PersonalLoanConfig instance) {

		String API = instance.constructPersonalLoanAPI();
		System.out.println("API url:" + API);
		String jsonStr = WebDriverUtils.callRESTAPI(API);
		return (PLAPIJsonObjects)IOUtils.loadJsonObjects(jsonStr, PLAPIJsonObjects.class, new PLAPIJsonObjectDeserializer(instance.getCategory(), instance.getAlfrescoSetup().name()));
	}

	public List<PersonalLoan> getResultObjectsFromJsonFile(final PersonalLoanConfig config) {

		PersonalLoanConfig instance = new PersonalLoanConfig(config);
		
		this.setJsonObjects(instance.getPlJsonObjects().getJsonObjects());

		Predicate<PLJsonObject> predicates = Predicates.and(PLJsonObject.IS_ACTIVE,
				PLJsonObject.IS_CATEGORY_TYPE(instance.getCategory().getJsonText()),
				PLJsonObject.IS_VALID_LOAN_AMOUNT(instance.getLoanAmount()),
				PLJsonObject.IS_VALID_LOAN_TENURE(instance.getLoanTenure_Month()));
		if (!Checker.isBlank(instance.getLanguage())) {
			predicates = Predicates.and(predicates, PLJsonObject.IS_SPECIFIED_LANGUAGE(instance.getLanguage()));
		}

		List<PLJsonObject> JsonObjects_temp = FluentIterable.from(JsonObjects).filter(predicates).toList();
		
		// From PLJsonObject to PersonalLoan
		Multimap<String, PLJsonObject> maps = ArrayListMultimap.create();
		for(PLJsonObject obj: JsonObjects_temp){
			if(obj.getCategories().size() > 0){
				maps.put(obj.getCategories().get(0).getCategoryType(), obj);
			}
		}
		
		List<PersonalLoan> objs = Lists.newArrayList();
		for(String category: maps.keySet()){
			Category categoryTemp = Category.fromJsonText(category, config.getAlfrescoSetup().name());
			List<PLJsonObject> pls = (List<PLJsonObject>)maps.get(category);

			if(categoryTemp != null && pls!= null){
				instance.setProductCategory(categoryTemp);
				instance.setPurposeCategory(instance.getCategory().name());
				
				objs.addAll(FluentIterable.from(pls).transform(this.mapPLJsonObjectsToPersonalLoans(instance))
						.filter(Predicates.notNull()).toList());	
			}
		}

		List<PersonalLoan> results = Lists.newArrayList(objs);
		// Add sponsored product
		for (PersonalLoan obj : objs) {
			if (obj.isSponsoredProduct()) {
				PersonalLoan copy = org.apache.commons.lang3.SerializationUtils.clone(obj);
				copy.setSponsoredProduct(false);
				results.add(copy);
			}
		}

		// Sort keywords
		this.sortPersonalLoans(instance.getCategory(), results);
		
		return results;
	}

	public PLJsonObjects loadJsonObjects(final PersonalLoanConfig config) {

		return cache.containsKey(config) ? cache.get(config)
				: (PLJsonObjects) IOUtils.loadJsonObjects(
						config.getAlfrescoSetup().getPLJsonFileFromAlfresco(config.getUrl()), PLJsonObjects.class,
						new PLJsonObjectDeserializer());
	}

	public List<Double> getSortedLoanAmounts() {

		Set<Double> temp = Sets.newHashSet();
		for (PLJsonObject obj : this.getJsonObjects()) {
			for (Interest interest : obj.getInterests()) {
				temp.add(interest.getLoanAmountMin());
				temp.add(interest.getLoanAmountMax());
			}
		}
		List<Double> list = Lists.newArrayList(temp);
		Collections.sort(list);
		this.loanAmounts = list;
		return this.loanAmounts;
	}

	public List<Double> getSortedLoanTenures() {

		Set<Double> temp = Sets.newHashSet();
		for (PLJsonObject obj : this.getJsonObjects()) {
			for (Interest interest : obj.getInterests()) {
				temp.add(interest.getLoanTenureMin());
				temp.add(interest.getLoanTenureMax());
			}
		}
		List<Double> list = Lists.newArrayList(temp);
		Collections.sort(list);
		this.loanTenures = list;
		return this.loanTenures;
	}

	public static List<Double> getCriticalValues(List<Double> values, Double minInclusive, Double maxExclusive) {

		List<Double> criticalValues = Lists.newArrayList();
		
		//Fix inputs: boundary values
		for (Double num : values) {
			criticalValues.add(num - 1);
			criticalValues.add(num);
			criticalValues.add(num + 1);
		}
		
		//Random inputs: increase possibility to find bugs
		for(int i = 0; i < values.size() - 1; i ++){
			Double num1 = values.get(i);
			Double num2 = values.get(i + 1);
			criticalValues.add(MathUtils.getRandomValue(num1, num2));
		}

		List<Double> result = criticalValues.stream().distinct().filter(input -> input != null
				&& MathUtils.isNotLessThan(input, minInclusive) && MathUtils.isLargerThan(maxExclusive, input))
				.collect(Collectors.toList());

		return result;
	}
}
