import java.util.ArrayList;
import java.util.HashMap;

public class Event {
  private String eventName;

  private String eventLocation;

  private String eventDate;

  private String eventOrganizer;

  private ArrayList<String> eventGuestList;

  private String eventID;

  private ArrayList<String> eventMovies;

  private ArrayList<String> eventStreamingServices;

  // A HashMap mapping the movieID with the number of votes it has received.
  private HashMap<String, Integer> movieVoteTotals;

  // A HashMap mapping the movieID to a list of usernames who have already voted on that movieID
  private HashMap<String, ArrayList<String>> movieVotingRecord;

  public Event(String name, String location, String date, String organizer, String id){
    this.eventName = name;
    this.eventLocation = location;
    this.eventDate = date;
    this.eventOrganizer = organizer;
    this.eventID = id;
    this.eventGuestList = new ArrayList<>();
    this.eventMovies = new ArrayList<>();
    this.eventStreamingServices = new ArrayList<>();
    this.movieVotingRecord = new HashMap<>();
    this.movieVoteTotals = new HashMap<>();
  }

  public String getEventName() {
    return eventName;
  }

  public void setEventName(String eventName) {
    this.eventName = eventName;
  }

  public String getEventLocation() {
    return eventLocation;
  }

  public void setEventLocation(String eventLocation) {
    this.eventLocation = eventLocation;
  }

  public String getEventDate() {
    return eventDate;
  }

  public void setEventDate(String eventDate) {
    this.eventDate = eventDate;
  }

  public String getEventOrganizer() {
    return eventOrganizer;
  }

  public void setEventOrganizer(String eventOrganizer) {
    this.eventOrganizer = eventOrganizer;
  }

  public String getEventID() {
    return eventID;
  }

  public void setEventID(String eventID) {
    this.eventID = eventID;
  }

  public ArrayList<String> getEventMovies(){
    return this.eventMovies;
  }

  public void addMovie(String title){
    this.eventMovies.add(title);
    this.movieVotingRecord.put(title, new ArrayList<String>());
  }

  public void removeMovie(String title){
    // movieID has to be casted to Object, otherwise treated as an index
    this.eventMovies.remove((Object)title);
    this.movieVotingRecord.remove(title);
  }

  public ArrayList<String> getEventGuestList() {
    return eventGuestList;
  }

  public void addEventGuest(String id) {
    this.eventGuestList.add(id);
  }

  public void removeEventGuest(String id) {
    this.eventGuestList.remove(id);
  }

  public ArrayList<String> getEventStreamingServices() { return eventStreamingServices; }

  public void addStreamingService(String service) { this.eventStreamingServices.add(service); }

  public void removeStreamingService(String service) { this.eventStreamingServices.remove(service); }

  public HashMap<String, ArrayList<String>> getMovieVotingRecord() { return movieVotingRecord; }

  public void addVoter(String title, String username) {
    ArrayList<String> tempArray = movieVotingRecord.get(title);
    tempArray.add(username);
    movieVotingRecord.put(title, tempArray);
    addMovieVote(title);
  }

  public void removeVoter(String title, String username) {
    ArrayList<String> tempArray = movieVotingRecord.get(title);
    tempArray.remove(username);
    movieVotingRecord.put(title, tempArray);
    removeMovieVote(title);
  }

  public HashMap<String, Integer> getMovieVoteTotals() { return movieVoteTotals; }

  private void addMovieVote(String title) {
    Integer tempInt = movieVoteTotals.get(title);
    tempInt = tempInt + 1;
    movieVoteTotals.put(title, tempInt);
  }

  private void removeMovieVote(String title) {
    Integer tempInt = movieVoteTotals.get(title);
    tempInt = tempInt - 1;
    movieVoteTotals.put(title, tempInt);
  }


  public static void main(String[] args){

  }
}
