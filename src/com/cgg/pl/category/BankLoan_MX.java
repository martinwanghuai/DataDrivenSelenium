package com.cgg.pl.category;

import com.cgg.model.PLAPIJsonObject;

public class BankLoan_MX extends PersonalInstalment_MX {
	
	protected static final long serialVersionUID = 1L;

	public BankLoan_MX(){
		
		super();
	}

	public BankLoan_MX(PLAPIJsonObject obj){
		
		super(obj);
	}
	
	public BankLoan_MX(PersonalLoan obj){
		
		super(obj);
	}
	
	@Override
	public String toString(){
		
		return super.toString();
	}
}
