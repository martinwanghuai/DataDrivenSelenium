package thirdparty;

import java.io.IOException;
import java.net.URI;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import utility.WebDriverUtils;

public class JenkinsScraper {

	public static void main(final String[] args){
		
		try{
			final String token = "4c91d74c5c9cf7b290067fbb60916c86fc401774";
			final String urlString = "http://jenkins-master.compareglobal.co.uk/view/SmokeTest/view/FunctionalTest/job/PersonalLoan_APITesting/buildWithParameters?token=" + token + "&country=HK&component=PLWidgets";
			
			//1. Normal login logic
/*			final String username = "";
			final String password = "";
			JenkinsScraper scraper = new JenkinsScraper();
			System.out.println(scraper.scrape(urlString, username, password));*/
			
			//2. Setup Jenkins first: enable remote access first; then construct the url with tokens 
			WebDriverUtils driver = new WebDriverUtils();
			System.out.println(driver.callRESTAPI(urlString));
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public String scrape(String urlString, String username, String password) throws ClientProtocolException, IOException {
		URI uri = URI.create(urlString);
		HttpHost host = new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme());
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(new AuthScope(uri.getHost(), uri.getPort()), new UsernamePasswordCredentials(username, password));
		// Create AuthCache instance
		AuthCache authCache = new BasicAuthCache();
		// Generate BASIC scheme object and add it to the local auth cache
		BasicScheme basicAuth = new BasicScheme();
		authCache.put(host, basicAuth);
		CloseableHttpClient httpClient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();
		HttpPost httpPost = new HttpPost(uri);
		// Add AuthCache to the execution context
		HttpClientContext localContext = HttpClientContext.create();
		localContext.setAuthCache(authCache);

		HttpResponse response = httpClient.execute(host, httpPost, localContext);

		return EntityUtils.toString(response.getEntity());
	}

}
