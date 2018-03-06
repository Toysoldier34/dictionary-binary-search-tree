/**
* Tony Thompson
* Mar 6, 2018
* RBTree.java
* 
*/
package dictionary;



import java.util.LinkedList;
import java.util.Queue;

public class RBTree<K extends Comparable<? super K>, V> {

	private static final boolean RED = true;
	private static final boolean BLACK = false;
	
	private Node root;

	// private inner Node class
	private class Node {
		K key;
		V value;
		int N;  //number of node in the subtree rooted at this node
		int height;
		int depth;
		boolean color; // true is red, false is black
		Node left, right;


		// constructor of node
		public Node(K key, V value, boolean color, int N, int depth) {
			this.key = key;
			this.value = value;
			this.color = color;
			this.N= N;
			this.depth = depth;
			this.height = 0;
		}// end constructor

	}// end class node


	// rotateRight
	private Node rotateRight(Node h) {
		Node x = h.left;
		h.left = x.right;
		x.right = h;
		x.color = h.color;
		h.color = RED;
		
		//update size
		x.N = h.N;
		h.N = 1 + size(h.left) + size(h.right);
		
		//update height
		h.height = 1 + Math.max(height(h.left), height(h.right));
		x.height = 1 + Math.max(height(x.left), height(x.right));
		
		setDepth(x, h.depth);
		
		return x;
	}// end rotateRight


	// rotateLeft
	private Node rotateLeft(Node h) {
		Node x = h.right;
		h.right = x.left;
		x.left = h;
		x.color = h.color;
		h.color = RED;
		
		//update size
		x.N = h.N;
		h.N = 1 + size(h.left) + size(h.right);
		
		//update height
		h.height = 1 + Math.max(height(h.left), height(h.right));
		x.height = 1 + Math.max(height(x.left), height(x.right));
				
		setDepth(x, h.depth);
		
		return x;
	}// end rotateLeft


	// inverts the colors of the node and its children
	private void flipColors(Node h) {
		h.color = RED;
		h.left.color = BLACK;
		h.right.color = BLACK;
	}// end flipColors


	private boolean isRed(Node h) {
		if (h == null)
			return false;
		return h.color == RED;
	}// end isRed


	// add method
	public void put(K key, V value) {
		root = put(root, key, value, 0);
		root.color = BLACK;
	}// end put


	private Node put(Node h, K key, V value, int depth) {
		//check for empty tree or null link
		if (h == null) {
			return new Node(key, value, RED, 1, depth);
		}
		int comp = key.compareTo(h.key);
		
		if (comp < 0) h.left = put(h.left, key, value, depth + 1);  //go left
		else if (comp > 0) h.right = put(h.right, key, value, depth + 1);  //go right
		else h.value = value;  //update the current node's value
		
		//balance
		if (isRed(h.right) && !isRed(h.left)) h = rotateLeft(h);  //left rotation
		if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);  //right rotation
		if (isRed(h.left) && isRed(h.right)) flipColors(h);  //invert colors
		
		//update size of node
		h.N = size(h.left) + size(h.right) + 1;
		h.height = 1 + Math.max(height(h.left), height(h.right));
		
		
		return h;
	}// end put recursive
	
	
	//set depth
	private void setDepth(Node current, int depth) {
		if (current == null) return;
		current.depth = depth;
		setDepth(current.left, depth + 1);
		setDepth(current.right, depth + 1);
	}
	
	
	//return height of tree
	public int height() {
		return height(root);
	}
	
	private int height(Node current) {
		if (current == null) return -1;
		return current.height;
	}

	
	//returns number of nodes in the tree
	public int size() {
		return size(root);
	}//end size
	
	
	private int size(Node x) {
		if (x == null) return 0;
		return x.N;
	}
	
	
	
	//traverse the tree in breadth order
	public void breadthTraversal() {
		if (root ==  null) return;
		
		//need a queue
		Queue<Node> queue = new LinkedList<>();
		
		int depth = root.depth;
		Node current = root;
		queue.add(current);
		
		//process the nodes tha are enqueued
		while (!queue.isEmpty()) {
			current = queue.remove();
			//are we at the next level?
			if (depth != current.depth) {
				System.out.println();
				depth = current.depth;	
			}
			String color = (isRed(current)) ? "R" : "B";  //set the color part of string
			System.out.print(current.key + "-" + color + "-" + current.depth + " ");
			
			//enqueue the children
			if (current.left != null) queue.add(current.left);
			if (current.right!= null) queue.add(current.right);
		}
	}//end breadthTraversal
	
	
	
	
}// end class RedBlackBST






















