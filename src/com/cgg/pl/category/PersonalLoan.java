package com.cgg.pl.category;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.cgg.model.MortgageAPIJsonObjects;
import com.cgg.model.PLAPIJsonObject;
import com.cgg.model.PLJsonObject;
import com.cgg.model.PersonalLoanConfig;
import com.cgg.model.TestCase;
import com.cgg.model.PLJsonObject.Interest;
import com.cgg.model.PLJsonObject.Locale;
import com.cgg.model.PLJsonObject.MinimumRequirement;
import com.cgg.model.PLJsonObject.MonthlyCashRebate;
import com.cgg.model.PLJsonObject.MonthlyRebate;
import com.cgg.model.PLJsonObject.Locale.EligibilityFeature;
import com.cgg.model.PLJsonObject.Locale.KeyFeature;
import com.cgg.model.PLJsonObject.Locale.LocaleFee;
import com.google.common.base.Function;
import com.google.common.base.Predicates;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;

import pageObjects.CommonFindObjects;
import utility.Checker;
import utility.Constant;
import utility.MathUtils;
import utility.ReflectionUtils;
import utility.WebDriverUtils;

public class PersonalLoan implements Serializable{
	
	protected static final long serialVersionUID = 1L;
	
	protected String ID;
	protected boolean isSponsoredProduct = false;
	protected String productName;
	protected String productLink;
	protected String provider;
	protected String currency;
	protected List<MinimumRequirement> minRequirements;
	protected double totalRepayment;
	protected double lowestAnnualFlatRate;
	protected double monthlyRepayment;
	protected double annualHandlingFee;
	protected double apr;
	protected String eir;
	protected boolean showApplyBtn;
	protected int featuredRank;
	protected double monthlyInterestRate;
	protected double annualInterestRate;//this is different from APR (See HK for example)
	protected String handlingFeeProfile;
	
	protected List<String> featureDescs;
	protected List<String> feeDescs;
	protected List<EligibilityFeature> eligibilityDescs;
	protected String bannerDesc;
	protected String bestDesc;
	protected String footerDesc;
	protected Locale locale;
	protected Interest interest;
	
	public PersonalLoan(){
	
	}
	
	public PersonalLoan(boolean isSponsoredProduct, double totalRepayment, double APR, double monthlyRepayment){
		
		this.isSponsoredProduct = isSponsoredProduct;
		this.totalRepayment = totalRepayment;
		this.apr = APR;
		this.monthlyRepayment = monthlyRepayment;
	}
	
	public PersonalLoan(PersonalLoan obj){
		
		this.setID(obj.getID());
		this.setSponsoredProduct(obj.isSponsoredProduct());
		this.setProductName(obj.getProductName());
		this.setProvider(obj.getProvider());
		this.setCurrency(obj.getCurrency());
		this.setApr(obj.getApr());
		this.setEir(obj.getEir());
		this.setLowestAnnualFlatRate(obj.getLowestAnnualFlatRate());
		this.setTotalRepayment(obj.getTotalRepayment());
		this.setMonthlyRepayment(obj.getMonthlyRepayment());
		this.setAnnualHandlingFee(obj.getAnnualHandlingFee());
		this.setAnnualInterestRate(obj.getAnnualInterestRate());
		this.setHandlingFeeProfile(obj.getHandlingFeeProfile());
		this.setShowApplyBtn(obj.isShowApplyBtn());
		this.setFeaturedRank(obj.getFeaturedRank());
		this.setFeatureDescs(obj.getFeatureDescs());
		this.setFeeDescs(obj.getFeeDescs());
		this.setEligibilityDescs(obj.getEligibilityDescs());
		this.setBannerDesc(obj.getBannerDesc());
		this.setBestDesc(obj.getBannerDesc());
		this.setFooterDesc(obj.getFooterDesc());
		this.setInterest(obj.getInterest());
		this.setLocale(obj.getLocale());
		this.setMinRequirements(obj.getMinRequirements());
		this.setMonthlyInterestRate(obj.getMonthlyInterestRate());
	}
	
	public PersonalLoan(PLAPIJsonObject obj){

		this.setID(obj.getCggId());
		this.setProductName(obj.getName());
		this.setProvider(obj.getProvider().getCggId());
		this.setBannerDesc(obj.getBanner().getBannerDesc());
		String footerDesc = obj.getFootnotes() == null ? "":String.join("", obj.getFootnotes());
		this.setFooterDesc(footerDesc);
		this.setEligibilityDescs(obj.getEligibilities()); 
		List<String> featureDescs = (List<String>)Stream.concat(obj.getFeatures().stream(), obj.getSpecifications().stream()).collect(Collectors.toList());
		this.setFeatureDescs(featureDescs);

		if(obj.getFee().getFeeDesc()!= null){
			List<String> feeDescs = obj.getFee().getFeeDesc().stream().map(p -> p.getDescription()).collect(Collectors.toList());
			this.setFeeDescs(feeDescs);
		}
		
		this.setBestDesc(obj.getBanner().getBannerDesc());
		this.setShowApplyBtn(obj.getHasDesktopApplyButton() || obj.getHasMobileApplyButton() || obj.getHasTabletApplyButton() || obj.getHasApplyButton() || obj.getShowCTALeadCapture());
	}
	
