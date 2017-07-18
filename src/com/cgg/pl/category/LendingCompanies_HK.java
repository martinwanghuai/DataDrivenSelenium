package com.cgg.pl.category;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.formula.functions.FinanceLib;
import org.openqa.selenium.By;

import com.cgg.model.PLAPIJsonObject;
import com.cgg.model.PLJsonObject;
import com.cgg.model.PersonalLoanConfig;
import com.cgg.model.PLJsonObject.MonthlyCashRebate;
import com.cgg.model.PLJsonObject.MonthlyRebate;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;

import pageObjects.CommonFindObjects;
import utility.MathUtils;
import utility.WebDriverUtils;

public class LendingCompanies_HK extends PersonalInstalment_HK {
	
	protected static final long serialVersionUID = 1L;
	
	public LendingCompanies_HK(){
		
		super();
	}
	
	public LendingCompanies_HK(PersonalLoan obj){
		
		super(obj);
	}
	
	public LendingCompanies_HK(PersonalInstalment_HK obj){
		
		super(obj);
	}
	
	public LendingCompanies_HK(PLAPIJsonObject obj){
		
		super(obj);
		if(obj.getAttributes() instanceof Attributes_HK){
			Attributes_HK attrs = ((Attributes_HK)obj.getAttributes());
			if(attrs.getMonthlyRate() != null){
				this.annualInterestRate = attrs.getMonthlyRate();
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
		List<LendingCompanies_HK> results = FluentIterable.from(objs).transform(new Function<PersonalLoan, LendingCompanies_HK>(){
			
			@Override
			public LendingCompanies_HK apply(final PersonalLoan obj){
				
				LendingCompanies_HK result = new LendingCompanies_HK(obj);
				result.setInterestRate(obj.getApr());
				return result;
			}
		}).toList();
		
		return (List<PersonalLoan>)(List<?>)results;
	}
	
	@Override
	public boolean sameSpecificValues(Object obj) {
		
		LendingCompanies_HK other = (LendingCompanies_HK) obj;
		if (!MathUtils.isWithinRange(interestRate, other.interestRate)) {
			String msg = "interest rate mismatch" + "\nJSON - rate: " + interestRate + "\nUI - rate: " + other.interestRate + "\n"; 
			System.out.println(msg);
			CommonFindObjects.loggerMethodFailure(msg);
			return false;
		}
		if (!MathUtils.isWithinRange(monthlyRepayment, other.monthlyRepayment)) {
			String msg = "monthly repayment mismatch" + "\nJSON - repayment: " + monthlyRepayment + "\nUI - repayment: " + other.monthlyRepayment + "\n"; 
			System.out.println(msg);
			CommonFindObjects.loggerMethodFailure(msg);
			return false;
		}
		if (!MathUtils.isWithinRange(totalRepayment, other.totalRepayment)) {
			String msg = "total repayment mismatch" + "\nJSON - repayment: " + totalRepayment + "\nUI - repayment: " + other.totalRepayment + "\n"; 
			System.out.println(msg);
			CommonFindObjects.loggerMethodFailure(msg);
			return false;
		}
		return true;
	}

	public static Function<PLJsonObject, PersonalLoan> getPersonalLoansFromJsonObjects(final PersonalLoanConfig instance){
		
		return new Function<PLJsonObject, PersonalLoan>(){
			
			@Override
			public LendingCompanies_HK apply(final PLJsonObject obj){
			
				LendingCompanies_HK result = new LendingCompanies_HK(PLJsonObject.convertPLJsonObjectIntoPersonalInstalmentHK(instance, obj));
				
				double monthlyRepayment = FinanceLib.pmt(result.getMonthlyInterestRate()/100.0, instance.getLoanTenure_Month(), -instance.getLoanAmount(), 0, false);
				result.setMonthlyRepayment(monthlyRepayment);
				
				double totalRepayment = monthlyRepayment * instance.getLoanTenure_Month();
				result.setTotalRepayment(totalRepayment);
				
				double apr = 0.0;
				double totalCashRebates = 0.0;
				if(obj.isCalculateApr()){
					double rate = 0.0;
					if(obj.getMonthlyRebates() == null || obj.getMonthlyRebates().size() == 0){
						rate = MathUtils.calculateIRR(instance.getLoanAmount(), instance.getLoanTenure_Month(), monthlyRepayment);	
					}else{

						List<MonthlyRebate> monthlyRebates = obj.getMonthlyRebates().stream()
								.filter(m -> m.getLoanAmountMin() <= instance.getLoanAmount()
										&& m.getLoanTenureMin() <= instance.getLoanTenure_Month())
								.sorted(Comparator.comparing(MonthlyRebate::getLoanAmountMin)
										.thenComparing(MonthlyRebate::getLoanTenureMin))
								.collect(Collectors.toList());
						
						if(monthlyRebates!= null && monthlyRebates.size() > 0){
							MonthlyRebate monthlyRebate = monthlyRebates.get(monthlyRebates.size() - 1);

							if (monthlyRebate != null) {
								double[] monthlyRepayments = new double[instance.getLoanTenure_Month()];
								Arrays.fill(monthlyRepayments, monthlyRepayment);
								for (MonthlyCashRebate monthlyCashRebate : monthlyRebate.getMonthlyCashRebates()) {
									monthlyRepayments[monthlyCashRebate.getMonthIndex() - 1] -= monthlyCashRebate
											.getCashRebate();
									totalCashRebates += monthlyCashRebate.getCashRebate();
								}
								rate = MathUtils.calculateIRR(instance.getLoanAmount(), instance.getLoanTenure_Month(),
										monthlyRepayments);
							} else {
								rate = MathUtils.calculateIRR(instance.getLoanAmount(), instance.getLoanTenure_Month(),
										monthlyRepayment);
							}
						}
					}
					apr = (Math.pow(1 + rate, 12) - 1) * 100;	
				}else{
					apr = result.getInterest().getApr();
				}
				
				result.setApr(MathUtils.round(apr, 4));
				result.setTotalRepayment(result.getTotalRepayment() - totalCashRebates);
				return result;
			}
		};
	}
	
	@Override
	public String toString(){
		
		return super.toString();
	}
}
