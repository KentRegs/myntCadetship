package javaLabs2;

import java.util.Map;
import java.util.Scanner;
import java.util.HashMap;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javaLabs2z.test.TestSMSChecker;

// accepts a map w exactly 3 items
public class Main {
	final private static Logger logger = Logger.getLogger(TestSMSChecker.class.getName());
	static Timestamp currentTimeStamp = new Timestamp(System.currentTimeMillis());
	static Map<Integer, String> smsMap = new HashMap<Integer, String>();		
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
		String promoCode = "PISO PIZZA";
		String msisdn = "123456789";		
		String shortCode = "1234";
		String checker;
		
		Map<Integer, String> smsChkMap = new HashMap<Integer, String>();	
		
		smsChkMap.put(1, msisdn);    
		smsChkMap.put(2, promoCode);
		smsChkMap.put(3, shortCode);			
		
		// check sms
		checker = smsChecker(genSMS(msisdn, promoCode, shortCode));
		
		if(checker == "SUCCESS") 
			logger.log(Level.INFO, "\n\nVALID SMS");		
		else logger.log(Level.INFO, "\n\nINVALID SMS");
	}
	
	// function for generating an SMS
	public static Map<Integer, String> genSMS(String msisdn, String promoCode, String shortCode) {
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
		if(genSMS(smsChkMap.get(1), smsChkMap.get(2), smsChkMap.get(3)).get(1) != null
			&& genSMS(smsChkMap.get(1), smsChkMap.get(2), smsChkMap.get(3)).get(1).matches("[0-9]+") 
			&& genSMS(smsChkMap.get(1), smsChkMap.get(2), smsChkMap.get(3)).get(1).length() == 9) 
			chk1 = 1;		
		
		// checks if the entered promo code is valid
		for(i = 0; i < promoCodes.length; i++) {
			if(smsChkMap.get(2) != null && smsChkMap.get(2).equals(promoCodes[i])) {
				index1.add(i);
			}
		}		

		// checks if the entered shortcode is valid
		for(j = 0; j < shortCodes.length; j++) {
			if(smsChkMap.get(3) != null && smsChkMap.get(3).equals(shortCodes[j]))
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
}