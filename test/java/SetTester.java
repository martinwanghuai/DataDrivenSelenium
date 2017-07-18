import java.util.HashSet;

import org.testng.annotations.Test;

import com.google.common.collect.Sets;

public class SetTester {

	@Test
	public void test(){
		HashSet<String> expectedValues = Sets.newHashSet();
		expectedValues.add("NORW");
		expectedValues.add("SAXO");
		HashSet<String> actualValues = Sets.newHashSet();
		actualValues.add("SAXO");
		actualValues.add("NORW");
		if(!Sets.symmetricDifference( expectedValues, actualValues).isEmpty()){
			System.out.println("Different");
		}else{
			System.out.println("Same");
		}
	}
}
