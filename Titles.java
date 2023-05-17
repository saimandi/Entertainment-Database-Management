import java.sql.Array;
import java.util.*;

public class Titles { // data container class to hold instances of Titles
    private HashMap<Integer, Title> titles; // each Title stored in HashMap with key as the id and value as the Title object
    private ArrayList<String> ratings;
    private ArrayList<String> directors;
    private ArrayList<String> genres;
    private ArrayList<Integer> numSeasons; // only used for TV Show
    private ArrayList<String> countries;
    private ArrayList<Integer> years;

    public Titles() {
        this.titles = new HashMap<Integer, Title>(); // initialize titles attribute to be a HashMap
        this.ratings = new ArrayList<>();
        this.directors = new ArrayList<>();
        this.genres = new ArrayList<>();
        this.numSeasons = new ArrayList<>();
        this.countries = new ArrayList<>();
        this.years = new ArrayList<>();
    }

    public HashMap<Integer, Title> getTitles(){
        return titles;
    }

    public int getCount() {
        return titles.size();
    } // return size of map

    public void addTitle(Title title) { // add title if title with existing show id does not exist in map
        // modified this method to allow for unique arraylists for each attribute to be kept
        titles.put(title.getId(), title); // first add Title to HashMap
        String rating = title.getRating();
        if (ratings.isEmpty() || !(ratings.contains(rating))) { // add to rating if it does not exist
            ratings.add(rating);
        }
        for (String director: title.getDirectors()){ // add to director if it does not exist
            if (directors.isEmpty() || !(directors.contains(director))) {
                directors.add(director);
            }
        }
        for (String genre: title.getGenres()){ // add to genre if it does not exist
            if (!(genres.contains(genre))) {
                genres.add(genre);
            }
        }
        if (title instanceof TVShow){
            if(numSeasons.isEmpty() || !(numSeasons.contains(((TVShow) title).getNumberOfSeasons()))) { // add to numSeasons list if it is unique
                numSeasons.add(((TVShow) title).getNumberOfSeasons());
            }
        }

        for (String country: title.getCountries()){
            if (!(countries.contains(country))){ // add to countries arraylist if it is unique
                countries.add(country);
            }
        }
        int releaseYear = title.getReleaseYear();
        if (!(years.contains(releaseYear))) { // add to years arraylist if it is unique
            years.add(releaseYear);
        }
    }

    public void updateTitle(Title title) { // if title with show id exists then update value from current Title object to new Title object
        if (titles.containsKey(title.getId())) {
            titles.replace(title.getId(), title);
        }
    }


    public void removeTitle(Title title) { // delete title from map based on id
        titles.remove(title.getId());
    }

    public void removeAllTitles() {
        titles.clear();
    } // clear inventory

    // below get unique methods return get unique if it exist
    public ArrayList<String> getUniqueRatings(){
        return ratings;
    }

    public ArrayList<String> getUniqueDirectors(){
        return directors;
    }

    public ArrayList<String> getUniqueGenres(){
        return genres;
    }

    public ArrayList<Integer> getUniqueNumSeasons(){
        return numSeasons;
    }

    public ArrayList<String> getUniqueCountries(){
        return countries;
    }

    public ArrayList<Integer> getUniqueYears(){
        return years;
    }

