package com.cgg.model;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.cgg.pl.category.AlfrescoSetup;
import com.cgg.pl.category.Category;
import com.cgg.pl.category.ResultSource;
import com.cgg.pl.category.SortingKeyword;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.google.gson.Gson;

import utility.Checker;
import utility.Constant;
import utility.IOUtils;
import utility.MathUtils;
import utility.WebDriverUtils;

public class PersonalLoanConfig {

	private List<String> visitedPLs;
	private String language;
	private String url;
	private AlfrescoSetup alfrescoSetup;
	private PLJsonObjects plJsonObjects;
	private MortgageJsonObject mgJsonObjects;
	private Integer loanAmount, minLoanAmount, maxLoanAmount;
	private Integer loanTenure_Month, minLoanTenure_Month, maxLoanTenure_Month;
	private Integer loanTenure_Year, minLoanTenure_Year, maxLoanTenure_Year;
	private Integer borrowPercentage;
	private List<String> categories;
	private Category category;
	private Category productCategory;
	private String purposeCategory;

	public PersonalLoanConfig() {

	}

	public PersonalLoanConfig(PersonalLoanConfig config) {

		super();
		this.visitedPLs = config.getVisitedPLs();
		this.language = config.getLanguage();
		this.url = config.getUrl();
		this.alfrescoSetup = config.getAlfrescoSetup();
		this.plJsonObjects = config.getPlJsonObjects();
		this.mgJsonObjects = config.getMgJsonObjects();

		this.loanAmount = config.getLoanAmount();
		this.minLoanAmount = config.getMinLoanAmount();
		this.maxLoanAmount = config.getMaxLoanAmount();

		this.loanTenure_Month = config.getLoanTenure_Month();
		this.minLoanTenure_Month = config.getMinLoanTenure_Month();
		this.maxLoanTenure_Month = config.getMaxLoanTenure_Month();

		this.loanTenure_Year = config.getLoanTenure_Year();
		this.minLoanTenure_Year = config.getMinLoanTenure_Year();
		this.maxLoanTenure_Year = config.getMaxLoanTenure_Year();

		this.categories = config.getCategories();
		this.category = config.getCategory();

		this.borrowPercentage = config.getBorrowPercentage();
	}

	public static PersonalLoanConfig fromUrl(final String url) {

		PersonalLoanConfig instance = new PersonalLoanConfig();
		instance.setUrl(url);
		instance.setAlfrescoSetup(AlfrescoSetup.fromURL(url));
		return instance;
	}

	public static PersonalLoanConfig fromInputs(final String input, final String url, final List<String> visited) {

		PersonalLoanConfig instance = new PersonalLoanConfig();
		instance.setUrl(url);
		instance.setAlfrescoSetup(AlfrescoSetup.fromURL(url));
		List<String> categoryTypes = Lists.newArrayList();
		if (!Checker.isBlank(input)) {
			String[] strs = input.split(";;");
			instance.setLanguage(strs[0]);
			instance.setMinLoanAmount(Integer.parseInt(strs[1]));
			instance.setMaxLoanAmount(Integer.parseInt(strs[2]));
			instance.setMinLoanTenure_Month(Integer.parseInt(strs[3]));
			instance.setMinLoanTenure_Year(Integer.parseInt(strs[3]));
			instance.setMaxLoanTenure_Month(Integer.parseInt(strs[4]));
			instance.setMaxLoanTenure_Year(Integer.parseInt(strs[4]));
			for (int i = 5; i < strs.length; i++) {
				categoryTypes.add(strs[i]);
			}
			instance.setCategories(categoryTypes);
		}

		return instance;
	}

	public String getUrlDomain() {

		String domain = "";
		try {
			URI uri = new URI(url);
			domain = uri.getHost();
		} catch (Exception e) {
		}

		return domain;
	}

