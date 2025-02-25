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
    }


    public void start() {


        readData();  // read data in from the file


        System.out.println("Welcome to the movie collection!");
        String menuOption = "";

        while (!menuOption.equals("q")) {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (c)ast");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scan.nextLine();

            if (menuOption.equals("t")) {
                searchTitles();
            } else if (menuOption.equals("c")) {

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
        System.out.println("Enter a title search term: ");
        String term = scan.nextLine();

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
        System.out.println("Enter Number: ");
        int num = scan.nextInt();
        System.out.println("Title: " + matches.get(num - 1).getTitle());
        System.out.println("Runtime: " + matches.get(num - 1).getRuntime());
        System.out.println("Directed By: " + matches.get(num - 1).getDirector());
        System.out.println("Cast: " + matches.get(num - 1).getCast());
        System.out.println("Overview: " + matches.get(num - 1).getOverview());
        System.out.println("User Rating: " + matches.get(num - 1).getUserRating());
    }

}

