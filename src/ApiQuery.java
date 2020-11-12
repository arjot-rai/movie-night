import java.util.ArrayList;
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
  {Search:[{Title, Year, imdbID, Type, Poster}, {Title...       ...}, {Title...       ...}]
      each page contains 3 entries
   */

  /**
   * * Search the Open Movie Database API for movies that match a given search term
   *
   * @param titleSearch String: the user-inputted string to search for matches
   * @param pages int: the number of pages of results to return - each page contains 3 results
   * @throws IOException if fails to open connection or read filestream
   * @throws ParseException if encounters malformed JSON
   */
  public ArrayList<Object> searchMovies(String titleSearch, int pages)
      throws IOException, ParseException {
    titleSearch = formatStringForURL(titleSearch);

    ArrayList<Object> results = new ArrayList<>();

    for (int i = 1; i <= pages; i++) {
      URL url = new URL(omdbUrl + omdbApiKey + "&s=" + titleSearch + "&type=movie&page=" + i);
      // check if the URL works
      if (isResponseCode200(url)) {
        JSONObject json = convertStringToJSON(readTextFromURL(url));

        // search returns an array, add all of them
        results.addAll((JSONArray) json.get("Search"));

      } else {
        System.out.println("error");
      }
    }
    return results;
    }

  /**
   * retrieve information on a specific movie
   * @param titleInput String: the title of the movie to retrieve
   * @return a Movie object containing all the relevant information about the movie
   * @throws IOException if fails to open connection or read filestream
   * @throws ParseException if encounters malformed JSON
   */
  public Movie getMovie(String titleInput) throws IOException, ParseException {
    titleInput = formatStringForURL(titleInput);
    //&t for title, &type=movie to only return movies
    URL url = new URL(omdbUrl + omdbApiKey + "&t=" + titleInput + "&type=movie");

    //response code 200 means the URL is valid
    if (isResponseCode200(url)) {
      JSONObject json = convertStringToJSON(readTextFromURL(url));
      return formatMovie(json);
    }
    else return null;
  }

  /**
   * retrieve information on a specific movie
   * @param imdbID int: the IMDB ID of the movie to retrieve
   * @return a Movie object containing all the relevant information about the movie
   * @throws IOException if fails to open connection or read filestream
   * @throws ParseException if encounters malformed JSON
   */
  public Movie getMovie(int imdbID) throws IOException, ParseException {
    String id;
    //some 8 digit IDs exist, but all other IDs need to be padded to 7 digits
    if(imdbID < 10000000){
      id = String.format("%07d", imdbID);
    }
    else{
      id = ((Integer) imdbID).toString();
    }

    //&i for ID, &type=movie to only return movies
    URL url = new URL(omdbUrl + omdbApiKey + "&i=tt" + id + "&type=movie");

    //response code 200 means the URL is valid
    if (isResponseCode200(url)) {
      JSONObject json = convertStringToJSON(readTextFromURL(url));
      return formatMovie(json);
    }
    else return null;
  }

  private Movie formatMovie(JSONObject json){
    String title = (String) json.get("Title");
    int id = Integer.parseInt(((String) json.get("imdbID")).replaceAll("t", ""));
    String director = (String) json.get("Director");
    String releaseDate = (String) json.get("Released");
    String rating = (String) json.get("Rated");
    String genre = (String) json.get("Genre");

    Movie movie = new Movie(title, id, director, releaseDate, rating, genre);

    String[] actors = ((String) json.get("Actors")).split(",");
    for (String actor : actors) {
      movie.addMovieActor(actor);
    }
    return movie;
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

  private String formatStringForURL(String input){
    input = input.strip();
    return input.replaceAll("\\s", "+");
  }

  public static void main(String[] args) throws IOException, ParseException {
  }


}
