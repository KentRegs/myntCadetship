package javaLabs2;

import java.sql.Timestamp;
import java.sql.Connection;
import java.util.ArrayList;

public interface ManageSms {	
	public void insertSms(ArrayList<Sms> smsList, Connection con);
	public void acquireSms(Timestamp start, Timestamp end, Connection con);
	// stringValue may be a promo code, msisdn, or message
	public void acquireSms(String stringValue);
	// sms received by the system
	public void acquireSms(ArrayList<Sms> smsList);
	// sms sent by the system
	public void sentSms();
	public void acquireSms(String...msisdn);	
}
