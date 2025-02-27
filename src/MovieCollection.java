import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;


public class MovieCollection {


    private ArrayList<Movie> movieList;
    private Scanner scan;


    public MovieCollection() {
        movieList = new ArrayList<>();
        scan = new Scanner(System.in);

        readData();  // read data in from the file


        System.out.println("Welcome to the movie collection!");
        String menuOption = "";

        while (!menuOption.equals("q")) {
            System.out.println();
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (c)ast");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scan.nextLine();

            if (menuOption.equals("t")) {
                searchTitles();
            } else if (menuOption.equals("c")) {
                searchCast();
            } else if (menuOption.equals("q")) {
                System.out.println("Goodbye!");
            } else {
                System.out.println("Invalid choice!");
            }
        }
    }

    private void readData() {
        try {
            File file = new File("src/movies_data.csv");
            Scanner fileScanner = new Scanner(file);
            fileScanner.nextLine();
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    String title = parts[0].trim();
                    String cast = parts[1].trim();
                    String director = parts[2].trim();
                    String overview = parts[3].trim();
                    int runtime = Integer.parseInt(parts[4].trim());
                    double userRating = Double.parseDouble(parts[5].trim());
                    movieList.add(new Movie(title,cast,director,overview,runtime,userRating));
                }
            }
            fileScanner.close();
        } catch (IOException e) {
            System.out.println("Error reading Movie list.");
        }
    }

    private void searchTitles() {
        ArrayList<Movie> matches = new ArrayList<Movie>();
        System.out.print("Enter a title search term: ");
        String term = scan.nextLine();
        term = term.toLowerCase();

        int counter = 0;
        Movie temp;
        int m = 0;

        for (int i = 0; i < movieList.size(); i++) {
            if (movieList.get(i).getTitle().toLowerCase().contains(term)) {
                counter++;
                matches.add(movieList.get(i));
            }
        }

        if (counter == 0 ) {
            System.out.println("No movie titles match that search term!");
            return;
        }
        counter = 0;

        for (int i = 1; i < matches.size(); i++) {
            m = i;
            while(m != 0 && matches.get(m).getTitle().compareTo(matches.get(m - 1).getTitle()) < 0) {
                temp = matches.get(m);
                matches.set(m , matches.get(m - 1));
                matches.set(m - 1, temp);
                m--;
            }
        }

        for (Movie match : matches) {
            counter++;
            System.out.println(counter + ". " + match.getTitle());
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter Number: ");
        int num = scan.nextInt();
        scan.nextLine();
        System.out.println();
        System.out.println("Title: " + matches.get(num - 1).getTitle());
        System.out.println("Runtime: " + matches.get(num - 1).getRuntime());
        System.out.println("Directed By: " + matches.get(num - 1).getDirector());
        System.out.println("Cast: " + matches.get(num - 1).getCast());
        System.out.println("Overview: " + matches.get(num - 1).getOverview());
        System.out.println("User Rating: " + matches.get(num - 1).getUserRating());
    }

    private void searchCast() {
        ArrayList<String> finalActors = new ArrayList<String>();
        ArrayList<Movie> trackingInfo = new ArrayList<Movie>();
        ArrayList<Movie> finalMovies = new ArrayList<Movie>();
        System.out.print("Enter a person to search for (first or last name): ");
        String term = scan.nextLine();
        term = term.toLowerCase();

        int counter = 0;
        String temp;
        Movie temp2;
        int m = 0;
        int t = 0;
        int q = 0;
        int check = 0;
        String theName = "";

        for (int i = 0; i < movieList.size(); i++) {
            String actors = movieList.get(i).getCast();
            String[] actorList = actors.split("\\|");

            for (int j = 0; j < actorList.length; j++) {
                if (actorList[j].toLowerCase().contains(term)) {
                    for (int k = 0; k < finalActors.size(); k++) {
                        if (finalActors.get(k).equals(actorList[j])) {
                            check = 1;
                        }
                    }
                    if (check == 0) {
                        finalActors.add(actorList[j]);
                        trackingInfo.add(movieList.get(i));
                    }
                    check = 0;
                }
            }
        }
        if (finalActors.isEmpty()) {
            System.out.println("No results match your search");
            return;
        }
        for (int x = 1; x < finalActors.size(); x++) {
            m = x;
            t = x;
            while(m != 0 && finalActors.get(m).compareTo(finalActors.get(m - 1)) < 0) {
                temp = finalActors.get(m);
                finalActors.set(m , finalActors.get(m - 1));
                finalActors.set(m - 1, temp);
                m--;
                temp2 = trackingInfo.get(t);
                trackingInfo.set(t , trackingInfo.get(t - 1));
                trackingInfo.set(t - 1, temp2);
                t--;
            }
        }
        for (String actor : finalActors) {
            counter++;
            System.out.println(counter + ". " + actor);
        }
        counter = 0;

        System.out.println("Which would you like to see all movies for?");
        System.out.print("Enter Number: ");
        int num = scan.nextInt();
        scan.nextLine();
        for (String name : finalActors) {
            counter++;
            if (counter == num) {
                theName = name;
                break;
            }
        }
        counter = 0;
        for (int g = 0; g < movieList.size(); g++) {
            if (movieList.get(g).getCast().contains(theName)) {
                finalMovies.add(movieList.get(g));
            }
        }
        for (int i = 1; i < finalMovies.size(); i++) {
            q = i;
            while(q != 0 && finalMovies.get(q).getTitle().compareTo(finalMovies.get(q - 1).getTitle()) < 0) {
                Movie temper = finalMovies.get(q);
                finalMovies.set(q , finalMovies.get(q - 1));
                finalMovies.set(q - 1, temper);
                q--;
            }
        }
        for (Movie theMovie : finalMovies) {
            counter++;
            System.out.println(counter + ". " + theMovie.getTitle());
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter Number: ");
        int number = scan.nextInt();
        scan.nextLine();
        System.out.println();
        System.out.println("Title: " + finalMovies.get(number - 1).getTitle());
        System.out.println("Runtime: " + finalMovies.get(number - 1).getRuntime());
        System.out.println("Directed By: " + finalMovies.get(number - 1).getDirector());
        System.out.println("Cast: " + finalMovies.get(number - 1).getCast());
        System.out.println("Overview: " + finalMovies.get(number - 1).getOverview());
        System.out.println("User Rating: " + finalMovies.get(number - 1).getUserRating());
    }

}

