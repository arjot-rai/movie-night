import java.util.ArrayList;
 
public class EventList {
  public ArrayList<Integer> confirmedEvents;

  public ArrayList<Integer> eventInvites;

  public EventList(){
    confirmedEvents = new ArrayList<>();
    eventInvites = new ArrayList<>();
  }

  public void addEvent(int eventID){
    confirmedEvents.add(eventID);
  }

  public void removeEvent(int eventID){
    confirmedEvents.remove((Object)eventID);
  }

  public void acceptInvite(int eventID){
    eventInvites.remove((Object)eventID);
    confirmedEvents.add(eventID);
  }

  public void rejectInvite(int eventID){
    eventInvites.remove((Object)eventID);
  }

  public void addInvitation(int eventID){
    eventInvites.add(eventID);
  }

  public static void main(String[] args){

  }
}
