package toy;


import org.apache.poi.ss.formula.functions.FinanceLib;
import org.testng.annotations.Test;

import utility.MathUtils;

public class APRTester {

	@Test
	public void test1(){
		
		this.personalInstalment_HK_MonthlyRepayment(0.38, 29999, 24, 0);
		
	}
	
	public double personalInstalment_HK_MonthlyRepayment(double monthlyRate, double amount, double tenure, double annualHandlingFee){
		
		final double monthlyPayment = (amount
				+ amount * tenure
						* (monthlyRate / 100 + annualHandlingFee / 1200))
				/ tenure;
		System.out.println(monthlyPayment);
		return monthlyPayment;
	}
	
	@Test
	public void test3(){

		this.personalInstalment_HK_APR(0.38, 29999, 24, 0.0);
	}
	
	public void personalInstalment_HK_APR(double monthlyInterestRate, double amount, double tenure, double fixedHandlingFee){

		double APR = 0.0;
		double monthlyRepayment = this.personalInstalment_HK_MonthlyRepayment(monthlyInterestRate, amount, tenure, fixedHandlingFee);;
		double rate = MathUtils.calculateIRR(amount, tenure, monthlyRepayment);
		APR = (Math.pow(1 + rate, 12) - 1) * 100;
		System.out.println(APR);
	}
	
	
	public void lendingCompany_HK_APR(double monthlyInterestRate, double amount, double tenure, double fixedHandlingFee){

		double APR = 0.0;
		double monthlyRepayment = FinanceLib.pmt(monthlyInterestRate/100.0, tenure, -(amount + amount * fixedHandlingFee/100), 0, false);
//		double rate = MathUtils.calculateRate(amount, tenure, monthlyRepayment);
		double rate = MathUtils.calculateIRR(amount, tenure, monthlyRepayment);
		APR = (Math.pow(1 + rate, 12) - 1) * 100;
		System.out.println(APR);
	}
	
	@Test
	public void test2(){
		
		this.lendingCompany_HK_APR(0.38, 29999, 84, 0);
//		this.lendingCompany_HK_APR(0.38, 29999, 24, 1.0);
	}
	
}
