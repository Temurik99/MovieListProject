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
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 5) {
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
        ArrayList<String> matches = new ArrayList<String>();
        System.out.println("Enter a title search term: ");
        String term = scan.nextLine();

        int counter = 0;
        int leftIdx = 0;
        int rightIdx = movieList.size() - 1;
        String temp = "";
        int m = 0;

        for (int i = 0; i < movieList.size() - 1; i++) {
            if (movieList.get(i).getTitle().contains(term)) {
                counter++;
                matches.add(movieList.get(i).getTitle());
            }
        }

        if (counter == 0 ) {
            System.out.println("No movie titles match that search term!");
            return;
        }
        counter = 0;

        for (int i = 1; i < matches.size(); i++) {
            m = i;
            while(m != 0 && matches.get(m).compareTo(matches.get(m - 1)) < 0) {
                temp = matches.get(m);
                matches.set(m , matches.get(m - 1));
                matches.set(m - 1, temp);
                m--;
            }
        }

        for (String match : matches) {
            counter++;
            System.out.println(counter + ". " + match);
        }

    }


    private void saveData() {
        try (PrintWriter writer = new PrintWriter("src/movies_data.csv")) {
            for (Movie movie : movieList) {
                writer.println(item.getName() + "," + item.getPrice());
            }
        } catch (IOException e) {
            System.out.println("Error saving shopping list.");
        }
    }


    private void viewShoppingList() {
        if (shoppingList.isEmpty()) {
            System.out.println("shopping list empty.");
            return;
        }
        for (Item item : shoppingList) {
            System.out.println(item.getName() + " " + item.getPrice());
        }
    }


    private void addItemToList() {
        System.out.print("Enter item name: ");
        String name = scan.nextLine().trim();
        System.out.print("Enter item price: ");
        double price;
        try {
            price = Double.parseDouble(scan.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid price. Item not added.");
            return;
        }
        shoppingList.add(new Item(name, price));
        System.out.println(name + " added to the shopping list.");
    }


    private void editItemPrice() {
        System.out.print("Enter item name to edit: ");
        String name = scan.nextLine().trim();


        for (Item item : shoppingList) {
            if (item.getName().equalsIgnoreCase(name)) {
                System.out.print("Enter new price: ");
                try {
                    double newPrice = Double.parseDouble(scan.nextLine().trim());
                    item.setPrice(newPrice);
                    System.out.println("Price updated for " + name);
                    return;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid price. No changes made.");
                    return;
                }
            }
        }
        System.out.println("Item not found.");
    }


    private void removeItem() {
        System.out.print("Enter item name to remove: ");
        String name = scan.nextLine().trim();


        for (int i = 0; i < shoppingList.size(); i++) {
            if (shoppingList.get(i).getName().equalsIgnoreCase(name)) {
                System.out.println(name + " removed.");
                shoppingList.remove(i);
                return;
            }
        }
        System.out.println("Item not found.");
    }
}

