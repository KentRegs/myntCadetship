package javaLabs2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PromoManager implements ManagePromo {
	final private static Logger logger = Logger.getLogger(PromoManager.class.getName());
	
	@Override
	public ArrayList<String> retrievePromos(String stringValue, Connection con) {		
String selectQuery = "";
		
		if(stringValue.contains("PISO")) {
			selectQuery = "SELECT * FROM sms_db.promo \r\n"
		   		   	    + "WHERE promoCode = ?";
		}
		else {
			selectQuery = "SELECT * FROM sms_db.promo \r\n"
	   		   	    	+ "WHERE promoCode = ?";
		}
							
        Statement statement = null;
        ResultSet resultSet = null;
        ArrayList<String> result = new ArrayList<>();

        try {
            statement = con.createStatement();
            resultSet = statement.executeQuery(selectQuery);

            while(resultSet.next()){
//                logger.log(Level.INFO, resultSet.getString(1) + " : " + resultSet.getString(2));
                result.add("\npromo code: " + resultSet.getString(1) 
                		+ "\ndetails: " + resultSet.getString(2)
                		+ "\nshort code: " + resultSet.getString(3)
                		+ "\nstart date: " + resultSet.getTimestamp(4)
                		+ "\nend date: " + resultSet.getTimestamp(5) + "\n\n");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQLException", e);
        }finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (resultSet != null) {
                    statement.close();
                }
            }catch (Exception e){
                logger.log(Level.SEVERE, "ERROR IN CLOSING", e);
            }
        }

        logger.log(Level.INFO, "\nRetrieved promos:\n{0}\n", result);
        return result;
	}
}
