package javaLabs2;

import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.HashMap;
import java.sql.Timestamp;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

// accepts a map w exactly 3 items
public class Main {
	final private static Logger logger = Logger.getLogger(TestSMSChecker.class.getName());
	static Timestamp currentTimeStamp = new Timestamp(System.currentTimeMillis());
	static Map<Integer, String> smsMap = new HashMap<Integer, String>();
	static Timestamp start = Timestamp.valueOf("2021-02-01 10:00:00");
	private static ArrayList<Promo> promos = new ArrayList<>();	
	private static ArrayList<Sms> smsList = new ArrayList<>();
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
		smsMap.put(1, msisdn);    
		smsMap.put(2, promoCode);
		smsMap.put(3, shortCode);	
		
		return smsMap;
	}
	
	// function for checking an SMS
	public static String smsChecker(Map<Integer, String> smsChkMap) {
		genSMS();
		
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
	
	// function for generating an SMS
	public static ArrayList<Sms> genSMS() {
		long offset = Timestamp.valueOf("2021-02-02 00:00:00").getTime();
		long end = Timestamp.valueOf("2021-06-30 23:59:00").getTime();
		long diff = end - offset +1;			
		
		// PISO PIZZA SMS data population
		for(int ctr = 0; ctr < 30; ctr++) {
			Timestamp randTimeStamp = new Timestamp(offset + (long)(Math.random() * diff));
			Sms sms = new Sms();				
			
			sms.setMsisdn(genMSISDN());
			sms.setRecipient("PISO Inc.");
			sms.setSender(genName());
			sms.setMessage("PISO PIZZA");
			sms.setShortCode("1234");
			sms.setTimestamp(randTimeStamp);
			sms.setType("User");			
			
			smsList.add(sms);
		}
		
		// SMS data population
		for(int ctr = 0; ctr < 30; ctr++) {
			Timestamp randTimeStamp = new Timestamp(offset + (long)(Math.random() * diff));
			int random = (int) ((Math.random() * (3 - 0)) + 0);
			Sms sms = new Sms();				
			
			sms.setMsisdn(genMSISDN());
			sms.setRecipient("PISO Inc.");
			sms.setSender(genMSISDN());
			sms.setMessage(promoCodes[random]);
			sms.setShortCode(shortCodes[random]);
			sms.setTimestamp(randTimeStamp);
			sms.setType("User");	
			
			smsList.add(sms);
		}
		return smsList;
	}
	
	public static ArrayList<Sms> smsChecker(Connection con) {
		ArrayList<Sms> verifiedSmsList = new ArrayList<>();
		ArrayList<Promo> checkerArrList = new ArrayList<>();
		PromoManager promoMngr = new PromoManager();
		
		genSMS();
		
		for(Sms entry : smsList) {
			int chk1 = 0;
			int chk2 = 0;
			int chk3 = 0;
			int chk4 = 0;
			int chk5 = 0;
			int ctr = 0;
			
			Sms sms = new Sms();						
			
			// checks if the msisdn is valid			
			if(entry.getMsisdn() != null && entry.getMsisdn().matches("[0-9]+")
		       && entry.getMsisdn().length() == 9) 
				chk1 = 1;
			
			// checks if the recipient is valid
			if(entry.getRecipient() != null && entry.getRecipient().matches("PISO Inc."))
				chk2 = 1;
			
			// checks if the sender has a valid name
			if(entry.getSender() != null && entry.getSender().matches("[a-zA-Z]+"))
				chk3 = 1;
			
			// checks if the entered message (promo code) is valid
			checkerArrList.addAll(promoMngr.retrievePromos(entry.getMessage(), con));				
			
			if(entry.getMessage() != null && !(checkerArrList.isEmpty())) {
				
				// checks if the entered shortcode is valid
				if(entry.getShortCode() != null &&
				   checkerArrList.indexOf(entry.getMessage()) 
				   == checkerArrList.indexOf(entry.getShortCode())) {
					chk4 = 1;
				}
			}
			
			ArrayList<Integer> index1 = new ArrayList<>();
			ArrayList<Integer> index2 = new ArrayList<>();
			
			
			// checks if the entered promo code is valid
			for(Promo entry1 : checkerArrList) {
				if(entry.getMessage() != null && entry.getMessage().matches(entry1.getPromoCode())) {
					index1.add(checkerArrList.indexOf(entry1));
				}
				
				if(entry.getShortCode() != null && entry.getMessage().matches(entry1.getShortCode())) {
					index2.add(checkerArrList.indexOf(entry1));
				}
			}
			
			if(index1.equals(index2))
				chk5 = 1;
					
			// checks if timestamp is within promo period
			Timestamp before = new Timestamp(0);
			Timestamp after = new Timestamp(0);
			
			before = checkerArrList.get(ctr).getStartDate();
			after = checkerArrList.get(ctr).getEndDate();
			
			if(entry.getTimestamp() != null && 
			   entry.getTimestamp().before(after)) {
				if(entry.getTimestamp().after(before))
					chk5 = 1;
			}
			
			if(chk1 == 1 && chk2 == 1 && chk3 == 1 &&
			   chk4 == 1 && chk5 == 1) {
				sms.setMsisdn(entry.getMsisdn());
				sms.setRecipient(entry.getRecipient());
				sms.setSender(entry.getSender());
				sms.setMessage(entry.getMessage());
				sms.setShortCode(entry.getShortCode());
				sms.setTimestamp(entry.getTimestamp());
				sms.setType(entry.getType());
				sms.setStatus("SUCCESS");
				
				verifiedSmsList.add(sms);						
			}
			
			ctr++;
		}	
			return verifiedSmsList;
	}				
	
	// function for generating random 9-digit mobile numbers (msisdn)
	public static String genMSISDN() {
		long num = new Random().nextInt(999999999);

		return String.format("%09d", num);
	}
	
	// function for generating random names
	public static String genName() {
		String[] firstName = { "Michael", "Michelle", "Donald", "Lakeith",
						       "Hiro", "Brendan", "Morgan", "Robert", "Steven", "David", "Stephen", 
						       "Harold", "William", "Matthew", "Max", 
						       "Christina", "Yna", "Bea", "Mika", "Samantha", "Yara", "Kiara",
						       "Isabelle", "Mila", "Miles"};
		String[] lastName = { " Rourke", " Smith", " Johnson", " Williams",
						      " Ong", " Davis", " Miller", " Adobo", " Wilson", " Peterson", " Ramirez", 
						      " Murai", " Stanfield", " Bourdain", " Picasso", 
						      " Ng", " Philips", " Riverson", " Abbott", " Moore", " Babbage", " Lee",
						      " White", " Gonzales", " Lopez"};
		Random randName = new Random();
		
		return firstName[randName.nextInt(firstName.length)] +
			   lastName[randName.nextInt(lastName.length)];
	}
	
	// function for generating end dates
	public static Timestamp genDate(int num) {		
		java.util.Date utilDate = new java.util.Date();
		Timestamp endDate = new Timestamp(utilDate.getTime());	    
	    
	    endDate = Timestamp.valueOf("2021-06-30 23:59:00");
	    
	    if(num == 1)
			endDate = Timestamp.valueOf("2021-07-30 23:59:00");				
		else if(num == 2)
			endDate = Timestamp.valueOf("2021-08-30 23:59:00");	
		else if(num == 3)
			endDate = Timestamp.valueOf("2021-09-30 23:59:00");
		else if(num == 4)
			endDate = Timestamp.valueOf("2021-10-30 23:59:00");
	    
//		try {
//			parsedDate = dateFormat.parse("2021-06-30 23:59:00");			
//			
//			if(num == 1)
//				parsedDate = dateFormat.parse("2021-04-30 23:59:00");				
//			else if(num == 2)
//				parsedDate = dateFormat.parse("2021-05-30 23:59:00");
//			else if(num == 3)
//				parsedDate = dateFormat.parse("2021-03-30 23:59:00");
//			else if(num == 4)
//				parsedDate = dateFormat.parse("2021-07-30 23:59:00");
//			
//			Timestamp end = new java.sql.Timestamp(parsedDate.getTime());
//			endDate = end;
//		} catch (ParseException e) {
//			e.printStackTrace();
//		} finally {
//			//
//		}
		
		return endDate;
	}
	
	// function for generating promos
	public static ArrayList<Promo> createPromo(){										
		for(int ctr = 0; ctr < 3; ctr++) {
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
