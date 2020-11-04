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
    butt_no.setVisible(true);
    butt_yes.setVisible(true);

    userField.setVisible(false);
    butt_teach.setVisible(false);
  }

  public void clickYes(MouseEvent mouseEvent) {

    if (bot.isGainInt()){
      bot.gainIntelligence("y");
      botOut.setText(bot.getQuestion());

    }else {

      butt_no.setVisible(true);
      butt_yes.setVisible(true);

      userField.setVisible(false);
      butt_teach.setVisible(false);

      bot.awaitResponse('Y');
      initialize();

    }


  }

  public void clickNo(MouseEvent mouseEvent) {


    if(bot.concede) {
      userField.setVisible(true);
      butt_teach.setVisible(true);
      butt_no.setVisible(false);
      butt_yes.setVisible(false);

    }else {
      butt_no.setVisible(true);
      butt_yes.setVisible(true);
      userField.setVisible(false);
      butt_teach.setVisible(false);
    }
    bot.awaitResponse('N');
    botOut.setText(bot.getQuestion());

  }


  public void teachAI(ActionEvent actionEvent) {

   if (bot.isInquireAnimal()) {
     bot.setNewAnimal(userField.getText());
     userField.clear();
     bot.setInquireAnimal(false);
     bot.setInquireQ(true);
     bot.determineQuestion();
     botOut.setText(bot.getQuestion());
   } else if(bot.isInquireQ()){
     bot.setNewQuestion(userField.getText());
     userField.clear();
     bot.setInquireQ(false);
     bot.setInquireAns(true);
     bot.determineQuestion();
     botOut.setText(bot.getQuestion());
     userField.setVisible(false);
     butt_no.setVisible(true);
     butt_yes.setVisible(true);
     butt_teach.setVisible(false);
     }
   }
  }

