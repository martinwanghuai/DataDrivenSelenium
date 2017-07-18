package com.cgg.pl.category;

import java.util.List;

import org.testng.collections.Lists;

import com.cgg.model.TestCase;

import utility.Checker;
import utility.Constant;
import utility.WebDriverUtils;

public enum AlfrescoSetup {

	HK("https://ap-alfresco.compareglobal.co.uk/", "hongkong", "HK_PL_DATA", ""){
		
		@Override
		public List<Category> getCategories(){
			
			return Lists.newArrayList(Category.PersonalInstalment_HK, Category.CreditLine_HK, Category.LendingCompanies_HK, Category.DebtConsolidation_HK, Category.Mortgage_HK);
		}
		
		@Override
		public boolean isLiveUrl(final String url){
			return url.contains("www.moneyhero.com.hk");
		}
	},

	SG("https://ap-alfresco.compareglobal.co.uk/", "singapore", "SG_PL_DATA", ""){
		
		@Override
		public List<Category> getCategories(){
			
			return Lists.newArrayList(Category.BalanceTransfer_SG, Category.CreditLine_SG, Category.PersonalInstalment_SG);
		}
		
		@Override
		public boolean isLiveUrl(final String url){
			return url.contains("www.singsaver.com.sg");
		}
	},
	
	TW("https://ap-alfresco.compareglobal.co.uk/", "taiwan", "TW_PL_DATA", ""){
		
		@Override
		public List<Category> getCategories(){
			
			return Lists.newArrayList(Category.GoodCreditLoan_TW, Category.ExistingCustomerLoan_TW, Category.GeneralInstalmentLoan_TW, Category.RevolvingCreditLoan_TW);
		}
		
		@Override
		public boolean isLiveUrl(final String url){
			return url.contains("www.money101.com.tw");
		}
	},
	
	DK("https://eu-alfresco.compareglobal.co.uk/", "denmark", "DK_PL_DATA", ""){
		
		@Override
		public List<Category> getCategories(){
			
			return Lists.newArrayList(Category.BankLoan_DK, Category.OnlineLoan_DK, Category.QuickLoan_DK);
		}
		
		@Override
		public boolean isLiveUrl(final String url){
			return url.contains("www.samlino.dk");
		}
	},
	
	FI("https://eu-alfresco.compareglobal.co.uk/", "finland", "FI_PL_DATA", ""){
		
		@Override
		public List<Category> getCategories(){
			
			return Lists.newArrayList(Category.QuickLoan_FI, Category.RevolvingLoan_FI, Category.TermLoan_FI);
		}
		
		@Override
		public boolean isLiveUrl(final String url){
			return url.contains("www.vertaaensin.fi");
		}
	},
	
	BE("https://eu-alfresco.compareglobal.co.uk/", "belgium", "BE_PL_DATA", ""){
		
		@Override
		public List<Category> getCategories(){
			
			return Lists.newArrayList(Category.RenovationLoan_BE, Category.VehicleLoan_BE, Category.PersonalLoan_BE);
		}
		
		@Override
		public boolean isLiveUrl(final String url){
			return url.contains("www.topcompare.be");
		}
	},
	
	NO("https://eu-alfresco.compareglobal.co.uk/", "norway", "NO_PL_DATA", ""){
		
		@Override
		public List<Category> getCategories(){
			
			return Lists.newArrayList(Category.PersonalInstalment_NO, Category.Refinancing_NO);
		}
		
		@Override
		public boolean isLiveUrl(final String url){
			return url.contains("www.samlino.no");
		}
	},
	
	MX("https://ap-alfresco.compareglobal.co.uk/", "mexico", "MX_PL_DATA", ""){
		
		@Override
		public List<Category> getCategories(){
			
			return Lists.newArrayList(Category.QuickLoan_MX, Category.PersonalInstalment_MX, Category.BankLoan_MX);
		}
		
		@Override
		public boolean isLiveUrl(final String url){
			return url.contains("www.comparaguru.com");
		}
	},
	
