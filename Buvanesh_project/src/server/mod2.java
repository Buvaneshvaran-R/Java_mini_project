package server;

import java.io.*;
import java.util.*;

public class mod2 {
    private static final String FILE_NAME = "movies.dat";

    public static void saveMovies(List<mod1> movies) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(movies);
            System.out.println("✅ Movies saved to file successfully!");
        } catch (IOException e) {
            System.out.println("❌ Error saving movies: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static List<mod1> loadMovies() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<mod1>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("⚠️ No saved file found. Starting with empty list.");
            return new ArrayList<>();
        }
    }

    public static void main(String[] args) {
        List<mod1> movies = new ArrayList<>();
        movies.add(new mod1("Avatar", "James Cameron", 2009, Arrays.asList("Action", "Adventure")));
        movies.add(new mod1("Interstellar", "Christopher Nolan", 2014, Arrays.asList("Sci-Fi", "Drama")));
        saveMovies(movies);

        System.out.println("\n🎬 Loaded Movies from File:");
        List<mod1> loaded = loadMovies();
        loaded.forEach(mod1::displayInfo);
    }
}
