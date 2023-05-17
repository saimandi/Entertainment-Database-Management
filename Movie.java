import java.util.ArrayList;

public class Movie extends Title { // child class of Title
    private int duration; // attribute to keep track of duration in minutes
    public Movie(int id, String type, String name, ArrayList<String> directors, ArrayList<String> countries, int releaseYear, String rating, int duration, ArrayList<String> genres) {
        super(id, type, name, directors, countries, releaseYear, rating, genres); // use constructor of parent class
        this.duration = duration; // initialize duration
    }

    // standard getters and setters below only for additional instance variable duration
    // remaining getters and setters inherited from Title
    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }


    // toString method created using toString of Title with additional attribute duration
    public String toString() {
        return String.format("%s\nDuration: %d", super.toString(), duration);
    }

}
