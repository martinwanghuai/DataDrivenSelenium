package com.cgg.pl.category;

import com.cgg.model.PLAPIJsonObject;

public class DebtConsolidation_MY extends PersonalLoan_MY {

	protected static final long serialVersionUID = 1L;
	
	public DebtConsolidation_MY(){
		
		super();
	}

	public DebtConsolidation_MY(PersonalLoan obj){
		
		super(obj);
	}
	
	public DebtConsolidation_MY(PLAPIJsonObject obj){
		
		super(obj);
	}

	@Override
	public String toString(){
		
		return super.toString();
	}
}
