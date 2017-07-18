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

public class CreditLine_SG extends PersonalLoan {
	
	protected static final long serialVersionUID = 1L;
	
	protected double annualFee;
	protected double minMonthlyRepayment;
	protected double lowestAnnualFlatRate;
	
	public CreditLine_SG(){
		super();
	}
	
	public CreditLine_SG(PersonalLoan obj){
		
		super(obj);
	}
	
	public CreditLine_SG(PLAPIJsonObject obj){
		
		super(obj);
		if(obj.getAttributes() instanceof Attributes_SG_CredlitLine){
			Attributes_SG_CredlitLine attrs = (Attributes_SG_CredlitLine) obj.getAttributes();
			if(attrs.getAnnualFee() != null){
				this.annualFee = attrs.getAnnualFee();	
			}
			if(attrs.getMonthlyRepaymentMin() != null){
				this.minMonthlyRepayment = attrs.getMonthlyRepaymentMin();	
			}
			if(attrs.getLowestAnnualFlatRate() != null){
				this.lowestAnnualFlatRate = attrs.getLowestAnnualFlatRate();	
			}
		}
	}
	
	public double getAnnualFee() {
		return annualFee;
	}

	public void setAnnualFee(double annualFee) {
		this.annualFee = annualFee;
	}

	public double getMinMonthlyRepayment() {
		return minMonthlyRepayment;
	}

	public void setMinMonthlyRepayment(double minMonthlyRepayment) {
		this.minMonthlyRepayment = minMonthlyRepayment;
	}

	public double getLowestAnnualFlatRate() {
		return lowestAnnualFlatRate;
	}

	public void setLowestAnnualFlatRate(double lowestAnnualFlatRate) {
		this.lowestAnnualFlatRate = lowestAnnualFlatRate;
	}
	
	public static List<PersonalLoan> getPersonalLoansFromUI(WebDriverUtils driver, By by){
		
		List<PersonalLoan> objs = PersonalLoan.getPersonalLoansFromUI(driver, by);
		List<CreditLine_SG> results = FluentIterable.from(objs).transform(new Function<PersonalLoan, CreditLine_SG>(){
			
			@Override
			public CreditLine_SG apply(final PersonalLoan obj){
				
				CreditLine_SG result = new CreditLine_SG(obj);
				result.setAnnualFee(obj.getApr());
				result.setMinMonthlyRepayment(obj.getTotalRepayment());
				result.setLowestAnnualFlatRate(obj.getMonthlyRepayment());
				return result;
			}
		}).toList();
		
		return (List<PersonalLoan>)(List<?>)results;
	}
	
	public <T extends PersonalLoan> void sortPersonalLoans(List<T> loanList, final SortingKeyword sortKey){

		sortPersonalLoans(loanList, getSortingKeywords(sortKey));
	}

	public List<SortingKeyword> getSortingKeywords(final SortingKeyword sortKey) {
		List<SortingKeyword> sortKeys = null;
		switch(sortKey){
		case AnnualFee:
			sortKeys = Lists.newArrayList(SortingKeyword.AnnualFee, SortingKeyword.LowestAnnualFlatRate, SortingKeyword.MinMonthlyRepayment);
			break;
		case MinMonthlyRepayment:
			sortKeys = Lists.newArrayList(SortingKeyword.MinMonthlyRepayment, SortingKeyword.LowestAnnualFlatRate, SortingKeyword.AnnualFee);
			break;
		case LowestAnnualFlatRate:
			sortKeys = Lists.newArrayList(SortingKeyword.LowestAnnualFlatRate, SortingKeyword.MinMonthlyRepayment, SortingKeyword.AnnualFee);
			break;
		}
		return sortKeys;
	}

	@Override
	public String toString() {
		
		return this.getClass().getSimpleName() + " [ID=" + ID + ",productName=" + productName + ", isSponsoredProduct=" + isSponsoredProduct + ",annualFee=" + MathUtils.round(annualFee, 2) + ", minMonthlyRepayment=" + MathUtils.round(minMonthlyRepayment, 2)
				+ ", lowestAnnualFlatRate=" + MathUtils.round(lowestAnnualFlatRate,2) + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(annualFee);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(lowestAnnualFlatRate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(minMonthlyRepayment);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean sameSpecificValues(Object obj) {
		
		boolean result = true;
		CreditLine_SG other = (CreditLine_SG) obj;
		if (!MathUtils.isWithinRange(annualFee, other.annualFee)) {
			String msg = "annual fee mismatch" + "\nJSON - fee: " + annualFee + "\nUI - fee: " + other.annualFee + "\n"; 
			System.out.println(msg);
			CommonFindObjects.loggerMethodFailure(msg);
			result = false;
		}
		if (!MathUtils.isWithinRange(lowestAnnualFlatRate, other.lowestAnnualFlatRate)) {
			String msg = "lowest annual flat rate mismatch" + "\nJSON - rate: " + lowestAnnualFlatRate + "\nUI - rate: " + other.lowestAnnualFlatRate + "\n"; 
			System.out.println(msg);
			CommonFindObjects.loggerMethodFailure(msg);
			result = false;
		}
		if (!MathUtils.isWithinRange(minMonthlyRepayment, other.minMonthlyRepayment)) {
			String msg = "min monthly repayment mismatch" + "\nJSON - repayment: " + minMonthlyRepayment + "\nUI - repayment: " + other.minMonthlyRepayment + "\n"; 
			System.out.println(msg);
			CommonFindObjects.loggerMethodFailure(msg);
			result = false;
		}
		return result;
	}

	public static Function<PLJsonObject, PersonalLoan> getPersonalLoansFromJsonObjects(final PersonalLoanConfig instance){
		
		return new Function<PLJsonObject, PersonalLoan>(){
			
			@Override
			public CreditLine_SG apply(final PLJsonObject obj){
			
				PersonalLoan loan = PersonalLoan.getPersonalLoansFromJsonObject(instance, obj);
				if(loan != null){
					CreditLine_SG result = new CreditLine_SG(loan);
					
					PLJsonObject.Fee fee = obj.getFee();
					//Annual Fee
					result.setAnnualFee(fee.getAnnualHandlingFee());
					
					//Min. Monthly Repayment
					result.setMinMonthlyRepayment(
							MathUtils.round(instance.getLoanAmount() * Double.parseDouble(fee.getMonthlyRepaymentPercent()) / 100.0, 2));
					
					//Lowest Annual Flat
					result.setLowestAnnualFlatRate(fee.getAnnualRate());
					
					return result;	
				}else{
					return null;
				}
			}
		};
	}

}
