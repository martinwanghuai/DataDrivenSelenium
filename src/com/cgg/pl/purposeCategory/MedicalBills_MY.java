package com.cgg.pl.purposeCategory;

import com.cgg.model.PLAPIJsonObject;
import com.cgg.pl.category.PersonalLoan;
import com.cgg.pl.category.PersonalLoan_MY;

public class MedicalBills_MY extends PersonalLoan_MY {

	protected static final long serialVersionUID = 1L;
	
	public MedicalBills_MY(){
		
		super();
	}

	public MedicalBills_MY(PersonalLoan obj){
		
		super(obj);
	}
	
	public MedicalBills_MY(PLAPIJsonObject obj){
		
		super(obj);
	}

	@Override
	public String toString(){
		
		return super.toString();
	}

}
