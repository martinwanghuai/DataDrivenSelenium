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

public class GeneralInstalmentLoan_TW extends PersonalInstalment_HK {

	protected static final long serialVersionUID = 1L;
	
	public GeneralInstalmentLoan_TW(){
		
		super();
	}
	
	public GeneralInstalmentLoan_TW(PersonalLoan obj){
		
		super(obj);
	}
	
	public GeneralInstalmentLoan_TW(PLAPIJsonObject obj){
		
		super(obj);
		if(obj.getAttributes() instanceof Attributes_TW){
			Attributes_TW attrs = (Attributes_TW)obj.getAttributes();
			
			if(attrs.getAnnualRate() != null){
				this.interestRate = attrs.getAnnualRate();	
			}
			
			if(attrs.getAnnualHandlingFee() != null){
				this.annualHandlingFee = attrs.getAnnualHandlingFee();	
			}
			
			if(attrs.getMonthlyRepayment() != null){
				this.monthlyRepayment = attrs.getMonthlyRepayment();	
			}
			
			if(attrs.getTotalRepayment() != null){
				this.totalRepayment = attrs.getTotalRepayment();	
			}
		}
	}

	public static List<PersonalLoan> getPersonalLoansFromUI(WebDriverUtils driver, By by){
		
		List<PersonalLoan> objs = PersonalLoan.getPersonalLoansFromUI(driver, by);
		
		List<GeneralInstalmentLoan_TW> results = FluentIterable.from(objs).transform(new Function<PersonalLoan, GeneralInstalmentLoan_TW>(){
			
			@Override
			public GeneralInstalmentLoan_TW apply(final PersonalLoan obj){
				
				GeneralInstalmentLoan_TW result = new GeneralInstalmentLoan_TW(obj);
				result.setInterestRate(obj.getApr());
				result.setAnnualHandlingFee(obj.getTotalRepayment());
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
		case InterestRate:
			sortKeys = Lists.newArrayList(SortingKeyword.InterestRate, SortingKeyword.MonthlyRepayment, SortingKeyword.AnnualHandlingFee);
			break;
		case MonthlyRepayment:
			sortKeys = Lists.newArrayList(SortingKeyword.MonthlyRepayment, SortingKeyword.AnnualHandlingFee, SortingKeyword.InterestRate);
			break;
		case AnnualHandlingFee:
			sortKeys = Lists.newArrayList(SortingKeyword.AnnualHandlingFee, SortingKeyword.MonthlyRepayment, SortingKeyword.InterestRate);
			break;
		}
		return sortKeys;
	}
	
	public static Function<PLJsonObject, PersonalLoan> getPersonalLoansFromJsonObjects(final PersonalLoanConfig instance){
		
		return new Function<PLJsonObject, PersonalLoan>(){
			
			@Override
			public GeneralInstalmentLoan_TW apply(final PLJsonObject obj){
			
				PersonalLoan loan = PersonalLoan.getPersonalLoansFromJsonObject(instance, obj);
				
				if(loan != null){
					GeneralInstalmentLoan_TW result = new GeneralInstalmentLoan_TW(loan);

					result.setInterestRate(result.getAnnualInterestRate());
					double interestRate_month = result.getInterestRate()/(12.0*100);
					
					double temp = Math.pow(1 + interestRate_month, instance.getLoanTenure_Month());
					final double monthlyPayment = instance.getLoanAmount() * (temp * interestRate_month / (temp -1));
					result.setMonthlyRepayment(MathUtils.round(monthlyPayment, 0));
					return result;	
				}else{
					return null;
				}
			}
		};
	}
	
	@Override
	public boolean sameSpecificValues(Object obj) {
		
		boolean result = true;
		GeneralInstalmentLoan_TW other = (GeneralInstalmentLoan_TW) obj;
		if (!MathUtils.isWithinRange(interestRate, other.interestRate)) {
			String msg = "interest rate mismatch" + "\nJSON - rate: " + interestRate + "\nUI - rate: " + other.interestRate + "\n"; 
			System.out.println(msg);
			CommonFindObjects.loggerMethodFailure(msg);
			result = false;
		}
		if (!MathUtils.isWithinRange(monthlyRepayment, other.monthlyRepayment)) {
			String msg = "monthly repayment mismatch" + "\nJSON - repayment: " + monthlyRepayment + "\nUI - repayment: " + other.monthlyRepayment + "\n"; 
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
				+ isSponsoredProduct + ", interestRate=" + MathUtils.round(interestRate, 2) + ", annualHandlingFee=" + MathUtils.round(annualHandlingFee, 0)
				+ ", monthlyRepayment=" + MathUtils.round(monthlyRepayment,0) + "]";
	}
}
