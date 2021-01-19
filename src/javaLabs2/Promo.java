package javaLabs2;

import java.sql.Date;

public class Promo {
	private String promoCode;
	private String details;
	private int shortCode;
	private Date startDate;
	private Date endDate;
	
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
	
	public int getShortCode() {
		return shortCode;
	}
	public void setShortCode(int shortCode) {
		this.shortCode = shortCode;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
