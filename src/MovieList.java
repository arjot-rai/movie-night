import java.util.Dictionary;
import java.util.Enumeration;

public class MovieList {
  public Dictionary<Movie, Float> movieRatings;

  public MovieList(){
    movieRatings = new Dictionary<Movie, Float>() {
      @Override
      public int size() {
        return 0;
      }

      @Override
      public boolean isEmpty() {
        return false;
      }

      @Override
      public Enumeration<Movie> keys() {
        return null;
      }

      @Override
      public Enumeration<Float> elements() {
        return null;
      }

      @Override
      public Float get(Object key) {
        return null;
      }

      @Override
      public Float put(Movie key, Float value) {
        return null;
      }

      @Override
      public Float remove(Object key) {
        return null;
      }
    };
  }

  public static void main(String[] args){

  }

}
