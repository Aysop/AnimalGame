import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Controller {

  @FXML
  public Label botOut;
  public Button butt_teach;
  public Button butt_no;
  public Button butt_yes;
  public TextField userField;
  public ImageView nigel;
  public ImageView speechBub;
  public ImageView background;
  public ImageView borderR;
  public ImageView borderL;

  @FXML
  private ImageView darwin;

  Bot bot = new Bot();

  public void initialize() {
    bot.determineQuestion();
    botOut.setText(bot.getQuestion());
    playMusic();

    Image bg = new Image("landscape.jpg");
    background.setImage(bg);

    Image nigelImg = new Image("Nigel_Point.png");
    nigel.setImage(nigelImg);

    userField.setVisible(false);
    butt_teach.setVisible(false);
  }

  public void clickYes(MouseEvent mouseEvent) {

    if (bot.tree.current.left == null || bot.tree.current.right == null) {
      Image nigelImg = new Image("Nigel_Curious.png");
      nigel.setImage(nigelImg);
    } else {
      Image nigelImg = new Image("Nigel_Point.png");
      nigel.setImage(nigelImg);
    }

    if (bot.isGainInt()) {
      bot.gainIntelligence("Y");
      botOut.setText(bot.getQuestion());
      darwin.setImage(null);
      Image bg = new Image("landscape.jpg");
      background.setImage(bg);
      Image nigelImg = new Image("Nigel_Point.png");
      nigel.setImage(nigelImg);

    } else {
      butt_no.setVisible(true);
      butt_yes.setVisible(true);
      userField.setVisible(false);
      butt_teach.setVisible(false);

      bot.setKey("");
      bot.awaitResponse('Y');
      bot.determineQuestion();
      botOut.setText(bot.getQuestion());
    }

  }

  public void clickNo(MouseEvent mouseEvent) {

    if (bot.isGainInt()) {
      bot.gainIntelligence("N");
      botOut.setText(bot.getQuestion());
      darwin.setImage(null);
      Image bg = new Image("landscape.jpg");
      background.setImage(bg);
      Image nigelImg = new Image("Nigel_Point.png");
      nigel.setImage(nigelImg);
    } else if (bot.restart) {
      bot.setExit(false);
      System.exit(1);
    } else {

      if (bot.tree.isAtEnd() || bot.tree.current.left == null) {
        bot.setConcede(true);
      } else {
        bot.setConcede(false);
      }

      if (bot.concede) {
        nigel.setImage(null);

          Image darwinImg = new Image("darwin.png");
          darwin.setImage(darwinImg);


        Image bg = new Image("landscape2.jpg");
        background.setImage(bg);
        userField.setVisible(true);
        butt_teach.setVisible(true);
        butt_no.setVisible(false);
        butt_yes.setVisible(false);
      } else {
        butt_no.setVisible(true);
        butt_yes.setVisible(true);
        userField.setVisible(false);
        butt_teach.setVisible(false);
      }

      bot.setKey("");
      bot.awaitResponse('N');
      botOut.setText(bot.getQuestion());
    }
  }


  public void teachAI(MouseEvent actionEvent) {

    if (bot.key.equals("inquireAnimal")) {
      Image darwinImg = new Image("darwin2.png");
      darwin.setImage(darwinImg);
      bot.setNewAnimal(userField.getText());
      userField.clear();
      bot.setKey("inquireQ");
      bot.determineQuestion();
      botOut.setText(bot.getQuestion());
    } else if (bot.key.equals("inquireQ")) {
      Image darwinImg = new Image("darwin.png");
      darwin.setImage(darwinImg);
      bot.setNewQuestion(userField.getText());
      userField.clear();
      bot.setKey("inquireAns");
      bot.determineQuestion();
      botOut.setText(bot.getQuestion());
      userField.setVisible(false);
      butt_no.setVisible(true);
      butt_yes.setVisible(true);
      butt_teach.setVisible(false);
    }
  }

  public void playMusic(){
    try {
      Clip clip = AudioSystem.getClip();
      AudioInputStream inputStream = AudioSystem.getAudioInputStream(
          Main.class.getResourceAsStream("bgMusic.wav"));
      clip.open(inputStream);
      clip.start();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }
}