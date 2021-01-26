package javaLabs2;

<<<<<<< HEAD
import java.sql.Timestamp;
import java.util.ArrayList;

public interface ManageSms {	
	public void insertSms(ArrayList<Sms> smsList);
	public void acquireSms(Timestamp start, Timestamp end);
	// stringValue may be a promo code, msisdn, or message
	public void acquireSms(String stringValue);
	// sms received by the system
	public void acquireSms(ArrayList<Sms> smsList);
	// sms sent by the system
	public void sentSms();
	public void acquireSms(String...msisdn);	
=======
import java.sql.Connection;
import java.util.ArrayList;

public interface ManageSms {
	public void insertSms(ArrayList<Sms> smsList, Connection con);
	public void acquireSms();	
>>>>>>> eef8f33c207f090591aa8ecb0ee85539ffd5e87f
}
