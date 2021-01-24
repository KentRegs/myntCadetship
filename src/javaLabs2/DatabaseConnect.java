package javaLabs2;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnect {
    final private static Logger logger = Logger.getLogger(DatabaseConnect.class.getName());    
    static ArrayList<Promo> promos = new ArrayList<>();
    private static Connection con = null;

    public static void main(String[] args){
    	promos = Main.createPromo();
    	
        DatabaseConnect.connect();
        DatabaseConnect.insertPromo(promos);
//        ArrayList<String> data = DatabaseConnect.retrievePromos();
        DatabaseConnect.disconnect();
    }

    public static void connect() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/sms?useTimezone=true&serverTimezone=UTC","root","pw");
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
        String query = "INSERT INTO user_payment "
        			 + "(promoCode, "
        			 + "details, "
        			 + "shortCode, "
        			 + "startDate"
        			 + "endDate) VALUES (?,?,?,?,?)";
        
	    try(PreparedStatement ps = con.prepareStatement(query)) {
	        for(Promo entry : promos) {
	            ps.setString(1, entry.getPromoCode());
	            ps.setString(2, entry.getDetails());
	            ps.setString(3, entry.getShortCode());
	            ps.setTimestamp(4, entry.getStartDate());
	            ps.setTimestamp(5, entry.getEndDate());	            
	        }
	        
	        ps.execute();

	        logger.log(Level.INFO, "Inserted : {0}", promos);
	    }
	    
	    catch (SQLException e) {
            logger.log(Level.SEVERE, "SQLException", e);
        }
	    
	    finally {
            try {
                if (con != null) {
                	con.close();
                }
            }
            catch (Exception e){
                logger.log(Level.SEVERE, "ERROR IN CLOSING", e);
            }
        }	    	    
    }
    
    public static void insertSMS(ArrayList<Integer> smsList){
//        String insertQuery = "Insert into datatable (data) values ('" + data + "')";
//        Statement statement = null;
//        int result= 0;
//
//        try {
//            statement = con.createStatement();
//            result = statement.executeUpdate(insertQuery);
//        } catch (SQLException e) {
//            logger.log(Level.SEVERE, "SQLException", e);
//        }finally {
//            try {
//                if (statement != null) {
//                    statement.close();
//                }
//            }catch (Exception e){
//                logger.log(Level.SEVERE, "ERROR IN CLOSING", e);
//            }
//        }               
//
//        logger.log(Level.INFO, "Inserted : {0}", result);
    }

    public static ArrayList<String> retrievePromos(){
        String selectQuery = "SELECT * FROM datatable";
        Statement statement = null;
        ResultSet resultSet = null;
        ArrayList<String> result = new ArrayList<>();

        try {
            statement = con.createStatement();
            resultSet = statement.executeQuery(selectQuery);

            while(resultSet.next()){
                logger.log(Level.INFO, resultSet.getString(1) + " : " + resultSet.getString(2));
                result.add(resultSet.getString(1) + " : " + resultSet.getString(2));
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

        logger.log(Level.INFO, "Retrieved : {0}", result);
        return result;
    }
}
