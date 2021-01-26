package javaLabs2;

import java.util.Map;
import java.util.HashMap;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSMSChecker {
	Map<Integer, String> smsChkMap = new HashMap<Integer, String>();	
	
	/*
	 * Test Cases
	 * > msisdn
	 * 		   Inputs			Expected Outputs
	 * 			" "					FAIL
	 * 			null				FAIL
	 * 		"123456789"				SUCCESS
	 * 		"qwertyuio"				FAIL
	 * 		"1q2w3e4r5"				FAIL
	 * 		 123456789				FAIL
	 * 		 12345678.9				FAIL
	 * 		
	 * > promo code (message)
	 * 		   Inputs			Expected Outputs
	 * 			" "					FAIL
	 * 			null				FAIL
	 * 		"PISO PIZZA"			SUCCESS
	 * 		"PISO CAKE"				SUCCESS
	 * 		"PISO PASTA"			SUCCESS
	 * 		"PISO FRIES"			SUCCESS
	 * 		"PISO ICECREAM"			SUCCESS
	 * 		" PISO ICECREAM"		FAIL
	 * 		"PISO pizza"			FAIL
	 * 		"piso pizza"			FAIL
	 * 
	 * > shortcode
	 * if promo code = "PISO PIZZA"
	 * 		   Inputs			Expected Outputs
	 * 			" "					FAIL
	 * 			null				FAIL
	 * 		   "1234"				SUCCESS
	 *		   "1234 "				FAIL
	 * 		   "1 2 3 4"			FAIL
	 *		    1234				FAIL
	 *		   "5678"				FAIL
	 * if promo code = "PISO CAKE"
	 * 		   Inputs			Expected Outputs
	 * 			" "					FAIL
	 * 			null				FAIL
	 * 		   "5678"				SUCCESS
	 *		   "5678 "				FAIL
	 * 		   "5 6 7 8"			FAIL
	 *		    5678				FAIL
	 *		   "4321"				FAIL
	 * if promo code = "PISO PASTA"
	 * 		   Inputs			Expected Outputs
	 * 			" "					FAIL
	 * 			null				FAIL
	 * 		   "4321"				SUCCESS
	 *		   "4321 "				FAIL
	 * 		   "4 3 2 1"			FAIL
	 *		    4321				FAIL
	 *		   "8765"				FAIL
	 * if promo code = "PISO FRIES"
	 * 		   Inputs			Expected Outputs
	 * 			" "					FAIL
	 * 			null				FAIL
	 * 		   "8765"				SUCCESS
	 *		   "8765 "				FAIL
	 * 		   "8 7 6 5"			FAIL
	 *		    8765				FAIL
	 *		   "9009"				FAIL
	 * if promo code = "PISO ICECREAM"
	 * 		   Inputs			Expected Outputs
	 * 			" "					FAIL
	 * 			null				FAIL
	 * 		   "9009"				SUCCESS
	 *		   "9009 "				FAIL
	 * 		   "9 0 0 9"			FAIL
	 *		    9009				FAIL
	 *		   "1234"				FAIL
	 */
	
	@Test 
    public void testMSISDN(){			
		smsChkMap.put(1, " ");    
		smsChkMap.put(2, "PISO PIZZA");
		smsChkMap.put(3, "1234");	
		
		assertEquals("FAIL", Main.smsChecker(smsChkMap));
		
		smsChkMap.put(1, null);    
		smsChkMap.put(2, "PISO PIZZA");
		smsChkMap.put(3, "1234");	
		
		assertEquals("FAIL", Main.smsChecker(smsChkMap));
		
		smsChkMap.put(1, "123456789");    
		smsChkMap.put(2, "PISO PIZZA");
		smsChkMap.put(3, "1234");	
		
		assertEquals("SUCCESS", Main.smsChecker(smsChkMap));	
		
		smsChkMap.put(1, "qwertyuio");    
		smsChkMap.put(2, "PISO PIZZA");
		smsChkMap.put(3, "1234");	
		
		assertEquals("FAIL", Main.smsChecker(smsChkMap));
		
		smsChkMap.put(1, "1q2w3e4r5");    
		smsChkMap.put(2, "PISO PIZZA");
		smsChkMap.put(3, "1234");	
		
		assertEquals("FAIL", Main.smsChecker(smsChkMap));
    }
	
	@Test
	public void testSMS(){
		smsChkMap.put(1, "123456789");    
		smsChkMap.put(2, " ");
		smsChkMap.put(3, "1234");	
		
		assertEquals("FAIL", Main.smsChecker(smsChkMap));
		
		smsChkMap.put(1, "123456789");    
		smsChkMap.put(2, null);
		smsChkMap.put(3, "1234");	
		
		assertEquals("FAIL", Main.smsChecker(smsChkMap));	
		
		smsChkMap.put(1, "123456789");    
		smsChkMap.put(2, "PISO CAKE");
		smsChkMap.put(3, "5678");	
		
		assertEquals("SUCCESS", Main.smsChecker(smsChkMap));
		
		smsChkMap.put(1, "123456789");    
		smsChkMap.put(2, "PISO PASTA");
		smsChkMap.put(3, "4321");	
		
		assertEquals("SUCCESS", Main.smsChecker(smsChkMap));
		
		smsChkMap.put(1, "123456789");    
		smsChkMap.put(2, "PISO FRIES");
		smsChkMap.put(3, "8765");	
		
		assertEquals("SUCCESS", Main.smsChecker(smsChkMap));	
		
		smsChkMap.put(1, "123456789");    
		smsChkMap.put(2, "PISO ICECREAM");
		smsChkMap.put(3, "9009");	
		
		assertEquals("SUCCESS", Main.smsChecker(smsChkMap));
		
		smsChkMap.put(1, "123456789");    
		smsChkMap.put(2, " PISO ICECREAM");
		smsChkMap.put(3, "9009");	
		
		assertEquals("FAIL", Main.smsChecker(smsChkMap));
		
		smsChkMap.put(1, "123456789");    
		smsChkMap.put(2, "PISO pizza");
		smsChkMap.put(3, "1234");	
		
		assertEquals("FAIL", Main.smsChecker(smsChkMap));	
		
		smsChkMap.put(1, "123456789");    
		smsChkMap.put(2, "piso pizza");
		smsChkMap.put(3, "1234");	
		
		assertEquals("FAIL", Main.smsChecker(smsChkMap));
    }
	
	@Test
	public void testShortCode(){	
		// if promo code = "PISO PIZZA"
		smsChkMap.put(1, "123456789");    
		smsChkMap.put(2, "PISO PIZZA");
		smsChkMap.put(3, " ");	
		
		assertEquals("FAIL", Main.smsChecker(smsChkMap));
		
		smsChkMap.put(1, "123456789");    
		smsChkMap.put(2, "PISO PIZZA");
		smsChkMap.put(3, null);	
		
		assertEquals("FAIL", Main.smsChecker(smsChkMap));
		
		smsChkMap.put(1, "123456789");    
		smsChkMap.put(2, "PISO PIZZA");
		smsChkMap.put(3, "1234");	
		
		assertEquals("SUCCESS", Main.smsChecker(smsChkMap));	
		
		smsChkMap.put(1, "123456789");    
		smsChkMap.put(2, "PISO PIZZA");
		smsChkMap.put(3, "1234 ");	
		
		assertEquals("FAIL", Main.smsChecker(smsChkMap));
		
		smsChkMap.put(1, "123456789");    
		smsChkMap.put(2, "PISO PIZZA");
		smsChkMap.put(3, "1 2 3 4");	
		
		assertEquals("FAIL", Main.smsChecker(smsChkMap));
		
		smsChkMap.put(1, "123456789");    
		smsChkMap.put(2, "PISO PIZZA");
		smsChkMap.put(3, "5678");	
		
		assertEquals("FAIL", Main.smsChecker(smsChkMap));	
		
		// if promo code = "PISO CAKE"
		smsChkMap.put(1, "123456789");    
		smsChkMap.put(2, "PISO CAKE");
		smsChkMap.put(3, " ");	
		
		assertEquals("FAIL", Main.smsChecker(smsChkMap));
		
		smsChkMap.put(1, "123456789");    
		smsChkMap.put(2, "PISO CAKE");
		smsChkMap.put(3, null);	
		
		assertEquals("FAIL", Main.smsChecker(smsChkMap));
		
		smsChkMap.put(1, "123456789");    
		smsChkMap.put(2, "PISO CAKE");
		smsChkMap.put(3, "5678");	
		
		assertEquals("SUCCESS", Main.smsChecker(smsChkMap));	
		
		smsChkMap.put(1, "123456789");    
		smsChkMap.put(2, "PISO CAKE");
		smsChkMap.put(3, "5678 ");	
		
		assertEquals("FAIL", Main.smsChecker(smsChkMap));
		
		smsChkMap.put(1, "123456789");    
		smsChkMap.put(2, "PISO CAKE");
		smsChkMap.put(3, "5 6 7 8");	
		
		assertEquals("FAIL", Main.smsChecker(smsChkMap));
		
		smsChkMap.put(1, "123456789");    
		smsChkMap.put(2, "PISO CAKE");
		smsChkMap.put(3, "4321");	
		
		assertEquals("FAIL", Main.smsChecker(smsChkMap));
		
		// if promo code = "PISO PASTA"
		smsChkMap.put(1, "123456789");    
		smsChkMap.put(2, "PISO PASTA");
		smsChkMap.put(3, " ");	
		
		assertEquals("FAIL", Main.smsChecker(smsChkMap));
		
		smsChkMap.put(1, "123456789");    
		smsChkMap.put(2, "PISO PASTA");
		smsChkMap.put(3, null);	
		
		assertEquals("FAIL", Main.smsChecker(smsChkMap));
		
		smsChkMap.put(1, "123456789");    
		smsChkMap.put(2, "PISO PASTA");
		smsChkMap.put(3, "4321");	
		
		assertEquals("SUCCESS", Main.smsChecker(smsChkMap));	
		
		smsChkMap.put(1, "123456789");    
		smsChkMap.put(2, "PISO PASTA");
		smsChkMap.put(3, "4321 ");	
		
		assertEquals("FAIL", Main.smsChecker(smsChkMap));
		
		smsChkMap.put(1, "123456789");    
		smsChkMap.put(2, "PISO PASTA");
		smsChkMap.put(3, "4 3 2 1");	
		
		assertEquals("FAIL", Main.smsChecker(smsChkMap));
		
		smsChkMap.put(1, "123456789");    
		smsChkMap.put(2, "PISO PASTA");
		smsChkMap.put(3, "8765");	
		
		assertEquals("FAIL", Main.smsChecker(smsChkMap));
		
		// if promo code = "PISO FRIES"
		smsChkMap.put(1, "123456789");    
		smsChkMap.put(2, "PISO PASTA");
		smsChkMap.put(3, " ");	
		
		assertEquals("FAIL", Main.smsChecker(smsChkMap));
		
		smsChkMap.put(1, "123456789");    
		smsChkMap.put(2, "PISO PASTA");
		smsChkMap.put(3, null);	
		
		assertEquals("FAIL", Main.smsChecker(smsChkMap));
		
		smsChkMap.put(1, "123456789");    
		smsChkMap.put(2, "PISO FRIES");
		smsChkMap.put(3, "8765");	
		
		assertEquals("SUCCESS", Main.smsChecker(smsChkMap));	
		
		smsChkMap.put(1, "123456789");    
		smsChkMap.put(2, "PISO FRIES");
		smsChkMap.put(3, "8765 ");	
		
		assertEquals("FAIL", Main.smsChecker(smsChkMap));
		
		smsChkMap.put(1, "123456789");    
		smsChkMap.put(2, "PISO FRIES");
		smsChkMap.put(3, "8 7 6 5");	
		
		assertEquals("FAIL", Main.smsChecker(smsChkMap));
		
		smsChkMap.put(1, "123456789");    
		smsChkMap.put(2, "PISO FRIES");
		smsChkMap.put(3, "9009");	
		
		assertEquals("FAIL", Main.smsChecker(smsChkMap));

		// if promo code = "PISO ICECREAM"
		smsChkMap.put(1, "123456789");    
		smsChkMap.put(2, "PISO ICECREAM");
		smsChkMap.put(3, " ");	
		
		assertEquals("FAIL", Main.smsChecker(smsChkMap));
		
		smsChkMap.put(1, "123456789");    
		smsChkMap.put(2, "PISO ICECREAM");
		smsChkMap.put(3, null);	
		
		assertEquals("FAIL", Main.smsChecker(smsChkMap));
		
		smsChkMap.put(1, "123456789");    
		smsChkMap.put(2, "PISO ICECREAM");
		smsChkMap.put(3, "9009");	
		
		assertEquals("SUCCESS", Main.smsChecker(smsChkMap));	
		
		smsChkMap.put(1, "123456789");    
		smsChkMap.put(2, "PISO ICECREAM");
		smsChkMap.put(3, "9009 ");	
		
		assertEquals("FAIL", Main.smsChecker(smsChkMap));
		
		smsChkMap.put(1, "123456789");    
		smsChkMap.put(2, "PISO ICECREAM");
		smsChkMap.put(3, "9 0 0 9");	
		
		assertEquals("FAIL", Main.smsChecker(smsChkMap));
		
		smsChkMap.put(1, "123456789");    
		smsChkMap.put(2, "PISO ICECREAM");
		smsChkMap.put(3, "1234");	
		
		assertEquals("FAIL", Main.smsChecker(smsChkMap));
	}
}
