package javaLabs2;

import java.sql.Connection;
import java.util.ArrayList;

public interface ManageSms {
	public void insertSms(ArrayList<Sms> smsList, Connection con);
	public void acquireSms();	
}
