
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

  private void setUpFields() {
    event_name.setText(thisEvent.getEventName());
    StringBuilder guestText = new StringBuilder();
    for (String guestID : thisEvent.getEventGuestList()) {
      guestText.append(guestID).append("\n");
    }
    attendees_text.setText(guestText.toString());
    date_text.setText(thisEvent.getEventDate());
    location_text.setText(thisEvent.getEventLocation());
    StringBuilder serviceText = new StringBuilder();
    for (String service : thisEvent.getEventStreamingServices()) {
      serviceText.append(service).append("\n");
    }
    streaming_text.setText(serviceText.toString());
  }


  public void pressedYesAttending(ActionEvent event) throws IOException {

  }

  public void pressedNoAttending(ActionEvent event) throws IOException {

  }

  public void pressedBackButton(ActionEvent event) throws IOException {
    MainScene mainScene = new MainScene(model);
  }

}
