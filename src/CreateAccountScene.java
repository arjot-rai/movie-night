import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
        //TODO: Check e-mail availability

        MainScene mainScene = new MainScene(model);
    }


    /*
    * Given a string, this function will return true if the email is a valid email address, false else
     */
    private boolean isEmailValid(String email) {
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        return isValidEntry(email, regex);
    }

    private boolean isUsernameValid(String userName) {
        String regex = "";
        return isValidEntry(userName, regex);
    }

    //private boolean isPasswordValid(String password, )

    private boolean isValidEntry(String toBeTested, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher match = pattern.matcher(toBeTested);
        return match.matches();
    }
}
