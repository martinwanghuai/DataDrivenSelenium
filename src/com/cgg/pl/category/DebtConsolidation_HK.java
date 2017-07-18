package com.cgg.pl.category;

import java.util.List;

import org.openqa.selenium.By;

import com.cgg.model.PLAPIJsonObject;
import com.cgg.model.PLJsonObject;
import com.cgg.model.PersonalLoanConfig;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;

import pageObjects.CommonFindObjects;
import utility.MathUtils;
import utility.WebDriverUtils;

public class DebtConsolidation_HK extends PersonalInstalment_HK {

	protected static final long serialVersionUID = 1L;
	
	public DebtConsolidation_HK(){
		
		super();
	}
	
	public DebtConsolidation_HK(PersonalLoan obj){
		
		super(obj);
	}
	
	public DebtConsolidation_HK(PersonalInstalment_HK obj){
		
		super(obj);
	}
	
	public DebtConsolidation_HK(PLAPIJsonObject obj){
		
		super(obj);
		
		if(obj.getAttributes() instanceof Attributes_HK){
			Attributes_HK attrs = ((Attributes_HK)obj.getAttributes());
			if(attrs.getMonthlyRate()!= null){
				this.annualInterestRate = attrs.getMonthlyRate();		
			}
			if(attrs.getTotalRepayment() != null){
				this.totalRepayment = attrs.getTotalRepayment();	
			}
			if(attrs.getMonthlyRepayment() != null){
				this.monthlyRepayment = attrs.getMonthlyRepayment();	
			}
		}
	}
	

	public static List<PersonalLoan> getPersonalLoansFromUI(WebDriverUtils driver, By by){
		
		List<PersonalLoan> objs = PersonalLoan.getPersonalLoansFromUI(driver, by);
		List<DebtConsolidation_HK> results = FluentIterable.from(objs).transform(new Function<PersonalLoan, DebtConsolidation_HK>(){
			
			@Override
			public DebtConsolidation_HK apply(final PersonalLoan obj){
				
				DebtConsolidation_HK result = new DebtConsolidation_HK(obj);
				result.setInterestRate(obj.getApr());
//				result.setApr(obj.getEir());
				return result;
			}
		}).toList();
		
		return (List<PersonalLoan>)(List<?>)results;
	}
	
	@Override
	public boolean sameSpecificValues(Object obj) {
		
		DebtConsolidation_HK other = (DebtConsolidation_HK) obj;
		if (!MathUtils.isWithinRange(interestRate, other.interestRate)) {
			String msg = "interest rate mismatch" + "\nJSON - rate: " + interestRate + "\nUI - rate: " + other.interestRate + "\n"; 
			System.out.println(msg);
			CommonFindObjects.loggerMethodFailure(msg);
			return false;
		}
		if (!MathUtils.isWithinRange(monthlyRepayment, other.monthlyRepayment)) {
			String msg = "monthly repayment mismatch" + "\nJSON - repayment: " + monthlyRepayment + "\nUI - repayment: " + other.monthlyRepayment + "\n"; 
			System.out.println(msg);
			CommonFindObjects.loggerMethodFailure(msg);
			return false;
		}
		if (!MathUtils.isWithinRange(totalRepayment, other.totalRepayment)) {
			String msg = "total repayment mismatch" + "\nJSON - repayment: " + totalRepayment + "\nUI - repayment: " + other.totalRepayment + "\n"; 
			System.out.println(msg);
			CommonFindObjects.loggerMethodFailure(msg);
			return false;
		}
		return true;
	}

	public static Function<PLJsonObject, PersonalLoan> getPersonalLoansFromJsonObjects(final PersonalLoanConfig instance){
		
		return new Function<PLJsonObject, PersonalLoan>(){
			
			@Override
			public DebtConsolidation_HK apply(final PLJsonObject obj){
			
				DebtConsolidation_HK result = new DebtConsolidation_HK(PLJsonObject.convertPLJsonObjectIntoPersonalInstalmentHK(instance, obj));
				result.setInterestRate(result.getAnnualInterestRate());
				return result;
			}
		};
	}
	
	@Override
	public String toString(){
		
		return super.toString();
	}
}
