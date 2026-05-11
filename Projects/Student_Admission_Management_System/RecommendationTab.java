import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RecommendationTab {

    private final Tab tab;
    private final AdmissionSystem system;
    private final ObservableList<Student> students;

    public RecommendationTab(AdmissionSystem system, ObservableList<Student> students) {
        this.system = system;
        this.students = students;

        tab = new Tab("Recommendation");

        HBox recommendationPane = new HBox(20);
        recommendationPane.setPadding(new Insets(20));

        VBox recommendationTextFieldsAndButtons = new VBox(15);
        recommendationTextFieldsAndButtons.setPrefWidth(250);

        ListView<String> majorsListView = new ListView<>();
        majorsListView.setPrefWidth(400);

        TextField tawjihiGradeTF = new TextField();
        tawjihiGradeTF.setPromptText("Tawjihi Grade");
        tawjihiGradeTF.setPrefWidth(200);

        TextField placementGradeTF = new TextField();
        placementGradeTF.setPromptText("Placement Grade");
        placementGradeTF.setPrefWidth(200);

        ComboBox<String> majorComboBox = new ComboBox<>();
        majorComboBox.setPromptText("Select Major (Optional)");
        majorComboBox.setPrefWidth(200);

        // انو اول ما افوت عالبين تتحدث الكومبو بوكس لحالها
        tab.setOnSelectionChanged(e -> {
            if (tab.isSelected()) {
                majorComboBox.getItems().clear();
                Node<Major> curr2 = system.getMajorsHead().getNext();
                while (curr2 != system.getMajorsHead()) {
                    majorComboBox.getItems().add(curr2.getData().getMajorName());
                    curr2 = curr2.getNext();
                }
            }
        });

        HBox recommendationButtons = new HBox(10);
        Button findButton = new Button("Find");
        Button clearButton = new Button("Clear");
        Button registerButton = new Button("Register");
        registerButton.setVisible(false);
        recommendationButtons.getChildren().addAll(findButton, clearButton, registerButton);

        recommendationTextFieldsAndButtons.getChildren().addAll(
                new Label("Tawjihi Grade:"), tawjihiGradeTF,
                new Label("Placement Grade:"), placementGradeTF,
                new Label("Preferred Major:"), majorComboBox,
                recommendationButtons
        );

        // find button برمجة ال
        findButton.setOnAction(e -> {
            if (tawjihiGradeTF.getText().isEmpty() || placementGradeTF.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please enter Tawjihi and Placement grades!");
                return;
            }

            double tawjihi, placement;
            try {
                tawjihi = Double.parseDouble(tawjihiGradeTF.getText());
                placement = Double.parseDouble(placementGradeTF.getText());
                if (tawjihi < 50 || tawjihi > 100 || placement < 50 || placement > 100) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Grades must be between 50 and 100!");
                    return;
                }
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Grades must be numbers!");
                return;
            }

            String chosenMajor = majorComboBox.getValue();
            DoubleLinkedList<Major> recommended = system.SelectionAndRecommendation(tawjihi, placement, chosenMajor);

            majorsListView.getItems().clear();
            Node<Major> curr2 = recommended.getHead().getNext();
            while (curr2 != recommended.getHead()) {
                majorsListView.getItems().add(curr2.getData().getMajorName());
                curr2 = curr2.getNext();
            }

            if (majorsListView.getItems().isEmpty()) {
                showAlert(Alert.AlertType.INFORMATION, "Result", "No majors found for your grades!");
                registerButton.setVisible(false);
            } else {
                registerButton.setVisible(true);
            }
        });
        // clear button برمجة ال
        clearButton.setOnAction(e -> {
            tawjihiGradeTF.clear();
            placementGradeTF.clear();
            majorComboBox.setValue(null);
            majorsListView.getItems().clear();
            registerButton.setVisible(false);
        });
        // register button برمجة ال
        registerButton.setOnAction(e -> {
            String selectedMajor = majorsListView.getSelectionModel().getSelectedItem();
            if (selectedMajor == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please select a major from the list!");
                return;
            }

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Register Student");
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            TextField idField = new TextField();
            idField.setPromptText("ID");

            TextField firstNameField = new TextField();
            firstNameField.setPromptText("First Name");

            TextField familyNameField = new TextField();
            familyNameField.setPromptText("Family Name");

            VBox layout = new VBox(10);
            layout.getChildren().addAll(idField, firstNameField, familyNameField);
            dialog.getDialogPane().setContent(layout);

            dialog.showAndWait().ifPresent(result -> {
                if (result == ButtonType.OK) {
                    if (idField.getText().isEmpty() || firstNameField.getText().isEmpty() || familyNameField.getText().isEmpty()) {
                        showAlert(Alert.AlertType.ERROR, "Error", "All fields are required!");
                        return;
                    }

                    int id;
                    try {
                        id = Integer.parseInt(idField.getText());
                        if (id <= 0) {
                            showAlert(Alert.AlertType.ERROR, "Error", "ID must be a positive number!");
                            return;
                        }
                    } catch (NumberFormatException ex) {
                        showAlert(Alert.AlertType.ERROR, "Error", "ID must be a number!");
                        return;
                    }

                    if (system.findSt(id) != null) {
                        showAlert(Alert.AlertType.ERROR, "Error", "Student ID already exists!");
                        return;
                    }

                    boolean validName = true;
                    for (char c : firstNameField.getText().toCharArray()) {
                        if (Character.isDigit(c)) {
                            validName = false;
                            break;
                        }
                    }
                    for (char c : familyNameField.getText().toCharArray()) {
                        if (Character.isDigit(c)) {
                            validName = false;
                            break;
                        }
                    }
                    if (!validName) {
                        showAlert(Alert.AlertType.ERROR, "Error", "Name must contain letters only!");
                        return;
                    }

                    double tawjihi = Double.parseDouble(tawjihiGradeTF.getText());
                    double placement = Double.parseDouble(placementGradeTF.getText());

                    system.insertStudent(id, firstNameField.getText(), familyNameField.getText(), tawjihi, placement, selectedMajor);
                    students.add(system.findSt(id));
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Student registered successfully!");
                    registerButton.setVisible(false);
                }
            });
        });

        recommendationPane.getChildren().addAll(recommendationTextFieldsAndButtons, majorsListView);
        tab.setContent(recommendationPane);
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
