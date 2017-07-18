package com.cgg.pl.category;

import java.util.List;

import org.openqa.selenium.By;

import com.cgg.model.PLAPIJsonObject;
import com.cgg.model.PLJsonObject;
import com.cgg.model.PersonalLoanConfig;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;

import pageObjects.CommonFindObjects;
import utility.MathUtils;
import utility.WebDriverUtils;

public class PersonalInstalment_HK extends PersonalInstalment_SG {

	protected static final long serialVersionUID = 1L;
	
	protected double interestRate;
	
	public PersonalInstalment_HK(){
		
		super();
	}
	
	public PersonalInstalment_HK(PersonalLoan obj){
		
		super(obj);
	}
	
	public PersonalInstalment_HK(PLAPIJsonObject obj){
		
		super(obj);
		if(obj.getAttributes() instanceof Attributes_HK){
			Attributes_HK attrs = (Attributes_HK)obj.getAttributes();
			if(attrs.getMonthlyRate() != null){
				this.interestRate = attrs.getMonthlyRate();
			}
			if(attrs.getTotalRepayment() != null){
				this.totalRepayment = attrs.getTotalRepayment();	
			}
			if(attrs.getMonthlyRepayment() != null){
				this.monthlyRepayment = attrs.getMonthlyRepayment();	
			}
			if(attrs.getApr() != null){
				this.apr = attrs.getApr();
			}
		}
	}
	
	
	public static List<PersonalLoan> getPersonalLoansFromUI(WebDriverUtils driver, By by){
		
		List<PersonalLoan> objs = PersonalLoan.getPersonalLoansFromUI(driver, by);
		List<PersonalInstalment_HK> results = FluentIterable.from(objs).transform(new Function<PersonalLoan, PersonalInstalment_HK>(){
			
			@Override
			public PersonalInstalment_HK apply(final PersonalLoan obj){
				
				PersonalInstalment_HK result = new PersonalInstalment_HK(obj);
				result.setInterestRate(obj.getApr());
//				result.setApr(apr);
				return result;
			}
		}).toList();
		
		return (List<PersonalLoan>)(List<?>)results;
	}

	public <T extends PersonalLoan> void sortPersonalLoans(List<T> loanList, final SortingKeyword sortKey){
		
		sortPersonalLoans(loanList, getSortingKeywords(sortKey));
	}

	public List<SortingKeyword> getSortingKeywords(final SortingKeyword sortKey) {
		List<SortingKeyword> sortKeys = null;
		switch(sortKey){
		case APR:
			sortKeys = Lists.newArrayList(SortingKeyword.APR, SortingKeyword.MonthlyRepayment, SortingKeyword.TotalRepayment);
			break;
		case TotalRepayment:
			sortKeys = Lists.newArrayList(SortingKeyword.TotalRepayment, SortingKeyword.MonthlyRepayment, SortingKeyword.APR);
			break;
		case MonthlyRepayment:
			sortKeys = Lists.newArrayList(SortingKeyword.MonthlyRepayment, SortingKeyword.TotalRepayment, SortingKeyword.APR);
			break;
		}
		return sortKeys;
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [ID=" + ID + ",productName=" + productName + ", isSponsoredProduct="
				+ isSponsoredProduct + ", apr=" + MathUtils.round(apr,3) + ", totalRepayment=" + MathUtils.round(totalRepayment,0)
				+ ", monthlyRepayment=" + MathUtils.round(monthlyRepayment,0) + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(apr);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean sameSpecificValues(Object obj) {
		
		boolean result = true;
		PersonalInstalment_HK other = (PersonalInstalment_HK) obj;
		if (!MathUtils.isWithinRange(apr, other.apr)) {
			String msg = "apr mismatch" + "\nJSON - apr: " + apr + "\nUI - apr: " + other.apr + "\n"; 
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
		if (!MathUtils.isWithinRange(totalRepayment, other.totalRepayment)) {
			String msg = "total repayment mismatch" + "\nJSON - repayment: " + totalRepayment + "\nUI - repayment: " + other.totalRepayment + "\n"; 
			System.out.println(msg);
			CommonFindObjects.loggerMethodFailure(msg);
			result = false;
		}
		
		return result;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public static Function<PLJsonObject, PersonalLoan> getPersonalLoansFromJsonObjects(final PersonalLoanConfig instance){
		
		return new Function<PLJsonObject, PersonalLoan>(){
			
			@Override
			public PersonalInstalment_HK apply(final PLJsonObject obj){
			
				return PLJsonObject.convertPLJsonObjectIntoPersonalInstalmentHK(instance, obj);
			}
		};
	}
}
