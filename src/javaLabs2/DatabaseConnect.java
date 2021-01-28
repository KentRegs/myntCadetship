package javaLabs2;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnect {
    final private static Logger logger = Logger.getLogger(DatabaseConnect.class.getName());
    static Timestamp start = Timestamp.valueOf("2021-02-01 10:00:00");
    static Timestamp end = Timestamp.valueOf("2021-03-30 23:59:00");
    static ArrayList<Promo> availablePromos = new ArrayList<>();
    static ArrayList<Sms> generatedSmsList = new ArrayList<>();
    static ArrayList<Sms> verifiedSmsList = new ArrayList<>();
    static PromoManager promoMngr = new PromoManager();       
    static SmsManager smsMngr = new SmsManager();    
    private static Connection con = null;
    
    public static void main(String[] args){	
    	
        DatabaseConnect.connect();
//        promoMngr.insertPromo(con);        
        availablePromos.addAll(promoMngr.retrievePromos(con));
        // user sends "PROMO" to the shortcode to see the details of the promo
//        promoMngr.retrievePromos("PISO CAKE", "5678", con);
        //user sends "REGISTExR" to the shortcode to avail the promo
        generatedSmsList.addAll(smsMngr.genSMS(availablePromos, "Register", "1234"));
//        processedSmsList.addAll(smsMngr.smsChecker(availablePromos, con));
//        generatedSmsList.addAll(smsMngr.genSMS(availablePromos));
        verifiedSmsList.addAll(smsMngr.smsChecker(generatedSmsList, con));
        smsMngr.insertSms(verifiedSmsList, con);
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
            logger.info("Connected\n");
        }
        catch(Exception e){
            logger.log(Level.SEVERE, "Not Connected\n", e);
        }
    }

    public static void disconnect() {
        try{
            if (con != null){
                con.close();
                logger.info("Disonnected\n");
            }
        }catch(Exception e){
            logger.log(Level.SEVERE, "Not Connected\n", e);
        }
    }      
}
