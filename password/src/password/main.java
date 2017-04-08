package password;

import java.util.Random;

public class main {
	static String letters = "abcdefghijklmnopqrstuvwxyz";
	static String numString = "0123456789";
	static String specialString = "!@#$%^&*()?";
	static String capString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static String passwordGeneration(int length, boolean cap, boolean special, boolean num){
		String password = "";
		Random random = new Random();
		String[] characters = {letters, numString, specialString, capString};
		int[] lengths = {0,0,0,0};	//letters, numbers, special, capitols
		int index = 0;
		for(int x = 0; x<length; x+=1){
			index = random.nextInt(characters.length);
			
			if(index == 1 && num){
				password+=numString.charAt(random.nextInt(numString.length()));
				lengths[1]+=1;
			}
			else if (index == 2 && special){
				password+=specialString.charAt(random.nextInt(specialString.length()));
				lengths[2]+=1;
			}
			else if(index == 3 && cap){
				password+=capString.charAt(random.nextInt(capString.length()));
				lengths[3]+=1;
			}
			else{
				password +=letters.charAt(random.nextInt(letters.length()));
				lengths[0]+=1;
			}
		}
		return password;
	}
	
	public static String passwordCheck(String password, int[] lengths, boolean cap, boolean special, boolean num) {
		Random ran = new Random();
		
		// If number of lowercase letters is 0
		if (lengths[0] == 0) {
			for (int i = 0; i <= 3; i++) {
				System.out.println(i);
			}
		}
		
		// If number of numbers is 0 and numbers are a requirement
		if (lengths[1] == 0 && num) {
			
		}
		
		if (lengths[2] == 0 && special) {
			
		}
		
		if (lengths[3] == 0 && cap) {
			
		}
		
		return null;
	}
	
	public static void main(String[] args){
		String password1 = passwordGeneration(10, false, false, false);
		String password2 = passwordGeneration(10, true, false, false);
		String password3 = passwordGeneration(10, true, true, false);
		String password4 = passwordGeneration(10, true, true, true);
		System.out.println(password1);
		System.out.println(password2);
		System.out.println(password3);
		System.out.println(password4);
	}
}

