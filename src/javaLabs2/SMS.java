package javaLabs2;

import java.sql.Timestamp;

public class SMS {
	private String msisdn;
	private String recipient;
	private String sender;
	private String message;
	private int shortCode;
	private int transactionId;
	private Timestamp timestamp;
	
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public int getShortCode() {
		return shortCode;
	}
	public void setShortCode(int shortCode) {
		this.shortCode = shortCode;
	}
	
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
	public int getTransactionId() {
		return transactionId;
	}
}