	public PersonalLoan(MortgageAPIJsonObjects.Product obj){
		
		this.setID(obj.getCggId());
		this.setProductName(obj.getName());
		this.setProvider(obj.getProvider().getCggId());
		this.setBannerDesc(obj.getBanner().getBannerDesc());
		String footerDesc = obj.getFootnote() == null ? "":String.join("", obj.getFootnote());
		this.setFooterDesc(footerDesc);
		List<String> featureDescs = (List<String>)(List<?>)Stream.concat(Arrays.asList(obj.getFeature()).stream(), Arrays.asList(obj.getSpecification()).stream()).collect(Collectors.toList());
		this.setFeatureDescs(featureDescs);

		if(obj.getFee().getFeeDesc()!= null){
			List<String> feeDescs = Arrays.asList(obj.getFee().getFeeDesc()).stream().map(p -> p.getDescription()).collect(Collectors.toList());
			this.setFeeDescs(feeDescs);
		}
		
		this.setBestDesc(obj.getBanner().getBannerDesc());
		this.setShowApplyBtn(obj.isHasDesktopApplyButton() || obj.isHasMobileApplyButton() || obj.isHasTabletApplyButton() || obj.isShowCTALeadCapture());
	}
	
	public static List<PersonalLoan> getPersonalLoansFromUI(WebDriverUtils driver, By by) {

		return Lists.newArrayList(FluentIterable.from(driver.findElements(by)).transform(new Function<WebElement, PersonalLoan>() {
			
			@Override
			public PersonalLoan apply(final WebElement elem){
				
				PersonalLoan obj = new PersonalLoan();
				obj.fromWebElement(driver, elem);
				return obj;
			}
		}));
	}
	
	protected PersonalLoan fromWebElement(final WebDriverUtils driver, final WebElement elem){
		
		this.setIDFromHTML(driver, elem);
		System.out.println("UI:" + this.ID);
		this.setShowApplyBtnFromHTML(driver, elem);
		this.setSponsoredProductFromHTML(driver, elem);
		this.setProductNameFromHTML(driver, elem);
		this.setProviderFromHTML(driver, elem);
		this.setCurrencyFromHTML(driver, elem);
		this.setEirFromHTML(driver, elem);
		
		this.setAPRFromHTML(driver, elem); //Column1
		this.setTotalRepaymentFromHTML(driver, elem);//Column2
		this.setMonthlyRepaymentFromHTML(driver, elem);//Column3
		
		this.setFeatureDescsFromHTML(driver, elem);
		this.setFeeDescsFromHTML(driver, elem);
		this.setEligibilityDescsFromHTML(driver, elem);
		this.setBannerDescFromHTML(driver, elem);
		this.setBestDescFromHTML(driver, elem);
		return this;
	}
	
	protected void setIDFromHTML(final WebDriverUtils driver, final WebElement elem){
		
		this.ID = driver.getAttributeValue(elem, "id");
	}

	protected void setSponsoredProductFromHTML(final WebDriverUtils driver, final WebElement elem){
		
		this.isSponsoredProduct = elem.findElements(By.xpath(".//div[@class='cgg-list-item__sponsored-banner ng-scope']")).size() > 0 ? true: false;
	}
	
	protected void setProductNameFromHTML(final WebDriverUtils driver, final WebElement elem){
									   
		By by = By.xpath(".//a[@class='cgg-secondary cgg-product-description clickable ng-binding ng-scope' or @class='cgg-secondary cgg-product-description ng-binding ng-scope']");
		this.productName = elem.findElement(by).getText();
	}

	protected void setProviderFromHTML(final WebDriverUtils driver, final WebElement elem){
		
		By by = By.xpath(".//img[@class='cgg-list-item__provider-image success' or @class='cgg-list-item__provider-image' or @class='cgg-list-item__provider-image clickable success' or @class='cgg-list-item__provider-image clickable']");  
		this.provider = elem.findElement(by).getAttribute("src");
	}
	
	protected void setEirFromHTML(final WebDriverUtils driver, final WebElement elem){
		
		By by = By.xpath(".//p[@class='cgg-secondary custom-footer ng-binding ng-scope']");
		if(driver.isElementPresent(by, elem)){
			String result = elem.findElement(by).getText();
			this.eir = result; // can be number or (P+.75%)	
		}
	} 
	
	protected void setCurrencyFromHTML(final WebDriverUtils driver, final WebElement elem){
		
		By by = By.xpath(
				".//p[@class='cgg-secondary custom-footer ng-binding ng-scope' and @ng-if='!customFooter && showCurrency && nummericValue && !options.currencyPosition']");
		
		String currency = "";
		if(driver.isElementPresent(by, elem)){
			currency = elem.findElement(by).getText();
			List<WebElement> elems = elem.findElements(by);
			if(elems.size() >  1){
				for(int i = 1; i < elems.size(); i ++){
					Assert.assertTrue(elems.get(i).getText().toLowerCase().equals(currency.toLowerCase()));
				}
			}
		}

		this.currency = currency;
	} 

	protected void setAPRFromHTML(final WebDriverUtils driver, final WebElement elem){
																							  
		By by = By.xpath("./descendant::p[@class='cgg-primary ng-binding ng-scope' or @class='cgg-primary cgg-primary-tablet-portrait ng-binding ng-scope']");
		if(driver.isElementPresent(by, elem)){
			String APR = elem.findElement(by).getText();
			APR = reformatString(driver, APR);
			this.apr = MathUtils.extractDouble(APR);	
		}
	}

	protected void setTotalRepaymentFromHTML(final WebDriverUtils driver, final WebElement elem){
		
		By by = By.xpath("./descendant::p[@class='cgg-primary ng-binding ng-scope' or @class='cgg-primary cgg-primary-tablet-portrait ng-binding ng-scope']");
		List<WebElement> elems = elem.findElements(by);
		if(elems.size() > 1){
			String totalRepayment = elems.get(1).getText();
			totalRepayment = reformatString(driver, totalRepayment);
			this.totalRepayment = MathUtils.extractDouble(totalRepayment);	
		}
	}

