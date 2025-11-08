package server;

import java.sql.*;

public class mod4 {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/movie_catalog";
        String user = "root";
        String pass = "murugan06";

        // ✅ Ensure the MySQL JDBC Driver is loaded
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("❌ MySQL JDBC Driver not found. Add mysql-connector-j.jar to classpath.");
            return;
        }

        // ✅ Database connection and query
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, title, director, year, genres FROM movies")) {

            System.out.println("🎬 Movies from Database:\n--------------------------------");
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String director = rs.getString("director");
                int year = rs.getInt("year");
                String genres = rs.getString("genres");

                System.out.printf("%d | %-20s | %-15s | %d | %s%n",
                        id, title, director, year, genres);
            }

        } catch (SQLException e) {
            System.out.println("❌ Database Error: " + e.getMessage());
        }
    }
}
