package javaLabs2;

import java.sql.Timestamp;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Random;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.PreparedStatement;

public class SmsManager implements ManageSms {
	final private static Logger logger = Logger.getLogger(DatabaseConnect.class.getName());
	static ArrayList<Sms> smsList = new ArrayList<>();		
	
	// function for generating random 9-digit mobile numbers (msisdn)
	public static String genMSISDN() {
		long num = new Random().nextInt(999999999);

		return String.format("%09d", num);
	}
	
	// function for generating random names
	public static String genName() {
		String[] firstName = { "Michael", "Michelle", "Donald", "Lakeith",
						       "Hiro", "Brendan", "Morgan", "Robert", "Steven", "David", "Stephen", 
						       "Harold", "William", "Matthew", "Max", 
						       "Christina", "Yna", "Bea", "Mika", "Samantha", "Yara", "Kiara",
						       "Isabelle", "Mila", "Miles"};
		String[] lastName = { " Rourke", " Smith", " Johnson", " Williams",
						      " Ong", " Davis", " Miller", " Adobo", " Wilson", " Peterson", " Ramirez", 
						      " Murai", " Stanfield", " Bourdain", " Picasso", 
						      " Ng", " Philips", " Riverson", " Abbott", " Moore", " Babbage", " Lee",
						      " White", " Gonzales", " Lopez"};
		Random randName = new Random();
		
		return firstName[randName.nextInt(firstName.length)] +
			   lastName[randName.nextInt(lastName.length)];
	}
	
	// function for generating end dates
	public static Timestamp genDate(int num) {		
		java.util.Date utilDate = new java.util.Date();
		Timestamp endDate = new Timestamp(utilDate.getTime());	    
	    
	    endDate = Timestamp.valueOf("2021-06-30 23:59:00");
	    
	    if(num == 1)
			endDate = Timestamp.valueOf("2021-07-30 23:59:00");				
		else if(num == 2)
			endDate = Timestamp.valueOf("2021-08-30 23:59:00");	
		else if(num == 3)
			endDate = Timestamp.valueOf("2021-09-30 23:59:00");
		else if(num == 4)
			endDate = Timestamp.valueOf("2021-10-30 23:59:00");
		
		return endDate;
	}
	
	// function for generating an SMS
	public ArrayList<Sms> genSMS(ArrayList<Promo> availablePromos) {
		String promoCodes[] = new String[3];
		String shortCodes[] = new String[3];
		String details[] = new String[3];
		int ctr = 0;
		
		// populate arrays with availale promos from the database
		for(Promo entry : availablePromos) {
			promoCodes[ctr] = entry.getPromoCode();
			shortCodes[ctr] = entry.getShortCode();
			details[ctr] = entry.getDetails();
			
			ctr++;
		}
		
		long offset = Timestamp.valueOf("2021-02-02 00:00:00").getTime();
		long end = Timestamp.valueOf("2021-12-30 23:59:00").getTime();
		long diff = end - offset + 1;			
		
		// PISO PIZZA SMS data population
		for(int ctr1 = 0; ctr1 < 30; ctr1++) {
			Timestamp randTimeStamp = new Timestamp(offset + (long)(Math.random() * diff));
			Sms sms = new Sms();				
			
			sms.setMsisdn(genMSISDN());
			sms.setRecipient("PISO Inc.");
			sms.setSender(genName());
			sms.setMessage("PISO PIZZA");
			sms.setShortCode("1234");
			sms.setTimestamp(randTimeStamp);
			sms.setType("User");			
			
			smsList.add(sms);
		}
		
		// SMS data population
		for(int ctr2 = 0; ctr2 < 30; ctr2++) {
			Timestamp randTimeStamp = new Timestamp(offset + (long)(Math.random() * diff));
			int end2 = promoCodes.length;
			int offset2 = 0;			
			int random = (int) ((Math.random() * (end2 - offset2)) + offset2);
			
			Sms sms = new Sms();				
			
			sms.setMsisdn(genMSISDN());
			sms.setRecipient("PISO Inc.");
			sms.setSender(genName());
			sms.setMessage(promoCodes[random]);
			sms.setShortCode(shortCodes[random]);
			sms.setTimestamp(randTimeStamp);
			sms.setType("User");	
			
			smsList.add(sms);
		}
		return smsList;
	}
	
