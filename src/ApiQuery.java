import java.util.ArrayList;
import java.util.Arrays;
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
  private String error = "";

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
   */
  public ArrayList<Object> searchMovies(String titleSearch, int pages) {
    titleSearch = formatStringForURL(titleSearch);

    ArrayList<Object> results = new ArrayList<>();

    for (int i = 1; i <= pages; i++) {
      try{
        URL url = new URL(omdbUrl + omdbApiKey + "&s=" + titleSearch + "&type=movie&page=" + i);
        // check if the URL works
        if (isResponseCode200(url)) {
          JSONObject json = convertStringToJSON(readTextFromURL(url));

          // search returns an array, add all of them
          if (json != null){
            results.addAll((JSONArray) json.get("Search"));
          }
          else{
            error = "Error: could not read JSON";
            return null;
          }

        } else {
          error = "Error: bad response from URL";
        }
      }
      catch(IOException e){
        error = "Error: malformed URL";
        return null;
      }

    }
    return results;
  }

  /**
   * Retrieve information on a specific movie
   *
   * @param titleInput String: the title of the movie to retrieve
   * @return a Movie object containing all the relevant information about the movie
   */
  public Movie getMovie(String titleInput) {
    titleInput = formatStringForURL(titleInput);

    try {
      // &t for title, &type=movie to only return movies
      URL url = new URL(omdbUrl + omdbApiKey + "&t=" + titleInput + "&type=movie&plot=full");

      // response code 200 means the URL is valid
      if (isResponseCode200(url)) {
        JSONObject json = convertStringToJSON(readTextFromURL(url));
        if(json != null){
          return formatMovie(json);
        }
        else{
          error = "Error: could not read JSON";
          return null;
        }
      } else return null;
    } catch (IOException e) {
      error = "Error: malformed URL";
      return null;
    }
  }

  /**
   * Retrieve information on a specific movie
   *
   * @param imdbID int: the IMDB ID of the movie to retrieve
   * @return a Movie object containing all the relevant information about the movie
   */
  public Movie getMovie(int imdbID) {
    String id;
    // some 8 digit IDs exist, but all other IDs need to be padded to 7 digits
    if (imdbID < 10000000) {
      id = String.format("%07d", imdbID);
    } else if(imdbID > 99999999){
      error = "Error: invalid IMDB ID";
      return null;
    } else {
      id = ((Integer) imdbID).toString();
    }

    // &i for ID, &type=movie to only return movies, &plot=full to get full description
    try {
      URL url = new URL(omdbUrl + omdbApiKey + "&i=tt" + id + "&type=movie&plot=full");

      // response code 200 means the URL is valid
      if (isResponseCode200(url)) {
        JSONObject json = convertStringToJSON(readTextFromURL(url));
        if(json != null){
          return formatMovie(json);
        }
        else {
          error = "Error: could not read JSON";
          return null;
        }

      } else return null;
    } catch (IOException e) {
      error = "Error: malformed URL";
      return null;
    }
  }

  /**
   * Get the last error thrown by the ApiRequest
   * @return error - the string of the error message
   */
  public String getError(){
    return error;
  }

  private Movie formatMovie(JSONObject json) {
    if(json.get("Title") == null){
      error = error = "Error: could not read JSON";
      return null;
    }
    String title = (String) json.get("Title");
    int id = Integer.parseInt(((String) json.get("imdbID")).replaceAll("t", ""));
    String director = (String) json.get("Director");
    String releaseDate = (String) json.get("Released");
    String rating = (String) json.get("Rated");
    String plot = (String) json.get("Plot");
    String posterUrl = (String) json.get("Poster");
    String runtimeString = (String) json.get("Runtime");
    int runtime = Integer.parseInt(runtimeString.substring(0, runtimeString.length() - 4));
    String[] genres = ((String) json.get("Genre")).split(",");
    ArrayList<String> genreList = new ArrayList<String>();

    for (String genre : genres) {
      genre = genre.trim();
      genreList.add(genre);
    }

    Movie movie = new Movie(title, id, director, releaseDate, rating, genreList, plot, runtime,
        posterUrl);

    String[] actors = ((String) json.get("Actors")).split(",");
    for (String actor : actors) {
      actor = actor.trim();
      movie.addMovieActor(actor);
    }
    return movie;
  }

  private boolean isResponseCode200(URL url) {
    try {
      HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
      urlConnection.setRequestMethod("GET");
      int responseCode = urlConnection.getResponseCode();
      return (responseCode == 200);
    } catch (IOException e) {
      error = "Error: Could not open connection to URL";
      return false;
    }
  }

  private String readTextFromURL(URL url) {
    String inline = "";
    try {
      Scanner scanner = new Scanner(url.openStream());
      while (scanner.hasNext()) {
        inline += scanner.nextLine();
      }
      scanner.close();

      return inline;
    } catch (IOException e) {
      error = "Error: Could not open connection to URL";
      return null;
    }
  }

  private JSONObject convertStringToJSON(String text) {
    JSONParser parser = new JSONParser();
    try {
      return (JSONObject) parser.parse(text);
    } catch (ParseException e) {
      error = "Error: Could not convert string to JSON";
      return null;
    }
  }

  private String formatStringForURL(String input) {
    input = input.strip();
    return input.replaceAll("\\s", "+");
  }

  public static void main(String[] args) {}
}
