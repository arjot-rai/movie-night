import java.util.ArrayList;
import java.util.Map;
import jdk.jfr.Frequency;

public class User{

  private static String userName = "";

  private static String first_name;

  private static String last_name;

  private static ArrayList<String> streamingServices = new ArrayList<>();

  private static FriendList friendList = new FriendList();

  private static MovieList movieList = new MovieList();

  private static EventList eventList = new EventList();

  public User(){

  }

  /**
   *
   * @param attributes:
   */
  public static void initialize(Map<String, Object> attributes){
    User.userName = userName;
    if(attributes.containsKey("username")){
      userName = attributes.get("username").toString();
    }
    if(attributes.containsKey("first_name")){
      first_name = attributes.get("first_name").toString();
    }
    if(attributes.containsKey("last_name")){
      last_name = attributes.get("last_name").toString();
    }

    updateFriends();
    updateFriendRequests();
    updatePendingRequests();
  }

  public static String getUserName() {
    return User.userName;
  }

  public static void setUserName(String userName) {
    User.userName = userName;
  }

  public static void addStream(String streamName){
    User.streamingServices.add(streamName);
  }

  public static boolean removeStream(String streamName){
    return User.streamingServices.remove(streamName);


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

  public static void updateFriends(){
    Object[] friendsNames = Server.getAllFriends(userName).keySet().toArray();
    for (Object name : friendsNames) {
      friendList.addFriend(name.toString());
    }
  }

  public static void updateFriendRequests(){
    Object[] friendRequests = Server.getFriendRequests(userName).keySet().toArray();
    for (Object name : friendRequests) {
      friendList.addInvitation(name.toString());
    }
  }

  public static void updatePendingRequests(){
    Object[] pendingRequests = Server.getPendingRequests(userName).keySet().toArray();
    for (Object name : pendingRequests) {
      friendList.addPending((name.toString()));
    }
  }

  public static void clearAttributes(){
    userName = "";
    first_name = "";
    last_name = "";
  }

  public static void main(String[] args) {
    //User.initialize("soro");
    //FriendList f = User.getFriendList();
    //f.addFriend(userName);
    //f.addFriend(userName);
    //System.out.println(User.getFriendList().confirmedFriends.toString());
  }

}
