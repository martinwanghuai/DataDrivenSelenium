package com.cgg.pl.purposeCategory;

import com.cgg.model.PLAPIJsonObject;
import com.cgg.pl.category.PersonalLoan;
import com.cgg.pl.category.PersonalLoan_MY;

public class SomethingElse_MY extends PersonalLoan_MY {

	protected static final long serialVersionUID = 1L;
	
	public SomethingElse_MY(){
		
		super();
	}

	public SomethingElse_MY(PersonalLoan obj){
		
		super(obj);
	}
	
	public SomethingElse_MY(PLAPIJsonObject obj){
		
		super(obj);
	}

	@Override
	public String toString(){
		
		return super.toString();
	}
}
