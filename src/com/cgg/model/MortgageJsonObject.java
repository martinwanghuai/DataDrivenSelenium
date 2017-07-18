package com.cgg.model;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.cgg.model.MortgageJsonObject.Mortgage.Product;
import com.cgg.model.PLJsonObject.Fee;
import com.cgg.model.PLJsonObject.Interest;
import com.cgg.model.PLJsonObject.Locale;
import com.cgg.model.PLJsonObject.MinimumRequirement;
import com.cgg.pl.category.Category;
import com.cgg.pl.category.PersonalLoan;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

import utility.Checker;
import utility.IOUtils;
import utility.ReflectionUtils;
import utility.WebDriverUtils;

public final class MortgageJsonObject{
    public final Mortgage mortgage;

    @JsonCreator
    public MortgageJsonObject(@JsonProperty("mortgage") Mortgage mortgage){
        this.mortgage = mortgage;
    }

    public static final class Mortgage {
        public Product products[];
        public final EuriborInterestRate euriborInterestRates[];
        public final HealthInsurance healthInsurances[];
        public final MultiRiskInsurance multiRiskInsurances[];
        public List<Double> loanAmounts;
        public List<Double> loanTenure_months;
        
        public List<Double> getSortedLoanAmounts() {

    		Set<Double> temp = Sets.newHashSet();
    		for (Product obj : this.products) {
    			System.out.println(obj.id);
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
    		for (Product obj : this.products) {
    			for (Interest interest : obj.getInterests()) {
    				temp.add(interest.getLoanTenureMax());
    				temp.add(interest.getLoanTenureMin());
    			}
    		}
    		List<Double> list = Lists.newArrayList(temp);
    		Collections.sort(list);
    		this.loanTenure_months = list;
    		return this.loanTenure_months;
    	}        
        
        @JsonCreator
        public Mortgage(@JsonProperty("products") Product[] products, @JsonProperty("euriborInterestRates") EuriborInterestRate[] euriborInterestRates, @JsonProperty("healthInsurances") HealthInsurance[] healthInsurances, @JsonProperty("multiRiskInsurances") MultiRiskInsurance[] multiRiskInsurances){
            this.products = products;
            this.euriborInterestRates = euriborInterestRates;
            this.healthInsurances = healthInsurances;
            this.multiRiskInsurances = multiRiskInsurances;
        }

        public static final class Product extends PLJsonObject{

            public final String rateType;
            public final List<HighInterest> highInterests;
            public final List<MediumInterest> mediumInterests;
            public final List<LowInterest> lowInterests;
            public final String minReqOverride;
            public final long euriborIndexMonth;
            public final boolean hasApplicationForm;
            public final boolean isApplicationFormGeneric;
            public double euribor;
            public double healthInsurance;
            public double multiRiskInsuranceValue;
            
            @JsonCreator
			public Product(@JsonProperty("id") String id, @JsonProperty("isActive") boolean isActive,
					@JsonProperty("showDesktopApplyButton") boolean showDesktopApplyButton,
					@JsonProperty("showMobileApplyButton") boolean showMobileApplyButton,
					@JsonProperty("showTabletApplyButton") boolean showTabletApplyButton,
					@JsonProperty("showCTALeadCapture") boolean showCTALeadCapture,
					@JsonProperty("bannerType") String bannerType, @JsonProperty("categories") Category[] categories,
					@JsonProperty("rateType") String rateType, @JsonProperty("interests") Interest[] interests,
					@JsonProperty("highInterests") HighInterest[] highInterests,
					@JsonProperty("mediumInterests") MediumInterest[] mediumInterests,
					@JsonProperty("lowInterests") LowInterest[] lowInterests, @JsonProperty("locales") Locale[] locales,
					@JsonProperty("fee") Fee fee,
					@JsonProperty("minimumRequirements") MinimumRequirement[] minimumRequirements,
					@JsonProperty("lastUpdated") String lastUpdated,
					@JsonProperty("minReqOverride") String minReqOverride,
					@JsonProperty("calculateApr") boolean calculateApr,
					@JsonProperty("euriborIndexMonth") long euriborIndexMonth,
					@JsonProperty("hasApplicationForm") boolean hasApplicationForm,
					@JsonProperty("isApplicationFormGeneric") boolean isApplicationFormGeneric,
					@JsonProperty("providerId") String providerId) {
                this.id = id;
                this.isActive = isActive;
                this.showDesktopApplyButton = showDesktopApplyButton;
                this.showMobileApplyButton = showMobileApplyButton;
                this.showTabletApplyButton = showTabletApplyButton;
                this.showCTALeadCapture = showCTALeadCapture;
                this.bannerType = bannerType;
                this.categories = Lists.newArrayList(categories);
                this.rateType = rateType;
                this.interests = Lists.newArrayList(interests);
                this.highInterests = Lists.newArrayList(highInterests);
                this.mediumInterests = Lists.newArrayList(mediumInterests);
                this.lowInterests = Lists.newArrayList(lowInterests);
                this.locales = Lists.newArrayList(locales);
                this.fee = fee;
                this.minimumRequirements = Lists.newArrayList(minimumRequirements);
                this.lastUpdated = lastUpdated;
                this.minReqOverride = minReqOverride;
                this.calculateApr = calculateApr;
                this.euriborIndexMonth = euriborIndexMonth;
                this.hasApplicationForm = hasApplicationForm;
                this.isApplicationFormGeneric = isApplicationFormGeneric;
                this.providerId = providerId;
            }
    
            public static final class HighInterest {
        
                @JsonCreator
                public HighInterest(){
                }
            }
    
            public static final class MediumInterest {
        
                @JsonCreator
                public MediumInterest(){
                }
            }
    
            public static final class LowInterest {
        
                @JsonCreator
                public LowInterest(){
                }
            }

			public double getEuribor() {
				return euribor;
			}

			public void setEuribor(double euribor) {
				this.euribor = euribor;
			}

			public double getHealthInsurance() {
				return healthInsurance;
			}

			public void setHealthInsurance(double healthInsurance) {
				this.healthInsurance = healthInsurance;
			}

			public String getRateType() {
				return rateType;
			}

			public List<HighInterest> getHighInterests() {
				return highInterests;
			}

			public List<MediumInterest> getMediumInterests() {
				return mediumInterests;
			}

			public List<LowInterest> getLowInterests() {
				return lowInterests;
			}

			public String getMinReqOverride() {
				return minReqOverride;
			}

			public long getEuriborIndexMonth() {
				return euriborIndexMonth;
			}

			public boolean isHasApplicationForm() {
				return hasApplicationForm;
			}

			public boolean isApplicationFormGeneric() {
				return isApplicationFormGeneric;
			}

			public double getMultiRiskInsuranceValue() {
				return multiRiskInsuranceValue;
			}

			public void setMultiRiskInsuranceValue(double multiRiskInsuranceValue) {
				this.multiRiskInsuranceValue = multiRiskInsuranceValue;
			}
        }

        public static final class EuriborInterestRate {
            public final long indexMonth;
            public final double value;
    
            @JsonCreator
            public EuriborInterestRate(@JsonProperty("indexMonth") long indexMonth, @JsonProperty("value") double value){
                this.indexMonth = indexMonth;
                this.value = value;
            }
        }

        public static final class HealthInsurance {
            public final long loanAmountMax;
            public final long loanAmountMin;
            public final long loanTenureMin;
            public final long loanTenureMax;
            public final double value;
    
            @JsonCreator
            public HealthInsurance(@JsonProperty("loanAmountMax") long loanAmountMax, @JsonProperty("loanAmountMin") long loanAmountMin, @JsonProperty("loanTenureMin") long loanTenureMin, @JsonProperty("loanTenureMax") long loanTenureMax, @JsonProperty("value") double value){
                this.loanAmountMax = loanAmountMax;
                this.loanAmountMin = loanAmountMin;
                this.loanTenureMin = loanTenureMin;
                this.loanTenureMax = loanTenureMax;
                this.value = value;
            }

			public long getLoanAmountMax() {
				return loanAmountMax;
			}

			public long getLoanAmountMin() {
				return loanAmountMin;
			}

			public long getLoanTenureMin() {
				return loanTenureMin;
			}

			public long getLoanTenureMax() {
				return loanTenureMax;
			}

			public double getValue() {
				return value;
			}
        }

        public static final class MultiRiskInsurance {
            public final long propertyValue;
            public final double value;
    
            @JsonCreator
            public MultiRiskInsurance(@JsonProperty("propertyValue") long propertyValue, @JsonProperty("value") double value){
                this.propertyValue = propertyValue;
                this.value = value;
            }
        }
    }