	PT("https://eu-alfresco.compareglobal.co.uk/", "portugal", "PT_PL_DATA", "PT_MG_DATA"){
//	PT("https://europen-cms.compareglobal.co.uk/", "portugal", "PT_PL_DATA"){
		@Override
		public List<Category> getCategories(){
			
			return Lists.newArrayList(Category.PersonalInstalment_PT, Category.SpecialisedCredit_PT, Category.ConsolidatedCredit_PT, Category.QuickLoan_PT, Category.Mortgage_PT);
		}
		
		@Override
		public boolean isLiveUrl(final String url){
			return url.contains("www.comparaja.pt");
		}
	},
	
	MY("https://ap-alfresco.compareglobal.co.uk/", "malaysia", "MY_PL_DATA",""){
		@Override
		public List<Category> getCategories(){
			
			return Lists.newArrayList(Category.PersonalLoan_MY, Category.AllLoan_MY, Category.FastApproval_MY, Category.GovernmentOrGLCEmployee_MY, Category.Islamic_MY, Category.DebtConsolidation_MY, 
					Category.HomeDecoration_MY, Category.Holiday_MY, Category.Education_MY, Category.InvestmentPurposes_MY, Category.PropertyPurchase_MY, Category.Business_MY, Category.MedicalBills_MY,
					Category.CarLoan_MY, Category.MortgageRepayment_MY, Category.Wedding_MY, Category.SomethingElse_MY);
		}
		
		@Override
		public boolean isLiveUrl(final String url){
			return url.contains("www.comparehero.my");
		}
	},
	TH("https://ap-alfresco.compareglobal.co.uk/", "thailand", "TH_PL_DATA", ""){
		@Override
		public List<Category> getCategories(){
			
			return Lists.newArrayList();
			/*return Lists.newArrayList(Category.PersonalLoan_TH, Category.AllLoan_MY, Category.FastApproval_MY, Category.GovernmentOrGLCEmployee_MY, Category.Islamic_MY, Category.DebtConsolidation_MY, 
					Category.HomeDecoration_MY, Category.Holiday_MY, Category.Education_MY, Category.InvestmentPurposes_MY, Category.PropertyPurchase_MY, Category.Business_MY, Category.MedicalBills_MY,
					Category.CarLoan_MY, Category.MortgageRepayment_MY, Category.Wedding_MY, Category.SomethingElse_MY);*/
		}
		
		@Override
		public boolean isLiveUrl(final String url){
			return url.contains("www.moneyguru.co.th");
		}
	},
	PH("https://ap-alfresco.compareglobal.co.uk/", "philippines", "PH_PL_DATA", ""){
		@Override
		public List<Category> getCategories(){
			
			return Lists.newArrayList();
			/*return Lists.newArrayList(Category.PersonalLoan_TH, Category.AllLoan_MY, Category.FastApproval_MY, Category.GovernmentOrGLCEmployee_MY, Category.Islamic_MY, Category.DebtConsolidation_MY, 
					Category.HomeDecoration_MY, Category.Holiday_MY, Category.Education_MY, Category.InvestmentPurposes_MY, Category.PropertyPurchase_MY, Category.Business_MY, Category.MedicalBills_MY,
					Category.CarLoan_MY, Category.MortgageRepayment_MY, Category.Wedding_MY, Category.SomethingElse_MY);*/
		}
		
		@Override
		public boolean isLiveUrl(final String url){
			return url.contains("www.moneymax.ph");
		}
	},
	ID("https://ap-alfresco.compareglobal.co.uk/", "indonesia", "ID_PL_DATA", ""){
		@Override
		public List<Category> getCategories(){
			
			return Lists.newArrayList();
			/*return Lists.newArrayList(Category.PersonalLoan_TH, Category.AllLoan_MY, Category.FastApproval_MY, Category.GovernmentOrGLCEmployee_MY, Category.Islamic_MY, Category.DebtConsolidation_MY, 
					Category.HomeDecoration_MY, Category.Holiday_MY, Category.Education_MY, Category.InvestmentPurposes_MY, Category.PropertyPurchase_MY, Category.Business_MY, Category.MedicalBills_MY,
					Category.CarLoan_MY, Category.MortgageRepayment_MY, Category.Wedding_MY, Category.SomethingElse_MY);*/
		}
		
		@Override
		public boolean isLiveUrl(final String url){
			return url.contains("www.halomoney.co.id");
		}
	};
	private String alfrescoURL, siteName, plJsonFileName, mgJsonFileName;
	private String authTicket, jsonStr;
	
