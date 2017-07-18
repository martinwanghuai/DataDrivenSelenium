package utility;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.mifmif.common.regex.Generex;

public class MathUtilsTest {

	@Test
	public void shouldGenRandomNum(){
		
		String pattern = "[1-9](5[0]{7}|[1-4][0-9]{7}|[1-9][0-9]{6}){7,8}";
		String input = new Generex(pattern).random();
		System.out.println(input);
	}
	
	@Test
	public void shouldExtractDoubleFromString(){

		Assert.assertEquals(MathUtils.extractDouble("20,000,000"), 20000000.0);
	}
	
	@Test
	public void shouldExtractDoubleFromString1(){

		Assert.assertEquals(MathUtils.extractDouble("20,000"), 20000.0);
	}
	
	@Test
	public void shouldExtractDoubleFromString2(){

		Assert.assertEquals(MathUtils.extractDouble("20.001"), 20.001);
	}

	@Test
	public void shouldExtractDoubleFromString3(){

		Assert.assertEquals(MathUtils.extractDouble("S$ 20,000"), 20000.0);
	}
	
	@Test
	public void shouldExtractDoubleFromString6(){

		Assert.assertEquals(MathUtils.extractDouble("20000 k"), 20000.0);
	}
	
	@Test
	public void shouldExtractDoubleFromString7(){

		Assert.assertEquals(MathUtils.extractDouble("20000 €"), 20000.0);
	}

	@Test
	public void shouldExtractDoubleFromString4(){

		Assert.assertEquals(MathUtils.extractDouble("21 years old"), 21.0);
	}
	
	@Test
	public void shouldExtractDoubleFromString5(){

		Assert.assertEquals(MathUtils.extractDouble("0%"), 0.0);
	}
}
