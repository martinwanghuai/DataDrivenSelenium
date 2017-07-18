package toy;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.cgg.pl.funnel.RequestPayloadChecker;
import com.mifmif.common.regex.Generex;

import utility.Constant;
import utility.ExcelUtils;
import utility.IOUtils;

public class StringTester {

	
	@Test
	public void testDataDictionary(){
		try{
			ExcelUtils util = new ExcelUtils(Constant.USRDIR + Constant.PATH_TESTDATA + Constant.FILE_DATADICTIONARY, "Common Rules");
			List<String> fields = util.getColumnData("Field Name Used in JSON");
			for(String field: fields){
				System.out.println(field);	
			}
			util.resetExcelWSheet("PL Specific");
			List<String> fields2 = util.getColumnData("Field Name Used in JSON");
			System.out.println("------------------------");
			for(String field: fields2){
				System.out.println(field);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testRequestPayloadParser(){
		String json = IOUtils.loadFile(Constant.USRDIR + Constant.PATH_TESTDATA + "RequestPayload.json");
		HashMap<String, Object> map = RequestPayloadChecker.createHashMapFromJsonString(json);
		this.iterateMapKeys(map);
	}
	
	private void iterateMapKeys(HashMap<String, Object> map){
		
		Set<String> keys = map.keySet();
		for(String key: keys){
			Object value = map.get(key);
			if(value instanceof String){
				System.out.println(key);	
			}else{
				this.iterateMapKeys((HashMap<String, Object>)value);
			}
		}
	}
	
	@Test(invocationCount=1)
	public void testXeger(){
		/*
		^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$
*/		
		
		//String regex = "19[0-9]{2}";
		String regex = "[0][23457](\\d)(?!\\1{6})\\d{6}";
		Generex gen = new Generex(regex);
		String result = gen.getFirstMatch();
		System.out.println(result);
	}
	
	@Test
	public void shouldRemoveWhitespace(){
		String str = " Balance Transfer rate of 0% for 12 months";
		String temp = str.trim();
		Assert.assertEquals(temp, "Balance Transfer rate of 0% for 12 months");
	}
	
	@Test
	public void shouldReplaceAll() throws Exception {

		String str = "3.66 kr.";
		String temp = str.replaceAll("\\.", "");
		Assert.assertEquals(temp, "366 kr");
	}
	
	@Test
	public void shouldReplaceAll2() throws Exception {

		String str = "3,66 kr,";
		String temp = str.replaceAll("\\,", "");
		Assert.assertEquals(temp, "366 kr");
	}
	
	@Test
	public void shouldSameString() throws Exception{
		/*String str1 = "Loan amount of up to HKD or times your monthly salary whichever is lower".replaceAll("\\s+", "");
		String str2 = "Loan amount of up to HKD or times your monthly salary whichever is lower".replaceAll("\\s+", "");
		
		System.out.println(str1.equalsIgnoreCase(str2));*/
		
		/*byte[] str1 = "Loan amount of up to HKD600,000 or 8 times your monthly salary (whichever is lower).".replaceAll("\\s+", "").getBytes("UTF-8");
		byte[] str2 = "Loan amount of up to HKD600,000 or 8 times your monthly salary (whichever is lower).".replaceAll("\\s+", "").getBytes("UTF-8");*/
		
		byte[] str1 = "Only the latest month's income proof is required people from all sectors are welcome to apply".replaceAll("\\s+", "").getBytes("UTF-8");
		byte[] str2 = "Only the latest month's income proof is required people from all sectors are welcome to apply".replaceAll("\\s+", "").getBytes("UTF-8");
		
		System.out.println(str1.length + ";" + str2.length);
		for(int i = 0; i < str1.length; i ++){
			
			byte b1 = str1[i];
			byte b2 = str2[i];
			if(!Integer.toBinaryString(b1).equals(Integer.toBinaryString(b2))){
				System.out.println("Different:" + i);
			}
			System.out.println("c1:" + (char)b1 + "->" + Integer.toBinaryString(b1));
			System.out.println("c2:" + (char)b2 + "->" + Integer.toBinaryString(b2));
		}
		Assert.assertEquals(str1, str2);
	}
	
	
	@Test
	public void shouldPrintChineseCharacter() throws Exception{

		System.out.println(Charset.defaultCharset());
		String str = "/寬頻上網/寬頻服務業者";
		
		String newStr = new String(str.getBytes(Charset.defaultCharset()), StandardCharsets.UTF_8);
		System.out.println(newStr);
	}
	
	@Test
	public void compareToStrings(){
		
		Assert.assertEquals("a".compareTo("b"), -1);
	}
}