	AlfrescoSetup(final String alfrescoURL, final String siteName, final String jsonFileName, final String mgJsonFileName) {

		this.alfrescoURL = alfrescoURL;
		this.siteName = siteName;
		this.plJsonFileName = jsonFileName;
		this.mgJsonFileName = mgJsonFileName;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getPLJsonFileName() {
		return plJsonFileName;
	}
	
	public String getMgJsonFileName() {
		return mgJsonFileName;
	}

	public void setPLJsonFileName(String plJsonFileName) {
		this.plJsonFileName = plJsonFileName;
	}

	public String getAlfrescoURL() {
		return alfrescoURL;
	}

	public void setAlfrescoURL(String alfrescoURL) {
		this.alfrescoURL = alfrescoURL;
	}

	public abstract List<Category> getCategories(); 
	public abstract boolean isLiveUrl(String url);
	
	public static AlfrescoSetup fromURL(final String url){
		
		if(TestCase.isHKURL(url)){
			return HK;
		}else if(TestCase.isSGURL(url)){
			return SG;
		}else if(TestCase.isTWURL(url)){
			return TW;
		}else if(TestCase.isDKURL(url)){
			return DK;
		}else if(TestCase.isFIURL(url)){
			return FI;
		}else if(TestCase.isBEURL(url)){
			return BE;
		}else if(TestCase.isNOURL(url)){
			return NO;
		}else if(TestCase.isMXURL(url)){
			return MX;
		}else if(TestCase.isPTURL(url)){
			return PT;
		}else if(TestCase.isMYURL(url)){
			return MY;
		}else if(TestCase.isPHURL(url)){
			return PH;
		}else if(TestCase.isTHURL(url)){
			return TH;
		}else if(TestCase.isIDURL(url)){
			return ID;
		}else{
			return SG;
		}
	}
	
	public String getMGJsonFileFromAlfresco(final String urlStr){
		
		if(Checker.isBlank(jsonStr)){
			String ticket = this.getAlfrescoTicket();
		    alfrescoURL = this.getAlfrescoURL() + "alfresco/api/-default-/public/cmis/versions/1.1/browser/root/sites/" 
			+ this.getSiteName() + "-" + this.getEnvironmentName(urlStr)+ "/documentLibrary/dataFiles/" 
		    		+ this.getMgJsonFileName() + ".json?alf_ticket=" + ticket;
		    System.out.println(alfrescoURL);
		    jsonStr = WebDriverUtils.callRESTAPI(alfrescoURL);	
		}
		
		return jsonStr;
	}
	
	public String getPLJsonFileFromAlfresco(final String urlStr){
		
		if(Checker.isBlank(jsonStr)){
			String ticket = this.getAlfrescoTicket();
		    alfrescoURL = this.getAlfrescoURL() + "alfresco/api/-default-/public/cmis/versions/1.1/browser/root/sites/" 
			+ this.getSiteName() + "-" + this.getEnvironmentName(urlStr)+ "/documentLibrary/dataFiles/" 
		    		+ this.getPLJsonFileName()+ ".json?alf_ticket=" + ticket;
		    System.out.println(alfrescoURL);
		    jsonStr = WebDriverUtils.callRESTAPI(alfrescoURL);	
		}
		
		return jsonStr;
	}
	
	public String getApplicationFormConfigFileFromAlfresco(final String urlStr, final String vertical){
		
		if(Checker.isBlank(jsonStr)){
			String ticket = this.getAlfrescoTicket();
			
			if(vertical.equals("cc")){
				alfrescoURL = "https://api.github.com/repos/compareasiagroup/cgg-config-repo/contents/"
						+ this.name().toLowerCase() + "/cc/checkout.json";
				System.out.println("GitHub URL:" + alfrescoURL);
				jsonStr = WebDriverUtils.callRESTAPI(alfrescoURL, "martinwanghuai", "1829979328b875a5a7acf149f5a8ee9f7b87240c");
			}else{
				/*alfrescoURL = this.getAlfrescoURL() + "alfresco/api/-default-/public/cmis/versions/1.1/browser/root/sites/"
						+ this.getSiteName() + "-" + this.getEnvironmentName(urlStr)
						+ "/documentLibrary/config/checkoutPage/layout-" + vertical.toLowerCase() + "-citi.json?alf_ticket="
						+ ticket;*/
				
				alfrescoURL = this.getAlfrescoURL() + "alfresco/api/-default-/public/cmis/versions/1.1/browser/root/sites/"
						+ this.getSiteName() + "-" + this.getEnvironmentName(urlStr)
						+ "/documentLibrary/config/checkoutPage/layout-" + vertical.toLowerCase() + ".json?alf_ticket="
						+ ticket;		
				
				/*alfrescoURL = this.getAlfrescoURL() + "alfresco/api/-default-/public/cmis/versions/1.1/browser/root/sites/"
						+ this.getSiteName() + "-" + this.getEnvironmentName(urlStr)
						+ "/documentLibrary/config/checkoutPage/layout-mg.json?alf_ticket="
						+ ticket;		*/
				
				System.out.println("Alfresco URL:" + alfrescoURL);
				jsonStr = WebDriverUtils.callRESTAPI(alfrescoURL);	
			}
		}
		return jsonStr;
	}
	
	public String getFunnelConfigFileFromAlfresco(final String urlStr, final String verticalStr){
		
		if(Checker.isBlank(jsonStr)){
			String ticket = this.getAlfrescoTicket();
			
			String vertical = getVertical(verticalStr);
			
			if(vertical.equals("cc")){
				alfrescoURL = "https://api.github.com/repos/compareasiagroup/cgg-config-repo/contents/"
						+ this.name().toLowerCase() + "/cc/funnel.json";
				jsonStr = WebDriverUtils.callRESTAPI(alfrescoURL, "martinwanghuai", "1829979328b875a5a7acf149f5a8ee9f7b87240c");
				
			}else{
				String vertical_converted = vertical.toLowerCase();
				 if(vertical.equals("ci")){
					 vertical_converted = "ti";
				 }
				alfrescoURL = this.getAlfrescoURL() + "alfresco/api/-default-/public/cmis/versions/1.1/browser/root/sites/" 
						+ this.getSiteName() + "-" + this.getEnvironmentName(urlStr) 
						+ "/documentLibrary/config/funnelPage/" + vertical_converted + "Config.json?alf_ticket=" + ticket;
				
				System.out.println("Alfresco URL:" + alfrescoURL);
				jsonStr = WebDriverUtils.callRESTAPI(alfrescoURL);	
			}
		}
		
		return jsonStr;
	}

	public static String getVertical(final String verticalStr) {
		String vertical = "";
		if(verticalStr.toLowerCase().contains("pl")){
			vertical = "pl";
		}else if(verticalStr.toLowerCase().contains("cc")){
			vertical = "cc";
		}else if(verticalStr.toLowerCase().contains("ci")){
			vertical = "ci";
		}else if(verticalStr.toLowerCase().contains("bb")){
			vertical = "bb";
		}else if(verticalStr.toLowerCase().contains("ti")){
			vertical = "ti";
		}
		return vertical;
	}

	public String getAlfrescoTicket() {
		
		if(Checker.isBlank(authTicket)){
			String alfrescoURL = this.getAlfrescoURL() + "alfresco/service/api/login?u=" +  Constant.UID_ALFRESCO+ "&pw=" + Constant.PWD_ALFRESCO;
			String xmlStr = WebDriverUtils.callRESTAPI(alfrescoURL);
			String ticket = xmlStr.substring(xmlStr.indexOf("<ticket>") + "<ticket>".length(), xmlStr.indexOf("</ticket>"));
			this.setAuthTicket(ticket);
		}
		
		return this.getAuthTicket();
	}
	
	public String getEnvironmentName(final String url){
		
		List<String> sites = Lists.newArrayList("qa", "integration", "staging", "preview", "production");
		for(String env: sites){
			if(url.contains(env)){
				return env;
			}
		}
		
		return TestCase.isSGURL(url) ? "secure-production": "production"; //production by default
	}

	public String getAuthTicket() {
		return authTicket;
	}

	public void setAuthTicket(String authTicket) {
		this.authTicket = authTicket;
	}
}
