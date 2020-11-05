import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


public class Controller {

  @FXML
  public Label botOut;
  public Button butt_teach;
  public Button butt_no;
  public Button butt_yes;
  public TextField userField;

  Bot bot = new Bot();

  public void initialize() {
    bot.determineQuestion();
    botOut.setText(bot.getQuestion());

    userField.setVisible(false);
    butt_teach.setVisible(false);
  }

  public void clickYes(MouseEvent mouseEvent) {

    if (bot.isGainInt()) {
      bot.gainIntelligence("Y");
      botOut.setText(bot.getQuestion());
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
    } else {

      if (bot.tree.isAtEnd() || bot.tree.current.left == null) {
        bot.setConcede(true);
      } else {
        bot.setConcede(false);
      }

      if (bot.concede) {
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


  public void teachAI(ActionEvent actionEvent) {

    if (bot.key.equals("inquireAnimal")) {
      bot.setNewAnimal(userField.getText());
      userField.clear();
      bot.setKey("inquireQ");
      bot.determineQuestion();
      botOut.setText(bot.getQuestion());
    } else if (bot.key.equals("inquireQ")) {
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
}

