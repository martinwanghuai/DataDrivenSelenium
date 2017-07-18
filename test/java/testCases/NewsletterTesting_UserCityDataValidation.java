package testCases;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.testng.annotations.Test;

import com.cgg.model.UserCityRecord;
import com.cgg.model.UserCityRecord.Result;
import com.google.gson.Gson;

public class NewsletterTesting_UserCityDataValidation {

	private String getUserCityData(){

		String result = "";
		try {
			URL url = new URL("https://usercityapidev.compareglobal.co.uk/dk/search/events?email=test@test.com");

			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			connection.setRequestProperty("Usercity-Auth-Token", "c1550bd0-e187-4bce-9044-846eb89cad8f");
			InputStream content = (InputStream) connection.getInputStream();
			BufferedReader bs = new BufferedReader(new InputStreamReader(content));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = bs.readLine()) != null) {
				sb.append(line).append("\n");
			}
			result = sb.toString();
		} catch (Exception e) {
			
		}
		System.out.println(result);
		
		return result;
	}
	
	@Test
	public void validateUserCityData(){
		
		String result = this.getUserCityData();
//		HashMap<String, Object> values = this.createHashMapFromJsonString(result);
		Gson gson = new Gson();
		UserCityRecord records = gson.fromJson(result, UserCityRecord.class);
		Result record = records.results[0];
		System.out.println("value:" + record);
	}
}
