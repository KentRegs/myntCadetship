package javaLabs2;

import java.sql.Timestamp;
import java.sql.Connection;
import java.util.ArrayList;

public interface ManageSms {	
	public ArrayList<Sms> smsChecker(ArrayList<Sms> generatedSms, Connection con);
	public void insertSms(ArrayList<Sms> verifiedSmsList, Connection con);
	public void acquireSms(Timestamp start, Timestamp end, Connection con);
	// stringValue may be a promo code or msisdn
	public void acquireSms(String stringValue, Connection con);
	// sms received by the system
	public void acquireSms(Connection con);	
	public void acquireSms(Connection con, String...msisdn);
	// list of failed transactions
	public void acquireFailSms(Connection con);
	// list of successful transactions
	public void acquireSuccessSms(Connection con);
	// list of subscribers who availed a promo
	public void acquirePersonsSms(Connection con);
	// total count of User-sent sms
	public void acquireUserSms(Connection con);
	// total count of System-sent sms
	public void acquireSystemSms(Connection con);
}
