import java.util.ArrayList;

public class Person {

  //User's saved info, this will be saved on the service
  String username;
  String password;
  int userID;
  ArrayList<String> streamingServices;

  /**
   * this is used for creating a new person
   * @param username: the new username of the person
   * @param password: the new password of the person
   * @param userID: the userID given to the node from neo4j when connected to the database
   *       (needs further implementation, just a random number for now)
   * @param streamingServices: the list of streaming services the new person is subscribed to
   */
  public Person(String username, String password, int userID, ArrayList<String> streamingServices){
    this.username = username;
    this.password = password;
    this.userID = userID;
    this.streamingServices = streamingServices;
  }

  /**
   * lets the user change their password
   * @param newPassword
   */
  public void changePassword(String newPassword){
    this.password = newPassword;
  }

  /**
   * returns the password from the user, mainly used for test purposes as of now
   * @return the password
   */
  public String getPassword(){
    return this.password;
  }

  /**
   * Currently takes a string to be added to the streaming services list, this will be restricted
   * to specific platforms only in the future
   * @param newStream the name of the new streaming service
   */
  public void addStream(String newStream){
    this.streamingServices.add(newStream);
  }

  /**
   * remove a service by name
   * @param removable the name of the service to remove
   */
  public void removeStream(String removable){
    this.streamingServices.remove(removable);
  }

  /**
   *  this returns the list of all the user's streaming services
   * @return the list of streaming services
   */
  public ArrayList<String> getStreamingServices(){
    return this.streamingServices;
  }

}
