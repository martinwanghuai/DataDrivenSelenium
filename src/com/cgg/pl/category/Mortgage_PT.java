package com.cgg.pl.category;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.formula.functions.FinanceLib;
import org.testng.collections.Lists;

import com.cgg.model.MortgageAPIJsonObjects;
import com.cgg.model.PLJsonObject;
import com.cgg.model.PersonalLoanConfig;
import com.cgg.model.MortgageJsonObject.Mortgage;
import com.cgg.model.MortgageJsonObject.Mortgage.EuriborInterestRate;
import com.cgg.model.MortgageJsonObject.Mortgage.HealthInsurance;
import com.cgg.model.MortgageJsonObject.Mortgage.MultiRiskInsurance;
import com.cgg.model.MortgageJsonObject.Mortgage.Product;
import com.cgg.model.PLJsonObject.StartUpFee;
import com.google.common.base.Function;

import pageObjects.CommonFindObjects;
import utility.MathUtils;

public class Mortgage_PT extends PersonalLoan {

	public Mortgage_PT(){
		
		super();
	}
	
	public Mortgage_PT(PersonalLoan obj){
		
		super(obj);
	}
	
	public Mortgage_PT(MortgageAPIJsonObjects.Product obj){
		
		super(obj);
		if(obj.getAttributes() != null ){
			MortgageAPIJsonObjects.Product.Attributes attrs = obj.getAttributes();
			if(attrs.getApr() != null){
				this.setApr(attrs.getApr());		
			}
			if(attrs.getTotalRepayment() != null){
				this.setTotalRepayment(attrs.getTotalRepayment());	
			}
			if(attrs.getMonthlyRepayment() != null){
				this.setMonthlyRepayment(attrs.getMonthlyRepayment());
			}	
		}
	}
	
	public <T extends PersonalLoan> void sortPersonalLoans(List<T> loanList, final SortingKeyword sortKey){
		
		sortPersonalLoans(loanList, getSortingKeywords(sortKey));
	}

	public List<SortingKeyword> getSortingKeywords(final SortingKeyword sortKey) {
		
		List<SortingKeyword> sortKeys = Lists.newArrayList();
		switch(sortKey){
		case MonthlyRepayment:
			sortKeys = Lists.newArrayList(SortingKeyword.MonthlyRepayment, SortingKeyword.TotalRepayment, SortingKeyword.AnnualRate);
			break;
		case TotalRepayment:
			sortKeys = Lists.newArrayList(SortingKeyword.TotalRepayment, SortingKeyword.MonthlyRepayment, SortingKeyword.AnnualRate);
			break;
		case AnnualRate:
			sortKeys = Lists.newArrayList(SortingKeyword.AnnualRate, SortingKeyword.MonthlyRepayment, SortingKeyword.TotalRepayment);
			break;
		}
		return sortKeys;
	}

	
	public static Function<Product, PersonalLoan> getPersonalLoansFromJsonObjects(final PersonalLoanConfig instance, final Mortgage mortgage){
		
		return new Function<Product, PersonalLoan>(){
			
			@Override
			public Mortgage_PT apply(final Product obj){
			
				Mortgage_PT result = new Mortgage_PT(PLJsonObject.convertPLJsonObjectIntoPersonalInstalmentHK(instance, (PLJsonObject)obj));
	
				List<Double> initialComissions = Lists.newArrayList();
				for(StartUpFee fee: obj.getFee().getStartUpFees()){
					initialComissions.add(new Double(fee.value));
				}

				for(EuriborInterestRate temp: mortgage.euriborInterestRates){
					if(temp.indexMonth == obj.getEuriborIndexMonth()){
						obj.setEuribor(temp.value);
						break;
					}
				}
				
				for(HealthInsurance insurance: mortgage.healthInsurances){
					if(MathUtils.isNotLessThan(instance.getLoanAmount(), insurance.getLoanAmountMin())
							&& MathUtils.isNotLessThan(insurance.getLoanAmountMax(), instance.getLoanAmount())
							&& MathUtils.isNotLessThan(instance.getLoanTenure_Year(), insurance.getLoanTenureMin())
							&& MathUtils.isNotLessThan(insurance.getLoanTenureMax(), instance.getLoanTenure_Year())){
						
						obj.setHealthInsurance(insurance.value);
						break;
					}
				}
				
				List<MultiRiskInsurance> insurances = Lists.newArrayList(mortgage.multiRiskInsurances).stream().sorted((o1, o2) -> o1.propertyValue >= o2.propertyValue ? 1: -1).collect(Collectors.toList());
				
				for(int i = 0; i < insurances.size() - 1; i ++){
					if(instance.getLoanAmount() > insurances.get(i).propertyValue && instance.getLoanAmount() <= insurances.get(i+1).propertyValue){
						obj.setMultiRiskInsuranceValue(insurances.get(i).value);
						break;
					}
				}
				
				//monthly repayment
				double monthlyRepayment = calculateMonthlyRepayment(instance, obj, result);
				result.setMonthlyRepayment(monthlyRepayment);
				
				//apr
				double apr = calculateAPR(instance, obj, monthlyRepayment, initialComissions);
				result.setApr(apr);
				
				//total repayment
				double totalRepayment = calculateTotalRepayment(monthlyRepayment, instance, initialComissions);
				result.setTotalRepayment(totalRepayment);
				
				return result;
			}
		};
	}
	
