package server;

import javafx.application.Application;
import javafx.beans.property.*;
import javafx.collections.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.sql.*;

public class mod5 extends Application {

    private final TableView<MovieRow> table = new TableView<>();
    private final ObservableList<MovieRow> data = FXCollections.observableArrayList();

    // Database credentials
    private static final String URL = "jdbc:mysql://localhost:3306/movie_catalog";
    private static final String USER = "root";
    private static final String PASS = "murugan06";

    public static void main(String[] args) {
    	
        launch(args);
    }

    @Override 
    public void start(Stage stage) {
        stage.setTitle("🎬 Movie Catalog - Module 5 (Interactive)");

        // ✅ Table Columns
        TableColumn<MovieRow, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(c -> c.getValue().idProperty().asObject());

        TableColumn<MovieRow, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(c -> c.getValue().titleProperty());

        TableColumn<MovieRow, String> directorCol = new TableColumn<>("Director");
        directorCol.setCellValueFactory(c -> c.getValue().directorProperty());

        TableColumn<MovieRow, Integer> yearCol = new TableColumn<>("Year");
        yearCol.setCellValueFactory(c -> c.getValue().yearProperty().asObject());

        TableColumn<MovieRow, String> genresCol = new TableColumn<>("Genres");
        genresCol.setCellValueFactory(c -> c.getValue().genresProperty());

        table.getColumns().addAll(idCol, titleCol, directorCol, yearCol, genresCol);
        table.setItems(data);
        loadMovies();

        // ✅ Input fields
        TextField titleField = new TextField();
        titleField.setPromptText("Title");
        TextField directorField = new TextField();
        directorField.setPromptText("Director");
        TextField yearField = new TextField();
        yearField.setPromptText("Year");
        TextField genresField = new TextField();
        genresField.setPromptText("Genres");

        Button addBtn = new Button("➕ Add");
        Button updateBtn = new Button("✏️ Update");
        Button deleteBtn = new Button("🗑 Delete");
        Button refreshBtn = new Button("🔄 Refresh");

        // ✅ Add new movie
        addBtn.setOnAction(e -> {
            String title = titleField.getText();
            String director = directorField.getText();
            String genres = genresField.getText();
            int year;
            try {
                year = Integer.parseInt(yearField.getText());
            } catch (NumberFormatException ex) {
                showAlert("Invalid Input", "Year must be a number!");
                return;
            }

            addMovie(title, director, year, genres);
            loadMovies();
            clearFields(titleField, directorField, yearField, genresField);
        });

        // ✅ Update selected movie
        updateBtn.setOnAction(e -> {
            MovieRow selected = table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showAlert("No Selection", "Select a movie to update!");
                return;
            }

            String title = titleField.getText();
            String director = directorField.getText();
            String genres = genresField.getText();
            int year;
            try {
                year = Integer.parseInt(yearField.getText());
            } catch (NumberFormatException ex) {
                showAlert("Invalid Input", "Year must be a number!");
                return;
            }

            updateMovie(selected.getId(), title, director, year, genres);
            loadMovies();
            clearFields(titleField, directorField, yearField, genresField);
        });

        // ✅ Delete selected movie
        deleteBtn.setOnAction(e -> {
            MovieRow selected = table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showAlert("No Selection", "Select a movie to delete!");
                return;
            }
            deleteMovie(selected.getId());
            loadMovies();
        });

        // ✅ Refresh button
        refreshBtn.setOnAction(e -> loadMovies());

        // ✅ Select a row to edit
        table.setOnMouseClicked(e -> {
            MovieRow selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                titleField.setText(selected.getTitle());
                directorField.setText(selected.getDirector());
                yearField.setText(String.valueOf(selected.getYear()));
                genresField.setText(selected.getGenres());
            }
        });

        // ✅ Layout
        HBox inputBox = new HBox(10, titleField, directorField, yearField, genresField);
        HBox btnBox = new HBox(10, addBtn, updateBtn, deleteBtn, refreshBtn);
        VBox root = new VBox(10, new Label("🎥 Movie Catalog (Database CRUD)"), table, inputBox, btnBox);
        root.setPadding(new Insets(15));

        stage.setScene(new Scene(root, 800, 500));
        stage.show();
    }

    // --- Database Methods ---

    private void loadMovies() {
        data.clear();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM movies")) {

            while (rs.next()) {
                data.add(new MovieRow(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("director"),
                        rs.getInt("year"),
                        rs.getString("genres")
                ));
            }
        } catch (SQLException e) {
            showAlert("Database Error", e.getMessage());
        }
    }

    private void addMovie(String title, String director, int year, String genres) {
        String sql = "INSERT INTO movies (title, director, year, genres) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setString(2, director);
            ps.setInt(3, year);
            ps.setString(4, genres);
            ps.executeUpdate();
        } catch (SQLException e) {
            showAlert("Insert Error", e.getMessage());
        }
    }

    private void updateMovie(int id, String title, String director, int year, String genres) {
        String sql = "UPDATE movies SET title=?, director=?, year=?, genres=? WHERE id=?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setString(2, director);
            ps.setInt(3, year);
            ps.setString(4, genres);
            ps.setInt(5, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            showAlert("Update Error", e.getMessage());
        }
    }

    private void deleteMovie(int id) {
        String sql = "DELETE FROM movies WHERE id=?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            showAlert("Delete Error", e.getMessage());
        }
    }

    private void clearFields(TextField... fields) {
        for (TextField f : fields) f.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // --- Inner Class for Table ---
    public static class MovieRow {
        private final IntegerProperty id;
        private final StringProperty title;
        private final StringProperty director;
        private final IntegerProperty year;
        private final StringProperty genres;

        public MovieRow(int id, String title, String director, int year, String genres) {
            this.id = new SimpleIntegerProperty(id);
            this.title = new SimpleStringProperty(title);
            this.director = new SimpleStringProperty(director);
            this.year = new SimpleIntegerProperty(year);
            this.genres = new SimpleStringProperty(genres);
        }

        public int getId() { return id.get(); }
        public String getTitle() { return title.get(); }
        public String getDirector() { return director.get(); }
        public int getYear() { return year.get(); }
        public String getGenres() { return genres.get(); }

        public IntegerProperty idProperty() { return id; }
        public StringProperty titleProperty() { return title; }
        public StringProperty directorProperty() { return director; }
        public IntegerProperty yearProperty() { return year; }
        public StringProperty genresProperty() { return genres; }
    }
}
