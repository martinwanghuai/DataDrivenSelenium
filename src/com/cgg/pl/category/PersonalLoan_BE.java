package com.cgg.pl.category;

import java.util.List;

import org.openqa.selenium.By;

import com.cgg.model.PLAPIJsonObject;
import com.cgg.model.PLJsonObject;
import com.cgg.model.PersonalLoanConfig;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;

import utility.WebDriverUtils;

public class PersonalLoan_BE extends PersonalInstalment_SG {

	protected static final long serialVersionUID = 1L;
	
	public PersonalLoan_BE(){
		
		super();
	}
	
	public PersonalLoan_BE(PersonalLoan obj){
		
		super(obj);
	}
	
	public PersonalLoan_BE(PLAPIJsonObject obj){
		
		super(obj);
		if(obj.getAttributes() instanceof Attributes_BE){
			Attributes_BE attrs = (Attributes_BE)obj.getAttributes();
			if(attrs.getApr() != null){
				this.apr = attrs.getApr();
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
		
		List<PersonalLoan_BE> results = FluentIterable.from(objs).transform(new Function<PersonalLoan, PersonalLoan_BE>(){
			
			@Override
			public PersonalLoan_BE apply(final PersonalLoan obj){
				
				PersonalLoan_BE result = new PersonalLoan_BE(obj);
				return result;
			}
		}).toList();
		
		return (List<PersonalLoan>)(List<?>)results;
		
	}
	
	public static Function<PLJsonObject, PersonalLoan> getPersonalLoansFromJsonObjects(final PersonalLoanConfig instance){
		
		return new Function<PLJsonObject, PersonalLoan>(){
			
			@Override
			public PersonalLoan_BE apply(final PLJsonObject obj){
			
				PersonalLoan loan = PersonalLoan.getPersonalLoansFromJsonObject(instance, obj);
				if(loan != null){
					PersonalLoan_BE result = new PersonalLoan_BE(loan);
					
					//monthly repayment
					Double monthlyInterestRate = Math.pow( 1 + (result.getApr()/100.0), 1.0/12.0) - 1;
				    double monthlyRepayment = instance.getLoanAmount()/((1 - Math.pow( 1 + monthlyInterestRate, -instance.getLoanTenure_Month()))/monthlyInterestRate);
				    result.setMonthlyRepayment(monthlyRepayment);
				    
					//Total repayment
					double totalRepayment = monthlyRepayment * instance.getLoanTenure_Month();				
					result.setTotalRepayment(totalRepayment);

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
