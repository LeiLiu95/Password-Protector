package password;

import java.util.Random;

public class main {
	static String letters = "abcdefghijklmnopqrstuvwxyz";
	static String numString = "0123456789";
	static String specialString = "!@#$%^&*()?";
	static String capString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static String passwordGeneration(int length, boolean cap, boolean special, boolean num){
		String password = "";
		Random ran = new Random();
		
		for(int x = 0; x<length; x+=1){
			if(cap){
				password+=capString.charAt(ran.nextInt(capString.length()));
				cap=false;
				continue;
			}
			else if (special){
				password+=specialString.charAt(ran.nextInt(specialString.length()));
				special=false;
				continue;
			}
			else if(num){
				password+=numString.charAt(ran.nextInt(numString.length()));
				num=false;
				continue;
			}
			else{
				password +=letters.charAt(ran.nextInt(letters.length()));
			}
		}
		return password;
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