	public static double calculateMonthlyRepayment(final PersonalLoanConfig config, final Product product, final Mortgage_PT result){
		
		double spread = result.getInterest().getFixedRate()/100.0;
		double loanTenure_Month = config.getLoanTenure_Year() * 12;
		double loanAmount = config.getLoanAmount() * config.getBorrowPercentage() / 100.0;
		double monthCommission = product.getFee().getMonthlyFee() * 1.04;
		double euribor = product.getEuribor();
		double healthInsurance = product.getHealthInsurance();
		double multiRiskInsurance = product.getMultiRiskInsuranceValue();
		
		return calculateMonthlyRepayment(euribor, spread, loanTenure_Month, loanAmount, monthCommission, healthInsurance, multiRiskInsurance); 
	}
	
	public static double calculateAPR(final PersonalLoanConfig config, final Product product , double monthlyRepayment, List<Double> initialCommissions){
	
		double loanAmount = config.getLoanAmount()* config.getBorrowPercentage()/100.0;
		double loanTenure_Month = config.getLoanTenure_Year() * 12.0;
		
		return calculateAPR(loanAmount, loanTenure_Month, initialCommissions, monthlyRepayment);
	}
	
	public static double calculateMonthlyRepayment(double euribor, double spread, double loanTenure_Month, double loanAmount, 
			double monthCommission, double healthInsurance, double multiRiskInsurance){
		
		double index = euribor/100.0;
		double norminalInterestRate = index + spread;
		double monthRepaymentInitial = FinanceLib.pmt(norminalInterestRate/12.0, loanTenure_Month, -loanAmount, 0, false);
		double monthRepayment_total = monthRepaymentInitial + monthCommission + healthInsurance + multiRiskInsurance;
		
		return monthRepayment_total;
	}

	public static double calculateAPR(double loanAmount, double loanTenure_Month, List<Double> initialComissions, double monthlyRepayment){

		double realCredit = loanAmount - (initialComissions.stream().mapToDouble(i -> i.doubleValue()).sum());
		double taxa = MathUtils.calculateRate(realCredit, loanTenure_Month, monthlyRepayment) * 12;
		double apr = Math.pow(1 + taxa/12.0, 12) - 1;
		return apr;
	}
	
	public static double calculateTotalRepayment(double monthlyRepayment, PersonalLoanConfig instance, final List<Double> initialCommissions){
		
		double loanTenure_Month = instance.getLoanTenure_Year() * 12.0;
		double loanAmount = instance.getLoanAmount()*instance.getBorrowPercentage()/100.0;
		return calculateTotalRepayment(monthlyRepayment, loanTenure_Month, loanAmount, initialCommissions);
	}
	
	public static double calculateTotalRepayment(double monthlyRepayment, double loanTenure_Month, double loanAmount, List<Double> initialComissions){
	
		double taxOnCredit = calculateTaxOnCredit(loanTenure_Month, loanAmount);
		double totalComissions = (initialComissions.stream().mapToDouble(i -> i.doubleValue()).sum()) * 1.04;
		
		double repayments = monthlyRepayment * loanTenure_Month;
		double totalRepayment = repayments + taxOnCredit
				+ totalComissions;
		
		return totalRepayment;
	}
	
	public static double calculateTaxOnCredit(double loanTenure_Month, double loanAmount){
		
		double taxOnCredit = 0.0;
		if(loanTenure_Month < 12){
			taxOnCredit = 0.0004 * loanTenure_Month * loanAmount;
		}else if(loanTenure_Month > 60){
			taxOnCredit = 0.006 * loanAmount;
		}else {
			taxOnCredit = 0.005 * loanAmount;
		}
		return taxOnCredit;
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName() +"[ID=" + ID + ", productName=" + productName + ", isSponsoredProduct=" + isSponsoredProduct
				+ ",apr=" + MathUtils.round(apr,3) + ", totalRepayment=" + MathUtils.round(totalRepayment,2) 
				+ ", monthlyRepayment=" + MathUtils.round(monthlyRepayment,0) + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(apr);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(monthlyRepayment);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(totalRepayment);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean sameSpecificValues(Object obj) {

		Mortgage_PT other = (Mortgage_PT) obj;
		if (!MathUtils.isWithinRange(apr, other.apr)) {
			String msg = "apr mismatch" + "\nJSON - apr: " + apr + "\nUI - apr: " + other.apr + "\n"; 
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
		if (!MathUtils.isWithinRange(monthlyRepayment, other.monthlyRepayment)) {
			String msg = "monthly repayment mismatch" + "\nJSON - repayment: " + monthlyRepayment + "\nUI - repayment: " + other.monthlyRepayment + "\n"; 
			System.out.println(msg);
			CommonFindObjects.loggerMethodFailure(msg);
			return false;
		}
		return true;
	}
}