	public ArrayList<Sms> smsChecker(ArrayList<Promo> availablePromos, Connection con) {
		ArrayList<Promo> checkerArrList = new ArrayList<>();
		ArrayList<Sms> processedSmsList = new ArrayList<>();
		PromoManager promoMngr = new PromoManager();
		Timestamp before = new Timestamp(0);
		Timestamp after = new Timestamp(0);
		int ctr = 0;
		
		// generate SMS ArrayList
		genSMS(availablePromos);
		
		for(Sms entry : smsList) {
			int chk1 = 0;
			int chk2 = 0;
			int chk3 = 0;
			int chk4 = 0;
			int chk5 = 0;						
			
			Sms sms = new Sms();						
			
			// checks if the msisdn is valid			
			if(entry.getMsisdn() != null && entry.getMsisdn().matches("[0-9]+")
		       && entry.getMsisdn().length() == 9) 
				chk1 = 1;
			
			// checks if the recipient is valid
			if(entry.getRecipient() != null && entry.getRecipient().matches("PISO Inc."))
				chk2 = 1;
			
			// checks if the sender has a valid name
			if(entry.getSender() != null && entry.getSender().matches("[a-zA-z]+[ ][a-zA-Z]+")) 
				chk3 = 1;
			
			// checks if the entered message (promo code) is valid
			checkerArrList.addAll(promoMngr.retrievePromos(entry.getMessage(), con));
		 
			ArrayList<Integer> index1 = new ArrayList<>();
			ArrayList<Integer> index2 = new ArrayList<>();
			
			// checks if the entered message (promo code) is valid
			for(Promo entry1 : checkerArrList) {
				if(entry.getMessage() != null && entry.getMessage().matches(entry1.getPromoCode()))
					index1.add(checkerArrList.indexOf(entry1));				
				
				if(entry.getShortCode() != null && entry.getShortCode().matches(entry1.getShortCode())) 
					index2.add(checkerArrList.indexOf(entry1));				
			}
			
			if(index1.equals(index2))
				chk4 = 1;
					
			// checks if timestamp is within promo period						
			before = checkerArrList.get(ctr).getStartDate();
			after = checkerArrList.get(ctr).getEndDate();
			
			if(entry.getTimestamp() != null && 
			   entry.getTimestamp().before(after)) {
				if(entry.getTimestamp().after(before))
					chk5 = 1;
			}
			
			sms.setMsisdn(entry.getMsisdn());
			sms.setRecipient(entry.getRecipient());
			sms.setSender(entry.getSender());
			sms.setMessage(entry.getMessage());
			sms.setShortCode(entry.getShortCode());
			sms.setTimestamp(entry.getTimestamp());
			sms.setType(entry.getType());
			
			if(chk1 == 1 && chk2 == 1 && chk3 == 1 &&
			   chk4 == 1 && chk5 == 1) 				
				sms.setStatus("SUCCESS");										
			
			else sms.setStatus("FAIL");
			
			processedSmsList.add(sms);			
			ctr++;
		}	
		logger.log(Level.INFO, "\nDONE PROCESSING SMS! \n\n");
		
		return processedSmsList;
	}	
	
	@Override
	public void insertSms(ArrayList<Promo> availablePromos, Connection con) {

		// checks generated SMS objects
		ArrayList<Sms> processedSmsList = new ArrayList<>();
		processedSmsList.addAll(smsChecker(availablePromos, con));
		
		String query = "INSERT INTO sms "
					 + "(msisdn, "
					 + "recipient, "
					 + "sender, "
					 + "message, "
					 + "shortCode, "
					 + "transactionId, "
					 + "timeStamp, "
					 + "type, "
					 + "status) VALUES (?,?,?,?,?,?,?,?,?)";

		try {
			for(Sms entry : processedSmsList) {
				PreparedStatement ps = con.prepareStatement(query);
				
				ps.setString(1, entry.getMsisdn());
			    ps.setString(2, entry.getRecipient());
			    ps.setString(3, entry.getSender());
			    ps.setString(4, entry.getMessage());
			    ps.setString(5, entry.getShortCode());
			    ps.setInt(6, entry.getTransactionId());
			    ps.setTimestamp(7, entry.getTimestamp());
			    ps.setString(8, entry.getType());
			    ps.setString(9, entry.getStatus());
			   
			    ps.execute();		    
			}
			
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "SQLException", e);
		} 
		
		logger.log(Level.INFO, "\nDONE INSERTING SMS! \n\n");
	}

	@Override
	public void acquireSms(Timestamp start, Timestamp end, Connection con) {
		String selectQuery = "SELECT * FROM sms_db.sms	\r\n"
						   + "WHERE timeStamp BETWEEN ? AND ?";		
		
        ResultSet resultSet = null;
        ArrayList<String> result = new ArrayList<>();

        try {
        	PreparedStatement ps = con.prepareStatement(selectQuery);
    		ps.setTimestamp(1, start);
    		ps.setTimestamp(2, end);
    		
    		resultSet = ps.executeQuery();

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

        logger.log(Level.INFO, "\nRetrieved SMS:\n{0}\n", result);        
	}

	@Override
	public void acquireSms(String stringValue, Connection con) {
		String selectQuery = "";
		
		if(stringValue.contains("PISO")) {
			selectQuery = "SELECT * FROM sms_db.sms	\r\n"
		   		   	    + "WHERE message = ?";
		}
		else {
			selectQuery = "SELECT * FROM sms_db.sms	\r\n"
	   		   	    	+ "WHERE msisdn = ?";
		}

		ResultSet resultSet = null;
		ArrayList<String> result = new ArrayList<>();
		
		try {
			PreparedStatement ps = con.prepareStatement(selectQuery);
			ps.setString(1, stringValue);
			
			resultSet = ps.executeQuery();
		
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
		
		logger.log(Level.INFO, "\nRetrieved SMS:\n{0}\n", result);
		logger.log(Level.INFO, "\n>> SAMPLE >>: (({0}))\n", result.get(8));
	}

	@Override
	public void acquireSms(Connection con) {
		String selectQuery = "SELECT * FROM sms_db.sms";		

		ResultSet resultSet = null;
		ArrayList<String> result = new ArrayList<>();
		
		try {
			PreparedStatement ps = con.prepareStatement(selectQuery);
			
			resultSet = ps.executeQuery();
		
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
		
		logger.log(Level.INFO, "\nRetrieved SMS:\n{0}\n", result);
	}

	@Override
	public void sentSms() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void acquireSms(Connection con, String... msisdn) {		
		for(String curr : msisdn) {
			String selectQuery = "SELECT * FROM sms_db.sms	\r\n"
							   + "WHERE msisdn = ?";		
	
			ResultSet resultSet = null;
			ArrayList<String> result = new ArrayList<>();
			
			try {
				PreparedStatement ps = con.prepareStatement(selectQuery);
				ps.setString(1, curr);
				
				resultSet = ps.executeQuery();
			
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
			
			logger.log(Level.INFO, "\nRetrieved SMS:\n{0}\n", result);
		}
	}
	
}
