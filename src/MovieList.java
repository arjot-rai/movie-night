import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

public class MovieList {
  public Dictionary<Integer, Float> movieRatings;

  public ArrayList<Integer> movies;

  public MovieList(){
    movies = new ArrayList<>();
    movieRatings = new Hashtable<>();
  }

  public void addMovie(int movieID){
    this.movies.add(movieID);
  }

  public boolean giveRating(int movieID, float rating){
    // rating can only be given if the movie is in user's list
    if(this.movies.contains(movieID)){
      this.movieRatings.put(movieID, rating);
      return true;
    }
    return false;
  }

  public static void main(String[] args){
    //testing MovieList class
    MovieList movieList = new MovieList();

    movieList.addMovie(1);
    if(movieList.giveRating(1, 4.3f)){
      System.out.println(movieList.movieRatings.get(1));
    } else{
      System.out.println("rating should not have been added");
    }

  }

}