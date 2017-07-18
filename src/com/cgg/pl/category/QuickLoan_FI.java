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

public class QuickLoan_FI extends TermLoan_FI {

	protected static final long serialVersionUID = 1L;
	
	protected double monthlyRepaymentMin;
	
	public QuickLoan_FI(){
		
		super();
	}
	
	public QuickLoan_FI(PersonalLoan obj){
		
		super(obj);
	}
	
	public QuickLoan_FI(PLAPIJsonObject obj){
		
		super(obj);
		if(obj.getAttributes() instanceof Attributes_FI){
			Attributes_FI attrs = (Attributes_FI)obj.getAttributes();
			if(attrs.getMonthlyRepaymentMin() != null){
				this.monthlyRepaymentMin = attrs.getMonthlyRepaymentMin();
			}
		}
	}

	public static List<PersonalLoan> getPersonalLoansFromUI(WebDriverUtils driver, By by){
		
		List<PersonalLoan> objs = PersonalLoan.getPersonalLoansFromUI(driver, by);
		
		List<QuickLoan_FI> results = FluentIterable.from(objs).transform(new Function<PersonalLoan, QuickLoan_FI>(){
			
			@Override
			public QuickLoan_FI apply(final PersonalLoan obj){
				
				QuickLoan_FI result = new QuickLoan_FI(obj);
				result.setMinMonthlyRepayment(obj.getApr());
				result.setMonthlyRepayment(obj.getTotalRepayment());
				result.setTotalRepayment(obj.getTotalRepayment());
				return result;
			}
		}).toList();
		
		return (List<PersonalLoan>)(List<?>)results;
		
	}
	
	public static Function<PLJsonObject, PersonalLoan> getPersonalLoansFromJsonObjects(final PersonalLoanConfig instance){
		
		return new Function<PLJsonObject, PersonalLoan>(){
			
			@Override
			public QuickLoan_FI apply(final PLJsonObject obj){
			
				PersonalLoan loan = PersonalLoan.getPersonalLoansFromJsonObject(instance, obj);
				if(loan != null){
					QuickLoan_FI result = new QuickLoan_FI(loan);
					
					//min. monthly repayment
					if(obj.getFee() != null){
						result.setMinMonthlyRepayment(MathUtils.round(obj.getFee().getMinMonthlyRepayment(), 2));	
					}

					if(result.getInterest() != null){
						//monthly repayment
						if(instance.getLoanTenure_Month() > 30){
							result.setMonthlyRepayment(MathUtils.extractDouble(result.getInterest().getQuickLoanPayment()));	
						}else{
							result.setMonthlyRepayment(MathUtils.extractDouble(result.getInterest().getQuickLoanTotal()));
						}
						//total repayment
						result.setTotalRepayment(MathUtils.extractDouble(result.getInterest().getQuickLoanTotal()));
						if(result.getTotalRepayment() <= 0){
							return null;
						}
					}
					
					return result;
				}else{
					return null;
				}
			}
		};
	}

	public double getMinMonthlyRepayment() {
		return monthlyRepaymentMin;
	}

	public void setMinMonthlyRepayment(double minMonthlyRepayment) {
		this.monthlyRepaymentMin = minMonthlyRepayment;
	}
	
	public <T extends PersonalLoan> void sortPersonalLoans(List<T> loanList, final SortingKeyword sortKey){
		
		sortPersonalLoans(loanList, getSortingKeywords(sortKey));
	}

	public List<SortingKeyword> getSortingKeywords(final SortingKeyword sortKey) {
		List<SortingKeyword> sortKeys = null;
		switch(sortKey){
		case MinMonthlyRepayment:
			sortKeys = Lists.newArrayList(SortingKeyword.MinMonthlyRepayment, SortingKeyword.TotalRepayment, SortingKeyword.MonthlyRepayment);
			break;
		case MonthlyRepayment:
			sortKeys = Lists.newArrayList(SortingKeyword.MonthlyRepayment, SortingKeyword.TotalRepayment, SortingKeyword.MinMonthlyRepayment);
			break;
		case TotalRepayment:
			sortKeys = Lists.newArrayList(SortingKeyword.TotalRepayment, SortingKeyword.MonthlyRepayment, SortingKeyword.MinMonthlyRepayment);
			break;
		}
		return sortKeys;
	}
	
	@Override
	public boolean sameSpecificValues(Object obj) {
		
		boolean result = true;
		QuickLoan_FI other = (QuickLoan_FI) obj;
		if (!MathUtils.isWithinRange(monthlyRepaymentMin, other.monthlyRepaymentMin)) {
			String msg = "min. monthly repayment mismatch" + "\nJSON - min. monthly repayment: " + monthlyRepaymentMin + "\nUI - min. monthly repayment: " + other.monthlyRepaymentMin + "\n"; 
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
			String msg = "total repayment mismatch" + "\nJSON - total repayment: " + totalRepayment + "\nUI - total repayment: " + other.totalRepayment + "\n"; 
			System.out.println(msg);
			CommonFindObjects.loggerMethodFailure(msg);
			result = false;
		}
		return result;
	}
	
	@Override
	public String toString() {

		return this.getClass().getSimpleName() + " [ID=" + ID + ",productName=" + productName + ", isSponsoredProduct="
				+ isSponsoredProduct + ", minMonthlyRepayment=" + MathUtils.round(monthlyRepaymentMin, 2) + ", TotalRepayment=" + MathUtils.round(totalRepayment,2) + ", monthlyRepayment="
				+ MathUtils.round(monthlyRepayment,0) + "]";
	}
}
