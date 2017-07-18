package com.cgg.pl.category;

public enum Category{
	
	PersonalInstalment_SG("personalInstalment_SG", "personalInstalment", SortingKeyword.MonthlyRepayment, Attributes_SG_PersonalInstalment.class, "year"),
	BalanceTransfer_SG("balanceTransfer_SG", "balanceTransfer", SortingKeyword.LowestTransferRate, Attributes_SG_BalanceTransfer.class, "year"),
	CreditLine_SG("creditLine_SG", "creditLine", SortingKeyword.LowestAnnualFlatRate, Attributes_SG_CredlitLine.class, "year"),
	PersonalInstalment_HK("personalInstalment_HK","personalInstalment", SortingKeyword.APR, Attributes_HK.class, "month"),
	DebtConsolidation_HK("debtConsolidation_HK", "debtConsolidation", SortingKeyword.APR, Attributes_HK.class, "month"),
	CreditLine_HK("creditLine_HK", "creditLine", SortingKeyword.APR, Attributes_HK.class, "month"),
	LendingCompanies_HK("lendingCompany_HK", "lendingCompanies", SortingKeyword.APR, Attributes_HK.class, "month"),
	GeneralInstalmentLoan_TW("generalInstallmentLoan_TW", "generalInstallmentLoan", SortingKeyword.MonthlyRepayment, Attributes_TW.class, "month"),
	GoodCreditLoan_TW("goodCreditLoan_TW", "goodCreditLoan", SortingKeyword.MonthlyRepayment, Attributes_TW.class, "month"),
	ExistingCustomerLoan_TW("existingCustomerLoan_TW", "existingCustomerLoan", SortingKeyword.MonthlyRepayment, Attributes_TW.class, "month"),
	RevolvingCreditLoan_TW("revolvingCreditLoan_TW", "revolvingCreditLoan", SortingKeyword.DailyRepayment_TW, Attributes_TW.class, "month"),
	OnlineLoan_DK("onlineLoan_DK", "onlineLoan", SortingKeyword.MonthlyRepayment, Attributes_DK.class, "month"),
	BankLoan_DK("bankLoan_DK", "bankLoan", SortingKeyword.MonthlyRepayment, Attributes_DK.class, "month"),
	QuickLoan_DK("quickLoan_DK", "quickLoan", SortingKeyword.TotalRepayment, Attributes_DK.class, "day"),
	TermLoan_FI("termLoan_FI", "termLoan", SortingKeyword.MonthlyRepayment, Attributes_FI.class, "month"),
	RevolvingLoan_FI("revolvingLoan_FI", "revolvingLoan", SortingKeyword.MonthlyRepayment, Attributes_FI.class, "month"),
	QuickLoan_FI("quickLoan_FI", "quickLoan", SortingKeyword.TotalRepayment, Attributes_FI.class, "day"),
	PersonalLoan_BE("personalLoan_BE", "personalLoan", SortingKeyword.MonthlyRepayment, Attributes_BE.class, "month"),
	RenovationLoan_BE("renovationLoan_BE", "renovationLoan", SortingKeyword.MonthlyRepayment, Attributes_BE.class, "month"),
	VehicleLoan_BE("vehicleLoan_BE", "vehicleLoan", SortingKeyword.MonthlyRepayment, Attributes_BE.class, "month"),
	PersonalInstalment_NO("personalInstalment_NO","personalInstalment", SortingKeyword.MonthlyRepayment, Attributes_NO.class, "year"),
	Refinancing_NO("refinancing_NO","refinancing", SortingKeyword.MonthlyRepayment, Attributes_NO.class, "year"),
	QuickLoan_MX("quickLoan_MX", "quickLoan", SortingKeyword.TotalRepayment, Attributes_MX.class, "day"),
	PersonalInstalment_MX("personalInstalment_MX", "personalInstalment", SortingKeyword.MonthlyRepayment, Attributes_MX.class, "month"),
	BankLoan_MX("bankLoan_MX", "bankLoan", SortingKeyword.MonthlyRepayment, Attributes_MX.class, "month"),
	PersonalInstalment_PT("personalInstalment_PT", "personalInstalment", SortingKeyword.MonthlyRepayment, Attributes_PT.class, "month"),
	SpecialisedCredit_PT("specialisedCredit_PT", "specialisedCredit", SortingKeyword.MonthlyRepayment, Attributes_PT.class, "month"),
	ConsolidatedCredit_PT("consolidatedCredit_PT", "consolidatedCredit", SortingKeyword.MonthlyRepayment, Attributes_PT.class, "month"),
	QuickLoan_PT("quickLoan_PT", "quickLoan", SortingKeyword.TotalRepayment, Attributes_PT.class, "month"),
	PersonalLoan_MY("personalLoan_MY", "personalLoan", SortingKeyword.MonthlyRepayment, Attributes_MY.class, "month"),
	AllLoan_MY("allLoan_MY", "allLoan", SortingKeyword.MonthlyRepayment, Attributes_MY.class, "month"),
	FastApproval_MY("fastApproval_MY", "fastApproval", SortingKeyword.MonthlyRepayment, Attributes_MY.class, "month"),
	GovernmentOrGLCEmployee_MY("governmentOrGLCEmployee_MY", "governmentOrGLCEmployee", SortingKeyword.MonthlyRepayment, Attributes_MY.class, "month"),
	Islamic_MY("islamic_MY", "islamic", SortingKeyword.MonthlyRepayment, Attributes_MY.class, "month"),
	DebtConsolidation_MY("debtConsolidation_MY", "debtConsolidation", SortingKeyword.MonthlyRepayment, Attributes_MY.class, "month"),
	HomeDecoration_MY("homeDecoration_MY", "homeDecoration", SortingKeyword.MonthlyRepayment, Attributes_MY.class, "month"),
	Holiday_MY("holiday_MY", "holiday", SortingKeyword.MonthlyRepayment, Attributes_MY.class, "month"),
	Education_MY("education_MY", "education", SortingKeyword.MonthlyRepayment, Attributes_MY.class, "month"),
	InvestmentPurposes_MY("investmentPurposes_MY", "investmentPurposes", SortingKeyword.MonthlyRepayment, Attributes_MY.class, "month"),
	PropertyPurchase_MY("propertyPurchase_MY", "propertyPurchase", SortingKeyword.MonthlyRepayment, Attributes_MY.class, "month"),
	Business_MY("business_MY", "business", SortingKeyword.MonthlyRepayment, Attributes_MY.class, "month"),
	MedicalBills_MY("medicalBills_MY", "medicalBills", SortingKeyword.MonthlyRepayment, Attributes_MY.class, "month"),
	CarLoan_MY("carLoan_MY", "carLoan", SortingKeyword.MonthlyRepayment, Attributes_MY.class, "month"),
	MortgageRepayment_MY("mortgageRepayment_MY", "carLoan", SortingKeyword.MonthlyRepayment, Attributes_MY.class, "month"),
	Wedding_MY("wedding_MY", "wedding", SortingKeyword.MonthlyRepayment, Attributes_MY.class, "month"),
	SomethingElse_MY("somethingElse_MY", "somethingElse", SortingKeyword.MonthlyRepayment, Attributes_MY.class, "month"),
	Mortgage_PT("mortgage_PT", "all", SortingKeyword.MonthlyRepayment, Attributes_PT.class, "year"),
	Mortgage_PH("mortgage_PH", "all", SortingKeyword.MonthlyRepayment, Attributes_PH.class, "month"),
	Mortgage_HK("mortgage_HK", "all", SortingKeyword.MonthlyRepayment, Attributes_HK.class, "year"),
	PersonalInstalment_ID("personalInstalment_ID", "personalInstalment", SortingKeyword.MonthlyRepayment, Attributes_ID.class, "month");
	
