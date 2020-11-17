/**
 * This class creates a Binary Tree that hold Nodes filled with an animal or question.
 */
public class BTree {

  //Data Members
  protected Node root; //Starting Point of Binary Tree
  protected Node current; //Current position


  /**
   * Default Constructor sets root and current node to null.
   */
  BTree() {
    root = null;
    current = null;
  }

  /**
   * Inserts String info into binary tree using a recursive function.
   *
   * @param info Value to be inserted into Binary Tree
   * @param move Value that is considered in order to properly place info
   */
  void Insert(String info, String move) {
    root = InsertItem(root, info, move);
  }


  /**
   * Determines whether or not Binary Tree is empty or not
   *
   * @return True if root is null
   */

  boolean isEmpty() {
    return root == null;
  }

  /**
   * Evaluates whether or not current is located at a ending leaf/child node
   *
   * @return True if node isn't at tree end
   */

  boolean isAtEnd() {
    return (current != null) && (current.left == null && current.right == null);
  }

  /**
   * Moves current pointer to the following right node
   */
  void moveCurrentYes() {
    current = current.right;
  }

  /**
   * Moves current pointer to the following left node
   */
  void moveCurrentNo() {
    current = current.left;
  }

  /**
   * Makes current node the root.
   */
  void setCurrentToRoot() {
    current = root;

  }


  /**
   * Inserts String value into the appropriate node. If newRoot is not fill then this method will
   * fill it recursively
   *
   * @param newRoot Node that's being  inserted
   * @param value   String to be inserted into new Node
   * @param dir     String that determines where to place other String values
   * @return newRoot The node that was inserted into the Binary Tree
   */
  private Node InsertItem(Node newRoot, String value, String dir) {
    if (newRoot == null) {
      newRoot = new Node(value);
      return newRoot;
    } else if (dir.equals("Y")) {
      newRoot.right = InsertItem(newRoot.right, value, dir);
    } else {
      newRoot.left = InsertItem(newRoot.left, value, dir);
    }
    return newRoot;
  }

}