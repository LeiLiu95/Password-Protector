package password;

import java.util.ArrayList;
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
		int[] lengths = {0,0,0,0};	//letters, numbers, special, capitals
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
		return passwordCheck(password, lengths, cap, special, num);
	}

	
	public static String passwordCheck(String password, int[] lengths, boolean cap, boolean special, boolean num) {
		Random random = new Random();
		String newPassword = password;
		ArrayList<Integer> replaceableTypes = new ArrayList<Integer>();
		
		// If type of character is replaceable, add it to the array list
		// 0 : letters
		// 1 : numbers
		// 2 : special
		// 3 : capital
		boolean[] replaceable = new boolean[4];
		if (lengths[0] > 1) {
			replaceable[0] = true;
			replaceableTypes.add(0);
		}
		if (lengths[1] > 1 && num) {
			replaceable[1] = true;
			replaceableTypes.add(1);
		}
		if (lengths[2] > 1 && special) {
			replaceable[2] = true;
			replaceableTypes.add(2);
		}
		if (lengths[3] > 1 && cap) {
			replaceable[3] = true;
			replaceableTypes.add(3);
		}
		
		// If character of password is replaceable, add index to array list
		ArrayList<Integer> replaceableIndices = new ArrayList<Integer>();
		for (int i = 0; i < password.length(); i++) {
			int type = 0;
			if (letters.indexOf(password.charAt(i)) >= 0) {
				type = 0;
			} else if (numString.indexOf(password.charAt(i)) >= 0) {
				type = 1;
			} else if (specialString.indexOf(password.charAt(i)) >= 0) {
				type = 2;
			} else if (capString.indexOf(password.charAt(i)) >= 0) {
				type = 3;
			}
			if (replaceableTypes.contains(type)) {
				replaceableIndices.add(i);
			}
		}
		
		// If number of lowercase letters is 0
		if (lengths[0] == 0) {
			newPassword = addMissingChar(random, newPassword, replaceableIndices, 0);
		}
		
		// If number of numbers is 0 and numbers are a requirement
		if (lengths[1] == 0 && num) {
			newPassword = addMissingChar(random, newPassword, replaceableIndices, 1);
		}
		
		if (lengths[2] == 0 && special) {
			newPassword = addMissingChar(random, newPassword, replaceableIndices, 2);
		}
		
		if (lengths[3] == 0 && cap) {
			newPassword = addMissingChar(random, newPassword, replaceableIndices, 3);
		}
		
		return newPassword;
	}

	private static String addMissingChar(Random random, String newPassword, ArrayList<Integer> replaceableIndices, int type) {
		char[] temp = newPassword.toCharArray();
		int tempIndex = random.nextInt(replaceableIndices.size());
		temp[tempIndex] = letters.charAt(random.nextInt(letters.length()));
		switch(type) {
		case 0:
			temp[tempIndex] = letters.charAt(random.nextInt(letters.length()));
			break;
		case 1:
			temp[tempIndex] = numString.charAt(random.nextInt(numString.length()));
			break;
		case 2:
			temp[tempIndex] = specialString.charAt(random.nextInt(specialString.length()));
			break;
		case 3:
			temp[tempIndex] = capString.charAt(random.nextInt(capString.length()));
			break;
		default:
			System.out.println("Invalid type");
			break;
		}
		newPassword = String.valueOf(temp);
		replaceableIndices.remove(tempIndex);
		return newPassword;
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


