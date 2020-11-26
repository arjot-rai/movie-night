
import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;


public class MainScene {

  private Model model;
  @FXML
  private Button logout_button;
  @FXML
  private Button profile_button;
  @FXML
  private TextArea featured_Text_Area;
  @FXML
  private ImageView movie_Poster;
  @FXML
  private Image movieImage;

  public MainScene(Model newModel) {
    model = newModel;



    //Loads the current scene
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
    User.clearAttributes();
    OpeningScene openingScene = new OpeningScene(model);
  }

  public void pressedProfileButton(ActionEvent event) throws IOException {
    ProfileScene profileScene = new ProfileScene(model);
  }

  public void pressedBrowseButton(ActionEvent event) throws IOException {
    BrowseScene browseScene = new BrowseScene(model);
  }
}
