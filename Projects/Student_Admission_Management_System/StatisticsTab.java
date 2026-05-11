import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class StatisticsTab {

    private final Tab tab;
    private final AdmissionSystem system;

    public StatisticsTab(AdmissionSystem system) {
        this.system = system;

        tab = new Tab("Statistics");

        VBox statisticsPane = new VBox(20);
        statisticsPane.setPadding(new Insets(20));

        HBox generalStats = new HBox(30);
        Label totalAcceptedLabel = new Label("Total Accepted: ");
        Label totalRejectedLabel = new Label("Total Rejected: ");
        Label acceptanceRateLabel = new Label("Acceptance Rate: ");
        generalStats.getChildren().addAll(totalAcceptedLabel, totalRejectedLabel, acceptanceRateLabel);

        HBox majorStats = new HBox(15);

        ComboBox<String> majorStatsCombo = new ComboBox<>();
        majorStatsCombo.setPromptText("Select Major");

        Label majorAcceptedLabel = new Label("Accepted: ");
        Label majorRejectedLabel = new Label("Rejected: ");
        Button majorStatsBtn = new Button("Get");

        majorStats.getChildren().addAll(majorStatsCombo, majorStatsBtn, majorAcceptedLabel, majorRejectedLabel);

        HBox topNPane = new HBox(15);
        ComboBox<String> topNCombo = new ComboBox<>();
        topNCombo.setPromptText("Select Major");
        TextField topNField = new TextField();
        topNField.setPromptText("N");
        topNField.setPrefWidth(60);
        Button topNBtn = new Button("Get");
        ListView<String> topNList = new ListView<>();
        topNList.setPrefHeight(200);
        topNPane.getChildren().addAll(topNCombo, topNField, topNBtn);
        statisticsPane.getChildren().addAll(generalStats, majorStats, topNPane, topNList);
        tab.setContent(statisticsPane);

        tab.setOnSelectionChanged(e -> {
            if (tab.isSelected()) {
                totalAcceptedLabel.setText("Total Accepted: " + system.totalAccepted());
                totalRejectedLabel.setText("Total Rejected: " + system.totalRejected());
                acceptanceRateLabel.setText("Acceptance Rate: " + String.format("%.2f", system.acceptanceRate()) + "%");
                majorStatsCombo.getItems().clear();
                topNCombo.getItems().clear();
                Node<Major> curr2 = system.getMajorsHead().getNext();
                while (curr2 != system.getMajorsHead()) {
                    majorStatsCombo.getItems().add(curr2.getData().getMajorName());
                    topNCombo.getItems().add(curr2.getData().getMajorName());
                    curr2 = curr2.getNext();
                }
            }
        });

        majorStatsBtn.setOnAction(e -> {
            if (majorStatsCombo.getValue() == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please select a major!");
                return;
            }
            majorAcceptedLabel.setText("Accepted: " + system.totalAcceptedInChosenMajor(majorStatsCombo.getValue()));
            majorRejectedLabel.setText("Rejected: " + system.totalRejectedInChosenMajor(majorStatsCombo.getValue()));
        });

        topNBtn.setOnAction(e -> {
            if (topNCombo.getValue() == null || topNField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please select a major and enter N!");
                return;
            }
            int n;
            try {
                n = Integer.parseInt(topNField.getText());
                if (n <= 0) {
                    showAlert(Alert.AlertType.ERROR, "Error", "N must be a positive number!");
                    return;
                }
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "N must be a number!");
                return;
            }
            DoubleLinkedList<Student> topN = system.topNStudents(topNCombo.getValue(), n);
            topNList.getItems().clear();
            if (topN == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "Major not found!");
                return;
            }
            Node<Student> curr2 = topN.getHead().getNext();
            while (curr2 != topN.getHead()) {
                topNList.getItems().add(curr2.getData().getFullName() + " - " + curr2.getData().getAdmissionMark());
                curr2 = curr2.getNext();
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
