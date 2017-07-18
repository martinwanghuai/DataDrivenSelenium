package utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import pageObjects.CommonFindObjects;


public class IOUtils {
	
	private static SimpleDateFormat formatter = new SimpleDateFormat(Constant.DATE_FORMAT); 
	
	public static String dateToString(final Date date) {

		return formatter.format(date);
	}

	
	public static void deleteFile(final String filePath){
		
		try {
		    new File(filePath).delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void mkParentDirs(File destFile){
		File dir = destFile.getParentFile();
		if(!dir.exists()){
			dir.mkdirs();
		}
	}
	
	public synchronized static void copyFile(File srcFile, File destFile){
		IOUtils.mkParentDirs(destFile);
		
		try {
			FileUtils.copyFile(srcFile, destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean exists(String file_str){
		
		return new File(file_str).exists();
	}
	
	public static String loadFile(String file_str){
		
		StringBuilder sb = new StringBuilder();
		List<String> strs = loadExcelFile(file_str);
		for(String str: strs){
			sb.append(str);
		}
		return sb.toString();
	}
	
	public static List<String> loadExcelFile(String file_str){
		
		List<String> results = Lists.newArrayList();
		
		if(!exists(file_str)){
			return results;
		}
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file_str), "UTF-8"));
			
			String str = "";
			while((str = br.readLine())!= null){
					results.add(str);	
			}
			
			br.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	public synchronized static void saveIntoFile(String str, String destFile_str){
		File destFile = new File(destFile_str);
		IOUtils.mkParentDirs(destFile);
		
		try {
			Writer bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(destFile_str), "UTF-8"));
			bw.write(str);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String toUTF8String(final String urlStr){

		String result = null;
		try {
			if(!Checker.isBlank(urlStr)){
				String data = urlStr.toString();
				data = data.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
				data = data.replaceAll("\\+", "%2B");
				result = URLDecoder.decode(data, "utf-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		
		return result;
	}
	
	public static String convertJsonObjectToString(final Object obj){
		
		Gson gson = new Gson();
		return gson.toJson(obj);
	}
	
	public static Object loadJsonObjects(final String jsonStr, final Class clz){
		
		Gson gson = new Gson();
		return gson.fromJson(jsonStr, clz);
	}
	
	public static Object loadJsonObjects(final String jsonStr, final Class clz, JsonDeserializer marshaller){
		
		Object result = null;
		try{
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.registerTypeAdapter(clz, marshaller);
			Gson gson = gsonBuilder.create();
			result = gson.fromJson(jsonStr, clz);
			
		}catch(Exception e){
			e.printStackTrace();
			String msg = "Json String: " + jsonStr;
			System.out.println(msg);
			CommonFindObjects.loggerMethodFailure(msg);
		}
		return result;
	}
}
