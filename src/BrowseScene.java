import javafx.event.ActionEvent;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;


public class BrowseScene {
  private Model model;

  @FXML private CheckBox action_checkbox, adventure_checkbox, animation_checkbox,
      biography_checkbox, comedy_checkbox, crime_checkbox, documentary_checkbox, drama_checkbox,
      family_checkbox, fantasy_checkbox, filmnoir_checkbox, gameshow_checkbox, history_checkbox,
      horror_checkbox, music_checkbox, musical_checkbox, mystery_checkbox, news_checkbox,
      reality_checkbox, romance_checkbox, scifi_checkbox, sport_checkbox, talkshow_checkbox,
      thriller_checkbox, war_checkbox, western_checkbox;

  @FXML private CheckBox g_rating_checkbox, pg_rating_checkbox, pg13_rating_checkbox,
      r_rating_checkbox, nc17_rating_checkbox;

  public BrowseScene(Model newModel) {
    model = newModel;

    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("BrowseScene.fxml"));

      loader.setController(this);

      model.stage.setScene(new Scene(loader.load()));

      model.stage.setTitle("MovieNight - BrowseScene");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void pressedBackButton(ActionEvent event) throws IOException {
    MainScene mainScene = new MainScene(model);
  }

  public void onSearch(ActionEvent event) throws IOException {

  }

}
