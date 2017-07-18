package com.cgg.pl.category;

public class Attributes_MY extends AbstractAttributes {

	private Double minIncome;
	private Double annualHandlingFeePercent;
	private Double fixedHandlingFeePercent;
	private Double minFixedHandlingFee;
	public Double getMinIncome() {
		return minIncome;
	}
	public void setMinIncome(Double minIncome) {
		this.minIncome = minIncome;
	}
	public Double getAnnualHandlingFeePercent() {
		return annualHandlingFeePercent;
	}
	public void setAnnualHandlingFeePercent(Double annualHandlingFeePercent) {
		this.annualHandlingFeePercent = annualHandlingFeePercent;
	}
	public Double getFixedHandlingFeePercent() {
		return fixedHandlingFeePercent;
	}
	public void setFixedHandlingFeePercent(Double fixedHandlingFeePercent) {
		this.fixedHandlingFeePercent = fixedHandlingFeePercent;
	}
	public Double getMinFixedHandlingFee() {
		return minFixedHandlingFee;
	}
	public void setMinFixedHandlingFee(Double minFixedHandlingFee) {
		this.minFixedHandlingFee = minFixedHandlingFee;
	}
}
