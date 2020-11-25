import java.util.ArrayList;

public class Model {

  public User user;
  public ApiQuery apiQuery;
  private ArrayList<SceneModelListener> subscribers;

  public Model() {
    apiQuery = new ApiQuery();
    user = new User();
    subscribers = new ArrayList<>();
  }

  public void addSubscribers(SceneModelListener sub) { subscribers.add(sub); }

  public void notifySubscribers() { subscribers.forEach(sub -> sub.modelChanged()); }

}
