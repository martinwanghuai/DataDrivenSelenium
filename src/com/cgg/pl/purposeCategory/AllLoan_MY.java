package com.cgg.pl.purposeCategory;

import com.cgg.model.PLAPIJsonObject;
import com.cgg.pl.category.PersonalLoan;
import com.cgg.pl.category.PersonalLoan_MY;

public class AllLoan_MY extends PersonalLoan_MY {

	protected static final long serialVersionUID = 1L;
	
	public AllLoan_MY(){
		
		super();
	}

	public AllLoan_MY(PersonalLoan obj){
		
		super(obj);
	}

	public AllLoan_MY(PLAPIJsonObject obj){
		
		super(obj);
	}

	@Override
	public String toString(){
		
		return super.toString();
	}
}
