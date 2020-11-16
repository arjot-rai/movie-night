import java.util.ArrayList;

public class Event {
  private String eventName;

  private String eventLocation;

  private String eventDate;

  private User eventOrganizer;

  private ArrayList<Integer> eventGuestList;

  private int eventID;

  private ArrayList<Integer> eventMovies;

  public Event(String name, String location, String date, User organizer, int id){
    this.eventName = name;
    this.eventLocation = location;
    this.eventDate = date;
    this.eventOrganizer = organizer;
    this.eventID = id;
    this.eventGuestList = new ArrayList<>();
    this.eventMovies = new ArrayList<>();
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

  public User getEventOrganizer() {
    return eventOrganizer;
  }

  public void setEventOrganizer(User eventOrganizer) {
    this.eventOrganizer = eventOrganizer;
  }

  public int getEventID() {
    return eventID;
  }

  public void setEventID(int eventID) {
    this.eventID = eventID;
  }

  public ArrayList<Integer> getEventMovies(){
    return this.eventMovies;
  }

  public void addMovie(int movieID){
    this.eventMovies.add(movieID);
  }

  public void removeMovie(int movieID){
    // movieID has to be casted to Object, otherwise treated as an index
    this.eventMovies.remove((Object)movieID);
  }

  public ArrayList<Integer> getEventGuestList() {
    return eventGuestList;
  }

  public void addEventGuest(int id) {
    this.eventGuestList.add(id);
  }

  public void removeEventGuest(int id) {
    this.eventGuestList.remove(id);
  }

  public static void main(String[] args){

  }
}