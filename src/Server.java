import org.neo4j.driver.*;
import org.neo4j.driver.Record;

import static org.neo4j.driver.Values.parameters;

public class Server {

    /**
     * THIS CLASS ONLY WORKS ON A LOCAL MACHINE AS OF RIGHT NOW, THE SERVER NEEDS TO BE MADE
     * REMOTE FIRST
     */

    Driver driver;

    /**
     * Create a connection with the server
     * @param uri uri to connect with server
     * @param user username to log in
     * @param password password to log in
     */
    public Server(String uri, String user, String password){
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
    }

    /**
     * remove all nodes with the exception of the source node
     */
    public void removeAllButSource(){
        try(Session session = driver.session()){
            session.run("MATCH (n) WHERE id(n)<>0 DETACH DELETE n");
        }
    }

    /**
     * this function checks all usernames as to not create duplicates, only needed when a new user is created
     * @param name the name of the new user
     * @return true if the username is available, false otherwise
     */
    public boolean checkUsernameAvailability(String name) {
        try (Session session = driver.session()) {
            Result result = session.run("MATCH (a:Source) WHERE (id(a)=0) " +
                    "MATCH (a)-[:SOURCE]->(b) RETURN b.name AS name");
            while (result.hasNext()) {
                org.neo4j.driver.Record record = result.next();
                if(name.toLowerCase().equals(record.get("name").asString().toLowerCase())) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * Add new node into the database
     * @param name: the name of the new person, other features will be added later
     * @return true if completed, false otherwise
     */
    public boolean addPerson(String name) {
        if (checkUsernameAvailability(name)) {
            try (Session session = driver.session()) {
                session.writeTransaction(transaction -> transaction.run("MERGE (a:Person {name: $x})",
                        parameters("x", name)));
                session.writeTransaction(transaction -> transaction.run("MATCH(a:Person),(b:Source) " +
                        "WHERE a.name=$x1 and id(b)=0 " +
                        "CREATE (b)-[f:SOURCE]->(a) " +
                        "RETURN a,b", parameters("x1", name)));
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
     * @param name: name of the person to remove, will use different features in the future
     */
    public void removePerson(String name){
        try(Session session = driver.session()){
            session.writeTransaction(transaction -> transaction.run(
                    "MATCH (a:Person {name: $x}) DETACH DELETE a", parameters("x", name)));
        }
    }

    /**
     * print off all the people in the data base who's name starts with A,this was for testing only
     * @param initial: the initial that all printed names will begin with
     */
    private void printPeople(String initial){
        try(Session session = driver.session()){
            Result result = session.run(
                    "MATCH (a:Person) WHERE a.name STARTS WITH $x RETURN a.name AS name",
                    parameters("x", initial));
            while(result.hasNext()){
                Record record = result.next();
                System.out.println(record.get("name").asString());
            }
        }
    }

    /**
     * Create a two way friendship between two nodes
     * @param name1: name of the first new friend
     * @param name2: name of the second new friend
     */
    public void createFriendship(String name1, String name2){
        try(Session session = driver.session()){
            session.writeTransaction(transaction -> transaction.run("MATCH(a:Person),(b:Person) " +
                    "WHERE a.name=$x1 and b.name=$x2 " +
                    "CREATE (a)-[f:FRIENDS]->(b) " +
                    "RETURN a,b", parameters("x1", name1, "x2", name2)));
            session.writeTransaction(transaction -> transaction.run("MATCH(a:Person),(b:Person) " +
                    "WHERE a.name=$x1 AND b.name=$x2 " +
                    "CREATE (a)-[f:FRIENDS]->(b) " +
                    "RETURN a,b", parameters("x1", name2, "x2", name1)));
        }
    }

    /**
     * remove a friendship between two nodes, in both directions
     * @param name1: name of the first friend to remove
     * @param name2: name of the second friend to remove
     */
    public void removeFriendship(String name1, String name2){
        try(Session session = driver.session()) {
            session.writeTransaction(transaction -> transaction.run("MATCH(a:Person),(b:Person) " +
                            "WHERE a.name=$x1 AND b.name=$x2 " +
                            "MATCH (a)-[f:FRIENDS]->(b) " +
                            "DELETE f",
                    parameters("x1", name1, "x2", name2)));
            session.writeTransaction(transaction -> transaction.run("MATCH(a:Person),(b:Person) " +
                            "WHERE a.name=$x1 AND b.name=$x2 " +
                            "MATCH (b)-[f:FRIENDS]->(a) " +
                            "DELETE f",
                    parameters("x1", name1, "x2", name2)));
        }
    }

    /**
     * counts how many nodes are in the database, excluding the source node
     * @return the int of how many nodes there are for testing purposes
     */
    public int getNodeCount(){
        try(Session session = driver.session()) {
            Result result = session.run("MATCH (n) RETURN n");
            int count = (int)result.stream().count() - 1;
            return count;
        }
    }

    /**
     * close out of driver after use
     */
    public void close(){

        driver.close();
    }
}
