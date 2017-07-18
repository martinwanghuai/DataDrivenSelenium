package com.cgg.consumer.brokenUrlFinder;

import com.cgg.producer.DetailedURLProducer;
import com.cgg.producer.URLProducerBuilder;

public class BrokenUrlFinderThread extends DetailedURLProducer {

	public BrokenUrlFinderThread(final URLProducerBuilder builder){
		
		super(builder);
	}

	@Override
	public int visitLinks(int counter){
		
		while (toVisitLinks.size() > 0) {

			String toVisitLink = toVisitLinks.remove();

			if (!this.isVisitedLink(toVisitLink)
					&& this.isInterestedURLToConsume(toVisitLink)) {

				counter ++;
				logger.info(this + " is checking(" + counter + "): " + toVisitLink);
				System.out.println(this + " is checking(" + counter + "): " + toVisitLink);

				//check response code
				this.updateVisitedLinks(toVisitLink);
				int responseCode = driver.checkUrl(this.getLastUrl(toVisitLink));
				
				if(responseCode == 404){
					
					logger.info(this + " found error: " + responseCode + " in: " + toVisitLink);
					System.out.println(this + " found error: " + responseCode + " in: " + toVisitLink);
				}
			}
		}
		
		return -1;
	}
}
