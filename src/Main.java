import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{

        Server.connectServer("bolt://174.2.15.198:7687", "neo4j", "cmpt370");

        /*Parent root = FXMLLoader.load(getClass().getResource("OpeningScene.fxml"));
        Scene scene = new Scene(root, 600, 400);
        Model model = new Model();

        LogInScene logInScene = new LogInScene();
        logInScene.setModel(model);

        primaryStage.setTitle("Movie Night");

        primaryStage.setScene(scene);
        primaryStage.show();*/

        Model model = new Model();
        OpeningScene openingScene = new OpeningScene(model);
        openingScene.showStage();

    }

    @Override
    public void stop(){
        Server.close();
    }


    public static void main(String[] args) {
        launch(args);
    }
}