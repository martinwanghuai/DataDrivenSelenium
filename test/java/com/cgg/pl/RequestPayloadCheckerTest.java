package com.cgg.pl;

import java.util.HashMap;

import org.junit.Assert;
import org.testng.annotations.Test;

import com.cgg.pl.funnel.RequestPayloadChecker;

public class RequestPayloadCheckerTest {

	@Test
	public void testCreateHashMapFromJsonStringForAppForm(){
		
		String json = "{\"phone\":\"9999999999\",\"email\":\"automated.qa@compareglobalgroup.com\",\"tc_consent\":true,\"dm_consent\":true,"
				+ "\"vertical\":\"personal_loan\",\"attributes\":{\"application_form\":{\"firstName\":\"tGGeGqhP\",\"lastName\":\"yfJPguSMk\",\"phone\":\"9999999999\","
						+ "\"email\":\"automated.qa@compareglobalgroup.com\",\"step\":\"Contact-Information\",\"completed\":\"false\",\"previous_steps\":{},\"stepIdList\":[]},"
								+ "\"email\":\"automated.qa@compareglobalgroup.com\",\"phone\":\"9999999999\",\"productName\":\"Fellow Finance Kulutusluotto\",\"productCode\":\"44657\","
								+ "\"productDescription\":\"Fellow Finance Kulutusluotto\",\"providerName\":\"Fellow Finance\",\"providerCode\":\"FELL\",\"productLogo\":"
								+ "\"/s3/finland/vertaaensin.fi/staging/fi/images/providerLogos/fellowFinance.png\",\"scoreRanking\":\"UNKNOWN\",\"scoreValue\":\"\"},\"source_url\":"
								+ "\"http://finland-staging.compareglobal.co.uk/laina/sopia?loanAmount=2000&loanTime=36&loanTimeUnit=month&queryParams=undefined&generic=null&cggId=44657&product=Fellow%20Finance%20Kulutusluotto&apiRefForCheckout=http%3A%2F%2Ffinland-staging.compareglobal.co.uk%2Fapi%2Fpersonal-loan%2Fv2%2Floans%2FtermLoan%3FsortBy%3Dmrp%252B%26sortBy%3Dapr%252B%26sortBy%3Dart%252B%26wantToBorrow%3D2000%26wantToBorrowTime%3D36%26wantToBorrowTimeUnit%3Dmonth%26provider%3DFELL%26lang%3Dfi%26pageSize%3D15#/1\","
								+ "\"language\":\"FI\",\"locale\":\"FI\"}";
		
		HashMap<String,Object> obj = RequestPayloadChecker.createHashMapFromJsonString(json);
		System.out.println(obj);
		Assert.assertTrue(obj.get("phone").equals("9999999999"));
		Assert.assertTrue(obj.get("email").equals("automated.qa@compareglobalgroup.com"));
		Assert.assertTrue(obj.get("tc_consent").equals("true"));
		Assert.assertTrue(obj.get("dm_consent").equals("true"));
		Assert.assertTrue(obj.get("firstName").equals("tGGeGqhP"));
		Assert.assertTrue(obj.get("lastName").equals("yfJPguSMk"));
		Assert.assertTrue(obj.get("step").equals("Contact-Information"));
		Assert.assertTrue(obj.get("completed").equals("false"));
		Assert.assertTrue(obj.get("productName").equals("Fellow Finance Kulutusluotto"));
		Assert.assertTrue(obj.get("productCode").equals("44657"));
		Assert.assertTrue(obj.get("productDescription").equals("Fellow Finance Kulutusluotto"));
		Assert.assertTrue(obj.get("providerName").equals("Fellow Finance"));
		Assert.assertTrue(obj.get("providerCode").equals("FELL"));
		Assert.assertTrue(obj.get("productLogo").equals("/s3/finland/vertaaensin.fi/staging/fi/images/providerLogos/fellowFinance.png"));
		
		Assert.assertTrue(obj.get("scoreRanking").equals("UNKNOWN"));
		Assert.assertTrue(obj.get("scoreValue").equals(""));
		Assert.assertTrue(obj.get("language").equals("FI"));
		Assert.assertTrue(obj.get("locale").equals("FI"));
	}
	
