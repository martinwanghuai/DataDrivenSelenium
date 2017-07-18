package com.cgg.pl.category;

import com.cgg.model.PLAPIJsonObject;
import com.cgg.model.PLJsonObject;
import com.cgg.model.PersonalLoanConfig;
import com.google.common.base.Function;

import utility.MathUtils;

public class PersonalInstalment_MX extends PersonalInstalment_HK {

	protected static final long serialVersionUID = 1L;
	
	public PersonalInstalment_MX(){
		
		super();
	}
	
	public PersonalInstalment_MX(PersonalLoan obj){
		
		super(obj);
	}

	public PersonalInstalment_MX(PLAPIJsonObject obj){
		
		super(obj);
		
		if(obj.getAttributes() instanceof Attributes_MX){
			Attributes_MX attrs = (Attributes_MX)obj.getAttributes();
			if(attrs.getTotalRepayment() != null){
				this.totalRepayment = attrs.getTotalRepayment();	
			}
			if(attrs.getMonthlyRepayment() != null){
				this.monthlyRepayment = attrs.getMonthlyRepayment();	
			}
			if(attrs.getAnnualRate() != null){
				this.interestRate =  attrs.getAnnualRate();
			}
		}
	}
	
	public static Function<PLJsonObject, PersonalLoan> getPersonalLoansFromJsonObjects(final PersonalLoanConfig instance){
		
		return new Function<PLJsonObject, PersonalLoan>(){
			
			@Override
			public PersonalInstalment_MX apply(final PLJsonObject obj){
			
				PersonalLoan loan = PersonalLoan.getPersonalLoansFromJsonObject(instance, obj);
				if(loan != null){
					PersonalInstalment_MX result = new PersonalInstalment_MX(loan);
					
					double annualInterestRate = result.getInterest().getInterestValue();
					result.setInterestRate(MathUtils.round(annualInterestRate, 2));
					double monthlyRate = annualInterestRate/12.0;
					
					double openingFeeTotal = 0.0;
					if(obj.getFee() != null && obj.getFee().getStartUpFee() > 0.0){
						openingFeeTotal += obj.getFee().getStartUpFee();
					}
					
					if(obj.getFee() != null && MathUtils.extractDouble(obj.getFee().getStartUpFeePercent()) > 0.0){
						openingFeeTotal += instance.getLoanAmount() * MathUtils.extractDouble(obj.getFee().getStartUpFeePercent())/100.0 * 1.16;
					}

					double adjustedLoanAmount = instance.getLoanAmount() + openingFeeTotal;
					double monthlyRepayment = ((monthlyRate / 100.0) * adjustedLoanAmount) / (1 - (Math.pow(1 + (monthlyRate / 100.0), - instance.getLoanTenure_Month())));
					result.setMonthlyRepayment(MathUtils.round(monthlyRepayment, 0));
					
					double totalRepayment = monthlyRepayment * instance.getLoanTenure_Month();
					result.setTotalRepayment(MathUtils.round(totalRepayment, 0));
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
