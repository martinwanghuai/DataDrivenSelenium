package toy;

import java.util.List;

import com.google.common.collect.Lists;

public class PatternValidator {

	public void validatePattern(){
		
		final List<String> strList = Lists.newArrayList("404.0","0.404", "aa.404", "404.aa", "4004","404");
		final String pattern = "^404$";
		
		for(final String str: strList){
			System.out.println(str.matches(pattern));
		}
	}
	
	public static void main(String[] args) {

		PatternValidator ins = new PatternValidator();
		ins.validatePattern();
	}

}
