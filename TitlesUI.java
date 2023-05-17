import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.*;
import java.util.*;


public class TitlesUI {
    private static final Scanner stringScanner = new Scanner(System.in).useDelimiter("\n");
    //private static final Titles titles = new Titles();

    private static final Titles movies = new Titles();

    private static final Titles tvShows = new Titles();

    private static String delimiter = null;


    private static ArrayList<String> stringToArrayList(String s) { // takes a String and converts to ArrayList used for all comma-separated attributes
        s = s.replaceAll("\"", ""); // replacing quotes
        String[] arr = s.split(", "); // splitting by commas
        return new ArrayList<>(Arrays.asList(arr));
    }

    public static void read(String csvFile) {
        try {
            File file = new File(csvFile);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            String[] tempArr;
            line = br.readLine(); // discard header
            if (line.contains("\t")){
                delimiter = "\t";
            } else if (line.contains(", ")){
                delimiter = ", ";
            }

            while ((line = br.readLine()) != null) {
                Title newTitle;
                tempArr = line.split("[\t,](?=([^\"]*\"[^\"]*\")*[^\"]*$)"); // complex regex to split by comma but outside double-quoted items
                int show_id = Integer.parseInt(tempArr[0].substring(1)); // get integer show_id by dropping the s
                String type = tempArr[1]; // Movie or TVShow
                String title = tempArr[2];
                ArrayList<String> directors = new ArrayList<>();
                if (!tempArr[3].isEmpty()) { // add to arraylist directors splitting by comma
                    directors = stringToArrayList(tempArr[3]);
                }
                ArrayList<String> countries = new ArrayList<>();
                if (!tempArr[4].isEmpty()) { // add to arraylist countries splitting by comma
                    countries = stringToArrayList(tempArr[4]);
                }
                int release_year = Integer.parseInt(tempArr[5]);
                if (!tempArr[5].isEmpty()) { // convert to Integer if it exists
                    release_year = Integer.parseInt(tempArr[5]);
                }
                String rating = tempArr[6]; // get rating
                int duration = Integer.parseInt(tempArr[7].split(" ")[0]); // get int from duration by splitting by space
                ArrayList<String> genres = stringToArrayList(tempArr[8]);
                if ("Movie".equals(type)) {
                    newTitle = new Movie(show_id, type, title, directors, countries, release_year, rating, duration, genres); // create new Movie
                    movies.addTitle(newTitle);
                } else {
                    newTitle = new TVShow(show_id, type, title, directors, countries, release_year, rating, duration, genres); // create new TVShow
                    tvShows.addTitle(newTitle);
                }
                //titles.addTitle(newTitle); // add to DataContainer
            }
            br.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

    public static int movieAttributeMenu() {
        System.out.print("Which attribute are you searching based on?\n");
        System.out.print("1. Rating\n");
        System.out.print("2. Director\n");
        System.out.print("3. Genre\n");
        System.out.print("4. Duration\n");
        System.out.print("5. Country\n");
        System.out.print("6. Year\n");
        System.out.print("Please enter option: ");
        int searchAttribute = 0;
        try {
            searchAttribute = Integer.parseInt(stringScanner.next());
            if (searchAttribute <= 0 || searchAttribute >= 7) {
                throw new NumberFormatException("Invalid input.");
            }
        } catch (NumberFormatException nfe) {
            System.out.println("Wrong input. Integer between 1 - 6 expected.");
        }
        return searchAttribute;
    }

    public static ArrayList<Title> movieSearch() throws Exception { // performing a movie search
        // printing menu for user
        int searchAttribute = movieAttributeMenu();
        int intSelection;
        String strSelection;
        int counter;
        switch (searchAttribute) {
            case 1 -> {
                ArrayList<String> ratings = movies.getUniqueRatings(); // getting all unique ratings in array
                counter = 1;
                System.out.println("RATING OPTIONS"); // display rating options
                for (String rating : ratings) {
                    System.out.printf("%d. %s\n", counter++, rating);
                }
                System.out.print("Please enter option: ");
                intSelection = stringScanner.nextInt(); // get rating choice
                return movies.findTitleByRating(ratings.get(intSelection - 1)); // use find title by method to get titles with that rating
            }
            case 2 -> {
                ArrayList<String> directors = movies.getUniqueDirectors(); // getting all unique directors
                counter = 1;
                System.out.println("DIRECTOR OPTIONS"); // display directors
                for (String director : directors) {
                    System.out.printf("%d. %s\n", counter++, director);
                }
                System.out.print("Please enter option: "); // get director choice
                intSelection = stringScanner.nextInt();
                return movies.findTitleByDirector(directors.get(intSelection - 1));
            }
            case 3 -> {
                ArrayList<String> genres = movies.getUniqueGenres(); // get all unique genres
                counter = 1;
                System.out.println("GENRE OPTIONS"); // display genres
                for (String genre : genres) {
                    System.out.printf("%d. %s\n", counter++, genre);
                }
                System.out.print("Please enter option: ");
                intSelection = stringScanner.nextInt();
                return movies.findTitleByGenre(genres.get(intSelection - 1)); // ge
            }
            case 4 -> {
                System.out.print("DURATION OPTIONS\n");
                System.out.print("1. 0 - 30 minutes\n");
                System.out.print("2. 31 - 60 minutes\n");
                System.out.print("3. 61 - 90 minutes\n");
                System.out.print("4. 91 - 120 minutes\n");
                System.out.print("5. 121 - 150 minutes\n");
                System.out.print("6. 151 - 180 minutes\n");
                System.out.print("7. 180+ minutes\n");
                System.out.print("Please enter option: ");
                strSelection = stringScanner.next(); // get duration choice

                // using find title between method to get all titles between certain durations
                if (Objects.equals(strSelection, "1")) {
                    return movies.findTitleBetweenDuration(0, 30);
                } else if (Objects.equals(strSelection, "2")) {
                    return movies.findTitleBetweenDuration(31, 60);
                } else if (Objects.equals(strSelection, "3")) {
                    return movies.findTitleBetweenDuration(61, 90);
                } else if (Objects.equals(strSelection, "4")) {
                    return movies.findTitleBetweenDuration(91, 120);
                } else if (Objects.equals(strSelection, "5")) {
                    return movies.findTitleBetweenDuration(121, 150);
                } else if (Objects.equals(strSelection, "6")) {
                    return movies.findTitleBetweenDuration(151, 180);
                } else if (Objects.equals(strSelection, "7")) {
                    return movies.findTitleBetweenDuration(180, 900000000); // duration is 180+ minutes
                } else {
                    System.out.println("Wrong option.");
                    return null;
                }
            }
            case 5 -> {
                ArrayList<String> countries = movies.getUniqueCountries(); // get unique countries
                counter = 1;
                System.out.println("COUNTRY OPTIONS"); // display all countries
                for (String country : countries) {
                    System.out.printf("%d. %s\n", counter++, country);
                }
                System.out.print("Please enter option: ");
                intSelection = stringScanner.nextInt();
                return movies.findTitleByCountry(countries.get(intSelection - 1)); // get all titles using find title by method
            }
            case 6 -> {
                ArrayList<Integer> years = movies.getUniqueYears(); // unique release years
                counter = 1;
                System.out.println("RELEASE YEAR OPTIONS"); // display unique release years
                for (int year : years) {
                    System.out.printf("%d. %d\n", counter++, year);
                }
                System.out.print("Please enter option: ");
                intSelection = stringScanner.nextInt();
                return movies.findTitleByReleaseYear(years.get(intSelection - 1)); // use find title method to find all release years
            }
            default -> throw new Exception("Not an option.");
        }
    }

    public static int tvShowAttributeMenu() {
        System.out.print("Which attribute are you searching based on?\n");
        System.out.print("1. Rating\n");
        System.out.print("2. Director\n");
        System.out.print("3. Genre\n");
        System.out.print("4. Number of Seasons\n");
        System.out.print("5. Country\n");
        System.out.print("6. Year\n");
        System.out.print("Please enter option: ");
        int searchAttribute = 0;
        try {
            searchAttribute = Integer.parseInt(stringScanner.next());
            if (searchAttribute <= 0 || searchAttribute >= 7) {
                throw new NumberFormatException("Invalid input.");
            }
        } catch (NumberFormatException nfe) {
            System.out.println("Wrong input. Integer between 1 - 6 expected.");
        }
        return searchAttribute;
    }

    public static ArrayList<Title> tvShowSearch() throws Exception { // exact same logic as for movieSearch()
        // creating database of all tvShows by looping through HashMap
        int searchAttribute = tvShowAttributeMenu();
        int intSelection;
        //String strSelection;
        int counter;
        switch (searchAttribute) {
            case 1:
                ArrayList<String> ratings = tvShows.getUniqueRatings();
                counter = 1;
                System.out.println("RATING OPTIONS");
                for (String rating : ratings) {
                    System.out.printf("%d. %s\n", counter++, rating);
                }
                intSelection = stringScanner.nextInt();
                return tvShows.findTitleByRating(ratings.get(intSelection - 1));
            case 2:
                ArrayList<String> directors = tvShows.getUniqueDirectors();
                counter = 1;
                System.out.println("DIRECTOR OPTIONS");
                for (String director : directors) {
                    System.out.printf("%d. %s\n", counter++, director);
                }
                System.out.print("Please enter option: ");
                intSelection = stringScanner.nextInt();
                return tvShows.findTitleByDirector(directors.get(intSelection - 1));
            case 3:
                ArrayList<String> genres = tvShows.getUniqueGenres();
                counter = 1;
                System.out.println("GENRE OPTIONS");
                for (String genre : genres) {
                    System.out.printf("%d. %s\n", counter++, genre);
                }
                System.out.print("Please enter option: ");
                intSelection = stringScanner.nextInt();
                return tvShows.findTitleByGenre(genres.get(intSelection - 1));
            case 4:
                ArrayList<Integer> numSeasons = tvShows.getUniqueNumSeasons(); // displaying only unique seasons
                counter = 1;
                System.out.println("NUMBER OF SEASONS OPTIONS");
                for (int ns : numSeasons) {
                    System.out.printf("%d. %s seasons\n", counter++, ns);
                }
                System.out.print("Please enter option: ");
                intSelection = stringScanner.nextInt();
                return tvShows.findTitleBySeasons(numSeasons.get(intSelection - 1));
            case 5:
                ArrayList<String> countries = tvShows.getUniqueCountries();
                counter = 1;
                System.out.println("COUNTRY OPTIONS");
                for (String country : countries) {
                    System.out.printf("%d. %s\n", counter++, country);
                }
                System.out.print("Please enter option: ");
                intSelection = stringScanner.nextInt();
                return tvShows.findTitleByCountry(countries.get(intSelection - 1));
            case 6:
                ArrayList<Integer> years = tvShows.getUniqueYears();
                System.out.print(years);
                counter = 1;
                System.out.println("RELEASE YEAR OPTIONS");
                for (int year : years) {
                    System.out.printf("%d. %d\n", counter++, year);
                }
                System.out.print("Please enter option: ");
                intSelection = stringScanner.nextInt();
                return tvShows.findTitleByReleaseYear(years.get(intSelection - 1));
            default:
                throw new Exception("Not an option.");

        }
    }

    public static int waitForUser(int minNum, int maxNum) { // helper function for delete
        System.out.println("Type space bar to see more, or type the number of the title you want to remove: ");
        Scanner scanner = new Scanner(System.in);
        while (true) { // perform in loop until valid input
            String input = scanner.nextLine();
            if (input.trim().isEmpty()) { // if line is empty
                return -1;
            } else {
                int value = Integer.parseInt(input); // get index of title user wants to remove
                if (value > maxNum || value < minNum) { // check if in range
                    System.out.println("Input out of range. Try again.");
                } else {
                    return value;
                }
            }
        }

    }

    public static void deleteTitles() {
        int index = 0; // starting index
        int pageStartIndex = 1; // starting index of each page
        ArrayList<Title> allTitles = new ArrayList<>(); // all titles
        allTitles.addAll(movies.getTitles().values()); // adding all movies first
        allTitles.addAll(tvShows.getTitles().values()); // adding all tvShows second
        for (Title title : allTitles) {
            System.out.printf("%d. %s\n", ++index, title.getTitle()); // display title and index
            if (((index % 10 == 0)) || (index >= allTitles.size())) { // if 10 titles have been displayed or on the last page
                int i = waitForUser(pageStartIndex, index); // wait for user to either see next batch of titles or delete a specific title
                if (i != -1) {
                    Title removedTitle = allTitles.get(i - 1); // get title objec to remove
                    System.out.printf("Title: %s will be deleted now\n", removedTitle.getTitle()); // print to user
                    if (removedTitle instanceof Movie) { // remove title from appropriate Titles object
                        movies.removeTitle(removedTitle);
                    } else {
                        tvShows.removeTitle(removedTitle);
                    }
                    return;
                }
                pageStartIndex = index + 1; // update the beginning index
            }
        }

    }

    public static void modifyTitle() {
        int index = 0; // starting index
        int pageStartIndex = 1; // starting index of each page
        ArrayList<Title> allTitles = new ArrayList<>(); // all titles
        allTitles.addAll(movies.getTitles().values()); // adding all movies first
        allTitles.addAll(tvShows.getTitles().values()); // adding all tvShows second
        for (Title title : allTitles) {
            System.out.printf("%d. %s\n", ++index, title.getTitle()); // display title and index
            if (((index % 10 == 0)) || (index >= allTitles.size())) { // if 10 titles have been displayed or on the last page
                int i = waitForUser(pageStartIndex, index); // wait for user to either see next batch of titles or delete a specific title
                if (i != -1) {
                    Title modifiedTitle = allTitles.get(i - 1); // get title object to modify
                    System.out.printf("Enter the new rating for the %s: ", modifiedTitle.getTitle());
                    String newRating = stringScanner.next();
                    modifiedTitle.setRating(newRating);
                    System.out.printf("Rating for %s is now %s\n", modifiedTitle.getTitle(), modifiedTitle.getRating());

                    return;
                }
                pageStartIndex = index + 1; // update the beginning index
            }
        }

    }

    public static void addTitle() {
        // prompting user for appropriate values
        System.out.print("Enter the type of title('Movie' or 'TV Show'): ");
        String type = stringScanner.next();
        System.out.print("Enter the show id: ");
        int showId = stringScanner.nextInt();
        System.out.print("Enter the title: ");
        String title = stringScanner.next();
        System.out.print("Enter the directors(comma-separated): ");
        ArrayList<String> directors = stringToArrayList(stringScanner.next()); // must convert to ArrayList to store in Title object
        System.out.print("Enter the countries(comma-separated): ");
        ArrayList<String> countries = stringToArrayList(stringScanner.next());  // must convert to ArrayList to store in Title object
        System.out.print("Enter the release year: ");
        int releaseYear = stringScanner.nextInt();
        System.out.print("Enter the rating: ");
        String rating = stringScanner.next();
        System.out.print("Enter the duration(in minutes if 'Movie' or number of seasons in 'TV Show': ");
        int duration = stringScanner.nextInt();
        System.out.print("Enter the genres(comma-separated): ");
        ArrayList<String> genres = stringToArrayList(stringScanner.next());  // must convert to ArrayList to store in Title object
        Title newTitle;  // create empty title object
        if (Objects.equals(type, "Movie")) {
            newTitle = new Movie(showId, type, title, directors, countries, releaseYear, rating, duration, genres); // assign to Movie
            movies.addTitle(newTitle); // add to movies Titles object(data container)
        } else if (Objects.equals(type, "TV Show")) {
            newTitle = new TVShow(showId, type, title, directors, countries, releaseYear, rating, duration, genres); // assign to TVShow
            tvShows.addTitle(newTitle); // add to tvShows Titles object(data container)
        }
        System.out.println("Title has been added successfully."); // message to show it has been added correctly
    }

    public static String titleToArrayList(Title title) {
        // collecting every attribute and converting to string
        String duration = null;
        if (title instanceof Movie) {
            duration = String.valueOf(((Movie) title).getDuration()) + " min";
        } else if (title instanceof TVShow) {
            duration = String.valueOf(((TVShow) title).getNumberOfSeasons()) + " Seasons";
        }

        String id = "s" + String.valueOf(title.getId());
        String type = title.getType();
        String name = title.getTitle();
        String directors = arrayListToCSVString(title.getDirectors());
        String countries = arrayListToCSVString(title.getCountries());
        String releaseYear = String.valueOf(title.getReleaseYear());
        String rating = title.getRating();
        String genres = arrayListToCSVString(title.getGenres());

        String[] complete = new String[]{id, type, name, directors, countries, releaseYear, rating, duration, genres}; // creating the new string
        String done = String.join(delimiter, complete); // comma separating it
        return done;
    }

    public static String arrayListToCSVString(ArrayList<String> s) { // helper function to correctly format every arraylist into a proper csv file
        String temp = "";
        if (!s.isEmpty() && s.size() > 1) {
            temp = "\"" + String.join(delimiter, s) + "\""; // use double quote escape character if multivalued
        } else if (!s.isEmpty()) {
            temp = s.get(0); // else use only the singular value, without double quotes
        }
        return temp;
    }

    public static void persistChanges(String csvFile) throws IOException {

        FileWriter outputFile = new FileWriter(csvFile);
        // create a BufferedWriter object to write data to the FileWriter
        BufferedWriter writer = new BufferedWriter(outputFile);

        // write the header row
        writer.write("show_id,type,title,director,country,release_year,rating,duration,genre");
        writer.newLine();

        for (Map.Entry<Integer, Title> entry : movies.getTitles().entrySet()) {
            writer.write(titleToArrayList(entry.getValue()));
            writer.newLine();
        }
        for (Map.Entry<Integer, Title> entry : tvShows.getTitles().entrySet()) {
            writer.write(titleToArrayList(entry.getValue()));
            writer.newLine();
        }

        // close the writer objects
        writer.close();
        outputFile.close();
    }

    public static void main(String[] args) throws IOException {
        // getting input file
        System.out.print("Please enter the name of the input file: ");
        String fileName = stringScanner.next();

        read(fileName); // reading file and processing titles
        String input;
        while (true) { // display in loop
            // display main menu
            System.out.print("MAIN MENU\n");
            System.out.print("1. Add a title\n");
            System.out.print("2. Delete a title\n");
            System.out.print("3. Search for titles\n");
            System.out.print("4. Modify a title\n");
            System.out.print("Type 'Exit' to end program\n");
            System.out.print("Please enter option: ");
            input = stringScanner.next();
            if (Objects.equals(input, "1")) {
                addTitle(); // calling addTitle method

            } else if (Objects.equals(input, "2")) {
                System.out.println("The list of all the movies will show first, followed by the list of all TV shows");
                deleteTitles(); // call method to delete
                System.out.println("Exiting delete menu.");

            } else if (Objects.equals(input, "3")) { // if searching
                System.out.print("Are you looking for a 'Movie' or a 'TVShow'? Please enter option: ");
                String type = stringScanner.next();
                ArrayList<Title> results = null; // gets all Titles matching search criteria
                try {
                    // call appropriate type of search
                    if (Objects.equals(type, "Movie")) {
                        results = movieSearch();
                    } else if (Objects.equals(type, "TVShow")) {
                        results = tvShowSearch();
                    }
                    System.out.println("SEARCH RESULTS"); // display results
                    for (Title title : results) {
                        System.out.println(title.getTitle()); // print only titles of each Title
                    }
                } catch (Exception e) {
                    System.out.print(e);
                }

            } else if (Objects.equals(input, "4")) {
                modifyTitle();
            } else if (Objects.equals(input, "Exit")) { // break loop if Exit is typed
                persistChanges("n3.txt");
                System.out.println("Exiting program.");
                break;
            } else {
                System.out.println("Sorry, not an option.");
            }

        }


    }

}









