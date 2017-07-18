package com.cgg.pl.category;

public class Attributes_PT extends AbstractAttributes {

	private Double installmentMonthlyFee;
	private Boolean hasInstallmentMonthlyFee;
	private Double startUpFee;
	private Double startUpFeePercent;
	private Double minStartUpFee;
	private Double maxStartUpFee;
	private Boolean requiresLifeInsurance;
	
	public Double getInstallmentMonthlyFee() {
		return installmentMonthlyFee;
	}
	public void setInstallmentMonthlyFee(Double installmentMonthlyFee) {
		this.installmentMonthlyFee = installmentMonthlyFee;
	}
	public Boolean getHasInstallmentMonthlyFee() {
		return hasInstallmentMonthlyFee;
	}
	public void setHasInstallmentMonthlyFee(Boolean hasInstallmentMonthlyFee) {
		this.hasInstallmentMonthlyFee = hasInstallmentMonthlyFee;
	}
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
	public Double getMaxStartUpFee() {
		return maxStartUpFee;
	}
	public void setMaxStartUpFee(Double maxStartUpFee) {
		this.maxStartUpFee = maxStartUpFee;
	}
	public Boolean getRequiresLifeInsurance() {
		return requiresLifeInsurance;
	}
	public void setRequiresLifeInsurance(Boolean requiresLifeInsurance) {
		this.requiresLifeInsurance = requiresLifeInsurance;
	}
}
