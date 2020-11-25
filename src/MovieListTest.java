import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MovieListTest {
  @Test
  void testComparingMovieLists(){
    MovieList firstList = new MovieList();
    MovieList secondList = new MovieList();
    firstList.addMovie(1);
    firstList.addMovie(2);
    secondList.addMovie(1);
    secondList.addMovie(3);
    MovieList movieList = firstList.compareMovies(secondList);
    System.out.println(movieList.movies.toString());
  }
}