package com.karakoum.mowers.exception;

public class MowersException extends RuntimeException {
	
	private String exceptionMsg;
	 
	public MowersException(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}
	
	public String getExceptionMsg(){
		return this.exceptionMsg;
	}
	
	public void setExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}
}
