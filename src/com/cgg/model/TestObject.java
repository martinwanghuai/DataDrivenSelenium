package com.cgg.model;



public class TestObject {

	private String strExecuted;
	
	public TestObject(){
		super();
	}
	
	public TestObject(String strExecuted){
		
		this.strExecuted = strExecuted;
	}
	
	public void setStrExecuted(String strExecuted) {
		this.strExecuted = strExecuted;
	}

	public String getStrExecuted() {
		return strExecuted;
	}
	
/*	public <T extends TestObject> Predicate<T> FILTER = new Predicate<T>(){
		
		@Override
		public boolean apply(final T testObject){
			return true;
		}
	};*/
	
	/*public Predicate<T extends TestObject> FILTER_EXECUTE_FLAG = new Predicate<T extends TestObject>(){
		
		@Override
		public boolean apply(final ? testObect){
			return true;
		}
	};
*/}
