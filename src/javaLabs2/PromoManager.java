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
	public ArrayList<String> retrievePromos(String message, String shortCode, Connection con) {
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
		return result;
	}
}
