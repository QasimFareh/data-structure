import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class StudentsTab {

    private final Tab tab;
    private final TableView<Student> studentTable;
    private final ObservableList<Student> students;
    private final ObservableList<Major> majors;
    private final AdmissionSystem system;
    private final Stage primaryStage;

    public StudentsTab(AdmissionSystem system, ObservableList<Student> students, ObservableList<Major> majors, Stage primaryStage) {
        this.system = system;
        this.students = students;
        this.majors = majors;
        this.primaryStage = primaryStage;

        tab = new Tab("Students");

        // student table view

        studentTable = new TableView<>();

        TableColumn<Student, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Student, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));

        TableColumn<Student, String> chosenMajorCol = new TableColumn<>("Chosen Major");
        chosenMajorCol.setCellValueFactory(new PropertyValueFactory<>("chosenMajor"));

        TableColumn<Student, Double> tawjihiGradeCol = new TableColumn<>("Tawjihi Grade");
        tawjihiGradeCol.setCellValueFactory(new PropertyValueFactory<>("tawjihiGrade"));

        TableColumn<Student, Double> placementGradeCol = new TableColumn<>("Placement Grade");
        placementGradeCol.setCellValueFactory(new PropertyValueFactory<>("placementGrade"));

        TableColumn<Student, Double> admissionCol = new TableColumn<>("Admission Mark");
        admissionCol.setCellValueFactory(new PropertyValueFactory<>("admissionMark"));

        TableColumn<Student, Boolean> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("accepted"));

        TableColumn<Student, String> rejectionReasonCol = new TableColumn<>("Rejection Reason");
        rejectionReasonCol.setCellValueFactory(new PropertyValueFactory<>("rejectionReason"));

        studentTable.getColumns().addAll(idCol, nameCol, chosenMajorCol, tawjihiGradeCol, placementGradeCol, admissionCol, statusCol ,  rejectionReasonCol);
        studentTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); // مشان عرض الكولومز

        studentTable.setItems(students);

        BorderPane studentPane = new BorderPane();

        studentPane.setCenter(studentTable);

        // الأزرار من تحت
        HBox buttons = new HBox(10);
        Button addBtn = new Button("Add");
        Button deleteBtn = new Button("Delete");
        Button updateBtn = new Button("Update");
        Button searchBtn = new Button("Search");
        Button loadBtn = new Button("Load File");
        buttons.getChildren().addAll(addBtn, deleteBtn, updateBtn, searchBtn, loadBtn);

        // تعريف الكبسات

        // load
        loadBtn.setOnAction(e -> {
            FileChooser fc = new FileChooser();

            fc.setTitle("Select Criteria File");
            File criteriaFile = fc.showOpenDialog(primaryStage);
            if (criteriaFile != null) {
                try {
                    system.loadCriteria(criteriaFile.getPath());
                } catch (Exception ex) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to load criteria file!");
                    return;
                }
            }

            fc.setTitle("Select Students File");
            File studentsFile = fc.showOpenDialog(primaryStage);
            if (studentsFile != null) {
                try {
                    system.loadStudents(studentsFile.getPath());

                    students.clear();
                    majors.clear();

                    Node<Student> curr = system.getAllStudentsHead().getNext();
                    while (curr != system.getAllStudentsHead()) {
                        students.add(curr.getData());
                        curr = curr.getNext();
                    }

                    Node<Major> currM = system.getMajorsHead().getNext();
                    while (currM != system.getMajorsHead()) {
                        majors.add(currM.getData());
                        currM = currM.getNext();
                    }
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Files loaded successfully!");
                } catch (Exception ex) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to load students file!");
                }
            }
        });
        // add
        addBtn.setOnAction(e -> {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Add Student");
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            TextField idField = new TextField();
            idField.setPromptText("ID");

            TextField firstNameField = new TextField();
            firstNameField.setPromptText("First Name");

            TextField familyNameField = new TextField();
            familyNameField.setPromptText("Family Name");

            TextField tawjihiField = new TextField();
            tawjihiField.setPromptText("Tawjihi Grade");

            TextField placementField = new TextField();
            placementField.setPromptText("Placement Grade");

            ComboBox<String> majorCombo = new ComboBox<>();
            Node<Major> curr = system.getMajorsHead().getNext();
            while (curr != system.getMajorsHead()) {
                majorCombo.getItems().add(curr.getData().getMajorName());
                curr = curr.getNext();
            }

            VBox layout = new VBox(10);
            layout.getChildren().addAll(idField, firstNameField, familyNameField, tawjihiField, placementField, majorCombo);
            dialog.getDialogPane().setContent(layout);

            dialog.showAndWait().ifPresent(result -> {
                if (result == ButtonType.OK) {

                    if (idField.getText().isEmpty() || firstNameField.getText().isEmpty() ||
                            familyNameField.getText().isEmpty() || tawjihiField.getText().isEmpty() ||
                            placementField.getText().isEmpty() || majorCombo.getValue() == null) {
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

                    double tawjihi, placement;
                    try {
                        tawjihi = Double.parseDouble(tawjihiField.getText());
                        placement = Double.parseDouble(placementField.getText());
                        if (tawjihi < 50 || tawjihi > 100) {
                            showAlert(Alert.AlertType.ERROR, "Error", "Tawjihi grade must be between 50 and 100!");
                            return;
                        }
                        if (placement < 50 || placement > 100) {
                            showAlert(Alert.AlertType.ERROR, "Error", "Placement grade must be between 50 and 100!");
                            return;
                        }
                    } catch (NumberFormatException ex) {
                        showAlert(Alert.AlertType.ERROR, "Error", "Grades must be numbers!");
                        return;
                    }

                    if (system.findSt(id) != null) {
                        showAlert(Alert.AlertType.ERROR, "Error", "Student ID already exists!");
                        return;
                    }

                    system.insertStudent(id, firstNameField.getText(), familyNameField.getText(), tawjihi, placement, majorCombo.getValue());
                    students.add(system.findSt(id));
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Student added successfully!");
                }
            });
        });
        // delete
        deleteBtn.setOnAction(e -> {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("delete Student");
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            TextField idField = new TextField();
            idField.setPromptText("ID");

            dialog.getDialogPane().setContent(idField);

            dialog.showAndWait().ifPresent(result -> {
                if (result == ButtonType.OK) {

                    // اذا فاضي
                    if (idField.getText().isEmpty()) {
                        showAlert(Alert.AlertType.ERROR, "Error", "ID field is required!");
                        return;
                    }

                    // سالب وحرف
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

                    // تحقق اذا الطالب موجود
                    Student deleted = system.deleteSt(id);
                    if (deleted == null) {
                        showAlert(Alert.AlertType.ERROR, "Error", "Student not found!");
                        return;
                    }

                    students.remove(deleted);
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Student deleted successfully!");
                }
            });

        });
        // update
        updateBtn.setOnAction(e -> {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Update Student");
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            TextField idField = new TextField();
            idField.setPromptText("ID");

            TextField firstNameField = new TextField();
            firstNameField.setPromptText("First Name");

            TextField familyNameField = new TextField();
            familyNameField.setPromptText("Family Name");

            TextField tawjihiField = new TextField();
            tawjihiField.setPromptText("Tawjihi Grade");

            TextField placementField = new TextField();
            placementField.setPromptText("Placement Grade");

            VBox layout = new VBox(10);
            layout.getChildren().addAll(idField, firstNameField, familyNameField, tawjihiField, placementField);
            dialog.getDialogPane().setContent(layout);

            dialog.showAndWait().ifPresent(result -> {
                if (result == ButtonType.OK) {

                    // اذا فاضيات
                    if (idField.getText().isEmpty() || firstNameField.getText().isEmpty() ||
                            familyNameField.getText().isEmpty() || tawjihiField.getText().isEmpty() ||
                            placementField.getText().isEmpty()) {
                        showAlert(Alert.AlertType.ERROR, "Error", "All fields are required!");
                        return;
                    }

                    // الاي دي
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

                    // تحقق إذا الطالب موجود
                    if (system.findSt(id) == null) {
                        showAlert(Alert.AlertType.ERROR, "Error", "Student not found!");
                        return;
                    }

                    // تحقق من الاسم
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

                    // تحقق من المعدلات
                    double tawjihi, placement;
                    try {
                        tawjihi = Double.parseDouble(tawjihiField.getText());
                        placement = Double.parseDouble(placementField.getText());
                        if (tawjihi < 50 || tawjihi > 100) {
                            showAlert(Alert.AlertType.ERROR, "Error", "Tawjihi grade must be between 50 and 100!");
                            return;
                        }
                        if (placement < 50 || placement > 100) {
                            showAlert(Alert.AlertType.ERROR, "Error", "Placement grade must be between 50 and 100!");
                            return;
                        }
                    } catch (NumberFormatException ex) {
                        showAlert(Alert.AlertType.ERROR, "Error", "Grades must be numbers!");
                        return;
                    }

                    system.updateStudent(id, firstNameField.getText(), familyNameField.getText(), tawjihi, placement);
                    // تحديث الجدول
                    studentTable.refresh();
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Student updated successfully!");
                }
            });
        });
        // search
        searchBtn.setOnAction(e -> {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Search for a Student");
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            TextField idField = new TextField();
            idField.setPromptText("ID");

            dialog.getDialogPane().setContent(idField);
            dialog.showAndWait().ifPresent(result -> {
                if (result == ButtonType.OK) {
                    if (idField.getText().isEmpty()) {
                        showAlert(Alert.AlertType.ERROR, "Error", "ID field is required!");
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

                    Student s = system.findSt(id);
                    if (s == null) {
                        showAlert(Alert.AlertType.ERROR, "Error", "Student not found!");
                        return;
                    }

                    showAlert(Alert.AlertType.INFORMATION, "Student Found",
                            "ID: " + s.getId() + "\n" +
                                    "Name: " + s.getFullName() + "\n" +
                                    "Major: " + s.getChosenMajor() + "\n" +
                                    "Tawjihi: " + s.getTawjihiGrade() + "\n" +
                                    "Placement: " + s.getPlacementGrade() + "\n" +
                                    "Admission Mark: " + s.getAdmissionMark() + "\n" +
                                    "Status: " + (s.isAccepted() ? "Accepted" : "Rejected"));
                }
            });
        });

        studentPane.setBottom(buttons);
        tab.setContent(studentPane);
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
