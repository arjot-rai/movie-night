import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;

public class MainScene {

  private Model model;

  @FXML private TextField movie_search;
  @FXML private Button logout_button;
  @FXML private Button profile_button;
  @FXML private TextArea featured_Text_Area;
  @FXML private ImageView movie_Poster;
  @FXML private Image movieImage;
  @FXML private Button featured1_button, featured2_button, featured3_button, featured4_button;
  @FXML private VBox request_scroll_space;
  @FXML private VBox friends_scroll_space;
  private ArrayList<Movie> featuredMovies;
  private final int FEATURED_MOVIE_LIST_SIZE = 4;
  private final int FEATURED_TEXT_LENGTH = 8;

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

    // creates a list of movies to be displayed in the featured movie section
    ApiQuery apiQuery = new ApiQuery();
    featuredMovies = getFeaturedMovies(apiQuery.getAllCachedMovies(), FEATURED_MOVIE_LIST_SIZE);
    initializeFeatureMovieScreen();
    User.getFriendList().friendInvites.add("arjot");
    setFriends_scroll_space();
    setRequest_scroll_space();
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

  public void pressedSearch(ActionEvent event) throws IOException {
    SearchScene searchScene = new SearchScene(model, movie_search.getText());
  }
  /**
   * Helper function to obtain a number of movies randomly from a larger list of movies
   *
   * @param cachedMovies: ArrayList<Movie>; the list of all cached movies
   * @param size: int; the number of random movies needed
   * @return a randomly generated list of movies
   */
  private ArrayList<Movie> getFeaturedMovies(ArrayList<Movie> cachedMovies, int size) {
    Collections.shuffle(cachedMovies);
    ArrayList<Movie> featuredList = new ArrayList<>();
    for (int i = 0; i < size; ++i) {
      featuredList.add(cachedMovies.get(i));
    }
    return featuredList;
  }

  /** Initializes elements for the featured movie section */
  private void initializeFeatureMovieScreen() {
    featured1_button.setOnAction(event -> featuredButtonOnClick(featured1_button));
    featured2_button.setOnAction(event -> featuredButtonOnClick(featured2_button));
    featured3_button.setOnAction(event -> featuredButtonOnClick(featured3_button));
    featured4_button.setOnAction(event -> featuredButtonOnClick(featured4_button));
    Movie movie = featuredMovies.get(0);
    setElements(movie);
  }

  /**
   * helper function that gets fired on a click of a button
   *
   * @param button: button element
   */
  private void featuredButtonOnClick(Button button) {
    Movie movie =
        featuredMovies.get(
            Character.getNumericValue(button.getId().charAt(FEATURED_TEXT_LENGTH)) - 1);
    setElements(movie);
  }

  /**
   * helper function to set up the image view and text area for a movie
   *
   * @param movie: Movie; the movie that needs to be displayed
   */
  private void setElements(Movie movie) {
    Image image =
        new Image(
            movie.getMoviePosterUrl(),
            movie_Poster.getFitWidth(),
            movie_Poster.getFitHeight(),
            false,
            false);
    movie_Poster.setImage(image);

    String text =
        movie.getMovieName()
            + "\n"
            + movie.getMoviePlot()
            + "\n"
            + "Director(s): "
            + movie.getMovieDirector()
            + "\n"
            + "Actors: "
            + movie.getMovieActor().toString().replace("[", "").replace("]", "");
    featured_Text_Area.setText(text);
  }

  private void setFriends_scroll_space(){
    friends_scroll_space.getChildren().clear();
    ArrayList<String> confirmedFriends = User.getFriendList().confirmedFriends;
    for (String friend : confirmedFriends ) {
        friends_scroll_space.getChildren().add(new Hyperlink(friend));
    }
  }

  private void setRequest_scroll_space(){
    request_scroll_space.getChildren().clear();
    ArrayList<String> requests = User.getFriendList().friendInvites;
    for (String friend : requests ) {
      HBox friendRequestBox = new HBox();
      Hyperlink friendLink = new Hyperlink(friend);
      Button acceptButton = new Button("✓");
      acceptButton.setPadding(new Insets(0, 0, 0, 0));
      acceptButton.setOnAction(event -> onFriendRequestAccept(acceptButton, friend));
      Button rejectButton = new Button("✗");
      rejectButton.setPadding(new Insets(0, 0, 0, 0));
      rejectButton.setOnAction(event -> onFriendRequestReject(rejectButton, friend));
      friendRequestBox.getChildren().addAll(friendLink, acceptButton, rejectButton);
      request_scroll_space.getChildren().add(friendRequestBox);
    }
  }

  private void onFriendRequestAccept(Button button, String userName){
    User.getFriendList().acceptInvite(userName);
    setRequest_scroll_space();
    setFriends_scroll_space();
  }

  private void onFriendRequestReject(Button button, String userName){
    User.getFriendList().rejectInvite(userName);
    setRequest_scroll_space();
  }
}
