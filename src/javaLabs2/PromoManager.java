package javaLabs2;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Connection;
import java.util.ArrayList;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.PreparedStatement;

public class PromoManager implements ManagePromo {
	final private static Logger logger = Logger.getLogger(PromoManager.class.getName());
	
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
	
	// function for generating promos
	public static ArrayList<Promo> createPromo(){										
		String promoCodes[] = {"PISO PIZZA", "PISO CAKE", "PISO PASTA", 
					     	   "PISO FRIES", "PISO ICECREAM"};	
		String details[] = {"Get a 12 inch pizza for only 1 peso!", 
						    "Get a vanilla dream cake for only 1 peso!", 
						    "Get a bowl of oriecchiette for only 1 peso!", 
						    "Get a bucket of fries for only 1 peso!", 
						    "Get a tub of vanilla ice cream for only 1 peso!"};
		String shortCodes[] = {"1234", "5678", "4321", "8765", "9009"};
		
		Timestamp start = Timestamp.valueOf("2021-02-01 10:00:00");
		ArrayList<Promo> promos = new ArrayList<>();	
		
		for(int ctr = 0; ctr < 3; ctr++) {
			Promo promo = new Promo();				
			
			// nextInt is normally exclusive of the top value,
			// so add 1 to make it inclusive
//				int randNum = ThreadLocalRandom.current().nextInt(0, 5);
			
			promo.setPromoCode(promoCodes[ctr]);
			promo.setDetails(details[ctr]);
			promo.setShortCode(shortCodes[ctr]);
			promo.setStartDate(start);
			promo.setEndDate(genDate(ctr));
			
			promos.add(promo);
		}
		return promos;		
	}
	
	@Override
	public void insertPromo(Connection con){    
		ArrayList<Promo> createdPromos = new ArrayList<>();
		
		// create promos
		createdPromos.addAll(createPromo());
		
        String query = "INSERT INTO promos "
        			 + "(promoCode, "
        			 + "details, "
        			 + "shortCode, "
        			 + "startDate, "
        			 + "endDate) VALUES (?,?,?,?,?)";
        
	    try {
	        for(Promo entry : createdPromos) {
	        	PreparedStatement ps = con.prepareStatement(query);
	        	
	            ps.setString(1, entry.getPromoCode());
	            ps.setString(2, entry.getDetails());
	            ps.setString(3, entry.getShortCode());
	            ps.setTimestamp(4, entry.getStartDate());
	            ps.setTimestamp(5, entry.getEndDate());	 	            	            
	            
	            ps.execute();
//	            logger.log(Level.INFO, "\n" + 
//        					   		   "Inserted : {0}\n", entry.getEndDate());
	        }
	    }
	    
	    catch (SQLException e) {
            logger.log(Level.SEVERE, "SQLException", e);
        }
	    
	    finally {
	    	logger.info("\nDONE INSERTING PROMOS! \n");
        }	    	    
    } 
	
	@Override
	public ArrayList<Promo> retrievePromos(String stringValue, Connection con) {		
		String selectQuery = "";
		
		if(stringValue.contains("PISO")) {
			selectQuery = "SELECT * FROM sms_db.promos \r\n"
		   		   	    + "WHERE promoCode = ?";
		}
		else {
			selectQuery = "SELECT * FROM sms_db.promos \r\n"
	   		   	    	+ "WHERE shortCode = ?";
		}
							
		Promo promo = new Promo();
		ResultSet resultSet = null;
		ArrayList<String> result = new ArrayList<>();
		ArrayList<Promo> promos = new ArrayList<>();
		
		try {
			PreparedStatement ps = con.prepareStatement(selectQuery);
			ps.setString(1, stringValue);
			
			resultSet = ps.executeQuery();

            while(resultSet.next()){
//                logger.log(Level.INFO, resultSet.getString(1) + " : " + resultSet.getString(2));
                result.add(resultSet.getString(1) 
                		+ " : promo code: " + resultSet.getString(2)
                		+ "\ndetails: " + resultSet.getString(3)
                		+ "\nshort code: " + resultSet.getString(4)
                		+ "\nstart date: " + resultSet.getString(5)
                		+ "\nend date: " + resultSet.getString(6) + "\n\n");
                
                promo.setPromoCode(resultSet.getString(2));
                promo.setDetails(resultSet.getString(3));
                promo.setShortCode(resultSet.getString(4));
                promo.setStartDate(Timestamp.valueOf(resultSet.getString(5)));
                promo.setEndDate(Timestamp.valueOf(resultSet.getString(6)));
                
                promos.add(promo);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQLException", e);
        }

//        logger.log(Level.INFO, "\n Promo available! \n");
        
        return promos;
	}

	@Override
	public ArrayList<Promo> retrievePromos(String message, String shortCode, Connection con) {
		String selectQuery = "SELECT DISTINCT promoCode, details, startDate, endDate FROM sms_db.promos \r\n"
			   		   	   + "WHERE promoCode = ? AND shortCode = ?";
		
		Promo promo = new Promo();
		ResultSet resultSet = null;
		ArrayList<String> result = new ArrayList<>();
		ArrayList<Promo> promos = new ArrayList<>();
		
		try {
			PreparedStatement ps = con.prepareStatement(selectQuery);
			ps.setString(1, message);
			ps.setString(2, shortCode);
			
			resultSet = ps.executeQuery();

            while(resultSet.next()){
//                logger.log(Level.INFO, resultSet.getString(1) + " : " + resultSet.getString(2));
                result.add("\npromo code: " + resultSet.getString(1) + 
                		   "\npromo details: " + resultSet.getString(2) +
                		   "\npromo start date: " + resultSet.getString(3) + 
                		   "\npromo end date: " + resultSet.getString(4) + "\n\n");
                
                promo.setPromoCode(resultSet.getString(1));
                promo.setDetails(resultSet.getString(2));
                promo.setStartDate(Timestamp.valueOf(resultSet.getString(3)));
                promo.setEndDate(Timestamp.valueOf(resultSet.getString(4)));
                
                promos.add(promo);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQLException", e);
        }
		logger.log(Level.INFO, "\nResults: {0}\n", result);
		return promos;
	}
}