	private String reformatString(WebDriverUtils driver, String str) {
		
		final String url = driver.getCurrentUrl();
		boolean transformed = false; 
		if(str.contains(",")){
			if(TestCase.isDKURL(url)|| TestCase.isFIURL(url) || TestCase.isBEURL(url) || TestCase.isNOURL(url) || TestCase.isPTURL(url)){
				str = str.replaceAll("\\,", "."); // for Denmark, Finland, Belgium
				transformed = true;
			}else{
				str = str.replaceAll("\\,", ""); // not the case for Denmark
			}	
		}
		
		if(str.contains(".")){
			if(!transformed && (TestCase.isBEURL(url)|| TestCase.isNOURL(url) || TestCase.isPTURL(url))){
				str = str.replaceAll("\\.", ""); // for Belgium total/monthly repayment
			}	
		}
		
		return str;
	} 
	
	protected void setShowApplyBtnFromHTML(final WebDriverUtils driver, final WebElement elem){
		
		boolean hasApplyBtn = elem.findElements(By.xpath(".//a[@class='cgg-button-action cgg-hidden-xs ng-binding' or @class='cgg-button-action cgg-hidden-xs ng-binding ng-scope']")).size() > 0 ? true: false;
		boolean hasApplyLink = elem.findElements(By.xpath(".//a[@class='cgg-visit-company-link cgg-visit-company-link-result ng-binding']")).size() > 0 ? true: false;
		if(hasApplyBtn && !hasApplyLink){
			String msg = "has apply btn but no apply link";
			System.out.println(msg);
			CommonFindObjects.loggerMethodFailure(msg);	
		}
		this.setShowApplyBtn(hasApplyBtn);
	}
	
	protected void expandMoreDetails(final WebDriverUtils driver, final WebElement elem){
																									  
		WebElement moreDetails = elem.findElement(By.xpath(".//p[@class='cgg-more-details' or @class='cgg-more-details cgg-col-md-3' or @class='cgg-more-details cgg-col-md-2']/span[1]"));
		if(moreDetails.getAttribute("class").equalsIgnoreCase("ng-binding ng-hide")){
			driver.clickLink(moreDetails);
		}
	}
	
	protected void setFeatureDescsFromHTML(final WebDriverUtils driver, final WebElement elem){
		
		this.expandMoreDetails(driver, elem);
		
		List<WebElement> elems = elem.findElements(By.xpath(
				".//div[@pros='pl.feature' and @cons='pl.specification' or @pros='::pl.feature' and @cons='::pl.specification']/descendant::div[@class='cgg-pros-cons-description ng-binding']"));
		this.setFeatureDescs(FluentIterable.from(elems).transform(new Function<WebElement, String>(){
			
			@Override
			public String apply(WebElement obj){
				return obj.getText();
			}
		}).toList());
	}

	protected void expandEIR(final WebDriverUtils driver, final WebElement elem){
		
		this.expandMoreDetails(driver, elem);
		By by = By.xpath(".//div[@options='infoSections.fees']/descendant::div[@class='cgg-info-section-title ng-binding']/i");
		if(driver.isElementPresent(by, elem)){
			WebElement eir = elem.findElement(by);
			if(!eir.getAttribute("class").contains("cgg-rotate180")){
				driver.clickLink(eir);	
			}
		}
	}
	
	protected void expandEligibility(final WebDriverUtils driver, final WebElement elem){
		
		this.expandMoreDetails(driver, elem);
		By by = By.xpath(".//div[@options='infoSections.eligibility']/descendant::div[@class='cgg-info-section-title ng-binding']/i");
		if(driver.isElementPresent(by, elem)){
			WebElement eligibility = elem.findElement(by);
			if(!eligibility.getAttribute("class").contains("cgg-rotate180")){
				driver.clickLink(eligibility);
			}	
		}
	}
	
	protected void setEligibilityDescsFromHTML(final WebDriverUtils driver, final WebElement elem){
		
		this.expandEligibility(driver, elem);
		
		By by = By.xpath(".//div[@options='::infoSections.eligibility']");
		if(driver.isElementPresent(by, elem)){
			WebElement eligibility = elem.findElement(by);
			List<WebElement> requirements = eligibility.findElements(By.xpath(".//div[@class='requirement ng-scope']"));
			List<EligibilityFeature> eligibilities = Lists.newArrayList();
			if(requirements != null && requirements.size() > 0 ){
				eligibilities = FluentIterable.from(requirements).transform(new Function<WebElement, EligibilityFeature>(){
					
					@Override
					public EligibilityFeature apply(WebElement obj){
						
						String key = obj.findElement(By.xpath(".//div[@class='key ng-binding']")).getText();
						int value = (int)MathUtils.extractDouble(obj.findElement(By.xpath(".//div[@class='val']/descendant::span[@class='ng-binding ng-scope']")).getText());
						return new EligibilityFeature(key, value + "");
					}
				}).toList();
			}
			this.setEligibilityDescs(eligibilities);
		}
	}

	protected void setFeeDescsFromHTML(final WebDriverUtils driver, final WebElement elem){
		
		this.expandEIR(driver, elem);
		
		List<WebElement> elemList = elem.findElements(By.xpath(".//div[@class='cgg-tab-pane__group ng-scope']"));
		if(elemList.size() > 1){
			WebElement EIR = elemList.get(1);
			List<WebElement> elems = EIR.findElements(By.xpath(".//div[@class='cgg-row ng-scope']/p[@class='ng-binding']"));
			
			if(elems.size() > 0){
				this.setFeeDescs(FluentIterable.from(elems).transform(new Function<WebElement, String>(){
					
					@Override
					public String apply(WebElement obj){
						return obj.getText();
					}
				}).toList());	
			}
		}
		
	}
	
