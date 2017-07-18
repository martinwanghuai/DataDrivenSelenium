package com.cgg.pl.category;

import java.util.List;

import org.apache.poi.ss.formula.eval.NumberEval;
import org.apache.poi.ss.formula.eval.ValueEval;
import org.apache.poi.ss.formula.functions.Rate;
import org.openqa.selenium.By;

import com.cgg.model.PLAPIJsonObject;
import com.cgg.model.PLJsonObject;
import com.cgg.model.PersonalLoanConfig;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;

import pageObjects.CommonFindObjects;
import utility.Checker;
import utility.MathUtils;
import utility.WebDriverUtils;

public class OnlineLoan_DK extends GeneralInstalmentLoan_TW {

	protected static final long serialVersionUID = 1L;
	
	
	public OnlineLoan_DK(){
		
		super();
	}
	
	public OnlineLoan_DK(PersonalLoan obj){
		
		super(obj);
	}
	
	public OnlineLoan_DK(PLAPIJsonObject obj){
		
		super(obj);
		if(obj.getAttributes() instanceof Attributes_DK){
			Attributes_DK attrs = (Attributes_DK) obj.getAttributes();
			if(attrs.getApr() != null){
				this.apr = attrs.getApr();
			}
			if(attrs.getMonthlyRate() != null){
				this.interestRate = attrs.getAnnualRate();	
			}
			if(attrs.getMonthlyRepayment() != null){
				this.monthlyRepayment = attrs.getMonthlyRepayment();	
			}
		}
	}

	public static List<PersonalLoan> getPersonalLoansFromUI(WebDriverUtils driver, By by){
		
		List<PersonalLoan> objs = PersonalLoan.getPersonalLoansFromUI(driver, by);
		
		List<OnlineLoan_DK> results = FluentIterable.from(objs).transform(new Function<PersonalLoan, OnlineLoan_DK>(){
			
			@Override
			public OnlineLoan_DK apply(final PersonalLoan obj){
				
				OnlineLoan_DK result = new OnlineLoan_DK(obj);
				result.setInterestRate(obj.getApr());
				result.setApr(obj.getTotalRepayment());
				return result;
			}
		}).toList();
		
		return (List<PersonalLoan>)(List<?>)results;
		
	}
	
	public static Function<PLJsonObject, PersonalLoan> getPersonalLoansFromJsonObjects(final PersonalLoanConfig instance){
		
		return new Function<PLJsonObject, PersonalLoan>(){
			
			@Override
			public OnlineLoan_DK apply(final PLJsonObject obj){
			
				PersonalLoan loan = PersonalLoan.getPersonalLoansFromJsonObject(instance, obj);
				if(loan != null){
					OnlineLoan_DK result = new OnlineLoan_DK(loan);
					double monthlyInterestRate = result.getAnnualInterestRate(); 
					result.setInterestRate(monthlyInterestRate);
					
					//Monthly repayment
					double variableFee1 = 0.0, variableFee2 = 0.0, fixedFee = 0.0, totalFees = 0.0;
					if(!Checker.isBlank(obj.getFee().getStartUpFeePercent())){
						variableFee1 = Double.parseDouble(obj.getFee().getStartUpFeePercent())* instance.getLoanAmount()/100.0;
					}
					if(!Checker.isBlank(obj.getFee().getMinStartUpFee())){
						variableFee2 = Double.parseDouble(obj.getFee().getMinStartUpFee());
					}
					double variableFee = Math.max(variableFee1, variableFee2);
					if(MathUtils.isLargerThan(obj.getFee().getFixedHandlingFee(), 0.0)){
						fixedFee = obj.getFee().getFixedHandlingFee(); 
					}
					totalFees = variableFee + fixedFee;
					
					double totalLoanAmount = instance.getLoanAmount() + totalFees;
					double subCal = Math.pow(1+monthlyInterestRate/100.0, 1.0/12.0)-1;
					double subCal2 = 1-1.0/Math.pow(1+subCal, instance.getLoanTenure_Month());
					double monthlyFees = MathUtils.extractDouble(obj.getFee().getInstallmentMonthlyFee());
					double monthlyRepayment = totalLoanAmount/(subCal2/subCal) + monthlyFees;
					result.setMonthlyRepayment(MathUtils.round(monthlyRepayment, 0));

					//APR
					double rate = MathUtils.calculateIRR(instance.getLoanAmount(), instance.getLoanTenure_Month(), monthlyRepayment);
					double  APR = (Math.pow(1 + rate, 12) -1)*100 ;
					result.setApr(MathUtils.round(APR, 2));
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
			sortKeys = Lists.newArrayList(SortingKeyword.InterestRate, SortingKeyword.MonthlyRepayment, SortingKeyword.APR);
			break;
		case MonthlyRepayment:
			sortKeys = Lists.newArrayList(SortingKeyword.MonthlyRepayment, SortingKeyword.APR, SortingKeyword.InterestRate);
			break;
		case APR:
			sortKeys = Lists.newArrayList(SortingKeyword.APR, SortingKeyword.MonthlyRepayment, SortingKeyword.InterestRate);
			break;
		}
		return sortKeys;
	}
	
	@Override
	public boolean sameSpecificValues(Object obj) {
		
		boolean result = true;
		OnlineLoan_DK other = (OnlineLoan_DK) obj;
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
		if (!MathUtils.isWithinRange(apr, other.apr)) {
			String msg = "apr mismatch" + "\nJSON - apr: " + apr + "\nUI - apr: " + other.apr + "\n"; 
			System.out.println(msg);
			CommonFindObjects.loggerMethodFailure(msg);
			result = false;
		}
		return result;
	}
	
	@Override
	public String toString() {

		return this.getClass().getSimpleName() + " [ID=" + ID + ",productName=" + productName + ", isSponsoredProduct="
				+ isSponsoredProduct + ", interestRate=" + MathUtils.round(interestRate, 2) + ", APR=" + MathUtils.round(apr,2) + ", monthlyRepayment="
				+ MathUtils.round(monthlyRepayment,0) + "]";
	}
}
