package dictionary;

/**
* Tony Thompson
* Mar 6, 2018
* BinarySearchTree.java
* 
*/

public class BinarySearchTree {
	
	private Node root;
	private int size;
	
	
	protected class Node {
		private String word;  //key
		private String definition;  //value
		private Node left;
		private Node right;
		
		public Node(String word, String definition) {
			this.word = word;
			this.definition = definition;
		}
		
		public String toString() {
			String string = word + ": " + definition;
			return string;
		}		
		
	}//end inner class Node
	
	
	
	public BinarySearchTree() {
		root = null;
	}
	
	public BinarySearchTree(String word, String definition) {
		root = new Node(word, definition);
		size++;
	}//end constructor
	
	
	
	
	private boolean add() {
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public void clear() {
		root = null;
		size = 0;
	}
	
	
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
		//TODO
		//System.out.println(n.element);
		recursiveInOrder(n.right);
	}
	
	
}//end class