	protected void setBannerDescFromHTML(final WebDriverUtils driver, final WebElement elem){
		
		By by = By.xpath(
				".//div[@class='cgg-banner-description-exclusive' "
				+ "or @class='cgg-banner-description-popular' "
				+ "or @class='cgg-banner-description cgg-banner-description-exclusive' "
				+ "or @class='cgg-banner-description cgg-banner-description-welcomeGift' "
				+ "or @class='cgg-banner-description cgg-banner-description-popular'"
				+ "or @class='cgg-banner-description-welcomeGift']");
		if(driver.isElementPresent(by, elem)){
			bannerDesc = elem.findElement(by).getText();
			this.setBannerDesc(bannerDesc);	
		}
	}
	
	protected void setBestDescFromHTML(final WebDriverUtils driver, final WebElement elem){
		
		By by = By.xpath(
				".//descendant::a[@class='cgg-product-bestdeals-description ng-scope']/span");
		if(driver.isElementPresent(by, elem)){
			bestDesc = elem.findElement(by).getText();
			this.setBestDesc(bestDesc);	
		}
	}
	
	protected void setFooterDescFromHTML(final WebDriverUtils driver, final WebElement elem){
		
		By by = By.xpath(
				".//descendant::a[@class='cgg-details-action clickable ng-binding ng-scope']/i");
		if(driver.isElementPresent(by, elem)){
			footerDesc = elem.findElement(by).getText();
			this.setBestDesc(footerDesc);	
		}
		
	}
	
	protected void setMonthlyRepaymentFromHTML(final WebDriverUtils driver, final WebElement elem){
		
		By by = By.xpath("./descendant::p[@class='cgg-primary ng-binding ng-scope' or @class='cgg-primary cgg-primary-tablet-portrait ng-binding ng-scope']");
		List<WebElement> elems = elem.findElements(by);
		if(elems.size() > 2){
			String monthlyRepayment = elems.get(2).getText();
			
			monthlyRepayment = monthlyRepayment.replaceAll("\\,", ""); //for DK/MX
			String url = driver.getCurrentUrl();
			if(TestCase.isDKURL(url)|| TestCase.isPTURL(url) || TestCase.isBEURL(url)|| TestCase.isNOURL(url)){//need to specify DK/PT since it conflicts with SG/HK, so 
				monthlyRepayment = monthlyRepayment.replaceAll("\\.", ""); //for DK	
			}
			if(TestCase.isFIURL(url)){
				monthlyRepayment = monthlyRepayment.replaceAll(" ", ""); 
				monthlyRepayment = monthlyRepayment.replaceAll("€", "");
			}
			this.monthlyRepayment = MathUtils.extractDouble(monthlyRepayment);	
		}
	}
	


	public boolean isSponsoredProduct() {
		return isSponsoredProduct;
	}

	public String getProductName() {
		return productName;
	}

	public String getProvider() {
		return provider;
	}

	public String getCurrency() {
		return currency;
	}

	public Double getTotalRepayment() {
		return totalRepayment;
	}

	public double getLowestAnnualFlatRate() {
		return lowestAnnualFlatRate;
	}

	public Double getMonthlyRepayment() {
		return monthlyRepayment;
	} 
	
	public Double getApr() {
		return apr;
	}

	public void setApr(double apr) {
		this.apr = apr;
	}

