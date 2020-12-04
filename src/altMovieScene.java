import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;


public class altMovieScene {
  private Model model;
  private ApiQuery apiQuery;
  private Movie movie;
  private Scene scene;

  private boolean onList = false;

  @FXML private AnchorPane main_anchor_pane;
  @FXML
  private Label movie_name, movie_desc, release_date, director, streaming_services;

  @FXML private Button back_button;

  @FXML private Button my_list_button;

  @FXML private Button event_button;

  @FXML private ImageView movie_poster;

  public altMovieScene(Model newModel, Movie movie, Scene scene) {
    model = newModel;
    this.movie = movie;
    this.scene = scene;

    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("MovieScene.fxml"));

      loader.setController(this);

      model.stage.setScene(new Scene(loader.load()));

      model.stage.setTitle("MovieNight - MovieScene");

      setUpMovieDetails();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  public void pressedBack(ActionEvent event) throws IOException {
    model.stage.setScene(scene);
  }

  /**
   * Sets the text fields to the information given by the Movie object passed in.
   */
  private void setUpMovieDetails() {
    movie_name.setText(movie.getMovieName());
    movie_desc.setText(movie.getMoviePlot());
    director.setText(movie.getMovieDirector());
    release_date.setText(movie.getMovieReleaseDate());
    Image image = new Image(movie.getMoviePosterUrl(), 150, 224, false, false);
    movie_poster.setImage(image);
    streaming_services.setText(""); // NYI

    HashMap<String, Map<String, Object>> wantMovies = Server.getUsersWantToWatchMovies(User.getUserName());
    Set<String> titles = wantMovies.keySet();

    if(titles.contains(movie.getMovieName())){
      my_list_button.setText("Remove from My List");
      onList = true;
    }
  }

  public void addMovieToList(){
    if(onList){
      Server.removeWantToWatch(movie.getMovieName(), User.getUserName());
      my_list_button.setText("Add to My List");
      onList = false;
    }
    else{
      Server.addWantToWatch(movie.getMovieName(), User.getUserName());
      my_list_button.setText("Remove from My List");
      onList = true;
    }

  }

  public void addMovieToEvent(){
    main_anchor_pane.setDisable(true);
    AddMovieToEventScene addMovieToEventScene = new AddMovieToEventScene(model, movie);
    main_anchor_pane.setDisable(false);
  }


}
