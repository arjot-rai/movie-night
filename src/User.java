import java.util.ArrayList;

public class User{

  private static String userName = "";

  private static int userID = 0;

  private static String userPassword = "";

  private static ArrayList<String> streamingServices = new ArrayList<>();

  private static FriendList friendList = new FriendList();

  private static MovieList movieList = new MovieList();

  private static EventList eventList = new EventList();

  public User(){

  }

  /**
   *
   * @param userName: String; the user name of the user
   * @param password: String; password the user is using
   * @param id: int; a unique identifier for the user
   */
  public static void initialize(String userName, String password, int id){
    User.userID = id;
    User.userName = userName;
    User.userPassword = password;
  }

  public static void setUserID(int id){
    User.userID = id;
  }

  public static int getUserID(){
    return User.userID;
  }

  public static String getUserName() {
    return User.userName;
  }

  public static void setUserName(String userName) {
    User.userName = userName;
  }

  public static void changePassword(String newPassword){
    User.userPassword = newPassword;
  }

  public static void addStream(String streamName){
    User.streamingServices.add(streamName);
  }

  public static boolean removeStream(String streamName){
    return User.streamingServices.remove(streamName);
  }

  public static String getUserPassword() {
    return User.userPassword;
  }

  public static void setUserPassword(String userPassword) {
    User.userPassword = userPassword;
  }

  public static ArrayList<String> getStreamingServices(){
    return User.streamingServices;
  }

  public static FriendList getFriendList() {
    return friendList;
  }

  public static MovieList getMovieList() {
    return movieList;
  }

  public static EventList getEventList() {
    return eventList;
  }

  public static void main(String[] args) {

  }

}
