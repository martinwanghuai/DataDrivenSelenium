package com.cgg.model;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.cgg.pl.category.BalanceTransfer_SG;
import com.cgg.pl.category.SortingKeyword;
import com.google.common.collect.Lists;

public class BalanceTransferTest {

	BalanceTransfer_SG instance = null;
	
	@BeforeTest
	public void setup(){
		instance = new BalanceTransfer_SG();
	}
	
	@Test
	public void shouldSortBySponsoredProduct(){
		List<BalanceTransfer_SG> results = Lists.newArrayList();
		results.add(new BalanceTransfer_SG("Citibank Credit Cards Balance Transfer", false, 0.0, 608.03, 25.0, ""));
		results.add(new BalanceTransfer_SG("Citibank Ready Credit Balance Transfer", true, 0.0, 608.03, 25.0, ""));
		
		instance.sortPersonalLoans(results, SortingKeyword.AnnualRate);
		
		Assert.assertEquals(results.get(0).getProductName(), "Citibank Ready Credit Balance Transfer");
		Assert.assertEquals(results.get(1).getProductName(), "Citibank Credit Cards Balance Transfer");
	}
	
	@Test
	public void shouldSortByAnnualRate(){
		List<BalanceTransfer_SG> results = Lists.newArrayList();
		
		results.add(new BalanceTransfer_SG("Citibank Credit Cards Balance Transfer", true, 0.0, 608.03, 25.0, ""));
		results.add(new BalanceTransfer_SG("Citibank Ready Credit Balance Transfer", true, 0.0, 608.03, 19.95, ""));
		
		instance.sortPersonalLoans(results, SortingKeyword.AnnualRate);
		
		Assert.assertEquals(results.get(0).getProductName(), "Citibank Ready Credit Balance Transfer");
		Assert.assertEquals(results.get(1).getProductName(), "Citibank Credit Cards Balance Transfer");
	}
	
	@Test
	public void shouldSortByAnnualRate1(){
		List<BalanceTransfer_SG> results = Lists.newArrayList();
		
		results.add(new BalanceTransfer_SG("Citibank Credit Cards Balance Transfer", true, 0.0, 608.03, 25.0, ""));
		results.add(new BalanceTransfer_SG("Citibank Ready Credit Balance Transfer", false, 0.0, 608.03, 19.95, ""));
		
		instance.sortPersonalLoans(results, SortingKeyword.AnnualRate);
		
		Assert.assertEquals(results.get(0).getProductName(), "Citibank Credit Cards Balance Transfer");
		Assert.assertEquals(results.get(1).getProductName(), "Citibank Ready Credit Balance Transfer");
	}
	
	@Test
	public void shouldSortByProcessingFee(){
		List<BalanceTransfer_SG> results = Lists.newArrayList();
		
		results.add(new BalanceTransfer_SG("Citibank Credit Cards Balance Transfer", true, 0.0, 700.0, 25.0, ""));
		results.add(new BalanceTransfer_SG("Citibank Ready Credit Balance Transfer", true, 0.0, 608.03, 25.0, ""));
		
		instance.sortPersonalLoans(results, SortingKeyword.AnnualRate);
		
		Assert.assertEquals(results.get(0).getProductName(), "Citibank Ready Credit Balance Transfer");
		Assert.assertEquals(results.get(1).getProductName(), "Citibank Credit Cards Balance Transfer");
	}
	
	@Test
	public void shouldSortByTransferRate(){
		List<BalanceTransfer_SG> results = Lists.newArrayList();
		
		results.add(new BalanceTransfer_SG("Citibank Credit Cards Balance Transfer", true, 0.0, 608.03, 50.0, ""));
		results.add(new BalanceTransfer_SG("Citibank Ready Credit Balance Transfer", true, 0.0, 608.03, 25.0, ""));
		
		instance.sortPersonalLoans(results, SortingKeyword.AnnualRate);
		
		Assert.assertEquals(results.get(0).getProductName(), "Citibank Ready Credit Balance Transfer");
		Assert.assertEquals(results.get(1).getProductName(), "Citibank Credit Cards Balance Transfer");
	}
	