	public String constructProvidersUrl() {

		StringBuilder sb = new StringBuilder();
		try {
			URI uri = new URI(url);
			String domain = uri.getHost();
			String protocol = uri.getScheme();
			sb.append(protocol + "://").append(domain).append("/api/personal-loan/v2/providersOrderByRanking");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		return sb.toString();
	}

	public String constructCarInsurancesUrl(String parsableURL) {

		String result = "";
		try {
			StringBuilder sb = new StringBuilder();
			URI uri = new URI(url);
			String domain = uri.getHost();
			String protocol = uri.getScheme();
			sb.append(protocol + "://").append(domain).append(parsableURL);
			result = sb.toString().replace("+", "%20");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		return result;
	}

	public String constructMortgageAPI() {

		StringBuilder sb = new StringBuilder();
		try {

			Category category = this.getCategory();
			URI uri = new URI(url);
			String domain = uri.getHost();
			String protocol = uri.getScheme();

			sb.append(protocol + "://").append(domain).append("/api/mortgage/v2/mortgages/")
					.append(category.getJsonText()).append("?");
			List<SortingKeyword> keywords = PLJsonObjects.getSortingKeywords(category);
			for (SortingKeyword keyword : keywords) {
				sb.append("sortBy=" + keyword.getShortName() + "%2B&");
			}
			sb.append("propertyValue=" + this.getLoanAmount() + "&")
					.append("wantToBorrow=" + this.getLoanAmount() * this.getBorrowPercentage() / 100.0 + "&")
					.append("wantToBorrowTime=" + this.getLoanTenure_Year() + "&")
					.append("wantToBorrowTimeUnit=" + category.getTimeUnit() + "&")
					.append("lang=" + this.getLanguage() + "&").append("pageSize=2000");
		} catch (Exception e) {
		}

		return sb.toString();
	}

	public String constructPersonalLoanAPI() {

		StringBuilder sb = new StringBuilder();
		try {

			Category category = this.getCategory();
			URI uri = new URI(url);
			String domain = uri.getHost();
			String protocol = uri.getScheme();

			if(category!= Category.PersonalInstalment_ID){
				sb.append(protocol + "://").append(domain).append("/api/personal-loan/v2/loans/")
				.append(category.getJsonText()).append("?");	
			}else{
				sb.append(protocol + "://").append(domain).append("/api/personal-loan/v2/loans/education?");
			}
			
			List<SortingKeyword> keywords = PLJsonObjects.getSortingKeywords(category);
			for (SortingKeyword keyword : keywords) {
				sb.append("sortBy=" + keyword.getShortName() + "%2B&");
			}
			sb.append("wantToBorrow=" + this.getLoanAmount() + "&");
			if (category.getTimeUnit().equals("year")) {
				int loanTenure_year = this.getLoanTenure_Month() / 12;
				loanTenure_year = loanTenure_year == 0 ? 1 : loanTenure_year;
				sb.append("wantToBorrowTime=" + loanTenure_year + "&");
			} else {
				sb.append("wantToBorrowTime=" + this.getLoanTenure_Month() + "&");
			}
			sb.append("wantToBorrowTimeUnit=" + category.getTimeUnit() + "&").append("lang=" + this.getLanguage() + "&")
					.append("pageSize=2000");
		} catch (Exception e) {
		}

		return sb.toString();
	}

	public void genInputsForPLResult(final List<String> visited, final ResultSource resultSource) {

		Category category;
		int loanAmount = 0, loanTenure_Month = 0, borrowPercentage = 0, loanTenure_Year;
		String newInstance = "";
		if (resultSource == ResultSource.PL_API || resultSource == ResultSource.PL_UI) {
			plJsonObjects = (PLJsonObjects) IOUtils.loadJsonObjects(
					this.getAlfrescoSetup().getPLJsonFileFromAlfresco(this.getUrl()), PLJsonObjects.class,
					new PLJsonObjectDeserializer());

			if (Constant.DEBUG_MODE) {
				loanAmount = 5000;
				loanTenure_Month = 18;
				category = Category.OnlineLoan_DK;
				if (category.getTimeUnit().equals("year")) {
					int loanTenure_year = loanTenure_Month / 12;
					this.setLoanTenure_Year(loanTenure_year);
					loanTenure_Month = loanTenure_year == 0 ? 12 : loanTenure_year * 12;
				}
				newInstance = category.name() + loanAmount + loanTenure_Month;
				visited.add(newInstance);
			} else {
				List<Double> loanAmounts = PLJsonObjects.getCriticalValues(plJsonObjects.getSortedLoanAmounts(),
						(double) this.getMinLoanAmount(), (double) this.getMaxLoanAmount());
				List<Double> loanTenures_Month = PLJsonObjects.getCriticalValues(plJsonObjects.getSortedLoanTenures(),
						(double) this.getMinLoanTenure_Month(), (double) this.getMaxLoanTenure_Month());
				List<Category> categories = FluentIterable.from(this.getAlfrescoSetup().getCategories())
						.filter(TestCase.IS_SPECIFIED_CATEGORY(this.getCategories())).toList();
				do {
					category = MathUtils.getRandomValue(categories);
					if (TestCase.isPTURL(url) || TestCase.isMYURL(url)) {
						loanTenures_Month = Lists.newArrayList(6.0, 12.0, 18.0, 24.0, 30.0, 36.0, 48.0, 54.0, 60.0,
								72.0, 84.0, 96.0, 108.0, 120.0);
					} else if (TestCase.isDKURL(url)) {
						loanAmounts = Lists.newArrayList(1000.0, 2000.0, 3000.0, 4000.0, 5000.0, 6000.0);
						loanTenures_Month = Lists.newArrayList(6.0, 12.0, 18.0, 24.0, 30.0, 36.0, 48.0, 54.0, 60.0);
					} else if (TestCase.isSGURL(url) && category.equals(Category.BalanceTransfer_SG)) {
						loanTenures_Month = Lists.newArrayList(3.0, 6.0, 12.0);
					}
					loanAmount = MathUtils.getRandomValue(loanAmounts).intValue();
					loanTenure_Month = MathUtils.getRandomValue(loanTenures_Month).intValue();

					if (category.getTimeUnit().equals("year")) {
						int loanTenure_year = loanTenure_Month / 12;
						loanTenure_Month = loanTenure_year == 0 ? 12 : loanTenure_year * 12;
					}

					newInstance = category.name() + loanAmount + loanTenure_Month;
				} while (visited.contains(newInstance));
				visited.add(newInstance);
			}

			this.setLoanAmount(loanAmount);
			this.setLoanTenure_Month(loanTenure_Month);
			this.setCategory(category);

		} else if (resultSource == ResultSource.MORTGAGE_API || resultSource == ResultSource.MORTGAGE_UI) {
			mgJsonObjects = (MortgageJsonObject) IOUtils.loadJsonObjects(
					this.getAlfrescoSetup().getMGJsonFileFromAlfresco(this.getUrl()), MortgageJsonObject.class);

			if (Constant.DEBUG_MODE) {
				loanAmount = 5000;
				loanTenure_Year = 4;
				category = Category.Mortgage_PT;
				borrowPercentage = 95;
				newInstance = category.name() + loanAmount + loanTenure_Month;
				visited.add(newInstance);
			} else {
				List<Double> loanAmounts = PLJsonObjects.getCriticalValues(
						mgJsonObjects.mortgage.getSortedLoanAmounts(), (double) this.getMinLoanAmount(),
						(double) this.getMaxLoanAmount());
				List<Double> loanTenures_Year = PLJsonObjects.getCriticalValues(
						mgJsonObjects.mortgage.getSortedLoanTenures(), (double) this.getMinLoanTenure_Year(),
						(double) this.getMaxLoanTenure_Year());
				List<Category> categories = FluentIterable.from(this.getAlfrescoSetup().getCategories())
						.filter(TestCase.IS_SPECIFIED_CATEGORY(this.getCategories())).toList();
				do {
					category = MathUtils.getRandomValue(categories);
					loanAmount = MathUtils.getRandomValue(loanAmounts).intValue();
					loanTenure_Year = MathUtils.getRandomValue(loanTenures_Year).intValue();
					borrowPercentage = MathUtils.getRandomValue(0, 100);
					newInstance = "" + loanAmount + loanTenure_Year + borrowPercentage;
				} while (visited.contains(newInstance));
				visited.add(newInstance);
			}

			this.setLoanAmount(loanAmount);
			this.setLoanTenure_Year(loanTenure_Year);
			this.setBorrowPercentage(borrowPercentage);
			this.setCategory(category);
		}
	}

	public List<String> getVisitedPLs() {
		return visitedPLs;
	}

	public void setVisitedPLs(List<String> visitedPLs) {
		this.visitedPLs = visitedPLs;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public AlfrescoSetup getAlfrescoSetup() {
		return alfrescoSetup;
	}

	public void setAlfrescoSetup(AlfrescoSetup alfrescoSetup) {
		this.alfrescoSetup = alfrescoSetup;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Integer getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(Integer loanAmount) {
		this.loanAmount = loanAmount;
	}

	public PLJsonObjects getPlJsonObjects() {
		return plJsonObjects;
	}

	public void setPlJsonObjects(PLJsonObjects plJsonObjects) {
		this.plJsonObjects = plJsonObjects;
	}

	public Integer getMinLoanAmount() {
		return minLoanAmount;
	}

	public void setMinLoanAmount(Integer minLoanAmount) {
		this.minLoanAmount = minLoanAmount;
	}

	public Integer getMaxLoanAmount() {
		return maxLoanAmount;
	}

	public void setMaxLoanAmount(Integer maxLoanAmount) {
		this.maxLoanAmount = maxLoanAmount;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public Integer getLoanTenure_Month() {
		return loanTenure_Month;
	}

	public void setLoanTenure_Month(Integer loanTenure_Month) {
		this.loanTenure_Month = loanTenure_Month;
	}

	public Integer getMinLoanTenure_Month() {
		return minLoanTenure_Month;
	}

	public void setMinLoanTenure_Month(Integer minLoanTenure_Month) {
		this.minLoanTenure_Month = minLoanTenure_Month;
	}

	public Integer getMaxLoanTenure_Month() {
		return maxLoanTenure_Month;
	}

	public void setMaxLoanTenure_Month(Integer maxLoanTenure_Month) {
		this.maxLoanTenure_Month = maxLoanTenure_Month;
	}

	public FunnelConfig loadPLFunnelObject(final String verticalStr) {

		return (FunnelConfig) IOUtils.loadJsonObjects(
				this.getAlfrescoSetup().getFunnelConfigFileFromAlfresco(this.getUrl(), verticalStr), FunnelConfig.class,
				new PLFunnelObjectDeserializer());
	}

	public FunnelConfig loadPLFunnelObject(final WebDriverUtils driver) {

		String path = "";
		try {
			By by = By.xpath("//funnel-page|//cgg-checkout-widget");
			WebElement elem = driver.findElement(by);
			path = elem.getAttribute("config-path");
			System.out.println("Funnel Config. URL:" + path);

			if (!path.startsWith("http")) {
				// relative path
				URI uri = new URI(url);
				String domain = uri.getHost();
				String protocol = uri.getScheme();
				path =protocol + "://" + domain + path;				
			}

		} catch (Exception e) {

		}
		return (FunnelConfig) IOUtils.loadJsonObjects(WebDriverUtils.callRESTAPI(path), FunnelConfig.class,
				new PLFunnelObjectDeserializer());
	}

	public AppFormConfig loadAppFormObject(final String verticalStr) {

		return (AppFormConfig) IOUtils.loadJsonObjects(
				this.getAlfrescoSetup().getApplicationFormConfigFileFromAlfresco(this.getUrl(), verticalStr),
				AppFormConfig.class, new AppFormObjectDeserializer());
	}

	public AppFormConfig loadAppFormObject(final WebDriverUtils driver) {

		String path = "";
		try {
			By by = By.xpath("//cgg-checkout-widget");
			WebElement elem = driver.findElement(by);
			path = elem.getAttribute("config-path");
			System.out.println("App. Form Config. URL:" + path);

			if (!path.startsWith("http")) {
				// relative path
				URI uri = new URI(url);
				String domain = uri.getHost();
				String protocol = uri.getScheme();
				path =protocol + "://" + domain + path;				
			}

		} catch (Exception e) {

		}
		
		return (AppFormConfig) IOUtils.loadJsonObjects(WebDriverUtils.callRESTAPI(path), AppFormConfig.class,
				new AppFormObjectDeserializer());
	}

	public ProvidersOrderByBanking loadProvidersOrderByBanking() {

		Gson gson = new Gson();
		String url = this.constructProvidersUrl();
		return gson.fromJson(WebDriverUtils.callRESTAPI(url), ProvidersOrderByBanking.class);
	}

	public CarInsurances loadCarLoans(String parsableURL) {

		Gson gson = new Gson();
		String url = this.constructCarInsurancesUrl(parsableURL);
		return gson.fromJson(WebDriverUtils.callRESTAPI(url), CarInsurances.class);
	}

	public Cars loadCars(String parsableURL) {

		Gson gson = new Gson();
		String url = this.constructCarInsurancesUrl(parsableURL);
		return gson.fromJson(WebDriverUtils.callRESTAPI(url), Cars.class);
	}

	public String getPurposeCategory() {
		return purposeCategory;
	}

	public void setPurposeCategory(String purposeCategory) {
		this.purposeCategory = purposeCategory;
	}

	public Category getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(Category productCategory) {
		this.productCategory = productCategory;
	}

	public MortgageJsonObject getMgJsonObjects() {
		return mgJsonObjects;
	}

	public void setMgJsonObjects(MortgageJsonObject mgJsonObjects) {
		this.mgJsonObjects = mgJsonObjects;
	}

	public Integer getBorrowPercentage() {
		return borrowPercentage;
	}

	public void setBorrowPercentage(Integer borrowPercentage) {
		this.borrowPercentage = borrowPercentage;
	}

	public Integer getLoanTenure_Year() {
		return loanTenure_Year;
	}

	public void setLoanTenure_Year(Integer loanTenure_Year) {
		this.loanTenure_Year = loanTenure_Year;
	}

	public Integer getMinLoanTenure_Year() {
		return minLoanTenure_Year;
	}

	public void setMinLoanTenure_Year(Integer minLoanTenure_Year) {
		this.minLoanTenure_Year = minLoanTenure_Year;
	}

	public Integer getMaxLoanTenure_Year() {
		return maxLoanTenure_Year;
	}

	public void setMaxLoanTenure_Year(Integer maxLoanTenure_Year) {
		this.maxLoanTenure_Year = maxLoanTenure_Year;
	}
}
