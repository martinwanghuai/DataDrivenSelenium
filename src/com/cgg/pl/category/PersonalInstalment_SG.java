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

public class PersonalInstalment_SG extends PersonalLoan{

	protected static final long serialVersionUID = 1L;
	
	public PersonalInstalment_SG(){
		
		super();
	}
	
	public PersonalInstalment_SG(boolean isSponsoredProduct, double totalRepayment, double APR, double monthlyRepayment){
		
		super(isSponsoredProduct, totalRepayment, APR, monthlyRepayment);
	}
	
	public PersonalInstalment_SG(PersonalLoan obj){
		
		super(obj);
	}
	
	public PersonalInstalment_SG(PLAPIJsonObject obj){
		
		super(obj);
		if(obj.getAttributes() instanceof Attributes_SG_PersonalInstalment){
			Attributes_SG_PersonalInstalment attrs = (Attributes_SG_PersonalInstalment)obj.getAttributes();
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
		
		List<PersonalInstalment_SG> results = FluentIterable.from(objs).transform(new Function<PersonalLoan, PersonalInstalment_SG>(){
			
			@Override
			public PersonalInstalment_SG apply(final PersonalLoan obj){
				
				PersonalInstalment_SG result = new PersonalInstalment_SG(obj);
				return result;
			}
		}).toList();
		
		return (List<PersonalLoan>)(List<?>)results;
		
	}
	
	public static Function<PLJsonObject, PersonalLoan> getPersonalLoansFromJsonObjects(final PersonalLoanConfig instance){
		
		return new Function<PLJsonObject, PersonalLoan>(){
			
			@Override
			public PersonalInstalment_SG apply(final PLJsonObject obj){
			
				PersonalLoan loan = PersonalLoan.getPersonalLoansFromJsonObject(instance, obj);
				if(loan != null){
					PersonalInstalment_SG result = new PersonalInstalment_SG(loan);
					return result;					
				}else{
					return null;
				}
			}
		};
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
	public boolean sameSpecificValues(Object obj) {
		
		boolean result = true;
		PersonalInstalment_SG other = (PersonalInstalment_SG) obj;
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

	
	@Override
	public String toString() {
		
		return this.getClass().getSimpleName() + " [ID="+ ID + ",productName=" + productName + ", isSponsoredProduct=" + isSponsoredProduct + ", APR=" + MathUtils.round(apr, 2) + ", totalRepayment="
				+ MathUtils.round(totalRepayment,0) + ", monthlyRepayment=" + MathUtils.round(monthlyRepayment,2) + "]";
	}
}
