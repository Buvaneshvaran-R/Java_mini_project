package server;

import java.util.*;

public class mod3 {
    private final List<mod1> movies = new ArrayList<>();

    public void addMovie(mod1 m) { movies.add(m); }
    public List<mod1> getAllMovies() { return movies; }

    public List<mod1> searchByTitle(String keyword) {
        List<mod1> result = new ArrayList<>();
        for (mod1 m : movies) {
            if (m.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(m);
            }
        }
        return result;
    }

    public void sortByYear() {
        movies.sort(Comparator.comparingInt(mod1::getYear));
    }

    public static void main(String[] args) {
        mod3 repo = new mod3();
        repo.addMovie(new mod1("Inception", "Nolan", 2010, Arrays.asList("Sci-Fi")));
        repo.addMovie(new mod1("Avatar", "Cameron", 2009, Arrays.asList("Action")));

        System.out.println("🎬 All Movies (sorted by year):");
        repo.sortByYear();
        repo.getAllMovies().forEach(mod1::displayInfo);

        System.out.println("🔍 Search results for 'in':");
        repo.searchByTitle("in").forEach(mod1::displayInfo);
    }
}
