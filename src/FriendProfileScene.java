import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class FriendProfileScene {
  private Model model;
  private Scene scene;
  private String name;

  @FXML
  private Button profile_back_button;

  @FXML
  private Label username_text;

  @FXML
  private Label friends_name_textfield;

  @FXML
  private ListView services_list;

  @FXML
  private VBox movie_ratings_vbox;

  @FXML
  private ImageView profile_picture;

  public FriendProfileScene(Model newModel, String friendName, Scene scene) {
    model = newModel;
    name = friendName;
    this.scene = scene;

    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("FriendProfileScene.fxml"));

      loader.setController(this);

      model.stage.setScene(new Scene(loader.load()));

      setUpProfileDetails();

      model.stage.setTitle("MovieNight - FriendProfileScene");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void pressedBackButton(ActionEvent event) throws IOException {
    model.stage.setScene(scene);
  }

  public void setUpProfileDetails() {
    Map<String, Object> friendInfo = Server.getAttributes(name);
    System.out.println(friendInfo);
    username_text.setText(friendInfo.get("username").toString());
    friends_name_textfield.setText(friendInfo.get("first_name").toString() + " " +
        friendInfo.get("last_name").toString() + "'s Profile");
    profile_picture.setImage(ProfilePicture.getProfilePic(name));
  }
}
