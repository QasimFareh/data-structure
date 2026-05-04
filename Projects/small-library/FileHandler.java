import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandler {
	
	
	public ArrayList<Book> fileReader(File fofo) {
		ArrayList<Book> aoao = new ArrayList<Book>();
		try (Scanner s = new Scanner(fofo)) {
			while (s.hasNextLine()) {
			    String line = s.nextLine();
			    String[] splits = line.split("/");

			    int bookId = Integer.parseInt(splits[0]);
			    String title = splits[1];
			    String author = splits[2];
			    String category = splits[3];
			    int publishedYear = Integer.parseInt(splits[4]);
			    String isbn = splits[5];

			    Book b = new Book(bookId, title, author, category, publishedYear, isbn);
			    aoao.add(b);
			}
		} catch (FileNotFoundException e) {
			System.out.println("allah a3lm sho el8lt !");;
		}

		return aoao;
	}
	
	

}
