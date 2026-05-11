import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class ReportsTab {

    private final Tab tab;
    private final AdmissionSystem system;
    private final Stage primaryStage;

    public ReportsTab(AdmissionSystem system, Stage primaryStage) {
        this.system = system;
        this.primaryStage = primaryStage;

        tab = new Tab("Reports");

        VBox reportVBox = new VBox(10);
        reportVBox.setPadding(new Insets(20));

        Label reportLabel = new Label("Generate Report");

        // HBox للستين
        HBox listsHBox = new HBox(20);

        // يسار - Students
        VBox studentsListVBox = new VBox(10);
        ListView<String> studentsListView = new ListView<>();
        studentsListView.setPrefHeight(150);
        CheckBox showAccepted = new CheckBox("Show Accepted");
        CheckBox showRejected = new CheckBox("Show Rejected");
        studentsListVBox.getChildren().addAll(new Label("Students:"), studentsListView, showAccepted, showRejected);

        // يمين - Majors
        VBox majorsListVBox = new VBox(10);
        ListView<String> majorsReportListView = new ListView<>();
        majorsReportListView.setPrefHeight(150);
        CheckBox fullMajors = new CheckBox("Full Majors");
        CheckBox availableMajors = new CheckBox("Available Majors");
        majorsListVBox.getChildren().addAll(new Label("Majors:"), majorsReportListView, fullMajors, availableMajors);

        listsHBox.getChildren().addAll(studentsListVBox, majorsListVBox);

        // ازرار
        HBox reportHBox = new HBox(10);
        Button reportGBtn = new Button("Generate");
        Button reportSBtn = new Button("Save to File");
        reportHBox.getChildren().addAll(reportGBtn, reportSBtn);

        // التقرير
        TextArea reportTextArea = new TextArea();
        reportTextArea.setEditable(false);
        reportTextArea.setPrefHeight(200);

        reportVBox.getChildren().addAll(reportLabel, listsHBox, reportHBox, reportTextArea);
        tab.setContent(reportVBox);

        // تحديث الليستات لما تدخل التاب
        tab.setOnSelectionChanged(e -> {
            if (tab.isSelected()) {
                studentsListView.getItems().clear();
                Node<Student> currS = system.getAllStudentsHead().getNext();
                while (currS != system.getAllStudentsHead()) {
                    studentsListView.getItems().add(currS.getData().getFullName());
                    currS = currS.getNext();
                }
                majorsReportListView.getItems().clear();
                Node<Major> currM = system.getMajorsHead().getNext();
                while (currM != system.getMajorsHead()) {
                    majorsReportListView.getItems().add(currM.getData().getMajorName());
                    currM = currM.getNext();
                }
            }
        });

        // منطق الشك بوكسات للطلاب
        Runnable updateStudentList = () -> {
            studentsListView.getItems().clear();
            if (!showAccepted.isSelected() && !showRejected.isSelected()) {
                // ما في شي محدد - عرض الكل
                Node<Student> curr2 = system.getAllStudentsHead().getNext();
                while (curr2 != system.getAllStudentsHead()) {
                    studentsListView.getItems().add(curr2.getData().getFullName());
                    curr2 = curr2.getNext();
                }
            } else if (showAccepted.isSelected() && showRejected.isSelected()) {
                // الاثنين محددين - ما تعرض شي
            } else {
                // واحد بس محدد
                Node<Student> curr2 = system.getAllStudentsHead().getNext();
                while (curr2 != system.getAllStudentsHead()) {
                    if (showAccepted.isSelected() && curr2.getData().isAccepted())
                        studentsListView.getItems().add(curr2.getData().getFullName());
                    if (showRejected.isSelected() && !curr2.getData().isAccepted())
                        studentsListView.getItems().add(curr2.getData().getFullName());
                    curr2 = curr2.getNext();
                }
            }
        };

        showAccepted.setOnAction(e -> updateStudentList.run());
        showRejected.setOnAction(e -> updateStudentList.run());

        // منطق الشك بوكسات للتخصصات
        Runnable updateMajorList = () -> {
            majorsReportListView.getItems().clear();
            if (!fullMajors.isSelected() && !availableMajors.isSelected()) {
                Node<Major> curr2 = system.getMajorsHead().getNext();
                while (curr2 != system.getMajorsHead()) {
                    majorsReportListView.getItems().add(curr2.getData().getMajorName());
                    curr2 = curr2.getNext();
                }
            } else if (fullMajors.isSelected() && availableMajors.isSelected()) {
                // الاثنين محددين - ما تعرض شي
            } else {
                Node<Major> curr2 = system.getMajorsHead().getNext();
                while (curr2 != system.getMajorsHead()) {
                    if (fullMajors.isSelected() && curr2.getData().getCapacity() == 0)
                        majorsReportListView.getItems().add(curr2.getData().getMajorName());
                    if (availableMajors.isSelected() && curr2.getData().getCapacity() > 0)
                        majorsReportListView.getItems().add(curr2.getData().getMajorName());
                    curr2 = curr2.getNext();
                }
            }
        };

        fullMajors.setOnAction(e -> updateMajorList.run());
        availableMajors.setOnAction(e -> updateMajorList.run());

        // Generate button
        reportGBtn.setOnAction(e -> {
            StringBuilder report = new StringBuilder();
            report.append("========================================\n");
            report.append("   Student Admission Management System  \n");
            report.append("========================================\n");
            report.append("Date: ").append(java.time.LocalDateTime.now().format(
                    java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n");
            report.append("========================================\n\n");

            report.append("Students:\n");
            for (String st : studentsListView.getItems()) {
                report.append("  - ").append(st).append("\n");
            }

            report.append("\nMajors:\n");
            for (String m : majorsReportListView.getItems()) {
                report.append("  - ").append(m).append("\n");
            }

            report.append("\n========================================\n");
            report.append("   Thank you for using our system!      \n");
            report.append("========================================\n");

            reportTextArea.setText(report.toString());
        });

        // Save button
        reportSBtn.setOnAction(e -> {
            if (reportTextArea.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please generate the report first!");
                return;
            }
            FileChooser fc = new FileChooser();
            fc.setTitle("Save Report");
            File file = fc.showSaveDialog(primaryStage);
            if (file != null) {
                try (java.io.PrintWriter pw = new java.io.PrintWriter(file)) {
                    pw.print(reportTextArea.getText());
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Report saved successfully!");
                } catch (Exception ex) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to save report!");
                }
            }
        });
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
