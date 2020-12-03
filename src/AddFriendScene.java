import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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

  private String sentRequestName;

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
    sentRequestName = username_textfield.getText();

    if(Server.checkUsernameAvailability(sentRequestName)){
      response_label.setText("User does not exist");
    }else{
      if(User.getFriendList().confirmedFriends.contains(sentRequestName)){
        response_label.setText("Already friends with this user");
      } else {
        if(checkPending(sentRequestName)){
          response_label.setText("friend request already sent");
        } else{
          Server.sendFriendRequest(User.getUserName().toLowerCase(), sentRequestName);
          response_label.setText("Friend Request Sent");
        }
      }
    }

  }

  private boolean checkPending(String userName){
    HashMap<String, Map<String, Object>> pendingRequestsMap = Server.getPendingRequests(User.getUserName());
    Iterator pending_it = pendingRequestsMap.entrySet().iterator();

    while (pending_it.hasNext()){
      Map.Entry pair = (Map.Entry) pending_it.next();
      if(username_textfield.getText().toLowerCase().equals(pair.getKey().toString().toLowerCase())){
        return true;
      }
    }
    return false;
  }


}
