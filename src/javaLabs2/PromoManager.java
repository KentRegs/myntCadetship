package javaLabs2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
                result.add("\npromo code: " + resultSet.getString(1) 
                		+ "\ndetails: " + resultSet.getString(2)
                		+ "\nshort code: " + resultSet.getString(3)
                		+ "\nstart date: " + resultSet.getString(4)
                		+ "\nend date: " + resultSet.getString(5) + "\n\n");
                
                promo.setPromoCode(resultSet.getString(1));
                promo.setDetails(resultSet.getString(2));
                promo.setShortCode(resultSet.getString(3));
                promo.setStartDate(Timestamp.valueOf(resultSet.getString(4)));
                promo.setEndDate(Timestamp.valueOf(resultSet.getString(5)));
                
                promos.add(promo);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQLException", e);
        }

        logger.log(Level.INFO, "\nRetrieved promos:\n{0}\n", result);
        
        return promos;
	}
}
