package utility;

import java.util.List;

import org.junit.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.collections.Lists;

import com.cgg.pl.category.PersonalInstalment_SG;
import com.cgg.pl.category.SortingKeyword;

public class PersonalInstalmentResultObjectTest {

	List<PersonalInstalment_SG> objs = Lists.newArrayList();
	PersonalInstalment_SG instance = null;
	
	@BeforeTest
	public void setup(){
		
		objs.add(new PersonalInstalment_SG(true, 11449, 4.83, 318.03));
		objs.add(new PersonalInstalment_SG(false, 11059, 3.53, 307.19));
		objs.add(new PersonalInstalment_SG(false, 11410, 4.7, 316.94));
		objs.add(new PersonalInstalment_SG(false, 11449, 4.83, 318.03));
		objs.add(new PersonalInstalment_SG(false, 12040, 6.80, 334.44));
		objs.add(new PersonalInstalment_SG(false, 12097, 5.18, 336.03));
		objs.add(new PersonalInstalment_SG(false, 12154, 3.78, 337.61));
		objs.add(new PersonalInstalment_SG(false, 12208, 7.32, 339.11));
		objs.add(new PersonalInstalment_SG(false, 12340, 6.60, 342.78));
		objs.add(new PersonalInstalment_SG(false, 12400, 8.00, 344.44));
		objs.add(new PersonalInstalment_SG(false, 12514, 6.88, 347.61));
		instance = new PersonalInstalment_SG();
	}
	
	@Test
	public void shouldSortByTotalPayment(){
		
		instance.sortPersonalLoans(objs, SortingKeyword.TotalRepayment);
		List<PersonalInstalment_SG> objs_expected = Lists.newArrayList();
		objs_expected.add(new PersonalInstalment_SG(true, 11449, 4.83, 318.03));
		objs_expected.add(new PersonalInstalment_SG(false, 11059, 3.53, 307.19));
		objs_expected.add(new PersonalInstalment_SG(false, 11410, 4.7, 316.94));
		objs_expected.add(new PersonalInstalment_SG(false, 11449, 4.83, 318.03));
		objs_expected.add(new PersonalInstalment_SG(false, 12040, 6.80, 334.44));
		objs_expected.add(new PersonalInstalment_SG(false, 12097, 5.18, 336.03));
		objs_expected.add(new PersonalInstalment_SG(false, 12154, 3.78, 337.61));
		objs_expected.add(new PersonalInstalment_SG(false, 12208, 7.32, 339.11));
		objs_expected.add(new PersonalInstalment_SG(false, 12340, 6.60, 342.78));
		objs_expected.add(new PersonalInstalment_SG(false, 12400, 8.00, 344.44));
		objs_expected.add(new PersonalInstalment_SG(false, 12514, 6.88, 347.61));
		
		Assert.assertTrue(objs.equals(objs_expected));
	}
	
	@Test
	public void shouldSortByFlatRate(){
		
		instance.sortPersonalLoans(objs, SortingKeyword.APR);
		List<PersonalInstalment_SG> objs_expected = Lists.newArrayList();
		objs_expected.add(new PersonalInstalment_SG(true, 11449, 4.83, 318.03));
		objs_expected.add(new PersonalInstalment_SG(false, 11059, 3.53, 307.19));
		objs_expected.add(new PersonalInstalment_SG(false, 12154, 3.78, 337.61));
		objs_expected.add(new PersonalInstalment_SG(false, 11410, 4.7, 316.94));
		objs_expected.add(new PersonalInstalment_SG(false, 11449, 4.83, 318.03));
		objs_expected.add(new PersonalInstalment_SG(false, 12097, 5.18, 336.03));
		objs_expected.add(new PersonalInstalment_SG(false, 12340, 6.60, 342.78));
		objs_expected.add(new PersonalInstalment_SG(false, 12040, 6.80, 334.44));
		objs_expected.add(new PersonalInstalment_SG(false, 12514, 6.88, 347.61));
		objs_expected.add(new PersonalInstalment_SG(false, 12208, 7.32, 339.11));
		objs_expected.add(new PersonalInstalment_SG(false, 12400, 8.00, 344.44));
		
		Assert.assertTrue(objs.equals(objs_expected));
	}
	
	@Test
	public void shouldSortByMonthlyRepayment(){
		
		instance.sortPersonalLoans(objs, SortingKeyword.MonthlyRepayment);
		List<PersonalInstalment_SG> objs_expected = Lists.newArrayList();
		objs_expected.add(new PersonalInstalment_SG(true, 11449, 4.83, 318.03));
		objs_expected.add(new PersonalInstalment_SG(false, 11059, 3.53, 307.19));
		objs_expected.add(new PersonalInstalment_SG(false, 11410, 4.7, 316.94));
		objs_expected.add(new PersonalInstalment_SG(false, 11449, 4.83, 318.03));
		objs_expected.add(new PersonalInstalment_SG(false, 12040, 6.80, 334.44));
		objs_expected.add(new PersonalInstalment_SG(false, 12097, 5.18, 336.03));
		objs_expected.add(new PersonalInstalment_SG(false, 12154, 3.78, 337.61));
		objs_expected.add(new PersonalInstalment_SG(false, 12208, 7.32, 339.11));
		objs_expected.add(new PersonalInstalment_SG(false, 12340, 6.60, 342.78));
		objs_expected.add(new PersonalInstalment_SG(false, 12400, 8.00, 344.44));
		objs_expected.add(new PersonalInstalment_SG(false, 12514, 6.88, 347.61));
		
		Assert.assertTrue(objs.equals(objs_expected));
	}
}
