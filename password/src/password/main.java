package password;

import java.util.ArrayList;
import java.util.Random;

public class main {
	static String letters = "abcdefghijklmnopqrstuvwxyz";	//global statements that are used for password generation criteria
	static String numString = "0123456789";
	static String specialString = "!@#$%^&*()?";
	static String capString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private int[] lengths;
	
	public String passwordGeneration(int length, boolean cap, boolean special, boolean num){	//method that takes input to run an algorithm to generate a password
		String password = "";
		Random random = new Random();

		
		String[] characters = {letters, numString, specialString, capString};	//an array made for all possible characters
		int[] lengths = {0,0,0,0};	//letters, numbers, special, capitals
		int index = 0;
		for(int x = 0; x<length; x+=1){	//for loop done until the passed length of the required password
			index = random.nextInt(characters.length);	//random number used to get the next value from array
			
			if(index == 1 && num){	//if case for numbers
				password+=numString.charAt(random.nextInt(numString.length()));
				lengths[1]+=1;
			}
			else if (index == 2 && special){	//if case for special characters
				password+=specialString.charAt(random.nextInt(specialString.length()));
				lengths[2]+=1;
			}
			else if(index == 3 && cap){		//if case for capital letters
				password+=capString.charAt(random.nextInt(capString.length()));
				lengths[3]+=1;
			}
			else{	//if case for lower case letters
				password +=letters.charAt(random.nextInt(letters.length()));
				lengths[0]+=1;
			}
		}
		this.lengths = lengths;	//set the objects length as the current length
		return passwordCheck(password, lengths, cap, special, num);	//final method to check if all password requirements were met
	}

	public String passwordCheck(String password, int[] lengths, boolean cap, boolean special, boolean num) {
		Random random = new Random();
		String newPassword = password;
		int templengths[] = lengths;
		
		ArrayList<Integer> replaceableTypes = updateReplaceableTypes(templengths, cap, special, num);
		
		// If number of lowercase letters is 0
		if (templengths[0] == 0) {
			int randomTypeIndex = random.nextInt(replaceableTypes.size());
			int randomType = replaceableTypes.get(randomTypeIndex);
			char[] temp = replaceCharacter(newPassword, random, randomType, 0);
			newPassword = String.valueOf(temp);
			
			templengths[0] = templengths[0] + 1;
			templengths[randomType] = templengths[randomType] - 1;
			replaceableTypes = updateReplaceableTypes(templengths, cap, special, num);
		}
		
		// If number of numbers is 0 and numbers are a requirement
		if (templengths[1] == 0 && num) {
			int randomTypeIndex = random.nextInt(replaceableTypes.size());
			int randomType = replaceableTypes.get(randomTypeIndex);
			char[] temp = replaceCharacter(newPassword, random, randomType, 1);
			newPassword = String.valueOf(temp);
			templengths[1] = templengths[1] + 1;
			templengths[randomType] = templengths[randomType] - 1;
			replaceableTypes = updateReplaceableTypes(templengths, cap, special, num);
		}
		
		// If number of special characters is 0 and special characters are a requirement
		if (templengths[2] == 0 && special) {
			int randomTypeIndex = random.nextInt(replaceableTypes.size());
			int randomType = replaceableTypes.get(randomTypeIndex);
			char[] temp = replaceCharacter(newPassword, random, randomType, 2);
			newPassword = String.valueOf(temp);
			templengths[2] = templengths[2] + 1;
			templengths[randomType] = templengths[randomType] - 1;
			replaceableTypes = updateReplaceableTypes(templengths, cap, special, num);
		}
		
		// If number of capital letters is 0 and capital letters are a requirement
		if (templengths[3] == 0 && cap) {
			int randomTypeIndex = random.nextInt(replaceableTypes.size());
			int randomType = replaceableTypes.get(randomTypeIndex);
			char[] temp = replaceCharacter(newPassword, random, randomType, 3);
			newPassword = String.valueOf(temp);
			templengths[3] = templengths[3] + 1;
			templengths[randomType] = templengths[randomType] - 1;
			replaceableTypes = updateReplaceableTypes(templengths, cap, special, num);
		}
		this.lengths = templengths;
		return newPassword;
	}

	private char[] replaceCharacter(String password, Random random, int randomType, int type) {
		ArrayList<Integer> replaceableIndices = new ArrayList<Integer>();
		for (int i = 0; i < password.length(); i++) {
			switch(randomType) {
			case 0:
				if (letters.indexOf(password.charAt(i)) >= 0)
					replaceableIndices.add(i);
				break;
			case 1:
				if (numString.indexOf(password.charAt(i)) >= 0)
					replaceableIndices.add(i);
				break;
			case 2:
				if (specialString.indexOf(password.charAt(i)) >= 0)
					replaceableIndices.add(i);
				break;
			case 3:
				if (capString.indexOf(password.charAt(i)) >= 0)
					replaceableIndices.add(i);
				break;
			default:
				break;
			}
		}
		char[] temp = password.toCharArray();
		int tempIndex = random.nextInt(replaceableIndices.size());
		temp[replaceableIndices.get(tempIndex)] = letters.charAt(random.nextInt(letters.length()));
		switch(type) {
		case 0:
			temp[replaceableIndices.get(tempIndex)] = letters.charAt(random.nextInt(letters.length()));
			break;
		case 1:
			temp[replaceableIndices.get(tempIndex)] = numString.charAt(random.nextInt(numString.length()));
			break;
		case 2:
			temp[replaceableIndices.get(tempIndex)] = specialString.charAt(random.nextInt(specialString.length()));
			break;
		case 3:
			temp[replaceableIndices.get(tempIndex)] = capString.charAt(random.nextInt(capString.length()));
			break;
		default:
			break;
		}
		return temp;
	}

	private ArrayList<Integer> updateReplaceableTypes(int[] lengths, boolean cap, boolean special, boolean num) {
		ArrayList<Integer> replaceableTypes = new ArrayList<Integer>();
		
		// If type of character is replaceable, add it to the array list
		// 0 : letters
		// 1 : numbers
		// 2 : special
		// 3 : capital
		if (lengths[0] > 1) {
			replaceableTypes.add(0);
		}
		if (lengths[1] > 1 && num) {
			replaceableTypes.add(1);
		}
		if (lengths[2] > 1 && special) {
			replaceableTypes.add(2);
		}
		if (lengths[3] > 1 && cap) {
			replaceableTypes.add(3);
		}
		return replaceableTypes;
	}
	
	public int[] getLengths() {
		return lengths;
	}
	public static void main(String[] args){
		main mainTest = new main();
		String password1 = mainTest.passwordGeneration(6, false, false, false);
		String password2 = mainTest.passwordGeneration(6, true, false, false);
		String password3 = mainTest.passwordGeneration(6, true, true, false);
		String password4 = mainTest.passwordGeneration(6, true, true, true);
		System.out.println(password1);
		System.out.println(password2);
		System.out.println(password3);
		System.out.println(password4);
		for(int i =0; i<=1000; i+=1){
			password1=mainTest.passwordGeneration(10, true, false, false);
			System.out.println(password1);
		}
	}
}
