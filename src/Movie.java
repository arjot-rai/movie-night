import java.util.ArrayList;

public class Movie {
  private String movieName;

  private int movieID;

  private String movieDirector;

  private String movieReleaseDate;

  private ArrayList<String> movieActor;

  private ArrayList<String> movieStreamingSite;

  private String movieRating;

  private String movieGenre;

  public Movie(String movieName, int id, String director,
      String release, String rating, String genre){
      this.movieName = movieName;
      this.movieID = id;
      this.movieDirector = director;
      this.movieReleaseDate = release;
      this.movieRating = rating;
      this.movieGenre = genre;
      this.movieStreamingSite = new ArrayList<>();
      this.movieActor = new ArrayList<>();
  }

  public String getMovieName() {
    return movieName;
  }

  public void setMovieName(String movieName) {
    this.movieName = movieName;
  }

  public int getMovieID() {
    return movieID;
  }

  public void setMovieID(int movieID) {
    this.movieID = movieID;
  }

  public String getMovieDirector() {
    return movieDirector;
  }

  public void setMovieDirector(String movieDirector) {
    this.movieDirector = movieDirector;
  }

  public String getMovieReleaseDate() {
    return movieReleaseDate;
  }

  public void setMovieReleaseDate(String movieReleaseDate) {
    this.movieReleaseDate = movieReleaseDate;
  }

  public ArrayList<String> getMovieActor() {
    return movieActor;
  }

  public void addMovieActor(String actor) {
    this.movieActor.add(actor);
  }

  public ArrayList<String> getMovieStreamingSite() {
    return movieStreamingSite;
  }

  public void addMovieStreamingSite(String streamingSite) {
    this.movieStreamingSite.add(streamingSite);
  }

  public String getMovieRating() {
    return movieRating;
  }

  public void setMovieRating(String movieRating) {
    this.movieRating = movieRating;
  }

  public String getMovieGenre() {
    return movieGenre;
  }

  public void setMovieGenre(String movieGenre) {
    this.movieGenre = movieGenre;
  }
}
