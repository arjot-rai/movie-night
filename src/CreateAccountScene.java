import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateAccountScene {
    private Model model;

    @FXML
    private Button back_button;

    @FXML
    private Button enter_button;

    public CreateAccountScene(Model newModel) {
        model = newModel;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateAccountScene.fxml"));

            loader.setController(this);

            model.stage.setScene(new Scene(loader.load()));

            model.stage.setTitle("MovieNight - CreateAccountScene");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void pressedBack(ActionEvent event) throws IOException {
        OpeningScene openingScene = new OpeningScene(model);
    }

    public void pressedEnter(ActionEvent event) throws  IOException {
        MainScene mainScene = new MainScene(model);
    }
}