	private String ID, JsonText, timeUnit;
	private SortingKeyword defaultSortingKeyword;
	private Class attributeClass;
	
	Category(final String ID, final String JsonText, final SortingKeyword keyword, final Class attributeClass, final String loanTenureTimeUnit){
		
		this.ID = ID;
		this.JsonText = JsonText;
		this.defaultSortingKeyword = keyword;
		this.attributeClass = attributeClass;
		this.timeUnit = loanTenureTimeUnit;
	}
	
	public static Category fromJsonText(final String jsonText, final String country){
		for(Category category: Category.values()){
			if(category.name().toLowerCase().contains(country.toLowerCase()) && category.getJsonText().equalsIgnoreCase(jsonText)){
				return category;
			}
		}
		
		return null;
	}

	public String getJsonText() {
		return JsonText;
	}

	public void setJsonText(String jsonText) {
		JsonText = jsonText;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public SortingKeyword getDefaultSortingKeyword() {
		return defaultSortingKeyword;
	}

	public void setDefaultSortingKeyword(SortingKeyword defaultSortingKeyword) {
		this.defaultSortingKeyword = defaultSortingKeyword;
	}

	public Class getAttributeClass() {
		return attributeClass;
	}

	public void setAttributeClass(Class attributeClass) {
		this.attributeClass = attributeClass;
	}

	public String getTimeUnit() {
		return timeUnit;
	}

	public void setTimeUnit(String timeUnit) {
		this.timeUnit = timeUnit;
	}
}
