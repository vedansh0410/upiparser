package com.example.upiparser.payload;

public class ApiResponse<T>{
	private boolean success;
	private String message;
	private T data;
	public ApiResponse(boolean success, String message, T data) {
		super();
		this.success = success;
		this.message = message;
		this.data = data;
	}
	public boolean isSuccess() {
		return success;
	}
	
	public String getMessage() {
		return message;
	}
	
	public T getData() {
		return data;
	}
	
}