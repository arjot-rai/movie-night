import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LogInScene implements SceneModelListener {
  private Model model;

  @FXML private Button back_button;

  @FXML private Button enter_button;

  @FXML private TextField username_field;

  @FXML private TextField password_field;

  /*
    * LogInScene takes in the model, calls the loader, sets the controller and sets the scene
    * To change scenes to this scene, simply call the constructor for this scene

  */
  public LogInScene(Model newModel) {
    model = newModel;

    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("LogInScene.fxml"));

      loader.setController(this);

      model.stage.setScene(new Scene(loader.load()));

      model.stage.setTitle("MovieNight - LogInScene");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void pressedBack(ActionEvent event) throws IOException {
    OpeningScene openingScene = new OpeningScene(model);
  }

  public void pressedEnter(ActionEvent event) throws IOException {
    String user = username_field.getText();
    String pass = password_field.getText();

    LogInReturn logInReturn = Server.attemptLogIn(user, pass);
    if(logInReturn.getSuccess()){
      User.initialize(logInReturn.getProfile());
      MainScene mainScene = new MainScene(model);
    }
  }

  @Override
  public void modelChanged() {}
}