	@Test
	public void testCreateHashMapFromJsonStringForAppForm1(){
		
		String json = "{childrenInHousehold=02, authenticated=false, providerCode=FELL, education=bachelorsUniversity, "
				+ "statusUrl=http://finland-staging.compareglobal.co.uk/laina/sopia?cggId=44657&loanAmount=2000&loanTime=36&loanTimeUnit=month#/status/7ad83500-5598-4718-bfdc-6f50f386a75d/FF, "
				+ "language=finnish, hasCreditCard=yes, productName=FellowFinanceKulutusluotto, ssn=311280-999J, Contact-Information=3bd17044-a791-5861-b722-41a555b4e37f, "
				+ "housingType=house, employmentTypeDisplay=Eläkkeellä, Address-Details=669e08dc-5a50-5635-adfd-e5c721dca568, loanPurpose=creditLimit, id=68457, "
				+ "cgg_id=dcbfd20f-111f-5a5c-85d4-07ba8d0869bb, productDescription=FellowFinanceKulutusluotto, providerName=FellowFinance, childrenInHouseholdDisplay=2, "
				+ "otherIncomeSpecification=goVycKPg, presentAddressPostcode=12008, ip=218.103.66.50, completed=true, event_epoch=1498014576426, Loan-Details=80a28a1f-38eb-5fd4-8495-ecc1eb4a249e, "
				+ "loanPurposeDisplay=Käyttöluotto, otherIncome=1, firstName=PeLHMcbZTZ, hasMortgageDisplay=Kyllä, creditCardMonthlyPayment=8816, nationality=AL, phone=9999999999, "
				+ "nationalityDisplay=Albania, iban=FI2112345600000785, languageDisplay=Suomi, maritalStatus=cohabit, presentAddressStartDate=2010-01-01, housingTypeDisplay=Omakotitalo, "
				+ "lastName=UIQwSZr, maritalStatusDisplay=Avoliitossa, ownershipStatus=other, vertical=personal_loan, locale=FI, tc_consent=true, hasOtherLoanDisplay=Kyllä, loanTime=49, "
				+ "Employment-Details=1aad27bb-6f8c-5c17-a06c-bde5e795b78b, housingCost=6, creditConsent=true, ownershipStatusDisplay=Muu, presentAddress1=3512, email=automated.qa@compareglobalgroup.com, "
				+ "loanTimeUnit=month, event_key=uc-5949e370-ee077c6, monthlyIncome=499, educationDisplay=Alempikorkeakoulututkinto, otherExpenses=43, hasOtherLoan=yes, employmentType=retired, "
				+ "loanAmount=1537, presentAddressCity=zhCAcmbY, event_id=440e6d96-6821-5527-9d6a-0771be36bff0, productCode=44657, hasCreditCardDisplay=Kyllä, hasMortgage=yes, "
				+ "Credit-Details=22ac2f9e-e70a-5d64-b59b-2d151217a966, hasSummerHouse=yes, hasSummerHouseDisplay=Kyllä, loanTimeUnitDisplay=Kuukautta, step=API, dm_consent=true, "
				+ "Personal-Information-1=d2af4322-5c08-509f-bba2-434d3b7fc639}";
		
		HashMap<String,Object> obj = RequestPayloadChecker.createHashMapFromJsonString(json);
		System.out.println(obj);
		Assert.assertTrue(obj.get("childrenInHousehold").equals("02"));
		Assert.assertTrue(obj.get("authenticated").equals("false"));
		Assert.assertTrue(obj.get("providerCode").equals("FELL"));
		Assert.assertTrue(obj.get("education").equals("bachelorsUniversity"));
		Assert.assertTrue(obj.get("housingType").equals("house"));
		Assert.assertTrue(obj.get("employmentTypeDisplay").equals("Eläkkeellä"));
		Assert.assertTrue(obj.get("Address-Details").equals("669e08dc-5a50-5635-adfd-e5c721dca568"));
		Assert.assertTrue(obj.get("loanPurpose").equals("creditLimit"));
		Assert.assertTrue(obj.get("id").equals("68457"));
		Assert.assertTrue(obj.get("cgg_id").equals("dcbfd20f-111f-5a5c-85d4-07ba8d0869bb"));
		Assert.assertTrue(obj.get("productDescription").equals("FellowFinanceKulutusluotto"));
		Assert.assertTrue(obj.get("providerName").equals("FellowFinance"));
		Assert.assertTrue(obj.get("childrenInHouseholdDisplay").equals("2"));
		Assert.assertTrue(obj.get("otherIncomeSpecification").equals("goVycKPg"));
		Assert.assertTrue(obj.get("presentAddressPostcode").equals("12008"));
		Assert.assertTrue(obj.get("ip").equals("218.103.66.50"));
		Assert.assertTrue(obj.get("completed").equals("true"));
		Assert.assertTrue(obj.get("event_epoch").equals("1498014576426"));
		Assert.assertTrue(obj.get("Loan-Details").equals("80a28a1f-38eb-5fd4-8495-ecc1eb4a249e"));
		Assert.assertTrue(obj.get("loanPurposeDisplay").equals("Käyttöluotto"));
		Assert.assertTrue(obj.get("otherIncome").equals("1"));
		Assert.assertTrue(obj.get("firstName").equals("PeLHMcbZTZ"));
		Assert.assertTrue(obj.get("hasMortgageDisplay").equals("Kyllä"));
		Assert.assertTrue(obj.get("creditCardMonthlyPayment").equals("8816"));
		Assert.assertTrue(obj.get("nationality").equals("AL"));
		Assert.assertTrue(obj.get("phone").equals("9999999999"));
		Assert.assertTrue(obj.get("nationalityDisplay").equals("Albania"));
		Assert.assertTrue(obj.get("iban").equals("FI2112345600000785"));
		Assert.assertTrue(obj.get("languageDisplay").equals("Suomi"));
		Assert.assertTrue(obj.get("maritalStatus").equals("cohabit"));
		Assert.assertTrue(obj.get("presentAddressStartDate").equals("2010-01-01"));
		Assert.assertTrue(obj.get("housingTypeDisplay").equals("Omakotitalo"));
		Assert.assertTrue(obj.get("lastName").equals("UIQwSZr"));
		Assert.assertTrue(obj.get("maritalStatusDisplay").equals("Avoliitossa"));
		Assert.assertTrue(obj.get("ownershipStatus").equals("other"));
		Assert.assertTrue(obj.get("vertical").equals("personal_loan"));
		Assert.assertTrue(obj.get("locale").equals("FI"));
		Assert.assertTrue(obj.get("tc_consent").equals("true"));
		Assert.assertTrue(obj.get("hasOtherLoanDisplay").equals("Kyllä"));
		Assert.assertTrue(obj.get("loanTime").equals("49"));
		Assert.assertTrue(obj.get("Employment-Details").equals("1aad27bb-6f8c-5c17-a06c-bde5e795b78b"));
		Assert.assertTrue(obj.get("housingCost").equals("6"));
		Assert.assertTrue(obj.get("creditConsent").equals("true"));
		Assert.assertTrue(obj.get("ownershipStatusDisplay").equals("Muu"));
		Assert.assertTrue(obj.get("presentAddress1").equals("3512"));
		Assert.assertTrue(obj.get("email").equals("automated.qa@compareglobalgroup.com"));
		Assert.assertTrue(obj.get("loanTimeUnit").equals("month"));
		Assert.assertTrue(obj.get("event_key").equals("uc-5949e370-ee077c6"));
		Assert.assertTrue(obj.get("monthlyIncome").equals("499"));
		Assert.assertTrue(obj.get("educationDisplay").equals("Alempikorkeakoulututkinto"));
		Assert.assertTrue(obj.get("otherExpenses").equals("43"));
		Assert.assertTrue(obj.get("hasOtherLoan").equals("yes"));
		Assert.assertTrue(obj.get("employmentType").equals("retired"));
		Assert.assertTrue(obj.get("Credit-Details").equals("22ac2f9e-e70a-5d64-b59b-2d151217a966"));
		Assert.assertTrue(obj.get("presentAddressCity").equals("zhCAcmbY"));
		Assert.assertTrue(obj.get("event_id").equals("440e6d96-6821-5527-9d6a-0771be36bff0"));
		Assert.assertTrue(obj.get("productCode").equals("44657"));
		Assert.assertTrue(obj.get("hasCreditCardDisplay").equals("Kyllä"));
		Assert.assertTrue(obj.get("hasMortgage").equals("yes"));
		Assert.assertTrue(obj.get("Credit-Details").equals("22ac2f9e-e70a-5d64-b59b-2d151217a966"));
		Assert.assertTrue(obj.get("hasSummerHouse").equals("yes"));
		Assert.assertTrue(obj.get("hasSummerHouseDisplay").equals("Kyllä"));
		Assert.assertTrue(obj.get("loanTimeUnitDisplay").equals("Kuukautta"));
		Assert.assertTrue(obj.get("step").equals("API"));
		Assert.assertTrue(obj.get("dm_consent").equals("true"));
		Assert.assertTrue(obj.get("Personal-Information-1").equals("d2af4322-5c08-509f-bba2-434d3b7fc639"));
	}
	
