import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ApiQueryTest {

  @Test
  void testMovieName() {
    ApiQuery apiQuery = new ApiQuery();
    Movie movie = apiQuery.getMovie("star wars");
    Assertions.assertEquals(movie.getMovieName(), "Star Wars: Episode IV - A New Hope");
  }

  @Test
  void testMovieID() {
    ApiQuery apiQuery = new ApiQuery();
    Movie movie = apiQuery.getMovie("star wars");
    Assertions.assertEquals(76759, movie.getMovieID());
  }

  @Test
  void testMovieDirector() {
    ApiQuery apiQuery = new ApiQuery();
    Movie movie = apiQuery.getMovie("star wars");
    Assertions.assertEquals("George Lucas", movie.getMovieDirector());
  }

  @Test
  void testMovieGenres() {
    ApiQuery apiQuery = new ApiQuery();
    Movie movie = apiQuery.getMovie("star wars");
    ArrayList<String> expected = new ArrayList<>();
    expected.add("Action");
    expected.add("Adventure");
    expected.add("Fantasy");
    expected.add("Sci-Fi");
    Assertions.assertEquals(expected, movie.getMovieGenre());
  }

  @Test
  void testMovieActors() {
    ApiQuery apiQuery = new ApiQuery();
    Movie movie = apiQuery.getMovie("star wars");
    ArrayList<String> expected = new ArrayList<>();
    expected.add("Mark Hamill");
    expected.add("Harrison Ford");
    expected.add("Carrie Fisher");
    expected.add("Peter Cushing");
    Assertions.assertEquals(expected, movie.getMovieActor());
  }

  @Test
  void testInvalidIDNineDigit() {
    ApiQuery apiQuery = new ApiQuery();
    Movie movie = apiQuery.getMovie(123456789);
    assertNull(movie);
    assertEquals(apiQuery.getError(), "Error: invalid IMDB ID");
  }

  @Test
  void testInvalidIDNonexistent() {
    ApiQuery apiQuery = new ApiQuery();
    Movie movie = apiQuery.getMovie(9999999);
    assertNull(movie);
    assertEquals(apiQuery.getError(), "Error: could not read JSON");
  }

  @Test
  void testGetMovieById() {
    ApiQuery apiQuery = new ApiQuery();
    Movie movie = apiQuery.getMovie(76759);
    Assertions.assertEquals("Star Wars: Episode IV - A New Hope", movie.getMovieName());
    Assertions.assertEquals("George Lucas", movie.getMovieDirector());
  }

  @Test
  void testGetMovieByIdEightDigit() {
    ApiQuery apiQuery = new ApiQuery();
    Movie movie = apiQuery.getMovie(10001870);
    Assertions.assertEquals("Disrupted Land", movie.getMovieName());
  }
}