import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class EventScene {
  private Model model;
  private Event thisEvent;
  private ApiQuery apiQuery;

  @FXML
  private TextArea attendees_text, date_text, location_text, streaming_text;

  @FXML
  private Button yes_attending, no_attending, back_button, add_attendee_button;

  @FXML
  private ImageView profile_picture;

  @FXML
  private Label event_name;

  @FXML
  private GridPane vote_gridpane;

  @FXML
  private AnchorPane vote_anchorpane, main_event_anchorpane;

  private ArrayList<Button> voteButtonList;

  public EventScene(Model newModel, Event newEvent) {
    model = newModel;
    thisEvent = newEvent;
    apiQuery = new ApiQuery();
    voteButtonList = new ArrayList<>();
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("EventScene.fxml"));
      loader.setController(this);
      model.stage.setScene(new Scene(loader.load()));
      model.stage.setTitle("MovieNight - EventScene");
      User.updateEvents();
      setUpFields();
      displayMovies();

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
    if (!thisEvent.getEventOrganizer().equals(User.getUserName())) {
      add_attendee_button.setDisable(true);
    }
  }

  public void pressedInviteButton(ActionEvent e) throws IOException {
    main_event_anchorpane.setDisable(true);
    InviteToEventScene inviteToEventScene = new InviteToEventScene(model, thisEvent);
    main_event_anchorpane.setDisable(false);
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
    }
  }

  public void pressedBackButton(ActionEvent e) throws IOException {
    MainScene mainScene = new MainScene(model);
  }

  /**
   * Set up the movies in the grid
   */
  public void displayMovies() {
    //System.out.println(Server.getEventAttendees(thisEvent.getEventID()).get(User.getUserName()).get());
    //System.out.println(Server.getUsersVotedMovie(User.getUserName(), thisEvent.getEventID()));
    String userVote = Server.getUsersVotedMovie(User.getUserName(), thisEvent.getEventID());

    HashMap<String, Map<String, Object>> movieVotes = Server.getEventMovies(thisEvent.getEventID());
    ArrayList<String> movieTitlesList = new ArrayList<>();
    movieVotes.forEach((movieTitle, votes) -> {movieTitlesList.add(movieTitle);});

    int moviesShown = 0;
    for (int rowIndex = 0; rowIndex < (movieVotes.size() / 2) + 1; rowIndex++) {
      for (int columnIndex = 0; columnIndex < 2; columnIndex++) {
        if (moviesShown < movieVotes.size()) {
          Movie movie = apiQuery.getMovie(movieTitlesList.get(rowIndex * 2 + columnIndex));
          VBox vBox = createVotingBox(movie, userVote);
          vote_gridpane.add(vBox, columnIndex, rowIndex);
          //TODO Create a button to bring you to movie page

          GridPane.setHalignment(vBox, HPos.CENTER);
        }
        moviesShown = moviesShown + 1;

      }
    }
    vote_anchorpane.setMinSize(vote_anchorpane.getPrefWidth(), ((1 + (float) movieVotes.size()) / 2 * 155));
    vote_anchorpane.setMaxSize(vote_anchorpane.getPrefWidth(), ((1 + (float) movieVotes.size()) / 2 * 155));
  }

  /**
   * Create the VBox
   * @param movie The movie to create the VBox for
   * @return returns the VBox with the movie picture, vote button and vote total
   */
  private VBox createVotingBox(Movie movie, String userVote) {
    Image image = new Image(movie.getMoviePosterUrl(), 92, 142, false, false);
    ImageView movieImage = new ImageView(image);

    VBox vBox = new VBox();
    vBox.getChildren().add(movieImage);


    Button voteButton = new Button("Vote");
    voteButtonList.add(voteButton);
    if (userVote.equals("no")) {
      voteButton.setDisable(true);
    } else {
      voteButton.setOnAction(e -> { pressedVoteButton(movie);});
    }
    Label voteLabel = new Label();
    String votes = Server.getEventMovies(thisEvent.getEventID()).get(movie.getMovieName()).get("vote").toString();
    voteLabel.setText("Votes: " + votes);


    HBox hBox = new HBox();
    hBox.getChildren().addAll(voteButton, voteLabel);

    vBox.getChildren().add(hBox);

    return vBox;
  }

  private void pressedVoteButton(Movie movie) {
    Server.userVoted(User.getUserName(), thisEvent.getEventID(), movie.getMovieName());
    //Update labels/buttons
    for (Button button : voteButtonList) {
      button.setDisable(true);
    }
  }


}