    public Set<Object> getUniqueAttributes(String attributeName){ // method that combines all attributes
        Set<Object> set = new HashSet<>();
        if (Objects.equals(attributeName, "Rating")){
            for (Map.Entry<Integer, Title> entry : titles.entrySet()){
                Title title = entry.getValue();
                set.add(title.getRating());
            }
        } else if (Objects.equals(attributeName, "Director")){
            for (Map.Entry<Integer, Title> entry : titles.entrySet()){
                Title title = entry.getValue();
                for (String director : title.getDirectors()) {
                    set.add(director);
                }
            }

        } else if (Objects.equals(attributeName, "Genre")) {
            for (Map.Entry<Integer, Title> entry : titles.entrySet()) {
                Title title = entry.getValue();
                for (String genre : title.getGenres()) {
                    set.add(genre);
                }
            }
        } else if (Objects.equals(attributeName, "Number of Seasons")) {


        } else if (Objects.equals(attributeName, "Country")){
            for (Map.Entry<Integer, Title> entry : titles.entrySet()){
                Title title = entry.getValue();
                for (String country : title.getCountries()) {
                    set.add(country);
                }
            }
        } else if (Objects.equals(attributeName, "Year")){
            for (Map.Entry<Integer, Title> entry : titles.entrySet()){
                Title title = entry.getValue();
                set.add(title.getReleaseYear());
            }
        }
        return set;

    }
    public ArrayList<Title> findTitleByRating(String rating){
        ArrayList<Title> matchingTitles = new ArrayList<>();
        for (Map.Entry<Integer, Title> entry : titles.entrySet()) {
            Title title = entry.getValue(); // get each title
            if (Objects.equals(title.getRating(), rating)){ // check if rating equals passed inr ating
                matchingTitles.add(title); // add to matching titles list
            }
        }
        return matchingTitles;
    }

    public ArrayList<Title> findTitleByDirector(String director){
        ArrayList<Title> matchingTitles = new ArrayList<>();
        for (Map.Entry<Integer, Title> entry : titles.entrySet()) {
            Title title = entry.getValue(); // get each title
            for (String d : title.getDirectors()) { // for each element in directors array check if it is matching
                if (Objects.equals(d, director)){
                    matchingTitles.add(title); // add to matching titles list
                }
            }
        }
        return matchingTitles;
    }

    public ArrayList<Title> findTitleByGenre(String genre){
        ArrayList<Title> matchingTitles = new ArrayList<>();
        for (Map.Entry<Integer, Title> entry : titles.entrySet()) {
            Title title = entry.getValue(); // get each title
            for (String g : title.getGenres()) { // for each element in genres check if it matches passed in genre
                if (Objects.equals(g, genre)){
                    matchingTitles.add(title);
                }
            }
        }
        return matchingTitles; // return matching titles
    }

    public ArrayList<Title> findTitleBetweenDuration(int lower, int upper){ // take in lower and upper limit duration
        ArrayList<Title> matchingTitles = new ArrayList<>();
        for (Map.Entry<Integer, Title> entry : titles.entrySet()) {
            Title title = entry.getValue();
            if (Objects.equals(title.getType(), "Movie")) { // if it is a movie
                int duration = ((Movie) title).getDuration(); // typecast Title to Movie
                if (lower <= duration && duration <= upper) { // check if duration is between two values
                    matchingTitles.add(title);
                }
            }
        }
        return matchingTitles;
    }

    public ArrayList<Title> findTitleBySeasons(int numSeasons) {
        ArrayList<Title> matchingTitles = new ArrayList<>();
        for (Map.Entry<Integer, Title> entry : titles.entrySet()) {
            Title title = entry.getValue();
            if (Objects.equals(title.getType(), "TV Show")) { // if it is a TV Show
                if (numSeasons == ((TVShow) title).getNumberOfSeasons()) { // typecast Title to TVShow
                    matchingTitles.add(title); // add if matching
                }
            }
        }
        return matchingTitles;
    }

    public ArrayList<Title> findTitleByCountry(String country){ // get country
        ArrayList<Title> matchingTitles = new ArrayList<>();
        for (Map.Entry<Integer, Title> entry : titles.entrySet()) {
            Title title = entry.getValue();
            for (String c : title.getCountries()) { // for each country in country array
                if (Objects.equals(c, country)){
                    matchingTitles.add(title); // check if it is matching
                }
            }
        }
        return matchingTitles;
    }

    public ArrayList<Title> findTitleByReleaseYear(int year) { // get release year
        ArrayList<Title> matchingTitles = new ArrayList<>();
        for (Map.Entry<Integer, Title> entry : titles.entrySet()) {
            Title title = entry.getValue();
            if (title.getReleaseYear() == year) { // check if values match
                matchingTitles.add(title);
            }
        }
        return matchingTitles;
    }





}
















