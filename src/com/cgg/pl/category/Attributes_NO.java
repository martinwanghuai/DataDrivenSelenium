package com.cgg.pl.category;

public class Attributes_NO extends AbstractAttributes {

	private Double startUpFee;
	private Boolean startUpFeeDeductedOfLoan;
	private Double installmentMonthlyFee;
	private Double electronicInvoiceFee;
	private Double postInvoiceFee;
	private Double latePayCharge;
	
	public Double getStartUpFee() {
		return startUpFee;
	}
	public void setStartUpFee(Double startUpFee) {
		this.startUpFee = startUpFee;
	}
	public Boolean getStartUpFeeDeductedOfLoan() {
		return startUpFeeDeductedOfLoan;
	}
	public void setStartUpFeeDeductedOfLoan(Boolean startUpFeeDeductedOfLoan) {
		this.startUpFeeDeductedOfLoan = startUpFeeDeductedOfLoan;
	}
	public Double getInstallmentMonthlyFee() {
		return installmentMonthlyFee;
	}
	public void setInstallmentMonthlyFee(Double installmentMonthlyFee) {
		this.installmentMonthlyFee = installmentMonthlyFee;
	}
	public Double getElectronicInvoiceFee() {
		return electronicInvoiceFee;
	}
	public void setElectronicInvoiceFee(Double electronicInvoiceFee) {
		this.electronicInvoiceFee = electronicInvoiceFee;
	}
	public Double getPostInvoiceFee() {
		return postInvoiceFee;
	}
	public void setPostInvoiceFee(Double postInvoiceFee) {
		this.postInvoiceFee = postInvoiceFee;
	}
	public Double getLatePayCharge() {
		return latePayCharge;
	}
	public void setLatePayCharge(Double latePayCharge) {
		this.latePayCharge = latePayCharge;
	}
}
