package com.cgg.pl.category;

import java.io.Serializable;

public abstract class AbstractAttributes implements Serializable{

	private Double apr;
	private Double aprMin;
	private Double aprMax;
	private String aprDesc;
	
    private Double monthlyRate;
    private Double monthlyRateMin;
	private Double monthlyRateMax;
	private String monthlyRateDesc;
    
    private Double annualRate;
    private Double annualRateMin;
	private Double annualRateMax;
	private String annualRateDesc;
	
    private Double monthlyRepayment;
    private Double monthlyRepaymentMin;
	private Double monthlyRepaymentMax;
	private String montlyRepaymentDesc;
    
    private Double totalRepayment;
    private Double totalRepaymentMin;
	private Double totalRepaymentMax;
	private String totalRepaymentDesc;
    
    private Double dailyRepayment;
    private Double dailyRepaymentMin;
	private Double dailyRepaymentMax;
	private String dailyRepaymentDesc;
	
    private Double totalCost;
    private Double totalCostMin;
	private Double totalCostMax;
	private String totalCostDesc;
	
	public Double getApr() {
		return apr;
	}
	public void setApr(Double apr) {
		this.apr = apr;
	}
	public Double getMonthlyRate() {
		return monthlyRate;
	}
	public void setMonthlyRate(Double monthlyRate) {
		this.monthlyRate = monthlyRate;
	}
	public Double getAnnualRate() {
		return annualRate;
	}
	public void setAnnualRate(Double annualRate) {
		this.annualRate = annualRate;
	}
	public Double getMonthlyRepayment() {
		return monthlyRepayment;
	}
	public void setMonthlyRepayment(Double monthlyRepayment) {
		this.monthlyRepayment = monthlyRepayment;
	}
	public Double getTotalRepayment() {
		return totalRepayment;
	}
	public void setTotalRepayment(Double totalRepayment) {
		this.totalRepayment = totalRepayment;
	}
	public Double getDailyRepayment() {
		return dailyRepayment;
	}
	public void setDailyRepayment(Double dailyRepayment) {
		this.dailyRepayment = dailyRepayment;
	}
	public Double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}
	public Double getAprMin() {
		return aprMin;
	}
	public void setAprMin(Double aprMin) {
		this.aprMin = aprMin;
	}
	public Double getAprMax() {
		return aprMax;
	}
	public void setAprMax(Double aprMax) {
		this.aprMax = aprMax;
	}
	public String getAprDesc() {
		return aprDesc;
	}
	public void setAprDesc(String aprDesc) {
		this.aprDesc = aprDesc;
	}
	public Double getMonthlyRateMin() {
		return monthlyRateMin;
	}
	public void setMonthlyRateMin(Double monthlyRateMin) {
		this.monthlyRateMin = monthlyRateMin;
	}
	public Double getMonthlyRateMax() {
		return monthlyRateMax;
	}
	public void setMonthlyRateMax(Double monthlyRateMax) {
		this.monthlyRateMax = monthlyRateMax;
	}
	public String getMonthlyRateDesc() {
		return monthlyRateDesc;
	}
	public void setMonthlyRateDesc(String monthlyRateDesc) {
		this.monthlyRateDesc = monthlyRateDesc;
	}
	public Double getAnnualRateMin() {
		return annualRateMin;
	}
	public void setAnnualRateMin(Double annualRateMin) {
		this.annualRateMin = annualRateMin;
	}
	public Double getAnnualRateMax() {
		return annualRateMax;
	}
	public void setAnnualRateMax(Double annualRateMax) {
		this.annualRateMax = annualRateMax;
	}
	public String getAnnualRateDesc() {
		return annualRateDesc;
	}
	public void setAnnualRateDesc(String annualRateDesc) {
		this.annualRateDesc = annualRateDesc;
	}
	public Double getMonthlyRepaymentMin() {
		return monthlyRepaymentMin;
	}
	public void setMonthlyRepaymentMin(Double monthlyRepaymentMin) {
		this.monthlyRepaymentMin = monthlyRepaymentMin;
	}
	public Double getMonthlyRepaymentMax() {
		return monthlyRepaymentMax;
	}
	public void setMonthlyRepaymentMax(Double monthlyRepaymentMax) {
		this.monthlyRepaymentMax = monthlyRepaymentMax;
	}
	public String getMontlyRepaymentDesc() {
		return montlyRepaymentDesc;
	}
	public void setMontlyRepaymentDesc(String montlyRepaymentDesc) {
		this.montlyRepaymentDesc = montlyRepaymentDesc;
	}
	public Double getTotalRepaymentMin() {
		return totalRepaymentMin;
	}
	public void setTotalRepaymentMin(Double totalRepaymentMin) {
		this.totalRepaymentMin = totalRepaymentMin;
	}
	public Double getTotalRepaymentMax() {
		return totalRepaymentMax;
	}
	public void setTotalRepaymentMax(Double totalRepaymentMax) {
		this.totalRepaymentMax = totalRepaymentMax;
	}
	public String getTotalRepaymentDesc() {
		return totalRepaymentDesc;
	}
	public void setTotalRepaymentDesc(String totalRepaymentDesc) {
		this.totalRepaymentDesc = totalRepaymentDesc;
	}
	public Double getDailyRepaymentMin() {
		return dailyRepaymentMin;
	}
	public void setDailyRepaymentMin(Double dailyRepaymentMin) {
		this.dailyRepaymentMin = dailyRepaymentMin;
	}
	public Double getDailyRepaymentMax() {
		return dailyRepaymentMax;
	}
	public void setDailyRepaymentMax(Double dailyRepaymentMax) {
		this.dailyRepaymentMax = dailyRepaymentMax;
	}
	public String getDailyRepaymentDesc() {
		return dailyRepaymentDesc;
	}
	public void setDailyRepaymentDesc(String dailyRepaymentDesc) {
		this.dailyRepaymentDesc = dailyRepaymentDesc;
	}
	public Double getTotalCostMin() {
		return totalCostMin;
	}
	public void setTotalCostMin(Double totalCostMin) {
		this.totalCostMin = totalCostMin;
	}
	public Double getTotalCostMax() {
		return totalCostMax;
	}
	public void setTotalCostMax(Double totalCostMax) {
		this.totalCostMax = totalCostMax;
	}
	public String getTotalCostDesc() {
		return totalCostDesc;
	}
	public void setTotalCostDesc(String totalCostDesc) {
		this.totalCostDesc = totalCostDesc;
	}

}
