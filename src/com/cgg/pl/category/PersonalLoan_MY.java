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

public class PersonalLoan_MY extends BankLoan_MX {

	protected static final long serialVersionUID = 1L;
	protected double minIncome;
	protected double annualRate;
	
	public PersonalLoan_MY(){
		
		super();
	}

	public PersonalLoan_MY(PersonalLoan obj){
		
		super(obj);
	}
	
	public PersonalLoan_MY(PLAPIJsonObject obj){
		
		super(obj);
		if(obj.getAttributes() instanceof Attributes_MY){
			Attributes_MY attrs = (Attributes_MY)obj.getAttributes();
			if(attrs.getMinIncome() != null){
				this.minIncome = attrs.getMinIncome();	
			}
			if(attrs.getAnnualRate() != null){
				this.annualRate = attrs.getAnnualRate();	
			}
			if(attrs.getMonthlyRepayment() != null){
				this.monthlyRepayment = attrs.getMonthlyRepayment();	
			}
		}
	}
	
	@Override
	public boolean sameSpecificValues(Object obj) {
		
		boolean result = true;
		PersonalLoan_MY other = (PersonalLoan_MY) obj;
		if (!MathUtils.isWithinRange(minIncome, other.minIncome)) {
			String msg = "min. income mismatch" + "\nJSON - min. income: " + minIncome + "\nUI - income: " + other.minIncome + "\n"; 
			System.out.println(msg);
			CommonFindObjects.loggerMethodFailure(msg);
			result = false;
		}
		if (!MathUtils.isWithinRange(annualRate, other.annualRate)) {
			String msg = "interest rate mismatch" + "\nJSON - rate: " + annualRate + "\nUI - rate: " + other.annualRate + "\n"; 
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
	
	@Override
	public String toString() {
		
		return this.getClass().getSimpleName() + " [ID=" + ID + ",productName=" + productName + ", isSponsoredProduct="
				+ isSponsoredProduct + ", minIncome=" + MathUtils.round(minIncome, 0) + ", annualRate="
				+ MathUtils.round(annualRate, 3) + ", monthlyRepayment=" + MathUtils.round(monthlyRepayment, 0) + "]";
	}
	
	public List<SortingKeyword> getSortingKeywords(final SortingKeyword sortKey) {
		List<SortingKeyword> sortKeys = null;
		switch(sortKey){
		case MinIncome:
			sortKeys = Lists.newArrayList(SortingKeyword.MinIncome, SortingKeyword.MonthlyRepayment, SortingKeyword.AnnualRate);
			break;
		case AnnualRate:
			sortKeys = Lists.newArrayList(SortingKeyword.AnnualRate, SortingKeyword.MonthlyRepayment, SortingKeyword.MinIncome);
			break;
		case MonthlyRepayment:
			sortKeys = Lists.newArrayList(SortingKeyword.MonthlyRepayment, SortingKeyword.AnnualRate, SortingKeyword.MinIncome);
			break;
		}
		return sortKeys;
	}


	public double getMinIncome() {
		return minIncome;
	}


	public void setMinIncome(double minIncome) {
		this.minIncome = minIncome;
	}
	
	public static Function<PLJsonObject, PersonalLoan> getPersonalLoansFromJsonObjects(final PersonalLoanConfig instance){
		
		return new Function<PLJsonObject, PersonalLoan>(){
			
			@Override
			public PersonalLoan_MY apply(final PLJsonObject obj){
				
				PersonalLoan loan = PersonalLoan.getPersonalLoansFromJsonObject(instance, obj);
				if(loan != null){
					PersonalLoan_MY result = new PersonalLoan_MY(loan);
					//min. income
					/*if(result.getMinRequirements()!= null){
						for(MinimumRequirement req: result.getMinRequirements()){
							if(req.getRequirementType()!= null && req.getRequirementType().equals("minIncome")){
								result.setMinIncome(Double.parseDouble(req.getValue()));
								break;
							}
						}	
					}*/
					if(result.getEligibilityDescs()!= null){
						for(EligibilityFeature eligibility: result.getEligibilityDescs()){
							if(eligibility.getKey().equals("MIN_MONTHLY_INCOME")){
								result.setMinIncome(Double.parseDouble(eligibility.getDescription()));
								break;
							}
						}
					}
					
					

					//annual rate
					result.setAnnualRate(MathUtils.round(result.getInterest().getInterestValue() * 12, 2));
					
					//Monthly repayment
					double monthlyRepayment = ((double)instance.getLoanAmount()) / instance.getLoanTenure_Month() + (instance.getLoanAmount()/100.0) * result.getInterest().getInterestValue();
					result.setMonthlyRepayment(MathUtils.round(monthlyRepayment,2));

					return result;	
				}else{
					return null;
				}
			}
		};
	}

	public double getAnnualRate() {
		return annualRate;
	}

	public void setAnnualRate(double annualRate) {
		this.annualRate = annualRate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(annualRate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(minIncome);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
}
