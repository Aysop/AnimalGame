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

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  String key = "";

  String dir = "";
  BTree tree; //Binary Tree for storing questions and answers
  char input;
  String question;

  public String getNewQuestion() {
    return newQuestion;
  }

  public void setNewQuestion(String newQuestion) {
    this.newQuestion = newQuestion;
  }

  String newQuestion;

  boolean victory = false;
  boolean restart = false;

  public boolean isInquireQ() {
    return inquireQ;
  }

  public void setInquireQ(boolean inquireQ) {
    this.inquireQ = inquireQ;
  }

  boolean inquireQ = false;

  public void setInquireAnimal(boolean inquireAnimal) {
    this.inquireAnimal = inquireAnimal;
  }

  boolean inquireAnimal = false;

  public void setInquireAns(boolean inquireAns) {
    this.inquireAns = inquireAns;
  }

  boolean inquireAns = false;

  public boolean isGainInt() {
    return gainInt;
  }

  public void setGainInt(boolean gainInt) {
    this.gainInt = gainInt;
  }

  boolean gainInt = false;


  public boolean isInquireAnimal() {
    return inquireAnimal;
  }


  public boolean isInquireAns() {
    return inquireAns;
  }


  public String getNewAnimal() {
    return newAnimal;
  }

  public void setNewAnimal(String newAnimal) {
    this.newAnimal = newAnimal;
  }

  String newAnimal;


  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public char getInput() {
    return input;
  }

  public void setInput(char input) {
    this.input = input;
  }

  public String getDir() {
    return dir;
  }

  public void setDir(String dir) {
    this.dir = dir;
  }

  public boolean isConcede() {
    return concede;
  }

  public void setConcede(boolean concede) {
    this.concede = concede;
  }

  boolean concede = false;

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
    } else if (restart) {
      restart();
    } else if (victory) {
      declareVictory();
    }else if (inquireQ) {
      inquireQuestion();
    } else if (inquireAns) {
      inquireAnswer();
    }  else if (tree.isAtEnd()) {
      guessAnimal();
    } else {
      askQuestion();
    }
  }

  public void declareVictory() {
    setQuestion("I win! Let's go again?");
    victory = false;
    restart = true;
  }

  public void restart() {
    tree.setCurrentToStart();
    setQuestion("Is it a " + tree.current.data + "?");
    restart = false;
    victory = false;
  }


  public void setBTreeRoot() {
    /*TREE IS EMPTY*/
    tree.Insert("mammal", null);
    tree.setCurrentToStart();
    tree.Insert("dog", "Y");
    determineQuestion();
  }


  public void guessAnimal() {
    /*AT END NODE*/
    String animal = tree.current.data;
    String question = "Is it " + vowelCheck(animal) + " " + animal + "?";
    //DoWhile loop to check character entered and allow user to re-enter valid arguments
    setQuestion(question);
  }

  public void askQuestion() {
    /*ASKING QUESTION*/
    String question = tree.current.data;//Question Node
    //DoWhile loop to check character entered and allow user to re-enter valid arguments

    if (question.equals("mammal")) {
      setQuestion("Is it a " + question + "? Y/N");
    } else {
      setQuestion(question);
    }
  }

  public void awaitResponse(char in) {

    switch (in) {
      case 'Y':
        if (tree.isAtEnd()) {
          victory = true;
        } else {
          tree.moveCurrentYes();
        }
        break;
      case 'N':
        if (tree.isAtEnd()) {
          concede();
        } else if (tree.current.left == null) {
          concede();
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

    setQuestion("I give up. What is it? ");
    setInquireAnimal(true);

  }

  public void inquireQuestion() {
    String question; //Concatenated String
    String animal = tree.current.data; //guessed Animal

    question =
        "What question would you ask to tell the difference between " + vowelCheck(newAnimal)
            + "\n" + newAnimal + " and " + vowelCheck(animal) + " " + animal + "?";
    setQuestion(question);

  }

public void inquireAnswer(){
  setQuestion("And your answer to this question would be? Y/N");
  setGainInt(true);
}

public void gainIntelligence(String dir){
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
  inquireAns = false;
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