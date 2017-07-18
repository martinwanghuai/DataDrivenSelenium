package com.cgg.model;

import java.util.List;

import org.testng.annotations.Test;
import org.testng.collections.Lists;

import com.cgg.model.MortgageJsonObject.Mortgage.Product;
import com.cgg.pl.category.Mortgage_PT;

import utility.Constant;
import utility.IOUtils;
import utility.WebDriverUtils;

public class MortgageTest {

	@Test
	public void testJsonFileMapper(){
		
		String alfrescoURL = "https://eu-alfresco.compareglobal.co.uk/alfresco/service/api/login?u=" +  Constant.UID_ALFRESCO+ "&pw=" + Constant.PWD_ALFRESCO;
		String xmlStr = WebDriverUtils.callRESTAPI(alfrescoURL);
		String ticket = xmlStr.substring(xmlStr.indexOf("<ticket>") + "<ticket>".length(), xmlStr.indexOf("</ticket>"));
		alfrescoURL = "https://eu-alfresco.compareglobal.co.uk/alfresco/api/-default-/public/cmis/versions/1.1/browser/root/sites/"
				+ "portugal-staging"
				+ "/documentLibrary/dataFiles/PT_MG_DATA.json?alf_ticket="
				+ ticket;
		
		String jsonStr = WebDriverUtils.callRESTAPI(alfrescoURL);;
		
		MortgageJsonObject json = (MortgageJsonObject)IOUtils.loadJsonObjects(jsonStr, MortgageJsonObject.class);
		Product[] products = json.mortgage.products;
		for(Product product: products){
			System.out.println(product.getId());
		}
	}
	
	@Test
	public void testAPR(){
		
		double euribor = -0.101;
		double spread = 1.6/100.0;
		double loanTenure_Month = 48.0;
		double loanAmount = 4750;
		double monthCommission = 1.872;
		double healthInsurance = 5.0;
		double multiRiskInsurance = 5.0;
		List<Double> initialComissions = Lists.newArrayList(230.0, 195.0, 0.0);

		double monthlyRepayment = Mortgage_PT.calculateMonthlyRepayment(euribor, spread, loanTenure_Month, loanAmount, monthCommission, healthInsurance, multiRiskInsurance);
		double apr = Mortgage_PT.calculateAPR(loanAmount, loanTenure_Month, initialComissions, monthlyRepayment);
		double totalRepayment = Mortgage_PT.calculateTotalRepayment(monthlyRepayment, loanTenure_Month, loanAmount, initialComissions);
		System.out.println(apr);		
	}
}
