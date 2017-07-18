package com.cgg.pl.category;

import com.cgg.model.PLAPIJsonObject;
import com.cgg.model.PLJsonObject;
import com.cgg.model.PersonalLoanConfig;
import com.cgg.model.PLJsonObject.Locale.ResponseTime;
import com.google.common.base.Function;

import utility.Checker;
import utility.MathUtils;

public class QuickLoan_MX extends QuickLoan_DK {

	protected static final long serialVersionUID = 1L;
	
	public QuickLoan_MX(){
		
		super();
	}
	
	public QuickLoan_MX(PersonalLoan obj){
		
		super(obj);
	}
	
	public QuickLoan_MX(PLAPIJsonObject obj){
		
		super(obj);
		if(obj.getAttributes() instanceof Attributes_MX){
			Attributes_MX attrs = (Attributes_MX)obj.getAttributes();
			if(attrs.getTotalInterest() != null){
				this.cost = attrs.getTotalInterest();	
			}
			if(attrs.getResponseTime() != null){
				com.cgg.pl.category.ResponseTime time = attrs.getResponseTime();
				if(time.getDays() != null){
					this.responseTime = time.getDays() + ""; 
				}else if(time.getHours() != null){
					this.responseTime = time.getHours() + "";
				}else if(time.getMinutes() != null){
					this.responseTime = time.getMinutes() + "";
				}
			}
			if(attrs.getTotalRepayment() != null){
				this.totalRepayment = attrs.getTotalRepayment();
			}
		}
	}

	public static Function<PLJsonObject, PersonalLoan> getPersonalLoansFromJsonObjects(final PersonalLoanConfig instance){
		
		return new Function<PLJsonObject, PersonalLoan>(){
			
			@Override
			public QuickLoan_MX apply(final PLJsonObject obj){
			
				PersonalLoan loan = PersonalLoan.getPersonalLoansFromJsonObject(instance, obj);
				if(loan != null){
					QuickLoan_MX result = new QuickLoan_MX(loan);
					
					ResponseTime time = result.getLocale().getResponseTime();
					if(!Checker.isBlank(time.getMinutes())){
						result.setResponseTime(time.getMinutes());	
					}else if(!Checker.isBlank(time.getHours())){
						result.setResponseTime(time.getHours());
					}else if(!Checker.isBlank(time.getDays())){
						result.setResponseTime(time.getDays());
					}
					
					double annualInterestRate = result.getInterest().getInterestValue();
					double totalInterest = instance.getLoanAmount() * instance.getLoanTenure_Month() * (annualInterestRate/100) * 1.16;
					double openingFeeTotal = 0.0;
					if(obj.getFee() != null && obj.getFee().getStartUpFee() > 0.0){
						openingFeeTotal += obj.getFee().getStartUpFee();
					}
					
					if(obj.getFee() != null && MathUtils.extractDouble(obj.getFee().getStartUpFeePercent()) > 0.0){
						openingFeeTotal += instance.getLoanAmount() * MathUtils.extractDouble(obj.getFee().getStartUpFeePercent())/100.0;
					}

					double totalRepayment = instance.getLoanAmount() + MathUtils.round(totalInterest,0) + MathUtils.round(openingFeeTotal,0);
					result.setTotalRepayment(MathUtils.round(totalRepayment,0));
					
					result.setCost(MathUtils.isLargerThan(result.getTotalRepayment() - instance.getLoanAmount(), 0.0) ? result.getTotalRepayment() - instance.getLoanAmount() : 0.0);					
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