	public void setSponsoredProduct(boolean isSponsoredProduct) {
		this.isSponsoredProduct = isSponsoredProduct;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public void setLowestAnnualFlatRate(double lowestAnnualFlatRate) {
		this.lowestAnnualFlatRate = lowestAnnualFlatRate;
	}

	public void setMonthlyRepayment(double monthlyRepayment) {
		this.monthlyRepayment = monthlyRepayment;
	}

	public String getEir() {
		return eir;
	}

	public void setEir(String eir) {
		this.eir = eir;
	}

	

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public void setTotalRepayment(double totalRepayment) {
		this.totalRepayment = totalRepayment;
	}

	public String getProductLink() {
		return productLink;
	}

	public void setProductLink(String productLink) {
		this.productLink = productLink;
	}

	public boolean isShowApplyBtn() {
		return showApplyBtn;
	}

	public void setShowApplyBtn(boolean showApplyBtn) {
		this.showApplyBtn = showApplyBtn;
	}

	public List<String> getFeatureDescs() {
		return featureDescs;
	}

	public void setFeatureDescs(List<String> featureDescs) {
		this.featureDescs = featureDescs;
	}

	public List<String> getFeeDescs() {
		return feeDescs;
	}

	public void setFeeDescs(List<String> feeDescs) {
		this.feeDescs = feeDescs;
	}

	public String getBannerDesc() {
		return bannerDesc;
	}

	public void setBannerDesc(String bannerDesc) {
		this.bannerDesc = bannerDesc;
	}

	public List<EligibilityFeature> getEligibilityDescs() {
		return eligibilityDescs;
	}

	public void setEligibilityDescs(List<EligibilityFeature> eligibilityDescs) {
		this.eligibilityDescs = eligibilityDescs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ID == null) ? 0 : ID.hashCode());
		result = prime * result + ((bannerDesc == null) ? 0 : bannerDesc.hashCode());
		result = prime * result + ((bestDesc == null) ? 0 : bestDesc.hashCode());
		result = prime * result + ((eligibilityDescs == null) ? 0 : eligibilityDescs.hashCode());
		result = prime * result + ((featureDescs == null) ? 0 : featureDescs.hashCode());
		result = prime * result + ((feeDescs == null) ? 0 : feeDescs.hashCode());
		long temp;
		result = prime * result + ((productName == null) ? 0 : productName.hashCode());
		result = prime * result + (showApplyBtn ? 1231 : 1237);
		temp = Double.doubleToLongBits(totalRepayment);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	public boolean sameCommonValues(Object obj_UI) {
		
		if (this == obj_UI) {
			return true;
		}
		if (obj_UI == null) {
			return false;
		}
		if (!(obj_UI instanceof PersonalLoan)) {
			return false;
		}
		PersonalLoan other_UI = (PersonalLoan) obj_UI;
		String tmpMsg = "ID:" + other_UI.getID() + ";Product Name:" + other_UI.getProductName();
		System.out.println(tmpMsg);
		CommonFindObjects.loggerMethodSucess(tmpMsg);
		
		if (Checker.isBlank(ID) && Checker.isBlank(other_UI.ID)){
			return true;
		} else if (Checker.isBlank(ID) && !Checker.isBlank(other_UI.ID)
				|| !Checker.isBlank(ID) && Checker.isBlank(other_UI.ID)) {
			return false;
		} else if (!ID.equals(other_UI.ID)) {
			String msg = "ID mismatch" + "\nJSON - ID: " + ID + "\nUI - ID: " + other_UI.ID + "\n"; 
			System.out.println(msg);
			CommonFindObjects.loggerMethodFailure(msg);
			return false;
		}
		
		
		if(Checker.isBlank(bannerDesc) && Checker.isBlank(other_UI.bannerDesc)){
			return true;
		} else if (Checker.isBlank(bannerDesc) && !Checker.isBlank(other_UI.bannerDesc)
				|| !Checker.isBlank(bannerDesc) && Checker.isBlank(other_UI.bannerDesc)) {
			return false;
		} else if (!bannerDesc.equals(other_UI.bannerDesc)) {
			String msg = "bannerDesc mismatch" + "\nJSON - bannerDesc: " + bannerDesc + "\nUI - bannerDesc: " + other_UI.bannerDesc; 
			System.out.println(msg);
			CommonFindObjects.loggerMethodFailure(msg);
			return false;
		}
		
		if (Checker.isBlank(bestDesc)&& Checker.isBlank(other_UI.bestDesc)){
			return true;
		} else if (Checker.isBlank(bestDesc)&& !Checker.isBlank(other_UI.bestDesc)
				|| !Checker.isBlank(bestDesc)&&Checker.isBlank(other_UI.bestDesc)) {
			return false;
		} else if (!bestDesc.equals(other_UI.bestDesc)) {
			String msg = "bestDesc mismatch" + "\nJSON - bestDesc:" + bestDesc + "\nUI - bestDesc:" + other_UI.bestDesc; 
			System.out.println(msg);
			CommonFindObjects.loggerMethodFailure(msg);
			return false;
		}
		
		/*if (Checker.isBlank(footerDesc)) {
			if (!Checker.isBlank(other_UI.footerDesc)) {
				return false;
			}
		} else if (!footerDesc.equals(other_UI.footerDesc)) {
			String msg = "footerDesc mismatch" + "\nJSON - footerDesc:" + footerDesc + "\nUI - footerDesc:" + other_UI.footerDesc; 
			System.out.println(msg);
			CommonFindObjects.loggerMethodFailure(msg);
			return false;
		}*/
		if (Checker.isEmpty(eligibilityDescs) && !Checker.isEmpty(other_UI.eligibilityDescs)){
			return true;
		} else if (Checker.isEmpty(eligibilityDescs) && !Checker.isEmpty(other_UI.eligibilityDescs) 
				|| !Checker.isEmpty(eligibilityDescs) && Checker.isEmpty(other_UI.eligibilityDescs)) {
			return false;
		} else if (!eligibilityDescs.equals(other_UI.eligibilityDescs)) {
			StringBuilder sb = new StringBuilder();
			sb.append("eligibilityDescs mismatch");
			
			for(int i = 0; i < eligibilityDescs.size(); i ++){
				String string = eligibilityDescs.get(i).getDescription() + "";
				String x = other_UI.eligibilityDescs.get(i).getDescription() + "";
				if(!x.trim().equalsIgnoreCase(string.trim())){
					sb.append("\nJSON - eligibilityDescs:" + string + ":"+ string.length());
					sb.append("\nUI - eligibilityDescs:" + x +":" +x.length());
					System.out.println(sb.toString());
					CommonFindObjects.loggerMethodFailure(sb.toString());
					return false;
				}
			}
			return false;
		}
		
		if (Checker.isEmpty(featureDescs) && Checker.isEmpty(other_UI.featureDescs)){
			return true;
		} else if (Checker.isEmpty(featureDescs) && !Checker.isEmpty(other_UI.featureDescs)
				|| !Checker.isEmpty(featureDescs) && Checker.isEmpty(other_UI.featureDescs)) {
			return false;
		} else if (!featureDescs.equals(other_UI.featureDescs)) {

			StringBuilder sb = new StringBuilder();
			sb.append("featureDescs mismatch");
			
			for(int i = 0; i < featureDescs.size(); i ++){
				String string = featureDescs.get(i).replaceAll("\\s", "");
				if(!Checker.isBlank(other_UI.featureDescs.get(i))){
					String x = other_UI.featureDescs.get(i).replaceAll("\\s", "");
					if(!x.trim().equalsIgnoreCase(string.trim())){
						sb.append("\nJSON - featureDescs:" + string + ":"+ string.length());
						sb.append("\nUI - featureDescs:" + x +":" +x.length());
						System.out.println(sb.toString());
						CommonFindObjects.loggerMethodFailure(sb.toString());
						return false;
					}	
				}
			}
		}
		
		if (Checker.isEmpty(feeDescs) && Checker.isEmpty(other_UI.feeDescs)){
			return true;
		} else if (Checker.isEmpty(feeDescs) && !Checker.isEmpty(other_UI.feeDescs)
				|| !Checker.isEmpty(feeDescs) && Checker.isEmpty(other_UI.feeDescs)) {
			return false;
		} else{
			List<List<String>> feeDescs_Partitioned = Lists.newArrayList();
			if(feeDescs.size() > 5){
				feeDescs_Partitioned = Lists.partition(feeDescs, 5);	
			}else{
				feeDescs_Partitioned.add(feeDescs);	
			}

			if (!feeDescs_Partitioned.get(0).equals(other_UI.feeDescs)) {
				StringBuilder sb = new StringBuilder();
				sb.append("feeDescs mismatch");
				
				for(int i = 0; i < feeDescs.size(); i ++){
					String string = feeDescs.get(i).replaceAll("\\s", "");
					String x = other_UI.feeDescs.get(i).replaceAll("\\s", "");
					if(!x.trim().equalsIgnoreCase(string.trim())){
						sb.append("\nJSON - feeDescs:" + string + ":"+ string.length());
						sb.append("\nUI - feeDescs:" + x +":" +x.length());
						System.out.println(sb.toString());
						CommonFindObjects.loggerMethodFailure(sb.toString());
						return false;
					}
				}
				
				return false;
			}
		}
		
		if (Checker.isBlank(productName) && !Checker.isBlank(other_UI.productName)){
			return true;
		} else if (Checker.isBlank(productName) && !Checker.isBlank(other_UI.productName)
				|| !Checker.isBlank(productName) && Checker.isBlank(other_UI.productName)) {
			return false;
		} else if (!productName.equals(other_UI.productName)) {
			String msg = "productName mismatch" + "\nJSON - productName:" + productName + "\nUI - productName:" + other_UI.productName; 
			System.out.println(msg);
			CommonFindObjects.loggerMethodFailure(msg);
			return false;
		}
		
		if (showApplyBtn != other_UI.showApplyBtn) {
			String msg = "showApplyBtn mismatch" + "\nJSON - showApplyBtn:" + showApplyBtn + "\nUI - showApplyBtn:" + other_UI.showApplyBtn; 
			System.out.println(msg);
			CommonFindObjects.loggerMethodFailure(msg);
			return false;
		}
		return true;
	}
	
