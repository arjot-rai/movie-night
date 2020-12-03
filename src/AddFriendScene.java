import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class AddFriendScene {
  private Model model;
  private Stage friendStage;

  @FXML
  private Button back_button;

  @FXML
  private TextField username_textfield;

  @FXML
  private Label response_label;


  public AddFriendScene(Model newModel) {
    model = newModel;

    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("AddFriendScene.fxml"));

      loader.setController(this);

      friendStage = new Stage();

      friendStage.setScene(new Scene(loader.load()));
      friendStage.setTitle("MovieNight - AddFriendScene");
      friendStage.showAndWait();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void pressedBackButton(ActionEvent event) throws IOException {
    friendStage.close();
  }

  public void sendFriendRequest(ActionEvent event) throws IOException {

  }


}
