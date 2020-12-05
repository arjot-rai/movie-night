import javafx.event.ActionEvent;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

public class ProfileScene {
  private static final String IDLE_BUTTON_STYLE = "-fx-background-color: #3892C7";
  private static final String HOVERED_BUTTON_STYLE = "-fx-background-color: #005BFF";
  private static final Paint TEXT_FILL = Color.web("#384BC7");
  private Model model;
  @FXML
  private Button profile_back_button;
  @FXML
  private Button change_ProfilePicture_Button;
  @FXML
  private Button change_Password_Button, add_Service_Button;

  public ProfileScene(Model newModel) {
    model = newModel;
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("ProfileScene.fxml"));

      loader.setController(this);

      model.stage.setScene(new Scene(loader.load()));

      model.stage.setTitle("MovieNight - ProfileScene");

      profile_back_button.setStyle(IDLE_BUTTON_STYLE);
      profile_back_button.setTextFill(TEXT_FILL);
      profile_back_button.setOnMouseEntered(e -> profile_back_button.setStyle(HOVERED_BUTTON_STYLE));
      profile_back_button.setOnMouseExited(e -> profile_back_button.setStyle(IDLE_BUTTON_STYLE));

      change_ProfilePicture_Button.setStyle(IDLE_BUTTON_STYLE);
      change_ProfilePicture_Button.setTextFill(TEXT_FILL);
      change_ProfilePicture_Button.setOnMouseEntered(e -> change_ProfilePicture_Button.setStyle(HOVERED_BUTTON_STYLE));
      change_ProfilePicture_Button.setOnMouseExited(e -> change_ProfilePicture_Button.setStyle(IDLE_BUTTON_STYLE));

      change_Password_Button.setStyle(IDLE_BUTTON_STYLE);
      change_Password_Button.setTextFill(TEXT_FILL);
      change_Password_Button.setOnMouseEntered(e -> change_Password_Button.setStyle(HOVERED_BUTTON_STYLE));
      change_Password_Button.setOnMouseExited(e -> change_Password_Button.setStyle(IDLE_BUTTON_STYLE));

      add_Service_Button.setStyle(IDLE_BUTTON_STYLE);
      add_Service_Button.setTextFill(TEXT_FILL);
      add_Service_Button.setOnMouseEntered(e -> add_Service_Button.setStyle(HOVERED_BUTTON_STYLE));
      add_Service_Button.setOnMouseExited(e -> add_Service_Button.setStyle((IDLE_BUTTON_STYLE)));

    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  public void pressedBackButton(ActionEvent event) throws IOException {
    MainScene mainScene = new MainScene(model);
  }

}