	@Override
	public boolean equals(Object obj_UI) {
		
		if(!this.sameCommonValues(obj_UI)){
			return sameSpecificValues(obj_UI);
		}
		
		return sameSpecificValues(obj_UI);
	}
	
	public boolean sameSpecificValues(Object obj_UI){
		
		return true;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [isSponsoredProduct=" + isSponsoredProduct + ", lowestAnnualFlatRate="
				+ lowestAnnualFlatRate + ", totalRepayment=" + totalRepayment + ", monthlyRepayment=" + monthlyRepayment
				+ ", provider=" + provider + ", productName=" + productName + ", ID=" + ID + ", productLink="
				+ productLink + ", currency=" + currency + ", minRequirements=" + minRequirements + ", apr=" + apr
				+ ", eir=" + eir + ", showApplyBtn=" + showApplyBtn + ", featureDescs=" + featureDescs + ", feeDescs=" + feeDescs + ", eligibilityDescs=" + eligibilityDescs + ", bannerDesc="
				+ bannerDesc + "]";
	}

	public int getFeaturedRank() {
		return featuredRank;
	}

	public void setFeaturedRank(int featuredRank) {
		this.featuredRank = featuredRank;
	}

	public double getAnnualHandlingFee() {
		return annualHandlingFee;
	}

	public void setAnnualHandlingFee(double annualHandlingFee) {
		this.annualHandlingFee = annualHandlingFee;
	}

	public String getBestDesc() {
		return bestDesc;
	}

	public void setBestDesc(String bestDesc) {
		this.bestDesc = bestDesc;
	}

	public double getAnnualInterestRate() {
		return annualInterestRate;
	}

	public void setAnnualInterestRate(double annualInterestRate) {
		this.annualInterestRate = annualInterestRate;
	}

	public String getFooterDesc() {
		return footerDesc;
	}

	public void setFooterDesc(String footerDesc) {
		this.footerDesc = footerDesc;
	}

	public String getHandlingFeeProfile() {
		return handlingFeeProfile;
	}

	public void setHandlingFeeProfile(String handlingFeeProfile) {
		this.handlingFeeProfile = handlingFeeProfile;
	}
	
	public static PersonalLoan getPersonalLoansFromJsonObject(final PersonalLoanConfig instance, final PLJsonObject obj) {
		
		PersonalLoan result = new PersonalLoan();
		
		result.setID(obj.getId());
		System.out.println("Json file:" + result.getID());
		result.setProvider(obj.getProviderId());
		result.setShowApplyBtn(obj.isShowDesktopApplyButton() || obj.isShowMobileApplyButton() || obj.isShowTabletApplyButton() || obj.isShowApplyButton() || obj.isShowApplyConfirmation() || obj.isShowCTALeadCapture());
		
		//isFeatured
		boolean matchCategory = false;
		
		List<PLJsonObject.Category> categories = obj.getPurposeCategories() ;
		if(categories != null){
			for (PLJsonObject.Category temp : categories) {
				if (temp.getCategoryType() != null && instance.getPurposeCategory().toLowerCase().contains(temp.getCategoryType().toLowerCase())) {
					matchCategory = true;
					result.setSponsoredProduct(temp.isFeatured());
					if (!Checker.isBlank(temp.getFeaturedRank())) {
						result.setFeaturedRank(Integer.parseInt(temp.getFeaturedRank()));
					}
					break;
				}
			}	
		}else{
			categories = obj.getCategories();
			//mortgage
			for(PLJsonObject.Category temp: categories){
				if(temp.getCategoryType().toLowerCase().equals("all") && instance.getCategory().name().toLowerCase().contains("mortgage")){
					matchCategory = true;
					break;
				}
			}
		}
		
		//product name
		Locale locale = null;
		boolean matchLanguage = false;
		if(!Checker.isEmpty(obj.getLocales())){
			for(PLJsonObject.Locale temp: obj.getLocales()){
				if(temp.getLocaleName().toLowerCase().contains(instance.getLanguage().toLowerCase())){
					matchLanguage = true;
					locale = temp;
					result.setLocale(locale);
					result.setFooterDesc(locale.getFooterDesc());
					break;
				}
			}
		}
		
		result.setProductName(Checker.isBlank(locale.getLoanName())? locale.getProductName() :locale.getLoanName());
		result.setProductLink(locale.getLoanLink());
		result.setBannerDesc(locale.getBannerDesc());
		
		List<KeyFeature> newFeatures = locale.getKeyFeatures().stream().filter(p -> !Boolean.parseBoolean(p.getIsNegative())).collect(Collectors.toList());
		newFeatures.addAll(locale.getKeyFeatures().stream().filter(p -> Boolean.parseBoolean(p.getIsNegative())).collect(Collectors.toList()));
		
		if(!Checker.isEmpty(locale.getKeyFeatures())){
			result.setFeatureDescs(FluentIterable.from(newFeatures).transform(new Function<KeyFeature, String>(){
				
				@Override
				public String apply(final KeyFeature obj){
					
					return obj.getDescription();
				}
			}).filter(Predicates.notNull()).toList());	
		}
		
		
		if(!Checker.isEmpty(locale.getFees())){
			result.setFeeDescs(FluentIterable.from(locale.getFees()).transform(new Function<LocaleFee, String>(){
				
				@Override
				public String apply(final LocaleFee obj){
				
					return obj.getDescription();
				}
			}).filter(Predicates.notNull()).toList());	
		}
		
		
		result.setEligibilityDescs(locale.getEligibilityFeatures());
		
		if(obj.getFee() != null){
			result.setAnnualHandlingFee(obj.getFee().getAnnualHandlingFee());
			result.setHandlingFeeProfile(obj.getFee().getHandlingFeeProfile());	
		}
		
		boolean matchInterest = false; 
		for(PLJsonObject.Interest interest: obj.getInterests()){
			if(matchLoanAmount(instance, interest) 
					&& matchLoanTenure(instance, interest)){

				if(obj.getFee() != null){
					result.setLowestAnnualFlatRate(obj.getFee().getAnnualRate());	
				}
				if(obj.getId().equals("41516")){
					System.out.println("here");
				}
				if(!instance.getCategory().name().toLowerCase().contains("mortgage")){
					if(!instance.getCategory().name().toLowerCase().contains("hk")){
						final double apr = interest.getApr();
						result.setApr(MathUtils.round(apr, 4));
						final double totalPayment = instance.getLoanAmount() + (instance.getLoanAmount() * (apr/100.0)*(instance.getLoanTenure_Month()/12));
						result.setTotalRepayment(MathUtils.round(totalPayment, 4));
						final double monthlyPayment = totalPayment/instance.getLoanTenure_Month();
						result.setMonthlyRepayment(MathUtils.round(monthlyPayment, 4));
						result.setAnnualInterestRate(MathUtils.round(interest.getInterestValue(), 4));						
					}else{
						final double monthlyInterestRate = interest.getInterestValue();
						result.setMonthlyInterestRate(MathUtils.round(monthlyInterestRate, 4));

						//1. Calculate monthly repayment
						double total = 0.0;
						if(obj.getFee() != null){
							String profile = obj.getFee().getHandlingFeeProfile();
							if(!Checker.isBlank(profile) && profile.toLowerCase().equals("integrated")){
									total = instance.getLoanAmount()
											+ instance.getLoanAmount() * instance.getLoanTenure_Month()
											* (result.getMonthlyInterestRate() / 100 + obj.getFee().getAnnualHandlingFee() / 1200);	
							}else{
									total = instance.getLoanAmount()
											+ instance.getLoanAmount() * instance.getLoanTenure_Month()
											* (result.getMonthlyInterestRate() / 100);
							}	
						}else{
							total = instance.getLoanAmount()
									+ instance.getLoanAmount() * instance.getLoanTenure_Month()
									* (result.getMonthlyInterestRate() / 100);
						}
						final double monthlyRepayment = total	/ instance.getLoanTenure_Month();
						result.setMonthlyRepayment(MathUtils.round(monthlyRepayment, 4));
						
						//2. Calculate APR
						double apr = 0.0;
						double totalCashRebates = 0.0;
						if(obj.isCalculateApr()){
							double rate = 0.0;
							if(obj.getMonthlyRebates() == null){
								rate = MathUtils.calculateIRR(instance.getLoanAmount(), instance.getLoanTenure_Month(), monthlyRepayment);	
							}else{
								Optional<MonthlyRebate> monthlyRebate = obj.getMonthlyRebates().stream().filter(m -> m.getLoanAmountMin() <= instance.getLoanAmount() && m.getLoanTenureMin() <= instance.getLoanTenure_Month())
										.sorted(Comparator.comparing(MonthlyRebate::getLoanAmountMin)
												.thenComparing(MonthlyRebate::getLoanTenureMin)).findFirst();
								
								if(monthlyRebate.isPresent()){
									for(MonthlyCashRebate monthRebate: monthlyRebate.get().getMonthlyCashRebates()){
										totalCashRebates += monthRebate.getCashRebate();
									}
									double[] monthlyRepayments = new double[instance.getLoanTenure_Month()];
									Arrays.fill(monthlyRepayments, monthlyRepayment);
									for(MonthlyCashRebate monthlyCashRebate: monthlyRebate.get().getMonthlyCashRebates()){
										monthlyRepayments[monthlyCashRebate.getMonthIndex()-1] -= monthlyCashRebate.getCashRebate(); 
									}
									
									rate = MathUtils.calculateIRR(instance.getLoanAmount(), instance.getLoanTenure_Month(), monthlyRepayments);
								}else{
									rate = MathUtils.calculateIRR(instance.getLoanAmount(), instance.getLoanTenure_Month(), monthlyRepayment);
								}
							}
							apr = (Math.pow(1 + rate, 12) - 1) * 100;	
						}else{
							apr = interest.getApr();
						}
						result.setApr(MathUtils.round(apr, 4));
						
						//3. Calculate total repayment
						final double totalPayment = result.getMonthlyRepayment() * instance.getLoanTenure_Month() - totalCashRebates;
						result.setTotalRepayment(MathUtils.round(totalPayment, 4));
					}
				}
				
				result.setInterest(interest);
				matchInterest = true;
				break;
			}
		}

		if(!Checker.isEmpty(obj.getMinimumRequirements())){
			result.setMinRequirements(obj.getMinimumRequirements());
		}
		
		if(Constant.DEBUG_MODE){
			if(matchLanguage && matchCategory && matchInterest){
				return result;
			}else{
				return null;
			}	
		}else{
			return matchLanguage && matchCategory && matchInterest ? result: null;
		}
	}

	public static boolean matchLoanAmount(final PersonalLoanConfig instance, PLJsonObject.Interest interest) {
		
		return MathUtils.isNotLessThan(instance.getLoanAmount(), interest.getLoanAmountMin())
				&& MathUtils.isNotLessThan(interest.getLoanAmountMax(), instance.getLoanAmount());
	}
	
	public static boolean matchLoanTenure(final PersonalLoanConfig instance, PLJsonObject.Interest interest) {
		
		if(instance.getCategory().name().toLowerCase().contains("mortgage")){
			return MathUtils.isNotLessThan(instance.getLoanTenure_Year(), interest.getLoanTenureMin())
					&& MathUtils.isNotLessThan(interest.getLoanTenureMax(), instance.getLoanTenure_Year());
		}else{
			return MathUtils.isNotLessThan(instance.getLoanTenure_Month(), interest.getLoanTenureMin())
					&& MathUtils.isNotLessThan(interest.getLoanTenureMax(), instance.getLoanTenure_Month());	
		}
	}

	public <T extends PersonalLoan> void sortPersonalLoans(List<T> loanList, final List<SortingKeyword> sortKeys){
		
		loanList.sort((o1, o2) -> {
			
			int result = -1; // o1 ahead of o2 except the following
			if(!o1.isSponsoredProduct() && o2.isSponsoredProduct()){
				result = 1;
			}else if (!o1.isSponsoredProduct() && !o2.isSponsoredProduct()){
				result = sortByAttribute(o1, o2, sortKeys);
			}else if( o1.isSponsoredProduct() && o2.isSponsoredProduct()){
				if(o1.getFeaturedRank() > o2.getFeaturedRank()){
					result = 1;
				}else if(o1.getFeaturedRank() == o2.getFeaturedRank()){
					result = sortByAttribute(o1, o2, sortKeys);
				}
			}
			return result;
		});
	}
	
	public static <T extends PersonalLoan> int sortByAttribute(T o1, T o2, List<SortingKeyword> sortKeys){
		
		int result = -1;
		int i = 0;
		for(; i < sortKeys.size(); i ++){
			SortingKeyword sortKey = sortKeys.get(i);
			String field = sortKey.keyword;
			String methodName = "get" + field;
			Object fieldValue1 = getFieldValue(o1, methodName, o1.getClass());
			int temp = 0;
			if(sortKey != SortingKeyword.ResponseTime){
				Double value1 = (Double)fieldValue1;
				Object fieldValue2 = getFieldValue(o2, methodName, o2.getClass());
				Double value2 = (Double)fieldValue2;
				temp = value1.compareTo(value2);	
			}else{
				//TO-DO: sort by response time
			}
			
			
			if(temp == 0){
				continue;
			}else{
				result = temp > 0 ? 1: -1;
				break;
			}
		}
		
		if(i == sortKeys.size()){
			String methodName = "getProductName";
			String value1 = (String)getFieldValue(o1, methodName, o1.getClass());	
			String value2 = (String)getFieldValue(o2, methodName, o2.getClass());
			int temp = value1.compareTo(value2);
			if(temp > 0){
				result = 1;
			}
		}
		
		return result;
	}

	private static <T extends PersonalLoan> Object getFieldValue(T o1, String methodName, Class<?> clz) {

		Object result = null;
		try {
			Method m = ReflectionUtils.getMethod(clz, methodName);
			result = m.invoke(o1);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public Interest getInterest() {
		return interest;
	}

	public void setInterest(Interest interest) {
		this.interest = interest;
	}

	public List<MinimumRequirement> getMinRequirements() {
		return minRequirements;
	}

	public void setMinRequirements(List<MinimumRequirement> minRequirements) {
		this.minRequirements = minRequirements;
	}

	public double getMonthlyInterestRate() {
		return monthlyInterestRate;
	}

	public void setMonthlyInterestRate(double monthlyInterestRate) {
		this.monthlyInterestRate = monthlyInterestRate;
	}
}
