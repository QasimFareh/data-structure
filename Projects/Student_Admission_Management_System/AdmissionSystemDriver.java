import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AdmissionSystemDriver extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        AdmissionSystem system = new AdmissionSystem();

        BorderPane bp = new BorderPane();
        TabPane tabPane = new TabPane();

        ObservableList<Student> students = FXCollections.observableArrayList();
        ObservableList<Major> majors = FXCollections.observableArrayList();

        // عمل التابات الي بنتنقل بينهم
        StudentsTab studentsTab = new StudentsTab(system, students, majors, primaryStage);
        MajorsTab majorsTab = new MajorsTab(system, majors, students, primaryStage);
        RecommendationTab recommendationTab = new RecommendationTab(system, students);
        StatisticsTab statisticsTab = new StatisticsTab(system);
        ReportsTab reportsTab = new ReportsTab(system, primaryStage);


        bp.setCenter(tabPane);

        tabPane.getTabs().addAll(
                studentsTab.getTab(),
                majorsTab.getTab(),
                recommendationTab.getTab(),
                statisticsTab.getTab(),
                reportsTab.getTab()
        );
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE); // مشان ما اقدر اسكر التابات

        Scene s = new Scene(bp, 900, 600);
        primaryStage.setScene(s);
        primaryStage.setTitle("Admission System");
        primaryStage.show();
    }
}
