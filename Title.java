import java.util.ArrayList;

public class Title { // abstract class because you cannot create a direct instance of title
    // declaring instance variables
    private int id = 0; // unique show id attribute declared
    private String type = ""; // type of title corresponding to Movie or TV show
    private String title = "";
    private ArrayList<String> directors;
    private ArrayList<String> countries;
    private int releaseYear = 0;
    private String rating = "";
    private ArrayList<String> genres;


    public Title(int id, String type, String name, ArrayList<String> directors, ArrayList<String> countries, int releaseYear, String rating, ArrayList<String> genres) {
        // initializing instance variables based on passed arguments
        this.id = id;
        this.type = type;
        this.title = name;
        this.directors = directors;
        this.countries = countries;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.genres = genres;
    }

    // standard getters and setters for all instance variables

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getDirectors() {
        return directors;
    }

    public void setDirectors(ArrayList<String> directors) {
        this.directors = directors;
    }

    public ArrayList<String> getCountries() {
        return countries;
    }

    public void setCountries(ArrayList<String> countries) {
        this.countries = countries;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }



    // toString method to provide string representation of a Title
    public String toString() {
        return String.format("ID: %d\nType: %s\nName: %s\nDirectors: %s\nCountries: %s\nRelease Year: %d\nRating: %s\nGenres: %s",
                id, type, title, directors.toString(), countries.toString(), releaseYear, rating, genres.toString());
    }


}