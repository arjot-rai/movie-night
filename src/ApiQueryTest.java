import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ApiQueryTest {

  @Test
  void getMovie() throws IOException, ParseException {
    ApiQuery apiQuery = new ApiQuery();
    Movie movie = apiQuery.getMovie("star wars");
    Assertions.assertEquals(movie.getMovieName(), "Star Wars");
  }
}