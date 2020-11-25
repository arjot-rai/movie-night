import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class LogInScene implements SceneModelListener {
    private Model model;

    @FXML
    private Button back_button;

    @FXML
    private Button enter_button;

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

    @Override
    public void modelChanged() {

    }
}
