package testCases;

import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestDriverTest {

	@Test
	public void testURL(){
		
		try{
			String url = "http://a.com/s3/carmodel/a b";
			URL temp = new URL(url);
			System.out.println(temp.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/*
	 * private int i = 0; private final Object lock = new Object();
	 * 
	 * @Test(threadPoolSize = 3, invocationCount = 6) public void test1() {
	 * synchronized (lock) { i++; System.out.println("Thread ID(" +
	 * (Thread.currentThread().getId()%3+1) + "):" + i); } }
	 */

	@DataProvider(name = "test1")
	public Object[][] primeNumbers() {
		return new Object[][] { { 2, true }, { 6, false }, { 19, true }, { 22, false }, { 23, true } };
	}

	ExecutorService exec = Executors.newFixedThreadPool(5);
	
	@Test(dataProvider = "test1")
	public void testPrimeNumberChecker(Integer inputNumber, Boolean expectedResult) {
		
		exec.execute(new Runnable(){
				@Override
				public void run(){
					printSth(inputNumber, expectedResult);			
				}
				
		});
	}

	private void printSth(Integer inputNumber, Boolean expectedResult) {
		System.out.println(inputNumber + " " + expectedResult);
	}
}
