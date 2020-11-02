import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class TestDriver {

    //list used to add streaming services straight to the person
    ArrayList<String> streamingServices;

    @Before
    public void personPreTest(){
        //pre-made list for the test person
        streamingServices = new ArrayList<>();
        streamingServices.add("Netflix");
        streamingServices.add("Hulu");

    }

    @Test
    public void personTesting(){
        //create new person for testing, including a pre-made streaming list
        Person person = new Person("user123", "password", 123, streamingServices);

        //change the user's password and check for the change
        person.changePassword("passwordChanged");
        Assert.assertEquals("Check if the password has been changed","passwordChanged",
                person.getPassword());
        System.out.println("The password changed");

        //Add to the streaming list and check for the update
        person.addStream("Crave");
        ArrayList<String> testList = new ArrayList<>();
        testList.add("Netflix");
        testList.add("Hulu");
        testList.add("Crave");
        Assert.assertArrayEquals("Check if Crave has been added",
                person.getStreamingServices().toArray(), testList.toArray());
        System.out.println("The streaming list has a new service");

        //Remove hulu from the list and check the update
        person.removeStream("Hulu");
        testList.remove("Hulu");
        Assert.assertArrayEquals("Check if Hulu has been removed",
                person.getStreamingServices().toArray(), testList.toArray());
        System.out.println("The streaming list removed a service");

        System.out.println("Great Success");
    }

    /*THESE TESTS WILL NOT WORK, THE SERVER DOES NOT WORK REMOTELY YET, ONLY LOCAL*/
/*
    Server server = new Server("bolt://localhost:7687", "neo4j", "password");

    @Before
    public void serverTestSetUp(){
        //this is temporary as it will literally delete all nodes but the source before beginning
        server.removeAllButSource();
        server.addPerson("Ada");
        server.addPerson("Alice");
        server.addPerson("Bob");
        server.createFriendship("Ada", "Bob");
        server.createFriendship("Alice", "Ada");
    }

    @Test
    public void serverTesting(){
        System.out.println("Testing get node count...");
        Assert.assertEquals(3, server.getNodeCount());
        System.out.println("Testing check username availability...");
        Assert.assertEquals(false, server.checkUsernameAvailability("Bob"));
        System.out.println("Testing add new person...");
        Assert.assertEquals(true, server.addPerson("Jeff"));
        System.out.println("Great Success");
    }

    @After
    public void serverTestClose(){
        server.close();
    }*/

}
