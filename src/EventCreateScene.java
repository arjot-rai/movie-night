import com.sun.javafx.iio.ios.IosDescriptor;

import java.io.IOException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javax.swing.Action;
import javax.swing.Scrollable;

public class EventCreateScene {
  private Model model;
  private ApiQuery apiQuery;
  private int maxMovies;
  ArrayList<Movie> movies;

  @FXML
  private Button back_button;

  @FXML
  private Button add_movie_button;

  @FXML
  private TextField event_name_field, date_field, location_field, add_movie_field;

  @FXML
  private VBox selected_movie_box;

  public EventCreateScene(Model newModel) {
    model = newModel;
    apiQuery = new ApiQuery();
    ArrayList<Movie> movies = new ArrayList<Movie>();
    maxMovies = 4;

    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("EventCreateScene.fxml"));

      loader.setController(this);

      model.stage.setScene(new Scene(loader.load()));

      model.stage.setTitle("MovieNight - EventCreateScene");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void pressedBackButton(ActionEvent event) throws IOException {
    MainScene mainScene = new MainScene(model);
  }

  public void pressedAddMovieButton(ActionEvent event) throws  IOException {
    String movieTitle = add_movie_field.getText();
    if (movieTitle != null){
      SearchResult movieToAdd = apiQuery.searchMovies(movieTitle).get(0);
      Movie movieObject = apiQuery.getMovie(movieToAdd.getImdbID());
      if (movieObject != null){

        movies.add(movieObject);

        HBox addedMovie = new HBox();
        Label title = new Label();
        Button remove = new Button();

        title.setText(movieObject.getMovieName());
        remove.setText("Remove");

        addedMovie.getChildren().add(title);
        addedMovie.getChildren().add(remove);

        selected_movie_box.getChildren().add(addedMovie);

        remove.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {

          }
        });
        title.setText(movieTitle);
      }
      else{

      }
    }
  }

}
