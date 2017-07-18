package com.cgg.consumer.performance;

import com.cgg.webCrawler.AbstractThreadController;

import utility.IOUtils;
import utility.WebDriverUtils;

public class GooglePageSpeedInsightController {
	
	public static void main(String[] args) throws Exception{

		GooglePageSpeedInsightController controller = new GooglePageSpeedInsightController();
		controller.callPageSpeedInsight("https://www.moneyhero.com.hk/en/personal-loan/personal-instalment", true);
	}

	public void callPageSpeedInsight(final String url, final boolean isDesktop){
		
		String api = "https://www.googleapis.com/pagespeedonline/v2/runPagespeed?url=" + url;
		api += isDesktop? "strategy=desktop":"strategy=mobile";
		
		String jsonStr = WebDriverUtils.callRESTAPI(api);
		
		GooglePageSpeedObject obj = (GooglePageSpeedObject)IOUtils.loadJsonObjects(jsonStr, GooglePageSpeedObject.class);
		StringBuilder sb = new StringBuilder();
		sb.append("score\n").append(obj.getRuleGroups().getSPEED().getScore()).append("\n");
		System.out.println(sb.toString());
		final String fileSuffix = AbstractThreadController.extractFileSuffixFromUrl(url);
		String saveFile = System.getProperty("user.dir") + "/src/PerformanceTesting/PageSpeedInsight/PageSpeedInsight_" + fileSuffix + "_" +(isDesktop ? "_desktop": "_mobile") + ".csv";
		IOUtils.saveIntoFile(sb.toString(), saveFile);
	}
}
