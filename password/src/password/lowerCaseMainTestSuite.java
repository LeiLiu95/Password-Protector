package password;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Iterator;

import org.junit.BeforeClass;
import org.junit.Test;

public class lowerCaseMainTestSuite {
	int testCases = 1000;
	static String numss = "0123456789";
	static String specials= "!@#$%^&*()?";
	static String capss= "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	static HashSet<Character> caps = new HashSet<Character>();
	static HashSet<Character> special = new HashSet<Character>();
	static HashSet<Character> nums = new HashSet<Character>();
	
	
	//Populates the caps, special, and number characters into hashsets to enable linear time search
	@BeforeClass 
	public static void populate(){
		for(int i = 0; i < numss.length(); i++){
			nums.add(numss.charAt(i));
		}
		for(int j = 0; j < specials.length(); j++){
			special.add(specials.charAt(j));
		}
		for(int k = 0; k < capss.length(); k++){
			caps.add(capss.charAt(k));
		}
//		
//		Iterator<Character> i = nums.iterator();
//		while(i.hasNext()){
//			char current = i.next();
//			System.out.print(current);
//		}
//		System.out.println();
//		Iterator<Character> j = special.iterator();
//		while(j.hasNext()){
//			char current = j.next();
//			System.out.print(current);
//		}
//		System.out.println();
//		Iterator<Character> k = caps.iterator();
//		while(k.hasNext()){
//			char current = k.next();
//			System.out.print(current);
//		}
	}


	
	// Tests lowercase characters only
	@Test
	public void testL() {
		main testMain = new main();
		for(int i = 0; i < testCases; i++){
			String password = testMain.passwordGeneration(20, false, false, false);
			assertFalse(hasCaps(password));
			assertFalse(hasSpecial(password));
			assertFalse(hasNums(password));
		}
	}
	
	// Tests lowercase and uppercase characters only
	@Test
	public void testLU() {
		main testMain = new main();
		int count = 0;
		for(int i = 0; i < testCases; i++){
			String password = testMain.passwordGeneration(20, true, false, false);
			System.out.println(password);
			count++;
			assertTrue(hasCaps(password));
			assertFalse(hasSpecial(password));
			assertFalse(hasNums(password));
			System.out.println(count);
		}
		
	}
	
	
	private boolean hasCaps(String pass){
		for(int i = 0; i < pass.length(); i++){
			if(caps.contains(pass.charAt(i))){
				return true;
			}
		}
		return false;
	}
	
	private boolean hasSpecial(String pass){
		for(int i = 0; i < pass.length(); i++){
			if(special.contains(pass.charAt(i))){
				return true;
			}
		}
		return false;
	}
	
	private boolean hasNums(String pass){
		for(int i = 0; i < pass.length(); i++){
			if(nums.contains(pass.charAt(i))){
				return true;
			}
		}
		return false;
	}

}
