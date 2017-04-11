package password;

import static org.junit.Assert.*;

import org.junit.Test;

public class mainTest {
	int testCases = 1000;
	
	// Testing passwords made of lowercase and uppercase letters
	@Test
	public void test1() {
		main testMain = new main();
		int counter = 0;
		for (int i = 0; i < testCases; i++) {
			String password = testMain.passwordGeneration(10, false, false, false);
			if (testMain.getLengths()[0] != 0) {
				counter++;
			}
		}
		assertEquals(testCases, counter);
	}
	
	// Testing passwords made of lowercase and uppercase letters
	@Test
	public void test2() {
		main testMain = new main();
		int counter = 0;
		for (int i = 0; i < testCases; i++) {
			String password = testMain.passwordGeneration(10, true, false, false);
			if (testMain.getLengths()[0] != 0) {
				counter++;
			}
			if (testMain.getLengths()[3] != 0) {
				counter++;
			}
		}
		assertEquals(testCases * 2, counter);
	}
	
	// Testing passwords made of lowercase letters, uppercase letters, and special characters
	@Test
	public void test3() {
		main testMain = new main();
		int counter = 0;
		for (int i = 0; i < testCases; i++) {
			String password = testMain.passwordGeneration(10, true, true, false);
			if (testMain.getLengths()[0] != 0) {
				counter++;
			}
			if (testMain.getLengths()[2] != 0) {
				counter++;
			}
			if (testMain.getLengths()[3] != 0) {
				counter++;
			}
		}
		assertEquals(testCases * 3, counter);
	}
	
	// Testing passwords made of lowercase letters, uppercase letters, special characters, and numbers
	@Test
	public void test4() {
		main testMain = new main();
		int counter = 0;
		for (int i = 0; i < testCases; i++) {
			String password = testMain.passwordGeneration(10, true, true, true);
			if (testMain.getLengths()[0] != 0) {
				counter++;
			}
			if (testMain.getLengths()[1] != 0) {
				counter++;
			}
			if (testMain.getLengths()[2] != 0) {
				counter++;
			}
			if (testMain.getLengths()[3] != 0) {
				counter++;
			}
		}
		assertEquals(testCases * 4, counter);
	}

}
