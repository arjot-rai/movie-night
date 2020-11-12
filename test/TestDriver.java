import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestDriver {


  @Before
  public void serverTestSetUp(){
    //Server.connectServer("bolt://174.2.15.198:7687", "neo4j", "cmpt370");
    //this line is for my local testing, use my external IP above
    Server.connectServer("bolt://localhost:7687", "neo4j", "password");

    //this top line is temporary as it will literally delete all
    // nodes but the source before beginning
    Server.removeAll();
    Server.addPerson("AdaUN", "password", "Ada", "AdaLN", "21");
    Server.addPerson("AliceUN", "password", "Alice", "AliceLN", "22");
    Server.addPerson("BobUN", "password", "Bob", "BobLN", "23");
    Server.createFriendship("AdaUN", "BobUN");
    Server.createFriendship("AliceUN", "AdaUN");

  }

  @Test
  public void serverTesting(){
    System.out.println("Testing get node count...");
    Assert.assertEquals(3, Server.getNodeCount());
    System.out.println("Passes");

    System.out.println("Testing check username availability...");
    Assert.assertEquals(false, Server.checkUsernameAvailability("BobUN"));
    System.out.println("Passes");

    System.out.println("Testing attempted log in failure...");
    LogInReturn logInAttempt1 = Server.attemptLogIn("BobUN", "asd");
    System.out.println("Passes");

    Assert.assertEquals(false, logInAttempt1.getSuccess());
    System.out.println("Testing add new person...");
    Assert.assertEquals(true, Server.addPerson("JeffUN", "password",
        "Jeff", "JeffLN", "21"));
    System.out.println("Passes");

    System.out.println("Testing attempted log in success...");
    LogInReturn logInAttempt2 = Server.attemptLogIn("JeffUN", "password");
    Assert.assertEquals(true, logInAttempt2.getSuccess());
    System.out.println("Passes");

    System.out.println("Testing get attributes...");
    Assert.assertEquals("AdaUN", Server.getAttributes("AdaUN").get("username"));
    System.out.println("Passes");

    System.out.println("Testing remove person...");
    Server.removePerson("JeffUN");
    Assert.assertEquals(3, Server.getNodeCount());
    Assert.assertEquals(null, Server.getAttributes("JeffUN"));
    Assert.assertEquals(true, Server.checkUsernameAvailability("JeffUN"));
    System.out.println("Passes");

    System.out.println("Testing get all friends...");
    Assert.assertEquals(true, Server.getAllFriends("AdaUN").keySet().contains("BobUN"));
    Assert.assertEquals(true, Server.getAllFriends("AdaUN").keySet().contains("AliceUN"));
    System.out.println("Passes");

    System.out.println("Testing remove friendship...");
    Server.removeFriendship("AdaUN", "AliceUN");
    Assert.assertEquals(false, Server.getAllFriends("AdaUN").keySet().contains("AliceUN"));
    Assert.assertEquals(true, Server.getAllFriends("AdaUN").keySet().contains("BobUN"));
    Assert.assertEquals(false, Server.getAllFriends("AliceUN").keySet().contains("AdaUN"));
    System.out.println("Passes");

    System.out.println("Testing create friendship...");
    Server.createFriendship("AdaUN", "AliceUN");
    Assert.assertEquals(true, Server.getAllFriends("AdaUN").keySet().contains("AliceUN"));
    Assert.assertEquals(true, Server.getAllFriends("AliceUN").keySet().contains("AdaUN"));
    System.out.println("Passes");


    System.out.println("Great Success");

  }

  @After
  public void serverTestClose(){
    Server.close();
  }

}
