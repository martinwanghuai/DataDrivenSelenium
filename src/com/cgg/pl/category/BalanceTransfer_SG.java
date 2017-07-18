package com.cgg.pl.category;

import java.util.List;

import org.openqa.selenium.By;

import com.cgg.model.PLAPIJsonObject;
import com.cgg.model.PLJsonObject;
import com.cgg.model.PersonalLoanConfig;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;

import pageObjects.CommonFindObjects;
import utility.MathUtils;
import utility.WebDriverUtils;

public class BalanceTransfer_SG extends PersonalLoan {
	
	protected static final long serialVersionUID = 1L;
	
	protected double lowestTransferRate;
	protected double processingFee;
	protected double annualRate;
	
	public BalanceTransfer_SG(){
		
		super();
	}
	
	public BalanceTransfer_SG(String productName, boolean isSponsoredProduct, double lowestTransferRate,
			double processingFee, double annualRate, String ID) {
		super();
		this.productName = productName;
		this.isSponsoredProduct = isSponsoredProduct;
		this.lowestTransferRate = lowestTransferRate;
		this.processingFee = processingFee;
		this.annualRate = annualRate;
		this.ID = ID;
	}
	
	public BalanceTransfer_SG(PersonalLoan obj){
		
		super(obj);
	}
	
	public BalanceTransfer_SG(PLAPIJsonObject obj){
		
		super(obj);
		
		if(obj.getAttributes() instanceof Attributes_SG_BalanceTransfer){
			Attributes_SG_BalanceTransfer attrs = (Attributes_SG_BalanceTransfer)obj.getAttributes();
			if(attrs.getAnnualRate() != null){
				this.annualRate = attrs.getAnnualRate();	
			}
			if(attrs.getLowestTransferRate() != null){
				this.lowestTransferRate = attrs.getLowestTransferRate();	
			}
			if(attrs.getMinProcessingFee() != null){
				this.processingFee = attrs.getMinProcessingFee();	
			}
		}
	}
	
	public static List<PersonalLoan> getPersonalLoansFromUI(WebDriverUtils driver, By by){
		
		List<PersonalLoan> objs = PersonalLoan.getPersonalLoansFromUI(driver, by);
		List<BalanceTransfer_SG> results = FluentIterable.from(objs).transform(new Function<PersonalLoan, BalanceTransfer_SG>(){
			
			@Override
			public BalanceTransfer_SG apply(final PersonalLoan obj){
				
				BalanceTransfer_SG result = new BalanceTransfer_SG(obj);
				result.setAnnualRate(obj.getApr());
				result.setProcessingFee(obj.getTotalRepayment());
				result.setLowestTransferRate(obj.getMonthlyRepayment());
				return result;
			}
		}).toList();
		
		return (List<PersonalLoan>)(List<?>)results;
	}

	public double getLowestTransferRate() {
		return lowestTransferRate;
	}
	public void setLowestTransferRate(double lowestTransferRate) {
		this.lowestTransferRate = lowestTransferRate;
	}
	public double getProcessingFee() {
		return processingFee;
	}
	public void setProcessingFee(double processingFee) {
		this.processingFee = processingFee;
	}
	public double getAnnualRate() {
		return annualRate;
	}
	public void setAnnualRate(double annualRate) {
		this.annualRate = annualRate;
	}

	public <T extends PersonalLoan> void sortPersonalLoans(List<T> loanList, final SortingKeyword sortKey){
		
		sortPersonalLoans(loanList, getSortingKeywords(sortKey));
	}

	public List<SortingKeyword> getSortingKeywords(final SortingKeyword sortKey) {
		List<SortingKeyword> sortKeys = null;
		switch(sortKey){
		case LowestTransferRate:
			sortKeys = Lists.newArrayList(SortingKeyword.LowestTransferRate, SortingKeyword.ProcessingFee, SortingKeyword.AnnualRate);
			break;
		case ProcessingFee:
			sortKeys = Lists.newArrayList(SortingKeyword.ProcessingFee, SortingKeyword.AnnualRate, SortingKeyword.LowestTransferRate);
			break;
		case AnnualRate:
			sortKeys = Lists.newArrayList(SortingKeyword.AnnualRate, SortingKeyword.ProcessingFee, SortingKeyword.LowestTransferRate);
			break;
		}
		return sortKeys;
	}
	
	@Override
	public String toString() {
		
		return this.getClass().getSimpleName() + " [ID=" + ID +", ProductName=" + productName + ", isSponsoredProduct=" + isSponsoredProduct + ", annualRate=" + MathUtils.round(annualRate, 2) + ", processingFee="
				+ MathUtils.round(processingFee, 2)+ ", lowestTransferRate=" + MathUtils.round(lowestTransferRate,2)   + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(annualRate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(lowestTransferRate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(processingFee);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean sameSpecificValues(Object obj) {
		
		BalanceTransfer_SG other = (BalanceTransfer_SG) obj;
		if (!MathUtils.isWithinRange(annualRate, other.annualRate)) {
			String msg = "annual rate mismatch" + "\nJSON - rate: " + annualRate + "\nUI - rate: " + other.annualRate + "\n"; 
			System.out.println(msg);
			CommonFindObjects.loggerMethodFailure(msg);
			return false;
		}
		if (!MathUtils.isWithinRange(lowestTransferRate, other.lowestTransferRate)) {
			String msg = "lowest transfer rate mismatch" + "\nJSON - rate: " + lowestTransferRate + "\nUI - rate: " + other.lowestAnnualFlatRate + "\n"; 
			System.out.println(msg);
			CommonFindObjects.loggerMethodFailure(msg);
			return false;
		}
		if (!MathUtils.isWithinRange(processingFee, other.processingFee)) {
			String msg = "processing fee mismatch" + "\nJSON - fee: " + processingFee + "\nUI - fee: " + other.processingFee + "\n"; 
			System.out.println(msg);
			CommonFindObjects.loggerMethodFailure(msg);
			return false;
		}
		return true;
	}

	public static Function<PLJsonObject, PersonalLoan> getPersonalLoansFromJsonObjects(final PersonalLoanConfig instance){
		
		return new Function<PLJsonObject, PersonalLoan>(){
			
			@Override
			public BalanceTransfer_SG apply(final PLJsonObject obj){
				PersonalLoan loan = PersonalLoan.getPersonalLoansFromJsonObject(instance, obj);
				if(loan != null){
					BalanceTransfer_SG result = new BalanceTransfer_SG(loan);
					
					PLJsonObject.Fee fee = obj.getFee();
					//Processing fee
					if(fee.getHandlingFeeProfile().equalsIgnoreCase("percent")){
						result.setProcessingFee(MathUtils.round(instance.getLoanAmount() * result.getInterest().getProcessingFee()/100, 2));
					}else if(fee.getHandlingFeeProfile().equalsIgnoreCase("cash")){
						result.setProcessingFee(MathUtils.round(result.getInterest().getProcessingFee(), 2));
					}
					
					//Annual rate
					result.setAnnualRate(MathUtils.round(fee.getAnnualRate(), 2));
					
					//Transfer rate
					result.setLowestTransferRate(MathUtils.round(result.getInterest().getInterestValue(), 2));
					
					return result;	
				}else{
					return null;
				}
			}
		};
	}
}
