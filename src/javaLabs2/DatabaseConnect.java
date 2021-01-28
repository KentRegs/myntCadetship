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
    static PromoManager promoMngr = new PromoManager();
    static ArrayList<Sms> smsList = new ArrayList<>();    
    static SmsManager smsMngr = new SmsManager();    
    private static Connection con = null;
    
    public static void main(String[] args){
    	promoList.addAll(Main.createPromo());    	
    	
        DatabaseConnect.connect();
        promoMngr.insertPromo(promoList, con);        
        // user sends "PROMO" to the shortcode to see the details of the promo
        promoMngr.retrievePromos("PISO PIZZA", "1234", con);
        
        //user sends "REGISTER" to the shortcode to avail the promo
        smsList.addAll(Main.smsChecker(con));              
        smsMngr.insertSms(smsList, con);
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
}
