import java.util.ArrayList;
 
public class EventList {
  public ArrayList<Event> confirmedEvents;

  public ArrayList<Event> eventInvites;

  public EventList(){
    confirmedEvents = new ArrayList<>();
    eventInvites = new ArrayList<>();
  }

  public void addEvent(Event eventID){
    confirmedEvents.add(eventID);
  }

  public void removeEvent(String eventID){
    confirmedEvents.remove((Object)eventID);
  }

  public void acceptInvite(Event eventID){
    eventInvites.remove((Object)eventID);
    confirmedEvents.add(eventID);
  }

  public void rejectInvite(Event eventID){
    eventInvites.remove((Object)eventID);
  }

  public void addInvitation(Event eventID){
    eventInvites.add(eventID);
  }

  public static void main(String[] args){

  }
}
