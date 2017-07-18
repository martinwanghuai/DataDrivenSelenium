package com.cgg.pl.category;

import java.util.List;

import com.cgg.model.PLAPIJsonObject;
import com.cgg.model.PLJsonObject;
import com.cgg.model.PersonalLoanConfig;
import com.cgg.model.PLJsonObject.Locale.EligibilityFeature;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

import pageObjects.CommonFindObjects;
import utility.MathUtils;

public class PersonalInstalment_ID extends PersonalLoan_MY {

		
	public PersonalInstalment_ID(){
		
		super();
	}

	public PersonalInstalment_ID(PersonalLoan obj){
		
		super(obj);
	}
	
	public PersonalInstalment_ID(PLAPIJsonObject obj){
		
		super(obj);
		if(obj.getAttributes() instanceof Attributes_ID){
			Attributes_ID attrs = (Attributes_ID)obj.getAttributes();
			
			if(attrs.getMonthlyRate() != null){
				this.interestRate = attrs.getMonthlyRate();
			}
			if(attrs.getMonthlyRepayment() != null){
				this.monthlyRepayment = attrs.getMonthlyRepayment();	
			}
			if(attrs.getTotalRepayment() != null){
				this.totalRepayment = attrs.getTotalRepayment();	
			}
		}
	}
	
	public static Function<PLJsonObject, PersonalLoan> getPersonalLoansFromJsonObjects(final PersonalLoanConfig instance){
		return new Function<PLJsonObject, PersonalLoan>(){
			
			@Override
			public PersonalInstalment_ID apply(final PLJsonObject obj){
				
				PersonalLoan loan = PersonalLoan.getPersonalLoansFromJsonObject(instance, obj);
				if(loan != null){
					PersonalInstalment_ID result = new PersonalInstalment_ID(loan);
					
					//interest rate
					double interestRate = result.getInterest().getInterestValue();
					result.setInterestRate(MathUtils.round(interestRate,2));
					
					double annualHandlingFee = instance.getLoanTenure_Month() > 12 ? 
							result.getAnnualHandlingFee() * (instance.getLoanTenure_Month() - 12) /12.0: 0.0;
					
					double totalFee = obj.getFee().getStartUpFee()
							+ Math.max(instance.getLoanAmount() * Double.parseDouble(obj.getFee().getStartUpFeePercent()) / 100.0,
									Double.parseDouble(obj.getFee().getMinStartUpFee()))
							+ annualHandlingFee;
							
					if(result.getEligibilityDescs()!= null){
						for(EligibilityFeature eligibility: result.getEligibilityDescs()){
							if(eligibility.getKey().equals("MIN_MONTHLY_INCOME")){
								result.setMinIncome(Double.parseDouble(eligibility.getDescription()));
								break;
							}
						}
					}
					
					//Monthly repayment
					double monthlyRepayment = ((double) instance.getLoanAmount()) / instance.getLoanTenure_Month()
							+ (instance.getLoanAmount() / 100.0) * result.getInterest().getInterestValue();
					result.setMonthlyRepayment(MathUtils.round(monthlyRepayment,0));
					
					//Total repayment
					double totalRepayment = monthlyRepayment * instance.getLoanTenure_Month() + totalFee;
					result.setTotalRepayment(MathUtils.round(totalRepayment, 0));

					return result;	
				}else{
					return null;
				}
			}
		};
	}
	
	@Override
	public String toString() {
		
		return this.getClass().getSimpleName() + " [ID=" + ID + ",productName=" + productName + ", isSponsoredProduct="
				+ isSponsoredProduct + ", interestRate=" + MathUtils.round(interestRate, 2) + ", totalRepayment="
				+ MathUtils.round(totalRepayment, 3) + ", monthlyRepayment=" + MathUtils.round(monthlyRepayment, 0) + "]";
	}
	
	public List<SortingKeyword> getSortingKeywords(final SortingKeyword sortKey) {
		List<SortingKeyword> sortKeys = null;
		switch(sortKey){
		case InterestRate:
			sortKeys = Lists.newArrayList(SortingKeyword.InterestRate, SortingKeyword.MonthlyRepayment, SortingKeyword.TotalRepayment);
			break;
		case TotalRepayment:
			sortKeys = Lists.newArrayList(SortingKeyword.TotalRepayment, SortingKeyword.MonthlyRepayment, SortingKeyword.InterestRate);
			break;
		case MonthlyRepayment:
			sortKeys = Lists.newArrayList(SortingKeyword.MonthlyRepayment, SortingKeyword.TotalRepayment, SortingKeyword.InterestRate);
			break;
		}
		return sortKeys;
	}
	
	@Override
	public boolean sameSpecificValues(Object obj) {
		
		boolean result = true;
		PersonalLoan_MY other = (PersonalLoan_MY) obj;
		if (!MathUtils.isWithinRange(interestRate, other.interestRate)) {
			String msg = "interest rate mismatch" + "\nJSON - interest rate: " + interestRate + "\nUI - interest rate: " + other.interestRate + "\n"; 
			System.out.println(msg);
			CommonFindObjects.loggerMethodFailure(msg);
			result = false;
		}
		if (!MathUtils.isWithinRange(totalRepayment, other.totalRepayment)) {
			String msg = "total repayment mismatch" + "\nJSON - total repayment: " + totalRepayment + "\nUI - total repayment: " + other.totalRepayment+ "\n"; 
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
		
		return result;
	}
}
