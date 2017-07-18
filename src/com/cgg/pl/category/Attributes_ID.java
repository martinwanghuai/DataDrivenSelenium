package com.cgg.pl.category;

public class Attributes_ID extends AbstractAttributes {

	private Double startUpFee;
    private Double startUpFeePercent;
    private Double minStartUpFee;
    private Double annualHandlingFee;
    private String interestRateTag;
    private String monthlyRepaymentTag;
    private String totalRepaymentTag;
    
	public Double getStartUpFee() {
		return startUpFee;
	}
	public void setStartUpFee(Double startUpFee) {
		this.startUpFee = startUpFee;
	}
	public Double getStartUpFeePercent() {
		return startUpFeePercent;
	}
	public void setStartUpFeePercent(Double startUpFeePercent) {
		this.startUpFeePercent = startUpFeePercent;
	}
	public Double getMinStartUpFee() {
		return minStartUpFee;
	}
	public void setMinStartUpFee(Double minStartUpFee) {
		this.minStartUpFee = minStartUpFee;
	}
	public Double getAnnualHandlingFee() {
		return annualHandlingFee;
	}
	public void setAnnualHandlingFee(Double annualHandlingFee) {
		this.annualHandlingFee = annualHandlingFee;
	}
	public String getInterestRateTag() {
		return interestRateTag;
	}
	public void setInterestRateTag(String interestRateTag) {
		this.interestRateTag = interestRateTag;
	}
	public String getMonthlyRepaymentTag() {
		return monthlyRepaymentTag;
	}
	public void setMonthlyRepaymentTag(String monthlyRepaymentTag) {
		this.monthlyRepaymentTag = monthlyRepaymentTag;
	}
	public String getTotalRepaymentTag() {
		return totalRepaymentTag;
	}
	public void setTotalRepaymentTag(String totalRepaymentTag) {
		this.totalRepaymentTag = totalRepaymentTag;
	}
}
