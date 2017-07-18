package com.cgg.pl.purposeCategory;

import com.cgg.model.PLAPIJsonObject;
import com.cgg.pl.category.PersonalLoan;
import com.cgg.pl.category.PersonalLoan_MY;

public class MortgageRepayment_MY extends PersonalLoan_MY {

	protected static final long serialVersionUID = 1L;
	
	public MortgageRepayment_MY(){
		
		super();
	}

	public MortgageRepayment_MY(PersonalLoan obj){
		
		super(obj);
	}
	
	public MortgageRepayment_MY(PLAPIJsonObject obj){
		
		super(obj);
	}

	@Override
	public String toString(){
		
		return super.toString();
	}
}
