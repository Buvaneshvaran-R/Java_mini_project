package server;

import java.io.Serializable;
import java.util.*;

public class mod1 implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;

    private final UUID id;
    private String title;
    private String director;
    private int year;
    private List<String> genres = new ArrayList<>();
    private static int movieCount = 0;

    public mod1(String title, String director, int year, List<String> genres) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.director = director;
        this.year = year;
        if (genres != null) this.genres.addAll(genres);
        movieCount++;
    }

    // Getters and setters
    public UUID getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    public List<String> getGenres() { return Collections.unmodifiableList(genres); }

    public void addGenre(String genre) { genres.add(genre); }
    public static int getMovieCount() { return movieCount; }

    public void displayInfo() {
        System.out.println("🎬 Movie ID: " + id);
        System.out.println("Title: " + title);
        System.out.println("Director: " + director);
        System.out.println("Year: " + year);
        System.out.println("Genres: " + genres);
        System.out.println();
    }

    @Override
    public String toString() {
        return String.format("%s (%d) by %s - %s", title, year, director, genres);
    }

    public static void main(String[] args) {
        mod1 m1 = new mod1("Inception", "Nolan", 2010, Arrays.asList("Sci-Fi"));
        mod1 m2 = new mod1("Avatar", "Cameron", 2009, Arrays.asList("Action"));
        m1.displayInfo();
        m2.displayInfo();
        System.out.println("✅ Total movies created: " + getMovieCount());
    }
}
