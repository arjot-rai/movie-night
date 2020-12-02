import com.amazonaws.auth.profile.internal.Profile;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class ProfileScene {
  private Model model;

  private ApiQuery api;

  @FXML private Button profile_back_button, change_ProfilePicture_Button, add_Service_Button;
  @FXML
  private MenuItem netflix_click,
      hulu_click,
      crave_click,
      prime_click,
      netflix_remove,
      hulu_remove,
      crave_remove,
      prime_remove;

  @FXML private ImageView profile_picture;

  @FXML private Label username_text;

  @FXML private ListView services_list;

  @FXML private VBox movie_ratings_vbox;

  @FXML private AnchorPane anchor;

  public ProfileScene(Model newModel) {
    model = newModel;
    api = new ApiQuery();
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("ProfileScene.fxml"));

      loader.setController(this);

      model.stage.setScene(new Scene(loader.load()));

      model.stage.setTitle("MovieNight - ProfileScene");
    } catch (IOException e) {
      e.printStackTrace();
    }

    // set profile picture
    profile_picture.setImage(ProfilePicture.getProfilePic(User.getUserName()));
    // set username
    username_text.setText(User.getUserName());
    // get all streaming services into an pbservable list so the listview can present them
    ObservableList<String> services = FXCollections.observableArrayList();

    services.addAll(Server.getUsersStreamingServices(User.getUserName()).keySet());
    services_list.setItems(services);

    // get all the movies into a hash map and iterate through them collecting each movie title and
    // rating as well as
    // getting their movie class to generate a movie post url
    HashMap<String, Map<String, Object>> fav_movies =
        Server.getUsersFavouriteMovies(User.getUserName());

    Iterator movie_it = fav_movies.entrySet().iterator();
    while (movie_it.hasNext()) {
      Map.Entry pair = (Map.Entry) movie_it.next();
      Movie movie = api.getMovie(pair.getKey().toString());

      HBox movie_collection = new HBox();
      ImageView movie_poster =
          new ImageView(new Image(movie.getMoviePosterUrl(), 75, 112, false, false));
      VBox info = new VBox();
      Label movie_title = new Label(pair.getKey().toString());
      Label movie_rating = new Label(fav_movies.get(pair.getKey()).get("rating").toString());

      Button removeButton = new Button("Remove");

      movie_title.setMinSize(400, 10);
      movie_title.setFont(new Font(20));
      info.getChildren().addAll(movie_title, movie_rating, removeButton);
      info.setSpacing(15);

      movie_collection.getChildren().addAll(movie_poster, info);

      anchor.setMinHeight(anchor.getMinHeight() + 112);
      movie_ratings_vbox.setMinHeight(movie_ratings_vbox.getMinHeight() + 112);
      movie_ratings_vbox.getChildren().add(movie_collection);
      removeButton.setOnAction(
          event -> removeFavouriteMovie(movie.getMovieName(), movie_collection));
    }

    change_ProfilePicture_Button.setOnAction(event -> pressedSetProfilePicButton());
    hulu_click.setOnAction(event -> pressedAddServiceButton("Hulu"));
    netflix_click.setOnAction(event -> pressedAddServiceButton("Netflix"));
    crave_click.setOnAction(event -> pressedAddServiceButton("Crave"));
    prime_click.setOnAction(event -> pressedAddServiceButton("Prime"));

    hulu_remove.setOnAction(event -> pressedRemoveServiceButton("Hulu"));
    netflix_remove.setOnAction(event -> pressedRemoveServiceButton("Netflix"));
    crave_remove.setOnAction(event -> pressedRemoveServiceButton("Crave"));
    prime_remove.setOnAction(event -> pressedRemoveServiceButton("Prime"));
  }

  public void pressedBackButton(ActionEvent event) throws IOException {
    MainScene mainScene = new MainScene(model);
  }

  public void pressedSetProfilePicButton() {
    ProfilePicture.setProfilePic();
    profile_picture.setImage(ProfilePicture.getProfilePic(User.getUserName()));
  }

  public void pressedAddServiceButton(String newService) {
    Server.addStreamingService(newService, User.getUserName());
    ObservableList<String> services = FXCollections.observableArrayList();
    services.addAll(Server.getUsersStreamingServices(User.getUserName()).keySet());
    services_list.setItems(services);
  }

  public void pressedRemoveServiceButton(String removeService) {
    Server.removeStreamingService(removeService, User.getUserName());
    ObservableList<String> services = FXCollections.observableArrayList();
    services.addAll(Server.getUsersStreamingServices(User.getUserName()).keySet());
    services_list.setItems(services);
  }

  public void removeFavouriteMovie(String movieName, HBox hBoxToBeRemoved) {
    Server.removeFavouriteMovie(movieName, User.getUserName());
    movie_ratings_vbox.getChildren().remove(hBoxToBeRemoved);
  }
}
