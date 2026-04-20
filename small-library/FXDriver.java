package project_1;

import java.io.*;
import java.util.*;
import java.util.stream.*;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FXDriver extends Application {

    private ArrayList<Book> bookList = new ArrayList<>();
    private ObservableList<Book> observableBooks = FXCollections.observableArrayList();
    private FileHandler fileHandler = new FileHandler();

    private TableView<Book> tableView = new TableView<>();

    // Insert fields
    private TextField tfId       = new TextField();
    private TextField tfTitle    = new TextField();
    private TextField tfAuthor   = new TextField();
    private TextField tfCategory = new TextField();
    private TextField tfYear     = new TextField();
    private TextField tfIsbn     = new TextField();

    // Delete
    private TextField tfDeleteId = new TextField();

    // Search
    private TextField tfSearch        = new TextField();
    private ComboBox<String> cbSearchType = new ComboBox<>();

    // Edit
    private TextField tfEditId    = new TextField();
    private TextField tfEditValue = new TextField();
    private ComboBox<String> cbEditField = new ComboBox<>();

    // Sort
    private ComboBox<String> cbSort = new ComboBox<>();

    // Statistics
    private TextField tfStatYear   = new TextField();
    private TextField tfStatAuthor = new TextField();
    private TextArea  taStats      = new TextArea();

    // Active author
    private TextField tfActiveAuthor  = new TextField();
    private Label     lblActiveResult = new Label();

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage primaryStage) {

        // Build table columns
        TableColumn<Book, Integer> colId    = new TableColumn<>("BookID");
        colId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        TableColumn<Book, String>  colTitle  = new TableColumn<>("Title");
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn<Book, String>  colAuthor = new TableColumn<>("Author");
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        TableColumn<Book, String>  colCat    = new TableColumn<>("Category");
        colCat.setCellValueFactory(new PropertyValueFactory<>("category"));
        TableColumn<Book, Integer> colYear   = new TableColumn<>("Published Year");
        colYear.setCellValueFactory(new PropertyValueFactory<>("publishedYear"));
        TableColumn<Book, String>  colIsbn   = new TableColumn<>("ISBN");
        colIsbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));

        colId.setPrefWidth(70);
        colTitle.setPrefWidth(180);
        colAuthor.setPrefWidth(140);
        colCat.setPrefWidth(100);
        colYear.setPrefWidth(110);
        colIsbn.setPrefWidth(140);

        tableView.getColumns().addAll(colId, colTitle, colAuthor, colCat, colYear, colIsbn);
        tableView.setItems(observableBooks);

        // ── Left panel ──────────────────────────────────────────────────────────
        VBox leftPanel = new VBox(8);
        leftPanel.setPadding(new Insets(10));
        leftPanel.setPrefWidth(265);

        // File
        Button btnLoad = new Button("Load books.txt");
        Button btnSave = new Button("Save to updatedBooks.txt");
        btnLoad.setMaxWidth(Double.MAX_VALUE);
        btnSave.setMaxWidth(Double.MAX_VALUE);
        btnLoad.setOnAction(e -> loadFile(primaryStage));
        btnSave.setOnAction(e -> saveFile(primaryStage));

        // Insert
        tfId.setPromptText("Book ID");
        tfTitle.setPromptText("Title");
        tfAuthor.setPromptText("Author");
        tfCategory.setPromptText("Category");
        tfYear.setPromptText("Published Year");
        tfIsbn.setPromptText("ISBN");
        Button btnInsert = new Button("Insert Book");
        btnInsert.setMaxWidth(Double.MAX_VALUE);
        btnInsert.setOnAction(e -> insertBook());

        // Delete
        tfDeleteId.setPromptText("Book ID to delete");
        Button btnDelete = new Button("Delete Book");
        btnDelete.setMaxWidth(Double.MAX_VALUE);
        btnDelete.setOnAction(e -> deleteBook());

        // Search
        cbSearchType.setItems(FXCollections.observableArrayList("By ID", "By Title", "By Author"));
        cbSearchType.setValue("By Title");
        cbSearchType.setMaxWidth(Double.MAX_VALUE);
        tfSearch.setPromptText("Keyword");
        Button btnSearch  = new Button("Search");
        Button btnShowAll = new Button("Show All");
        btnSearch.setMaxWidth(Double.MAX_VALUE);
        btnShowAll.setMaxWidth(Double.MAX_VALUE);
        btnSearch.setOnAction(e -> searchBooks());
        btnShowAll.setOnAction(e -> { observableBooks.setAll(bookList); status("Showing all books."); });

        // Edit
        tfEditId.setPromptText("Book ID to edit");
        cbEditField.setItems(FXCollections.observableArrayList(
                "Title", "Author", "Category", "Published Year", "ISBN"));
        cbEditField.setValue("Title");
        cbEditField.setMaxWidth(Double.MAX_VALUE);
        tfEditValue.setPromptText("New value");
        Button btnEdit = new Button("Apply Edit");
        btnEdit.setMaxWidth(Double.MAX_VALUE);
        btnEdit.setOnAction(e -> editBook());

        // Sort
        cbSort.setItems(FXCollections.observableArrayList(
                "Title (A-Z)", "Author (A-Z)", "Year (Asc)", "Year (Desc)"));
        cbSort.setValue("Title (A-Z)");
        cbSort.setMaxWidth(Double.MAX_VALUE);
        Button btnSort = new Button("Sort");
        btnSort.setMaxWidth(Double.MAX_VALUE);
        btnSort.setOnAction(e -> sortBooks());

        // Statistics
        tfStatYear.setPromptText("Year (optional)");
        tfStatAuthor.setPromptText("Author (optional)");
        Button btnStats = new Button("Show Statistics");
        btnStats.setMaxWidth(Double.MAX_VALUE);
        btnStats.setOnAction(e -> showStatistics());
        taStats.setEditable(false);
        taStats.setPrefHeight(150);
        taStats.setWrapText(true);

        // Active author
        tfActiveAuthor.setPromptText("Author name");
        Button btnActive = new Button("Check Active Author");
        btnActive.setMaxWidth(Double.MAX_VALUE);
        btnActive.setOnAction(e -> checkActiveAuthor());

        leftPanel.getChildren().addAll(
                new Label("File:"),
                btnLoad, btnSave,
                new Separator(),
                new Label("Insert Book:"),
                tfId, tfTitle, tfAuthor, tfCategory, tfYear, tfIsbn, btnInsert,
                new Separator(),
                new Label("Delete Book:"),
                tfDeleteId, btnDelete,
                new Separator(),
                new Label("Search:"),
                cbSearchType, tfSearch, btnSearch, btnShowAll,
                new Separator(),
                new Label("Edit Book:"),
                tfEditId, cbEditField, tfEditValue, btnEdit,
                new Separator(),
                new Label("Sort:"),
                cbSort, btnSort,
                new Separator(),
                new Label("Statistics:"),
                tfStatYear, tfStatAuthor, btnStats, taStats,
                new Separator(),
                new Label("Check Active Author:"),
                tfActiveAuthor, btnActive, lblActiveResult
        );

        ScrollPane scroll = new ScrollPane(leftPanel);
        scroll.setFitToWidth(true);
        scroll.setPrefWidth(285);

        BorderPane root = new BorderPane();
        root.setLeft(scroll);
        root.setCenter(tableView);

        primaryStage.setScene(new Scene(root, 1000, 650));
        primaryStage.setTitle("Library Management System");
        primaryStage.show();
    }

    // ── Load ────────────────────────────────────────────────────────────────────
    private void loadFile(Stage stage) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Select books.txt");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fc.showOpenDialog(stage);
        if (file == null) return;
        bookList = fileHandler.fileReader(file);
        observableBooks.setAll(bookList);
        status("Loaded " + bookList.size() + " books from " + file.getName());
    }

    // ── Save ────────────────────────────────────────────────────────────────────
    private void saveFile(Stage stage) {
        if (bookList.isEmpty()) { status("No books to save."); return; }
        FileChooser fc = new FileChooser();
        fc.setTitle("Save file");
        fc.setInitialFileName("updatedBooks.txt");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fc.showSaveDialog(stage);
        if (file == null) return;
        fileHandler.fileWriter(file, bookList);
        status("Saved " + bookList.size() + " books to " + file.getName());
    }

    // ── Insert ──────────────────────────────────────────────────────────────────
    private void insertBook() {
        try {
            int id      = Integer.parseInt(tfId.getText().trim());
            if (id < 0) {
                status("ID cannot be negative.");
                return;
            }
            String title    = tfTitle.getText().trim();
            String author   = tfAuthor.getText().trim();
            String category = tfCategory.getText().trim();
            int year        = Integer.parseInt(tfYear.getText().trim());
            String isbn     = tfIsbn.getText().trim();

            if (title.isEmpty() || author.isEmpty() || category.isEmpty() || isbn.isEmpty()) {
                status("Fill all fields."); return;
            }
            boolean exists = false;
            for (int i = 0; i < bookList.size(); i++) {
                if (bookList.get(i).getBookId() == id) {
                    exists = true;
                    break;
                }
            }
            if (exists) {
                status("ID " + id + " already exists.");
                return;
            }
            bookList.add(new Book(id, title, author, category, year, isbn));
            observableBooks.setAll(bookList);
            tfId.clear(); tfTitle.clear(); tfAuthor.clear();
            tfCategory.clear(); tfYear.clear(); tfIsbn.clear();
            status("Book inserted: " + title);
        } catch (NumberFormatException e) {
            status("ID and Year must be numbers.");
        }
    }

    // ── Delete ──────────────────────────────────────────────────────────────────
    private void deleteBook() {
        try {
            int id = Integer.parseInt(tfDeleteId.getText().trim());
            boolean removed = false;
            for (int i = 0; i < bookList.size(); i++) {
                if (bookList.get(i).getBookId() == id) {
                    bookList.remove(i);
                    removed = true;
                    break;
                }
            }
            observableBooks.setAll(bookList);
            tfDeleteId.clear();
            status(removed ? "Book " + id + " deleted." : "No book with ID " + id);
        } catch (NumberFormatException e) {
            status("Enter a valid numeric ID.");
        }
    }

    // ── Search ──────────────────────────────────────────────────────────────────
    private void searchBooks() {
        String kw = tfSearch.getText().trim().toLowerCase();
        if (kw.isEmpty()) { status("Enter a keyword."); return; }

        List<Book> results;
        switch (cbSearchType.getValue()) {
            case "By ID":
                try {
                    int id = Integer.parseInt(kw);
                    results = bookList.stream().filter(b -> b.getBookId() == id).collect(Collectors.toList());
                } catch (NumberFormatException e) { status("ID must be a number."); return; }
                break;
            case "By Author":
                results = bookList.stream()
                        .filter(b -> b.getAuthor().toLowerCase().contains(kw))
                        .collect(Collectors.toList());
                break;
            default:
                results = bookList.stream()
                        .filter(b -> b.getTitle().toLowerCase().contains(kw))
                        .collect(Collectors.toList());
        }
        observableBooks.setAll(results);
        status("Found " + results.size() + " result(s) for: " + kw);
    }

    // ── Edit ────────────────────────────────────────────────────────────────────
    private void editBook() {
        try {
            int id = Integer.parseInt(tfEditId.getText().trim());
            String newVal = tfEditValue.getText().trim();
            if (newVal.isEmpty()) { status("Enter a new value."); return; }

            Optional<Book> opt = bookList.stream().filter(b -> b.getBookId() == id).findFirst();
            if (!opt.isPresent()) { status("No book with ID " + id); return; }

            Book b = opt.get();
            switch (cbEditField.getValue()) {
                case "Title":          b.setTitle(newVal);                           break;
                case "Author":         b.setAuthor(newVal);                          break;
                case "Category":       b.setCategory(newVal);                        break;
                case "Published Year": b.setPublishedYear(Integer.parseInt(newVal)); break;
                case "ISBN":           b.setIsbn(newVal);                            break;
            }
            observableBooks.setAll(bookList);
            status("Book " + id + " updated.");
        } catch (NumberFormatException e) {
            status("ID and Year must be numbers.");
        }
    }

    // ── Sort ────────────────────────────────────────────────────────────────────
    private void sortBooks() {
        switch (cbSort.getValue()) {
            case "Title (A-Z)":  bookList.sort(Comparator.comparing(Book::getTitle,  String.CASE_INSENSITIVE_ORDER)); break;
            case "Author (A-Z)": bookList.sort(Comparator.comparing(Book::getAuthor, String.CASE_INSENSITIVE_ORDER)); break;
            case "Year (Asc)":   bookList.sort(Comparator.comparingInt(Book::getPublishedYear));                      break;
            case "Year (Desc)":  bookList.sort(Comparator.comparingInt(Book::getPublishedYear).reversed());           break;
        }
        observableBooks.setAll(bookList);
        status("Sorted by: " + cbSort.getValue());
    }

    // ── Statistics ──────────────────────────────────────────────────────────────
    private void showStatistics() {
        if (bookList.isEmpty()) { status("Load books first."); return; }

        String result = "";

        // Books by Category
        result += "Books by Category:\n";
        ArrayList<String> countedCategories = new ArrayList<>();
        for (int i = 0; i < bookList.size(); i++) {
            String cat = bookList.get(i).getCategory();
            if (!countedCategories.contains(cat)) {
                countedCategories.add(cat);
                int cnt = 0;
                for (int j = 0; j < bookList.size(); j++) {
                    if (bookList.get(j).getCategory().equals(cat)) {
                        cnt++;
                    }
                }
                result += "  " + cat + ": " + cnt + "\n";
            }
        }

        // Books by Author
        result += "\nBooks by Author:\n";
        ArrayList<String> countedAuthors = new ArrayList<>();
        for (int i = 0; i < bookList.size(); i++) {
            String au = bookList.get(i).getAuthor();
            if (!countedAuthors.contains(au)) {
                countedAuthors.add(au);
                int cnt = 0;
                for (int j = 0; j < bookList.size(); j++) {
                    if (bookList.get(j).getAuthor().equals(au)) {
                        cnt++;
                    }
                }
                result += "  " + au + ": " + cnt + "\n";
            }
        }

        // Books in specific year
        String yrStr = tfStatYear.getText().trim();
        if (!yrStr.isEmpty()) {
            try {
                int yr = Integer.parseInt(yrStr);
                int cnt = 0;
                for (int i = 0; i < bookList.size(); i++) {
                    if (bookList.get(i).getPublishedYear() == yr) {
                        cnt++;
                    }
                }
                result += "\nBooks in " + yr + ": " + cnt + "\n";
            } catch (NumberFormatException e) {
                result += "\nInvalid year.\n";
            }
        }

        // Year with most and least books
        int maxYearCount = 0, minYearCount = Integer.MAX_VALUE;
        int maxYear = 0, minYear = 0;
        ArrayList<Integer> countedYears = new ArrayList<>();
        for (int i = 0; i < bookList.size(); i++) {
            int yr = bookList.get(i).getPublishedYear();
            if (!countedYears.contains(yr)) {
                countedYears.add(yr);
                int cnt = 0;
                for (int j = 0; j < bookList.size(); j++) {
                    if (bookList.get(j).getPublishedYear() == yr) {
                        cnt++;
                    }
                }
                if (cnt > maxYearCount) {
                    maxYearCount = cnt;
                    maxYear = yr;
                }
                if (cnt < minYearCount) {
                    minYearCount = cnt;
                    minYear = yr;
                }
            }
        }
        result += "\nYear with most books: " + maxYear + " (" + maxYearCount + ")\n";
        result += "Year with least books: " + minYear + " (" + minYearCount + ")\n";

        // Author with most and least books
        int maxAuthorCount = 0, minAuthorCount = Integer.MAX_VALUE;
        String maxAuthor = "", minAuthor = "";
        ArrayList<String> countedAuthors2 = new ArrayList<>();
        for (int i = 0; i < bookList.size(); i++) {
            String au = bookList.get(i).getAuthor();
            if (!countedAuthors2.contains(au)) {
                countedAuthors2.add(au);
                int cnt = 0;
                for (int j = 0; j < bookList.size(); j++) {
                    if (bookList.get(j).getAuthor().equals(au)) {
                        cnt++;
                    }
                }
                if (cnt > maxAuthorCount) {
                    maxAuthorCount = cnt;
                    maxAuthor = au;
                }
                if (cnt < minAuthorCount) {
                    minAuthorCount = cnt;
                    minAuthor = au;
                }
            }
        }

        result += "\nAuthor with most books: " + maxAuthor + " (" + maxAuthorCount + ")\n";
        for (int i = 0; i < bookList.size(); i++) {
            if (bookList.get(i).getAuthor().equals(maxAuthor)) {
                result += "  - " + bookList.get(i).getTitle() + "\n";
            }
        }

        result += "Author with least books: " + minAuthor + " (" + minAuthorCount + ")\n";
        for (int i = 0; i < bookList.size(); i++) {
            if (bookList.get(i).getAuthor().equals(minAuthor)) {
                result += "  - " + bookList.get(i).getTitle() + "\n";
            }
        }

        // Books by specific author
        String auStr = tfStatAuthor.getText().trim();
        if (!auStr.isEmpty()) {
            int cnt = 0;
            for (int i = 0; i < bookList.size(); i++) {
                if (bookList.get(i).getAuthor().equalsIgnoreCase(auStr)) {
                    cnt++;
                }
            }
            result += "\nBooks by " + auStr + ": " + cnt + "\n";
        }

        taStats.setText(result);
        status("Statistics generated.");
    }

    // ── Active Author ────────────────────────────────────────────────────────────
    private void checkActiveAuthor() {
        String name = tfActiveAuthor.getText().trim();
        if (name.isEmpty()) { status("Enter an author name."); return; }

        int currentYear = java.time.Year.now().getValue();

        boolean exists = false;
        for (int i = 0; i < bookList.size(); i++) {
            if (bookList.get(i).getAuthor().equalsIgnoreCase(name)) {
                exists = true;
                break;
            }
        }
        if (!exists) { lblActiveResult.setText("Author not found."); return; }

        boolean active = false;
        for (int i = 0; i < bookList.size(); i++) {
            if (bookList.get(i).getAuthor().equalsIgnoreCase(name)) {
                if ((currentYear - bookList.get(i).getPublishedYear()) <= 5) {
                    active = true;
                    break;
                }
            }
        }

        if (active) {
            lblActiveResult.setText(name + " is ACTIVE.");
        } else {
            lblActiveResult.setText(name + " is NOT active.");
        }
        status("Author check done.");
    }

    private void status(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Library System");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }}