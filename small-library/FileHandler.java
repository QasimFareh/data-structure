package project_1;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandler {

	// ── Read ────────────────────────────────────────────────────────────────────
	public ArrayList<Book> fileReader(File file) {
		ArrayList<Book> bookArrayList = new ArrayList<>();
		try (Scanner s = new Scanner(file)) {
			while (s.hasNextLine()) {
				String line = s.nextLine().trim();
				if (line.isEmpty() || line.toLowerCase().startsWith("bookid")) continue;

				String[] splits = line.split(",");
				if (splits.length < 6) continue;

				int    bookId        = Integer.parseInt(splits[0].trim());
				String title         = splits[1].trim();
				String author        = splits[2].trim();
				String category      = splits[3].trim();
				int    publishedYear = Integer.parseInt(splits[4].trim());
				String isbn          = splits[5].trim();

				bookArrayList.add(new Book(bookId, title, author, category, publishedYear, isbn));
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found: " + e.getMessage());
		} catch (NumberFormatException e) {
			System.out.println("Bad data in file: " + e.getMessage());
		}
		return bookArrayList;
	}

	// ── Write ───────────────────────────────────────────────────────────────────
	public void fileWriter(File file, ArrayList<Book> books) {
		try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
			pw.println("BookID, Title, Author, Category, Published Year, ISBN");
			for (Book b : books) {
				pw.println(b.toString());
			}
		} catch (IOException e) {
			System.out.println("Error writing file: " + e.getMessage());
		}
	}
}