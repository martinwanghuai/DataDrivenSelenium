package com.cgg.pl.category;

import java.util.List;

import org.apache.poi.ss.formula.functions.Finance;
import org.openqa.selenium.By;

import com.cgg.model.PLAPIJsonObject;
import com.cgg.model.PLJsonObject;
import com.cgg.model.PersonalLoanConfig;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;

import utility.Checker;
import utility.MathUtils;
import utility.WebDriverUtils;

public class PersonalInstalment_NO extends PersonalInstalment_SG {

	protected static final long serialVersionUID = 1L;
	
	public PersonalInstalment_NO(){
		
		super();
	}
	
	public PersonalInstalment_NO(PersonalLoan obj){
		
		super(obj);
	}
	
	public PersonalInstalment_NO(PLAPIJsonObject obj){
		
		super(obj);
		if(obj.getAttributes() instanceof Attributes_NO){
			Attributes_NO attrs = (Attributes_NO)obj.getAttributes();
			if(attrs.getApr() != null){
				this.apr = attrs.getApr();	
			}
			if(attrs.getTotalRepayment() != null){
				this.totalRepayment = attrs.getTotalRepayment();	
			}
			if( attrs.getMonthlyRepayment() != null){
				this.monthlyRepayment = attrs.getMonthlyRepayment();	
			}
		}
	}

	public static List<PersonalLoan> getPersonalLoansFromUI(WebDriverUtils driver, By by){
		
		List<PersonalLoan> objs = PersonalLoan.getPersonalLoansFromUI(driver, by);
		
		List<PersonalInstalment_NO> results = FluentIterable.from(objs).transform(new Function<PersonalLoan, PersonalInstalment_NO>(){
			
			@Override
			public PersonalInstalment_NO apply(final PersonalLoan obj){
				
				PersonalInstalment_NO result = new PersonalInstalment_NO(obj);
				return result;
			}
		}).toList();
		
		return (List<PersonalLoan>)(List<?>)results;
		
	}
													   					
	public static Function<PLJsonObject, PersonalLoan> getPersonalLoansFromJsonObjects(final PersonalLoanConfig instance){
		
		return new Function<PLJsonObject, PersonalLoan>(){
			
			@Override
			public PersonalInstalment_NO apply(final PLJsonObject obj){
				
				PersonalLoan loan = PersonalLoan.getPersonalLoansFromJsonObject(instance, obj);
				if(loan != null){
					PersonalInstalment_NO result = new PersonalInstalment_NO(loan);
					double monthlyInterestRate = result.getAnnualInterestRate(); 
					
					//Monthly repayment
					double adjustedLoanAmount = instance.getLoanAmount();
					if(obj.getFee() != null){
						if(obj.getFee().getStartUpFee() > 0.0 && !Boolean.parseBoolean(obj.getFee().getStartUpFeeDeductedOfLoan())){
							adjustedLoanAmount += obj.getFee().getStartUpFee();
						}
					}
					double monthlyRepayment = Finance.pmt(monthlyInterestRate/100, (int)instance.getLoanTenure_Month(), - adjustedLoanAmount);
					if(obj.getFee() != null && !Checker.isBlank(obj.getFee().getInstallmentMonthlyFee())){
						monthlyRepayment += MathUtils.extractDouble(obj.getFee().getInstallmentMonthlyFee());	
					}
					
					result.setMonthlyRepayment(MathUtils.round(monthlyRepayment, 0));

					//Total repayment
					double totalRepayment = monthlyRepayment * instance.getLoanTenure_Month();
					double adjustedTotalRepayment = totalRepayment;
					if(obj.getFee() != null){
						if(MathUtils.isLargerThan(obj.getFee().getStartUpFee(), 0.0)){
							adjustedTotalRepayment += obj.getFee().getStartUpFee();
						}
					}
					
					result.setTotalRepayment(adjustedTotalRepayment);
					
					//APR
					double adjustedAprLoanAmount = obj.getFee() != null
							&& MathUtils.isLargerThan(obj.getFee().getStartUpFee(), 0.0)
							&& Boolean.parseBoolean(obj.getFee().getStartUpFeeDeductedOfLoan())
									? instance.getLoanAmount() - obj.getFee().getStartUpFee() : instance.getLoanAmount();
					double rate = MathUtils.calculateRate(adjustedAprLoanAmount, instance.getLoanTenure_Month(), monthlyRepayment);
					
					double  APR = (Math.pow(1 + rate, 12) -1) * 100 ;
					result.setApr(MathUtils.round(APR, 2));

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
