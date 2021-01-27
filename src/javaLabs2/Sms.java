package javaLabs2;

import java.sql.Timestamp;

public class Sms {
	private String msisdn;
	private String recipient;
	private String sender;
	private String message;
	private String shortCode;
	private int transactionId;
	private Timestamp timeStamp;
	private String type;
	private String status;
	
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
	
	public String getShortCode() {
		return shortCode;
	}
	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}
	
	public Timestamp getTimestamp() {
		return timeStamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timeStamp = timestamp;
	}
	
	public int getTransactionId() {
		return transactionId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
