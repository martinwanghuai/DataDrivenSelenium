package com.cgg.model;

import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.testng.collections.Lists;

import com.google.common.base.Function;
import com.google.common.base.Predicates;
import com.google.common.collect.FluentIterable;

import utility.Checker;
import utility.Constant;
import utility.ExcelUtils;
import utility.IOUtils;
import utility.MathUtils;

public class Redirection {

	private int ID;
	private int responseCode;
	private Platform platform;
	private String fromURL;
	private String toURL;
	private String actualToURL;
	
	public Redirection(int ID, int responseCode, Platform platform, String fromURL, String toURL) {
		super();
		this.ID = ID;
		this.responseCode = responseCode;
		this.platform = platform;
		this.fromURL = fromURL;
		this.toURL = toURL;
	}
	
	public String serialize(){
		
		return "Redirection [ID=" + ID + ", fromURL=" + IOUtils.toUTF8String(fromURL) + ", toURL=" + IOUtils.toUTF8String(toURL) + ", actualToURL=" + IOUtils.toUTF8String(actualToURL)
				+ ", responseCode=" + responseCode + ", platform=" + platform + "]";
	}
	
	public static Redirection deseriablize(final String str){
		
		String[] strs = str.split(",");
		int ID = Integer.parseInt(strs[0].substring(strs[0].indexOf("ID=") + "ID=".length()));
		String fromURL = strs[1].substring(strs[1].indexOf("fromURL=") + "fromURL=".length());
		String toURL = strs[2].substring(strs[2].indexOf("toURL=") + "toURL=".length());
		String actualToURL = strs[3].substring(strs[3].indexOf("actualToURL=") + "actualToURL=".length());
		int responseCode = Integer.parseInt(strs[4].substring(strs[4].indexOf("responseCode=") + "responseCode=".length()));
		Platform platform =  Platform.valueOf(strs[5].substring(strs[5].indexOf("platform=") + "platform=".length(), strs[5].indexOf("]")));
		Redirection ins =  new Redirection(ID, responseCode, platform, fromURL, toURL);
		ins.setActualToURL(actualToURL);
		
		return ins;
	}
	
	public String getFromURL() {
		return fromURL;
	}
	
	public void setFromURL(String fromURL) {
		this.fromURL = fromURL;
	}
	
	public String getToURL() {
		return toURL;
	}
	
	public void setToURL(String toURL) {
		this.toURL = toURL;
	}
	
	public static Platform currentPlatform = Platform.LIBRIS;
	
	public static int id = 0;
	public static List<String> bulkMapExcelRowToRedirectionObject(final String sheetName) throws Exception {

		List<String> redirections = Lists.newArrayList();
		ExcelUtils util = new ExcelUtils(Constant.USRDIR + Constant.PATH_TESTDATA + Constant.FILE_REDIRECTION_URL, sheetName);
		List<List<String>> redirectionList = util.getAllRows();
		
		redirections = FluentIterable.from(redirectionList).transform(new Function<List<String>, String>() {
			@Override
			public String apply(final List<String> row) {

				if(row.get(0).toLowerCase().contains("libris")){
					currentPlatform = Platform.LIBRIS;
					return null;
				}else if(row.get(0).toLowerCase().contains("ciab")){
					currentPlatform = Platform.CIAB;
					return null;
				}else{
					final String fromURL = row.get(0);
					
					if(Checker.isBlank(fromURL)){
						return null;
					}
					
					final int responseCode = ((Double)MathUtils.extractDouble(row.get(1))).intValue();
					final String toURL = row.get(2);
					id ++;
					return new Redirection( id, responseCode, currentPlatform, fromURL, toURL).serialize();
					
				}
			}
		}).filter(Predicates.notNull()).toList();

		return redirections;
	}

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public static int getId() {
		return id;
	}

	public static void setId(int id) {
		Redirection.id = id;
	}

	public String getActualToURL() {
		return actualToURL;
	}

	public void setActualToURL(String actualToURL) {
		this.actualToURL = actualToURL;
	}

	public static Platform getCurrentPlatform() {
		return currentPlatform;
	}

	public static void setCurrentPlatform(Platform currentPlatform) {
		Redirection.currentPlatform = currentPlatform;
	}

	@Override
	public String toString() {
		
		return this.serialize();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ID;
		result = prime * result + ((actualToURL == null) ? 0 : actualToURL.hashCode());
		result = prime * result + ((fromURL == null) ? 0 : fromURL.hashCode());
		result = prime * result + ((toURL == null) ? 0 : toURL.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Redirection)) {
			return false;
		}
		Redirection other = (Redirection) obj;
		if (ID != other.ID) {
			return false;
		}
		if (actualToURL == null) {
			if (other.actualToURL != null) {
				return false;
			}
		} else if (!actualToURL.equals(other.actualToURL)) {
			return false;
		}
		if (fromURL == null) {
			if (other.fromURL != null) {
				return false;
			}
		} else if (!fromURL.equals(other.fromURL)) {
			return false;
		}
		if (toURL == null) {
			if (other.toURL != null) {
				return false;
			}
		} else if (!toURL.equals(other.toURL)) {
			return false;
		}
		return true;
	}
}
