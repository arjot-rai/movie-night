public class Person {
  private String userName;

  private int userID;

  public Person(int id, String userName){
    this.userID = id;
    this.userName = userName;
  }

  public int getUserID() {
    return this.userID;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

}
