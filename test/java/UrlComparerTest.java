import org.junit.Test;

import junit.framework.Assert;
import utility.UrlComparer;


public class UrlComparerTest {

	@Test
	public void test1() throws Exception{
		
		UrlComparer compare = new UrlComparer();
		Assert.assertEquals(true, compare.urlsMatch("http://dev.vlvdev.com/men/tops-men.html?cat=16#store.menu", "http://dev.vlvdev.com/men/tops-men.html?cat=14#store.menu"));
		Assert.assertEquals(true, compare.urlsMatch("http://dev.vlvdev.com/men/tops-men.html?color=16", "http://dev.vlvdev.com/men/tops-men.html?color=14"));
		
	}
}
