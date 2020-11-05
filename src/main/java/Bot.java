import java.util.*;

/**
 * This class creates a "Bot" that operates as the programs voice and prompter. It creates a new
 * Binary Tree and manipulates it using methods belonging to the BinaryTree class. This "Bot" will
 * ask the questions and insert answers into a Binary Tree. It will keep guessing until it gives up
 * or reaches a correct answer.
 *
 * @author chao
 */
public class Bot {

  String key = "";
  BTree tree; //Binary Tree for storing questions and answers
  String question;

  public boolean isConcede() {
    return concede;
  }

  public void setConcede(boolean concede) {
    this.concede = concede;
  }

  boolean concede = true;
  boolean restart = false;
  String newQuestion;
  boolean gainInt = false;
  String newAnimal;


  public void setKey(String key) {
    this.key = key;
  }

  public String getNewQuestion() {
    return newQuestion;
  }

  public void setNewQuestion(String newQuestion) {
    this.newQuestion = newQuestion;
  }

  public boolean isGainInt() {
    return gainInt;
  }

  public void setGainInt(boolean gainInt) {
    this.gainInt = gainInt;
  }


  public String getNewAnimal() {
    return newAnimal;
  }

  public void setNewAnimal(String newAnimal) {
    this.newAnimal = newAnimal;
  }

  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }


  /**
   * Default Constructor, creates a new Binary Tree
   */
  Bot() {
    tree = new BTree();
  }

  /**
   * This method asks the user questions and will try to guess what the animal is.
   */
  void determineQuestion() {

    if (tree.isEmpty()) {
      setBTreeRoot();
    } else {
      switch (key) {
        case "victory":
          declareVictory();
          break;
        case "concede":
          concede();
          break;
        case "inquireQ":
          inquireQuestion();
          break;
        case "inquireAns":
          inquireAnswer();
          break;
        default:
          askQuestion();

      }
    }
  }


  public void declareVictory() {
    setQuestion("I win! Let's go again?");
    tree.setCurrentToStart();
    restart = true;
    key = "";
  }


  public void setBTreeRoot() {
    /*TREE IS EMPTY*/
    tree.Insert("mammal", null);
    tree.setCurrentToStart();
    tree.Insert("dog", "Y");
    determineQuestion();
  }


  public void askQuestion() {
    /*ASKING QUESTION*/
    String guess = tree.current.data;//Question Node
    //DoWhile loop to check character entered and allow user to re-enter valid arguments

    if (guess.contains("?")) {
      setQuestion(guess);
    } else {
      setQuestion("Is it a " + guess + "?");
    }

  }

  public void awaitResponse(char in) {

    switch (in) {
      case 'Y':
        if (restart) {
          restart = false;
          determineQuestion();
        } else if (tree.isAtEnd()) {
          key = "victory";
        } else {
          tree.moveCurrentYes();
        }
        break;
      case 'N':
        if (tree.isAtEnd()) {
          key = "concede";
          determineQuestion();
        } else if (tree.current.left == null) {
          key = "concede";
          determineQuestion();
        } else {
          tree.moveCurrentNo();
          askQuestion();
        }
        break;
    }

  }

  /**
   * If the Bot cannot guess the correct animal, it will give up and ask the user to input the
   * animal and a question that describes it.
   */
  void concede() {


    setQuestion("I give up. What is it?");
    key = "inquireAnimal";
  }

  public void inquireQuestion() {
    String question; //Concatenated String
    String animal = tree.current.data; //guessed Animal


    question = "What  yes or no question would you ask for a " + newAnimal + "?";
    setQuestion(question);

  }

  public void inquireAnswer() {
    setQuestion("And your answer to this question for a " + newAnimal + " would be?");
    setGainInt(true);
    key = "";
    concede = false;
  }

  public void gainIntelligence(String dir) {
    String newQuestion = getNewQuestion();
    String newAnimal = getNewAnimal();

    if (tree.current.data.equals(tree.root.data)) {
      creatNewNode(newQuestion, dir, newAnimal);
    } else {
      if (tree.current.data.contains("?")) {
        addQuestion(newQuestion, newAnimal);
      } else {
        addAnimal(newQuestion, newAnimal);
      }
    }
    setQuestion("Okay, got it. Let's go again?");

    tree.setCurrentToStart();
    gainInt = false;
    restart = true;

  }


  public void creatNewNode(String newQuestion, String dir, String newAnimal) {

    //moves new question to the left
    tree.Insert(newQuestion, dir);

    //set current pointer to the new question node
    tree.moveCurrentNo();
    //moves new newAnimal to the right of new question
    tree.current.right = new Node(newAnimal);
    tree.setCurrentToStart();
  }

  public void addQuestion(String newQuestion, String newAnimal) {
    //Create new left node
    tree.current.left = new Node(newQuestion);
    //Create new left.right node
    tree.current.left.right = new Node(newAnimal);
    tree.setCurrentToStart();
  }

  public void addAnimal(String newQuestion, String newAnimal) {
    String animal = tree.current.data;
    //the new question replaces the animal node
    tree.current.data = newQuestion;
    //the animal is pushed to the left
    tree.current.left = new Node(animal);
    //the new correct newAnimal placed to the right
    tree.current.right = new Node(newAnimal);
    tree.setCurrentToStart();
  }

  /**
   * This method places correct grammatical structure, a or an, in front of an animal string.
   *
   * @param animal The animal that needs to be analyzed
   * @return aOran an if the animal's first letter begins with a vowel, a for anything else
   */
  String vowelCheck(String animal) {
    char firstLetter;
    //Array of Vowels
    ArrayList<Character> vowels = new ArrayList<>();
    vowels.add('A');
    vowels.add('E');
    vowels.add('I');
    vowels.add('O');
    vowels.add('U');

    firstLetter = animal.toUpperCase().charAt(0);

    if (vowels.contains(firstLetter)) {
      return "an";
    } else {
      return "a";
    }
  }
}