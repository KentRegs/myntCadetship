package javaLabs2;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnect {
    final private static Logger logger = Logger.getLogger(DatabaseConnect.class.getName());
    static Timestamp start = Timestamp.valueOf("2021-02-01 10:00:00");
    static Timestamp end = Timestamp.valueOf("2021-03-30 23:59:00");
    static ArrayList<Promo> promoList = new ArrayList<>();
    static ArrayList<Sms> smsList = new ArrayList<>();    
    static SmsManager smsMngr = new SmsManager();
    private static Connection con = null;
    
    public static void main(String[] args){
    	promoList.addAll(Main.createPromo());    	
    	
        DatabaseConnect.connect();
//        insertPromo(promoList);
//        smsList.addAll(Main.smsChecker(con));        
//        smsMngr.insertSms(smsList, con);
//        smsMngr.acquireSms(start, end, con);
//        smsMngr.acquireSms("403594942", con);
//        smsMngr.acquireSms(con);
//        smsMngr.acquireSms(con, "", "", "");
//        DatabaseConnect.retrievePromos();
        DatabaseConnect.disconnect();
    }

    public static void connect() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // enables timezones and sets it to Universal Time (UTC)
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms_db?useTimezone=true&serverTimezone=UTC","root","p4ssw0rd*");
            logger.info("Connected");
        }
        catch(Exception e){
            logger.log(Level.SEVERE, "Not Connected", e);
        }
    }

    public static void disconnect() {
        try{
            if (con != null){
                con.close();
                logger.info("Disonnected");
            }
        }catch(Exception e){
            logger.log(Level.SEVERE, "Not Connected", e);
        }
    }

    public static void insertPromo(ArrayList<Promo> promos){        
        String query = "INSERT INTO promos "
        			 + "(promoCode, "
        			 + "details, "
        			 + "shortCode, "
        			 + "startDate, "
        			 + "endDate) VALUES (?,?,?,?,?)";
        
	    try {
	        for(Promo entry : promos) {
	        	PreparedStatement ps = con.prepareStatement(query);
	            ps.setString(1, entry.getPromoCode());
	            ps.setString(2, entry.getDetails());
	            ps.setString(3, entry.getShortCode());
	            ps.setTimestamp(4, entry.getStartDate());
	            ps.setTimestamp(5, entry.getEndDate());	 	            	            
	            
	            ps.execute();
	            logger.log(Level.INFO, "\n" + 
        					   		   "Inserted : {0}\n", entry.getEndDate());
	        }
	    }
	    
	    catch (SQLException e) {
            logger.log(Level.SEVERE, "SQLException", e);
        }
	    
	    finally {
//            try {
//                if (con != null) {
//                	con.close();
//                }
//            }
//            catch (Exception e){
//                logger.log(Level.SEVERE, "ERROR IN CLOSING", e);
//            }
	    	logger.info("\nDONE INSERTING PROMOS! \n");
        }	    	    
    }       

    public static ArrayList<String> retrievePromos(){
        String selectQuery = "SELECT * FROM promos";
        Statement statement = null;
        ResultSet resultSet = null;
        ArrayList<String> result = new ArrayList<>();

        try {
            statement = con.createStatement();
            resultSet = statement.executeQuery(selectQuery);

            while(resultSet.next()){
//                logger.log(Level.INFO, resultSet.getString(1) + " : " + resultSet.getString(2));
                result.add(resultSet.getString(1) 
                		+ " : " + resultSet.getString(2)
                		+ "\n     " + resultSet.getString(3)
                		+ "\n     " + resultSet.getString(4)
                		+ "\n     " + resultSet.getString(5) + "\n\n");
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
