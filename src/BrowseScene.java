import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import javafx.event.ActionEvent;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;


public class BrowseScene {
  private Model model;
  private ApiQuery apiQuery;
  private ArrayList<Movie> movieList;
  private ArrayList<Button> displayedMovies = new ArrayList<>();
  private ArrayList<Button> filteredMovies = new ArrayList<>();
  private int rowsDisplayed;
  private int checkPages;
  private int lastsize = 0;

  @FXML private CheckBox action_checkbox, adventure_checkbox, animation_checkbox,
      biography_checkbox, comedy_checkbox, crime_checkbox, documentary_checkbox, drama_checkbox,
      family_checkbox, fantasy_checkbox, filmnoir_checkbox, gameshow_checkbox, history_checkbox,
      horror_checkbox, music_checkbox, musical_checkbox, mystery_checkbox, news_checkbox,
      reality_checkbox, romance_checkbox, scifi_checkbox, sport_checkbox, talkshow_checkbox,
      thriller_checkbox, war_checkbox, western_checkbox;

  @FXML private CheckBox g_rating_checkbox, pg_rating_checkbox, pg13_rating_checkbox,
      r_rating_checkbox, nc17_rating_checkbox;

  @FXML private TextField startyear_textfield, endyear_textfield, searchbar_textfield;

  @FXML private GridPane movie_gridpane;

  @FXML private AnchorPane movie_anchorpane;

  @FXML private ScrollPane movie_scrollpane;

  public BrowseScene(Model newModel) {
    model = newModel;
    apiQuery = new ApiQuery();
    movieList = apiQuery.getAllCachedMovies();
    Collections.shuffle(movieList);
    rowsDisplayed = 0;

    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("BrowseScene.fxml"));

      loader.setController(this);

      model.stage.setScene(new Scene(loader.load()));

      model.stage.setTitle("MovieNight - BrowseScene");

      ScrollBar scrollBar = (ScrollBar) movie_scrollpane.lookup(".scroll-bar:vertical");
      scrollBar.valueProperty().addListener((observable, oldValue, newValue) -> {
        if ((Double) newValue == 1.0) {
          System.out.println("Bottom!");
          populateSearchField();
        }
      });

      populateSearchField();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void pressedBackButton(ActionEvent event) throws IOException {
    MainScene mainScene = new MainScene(model);
  }

  public void onSearch(ActionEvent event) throws IOException {
    SearchScene newSearchScene = new SearchScene(model, searchbar_textfield.getText());
  }

  public void yearFilterPressed(){
    lastsize = 0;
    filter();
  }

  public void filter(){
    int startYear;
    int endYear;
    try{
      startYear = Integer.parseInt(startyear_textfield.getText());
    }
    catch(Exception e){
      startYear = 0;
    }

    try{
      endYear = Integer.parseInt(endyear_textfield.getText());
    }
    catch(Exception e){
      endYear = 10000;
    }
    filterResults(startYear, endYear);
    condenseFilteredResults();
  }

  /**
  * Populate 3 rows of images for the browse page when called and
  * increments the rowDsiplayed counter.
   */
  public void populateSearchField() {
    for (int rowIndex = rowsDisplayed; rowIndex < rowsDisplayed + 3; rowIndex++) {
      movie_anchorpane.setMinSize(movie_anchorpane.getMinWidth(), movie_anchorpane.getMinHeight() + 137);
      for (int columnIndex = 0; columnIndex < 5; columnIndex++) {
        if(rowIndex * 5 + columnIndex < movieList.size()){
          Movie movie = movieList.get(rowIndex * 5 + columnIndex);
          Image image;
          try {
            image =
                new Image(
                    movie.getMoviePosterUrl(),
                    75,
                    112,
                    false,
                    false);
          } catch (Exception e) {
            String filePath = new File("").getAbsolutePath();
            FileInputStream inputstream;
            try {
              // use placeholder image if failed to retrieve from url
              inputstream = new FileInputStream(filePath + "/img/placeholder.png");
            } catch (FileNotFoundException fnfe) {
              break;
            }
            image = new Image(inputstream, 75, 112, false, false);
          }
          ImageView movieImage = new ImageView(image);

          Button button = new Button();
          button.setGraphic(movieImage);
          button.setMaxSize(75, 112);
          displayedMovies.add(button);
          button.setOnAction(actionEvent -> {altMovieScene movieScene = new altMovieScene(model,
              movie, model.stage.getScene());});
          movie_gridpane.add(button, columnIndex, rowIndex);
          GridPane.setHalignment(button, HPos.CENTER);
        }
      }

    }
    rowsDisplayed = rowsDisplayed + 3;
    filter();
  }

  private void filterResults(int startYear, int endYear){
    filteredMovies.clear();
    for (int i = 0; i < displayedMovies.size(); i++) {
      int year = Integer.parseInt(movieList.get(i).getMovieYear());
      if(year < startYear || year > endYear){
        movie_gridpane.getChildren().remove(displayedMovies.get(i));
      }
      else{
        filteredMovies.add(displayedMovies.get(i));
      }
    }
  }

  private void condenseFilteredResults(){
    movie_gridpane.getChildren().clear();
    for(int i = 0; i < filteredMovies.size(); i++){
      movie_gridpane.add(filteredMovies.get(i), i % 5, i / 5);
    }

    movie_anchorpane.setMinSize(movie_anchorpane.getMinWidth(), (float)(filteredMovies.size() / 5 * 137 - 10));
    if ((filteredMovies.size() < 15 || (filteredMovies.size() - lastsize) < 5) && displayedMovies.size() < movieList.size()) {
      System.out.println(filteredMovies.size() + ", " + lastsize);
      populateSearchField();
    }
    else{
      lastsize = filteredMovies.size();
    }
  }

}
