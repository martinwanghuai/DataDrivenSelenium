package com.cgg.pl.funnel;

import org.apache.log4j.Logger;

import com.cgg.producer.DetailedURLProducer;

import pages.FunnelStep;
import utility.Checker;
import utility.Constant;
import utility.WebDriverUtils;

public class BrokenUrlFinder extends SimpleFunnelChecker {

	Checker checker = null;
	Logger logger = null;
	
	public BrokenUrlFinder(final WebDriverUtils driver){
		
		super(driver);
		checker = new Checker(driver);
		logger = Logger.getLogger(BrokenUrlFinder.class);
	}

	@Override
	public boolean checkFunnelStep(final FunnelStep step) {
		
		System.out.println("[" + this.getClass().getSimpleName() + "] Started\n");
		String link = getLink(step);

		// 1. Collect urls in this page
		DetailedURLProducer producer = new DetailedURLProducer();
		producer.setDriver(driver);
		producer.setDomainUrl("");
		producer.addEvents(DetailedURLProducer.eventNames, 0, 0, 200, driver.getCurrentUrl());

		// 2. Check urls
		boolean haveBrokenURL = false;
		while (producer.getToVisitLinks().size() > 0) {
			String toVisitLink = producer.getLastUrl(producer.getToVisitLinks().remove());
			int responseCode = driver.checkUrl(toVisitLink);

			if (responseCode == 404) {
				System.out.println(
						"[" + this.getClass().getSimpleName() + "] " + link + ": Found broken url:" + toVisitLink);
				haveBrokenURL = true;
			}
		}

		if (!haveBrokenURL) {
			System.out.println("[" + this.getClass().getSimpleName() + "] No broken url\n");
		}

		return true;
	}
	
	@Override
	public boolean checkCurrentPage(){
		
		return this.checkFunnelStep(null);
	}
	
	@Override
	public boolean isEnabled(){
		
		return Constant.ENABLE_FUNNEL_TESTER;
	}
}
