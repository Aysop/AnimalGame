/**
 * This class creates a Node that holds a String and left,right nodes. 
 * @author chao
 *
 */
public class Node {

  protected Node left;
  protected Node right;
  protected String data;

  /**
   * Non-default Constructor, sets left and right nodes to null and sets String data to info
   * @param info String value
   */
  Node(String info) {
    left = null;
    right = null;
    data = info;
  }

}