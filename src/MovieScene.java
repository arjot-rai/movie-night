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
  @FXML private TextField movie_name_textfield, movie_desc, release_date, director, streaming_services;

  public MovieScene(Model newModel, Movie newMovie) {
    model = newModel;
    movie = newMovie;
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

  private void backButtonPressed(ActionEvent event) {

  }

  private void setUpMovieDetails() {
    movie_name_textfield.setText(movie.getMovieName());
    movie_desc.setText(movie.getMoviePlot());
    director.setText(movie.getMovieDirector());
    release_date.setText(movie.getMovieReleaseDate());
    //streaming_services.setText(movie.getMovieStreamingSite()); // NYI

  }

}
