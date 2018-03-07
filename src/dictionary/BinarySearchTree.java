/**
* Tony Thompson
* Mar 6, 2018
* BinarySearchTree.java
* 
*/

package dictionary;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


/**
 *  Binary Search Tree that has an invariant that no duplicate data is allowed
 * @author Tony Thompson
 *
 */
@SuppressWarnings("serial")
public class BinarySearchTree implements Serializable {

	// root of tree
	private Node root;
	private int size;

	// internal class that defines a node's structure
	// we will be accessing the internal members of this
	// class directly
	protected class Node implements Serializable {
		String word;
		String definition; // value
		private Node left;
		private Node right;
		int height;
		int depth;


		/**
		 *  constructor for Node
		 * @param word String
		 * @param definition String
		 * @param depth int
		 */
		public Node(String word, String definition, int depth) {
			this.word = word;
			this.definition = definition;
			this.left = null;
			this.right = null;
			this.depth = depth;
		}


		/**
		 *  toString for Node class 
		 *  returns word and definition
		 */
		public String toString() {
			String string = word + ": " + definition;
			return string;
		}

	}// end inner class Node


	/**
	 * constructor for the tree itself
	 */
	public BinarySearchTree() {
		root = null;
	}


	/**
	 * constructor for the tree that takes a starting root node
	 * 
	 * @param word
	 *            String
	 * @param definition
	 *            String
	 */
	public BinarySearchTree(String word, String definition) {
		root = new Node(word, definition, 0);
		size++;
	}// end constructor


	/**
	 * Add method recursive version
	 * 
	 * @param word
	 *            String
	 * @param definition
	 *            String
	 * @return boolean true if added
	 */
	public boolean add(String word, String definition) {
		if (root == null) {
			root = new Node(word, definition, 0);
			size++;
			return true;
		}
		return add(word, definition, root, 0);
	}


	// recursive add with helper
	private boolean add(String word, String definition, Node current, int depth) {
		// find the word in the BST
		int comp = word.compareTo(current.word);

		if (comp != 0) {
			// test results of comparison
			if (comp < 0) { // search left
				if (current.left != null) {
					return add(word, definition, current.left, depth + 1);
				} else {
					current.left = new Node(word, definition, depth + 1);
					return true;
				}
			} else { // search right
				if (current.right != null) {
					return add(word, definition, current.right, depth + 1);
				} else {
					current.right = new Node(word, definition, depth + 1);
					return true;
				}
			}
		} else {
			return false;
		}
	}// end add


	/**
	 * accepts sorted list and adds it to tree recursively
	 * 
	 * @param arrayList
	 *            sorted list of word and definition pairs
	 */
	public void addList(ArrayList<ArrayList<String>> arrayList) {
		int size = arrayList.size();
		int half = (size / 2) - 1;

		if (arrayList.size() >= 2) {
			if ((arrayList.size() % 2) == 1)
				half++; // corrects halfway value for odd size lists
			ArrayList<ArrayList<String>> left = new ArrayList<ArrayList<String>>(arrayList.subList(0, half));
			ArrayList<ArrayList<String>> right = new ArrayList<ArrayList<String>>(arrayList.subList(half + 1, size));
			splitAdd(arrayList.get(half)); // adding words here

			if (left.size() > 1) {
				addList(left);
			} else {
				splitAdd(arrayList.get(0));
			} // end if else

			if (right.size() > 1) {
				addList(right);
			} else {
				splitAdd(arrayList.get(half + 1));
			} // end if else
		} // end if
	}// end addList


	// splits word and definition into separate values
	private void splitAdd(List<String> list) {
		String word = list.get(0);
		String definition = list.get(1);
		add(word, definition);
	}// end splitAdd


	/**
	 * get the size of the BST
	 * 
	 * @return size int
	 */
	public int size() {
		return size;
	}// end size


	/**
	 * return a boolean to indicate is BST is empty
	 * 
	 * @return boolean
	 */
	public boolean isEmpty() {
		return size == 0;
	}// end isEmpty


	/**
	 * clear the BST
	 */
	public void clear() {
		root = null;
		size = 0;
	}// end clear


	/**
	 * Contains iterative method
	 * 
	 * @param word
	 *            String to search for
	 * @return true if word is found if word is found the word and definition are
	 *         printed from here
	 */
	public boolean contains(String word) {
		if (root == null) {
			return false;
		}

		Node current = root;
		int comp;
		while (current != null) {

			comp = word.compareTo(current.word);
			if (comp < 0) {
				current = current.left;
			} else if (comp > 0) {
				current = current.right;
			} else if (comp == 0) {
				System.out.println(current.toString());
				return true;
			}
		}
		return false;
	}// end contains


	/**
	 * remove an word from the tree if it exists -- recursively
	 * 
	 * @param word
	 */
	public void delete(String word) {
		root = delete(word, root);
	}


	private Node delete(String word, Node current) {
		if (current == null) {
			return current;
		}

		// find the word in the BST
		int comp = word.compareTo(current.word);

		// test results of comparison
		if (comp < 0) { // search left
			current.left = delete(word, current.left);
		} else if (comp > 0) { // search right
			current.right = delete(word, current.right);
		} else { // found the word we want to delete

			if (current.right == null) { // one or no children to the left
				current = current.left;
				size--;
			} else if (current.left == null) { // one child on the right
				current = current.right;
				size--;
			} else { // two children
				Node minRight = findMin(current.right);
				current.word = minRight.word;

				// now delete the min value's original node
				current.right = delete(minRight.word, current.right);
			}
		}
		return current;
	}// end delete


	/**
	 * iterative findMax helper public
	 * 
	 * @return node at max position
	 */
	public Node findMax() {
		return findMax(root);
	}


	// iterative findMax
	private Node findMax(Node node) {
		Node current = node;
		while (current != null) {
			if (current.right == null) {
				System.out.println(current);
				return current;
			}
			current = current.right;
		} // end while
		return node;
	}// end findMax


	/**
	 * iterative findMin helper public
	 * 
	 * @return node at min position
	 */
	public Node findMin() {
		return findMin(root);
	}


	// iterative findMin
	private Node findMin(Node node) {
		Node current = node;
		while (current != null) {
			if (current.left == null) {
				System.out.println(current);
				return current;
			}
			current = current.left;
		} // end while
		return node;
	}// end findMin


	/******* TRAVERSALS **********************/
	/**
	 * recursive version of inorder traversal
	 */
	public void recursiveInOrder() {
		recursiveInOrder(root);
	}


	/**
	 * overloaded method
	 * 
	 * @param n
	 *            starting node, helper starts with root
	 */
	public void recursiveInOrder(Node n) {
		if (n == null)
			return;
		recursiveInOrder(n.left);
		System.out.println(n.word);
		recursiveInOrder(n.right);
	}// end recursiveInOrder


	/**
	 * traverse the tree in breadth order
	 */
	public void breadthTraversal() {
		if (root == null)
			return;

		// need a queue
		Queue<Node> queue = new LinkedList<>();

		int depth = root.depth;
		Node current = root;
		queue.add(current);

		// process the nodes that are enqueued
		while (!queue.isEmpty()) {
			current = queue.remove();
			// are we at the next level?
			if (depth != current.depth) {
				System.out.println();
				depth = current.depth;
			}
			System.out.print(current.word + ": " + current.depth + " ");

			// enqueue the children
			if (current.left != null)
				queue.add(current.left);
			if (current.right != null)
				queue.add(current.right);
		}
	}// end breadthTraversal

}// end class
