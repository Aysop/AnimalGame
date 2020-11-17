/**
 * This class creates a Node that holds a String and left,right nodes.
 */
public class Node {

  protected Node left;
  protected Node right;
  protected String data;

  /**
   * Sets left and right nodes to null and sets String data to info
   * @param info String
   */
  Node(String info) {
    left = null;
    right = null;
    data = info;
  }

}