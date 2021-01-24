package javaLabs2;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class scratch {
	final private static Logger logger = Logger.getLogger(scratch.class.getName());
	
	public static void main(String[] args){		
//		Timestamp sample = Timestamp.valueOf("2021-02-01 10:00:00");
//		Timestamp sample2 = Timestamp.valueOf("2021-06-01 23:59:00");
//		String promoCodes[] = {"PISO PIZZA", "PISO CAKE", "PISO PASTA", "PISO FRIES", "PISO ICECREAM"};
		
//		logger.log(Level.INFO, "{0}", promoCodes[1]);
//		if(sample.after(sample2))
//			logger.log(Level.INFO, "\ndate1 = " + sample + "\ndat2 = " + sample2);		
//        logger.info("Kent Regalado : " + "Kent Regalado".hashCode() );
		Map<Integer, String> smsMap = new HashMap<Integer, String>();	
//		smsMap = Main.genSMS();
		
		String promoCodes[] = {"PISO PIZZA", "PISO CAKE", "PISO PASTA", 
	   			   "PISO FRIES", "PISO ICECREAM"};

		// check if the entered promo code is valid
		boolean contains = Arrays.stream(promoCodes).anyMatch(smsMap.get(2)::equals);
		
		if(contains)
			logger.log(Level.INFO, "\nSUCCESS");
		else logger.log(Level.INFO, "\nFAILED");
    }
}
