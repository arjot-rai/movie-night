import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainScene {

  private Model model;

  private Button logout_button;

  public MainScene(Model newModel) {
    model = newModel;
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScene.fxml"));

      loader.setController(this);

      model.stage.setScene(new Scene(loader.load()));

      model.stage.setTitle("MovieNight - MainScene");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void pressedLogoutButton(ActionEvent event) throws IOException {
    //TODO: Drop user information, either set user to null or pass in a new model
    OpeningScene openingScene = new OpeningScene(model);
  }
}
