/**
 * This class creates a Binary Tree that hold Nodes filled with a String.
 *
 * @author chao
 */
public class BTree {

  //Data Members
  protected Node root; //Starting Point of Binary Tree
  protected Node current; //Current position


  /**
   * Default Constructor, sets root and current to null.
   */
  BTree() {
    root = null;
    current = null;
  }

  /**
   * Inserts String info into Binary Tree based on String Move using a private recursive function.
   *
   * @param info Value to be inserted into Binary Tree
   * @param move Value that is considered in order to properly place info
   */
  void Insert(String info, String move) {
    root = InsertItem(root, info, move);
  }


  /**
   * Evaluates whether or not Binary Tree is empty or not
   *
   * @return trueOrfalse True if root equals null
   */

  boolean isEmpty() {
    return root == null;
  }

  /**
   * Evaluates whether or not current is located at a ending leaf/child node
   *
   * @return trueOrfalse True if current has to nodes to travel to
   */

  boolean isAtEnd() {
    return (current != null) && (current.left == null && current.right == null);
  }

  /**
   * Moves current pointer to the following right node, signaling a YES
   */
  void moveCurrentYes() {
    current = current.right;
  }

  /**
   * Moves current pointer to the following left node, signaling a NO
   */
  void moveCurrentNo() {
    current = current.left;
  }

  /**
   * Moves current to root, the starting point.
   */
  void setCurrentToStart() {
    current = root;

  }

  //Recursive Functions

  /**
   * Inserts String value into the appropriate node. If newRoot is not fill then this method will
   * fill it recursively
   *
   * @param newRoot node that is considered for insertion
   * @param value   String to be inserted into new Node
   * @param dir     String that determines where to place other String values
   * @return newRoot The node that was inserted into the Binary Tree
   */
  private Node InsertItem(Node newRoot, String value, String dir) {
    if (newRoot == null) {
      newRoot = new Node(value);
      //			System.out.println("ROOT INSERTED");
      return newRoot;
    } else if (dir.equals("Y")) {
      // 	System.out.println("YES INSERTED");
      newRoot.right = InsertItem(newRoot.right, value, dir);
    } else {
      //	System.out.println("NO INSERTED");
      newRoot.left = InsertItem(newRoot.left, value, dir);
    }
    return newRoot;
  }

}