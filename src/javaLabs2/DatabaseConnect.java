package javaLabs2;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnect {

    final private static Logger logger = Logger.getLogger(DatabaseConnect.class.getName());
    private static Connection con = null;

    public static void main(String[] args){
        DatabaseConnect.connect();
//        DatabaseConnect.insertData("Sample");
        ArrayList<String> data = DatabaseConnect.retrievePromos();
        DatabaseConnect.disconnect();
    }

    public static void connect() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con= DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/sms?useTimezone=true&serverTimezone=UTC","root","Hy4kk1y4k0u*");
            logger.info("Connected");
        }catch(Exception e){
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

    public static void insertPromo(String data){
        String insertQuery = "Insert into datatable (data) values ('" + data + "')";
        Statement statement = null;
        int result= 0;

        try {
            statement = con.createStatement();
            result = statement.executeUpdate(insertQuery);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQLException", e);
        }finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            }catch (Exception e){
                logger.log(Level.SEVERE, "ERROR IN CLOSING", e);
            }
        }

        logger.log(Level.INFO, "Inserted : {0}", result);
    }
    
    public static void insertSMS(String data){
        String insertQuery = "Insert into datatable (data) values ('" + data + "')";
        Statement statement = null;
        int result= 0;

        try {
            statement = con.createStatement();
            result = statement.executeUpdate(insertQuery);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQLException", e);
        }finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            }catch (Exception e){
                logger.log(Level.SEVERE, "ERROR IN CLOSING", e);
            }
        }

        logger.log(Level.INFO, "Inserted : {0}", result);
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
