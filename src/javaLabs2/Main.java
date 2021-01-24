package javaLabs2;

import java.util.Map;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import java.util.HashMap;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;

// accepts a map w exactly 3 items
public class Main {
	final private static Logger logger = Logger.getLogger(TestSMSChecker.class.getName());
	private static ArrayList<Promo> promos = new ArrayList<>();
	Timestamp dateAndTime = new Timestamp(System.currentTimeMillis());
	static Scanner sc = new Scanner(System.in);
	
	static String promoCodes[] = {"PISO PIZZA", "PISO CAKE", "PISO PASTA", 
			   			   "PISO FRIES", "PISO ICECREAM"};	
	static String details[] = {"Get a 12 inch pizza for only 1 peso!", 
						"Get a vanilla dream cake for only 1 peso!", 
						"Get a bowl of oriecchiette for only 1 peso!", 
						"Get a bucket of fries for only 1 peso!", 
						"Get a tub of vanilla ice cream for only 1 peso!"};
	static String shortCodes[] = {"1234", "5678", "4321", "8765", "9009"};
	
	public static void main(String[] args){
		String msisdn;
		String promoCode;
		String shortCode;
		String checker;
		
		Map<Integer, String> smsChkMap = new HashMap<Integer, String>();	
		
		createPromo();
		showPromos();
		
		logger.log(Level.INFO, "Please enter your 9-digit mobile number: ");
		msisdn = sc.nextLine();
		logger.log(Level.INFO, "Please select a promo code: ");
		promoCode = sc.nextLine();
		logger.log(Level.INFO, "Please enter the selected "
						     + "promo code's 4-digit shortcode: ");
		shortCode = sc.nextLine();
		
		smsChkMap.put(1, "123456789");    
		smsChkMap.put(2, "PISO PIZZA");
		smsChkMap.put(3, "1234");			
		
		// check sms
		checker = smsChecker(genSMS(msisdn, promoCode, shortCode));
		
		if(checker == "SUCCESS") {
			DatabaseConnect.connect();			
//			DatabaseConnect.insertPromo("PROMO");
		}			
		else logger.log(Level.INFO, "\n\nINVALID SMS");
	}
	
	// function for generating an SMS
	public static Map<Integer, String> genSMS(String msisdn, String promoCode, String shortCode) {
		Map<Integer, String> smsMap = new HashMap<Integer, String>();			
		
		smsMap.put(1, msisdn);    
		smsMap.put(2, promoCode);
		smsMap.put(3, shortCode);	
		
		return smsMap;
	}
	
	// function for checking an SMS
	public static String smsChecker(Map<Integer, String> smsChkMap) {		
		int i = 0;
		int j = 0;
		int chk1 = 0;
		int chk2 = 0;
		int chk3 = 0;
		String value = null;
		ArrayList<Integer> index1 = new ArrayList<>();
		ArrayList<Integer> index2 = new ArrayList<>();				
		
		// checks if the msisdn is valid
		if(genSMS(smsChkMap.get(1), smsChkMap.get(2), smsChkMap.get(3)).get(1).matches("[0-9]+") 
		&& genSMS(smsChkMap.get(1), smsChkMap.get(2), smsChkMap.get(3)).get(1).length() == 9) 
			chk1 = 1;		
		
		// checks if the entered promo code is valid
		for(i = 0; i < promoCodes.length; i++) {
			if(smsChkMap.get(2).equals(promoCodes[i])) {
				index1.add(i);
			}
		}		

		// checks if the entered shortcode is valid
		for(j = 0; j < shortCodes.length; j++) {
			if(smsChkMap.get(3).equals(shortCodes[j]))
				index2.add(j);
		}
				
		// checks if the promo code and shortcode match
		if(index1.equals(index2)) {
			chk2 = 1;
			chk3 = 1;
		}
		
		if(chk1 == 1 && chk2 == 1 && chk3 == 1)
			value = "SUCCESS";
		else value = "FAIL";
		
		return value;
	}
	
	// function for generating random 9-digit mobile numbers (msisdn)
	public static String genMSISDN() {
		long num = new Random().nextInt(999999999);

		return String.format("%09d", num);
	}		
	
	// function for generating end dates
	public static Timestamp genDate(int num) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		java.util.Date utilDate = new java.util.Date();
		Timestamp endDate = new Timestamp(utilDate.getTime());
	    Date parsedDate;
	    
		try {
			parsedDate = dateFormat.parse("2021-06-30 23:59:00");			
			
			if(num == 1)
				parsedDate = dateFormat.parse("2021-04-30 23:59:00");				
			else if(num == 2)
				parsedDate = dateFormat.parse("2021-05-30 23:59:00");
			else if(num == 3)
				parsedDate = dateFormat.parse("2021-03-30 23:59:00");
			else if(num == 4)
				parsedDate = dateFormat.parse("2021-07-30 23:59:00");
			
			Timestamp end = new java.sql.Timestamp(parsedDate.getTime());
			endDate = end;
		} 
		
		catch (ParseException e) {
			e.printStackTrace();
		}	    
		
		finally {
			//
		}
		
		return endDate;
	}
	
	// function for generating promos
	public static ArrayList<Promo> createPromo(){							
		
		Timestamp start = Timestamp.valueOf("2021-02-01 10:00:00");
		
		for(int ctr = 0; ctr < 5; ctr++) {
			Promo promo = new Promo();				
			
			// nextInt is normally exclusive of the top value,
			// so add 1 to make it inclusive
//			int randNum = ThreadLocalRandom.current().nextInt(0, 5);
			
			promo.setPromoCode(promoCodes[ctr]);
			promo.setDetails(details[ctr]);
			promo.setShortCode(shortCodes[ctr]);
			promo.setStartDate(start);
			promo.setEndDate(genDate(ctr));
			
			promos.add(promo);
		}
		return promos;		
	}
	
	public static void showPromos() {
		for(Promo promo: promos) {
			logger.log(Level.INFO, "\n\npromo code: " + promo.getPromoCode() +
				                   "\ndetails: " + promo.getDetails() +
				                   "\nshortcode: " + promo.getShortCode() + 
				                   "\nstart date: " + promo.getStartDate() + 
				                   "\nend date: " + promo.getEndDate() + "\n");
		}
	}
}
