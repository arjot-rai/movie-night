import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateAccountScene {
    @FXML
    private Button back_button;

    @FXML
    private Button enter_button;

    public void pressedBack(ActionEvent event) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource("OpeningScene.fxml"));

        Scene scene = new Scene(pane);

        Stage window = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void pressedEnter(ActionEvent event) throws  IOException {
        Parent pane = FXMLLoader.load(getClass().getResource("MainScene.fxml"));

        Scene scene = new Scene(pane);
        Stage window = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}
