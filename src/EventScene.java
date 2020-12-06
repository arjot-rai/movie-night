
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

public class EventScene {
  private Model model;
  private Event thisEvent;
  private ApiQuery apiQuery;

  @FXML
  private TextArea attendees_text, date_text, location_text, streaming_text;

  @FXML
  private Button yes_attending, no_attending, back_button;

  @FXML
  private ImageView profile_picture;

  @FXML
  private Label event_name;

  public EventScene(Model newModel, Event newEvent) {
    model = newModel;
    thisEvent = newEvent;
    apiQuery = new ApiQuery();
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("EventScene.fxml"));
      loader.setController(this);
      model.stage.setScene(new Scene(loader.load()));
      model.stage.setTitle("MovieNight - EventScene");
      setUpFields();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Set up all the UI elements in the scene
   *
   */
  private void setUpFields() {
    event_name.setText(thisEvent.getEventName());
    StringBuilder guestText = new StringBuilder();
    for (String guestID : thisEvent.getEventGuestList()) {
      guestText.append(guestID).append("\n");
    }
    attendees_text.setText(guestText.toString());
    attendees_text.setEditable(false);
    date_text.setText(thisEvent.getEventDate());
    date_text.setEditable(false);
    location_text.setText(thisEvent.getEventLocation());
    location_text.setEditable(false);
    StringBuilder serviceText = new StringBuilder();
    for (String service : thisEvent.getEventStreamingServices()) {
      serviceText.append(service).append("\n");
    }
    streaming_text.setText(serviceText.toString());
    streaming_text.setEditable(false);
    profile_picture.setImage(ProfilePicture.getProfilePic(thisEvent.getEventOrganizer()));
  }


  public void pressedYesAttending(ActionEvent e) throws IOException {
    if (!User.getEventList().confirmedEvents.contains(thisEvent)) {
      Server.acceptEventInvite(User.getUserName(), thisEvent.getEventID());
      //TODO: Change these buttons to Accepted or something like that
    }
  }

  public void pressedNoAttending(ActionEvent e) throws IOException {
    if (User.getEventList().confirmedEvents.contains(thisEvent)) {
      Server.removeEventInvite(User.getUserName(), thisEvent.getEventID());
      MainScene mainScene = new MainScene(model);
    }
  }

  public void pressedBackButton(ActionEvent e) throws IOException {
    MainScene mainScene = new MainScene(model);
  }

  /**
   * Set up the movies in the grid
   */
  public void displayMovies() {
    for (int rowIndex = 0; rowIndex < (thisEvent.getEventMovies().size() / 2) + 1; rowIndex++) {
      for (int columnIndex = 0; columnIndex < 2; columnIndex++) {
        Movie movie = apiQuery.getMovie(thisEvent.getEventMovies().get(rowIndex * 2 + columnIndex));
        Button button = new Button();
        button.setMaxSize(75, 112);
        button.setOnAction(e -> {});

      }
    }
  }

}
