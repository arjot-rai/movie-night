import java.util.ArrayList;

public class User {
  private String userName;

  private String userPassword;

  private int userID;

  private ArrayList<String> streamingServices;

  private FriendList friendList;

  private MovieList movieList;

  private EventList eventList;

  public User(String userName, String password, int id){
    this.userName = userName;
    this.userPassword = password;
    this.userID = id;
    this.streamingServices = new ArrayList<>();
    this.friendList = new FriendList();
    this.movieList = new MovieList();
    this.eventList = new EventList();
  }

  public void changePassword(String newPassword){
    this.userPassword = newPassword;
  }

  public void addStream(String streamName){
    this.streamingServices.add(streamName);
  }

  public boolean removeStream(String streamName){
    return this.streamingServices.remove(streamName);
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserPassword() {
    return userPassword;
  }

  public void setUserPassword(String userPassword) {
    this.userPassword = userPassword;
  }

  public ArrayList<String> getStreamingServices(){
    return streamingServices;
  }

  public int getUserID() {
    return userID;
  }

  public FriendList getFriendList() {
    return friendList;
  }

  public MovieList getMovieList() {
    return movieList;
  }

  public EventList getEventList() {
    return eventList;
  }

  public static void main(String[] args) {
    // test for changePassword()
    User user = new User("me", "12345", 1);

    user.changePassword("new");
    System.out.println(user.getUserPassword());

    // test for addStream
    user.addStream("netflix");
    user.addStream("prime");
    System.out.println(user.getStreamingServices().toString());

    // test for removeStream
    System.out.println(user.removeStream("netflix")); // should be true
  }

}
