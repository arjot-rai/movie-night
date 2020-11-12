import java.util.ArrayList;

public class Event {
  private String eventName;

  private String eventLocation;

  private String eventDate;

  private User eventOrganizer;

  private ArrayList<User> eventGuestList;

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

  public ArrayList<User> getEventGuestList() {
    return eventGuestList;
  }

  public void addEventGuest(User guest) {
    this.eventGuestList.add(guest);
  }

  public void removeEventGuest(User guest) {
    this.eventGuestList.remove(guest);
  }

  public static void main(String[] args){
    // testing the event class
    User u = new User("me", "12345", 1);
    Event e = new Event("gettogether", "myplace", "2016-08-01", u, 1);
    e.addMovie(2);
    System.out.println(e.getEventMovies().toString());
    e.removeMovie(2);
    System.out.println(e.getEventMovies().toString());
    e.addEventGuest(u);
    System.out.println(e.getEventGuestList().toString());
    e.removeEventGuest(u);
    System.out.println(e.getEventGuestList().toString());
  }
}
