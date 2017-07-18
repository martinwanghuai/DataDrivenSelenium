package com.cgg.pl.category;

public class Attributes_MX extends AbstractAttributes {

	private Double openingFee;
    private Double openingFeePercent;
    private Double totalInterest;
    private ResponseTime responseTime;
    private String responseTimeString;
    private Double commissionAmount;
    private Double commissionPercentage;
    
	public Double getOpeningFee() {
		return openingFee;
	}
	public void setOpeningFee(Double openingFee) {
		this.openingFee = openingFee;
	}
	public Double getOpeningFeePercent() {
		return openingFeePercent;
	}
	public void setOpeningFeePercent(Double openingFeePercent) {
		this.openingFeePercent = openingFeePercent;
	}
	public Double getTotalInterest() {
		return totalInterest;
	}
	public void setTotalInterest(Double totalInterest) {
		this.totalInterest = totalInterest;
	}
	public ResponseTime getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(ResponseTime responseTime) {
		this.responseTime = responseTime;
	}
	public String getResponseTimeString() {
		return responseTimeString;
	}
	public void setResponseTimeString(String responseTimeString) {
		this.responseTimeString = responseTimeString;
	}
	public Double getCommissionAmount() {
		return commissionAmount;
	}
	public void setCommissionAmount(Double commissionAmount) {
		this.commissionAmount = commissionAmount;
	}
	public Double getCommissionPercentage() {
		return commissionPercentage;
	}
	public void setCommissionPercentage(Double commissionPercentage) {
		this.commissionPercentage = commissionPercentage;
	}


}
