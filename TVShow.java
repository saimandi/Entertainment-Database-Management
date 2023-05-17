import java.util.ArrayList;

public class TVShow extends Title { // child class of Title
    // declaring two additional instance variables, remainder are inherited
    private int numberOfSeasons;

    public TVShow(int id, String type, String title, ArrayList<String> director, ArrayList<String> countries, int releaseYear, String rating, int numberOfSeasons, ArrayList<String> genres) {
        // initializing instance variables, even those inherited from Title
        super(id, type, title, director, countries, releaseYear, rating, genres); // using parent constructor
        this.numberOfSeasons = numberOfSeasons;
    }

    // standard getters and setters for instance variable unique to TVShow

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }


    // using toString from Title, as well as string representation of additional attributes
    public String toString() {
        return String.format("%s\nNumber of Seasons: %d", super.toString(), numberOfSeasons);
    }
}


