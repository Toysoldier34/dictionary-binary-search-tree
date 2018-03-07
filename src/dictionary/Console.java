package dictionary;
/**
* Tony Thompson
* Mar 6, 2018
* Console.java
* 
*/

import java.util.Scanner;

/**
 * handles console IO prints menu and prompts gets user inputs
 * 
 * @author Tony Thompson
 *
 */
public class Console {

	// field
	private static Scanner console = new Scanner(System.in);
	private static final int NUM_MENU = 5;  //number of menu options


	/*
	 * OUTPUTS
	 */

	/**
	 * prints out text for menu
	 */
	public static void printMenu() {
		System.out.println();
		System.out.println("*************************");
		System.out.println("Enter a number");
		System.out.println("1. Load dictionary from unordered pairs");
		System.out.println("2. Load dictionary from serialized tree");
		System.out.println("3. Define a word");
		System.out.println("4. Save dictionary");
		System.out.println("5. Exit");
	}// end printMenu


	/*
	 * INPUTS
	 */

	/**
	 * prompts user for an int between 0-NUM_MENU to return
	 * 
	 * @param prompt
	 *            String to display to user
	 * @return int entered by user
	 */
	public static int getInt(String prompt) {
		System.out.println(prompt + ": ");
		boolean valid = false;
		int result = 1;
		while (!valid) {
			while (!console.hasNextInt()) { // waits for user input
				System.out.println("Please enter a valid integer between 1 and " + NUM_MENU + ": ");
				// clear the scanner buffer
				console.nextLine();
			} // end while
			result = console.nextInt(); // store user input
			if ((result >= 1) && (result <= NUM_MENU)) { // validates user input
				valid = true; // breaks while after good input
			} else {
				System.out.println("Please enter a valid integer between 1 and " + NUM_MENU + ": ");
			} // end if
		} // end while
			// clear the scanner buffer
		console.nextLine();
		return result;
	}// end getInt


	/**
	 * prompts user for a String
	 * 
	 * @param prompt
	 *            String to display to user
	 * @return String entered by user
	 */
	public static String getString(String prompt) {
		System.out.println(prompt + ": ");
		return console.nextLine();
	}// end getString

}// end class
