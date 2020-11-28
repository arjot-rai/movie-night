import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.IOException;

public class MainScene {

  private Model model;
  @FXML private Button logout_button;
  @FXML private Button profile_button;
  @FXML private TextArea featured_Text_Area;
  @FXML private ImageView movie_Poster;
  @FXML private Image movieImage;
  @FXML private Button featured1_button, featured2_button, featured3_button, featured4_button;
  private ArrayList<Movie> featuredMovies;
  private final int FEATURED_MOVIE_LIST_SIZE = 4;

  public MainScene(Model newModel) {
    model = newModel;

    // Loads the current scene
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScene.fxml"));

      loader.setController(this);

      model.stage.setScene(new Scene(loader.load()));

      model.stage.setTitle("MovieNight - MainScene");

    } catch (IOException e) {
      e.printStackTrace();
    }

    ApiQuery apiQuery = new ApiQuery();
    featuredMovies = getFeaturedMovies(apiQuery.getAllCachedMovies(), FEATURED_MOVIE_LIST_SIZE);
    initializeFeatureMovieScreen();
  }

  public void pressedLogoutButton(ActionEvent event) throws IOException {
    User.clearAttributes();
    OpeningScene openingScene = new OpeningScene(model);
  }

  public void pressedProfileButton(ActionEvent event) throws IOException {
    ProfileScene profileScene = new ProfileScene(model);
  }

  public void pressedBrowseButton(ActionEvent event) throws IOException {
    BrowseScene browseScene = new BrowseScene(model);
  }

  private ArrayList<Movie> getFeaturedMovies(ArrayList<Movie> cachedMovies, int size) {
    Collections.shuffle(cachedMovies);
    ArrayList<Movie> featuredList = new ArrayList<>();
    for(int i =0; i < size; ++i){
      featuredList.add(cachedMovies.get(i));
    }
    return featuredList;
  }

  private void initializeFeatureMovieScreen(){
    Image image = new Image(featuredMovies.get(0).getMoviePosterUrl(), movie_Poster.getFitWidth(),
        movie_Poster.getFitHeight(), false, false);
    movie_Poster.setImage(image);
    featured1_button.setOnAction(event -> featuredButtonOnClick(featured1_button));
    featured2_button.setOnAction(event -> featuredButtonOnClick(featured2_button));
    featured3_button.setOnAction(event -> featuredButtonOnClick(featured3_button));
    featured4_button.setOnAction(event -> featuredButtonOnClick(featured4_button));
  }

  private void featuredButtonOnClick(Button button){
    Image image = new Image(featuredMovies.get(Character.getNumericValue(button.getId().charAt(8)))
        .getMoviePosterUrl(), movie_Poster.getFitWidth(), movie_Poster.getFitHeight(),
        false, false);
    movie_Poster.setImage(image);
  }
}
