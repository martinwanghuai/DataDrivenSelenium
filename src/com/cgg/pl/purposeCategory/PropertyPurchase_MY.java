package com.cgg.pl.purposeCategory;

import com.cgg.model.PLAPIJsonObject;
import com.cgg.pl.category.PersonalLoan;
import com.cgg.pl.category.PersonalLoan_MY;

public class PropertyPurchase_MY extends PersonalLoan_MY {

	protected static final long serialVersionUID = 1L;
	
	public PropertyPurchase_MY(){
		
		super();
	}

	public PropertyPurchase_MY(PersonalLoan obj){
		
		super(obj);
	}
	
	public PropertyPurchase_MY(PLAPIJsonObject obj){
		
		super(obj);
	}

	@Override
	public String toString(){
		
		return super.toString();
	}
	
}
