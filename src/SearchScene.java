import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
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


public class SearchScene {
  private Model model;
  private ApiQuery apiQuery;
  private ArrayList<SearchResult> searchList = new ArrayList<>();
  private int rowsDisplayed;
  private int pagesQueried = 0;
  private String searchText;

  //private ImageView movieImage;

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
      scrollBar.valueProperty().addListener((observable, oldValue, newValue) -> {
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

  }

  public void getResults(){
    searchList.addAll(apiQuery.searchMovies(searchText, pagesQueried + 1, pagesQueried + 2));
    pagesQueried += 2;
    for (int rowIndex = rowsDisplayed; rowIndex < rowsDisplayed + 4; rowIndex++) {
      if(rowIndex * 5 < searchList.size()){
        movie_anchorpane.setMinSize(movie_anchorpane.getMinWidth(), movie_anchorpane.getMinHeight() + 122);
      }
      for (int columnIndex = 0; columnIndex < 5; columnIndex++) {
        if(rowIndex * 5 + columnIndex < searchList.size()){
          Image image;
          try{
            image = new Image(searchList.get(rowIndex * 5 + columnIndex).getPosterUrl(), 75, 112, false, false);
          }
          catch (Exception e){
            String filePath = new File("").getAbsolutePath();
            FileInputStream inputstream;
            try{
               inputstream = new FileInputStream(filePath + "/img/placeholder.png");
            }
            catch (FileNotFoundException fnfe)
            {
              break;
            }
            image = new Image(inputstream, 75, 112, false, false);
          }

          ImageView movieImage = new ImageView(image);

          Button button = new Button();
          button.setGraphic(movieImage);
          button.setMaxSize(75, 112);
          movie_gridpane.add(button, columnIndex, rowIndex);
          GridPane.setHalignment(button, HPos.CENTER);
        }
        }
    }
    rowsDisplayed = rowsDisplayed + 4;
  }

}
