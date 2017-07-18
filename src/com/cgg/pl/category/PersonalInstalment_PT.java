package com.cgg.pl.category;

import org.apache.poi.ss.formula.functions.FinanceLib;

import com.cgg.model.PLAPIJsonObject;
import com.cgg.model.PLJsonObject;
import com.cgg.model.PersonalLoanConfig;
import com.google.common.base.Function;

import utility.MathUtils;

public class PersonalInstalment_PT extends PersonalInstalment_SG {

	private static final double MULTIPLY_FEE_TAX = 1.04;
	private static final double MULTIPLY_INSURANCE = 0.0075;
	private static final double TAX_LESS_THAN_TWELVE = 0.00105;
	private static final double TAX_BETWEEN_TWELVE_AND_SIXTY = 0.0135;
	private static final double TAX_GREATER_SIXTY = 0.015;
	
	protected static final long serialVersionUID = 1L;

	public PersonalInstalment_PT(){
		
		super();
	}
	
	public PersonalInstalment_PT(PersonalLoan obj){
		
		super(obj);
	}
	
	public PersonalInstalment_PT(PLAPIJsonObject obj){
		
		super(obj);
		if(obj.getAttributes() instanceof Attributes_PT){
			Attributes_PT attrs = (Attributes_PT)obj.getAttributes();
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
	
	public static Function<PLJsonObject, PersonalLoan> getPersonalLoansFromJsonObjects(final PersonalLoanConfig instance) {

		return new Function<PLJsonObject, PersonalLoan>() {

			@Override
			public PersonalInstalment_PT apply(final PLJsonObject obj) {

				PersonalLoan loan = PersonalLoan.getPersonalLoansFromJsonObject(instance, obj);
				if (loan != null) {
					PersonalInstalment_PT result = new PersonalInstalment_PT(loan);

					//Monthly Repayment
					double annualInterestRate = result.getInterest().getInterestValue();
					double monthlyRate = annualInterestRate / 12.0;

					double totalStartUpFee = 0.0;
					boolean hasInstalmentMonthlyFee = false;
					double instalmentMonthlyFee = 0.0;
					if (obj.getFee() != null){
						if(obj.getFee().getStartUpFee() > 0.0) {
							totalStartUpFee += obj.getFee().getStartUpFee() * MULTIPLY_FEE_TAX;	
						}
						
						double startUpFeePercent = MathUtils.extractDouble(obj.getFee().getStartUpFeePercent());
						startUpFeePercent = startUpFeePercent > 0.0 ? startUpFeePercent : 0.0;  
						double minStartUpFee = MathUtils.extractDouble(obj.getFee().getMinStartUpFee());
						minStartUpFee = minStartUpFee > 0.0 ? minStartUpFee : 0.0;
						double maxStartUpFee = obj.getFee().getMaxStartUpFee();
						maxStartUpFee = maxStartUpFee > 0.0 ? maxStartUpFee : 0.0;
						
						totalStartUpFee += Math.min(maxStartUpFee, Math.max(minStartUpFee, startUpFeePercent * instance.getLoanAmount()/100.0))*MULTIPLY_FEE_TAX;
						hasInstalmentMonthlyFee = obj.getFee().isHasInstallmentMonthlyFee();
						instalmentMonthlyFee = MathUtils.extractDouble(obj.getFee().getInstallmentMonthlyFee());
					}

					double taxOnCredit = 0.0;
					if(instance.getLoanTenure_Month() < 12){
						taxOnCredit = instance.getLoanAmount() * instance.getLoanTenure_Month() * TAX_LESS_THAN_TWELVE;
					}else if (instance.getLoanTenure_Month() > 60){
						taxOnCredit = instance.getLoanAmount() * TAX_GREATER_SIXTY;
					}else{
						taxOnCredit = instance.getLoanAmount() * TAX_BETWEEN_TWELVE_AND_SIXTY;
					}
					
					double insuranceAmount = obj.getFee().isRequiresLifeInsurance() ? instance.getLoanAmount() * instance.getLoanTenure_Month() * MULTIPLY_INSURANCE/12.0 : 0.0;
					
					double adjustedLoanAmount = instance.getLoanAmount() + totalStartUpFee + taxOnCredit + insuranceAmount;
					
					double monthlyRepayment = FinanceLib.pmt(monthlyRate * MULTIPLY_FEE_TAX/100.0, instance.getLoanTenure_Month(), -adjustedLoanAmount, 0, false);
					double totalInstalmentMonthlyFee = hasInstalmentMonthlyFee && instalmentMonthlyFee > 0.0 ? instalmentMonthlyFee * MULTIPLY_FEE_TAX: 0.0;
					monthlyRepayment += totalInstalmentMonthlyFee;
					result.setMonthlyRepayment(MathUtils.round(monthlyRepayment, 0));

					//Total Repayment
					double totalRepayment = monthlyRepayment * instance.getLoanTenure_Month();
					result.setTotalRepayment(MathUtils.round(totalRepayment, 0));
					
					//APR
					double rate = MathUtils.calculateRate( instance.getLoanAmount(), instance.getLoanTenure_Month(), monthlyRepayment);
					double apr = (Math.pow( 1 + rate, 12) - 1) * 100.0;
					result.setApr(MathUtils.round(apr, 1));
					return result;

				} else {
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
