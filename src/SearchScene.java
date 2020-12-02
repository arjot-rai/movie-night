import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Node;
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

public class SearchScene {
  private Model model;
  private ApiQuery apiQuery;
  private ArrayList<SearchResult> searchList = new ArrayList<>();
  private ArrayList<Button> displayedMovies = new ArrayList<>();
  private ArrayList<Button> filteredMovies = new ArrayList<>();
  private int rowsDisplayed;
  private int pagesQueried = 0;
  private String searchText;
  private int checkPages = 0;

  @FXML private TextField startyear_textfield, endyear_textfield, searchbar_textfield;

  @FXML private GridPane movie_gridpane;

  @FXML private AnchorPane movie_anchorpane;

  @FXML private ScrollPane movie_scrollpane;

  public SearchScene(Model newModel, String searchText) {
    model = newModel;
    apiQuery = new ApiQuery();
    rowsDisplayed = 0;
    this.searchText = searchText;

    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("SearchScene.fxml"));

      loader.setController(this);

      model.stage.setScene(new Scene(loader.load()));

      model.stage.setTitle("MovieNight - SearchScene");

      searchbar_textfield.setText(searchText);

      ScrollBar scrollBar = (ScrollBar) movie_scrollpane.lookup(".scroll-bar:vertical");
      scrollBar
          .valueProperty()
          .addListener(
              (observable, oldValue, newValue) -> {
                if ((Double) newValue == 1.0) {
                  System.out.println("Bottom!");
                  getResults();
                }
              });

      getResults();
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
    checkPages = 0;
    filterYear();
  }

  public void filterYear(){
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
   * tries to fetch the next page of search results and display them in the window
   */
  public void getResults() {
    searchList.addAll(apiQuery.searchMovies(searchText, pagesQueried + 1, pagesQueried + 2));
    pagesQueried += 2; // 2x10 movies to fill the screen
    for (int rowIndex = rowsDisplayed; rowIndex < rowsDisplayed + 4; rowIndex++) {
      if (rowIndex * 5 < searchList.size()) { // check if there are more movies to add
        movie_anchorpane.setMinSize(
            movie_anchorpane.getMinWidth(), movie_anchorpane.getMinHeight() + 132);
      }
      for (int columnIndex = 0; columnIndex < 5; columnIndex++) {
        if (rowIndex * 5 + columnIndex
            < searchList.size()) { // check that you havent run out of results
          Image image;
          try {
            image =
                new Image(
                    searchList.get(rowIndex * 5 + columnIndex).getPosterUrl(),
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
          SearchResult result = searchList.get(rowIndex * 5 + columnIndex);
          button.setOnAction(actionEvent -> {altMovieScene movieScene = new altMovieScene(model,
              apiQuery.getMovie(result.getImdbID()), model.stage.getScene());});
          movie_gridpane.add(button, columnIndex, rowIndex);
          GridPane.setHalignment(button, HPos.CENTER);
        }
      }
    }
    rowsDisplayed = rowsDisplayed + 4;
    filterYear();
    condenseFilteredResults();
  }

  private void filterResults(int startYear, int endYear){
    filteredMovies.clear();
    for (int i = 0; i < searchList.size(); i++) {
        int year = Integer.parseInt(searchList.get(i).getYear());
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

    movie_anchorpane.setMinSize(movie_anchorpane.getPrefWidth(), (float)(filteredMovies.size() / 5 * 130));
    movie_anchorpane.setMaxSize(movie_anchorpane.getPrefWidth(), (float)(filteredMovies.size() / 5 * 130));
    if (filteredMovies.size() < 15 && checkPages < 3) {
      checkPages++;
      getResults();
    }
  }
}
