/**
* Tony Thompson
* Mar 6, 2018
* DictionaryDriver.java
* 
*/
package dictionary;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

/**
 * allows for the loading and saving of dictionary files
 * allows ability to search dictionary 
 * 
 * @author Tony Thompson
 *
 */
public class DictionaryDriver {

	static BinarySearchTree bst = new BinarySearchTree();


	/**
	 * allows for the loading and saving of dictionary files
	 * allows ability to search dictionary
	 * @param args
	 */
	public static void main(String[] args) {
		boolean killProgram = false;
		System.out.println("Dictionary Search");

		/*
		 * main loop of program prompts user for action then executes the action
		 * prompting user for next choice
		 */
		while (!killProgram) {
			Console.printMenu();
			killProgram = menuChoice(Console.getInt("Enter number for desired action"));
		} // end while
		// bst.breadthTraversal();
		// bst.recursiveInOrder();

		System.out.println("Program exiting");
		System.exit(0);
	}// end main


	// triggers action based on menu choice
	private static boolean menuChoice(int choice) {
		switch (choice) { // cases match corresponding menu items
			case 1:
				bst.addList(parseList("dictionary.txt"));
				return false;
			case 2:
				//bst.addList(parseList("dictionary2.txt"));
				loadSerialFile();
				return false;
			case 3:
				String word = Console.getString("Enter word to search for");
				word = word.toLowerCase();
				boolean contains = bst.contains(word);
				if (!contains) { // contains prints message if found
					System.out.println(word + " is not found within the dictionary.");
				}
				return false;
			case 4: // page history + session
				saveToFile();
				return false;
			case 5:
				return true; // breaks main loop ending program
			default:
				System.out.println("Menu Switch Error");
				return false;
		}// end switch
	}// end menuChoice


	//serializes and saves BinarySearchTree bst to file
	private static void saveToFile() {
		
		try {  //basic file i/o out
			FileOutputStream fout = new FileOutputStream("dictionary2.txt");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(bst);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/* 
		 * alternative
		 *
		try {
		ArrayList<String> list = bst.breadthTraversal();
		FileWriter writer = new FileWriter("dictionary2.txt"); 
		for(String line: list) {
		  writer.write(line);
		}
		
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}// end saveToFile


	//deserializes and loads BinarySearchTree bst from file
	private static void loadSerialFile() {
		ObjectInputStream objectinputstream = null;
		try {
			FileInputStream streamIn = new FileInputStream("dictionary2.txt");
			objectinputstream = new ObjectInputStream(streamIn);
			bst = (BinarySearchTree) objectinputstream.readObject();
		} catch (Exception e) { e.printStackTrace();
		} finally {
			if (objectinputstream != null) {
				try {
					objectinputstream.close();
				} catch (IOException e) { e.printStackTrace();
				}
			}
		}
	}// end loadSerialFile


	//takes in text file of words and definitions splitting them into 
	//an arrayList of pairs that are then sorted by key word
	private static ArrayList<ArrayList<String>> parseList(String file) {
		ArrayList<String> listRaw = loadFile(file); // get raw file input
		ArrayList<ArrayList<String>> listSplit = new ArrayList<ArrayList<String>>();
		for (String line : listRaw) {
			ArrayList<String> tempList = new ArrayList<String>();
			String[] temp = line.split(":"); // split word and definition
			tempList.add(temp[0]);
			tempList.add(temp[1]);
			listSplit.add(tempList); // add combo to listSplit
		}
		// sort list by key word
		Collections.sort(listSplit, new Comparator<ArrayList<String>>() {
			@Override
			public int compare(ArrayList<String> o1, ArrayList<String> o2) {
				return o1.get(0).compareTo(o2.get(0));
			}
		});
		return listSplit;
	}// end parseList


	//loads in unserialized raw text with a single word and definition
	//pair per line divided by a ":"
	private static ArrayList<String> loadFile(String file) {
		Scanner s;
		ArrayList<String> list = new ArrayList<String>();
		try {
			s = new Scanner(new File(file));
			while (s.hasNextLine()) {
				list.add(s.nextLine());
			}
			s.close();
		} catch (FileNotFoundException e) { e.printStackTrace();
		}
		return list;
	}// end loadFile

}// end class
