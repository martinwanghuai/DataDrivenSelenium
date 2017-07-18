package com.cgg.pl.category;

public enum SortingKeyword {

	TotalRepayment("TotalRepayment", "trp"),
	APR("Apr", "apr"),
	MonthlyRepayment("MonthlyRepayment", "mrp"),
	LowestTransferRate("LowestTransferRate", "ltr"),
	ProcessingFee("ProcessingFee", "mpf"),
	AnnualRate("AnnualRate", "art"),
	AnnualFee("AnnualFee", "afe"),
	MinMonthlyRepayment("MinMonthlyRepayment", "mmr"),
	LowestAnnualFlatRate("LowestAnnualFlatRate", "lar"),
	InterestRate("InterestRate", "mrt"), //for HK
	AnnualInterestRate("AnnualInterestRate", "art"), //for HK
	DailyRepayment("DailyRepayment", "drp"),
	DailyRepayment_TW("DailyRepayment", "dip"),
	AnnualHandlingFee("AnnualHandlingFee", "ahf"),
	Cost("Cost", "tip"),
	ResponseTime("ResponseTime", "rpt"),
	MinIncome("MinIncome", "mic");
	
	
	protected String keyword, shortName;
	SortingKeyword(final String keyword, final String shortName){
		this.keyword = keyword;
		this.shortName = shortName;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
}
