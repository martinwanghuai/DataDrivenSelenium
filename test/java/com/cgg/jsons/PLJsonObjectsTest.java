package com.cgg.jsons;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.cgg.model.PLJsonObjects;
import com.cgg.model.PersonalLoanConfig;
import com.cgg.pl.category.Category;
import com.cgg.pl.category.PersonalLoan;

public class PLJsonObjectsTest {

	PLJsonObjects ins = null;
	PersonalLoanConfig config = null;
	String File_JsonData = "SG_PL_DATA.json";
	
	@BeforeTest
	public void setup(){
		ins = new PLJsonObjects();
		config = new PersonalLoanConfig();
		
	}
	
	/*@Test(enabled = false)
	public void test1(){
		config.setJsonFile(File_JsonData);
		config.setCategory(Category.PersonalInstalment_SG);
		config.setLoanAmount(20000);
		config.setLoanTenure_Month(10);
		config.setLanguage("");
		
		List<PersonalLoan> resultObjs = new PLJsonObjects().getValidResultObjectFromJsonFile(config);
		System.out.println(resultObjs.size());
		
		for(final PersonalLoan obj: resultObjs){
			System.out.println(obj);
		}	
	}
	
	@Test 
	public void shouldLoadResultObjectFromJsonFile(){
		
		PLJsonObjects resultObjs = ins.loadJsonObjects(File_JsonData);
		Assert.assertEquals(resultObjs.getJsonObjects().size(), 38);
	}*/
	
}
