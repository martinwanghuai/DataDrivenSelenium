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

public class RevolvingCreditLoan_TW extends GeneralInstalmentLoan_TW {

	protected static final long serialVersionUID = 1L;
	protected double dailyRepayment;
	
	public RevolvingCreditLoan_TW(){
		
		super();
	}
	
	public RevolvingCreditLoan_TW(PersonalLoan obj){
		
		super(obj);
	}
	
	public RevolvingCreditLoan_TW(PLAPIJsonObject obj){
		
		super(obj);
		if(obj.getAttributes() instanceof Attributes_TW){
			Attributes_TW attrs = (Attributes_TW)obj.getAttributes();
			if(attrs.getAnnualHandlingFee() != null){
				this.annualHandlingFee = attrs.getAnnualHandlingFee();	
			}
			if(attrs.getDailyInterest() != null){
				this.dailyRepayment = attrs.getDailyInterest();	
			}
		}
	}
	
	public double getDailyRepayment() {
		return dailyRepayment;
	}
	
	public void setDailyRepayment(double dailyRepayment) {
		this.dailyRepayment = dailyRepayment;
	}
	
	public static List<PersonalLoan> getPersonalLoansFromUI(WebDriverUtils driver, By by){
		
		List<PersonalLoan> objs = PersonalLoan.getPersonalLoansFromUI(driver, by);
		
		List<RevolvingCreditLoan_TW> results = FluentIterable.from(objs).transform(new Function<PersonalLoan, RevolvingCreditLoan_TW>(){
			
			@Override
			public RevolvingCreditLoan_TW apply(final PersonalLoan obj){
				
				RevolvingCreditLoan_TW result = new RevolvingCreditLoan_TW(obj);
				result.setInterestRate(obj.getApr());
				result.setAnnualHandlingFee(obj.getTotalRepayment());
				result.setDailyRepayment(obj.getMonthlyRepayment());
				return result;
			}
		}).toList();
		
		return (List<PersonalLoan>)(List<?>)results;
		
	}
	
	public static Function<PLJsonObject, PersonalLoan> getPersonalLoansFromJsonObjects(final PersonalLoanConfig instance){
		
		return new Function<PLJsonObject, PersonalLoan>(){
			
			@Override
			public RevolvingCreditLoan_TW apply(final PLJsonObject obj){
			
				PersonalLoan loan = PersonalLoan.getPersonalLoansFromJsonObject(instance, obj);
				if(loan != null){
					RevolvingCreditLoan_TW result = new RevolvingCreditLoan_TW(loan);

					result.setInterestRate(result.getAnnualInterestRate());
				
					//Daily repayment
					result.setDailyRepayment(MathUtils.round(instance.getLoanAmount() * (result.getAnnualInterestRate()/100)/365.0, 0));
					
					return result;
	
				}else{
					return null;
				}
			}
		};
	}

	public <T extends PersonalLoan> void sortPersonalLoans(List<T> loanList, final SortingKeyword sortKey){
		
		sortPersonalLoans(loanList, getSortingKeywords(sortKey));
	}

	public List<SortingKeyword> getSortingKeywords(final SortingKeyword sortKey) {
		List<SortingKeyword> sortKeys = null;
		switch(sortKey){
		case InterestRate:
			sortKeys = Lists.newArrayList(SortingKeyword.InterestRate, SortingKeyword.DailyRepayment_TW, SortingKeyword.AnnualHandlingFee);
			break;
		case DailyRepayment_TW:
			sortKeys = Lists.newArrayList(SortingKeyword.DailyRepayment_TW, SortingKeyword.AnnualHandlingFee, SortingKeyword.InterestRate);
			break;
		case AnnualHandlingFee:
			sortKeys = Lists.newArrayList(SortingKeyword.AnnualHandlingFee, SortingKeyword.DailyRepayment_TW, SortingKeyword.InterestRate);
			break;
		}
		return sortKeys;
	}
	
	@Override
	public boolean sameSpecificValues(Object obj) {
		
		boolean result = true;
		RevolvingCreditLoan_TW other = (RevolvingCreditLoan_TW) obj;
		if (!MathUtils.isWithinRange(interestRate, other.interestRate)) {
			String msg = "interest rate mismatch" + "\nJSON - rate: " + interestRate + "\nUI - rate: " + other.interestRate + "\n"; 
			System.out.println(msg);
			CommonFindObjects.loggerMethodFailure(msg);
			result = false;
		}
		if (!MathUtils.isWithinRange(dailyRepayment, other.dailyRepayment)) {
			String msg = "daily repayment mismatch" + "\nJSON - repayment: " + dailyRepayment + "\nUI - repayment: " + other.dailyRepayment + "\n"; 
			System.out.println(msg);
			CommonFindObjects.loggerMethodFailure(msg);
			result = false;
		}
		if (!MathUtils.isWithinRange(annualHandlingFee, other.annualHandlingFee)) {
			String msg = "annual handling fee mismatch" + "\nJSON - fee: " + annualHandlingFee + "\nUI - fee: " + other.annualHandlingFee + "\n"; 
			System.out.println(msg);
			CommonFindObjects.loggerMethodFailure(msg);
			result = false;
		}
		return result;
	}

	@Override
	public String toString() {

		return this.getClass().getSimpleName() + " [ID=" + ID + ",productName=" + productName + ", isSponsoredProduct="
				+ isSponsoredProduct + ", interestRate=" + MathUtils.round(interestRate,2) + ", annualHandlingFee=" + MathUtils.round(annualHandlingFee,0)
				+ ", dailyRepayment=" + MathUtils.round(dailyRepayment,0) + "]";
	}
}
