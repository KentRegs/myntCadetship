package javaLabs2;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SmsChecker {
	public SmsChecker(){
		return;
	}
	
	public static void main(String[] args){
		Map<Integer, String> smsMap = new HashMap<Integer, String>();
		
		smsMap.put(1, genMSISDN());    
		smsMap.put(2, "Sample message!");
		smsMap.put(3, genShortCode());        
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
}
