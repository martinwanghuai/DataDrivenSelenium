package com.cgg.pl.category;

import java.util.List;

import org.openqa.selenium.By;

import com.cgg.model.PLAPIJsonObject;
import com.cgg.model.PLJsonObject;
import com.cgg.model.PersonalLoanConfig;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;

import utility.WebDriverUtils;

public class TermLoan_FI extends OnlineLoan_DK {

	protected static final long serialVersionUID = 1L;
	
	public TermLoan_FI(){
		
		super();
	}
	
	public TermLoan_FI(PersonalLoan obj){
		
		super(obj);
	}

	public TermLoan_FI(PLAPIJsonObject obj){
		
		super(obj);
		if(obj.getAttributes() instanceof Attributes_FI){
			Attributes_FI attrs = (Attributes_FI)obj.getAttributes();
			if(attrs.getApr() != null){
				this.apr = attrs.getApr();
			}
			if(attrs.getMonthlyRepayment() != null){
				this.monthlyRepayment = attrs.getMonthlyRepayment();	
			}
			if(attrs.getAnnualRate() != null){
				this.interestRate = attrs.getAnnualRate();
			}
			if(attrs.getTotalRepayment() != null){
				this.totalRepayment = attrs.getTotalRepayment();
			}
		}
	}
	
	public static List<PersonalLoan> getPersonalLoansFromUI(WebDriverUtils driver, By by){
		
		List<PersonalLoan> objs = PersonalLoan.getPersonalLoansFromUI(driver, by);
		
		List<TermLoan_FI> results = FluentIterable.from(objs).transform(new Function<PersonalLoan, TermLoan_FI>(){
			
			@Override
			public TermLoan_FI apply(final PersonalLoan obj){
				
				TermLoan_FI result = new TermLoan_FI(obj);
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
			public TermLoan_FI apply(final PLJsonObject obj){
			
				return PLJsonObject.convertPLJsonObjectIntoTermLoan(instance, obj);
			}
		};
	}
	
	@Override
	public String toString(){
		
		return super.toString();
	}
}
