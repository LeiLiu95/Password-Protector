package password;

import java.util.ArrayList;
import java.util.Random;

public class main {
	static String letters = "abcdefghijklmnopqrstuvwxyz";
	static String numString = "0123456789";
	static String specialString = "!@#$%^&*()?";
	static String capString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private int[] lengths;
	
	public String passwordGeneration(int length, boolean cap, boolean special, boolean num){
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
		this.lengths = lengths;
		return passwordCheck(password, lengths, cap, special, num);
	}

	public String passwordCheck(String password, int[] lengths, boolean cap, boolean special, boolean num) {
		Random random = new Random();
		String newPassword = password;
		int templengths[] = lengths;
		
		ArrayList<Integer> replaceableTypes = updateReplaceableTypes(lengths, cap, special, num);
		
		// If number of lowercase letters is 0
		if (lengths[0] == 0) {
			int randomTypeIndex = random.nextInt(replaceableTypes.size());
			int randomType = replaceableTypes.get(randomTypeIndex);
			char[] temp = replaceCharacter(password, random, newPassword, randomType, 0);
			newPassword = String.valueOf(temp);
			
			lengths[0] = lengths[0] + 1;
			lengths[randomType] = lengths[randomType] - 1;
			replaceableTypes = updateReplaceableTypes(lengths, cap, special, num);
		}
		
		// If number of numbers is 0 and numbers are a requirement
		if (lengths[1] == 0 && num) {
			int randomTypeIndex = random.nextInt(replaceableTypes.size());
			int randomType = replaceableTypes.get(randomTypeIndex);
			char[] temp = replaceCharacter(password, random, newPassword, randomType, 1);
			newPassword = String.valueOf(temp);
			lengths[1] = lengths[1] + 1;
			lengths[randomType] = lengths[randomType] - 1;
			replaceableTypes = updateReplaceableTypes(lengths, cap, special, num);
		}
		
		// If number of special characters is 0 and special characters are a requirement
		if (lengths[2] == 0 && special) {
			int randomTypeIndex = random.nextInt(replaceableTypes.size());
			int randomType = replaceableTypes.get(randomTypeIndex);
			char[] temp = replaceCharacter(password, random, newPassword, randomType, 0);
			newPassword = String.valueOf(temp);
			lengths[2] = lengths[2] + 1;
			lengths[randomType] = lengths[randomType] - 1;
			replaceableTypes = updateReplaceableTypes(lengths, cap, special, num);
		}
		
		// If number of capital letters is 0 and capital letters are a requirement
		if (lengths[3] == 0 && cap) {
			int randomTypeIndex = random.nextInt(replaceableTypes.size());
			int randomType = replaceableTypes.get(randomTypeIndex);
			char[] temp = replaceCharacter(password, random, newPassword, randomType, 0);
			newPassword = String.valueOf(temp);
			lengths[3] = lengths[3] + 1;
			lengths[randomType] = lengths[randomType] - 1;
			replaceableTypes = updateReplaceableTypes(lengths, cap, special, num);
		}
		this.lengths = templengths;
		return newPassword;
	}

	private char[] replaceCharacter(String password, Random random, String newPassword, int randomType, int type) {
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
		char[] temp = newPassword.toCharArray();
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
		String password1 = mainTest.passwordGeneration(10, false, false, false);
		String password2 = mainTest.passwordGeneration(10, true, false, false);
		String password3 = mainTest.passwordGeneration(10, true, true, false);
		String password4 = mainTest.passwordGeneration(10, true, true, true);
		System.out.println(password1);
		System.out.println(password2);
		System.out.println(password3);
		System.out.println(password4);		
	}
}