	public MortgageAPIJsonObjects getMortgagesFromAPI(final PersonalLoanConfig instance) {

		String API = instance.constructMortgageAPI();
		System.out.println("API url:" + API);
		String jsonStr = WebDriverUtils.callRESTAPI(API);
		return (MortgageAPIJsonObjects)IOUtils.loadJsonObjects(jsonStr, MortgageAPIJsonObjects.class);
	}
    
	public PLAPIJsonObjects getResultObjectsFromAPI(final PersonalLoanConfig instance) {

		String API = instance.constructPersonalLoanAPI();
		System.out.println("API url:" + API);
		String jsonStr = WebDriverUtils.callRESTAPI(API);
		return (PLAPIJsonObjects)IOUtils.loadJsonObjects(jsonStr, PLAPIJsonObjects.class, new PLAPIJsonObjectDeserializer(instance.getCategory(), instance.getAlfrescoSetup().name()));
	}
    
    public List<PersonalLoan> getResultObjectsFromJsonFile(final PersonalLoanConfig config) {

		PersonalLoanConfig instance = new PersonalLoanConfig(config);
		
		this.mortgage.products = instance.getMgJsonObjects().mortgage.products;

		Predicate<PLJsonObject> predicates = Predicates.and(PLJsonObject.IS_ACTIVE,
				PLJsonObject.IS_CATEGORY_TYPE(instance.getCategory().getJsonText()),
				PLJsonObject.IS_VALID_LOAN_AMOUNT(isMortgage(instance) ? 
						instance.getLoanAmount()* instance.getBorrowPercentage()/100.0 : instance.getLoanAmount()),
				PLJsonObject.IS_VALID_LOAN_TENURE(isMortgage(instance) ? 
						instance.getLoanTenure_Year() : instance.getLoanTenure_Month()));
		
		if (!Checker.isBlank(instance.getLanguage())) {
			predicates = Predicates.and(predicates, PLJsonObject.IS_SPECIFIED_LANGUAGE(instance.getLanguage()));
		}

		List<Product> JsonObjects_temp = FluentIterable.from(Lists.newArrayList(this.mortgage.products)).filter(predicates).toList();
		System.out.println(JsonObjects_temp.size());
		// From Product to PersonalLoan
		Multimap<String, Product> maps = ArrayListMultimap.create();
		for(Product obj: JsonObjects_temp){
			if(obj.getCategories().size() > 0){
				maps.put(obj.getCategories().get(0).getCategoryType(), obj);
			}
		}
		
		List<PersonalLoan> objs = Lists.newArrayList();
		for(String category: maps.keySet()){
			Category categoryTemp = Category.fromJsonText(category, config.getAlfrescoSetup().name());
			List<Product> pls = (List<Product>)maps.get(category);

			if(categoryTemp != null){
				instance.setProductCategory(categoryTemp);
				instance.setPurposeCategory(instance.getCategory().name());
				
				objs.addAll(FluentIterable.from(pls).transform(this.mapMGJsonObjectsToPersonalLoans(instance, this.mortgage))
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
		PLJsonObjects.sortPersonalLoans(instance.getCategory(), results);
		
		return results;
	}

	public boolean isMortgage(PersonalLoanConfig instance) {
		return instance.getCategory().getJsonText().equalsIgnoreCase("all");
	}

    private Function<Product, PersonalLoan> mapMGJsonObjectsToPersonalLoans(final PersonalLoanConfig instance, final Mortgage mortgage) {

		Function<Product, PersonalLoan> result = null;
		try {
			String packName = instance.getCategory().getClass().getPackage().getName();
			if(!ReflectionUtils.peekClass(packName + "." + instance.getCategory().name())){
				packName = "com.cgg.pl.purposeCategory";
			}
			Class<?> clz = ReflectionUtils.loadClass(packName + "." + instance.getProductCategory().name());
			Method m = ReflectionUtils.getMethod(clz, "getPersonalLoansFromJsonObjects");
			result = (Function<Product, PersonalLoan>) m.invoke(clz, instance, mortgage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
