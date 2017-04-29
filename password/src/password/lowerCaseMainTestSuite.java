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
	
	
	//Populates the caps, special, and number characters into hashsets to enable linear time search
	@BeforeClass 
	public static void populate(){
		HashSet<Character> caps = new HashSet<Character>();
		HashSet<Character> special = new HashSet<Character>();
		HashSet<Character> nums = new HashSet<Character>();
		
		for(int i = 0; i < numss.length(); i++){
			nums.add(numss.charAt(i));
		}
		for(int j = 0; j < specials.length(); j++){
			special.add(specials.charAt(j));
		}
		Iterator<Character> i = nums.iterator();
		while(i.hasNext()){
			char current = i.next();
			System.out.print(current);
		}
		System.out.println();
		Iterator<Character> j = special.iterator();
		while(j.hasNext()){
			char current = j.next();
			System.out.print(current);
		}
	}


	
	// Tests lowercase characters only
	@Test
	public void testL() {

	}

}