	@Test
	public void StrReplaceTest(){
		
		System.out.println("{childrenInHousehold=02, authenticated=false, providerCode=FELL, education=bachelorsUniversity, "
				+ "statusUrl=http://finland-staging.compareglobal.co.uk/laina/sopia?cggId=44657&loanAmount=2000&loanTime=36&loanTimeUnit=month#/status/7ad83500-5598-4718-bfdc-6f50f386a75d/FF, "
				+ "language=finnish, hasCreditCard=yes, productName=Fellow Finance Kulutusluotto, ssn=311280-999J, Contact-Information=3bd17044-a791-5861-b722-41a555b4e37f, "
				+ "housingType=house, employmentTypeDisplay=Eläkkeellä, Address-Details=669e08dc-5a50-5635-adfd-e5c721dca568, loanPurpose=creditLimit, id=68457, "
				+ "cgg_id=dcbfd20f-111f-5a5c-85d4-07ba8d0869bb, productDescription=Fellow Finance Kulutusluotto, providerName=Fellow Finance, childrenInHouseholdDisplay=2, "
				+ "otherIncomeSpecification=goVycKPg, presentAddressPostcode=12008, ip=218.103.66.50, completed=true, event_epoch=1498014576426, Loan-Details=80a28a1f-38eb-5fd4-8495-ecc1eb4a249e, "
				+ "loanPurposeDisplay=Käyttöluotto, otherIncome=1, firstName=PeLHMcbZTZ, hasMortgageDisplay=Kyllä, creditCardMonthlyPayment=8816, nationality=AL, phone=9999999999, "
				+ "nationalityDisplay=Albania, iban=FI21 1234 5600 0007 85, languageDisplay=Suomi, maritalStatus=cohabit, presentAddressStartDate=2010-01-01, housingTypeDisplay=Omakotitalo, "
				+ "lastName=UIQwSZr, maritalStatusDisplay=Avoliitossa, ownershipStatus=other, vertical=personal_loan, locale=FI, tc_consent=true, hasOtherLoanDisplay=Kyllä, loanTime=49, "
				+ "Employment-Details=1aad27bb-6f8c-5c17-a06c-bde5e795b78b, housingCost=6, creditConsent=true, ownershipStatusDisplay=Muu, presentAddress1=3512, email=automated.qa@compareglobalgroup.com, "
				+ "loanTimeUnit=month, event_key=uc-5949e370-ee077c6, monthlyIncome=499, educationDisplay=Alempi korkeakoulututkinto, otherExpenses=43, hasOtherLoan=yes, employmentType=retired, "
				+ "loanAmount=1537, presentAddressCity=zhCAcmbY, event_id=440e6d96-6821-5527-9d6a-0771be36bff0, productCode=44657, hasCreditCardDisplay=Kyllä, hasMortgage=yes, "
				+ "Credit-Details=22ac2f9e-e70a-5d64-b59b-2d151217a966, hasSummerHouse=yes, hasSummerHouseDisplay=Kyllä, loanTimeUnitDisplay=Kuukautta, step=API, dm_consent=true, "
				+ "Personal-Information-1=d2af4322-5c08-509f-bba2-434d3b7fc639}".replace("statusUrl=http://finland-staging.compareglobal.co.uk/laina/sopia?cggId=44657&loanAmount=2000&loanTime=36&loanTimeUnit=month#/status/7ad83500-5598-4718-bfdc-6f50f386a75d/FF", ""));
	}
}
