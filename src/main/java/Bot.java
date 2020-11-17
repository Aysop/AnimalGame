import java.util.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Bot class that creates new instance of the AI, that acts as the programs director by asking the
 * user yes/no questions until the animal they're thinking of is guessed, or is given a new question
 * to ask by the user if the animal can't be determined. The bot also creates a BTree class that
 * acts as it's decision making center.
 */
public class Bot {

  BTree tree; //Binary Tree for storing questions and answers
  boolean concede = true; // The following booleans act as flags to trigger certain events
  boolean gainInt = false;
  boolean restart = false;
  boolean exit = false;
  String key = "";
  String newQuestion;
  String newAnimal;
  String question;

  public void setExit(boolean exit) {
    this.exit = exit;
  }

  public void setConcede(boolean concede) {
    this.concede = concede;
  }

  public boolean isGainInt() {
    return gainInt;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getNewQuestion() {
    return newQuestion;
  }

  public void setNewQuestion(String newQuestion) {
    this.newQuestion = newQuestion;
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
      setBTree();
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
          break;

      }
    }
  }


  public void declareVictory() {

    try {
      Clip clip = AudioSystem.getClip();
      AudioInputStream inputStream = AudioSystem.getAudioInputStream(
          Main.class.getResourceAsStream("Smashing.wav"));
      clip.open(inputStream);
      clip.start();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }

    setQuestion("Smashing, I win! Let's go again?");
    tree.setCurrentToRoot();
    restart = true;
    exit = true;
    key = "";
  }


  public void setBTree() {
    /*TREE IS EMPTY*/

    tree.Insert("mammal", null);
    tree.setCurrentToRoot();
    tree.Insert("dog", "Y");
    tree.Insert("fish", "N");
    determineQuestion();
  }


  public void askQuestion() {
    /*ASKING QUESTION*/
    String guess = tree.current.data;//Question Node

    if (guess.contains("?")) {
      setQuestion(guess);
    } else {
      setQuestion("Is it " + vowelCheck(guess) + guess + "?");
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
        if (tree.current.left == null) {
          key = "concede";
          determineQuestion();
        } else {
          tree.moveCurrentNo();
          determineQuestion();
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

    question =
        "What  yes or no question would you ask for " + vowelCheck(newAnimal) + newAnimal + "?";
    setQuestion(question);

  }

  public void inquireAnswer() {
    setQuestion(
        "And your answer to this question for " + vowelCheck(newAnimal) + newAnimal + " would be?");
    gainInt = true;
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
        addAnimal(newQuestion, newAnimal, dir);
      }
    }
    setQuestion("Okay, got it. Let's go again?");

    tree.setCurrentToRoot();
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
    tree.setCurrentToRoot();
  }

  public void addQuestion(String newQuestion, String newAnimal) {
    //Create new left node
    tree.current.left = new Node(newQuestion);
    //Create new left.right node
    tree.current.left.right = new Node(newAnimal);
    tree.setCurrentToRoot();
  }

  public void addAnimal(String newQuestion, String newAnimal, String dir) {
    String animal = tree.current.data;
    //the new question replaces the animal node
    tree.current.data = newQuestion;
    //the animal is pushed to the left

    if (dir.equals("Y")) {
      tree.current.left = new Node(animal);
      //the new correct newAnimal placed to the right
      tree.current.right = new Node(newAnimal);
    } else {
      tree.current.left = new Node(newAnimal);
      //the new correct newAnimal placed to the right
      tree.current.right = new Node(animal);
    }

    tree.setCurrentToRoot();
  }

  /**
   * This method places correct grammatical structure, a or an, in front of an animal string.
   *
   * @param animal The animal name that needs to be evaluated
   * @return a or an depending if the animal's first letter
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
      return "an ";
    } else {
      return "a ";
    }
  }

}