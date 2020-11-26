import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateAccountScene {
    private Model model;

    @FXML
    private Button back_button;

    @FXML
    private Button enter_button;

    @FXML
    private TextField first_name_field;

    @FXML
    private TextField last_name_field;

    @FXML
    private TextField username_field, password_field, confirm_password_field;

    @FXML
    private Label error_message;

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
        String firstName = first_name_field.getText();
        String lastName = last_name_field.getText();
        String user = username_field.getText();
        String pass = password_field.getText();
        String confirm = confirm_password_field.getText();

        if(!checkEmptyFields(firstName, lastName, user, pass, confirm)){
            if (pass.equals(confirm)){
                if(Server.addPerson(user,pass,firstName,lastName)){
                    User.initialize(Objects.requireNonNull(Server.getAttributes(user)));
                    MainScene mainScene = new MainScene(model);
                }
                else{
                    error_message.setText("User already exists!");
                    error_message.setVisible(true);
                }
            }
            else{
                error_message.setText("Be sure your password and confirmed password are the same.");
                error_message.setVisible(true);
            }
        }
        else{
            error_message.setText("Please fill out all fields");
            error_message.setVisible(true);
        }



    }
    /**
     * Checks if any of the fields are empty
     * @return false if all fields are filled, true if any are empty
     */public boolean checkEmptyFields(String fname, String lname, String username, String password, String confirmPassword){
        if(username.trim().isEmpty() || fname.trim().isEmpty() || lname.trim().isEmpty() || confirmPassword.trim().isEmpty() ||
            password.trim().isEmpty()){
            return true;
        }
        return false;
    }
}
