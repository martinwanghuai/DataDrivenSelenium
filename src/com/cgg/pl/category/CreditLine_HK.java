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

public class CreditLine_HK extends CreditLine_SG {

	protected static final long serialVersionUID = 1L;
	
	protected double annualInterestRate;
	protected double dailyRepayment;
	
	public CreditLine_HK(){
		super();
	}
	
	public CreditLine_HK(PersonalLoan obj){
		
		super(obj);
	}
	
	public CreditLine_HK(PLAPIJsonObject obj){
		
		super(obj);
		
		if(obj.getAttributes() instanceof Attributes_HK){
			Attributes_HK attrs = ((Attributes_HK)obj.getAttributes());
			if(attrs.getMonthlyRate() != null){
				this.annualInterestRate = attrs.getMonthlyRate();		
			}
			if(attrs.getMonthlyRepaymentMin() != null){
				this.minMonthlyRepayment = attrs.getMonthlyRepaymentMin();	
			}
			if(attrs.getDailyRepayment() != null){
				this.dailyRepayment = attrs.getDailyRepayment();	
			}
			if(attrs.getApr() != null){
				this.apr = attrs.getApr();
			}
		}
	}
	
	public double getAnnualInterestRate() {
		return annualInterestRate;
	}

	public void setAnnualInterestRate(double annualInterestRate) {
		this.annualInterestRate = annualInterestRate;
	}

	public double getDailyRepayment() {
		return dailyRepayment;
	}

	public void setDailyRepayment(double dailyRepayment) {
		this.dailyRepayment = dailyRepayment;
	}
	
	public static List<PersonalLoan> getPersonalLoansFromUI(WebDriverUtils driver, By by){
		
		List<PersonalLoan> objs = PersonalLoan.getPersonalLoansFromUI(driver, by);
		List<CreditLine_HK> results = FluentIterable.from(objs).transform(new Function<PersonalLoan, CreditLine_HK>(){
			
			@Override
			public CreditLine_HK apply(final PersonalLoan obj){
				
				CreditLine_HK result = new CreditLine_HK(obj);
				result.setApr(obj.getApr());
				result.setMinMonthlyRepayment(obj.getTotalRepayment());
				result.setDailyRepayment(obj.getMonthlyRepayment());
				return result;
			}
		}).toList();
		
		return (List<PersonalLoan>)(List<?>)results;
	}

	public <T extends PersonalLoan> void sortPersonalLoans(List<T> loanList, final SortingKeyword sortKey){
		
		sortPersonalLoans(loanList, getSortingKeywords(sortKey));
	}

	public List<SortingKeyword> getSortingKeywords(final SortingKeyword sortKey) {
		
		List<SortingKeyword> sortKeys = Lists.newArrayList();
		switch(sortKey){
		case APR:
			sortKeys = Lists.newArrayList(SortingKeyword.APR, SortingKeyword.DailyRepayment, SortingKeyword.MinMonthlyRepayment);
			break;
		case MinMonthlyRepayment:
			sortKeys = Lists.newArrayList(SortingKeyword.MinMonthlyRepayment, SortingKeyword.DailyRepayment, SortingKeyword.APR);
			break;
		case DailyRepayment:
			sortKeys = Lists.newArrayList(SortingKeyword.DailyRepayment, SortingKeyword.MinMonthlyRepayment, SortingKeyword.APR);
			break;
		}
		return sortKeys;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() +"[ID=" + ID + ", productName=" + productName + ", isSponsoredProduct=" + isSponsoredProduct
				+ ",apr=" + MathUtils.round(apr,3) + ", minMonthlyRepayment=" + MathUtils.round(minMonthlyRepayment,0)
				+ ", dailyRepayment=" + MathUtils.round(dailyRepayment,2) + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(annualInterestRate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(minMonthlyRepayment);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(dailyRepayment);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean sameSpecificValues(Object obj) {

		CreditLine_HK other = (CreditLine_HK) obj;
		if (!MathUtils.isWithinRange(apr, other.apr)) {
			String msg = "apr mismatch" + "\nJSON - apr: " + apr + "\nUI - apr: " + other.apr + "\n"; 
			System.out.println(msg);
			CommonFindObjects.loggerMethodFailure(msg);
			return false;
		}
		if (!MathUtils.isWithinRange(dailyRepayment, other.dailyRepayment)) {
			String msg = "daily repayment mismatch" + "\nJSON - repayment: " + dailyRepayment + "\nUI - repayment: " + other.dailyRepayment + "\n"; 
			System.out.println(msg);
			CommonFindObjects.loggerMethodFailure(msg);
			return false;
		}
		if (!MathUtils.isWithinRange(minMonthlyRepayment, other.minMonthlyRepayment)) {
			String msg = "min. monthly repayment mismatch" + "\nJSON - repayment: " + minMonthlyRepayment + "\nUI - repayment: " + other.minMonthlyRepayment + "\n"; 
			System.out.println(msg);
			CommonFindObjects.loggerMethodFailure(msg);
			return false;
		}
		return true;
	}

	public static Function<PLJsonObject, PersonalLoan> getPersonalLoansFromJsonObjects(final PersonalLoanConfig instance){
		
		return new Function<PLJsonObject, PersonalLoan>(){
			
			@Override
			public CreditLine_HK apply(final PLJsonObject obj){
			
				CreditLine_HK result = new CreditLine_HK(PLJsonObject.convertPLJsonObjectIntoPersonalInstalmentHK(instance, obj));
	
				PLJsonObject.Fee fee = obj.getFee();
				//Min. Monthly Repayment
				result.setMinMonthlyRepayment(
						MathUtils.round(instance.getLoanAmount() * Double.parseDouble(fee.getMonthlyRepaymentPercent()) / 100, 2));
				
				//Daily repayment
				result.setDailyRepayment(MathUtils.round(instance.getLoanAmount() * (result.getMonthlyInterestRate()/100)/365.0, 2));
				
				return result;
			}
		};
	}
}
