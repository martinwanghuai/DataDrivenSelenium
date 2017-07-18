package com.cgg.pl.category;

import java.util.List;

import org.openqa.selenium.By;

import com.cgg.model.PLAPIJsonObject;
import com.cgg.model.PLJsonObject;
import com.cgg.model.PersonalLoanConfig;
import com.cgg.model.PLJsonObject.Locale.ResponseTime;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;

import pageObjects.CommonFindObjects;
import utility.Checker;
import utility.MathUtils;
import utility.WebDriverUtils;

public class QuickLoan_DK extends PersonalInstalment_SG {

	protected static final long serialVersionUID = 1L;
	protected double cost;
	protected String responseTime;
	
	public QuickLoan_DK(){
		
		super();
	}
	
	public QuickLoan_DK(PersonalLoan obj){
		
		super(obj);
	}
	
	public QuickLoan_DK(PLAPIJsonObject obj){
		
		super(obj);
		if(obj.getAttributes() instanceof Attributes_DK){
			Attributes_DK attrs = (Attributes_DK)obj.getAttributes();
			if(attrs.getTotalRepayment() != null){
				this.cost = attrs.getTotalRepayment();	
			}
			if(attrs.getResponseTime() != null){
				this.responseTime = attrs.getResponseTime();	
			}
		}
	}

	public static List<PersonalLoan> getPersonalLoansFromUI(WebDriverUtils driver, By by){
		
		List<PersonalLoan> objs = PersonalLoan.getPersonalLoansFromUI(driver, by);
		
		List<QuickLoan_DK> results = FluentIterable.from(objs).transform(new Function<PersonalLoan, QuickLoan_DK>(){
			
			@Override
			public QuickLoan_DK apply(final PersonalLoan obj){
				
				QuickLoan_DK result = new QuickLoan_DK(obj);
				result.setCost(obj.getApr());
				result.setResponseTime(obj.getTotalRepayment() + "");
				result.setTotalRepayment(obj.getMonthlyRepayment());
				return result;
			}
		}).toList();
		
		return (List<PersonalLoan>)(List<?>)results;
		
	}
	
	public static Function<PLJsonObject, PersonalLoan> getPersonalLoansFromJsonObjects(final PersonalLoanConfig instance){
		
		return new Function<PLJsonObject, PersonalLoan>(){
			
			@Override
			public QuickLoan_DK apply(final PLJsonObject obj){
			
				PersonalLoan loan = PersonalLoan.getPersonalLoansFromJsonObject(instance, obj);
				if(loan != null){
					QuickLoan_DK result = new QuickLoan_DK(loan);
					
					result.setTotalRepayment(Double.parseDouble(result.getInterest().getQuickLoanPayment()));
					result.setCost(MathUtils.isLargerThan(result.getTotalRepayment() - instance.getLoanAmount(), 0.0) ? result.getTotalRepayment() - instance.getLoanAmount() : 0.0);

					ResponseTime time = result.getLocale().getResponseTime();
					if(!Checker.isBlank(time.getMinutes())){
						result.setResponseTime(time.getMinutes());	
					}else if(!Checker.isBlank(time.getHours())){
						result.setResponseTime(time.getHours());
					}else if(!Checker.isBlank(time.getDays())){
						result.setResponseTime(time.getDays());
					}
					
					return result;
	
				}else{
					return null;
				}
			}
		};
	}
	
	@Override
	public boolean sameSpecificValues(Object obj) {
		
		boolean result = true;
		QuickLoan_DK other = (QuickLoan_DK) obj;
		if (!MathUtils.isWithinRange(cost, other.getCost())) {
			String msg = "cost mismatch" + "\nJSON - cost: " + cost + "\nUI - cost: " + other.cost + "\n"; 
			System.out.println(msg);
			CommonFindObjects.loggerMethodFailure(msg);
			result = false;
		}
		if (responseTime.equalsIgnoreCase(other.getResponseTime())) {
			String msg = "response time mismatch" + "\nJSON - response time: " + responseTime + "\nUI - response time: " + other.responseTime + "\n"; 
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
	
	public <T extends PersonalLoan> void sortPersonalLoans(List<T> loanList, final SortingKeyword sortKey){
		
		sortPersonalLoans(loanList, getSortingKeywords(sortKey));
	}

	public List<SortingKeyword> getSortingKeywords(final SortingKeyword sortKey) {
		List<SortingKeyword> sortKeys = null;
		switch(sortKey){
		case Cost:
			sortKeys = Lists.newArrayList(SortingKeyword.Cost, SortingKeyword.TotalRepayment, SortingKeyword.ResponseTime);
			break;
		case ResponseTime:
			sortKeys = Lists.newArrayList(SortingKeyword.ResponseTime, SortingKeyword.TotalRepayment, SortingKeyword.Cost);
			break;
		case TotalRepayment:
			sortKeys = Lists.newArrayList(SortingKeyword.TotalRepayment, SortingKeyword.ResponseTime, SortingKeyword.Cost);
			break;
		}
		return sortKeys;
	}
	
	
	@Override
	public String toString() {
		
		return this.getClass().getSimpleName() + " [ID=" + ID + ",productName=" + productName + ", isSponsoredProduct="
				+ isSponsoredProduct + ", cost=" + cost + ", response time=" + responseTime + ", totalRepayment="
				+ totalRepayment + "]";
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public String getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}
}
