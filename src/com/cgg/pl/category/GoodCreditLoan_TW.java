package com.cgg.pl.category;

import java.util.List;

import org.openqa.selenium.By;

import com.cgg.model.PLAPIJsonObject;
import com.cgg.model.PLJsonObject;
import com.cgg.model.PersonalLoanConfig;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;

import utility.MathUtils;
import utility.WebDriverUtils;

public class GoodCreditLoan_TW extends GeneralInstalmentLoan_TW {

	protected static final long serialVersionUID = 1L;
	
	public GoodCreditLoan_TW(){
		
		super();
	}
	
	public GoodCreditLoan_TW(PersonalLoan obj){
		
		super(obj);
	}
	
	public GoodCreditLoan_TW(PLAPIJsonObject obj){
		
		super(obj);
	}

	
	public static List<PersonalLoan> getPersonalLoansFromUI(WebDriverUtils driver, By by){
		
		List<PersonalLoan> objs = PersonalLoan.getPersonalLoansFromUI(driver, by);
		
		List<GoodCreditLoan_TW> results = FluentIterable.from(objs).transform(new Function<PersonalLoan, GoodCreditLoan_TW>(){
			
			@Override
			public GoodCreditLoan_TW apply(final PersonalLoan obj){
				
				GoodCreditLoan_TW result = new GoodCreditLoan_TW(obj);
				result.setInterestRate(obj.getApr());
				result.setAnnualHandlingFee(obj.getTotalRepayment());
				return result;
			}
		}).toList();
		
		return (List<PersonalLoan>)(List<?>)results;
		
	}
	
	public static Function<PLJsonObject, PersonalLoan> getPersonalLoansFromJsonObjects(final PersonalLoanConfig instance){
		
		return new Function<PLJsonObject, PersonalLoan>(){
			
			@Override
			public GoodCreditLoan_TW apply(final PLJsonObject obj){
			
				PersonalLoan loan = PersonalLoan.getPersonalLoansFromJsonObject(instance, obj);
				if(loan != null){
					GoodCreditLoan_TW result = new GoodCreditLoan_TW(loan);

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
	public String toString(){
		
		return super.toString();
	}
}
