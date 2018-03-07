/**
* Tony Thompson
* Mar 6, 2018
* DictionaryDriver.java
* 
*/
package dictionary;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;


public class DictionaryDriver {

	public static void main(String[] args) {

//		List<Integer> numbers = new ArrayList<Integer>(
//				Arrays.asList(13, 20, 31, 33, 44, 57, 61, 90, 95, 100, 120, 141, 160, 180));
//		addList(numbers);
		
		
		BinarySearchTree bst = new BinarySearchTree();
		bst.addList(parseList("dictionary.txt"));

		
		//System.out.println();
		//bst.breadthTraversal();
		bst.recursiveInOrder();
		System.out.println();
		System.out.println("contains:" +
				bst.contains("transhuman"));
		
		bst.findMin();
		bst.findMax();
		
	}// end main


	


	public static  ArrayList<ArrayList<String>> parseList(String file) {
		ArrayList<String> listRaw = loadFile(file);  //get raw file input
		ArrayList<ArrayList<String>> listSplit = new ArrayList<ArrayList<String>>();
		for (String line: listRaw) {
			//System.out.println("Inside parseList line: " + line);
			ArrayList<String> tempList = new ArrayList<String>();
		    String[] temp = line.split(":");  //split word and definition
		    //System.out.println("temp" + Arrays.toString(temp));
			tempList.add(temp[0]);
			tempList.add(temp[1]);
			listSplit.add(tempList);  //add combo to listSplit	
		}
		//sort list
		Collections.sort(listSplit, new Comparator<ArrayList<String>>() {    
		        @Override
		        public int compare(ArrayList<String> o1, ArrayList<String> o2) {
		            return o1.get(0).compareTo(o2.get(0));
		        }               
		});
		return listSplit;
	}//end parseList


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
		//System.out.println(list);
		return list;
	}// end loadFile

}// end class

/*


























*/
