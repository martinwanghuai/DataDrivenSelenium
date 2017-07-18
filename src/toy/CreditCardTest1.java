package toy;

import org.testng.annotations.Test;

import com.cgg.model.CRMMapping;
import com.cgg.model.CRMMappingDeserializer;

import utility.Constant;
import utility.IOUtils;
import utility.WebDriverUtils;

public class CreditCardTest1 {

	@Test
	public void testCRMMapping(){
		
		CRMMapping mapping = (CRMMapping) IOUtils.loadJsonObjects(
				IOUtils.loadFile(Constant.USRDIR + Constant.PATH_TESTDATA + "personal_loan_sg.json"), CRMMapping.class, new CRMMappingDeserializer());
		
		System.out.println("here");
	}
	@Test
	public void testCase1(){
		
		WebDriverUtils driver = new WebDriverUtils();
		String str = driver.getResponseHtml("https://www.singsaver.com.sg/");
		int index = str.indexOf("\"authenticated\":");
		int index1 = str.indexOf("\"authenticatedMethod\"");
		if(index > -1){
			String flag = str.substring(index + "\"authenticated\":".length(),index1).trim().replace(",", "");
			System.out.println(flag);
		}
	}

}
