package javaLabs2;

import java.util.Map;
import java.util.Random;
import java.util.HashMap;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.concurrent.ThreadLocalRandom;

// accepts a map w exactly 3 items
public class SmsChecker {
	final private static Logger logger = Logger.getLogger(SmsChecker.class.getName());
	private static ArrayList<Promo> promos = new ArrayList<>();
	Timestamp dateAndTime = new Timestamp(System.currentTimeMillis());	
	
	public static int smsChecker(Map<Integer, String> smsMap){
		if(smsMap.get(1).matches("[0-9]+") && smsMap.get(1).length() == 9) 
			return 1;
//		if(smsMap.get(2));
		
		return (Integer) null;
	}
	
	public static void main(String[] args){
		Map<Integer, String> smsMap = new HashMap<Integer, String>();			
		
		smsMap.put(1, genMSISDN());    
		smsMap.put(2, "PISO PIZZA");
		smsMap.put(3, genShortCode());   
		
		createPromo();
		showPromos();
		
		DatabaseConnect.connect();
		
//		promos.add();
		DatabaseConnect.insertPromo("PROMO");
		
		smsChecker(smsMap);
	}
	
	// function for generating random 9-digit mobile numbers (msisdn)
	public static String genMSISDN() {
		long num = new Random().nextInt(999999999);

		return String.format("%09d", num);
	}
	
	// function for generating random 4-digit shortcodes
	public static String genShortCode() {
		long num = new Random().nextInt(9999);

		return String.format("%04d", num);
	}			
	
	// function for generating random end dates
	public static Timestamp genDate(int num) {		
		Timestamp end = Timestamp.valueOf("2021-03-30 10:00:00");
		
		if(num == 1)
			end = Timestamp.valueOf("2021-04-30 10:00:00");
		else if(num == 2)
			end = Timestamp.valueOf("2021-05-30 10:00:00");
		else if(num == 3)
			end = Timestamp.valueOf("2021-06-30 10:00:00");
		else if(num == 4)
			end = Timestamp.valueOf("2021-07-30 10:00:00");
		
		return end;
	}
	
	// function for generating promos
	public static void createPromo(){					
		String promoCodes[] = {"PISO PIZZA", "PISO CAKE", "PISO PASTA", 
							   "PISO FRIES", "PISO ICECREAM"};
		String details[] = {"Get a 12 inch pizza for only 1 peso!", 
							"Get a vanilla dream cake for only 1 peso!", 
							"Get an Oriecchiette for only 1 peso!", 
							"Get a bucket of fries for only 1 peso!", 
							"Get a tub of vanilla ice cream for only 1 peso!"};
		
		Timestamp start = Timestamp.valueOf("2021-02-01 10:00:00");
		
		for(int ctr = 0; ctr < 5; ctr++) {
			Promo promo = new Promo();
			String shortCode = genShortCode();					
			
			// nextInt is normally exclusive of the top value,
			// so add 1 to make it inclusive
			int randNum = ThreadLocalRandom.current().nextInt(0, 5);
			
			promo.setPromoCode(promoCodes[randNum]);
			promo.setDetails(details[randNum]);
			promo.setShortCode(shortCode);
			promo.setStartDate(start);
			promo.setEndDate(genDate(randNum));
			
			promos.add(promo);
		}		
	}
	
	public static void showPromos() {
		for(Promo promo: promos) {
			logger.log(Level.INFO, "\npromo code: " + promo.getPromoCode() +
				                    "\ndetails: " + promo.getDetails() +
				                    "\nshortcode: " + promo.getShortCode() + 
				                    "\nstart date: " + promo.getStartDate() + 
				                    "\nend date: " + promo.getEndDate() + "\n");
		}
	}
}