	@Test
	public void shouldMultiSortByAnnualRate(){
		
		List<BalanceTransfer_SG> results = Lists.newArrayList();

		results.add(new BalanceTransfer_SG("Citibank Ready Credit Balance Transfer", true, 0.0, 158.00, 19.95, "1"));
		results.add(new BalanceTransfer_SG("Citibank Credit Cards Balance Transfer", true, 0.0, 158.00, 25.0, "2"));
		results.add(new BalanceTransfer_SG("HSBC Personal Line of Credit Balance Transfer", false, 3.2, 88.0, 16.5, "3"));
		results.add(new BalanceTransfer_SG("UOB CashPlus Funds Transfer", false, 0.0, 250, 17.95, "4"));
		results.add(new BalanceTransfer_SG("DBS Cashline Balance Transfer", false, 0.0, 358, 19.8, "5"));
		results.add(new BalanceTransfer_SG("Citibank Ready Credit Balance Transfer", false, 0.0, 158, 19.95, "6"));
		results.add(new BalanceTransfer_SG("OCBC EasiCredit Balance Transfer", false, 0.0, 168, 19.98, "7"));
		results.add(new BalanceTransfer_SG("ANZ Credit Card Balance Transfer", false, 0.0, 100, 23.0, "8"));		
		results.add(new BalanceTransfer_SG("Maybank Fund Transfer for CreditAble Customers", false, 0.0, 138, 24.0, "10"));
		results.add(new BalanceTransfer_SG("Maybank Credit Cards Funds Transfer", false, 0.0, 138, 24.0, "9"));
		results.add(new BalanceTransfer_SG("Standard Chartered Credit Cards Funds Transfer", false, 0.0, 250, 24.0, "11"));
		results.add(new BalanceTransfer_SG("DBS Credit Cards Balance Transfer", false, 0.0, 250, 24.9, "12"));
		results.add(new BalanceTransfer_SG("Citibank Credit Cards Balance Transfer", false, 0.0, 158, 25.0, "13"));
		results.add(new BalanceTransfer_SG("UOB Credit Cards Funds Transfer", false, 0.0, 250, 25.0, "14"));
		results.add(new BalanceTransfer_SG("OCBC Credit Cards Balance Transfer", false, 0.0, 168, 25.92, "15"));		
		results.add(new BalanceTransfer_SG("HSBC Credit Cards Balance Transfer", false, 3.2, 88.0, 26.5, "16"));
		
		instance.sortPersonalLoans(results, SortingKeyword.AnnualRate);
		
		Assert.assertEquals(results.get(0).getProductName(), "Citibank Ready Credit Balance Transfer");
		Assert.assertEquals(results.get(1).getProductName(), "Citibank Credit Cards Balance Transfer");
		Assert.assertEquals(results.get(2).getProductName(), "HSBC Personal Line of Credit Balance Transfer");
		Assert.assertEquals(results.get(3).getProductName(), "UOB CashPlus Funds Transfer");
		Assert.assertEquals(results.get(4).getProductName(), "DBS Cashline Balance Transfer");
		Assert.assertEquals(results.get(5).getProductName(), "Citibank Ready Credit Balance Transfer");
		Assert.assertEquals(results.get(6).getProductName(), "OCBC EasiCredit Balance Transfer");
		Assert.assertEquals(results.get(7).getProductName(), "ANZ Credit Card Balance Transfer");
		Assert.assertEquals(results.get(8).getProductName(), "Maybank Fund Transfer for CreditAble Customers");
		Assert.assertEquals(results.get(9).getProductName(), "Maybank Credit Cards Funds Transfer");
		Assert.assertEquals(results.get(10).getProductName(), "Standard Chartered Credit Cards Funds Transfer");
		Assert.assertEquals(results.get(11).getProductName(), "DBS Credit Cards Balance Transfer");
		Assert.assertEquals(results.get(12).getProductName(), "Citibank Credit Cards Balance Transfer");
		Assert.assertEquals(results.get(13).getProductName(), "UOB Credit Cards Funds Transfer");
		Assert.assertEquals(results.get(14).getProductName(), "OCBC Credit Cards Balance Transfer");
		Assert.assertEquals(results.get(15).getProductName(), "HSBC Credit Cards Balance Transfer");
	}
	
}
