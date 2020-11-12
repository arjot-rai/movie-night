import java.util.ArrayList;

public class FriendList {
  public ArrayList<Integer> confirmedFriends;

  public ArrayList<Integer> friendInvites;

  public FriendList(){
    confirmedFriends = new ArrayList<>();
    friendInvites = new ArrayList<>();
  }

  public void addFriend(int userID){
    confirmedFriends.add(userID);
  }

  public void removeFriend(int userID){
    confirmedFriends.remove((Object)userID);
  }

  public void acceptInvite(int userID){
    // userID has to be casted to Object, otherwise treated as an index
    friendInvites.remove((Object)userID);
    confirmedFriends.add(userID);
  }

  public void rejectInvite(int userID){
    // userID has to be casted to Object, otherwise treated as an index
    friendInvites.remove((Object)userID);
  }

  public void addInvitation(int userID){
    friendInvites.add(userID);
  }

  public static void main(String[] args){

  }
}
