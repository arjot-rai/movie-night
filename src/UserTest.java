import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Array;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserTest {

  @Test
  void testInitialization(){
    User.initialize("group8", "CMPT370", 1);
    Assertions.assertAll("initialization",
        () -> assertEquals("group8", User.getUserName()),
        () -> assertEquals("CMPT370", User.getUserPassword()),
        () -> assertEquals(1, User.getUserID()));
  }

  @Test
  void testPassword(){
    User.initialize("group8", "CMPT370", 1);
    User.changePassword("newCMPT370");
    Assertions.assertEquals("newCMPT370", User.getUserPassword());
  }

  @Test
  void testStreamingServices(){
    User.addStream("Netflix");
    User.addStream("Prime");
    String[] expected = new String[]{"Netflix", "Prime"};
    Assertions.assertArrayEquals(expected, User.getStreamingServices().toArray());
    User.removeStream("Netflix");
    expected = new String[]{"Prime"};
    Assertions.assertArrayEquals(expected, User.getStreamingServices().toArray());
  }
}