import java.io.File;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Fx extends Application{

	public static void main(String[] args) {
		
		launch(args);
		
		
	}
	
	public void start(Stage primaryStage) throws Exception {

		BorderPane p = new BorderPane();
		TableView<Book> BookTV = new TableView<>();
		
		TableColumn<Book, Integer> idC = new TableColumn<>("Id");
		idC.setCellValueFactory(new PropertyValueFactory<>("bookId"));
		TableColumn<Book, String> titleC = new TableColumn<>("Title");
		titleC.setCellValueFactory(new PropertyValueFactory<>("title"));
		TableColumn<Book, String> authorC = new TableColumn<>("Author");
		authorC.setCellValueFactory(new PropertyValueFactory<>("author"));
		TableColumn<Book, String> categoryC = new TableColumn<>("Category");
		categoryC.setCellValueFactory(new PropertyValueFactory<>("category"));
		TableColumn<Book, Integer> publishedYearC = new TableColumn<>("Published Year");
		publishedYearC.setCellValueFactory(new PropertyValueFactory<>("publishedYear"));
		TableColumn<Book, String> isbnC = new TableColumn<>("Isbn");
		isbnC.setCellValueFactory(new PropertyValueFactory<>("isbn"));
		
		idC.setPrefWidth(150);
		authorC.setPrefWidth(150);
		titleC.setPrefWidth(150);
		isbnC.setPrefWidth(150);
		categoryC.setPrefWidth(150);
		publishedYearC.setPrefWidth(150);
		
		BookTV.getColumns().addAll(idC,titleC,authorC,categoryC,publishedYearC,isbnC);
		p.setCenter(BookTV);
		
		
		
		FileChooser myFile = new FileChooser();
		File selectedFile = myFile.showOpenDialog(primaryStage);
		FileHandler f = new FileHandler();
		ArrayList<Book> a = f.fileReader(selectedFile);
		ObservableList<Book> o = FXCollections.observableArrayList(a);
		BookTV.setItems(o);
		
		Scene s = new Scene(p , 900 , 700);
		primaryStage.setScene(s);
		primaryStage.show();
		
		
	}

}
