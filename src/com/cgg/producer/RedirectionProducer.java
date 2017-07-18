package com.cgg.producer;

import java.util.Iterator;
import java.util.List;

import com.cgg.model.Redirection;
import com.cgg.webCrawler.AbstractThread;

public class RedirectionProducer extends AbstractThread {

	public RedirectionProducer(final Builder builder){
		super(builder);
	}
	
	@Override
	protected int visitLinks(int linkNo) throws Exception{

		if (!Thread.interrupted() && visitedLinks.size() == 0) {
			
			final String sheetName = "Redirection Rule";
			List<String> redirections = Redirection.bulkMapExcelRowToRedirectionObject(sheetName);
			
			visitedLinks.addAll(redirections);
			Iterator<String> ite = visitedLinks.iterator();
			while(ite.hasNext()){
				String redirection = ite.next();
				logger.info(this + "" + redirection);
				System.out.println(this + "" + redirection);
			}
			logger.info(this + ": load rediction rules:" + redirections.size());
			System.out.println(this + ": load rediction rules:" + redirections.size());
		}
		
		return -1;
	}

	@Override
	protected boolean isInterestedURLToVisit(String url, String domainUrl) {
		return true;
	}

}
