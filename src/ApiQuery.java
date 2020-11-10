import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection;
import java.util.Scanner;
import org.json.simple.parser.ParseException;

public class ApiQuery {

  private String omdbUrl = "http://www.omdbapi.com/?apikey="; // need '&' after API key
  private String omdbApiKey = "1eafea25";

  /*
  JSON format:
  By Movie:
  {Title, Year, Rated, Released, Runtime, Genre, Director, Writer, Actors, Plot, Language, Country,
      Awards, Poster, Ratings[3], Metascore, imdbRating, imdbVotes, imdbID, Type, DVD, Production,
      Website, Response}

  By Search:
  {Search:[{Title, Year, imdbID, Type, Poster}, {Title...       ...}]
   */

  /**
   * * Search the Open Movie Database API for movies that match a given search term
   *
   * @param titleSearch the user-inputted string to search for matches
   * @throws IOException if fails to open connection or read filestream
   * @throws ParseException if encounters malformed JSON
   */
  public void searchMovie(String titleSearch) throws IOException, ParseException {
    URL url = new URL(omdbUrl + omdbApiKey + "&s=" + titleSearch + "&page=1");
    if (isResponseCode200(url)) {
      JSONObject json = convertStringToJSON(readTextFromURL(url));

      for (int i = 0; i < json.size(); i++) {
        System.out.println(((JSONObject) ((JSONArray) json.get("Search")).get(i)).get("Title"));
      }
    } else {
      System.out.println("error");
    }
  }

  private boolean isResponseCode200(URL url) throws IOException {
    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
    urlConnection.setRequestMethod("GET");
    int responseCode = urlConnection.getResponseCode();
    return (responseCode == 200);
  }

  private String readTextFromURL(URL url) throws IOException {
    String inline = "";
    Scanner scanner = new Scanner(url.openStream());
    while (scanner.hasNext()) {
      inline += scanner.nextLine();
    }
    scanner.close();

    return inline;
  }

  private JSONObject convertStringToJSON(String text) throws ParseException {
    JSONParser parser = new JSONParser();
    return (JSONObject) parser.parse(text);
  }

  public static void main(String[] args) throws IOException, ParseException {
    ApiQuery query = new ApiQuery();
    query.searchMovie("lord+of+the+rings");
  }
}
