package javaLabs2;

import java.sql.Date;
import java.sql.Timestamp;

public class Promo {
	private String promoCode;
	private String details;
	private String shortCode;
	private Timestamp startDate;
	private Timestamp endDate;
	
	public String getPromoCode() {
		return promoCode;
	}
	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}
	
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	
	public String getShortCode() {
		return shortCode;
	}
	public void setShortCode(String string) {
		this.shortCode = string;
	}
	
	public Timestamp getStartDate() {
		return startDate;
	}
	public void setStartDate(Timestamp start) {
		this.startDate = start;
	}
	
	public Timestamp getEndDate() {
		return endDate;
	}
	public void setEndDate(Timestamp end) {
		this.endDate = end;
	}
}
