package com.kanke.search.exception;

public class StoreFieldIdNullRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8638614427078524870L;
	
	
	private Class<?> clazz;
	
	public StoreFieldIdNullRuntimeException(Class<?> clazz) {
		this.clazz = clazz;
	}

	@Override
	public String getMessage() {
		return this.clazz.getName()+" StoreFieldId can't null";
	}
	

}
