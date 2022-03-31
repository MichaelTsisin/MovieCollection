import java.io.*;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MovieCollection
{
    private ArrayList<Movie> movies;
    private Scanner scanner;

    public MovieCollection(String fileName)
    {
        importMovieListJSON(fileName);
        scanner = new Scanner(System.in);
    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t"))
        {
            searchTitles();
        }
        else if (option.equals("c"))
        {
            searchCast();
        }
        else if (option.equals("k"))
        {
            searchKeywords();
        }
        else if (option.equals("g"))
        {
            listGenres();
        }
        else if (option.equals("r"))
        {
            listHighestRated();
        }
        else if (option.equals("h"))
        {
            listHighestRevenue();
        }
        else
        {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void sortResult(ArrayList<String> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            String temp = listToSort.get(j);
            int possibleIndex = j;
            while (possibleIndex > 0 && temp.compareTo(listToSort.get(possibleIndex - 1)) < 0)
            {
                listToSort.set(possibleIndex,listToSort.get(possibleIndex - 1));
                possibleIndex--;

            }
            listToSort.set(possibleIndex,temp);
        }
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Genre: " + movie.getGenres());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast()
    {
        System.out.print("Enter a cast search term: ");
        String searchTerm = scanner.nextLine();
        searchTerm = searchTerm.toLowerCase();
        String cast = "";
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < movies.size(); i++)
        {
            cast = movies.get(i).getCast().toLowerCase();
            String[] castList = cast.split("\\|");
            for (int j = 0; j < castList.length; j++)
            {
                if (list.indexOf(castList[j]) == -1)
                {
                    list.add(castList[j].toLowerCase());
                }
            }
        }

        sortResult(list);

        ArrayList<String> list1 = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++)
        {
            if (list.get(i).indexOf(searchTerm) != -1)
            {
                list1.add(list.get(i));
            }
        }

        for (int i = 0; i < list1.size(); i++)
        {
            String cast1 = list1.get(i);

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + cast1);
        }
        System.out.println("Which genre would you like to learn more about?");
        System.out.print("Enter number: ");
        int choice1 = scanner.nextInt();
        scanner.nextLine();

        searchTerm = list1.get(choice1-1);
        System.out.println(searchTerm);

        ArrayList<Movie> results = new ArrayList<Movie>();
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getCast();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
                //add the Movie objest to the results list
                results.add(movies.get(i));
        }
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void searchKeywords()
    {
        System.out.print("Enter a keyword search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getKeywords();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listGenres()
    {
        String genre = "";
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < movies.size(); i++)
        {
            genre = movies.get(i).getGenres();
            String[] genreList = genre.split("\\|");
            for (int j = 0; j < genreList.length; j++)
            {
                if (list.indexOf(genreList[j]) == -1)
                {
                    list.add(genreList[j]);
                }
            }
        }

        System.out.println("List of Genres:");
        sortResult(list);
        for (int i = 0; i < list.size(); i++)
        {
            String cast = list.get(i);

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + cast);
        }
        System.out.println("Which genre would you like to learn more about?");
        System.out.print("Enter number: ");
        int choice1 = scanner.nextInt();
        scanner.nextLine();

        String searchTerm = "";

        searchTerm = list.get(choice1-1);
        System.out.println(searchTerm);

        ArrayList<String> list1 = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++)
        {
            if (list.get(i).indexOf(searchTerm) != -1)
            {
                list1.add(list.get(i));
            }
        }
        System.out.println(list1);

        ArrayList<Movie> results = new ArrayList<Movie>();
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getGenres();

            for (int j = 0; j < list1.size(); j++)
            {
                if (movieTitle.indexOf(list1.get(j)) != -1)
                    //add the Movie objest to the results list
                    results.add(movies.get(i));
            }
        }
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listHighestRated()
    {
        int runtime = 0;
        for (int j = 0; j < movies.size() - 1; j++)
        {
            int minIndex = j;
            for (int k = j + 1; k < movies.size(); k++)
            {
                if (movies.get(k).getUserRating() < movies.get(minIndex).getUserRating())
                {
                    minIndex = k;
                }
                runtime++;
            }
            Movie temp = movies.get(j);
            movies.set(j, movies.get(minIndex));
            movies.set(minIndex, temp);
        }

        ArrayList<Movie> list = new ArrayList<Movie>();
        for (int i = movies.size()-1; i > movies.size()-51; i--)
        {
            list.add(movies.get(i));
        }

        for (int i = 0; i < list.size(); i++)
        {
            String title = list.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = list.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listHighestRevenue()
    {
        int runtime = 0;
        for (int j = 0; j < movies.size() - 1; j++)
        {
            int minIndex = j;
            for (int k = j + 1; k < movies.size(); k++)
            {
                if (movies.get(k).getRevenue() < movies.get(minIndex).getRevenue())
                {
                    minIndex = k;
                }
                runtime++;
            }
            Movie temp = movies.get(j);
            movies.set(j, movies.get(minIndex));
            movies.set(minIndex, temp);
        }

        ArrayList<Movie> list = new ArrayList<Movie>();
        for (int i = movies.size()-1; i > movies.size()-51; i--)
        {
            list.add(movies.get(i));
        }

        for (int i = 0; i < list.size(); i++)
        {
            String title = list.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = list.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    public void kevinBaconCount(String actorName)
    {
        ArrayList<Movie> list = new ArrayList<Movie>();
        int count = 0;
        for (int i = 0; i < movies.size(); i++)
        {
            if (movies.get(i).getCast().indexOf("Kevin Bacon") != -1)
            {
                list.add(movies.get(i));
            }
        }
        for (int j = 0; j < movies.size(); )
    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }

    public void importMovieListJSON(String fileName) {
        try {
            FileReader fileReader = new FileReader(fileName);

            movies = new ArrayList<Movie>();

            Scanner scanner = new Scanner(new File(fileName));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                //System.out.println(line);

                String castList = "";
                String directorsList = "";
                String producersList = "";
                String companiesList = "";

                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = (JSONObject) jsonParser.parse(line);

                //title
                String title = (String) jsonObject.get("title");
                //cast
                if (jsonObject.get("cast") != null)
                {
                    JSONArray cast = (JSONArray) jsonObject.get("cast");
                    Iterator<String> iterator = cast.iterator();
                    while (iterator.hasNext()) {
                        String caster = iterator.next();
                        if (caster.indexOf("[") != -1)
                        {
                            caster = caster.substring(2);
                            caster = caster.substring(0, caster.length() - 2);
                            castList += caster + "|";
                        }
                        else
                        {
                            castList += caster + "|";
                        }
                    }
                }

                //directors
                if (jsonObject.get("directors") != null) {
                    JSONArray directors = (JSONArray) jsonObject.get("directors");
                    Iterator<String> iterator1 = directors.iterator();
                    while (iterator1.hasNext()) {
                        String director = iterator1.next();
                        if (director.indexOf("[") != -1)
                        {
                            director = director.substring(2);
                            director = director.substring(0, director.length() - 2);
                            directorsList += director + "|";
                        }
                        else
                            directorsList += director + "|";
                    }
                }
                //producers
                if (jsonObject.get("producers") != null) {
                    JSONArray producers = (JSONArray) jsonObject.get("producers");
                    Iterator<String> iterator2 = producers.iterator();
                    while (iterator2.hasNext()) {
                        String producer = iterator2.next();
                        if (producer.indexOf("[") != -1)
                        {
                            producer = producer.substring(2);
                            producer = producer.substring(0, producer.length() - 2);
                            producersList += producer + "|";
                        }
                        else
                            producersList += producer + "|";
                    }
                }
                //companies
                if (jsonObject.get("companies") != null) {
                    JSONArray companies = (JSONArray) jsonObject.get("companies");
                    Iterator<String> iterator3 = companies.iterator();
                    while (iterator3.hasNext()) {
                        String company = iterator3.next();
                        if (company.indexOf("[") != -1)
                        {
                            company = company.substring(2);
                            company = company.substring(0, company.length() - 2);
                            companiesList += company + "|";
                        }
                        else
                            companiesList += company + "|";
                    }
                }
                //year
                long year = 0;
                if (jsonObject.get("year") != null) {
                    year = (long) jsonObject.get("year");
                }
                int year1 = (int) year;

                Movie nextMovie = new Movie(title, castList, directorsList, producersList, companiesList, year1);
                movies.add(nextMovie);
            }

            scanner.close();

        } catch (FileNotFoundException | ParseException e) {
            e.printStackTrace();
        }
    }
}
