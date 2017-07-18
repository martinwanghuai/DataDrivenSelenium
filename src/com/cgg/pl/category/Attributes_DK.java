package com.cgg.pl.category;

import java.io.Serializable;

public class Attributes_DK extends AbstractAttributes {

    private Double startUpFeePercent;
    private Double minStartUpFee;
    private Double monthlyFee;
    private Double handlingFee;
    private Double totalInterest;
    private String responseTime;
    private String responseTimeString;
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
	public Double getMonthlyFee() {
		return monthlyFee;
	}
	public void setMonthlyFee(Double monthlyFee) {
		this.monthlyFee = monthlyFee;
	}
	public Double getHandlingFee() {
		return handlingFee;
	}
	public void setHandlingFee(Double handlingFee) {
		this.handlingFee = handlingFee;
	}
	public Double getTotalInterest() {
		return totalInterest;
	}
	public void setTotalInterest(Double totalInterest) {
		this.totalInterest = totalInterest;
	}
	public String getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}
	public String getResponseTimeString() {
		return responseTimeString;
	}
	public void setResponseTimeString(String responseTimeString) {
		this.responseTimeString = responseTimeString;
	}
    
}
