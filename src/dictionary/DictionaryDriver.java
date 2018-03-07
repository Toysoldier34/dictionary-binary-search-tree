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
import java.util.List;
import java.util.Scanner;


public class DictionaryDriver {

	public static void main(String[] args) {

		boolean killProgram = false;
		System.out.println("Dictionary Search");

		BinarySearchTree bst = new BinarySearchTree();
		//bst.addList(parseList("dictionary.txt"));

		// bst.breadthTraversal();

		
		/*
		 * main loop of program prompts user for action then executes the action
		 * prompting user for next choice
		 */
		while (!killProgram) {
			Console.printMenu();
			killProgram = menuChoice(Console.getInt("Enter number for desired action"), bst);
		} // end while
		bst.recursiveInOrder();
		bst.findMin();
		bst.findMax();

		System.exit(0);
	}// end main


	// triggers action based on menu choice
	private static boolean menuChoice(int choice, BinarySearchTree bst) {
		switch (choice) { // cases match corresponding menu items
			case 1:
				//bst.addList(parseList(Console.getString("Enter dictionary file name \n dictionary.txt")));
				bst.addList(parseList("dictionary.txt"));
				return false;
			case 2:
				//bst.addList(parseList(Console.getString("Enter dictionary file name \n dictionary2.txt")));
				bst = loadSerialFile();
				return false;
			case 3:
				String word = Console.getString("Enter word to search for");
				boolean contains = bst.contains(word);
				if (!contains) { // contains prints message if found
					System.out.println(word + " is not found within the dictionary.");
				}
				return false;
			case 4: // page history + session
				saveToFile(bst);
				return false;
			case 5:
				return true; // breaks main loop ending program
			default:
				System.out.println("Menu Switch Error");
				return false;
		}// end switch
	}// end menuChoice


	private static void saveToFile(BinarySearchTree bst) {
		try {
			FileOutputStream fout = new FileOutputStream("dictionary2.txt");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(bst);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}// end saveToFile

	
	private static BinarySearchTree loadSerialFile() {
		ObjectInputStream objectinputstream = null;
		BinarySearchTree bst = new BinarySearchTree();
		try {
		    FileInputStream streamIn = new FileInputStream("dictionary2.txt");
		    objectinputstream = new ObjectInputStream(streamIn);
		    bst = (BinarySearchTree) objectinputstream.readObject();
		    System.out.println(bst.toString());
		} catch (Exception e) {
		    e.printStackTrace();
		} finally {
		    if(objectinputstream != null){
		        try {
					objectinputstream .close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		    } 
		}
		return bst;
	}//end loadSerialFile

	public static ArrayList<ArrayList<String>> parseList(String file) {
		ArrayList<String> listRaw = loadFile(file); // get raw file input
		ArrayList<ArrayList<String>> listSplit = new ArrayList<ArrayList<String>>();
		for (String line : listRaw) {
			// System.out.println("Inside parseList line: " + line);
			ArrayList<String> tempList = new ArrayList<String>();
			String[] temp = line.split(":"); // split word and definition
			// System.out.println("temp" + Arrays.toString(temp));
			tempList.add(temp[0]);
			tempList.add(temp[1]);
			listSplit.add(tempList); // add combo to listSplit
		}
		// sort list
		Collections.sort(listSplit, new Comparator<ArrayList<String>>() {
			@Override
			public int compare(ArrayList<String> o1, ArrayList<String> o2) {
				return o1.get(0).compareTo(o2.get(0));
			}
		});
		return listSplit;
	}// end parseList


	public static ArrayList loadFile(String file) {
		Scanner s;
		ArrayList<String> list = new ArrayList<String>();
		try {
			s = new Scanner(new File(file));
			while (s.hasNextLine()) {
				list.add(s.nextLine());

			}
			s.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// System.out.println(list);
		return list;
	}// end loadFile

}// end class






