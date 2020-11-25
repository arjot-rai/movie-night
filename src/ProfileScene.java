import javafx.event.ActionEvent;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class ProfileScene {
  private Model model;
  @FXML
  private Button profile_back_button;

  public ProfileScene(Model newModel) {
    model = newModel;
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("ProfileScene.fxml"));

      loader.setController(this);

      model.stage.setScene(new Scene(loader.load()));

      model.stage.setTitle("MovieNight - ProfileScene");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  public void pressedBackButton(ActionEvent event) throws IOException {
    MainScene mainScene = new MainScene(model);
  }

}
