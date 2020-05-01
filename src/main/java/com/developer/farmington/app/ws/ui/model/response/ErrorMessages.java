package com.developer.farmington.app.ws.ui.model.response;

public enum ErrorMessages {
	
	MISSING_REQUIRED_FIELD("Some fields are missing, please check the Documentation!"),
	RECORD_NOT_FOUND("Record not found");
	
	private String message;
	
	ErrorMessages(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
