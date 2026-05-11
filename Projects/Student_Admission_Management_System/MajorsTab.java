import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class MajorsTab {

    private final Tab tab;
    private final TableView<Major> majorTable;
    private final ObservableList<Major> majors;
    private final ObservableList<Student> students;
    private final AdmissionSystem system;
    private final Stage primaryStage;

    public MajorsTab(AdmissionSystem system, ObservableList<Major> majors, ObservableList<Student> students, Stage primaryStage) {
        this.system = system;
        this.majors = majors;
        this.students = students;
        this.primaryStage = primaryStage;

        tab = new Tab("Majors");

        // ارجع لسطر 72

        majorTable = new TableView<>();
        majorTable.setItems(majors);

        TableColumn<Major, String> majorNameCol = new TableColumn<>("Major Name");
        majorNameCol.setCellValueFactory(new PropertyValueFactory<>("majorName"));

        TableColumn<Major, Integer> capacityCol = new TableColumn<>("Capacity");
        capacityCol.setCellValueFactory(new PropertyValueFactory<>("capacity"));

        TableColumn<Major, Double> acceptanceGradeCol = new TableColumn<>("Acceptance Grade");
        acceptanceGradeCol.setCellValueFactory(new PropertyValueFactory<>("acceptanceGrade"));

        TableColumn<Major, Double> tawjihiWeightCol = new TableColumn<>("Tawjihi Weight");
        tawjihiWeightCol.setCellValueFactory(new PropertyValueFactory<>("tawjihiWeight"));

        TableColumn<Major, Double> placementWeightCol = new TableColumn<>("Placement Weight");
        placementWeightCol.setCellValueFactory(new PropertyValueFactory<>("placementWeight"));

        majorTable.getColumns().addAll(majorNameCol, capacityCol, acceptanceGradeCol, tawjihiWeightCol, placementWeightCol);
        majorTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // ارجع لسطر 74 73


        BorderPane majorPane = new BorderPane();
        majorPane.setCenter(majorTable);

        HBox majorButtons = new HBox(10);
        Button addMajorBtn = new Button("Add");
        Button deleteMajorBtn = new Button("Delete");
        Button updateMajorBtn = new Button("Update");
        Button searchMajorBtn = new Button("Search");
        Button loadMajorBtn = new Button("Load File");
        majorButtons.getChildren().addAll(addMajorBtn, deleteMajorBtn, updateMajorBtn, searchMajorBtn, loadMajorBtn);

        // Load
        loadMajorBtn.setOnAction(e -> {
            FileChooser fc = new FileChooser();
            fc.setTitle("Select Criteria File");
            File criteriaFile = fc.showOpenDialog(primaryStage);
            if (criteriaFile != null) {
                try {
                    system.clearMajors();
                    system.loadCriteria(criteriaFile.getPath());
                    majors.clear();
                    Node<Major> curr = system.getMajorsHead().getNext();
                    while (curr != system.getMajorsHead()) {
                        majors.add(curr.getData());
                        curr = curr.getNext();
                    }
                    students.clear();
                    Node<Student> currS = system.getAllStudentsHead().getNext();
                    while (currS != system.getAllStudentsHead()) {
                        students.add(currS.getData());
                        currS = currS.getNext();
                    }
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Criteria file loaded successfully!");
                } catch (Exception ex) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to load criteria file!");
                }
            }
        });
        // Add
        addMajorBtn.setOnAction(e -> {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Add Major");
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            TextField majorNameField = new TextField();
            majorNameField.setPromptText("Major Name");

            TextField capacityField = new TextField();
            capacityField.setPromptText("Capacity");

            TextField acceptanceGradeField = new TextField();
            acceptanceGradeField.setPromptText("Acceptance Grade");

            TextField tawjihiWeightField = new TextField();
            tawjihiWeightField.setPromptText("Tawjihi Weight");

            TextField placementWeightField = new TextField();
            placementWeightField.setPromptText("Placement Weight");

            VBox layout = new VBox(10);
            layout.getChildren().addAll(majorNameField, capacityField, acceptanceGradeField, tawjihiWeightField, placementWeightField);
            dialog.getDialogPane().setContent(layout);

            dialog.showAndWait().ifPresent(result -> {
                if (result == ButtonType.OK) {

                    if (majorNameField.getText().isEmpty() || capacityField.getText().isEmpty() ||
                            acceptanceGradeField.getText().isEmpty() || tawjihiWeightField.getText().isEmpty() ||
                            placementWeightField.getText().isEmpty()) {
                        showAlert(Alert.AlertType.ERROR, "Error", "All fields are required!");
                        return;
                    }

                    int capacity;
                    try {
                        capacity = Integer.parseInt(capacityField.getText().trim());
                        if (capacity <= 0) {
                            showAlert(Alert.AlertType.ERROR, "Error", "Capacity must be a positive number!");
                            return;
                        }
                    } catch (NumberFormatException ex) {
                        showAlert(Alert.AlertType.ERROR, "Error", "Capacity must be a number!");
                        return;
                    }

                    double acceptanceGrade, tawjihiWeight, placementWeight;
                    try {
                        acceptanceGrade = Double.parseDouble(acceptanceGradeField.getText().trim());
                        tawjihiWeight = Double.parseDouble(tawjihiWeightField.getText().trim());
                        placementWeight = Double.parseDouble(placementWeightField.getText().trim());
                        if (acceptanceGrade < 0 || acceptanceGrade > 100) {
                            showAlert(Alert.AlertType.ERROR, "Error", "Acceptance grade must be between 0 and 100!");
                            return;
                        }
                        if (tawjihiWeight + placementWeight != 1.0) {
                            showAlert(Alert.AlertType.ERROR, "Error", "Tawjihi and Placement weights must sum to 1!");
                            return;
                        }
                    } catch (NumberFormatException ex) {
                        showAlert(Alert.AlertType.ERROR, "Error", "Grades and weights must be numbers!");
                        return;
                    }

                    if (system.searchMajor(majorNameField.getText()) != null) {
                        showAlert(Alert.AlertType.ERROR, "Error", "Major already exists!");
                        return;
                    }

                    system.insertMajor(majorNameField.getText(), capacity, acceptanceGrade, tawjihiWeight, placementWeight);
                    majors.add(system.searchMajor(majorNameField.getText()));
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Major added successfully!");
                }
            });
        });
        // Delete
        deleteMajorBtn.setOnAction(e -> {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Delete Major");
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            TextField majorNameField = new TextField();
            majorNameField.setPromptText("Major Name");

            dialog.getDialogPane().setContent(majorNameField);

            dialog.showAndWait().ifPresent(result -> {
                if (result == ButtonType.OK) {
                    if (majorNameField.getText().isEmpty()) {
                        showAlert(Alert.AlertType.ERROR, "Error", "Major name is required!");
                        return;
                    }

                    Major deleted = system.deleteMajor(majorNameField.getText());
                    if (deleted == null) {
                        showAlert(Alert.AlertType.ERROR, "Error", "Major not found!");
                        return;
                    }

                    majors.remove(deleted);
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Major deleted successfully!");
                }
            });
        });
        // Update
        updateMajorBtn.setOnAction(e -> {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Update Major");
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            TextField oldMajorNameField = new TextField();
            oldMajorNameField.setPromptText("Current Major Name");

            TextField newMajorNameField = new TextField();
            newMajorNameField.setPromptText("New Major Name");

            TextField capacityField = new TextField();
            capacityField.setPromptText("Capacity");

            TextField acceptanceGradeField = new TextField();
            acceptanceGradeField.setPromptText("Acceptance Grade");

            TextField tawjihiWeightField = new TextField();
            tawjihiWeightField.setPromptText("Tawjihi Weight");

            TextField placementWeightField = new TextField();
            placementWeightField.setPromptText("Placement Weight");

            VBox layout = new VBox(10);
            layout.getChildren().addAll(oldMajorNameField, newMajorNameField, capacityField, acceptanceGradeField, tawjihiWeightField, placementWeightField);
            dialog.getDialogPane().setContent(layout);

            dialog.showAndWait().ifPresent(result -> {
                if (result == ButtonType.OK) {

                    if (oldMajorNameField.getText().isEmpty() || newMajorNameField.getText().isEmpty() ||
                            capacityField.getText().isEmpty() || acceptanceGradeField.getText().isEmpty() ||
                            tawjihiWeightField.getText().isEmpty() || placementWeightField.getText().isEmpty()) {
                        showAlert(Alert.AlertType.ERROR, "Error", "All fields are required!");
                        return;
                    }

                    if (system.searchMajor(oldMajorNameField.getText()) == null) {
                        showAlert(Alert.AlertType.ERROR, "Error", "Major not found!");
                        return;
                    }

                    int capacity;
                    try {
                        capacity = Integer.parseInt(capacityField.getText().trim());
                        if (capacity <= 0) {
                            showAlert(Alert.AlertType.ERROR, "Error", "Capacity must be a positive number!");
                            return;
                        }
                    } catch (NumberFormatException ex) {
                        showAlert(Alert.AlertType.ERROR, "Error", "Capacity must be a number!");
                        return;
                    }

                    double acceptanceGrade, tawjihiWeight, placementWeight;
                    try {
                        acceptanceGrade = Double.parseDouble(acceptanceGradeField.getText().trim());
                        tawjihiWeight = Double.parseDouble(tawjihiWeightField.getText().trim());
                        placementWeight = Double.parseDouble(placementWeightField.getText().trim());
                        if (tawjihiWeight + placementWeight != 1.0) {
                            showAlert(Alert.AlertType.ERROR, "Error", "Tawjihi and Placement weights must sum to 1!");
                            return;
                        }
                    } catch (NumberFormatException ex) {
                        showAlert(Alert.AlertType.ERROR, "Error", "Grades and weights must be numbers!");
                        return;
                    }

                    system.updateMajor(oldMajorNameField.getText(), newMajorNameField.getText(), capacity, acceptanceGrade, tawjihiWeight, placementWeight);
                    majorTable.refresh();
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Major updated successfully!");
                }
            });
        });
        // Search
        searchMajorBtn.setOnAction(e -> {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Search for a Major");
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            TextField majorNameField = new TextField();
            majorNameField.setPromptText("Major Name");

            dialog.getDialogPane().setContent(majorNameField);

            dialog.showAndWait().ifPresent(result -> {
                if (result == ButtonType.OK) {
                    if (majorNameField.getText().isEmpty()) {
                        showAlert(Alert.AlertType.ERROR, "Error", "Major name is required!");
                        return;
                    }

                    Major m = system.searchMajor(majorNameField.getText());
                    if (m == null) {
                        showAlert(Alert.AlertType.ERROR, "Error", "Major not found!");
                        return;
                    }

                    showAlert(Alert.AlertType.INFORMATION, "Major Found",
                            "Name: " + m.getMajorName() + "\n" +
                                    "Capacity: " + m.getCapacity() + "\n" +
                                    "Acceptance Grade: " + m.getAcceptanceGrade() + "\n" +
                                    "Tawjihi Weight: " + m.getTawjihiWeight() + "\n" +
                                    "Placement Weight: " + m.getPlacementWeight());
                }
            });
        });

        majorPane.setBottom(majorButtons);
        tab.setContent(majorPane);
    }

    public Tab getTab() {
        return tab;
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
