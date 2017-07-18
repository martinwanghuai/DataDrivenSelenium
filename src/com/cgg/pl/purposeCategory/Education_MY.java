package com.cgg.pl.purposeCategory;

import com.cgg.model.PLAPIJsonObject;
import com.cgg.pl.category.PersonalLoan;
import com.cgg.pl.category.PersonalLoan_MY;

public class Education_MY extends PersonalLoan_MY {

	protected static final long serialVersionUID = 1L;
	
	public Education_MY(){
		
		super();
	}

	public Education_MY(PersonalLoan obj){
		
		super(obj);
	}
	
	public Education_MY(PLAPIJsonObject obj){
		
		super(obj);
	}

	@Override
	public String toString(){
		
		return super.toString();
	}
}
