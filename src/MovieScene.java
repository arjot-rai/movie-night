import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class MovieScene {
  private Model model;
  private Movie movie;
  private Scene scene;
  @FXML private TextField movie_name_textfield, movie_desc, release_date, director, streaming_services;

  @FXML private Button back_button;

  public MovieScene(Model newModel, Movie newMovie, Scene oldScene) {
    model = newModel;
    movie = newMovie;
    scene = oldScene;

    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("MovieScene.fxml"));

      loader.setController(this);

      setUpMovieDetails();

      model.stage.setScene(new Scene(loader.load()));

      model.stage.setTitle("MovieNight - MovieScene");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Takes the scene that was passed into the constructor and sets the stage back to it.
   */
  private void backButton(ActionEvent event) throws IOException {
    model.stage.setScene(scene);
  }

  /**
   * Sets the text fields to the information given by the Movie object passed in.
   */
  private void setUpMovieDetails() {
    movie_name_textfield.setText(movie.getMovieName());
    movie_desc.setText(movie.getMoviePlot());
    director.setText(movie.getMovieDirector());
    release_date.setText(movie.getMovieReleaseDate());
    //streaming_services.setText(movie.getMovieStreamingSite()); // NYI

  }

}
