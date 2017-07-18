package toy;

import java.util.Collection;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeDriver;

import com.googlecode.fightinglayoutbugs.LayoutBug;
import com.googlecode.fightinglayoutbugs.WebPage;

import utility.Constant;

public class LayoutBugFighter {

	public static void main(String[] args) {

		System.setProperty("webdriver.chrome.driver", Constant.USRDIR + Constant.PATH_TO_CHROME_DRIVER);
		ChromeDriver driver = new ChromeDriver();
		
		try {
			String testPageUrl = "http://www.money101.com.tw/%E8%81%AF%E7%B5%A1%E6%88%91%E5%80%91";
			driver.get(testPageUrl);
			driver.manage().window().setSize(new Dimension(743, 288));
			WebPage webPage = new WebPage(driver);
			
			Thread.sleep(10000);
			/*FightingLayoutBugs detector = new FightingLayoutBugs();
			detector.enableDebugMode();*/
			
			DetectTextWithTooLowContrast detector = new DetectTextWithTooLowContrast();
			final Collection<LayoutBug> layoutBugs = detector.findLayoutBugsIn(webPage);
			System.out.println("Found " + layoutBugs.size() + " layout bug(s).");
			for (LayoutBug bug : layoutBugs) {
				System.out.println(bug);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			driver.quit();
		}
	}

}
