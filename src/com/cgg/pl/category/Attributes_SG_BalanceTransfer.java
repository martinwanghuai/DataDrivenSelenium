package com.cgg.pl.category;

public class Attributes_SG_BalanceTransfer extends AbstractAttributes {

	private Double lowestTransferRate;
	private Double minProcessingFee;
	private String eir;
	
	public Double getLowestTransferRate() {
		return lowestTransferRate;
	}
	public void setLowestTransferRate(Double lowestTransferRate) {
		this.lowestTransferRate = lowestTransferRate;
	}
	public Double getMinProcessingFee() {
		return minProcessingFee;
	}
	public void setMinProcessingFee(Double minProcessingFee) {
		this.minProcessingFee = minProcessingFee;
	}
	public String getEir() {
		return eir;
	}
	public void setEir(String eir) {
		this.eir = eir;
	}
	
}
