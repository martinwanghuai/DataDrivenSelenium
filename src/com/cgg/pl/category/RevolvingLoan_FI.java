package com.cgg.pl.category;

import java.util.List;

import org.openqa.selenium.By;

import com.cgg.model.PLAPIJsonObject;
import com.cgg.model.PLJsonObject;
import com.cgg.model.PersonalLoanConfig;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;

import utility.WebDriverUtils;

public class RevolvingLoan_FI extends TermLoan_FI {

	protected static final long serialVersionUID = 1L;
	
	public RevolvingLoan_FI(){
		
		super();
	}
	
	public RevolvingLoan_FI(PersonalLoan obj){
		
		super(obj);
	}
	
	public RevolvingLoan_FI(TermLoan_FI obj){
		
		super(obj);
		this.setInterestRate(obj.getInterestRate());
	}
	
	public RevolvingLoan_FI(PLAPIJsonObject obj){
		
		super(obj);
	}

	public static List<PersonalLoan> getPersonalLoansFromUI(WebDriverUtils driver, By by){
		
		List<PersonalLoan> objs = PersonalLoan.getPersonalLoansFromUI(driver, by);
		
		List<RevolvingLoan_FI> results = FluentIterable.from(objs).transform(new Function<PersonalLoan, RevolvingLoan_FI>(){
			
			@Override
			public RevolvingLoan_FI apply(final PersonalLoan obj){
				
				RevolvingLoan_FI result = new RevolvingLoan_FI(obj);
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
			public RevolvingLoan_FI apply(final PLJsonObject obj){
			
				TermLoan_FI termLoan = PLJsonObject.convertPLJsonObjectIntoTermLoan(instance, obj);
				return termLoan == null ? null: new RevolvingLoan_FI(termLoan);
			}
		};
	}
}
