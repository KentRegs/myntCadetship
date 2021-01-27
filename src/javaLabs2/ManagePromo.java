package javaLabs2;

import java.sql.Connection;
import java.util.ArrayList;

public interface ManagePromo {
	// retrieves promo/s given an sms message or sms shortcode
	public ArrayList<Promo> retrievePromos(String stringValue, Connection con);
	// retrieves promo details, start date, and end date given sms message 
	// and sms shortcode
	public ArrayList<String> retrievePromos(String message, String shortCode, Connection con);
}
