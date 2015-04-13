package com.vladkel.help.parebrise.ws.model.error;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "error")
@XmlType(propOrder = { "exceptionName", "exceptionMessage", "exceptionStackTrace" })
public class XMLError {
	
	private String exceptionName;
	
	private String exceptionMessage;
	
	private String exceptionStackTrace;
	
	
	public XMLError(){
		super();
	}
	
	public XMLError(Exception exception){
		setExceptionName(exception.getClass().getSimpleName());
		setExceptionMessage(exception.getMessage());
		setExceptionStackTrace(exception.getStackTrace().toString());
	}

	@XmlElement
	public String getExceptionName(){
		return exceptionName;
	}

	public void setExceptionName(String exceptionName) {
		this.exceptionName = exceptionName;
	}
	
	@XmlElement
	public String getExceptionMessage(){
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}
	
	@XmlElement
	public String getExceptionStackTrace(){
		return exceptionStackTrace;
	}

	public void setExceptionStackTrace(String exceptionStackTrace) {
		this.exceptionStackTrace = exceptionStackTrace;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Error type : " + getExceptionName() + "\n");
		sb.append("\tMessage : " + getExceptionMessage() + "\n");
		sb.append("\tStacktrace : " + getExceptionStackTrace() + "\n");
		return sb.toString();
	}
	
}
