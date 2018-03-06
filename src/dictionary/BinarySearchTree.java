/**
* Tony Thompson
* Mar 6, 2018
* BinarySearchTree.java
* 
*/

package dictionary;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


// Binary Search Tree that has an invariant that no duplicate data is allowed
public class BinarySearchTree {

	// root of tree
	private Node root;
	private int size;

	// internal class that defines a node's structure
	// we will be accessing the internal members of this
	// class directly
	protected class Node {
		private String word;
		private String definition; // value
		private Node left;
		private Node right;
		int height;
		int depth;


		// constructor for Node
		public Node(String word, String definition, int depth) {
			this.word = word;
			this.definition = definition;
			this.left = null;
			this.right = null;
			this.depth = depth;
		}


		// toString for Node class
		public String toString() {
			String string = word + ": " + definition;
			return string;
		}
	}// end inner class Node


	// constructor for the tree itself
	public BinarySearchTree() {
		root = null;
	}


	// constructor for the tree that takes a starting
	// root node
	public BinarySearchTree(String word, String definition) {
		root = new Node(word, definition, 0);
		size++;
	}// end constructor


	// Add method recursive version
	public boolean add(String word, String definition) {
		if (root == null) {
			root = new Node(word, definition, 0);
			size++;
			return true;
		}
		return add(word, definition, root, 0);
	}


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
	}


	// accepts sorted list and adds it to tree recursively
	public void addList(List<String> list) {
		System.out.println();

		int size = list.size();
		int half = (size / 2) - 1;
		System.out.println("size:" + size + " half:" + half + "/" + list.get(half) + " " + list);

		if (list.size() >= 2) {//

			if ((list.size() % 2) == 1)
				half++;

			List<String> left = new ArrayList<String>(list.subList(0, half));
			System.out.println("left" + left);

			List<String> right = new ArrayList<String>(list.subList(half + 1, size));
			System.out.println("right" + right);

			System.out.println("Adding: " + list.get(half));
			splitAdd(list.get(half));

			if (left.size() > 1) {
				addList(left);
			} else {
				System.out.println("Adding: Solo L " + left);
				splitAdd(list.get(0));
			}

			if (right.size() > 1) {
				addList(right);
			} else {
				System.out.println("Adding Solo R " + right);
				splitAdd(list.get(half + 1));
			}

		}

	}// end addList


	private void splitAdd(String item) {
		String word;
		String definition;
		add(word, definition);
	}


	// get the size of the BST
	public int size() {
		return size;
	}


	// return a boolean to indicate is BST is empty
	public boolean isEmpty() {
		return size == 0;
	}


	// clear the BST
	public void clear() {
		root = null;
		size = 0;
	}


	// Contains iterative method
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
				return true;
			}
		}
		return false;
	}


	// remove an word from the tree if it exists -- recursively
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


	// iterative findMax helper public
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


	// iterative findMin helper public
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


	/*
	 * // recursive findMin private Node findMin(Node node) { if (node == null)
	 * return null; else if (node.left == null) return node; return
	 * findMin(node.left); }
	 */

	/******* TRAVERSALS **********************/
	// recursive version of inorder traversal
	public void recursiveInOrder() {
		recursiveInOrder(root);
	}


	// overloaded method
	public void recursiveInOrder(Node n) {
		if (n == null)
			return;
		recursiveInOrder(n.left);
		System.out.println(n.word);
		recursiveInOrder(n.right);
	}


	// traverse the tree in breadth order
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
