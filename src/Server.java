import org.neo4j.driver.*;
import org.neo4j.driver.Record;


import java.util.HashMap;
import java.util.Map;

import static org.neo4j.driver.Values.parameters;

public class Server {

  /**
   * To Do list:
   *  -create method for friends invitations
   *  -create events
   *  -remove events
   *  -invite to events
   *  -remove from events
   *  -vote on movies in events
   *  -user's profile pictures, set and get
   */

  //Driver to connect with the database server
  public static Driver driver;

  /**
   * This is a faked static class, should not be instanced
   */
  public Server(){}

  /**
   * connect to the database with a proper connection
   * @param uri localhost or the server IP
   * @param user the username of the server
   * @param password the password for the server
   */
  public static void connectServer(String uri, String user, String password){
    driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
  }

  /**
   * remove all nodes with the exception of the source node
   */
  public static void removeAll(){
    try(Session session = driver.session()){
      session.run("MATCH (n) DETACH DELETE n");
    }
  }

  /**
   * this function checks all usernames as to not create duplicates, only needed when a new user is created
   * @param username the name of the new user
   * @return true if the username is available, false otherwise
   */
  public static boolean checkUsernameAvailability(String username) {
    try (Session session = driver.session()) {
      Result result = session.run("MATCH (a:Person) WHERE a.username=$x1 RETURN a",
          parameters("x1", username));
      if(result.hasNext()){
        return false;
      }
      return true;
    }
  }

  /**
   * Logs in with username and password, password will be encrypted at some point in the future
   * @param username: person's username
   * @param password: person's password
   * @return returns a custom class for a tuple that contains whether the query was a success,
   * and if it was all the attributes along side it
   */
  public static LogInReturn attemptLogIn(String username, String password){
    try(Session session = driver.session()){
      Result result = session.run("MATCH (a:Person) WHERE toLower(a.username)=$x1 AND a.password=$x2" +
              " RETURN properties(a)",
          parameters("x1",username.toLowerCase(), "x2", password));
      if(result.hasNext()){
        Map<String, Object> returnable = result.next().get("properties(a)").asMap();
        //returnable.remove(password);
        //find a way to remove the password, or never query it at all
        return new LogInReturn(true, returnable);

      }
    }
    return new LogInReturn(false, null);
  }


  /**
   * this function returns all the attributes excluding passwords
   * @param username the username to get the attributes from
   * @return all the user's attributes in a map with the key as the attribute's name and the
   * value as the attribute's value
   */
  public static Map<String, Object> getAttributes(String username){
    try(Session session = driver.session()){
      Result result = session.run("MATCH (a:Person) WHERE toLower(a.username)=$x1" +
              " RETURN properties(a)",
          parameters("x1",username.toLowerCase()));
      if(result.hasNext()){
        //find a way to remove the password, or never query it at all
        return result.next().get("properties(a)").asMap();

      }
    }
    return null;
  }

  /**
   * Add new node into the database, password needs to be encrypted
   * @param username: the name of the new person, other features will be added later
   * @return true if completed, false otherwise
   */
  public static boolean addPerson(String username, String password, String first_name,
                  String last_name, String age) {
    if (checkUsernameAvailability(username)) {
      try (Session session = driver.session()) {
        session.writeTransaction(transaction -> transaction.run("MERGE (a:Person " +
                "{username:$x1, password:$x2, first_name:$x3, last_name:$x4, age:$x5})",
            parameters("x1", username, "x2", password, "x3", first_name,
                "x4", last_name, "x5", age)));
        return true;
      }
    }
    else{
      System.out.println("this name is already taken");
      return false;
    }
  }

  /**
   * remove a node from the database using their name
   * @param username: name of the person to remove, will use different features in the future
   */
  public static void removePerson(String username){
    try(Session session = driver.session()){
      session.writeTransaction(transaction -> transaction.run(
          "MATCH (a:Person) WHERE toLower(a.username)=$x DETACH DELETE a",
          parameters("x", username.toLowerCase())));
    }
  }


  /**
   * Create a two way friendship between two nodes
   * @param username1: name of the first new friend
   * @param username2: name of the second new friend
   */
  public static void createFriendship(String username1, String username2){
    try(Session session = driver.session()){
      session.writeTransaction(transaction -> transaction.run("MATCH(a:Person),(b:Person) " +
          "WHERE toLower(a.username)=$x1 and toLower(b.username)=$x2 " +
          "CREATE (a)-[f:FRIENDS]->(b) " +
          "RETURN a,b", parameters("x1", username1.toLowerCase(),
          "x2", username2.toLowerCase())));
      session.writeTransaction(transaction -> transaction.run("MATCH(a:Person),(b:Person) " +
          "WHERE toLower(a.username)=$x1 AND toLower(b.username)=$x2 " +
          "CREATE (a)-[f:FRIENDS]->(b) " +
          "RETURN a,b", parameters("x1", username2.toLowerCase(),
          "x2", username1.toLowerCase())));
    }
  }

  /**
   * Gets all of a user's friends, with all of their attributes
   * @param username the user to find their friends
   * @return this will return a hashmap with all the friend's usernames as the keys, and
   * their properties as the value map, with this keys as the attribute and value as the values
   */
  public static HashMap<String, Map<String, Object>> getAllFriends(String username){
    HashMap<String, Map<String, Object>> friends = new HashMap<>();
    try(Session session = driver.session()){
      Result result = session.run("Match (a:Person)-[:FRIENDS]->(b:Person) " +
          "WHERE a.username=$x1 Return properties(b) ", parameters("x1", username));
      while(result.hasNext()){
        Record record = result.next();
        Map<String, Object> friend = record.get("properties(b)").asMap();
        friends.put(record.get("properties(b)").get("username").asString(),
            friend);
      }
    }
    return friends;
  }

  /**
   * remove a friendship between two nodes, in both directions
   * @param username1: name of the first friend to remove
   * @param username2: name of the second friend to remove
   */
  public static void removeFriendship(String username1, String username2){
    try(Session session = driver.session()) {
      session.writeTransaction(transaction -> transaction.run("MATCH(a:Person),(b:Person) " +
              "WHERE a.username=$x1 AND b.username=$x2 " +
              "MATCH (a)-[f:FRIENDS]->(b) " +
              "DELETE f",
          parameters("x1", username1, "x2", username2)));
      session.writeTransaction(transaction -> transaction.run("MATCH(a:Person),(b:Person) " +
              "WHERE a.username=$x1 AND b.username=$x2 " +
              "MATCH (b)-[f:FRIENDS]->(a) " +
              "DELETE f",
          parameters("x1", username1, "x2", username2)));
    }
  }

  /**
   * counts how many nodes are in the database, excluding the source node
   * @return the int of how many nodes there are for testing purposes
   */
  public static int getNodeCount(){
    try(Session session = driver.session()) {
      Result result = session.run("MATCH (n) RETURN n");
      int count = (int)result.stream().count();
      return count;
    }
  }

  /**
   * close out of driver after use
   */
  public static void close(){

    driver.close();
  }
}
