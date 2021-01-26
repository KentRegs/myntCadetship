package javaLabs2;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SmsManager implements ManageSms {
	final private static Logger logger = Logger.getLogger(DatabaseConnect.class.getName());
	static ArrayList<Sms> smsList = new ArrayList<>();
	
	@Override
	public void insertSms(ArrayList<Sms> smsList, Connection con) {
		String query = "INSERT INTO sms "
					 + "(msisdn, "
					 + "recipient, "
					 + "sender, "
					 + "message, "
					 + "shortCode, "
					 + "transactionId, "
					 + "timeStamp) VALUES (?,?,?,?,?,?,?)";

		ResultSet resultSet = null;
        ArrayList<String> result = new ArrayList<>();
		
		try {
			for(Sms entry : smsList) {
				PreparedStatement ps = con.prepareStatement(query);
					
					while(resultSet.next()){
		            	result.add("\nidSMS: " + resultSet.getInt(1) 
		                		+ "\nmsisdn: " + resultSet.getString(2)
		                		+ "\nrecipient: " + resultSet.getString(3)
		                		+ "\nsender: " + resultSet.getString(4)
		                		+ "\nmessage: " + resultSet.getString(5)
		                		+ "\nshortcode: " + resultSet.getString(6)
		                		+ "\ntransaction id: " + resultSet.getInt(7)
		                		+ "\ntimestamp: " + resultSet.getTimestamp(8) + "\n\n");
		            }
					ps.setString(1, entry.getMsisdn());
				    ps.setString(2, entry.getRecipient());
				    ps.setString(3, entry.getSender());
				    ps.setString(4, entry.getMessage());
				    ps.setString(5, entry.getShortCode());
				    ps.setInt(6, entry.getTransactionId());
				    ps.setTimestamp(7, entry.getTimestamp());
				   
				    ps.execute();
				    logger.log(Level.INFO, "\n" + 
			   					   		   "Inserted : {0}\n", entry.getMsisdn());
			}
			
			logger.log(Level.INFO, "\n\n>> DONE INSERTING SMS! <<\n\n");
			
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "SQLException", e);
		} finally {
//			try {
//			   if (con != null) {
//				   logger.info("\nDONE INSERTING SMS! \n");
//				   con.close();
//			   }
//			} catch (Exception e){
//				logger.log(Level.SEVERE, "ERROR IN CLOSING", e);
//			}				
		}	 
	}

	@Override
	public void acquireSms(Timestamp start, Timestamp end, Connection con) {
		String selectQuery = "select * from sms_db.sms	\r\n"
						   + "WHERE timeStamp BETWEEN ? AND ?";		
		
        ResultSet resultSet = null;
        ArrayList<String> result = new ArrayList<>();

        try {
        	PreparedStatement ps = con.prepareStatement(selectQuery);
    		ps.setTimestamp(1, start);
    		ps.setTimestamp(2, end);
    		
    		resultSet = ps.executeQuery();
//            statement = con.createStatement();
//            resultSet = statement.executeQuery(selectQuery);

            while(resultSet.next()){
            	result.add("\nidSMS: " + resultSet.getInt(1) 
                		+ "\nmsisdn: " + resultSet.getString(2)
                		+ "\nrecipient: " + resultSet.getString(3)
                		+ "\nsender: " + resultSet.getString(4)
                		+ "\nmessage: " + resultSet.getString(5)
                		+ "\nshortcode: " + resultSet.getString(6)
                		+ "\ntransaction id: " + resultSet.getInt(7)
                		+ "\ntimestamp: " + resultSet.getTimestamp(8) + "\n\n");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQLException", e);
        }

        logger.log(Level.INFO, "\nRetrieved promos:\n{0}\n", result);        
	}

	@Override
	public void acquireSms(String stringValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void acquireSms(ArrayList<Sms> smsList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sentSms() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void acquireSms(String... msisdn) {
		// TODO Auto-generated method stub
		
	}
	
}
