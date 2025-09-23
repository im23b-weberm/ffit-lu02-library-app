package ch.bzz;

import io.BookImporter;
import io.TsvReader;
import model.Book;

import java.sql.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class LibraryAppMain {

    /**
     * Reads all books from the database.
     */
    public static List<Book> getBooksFromDB() {
        String url = Config.get("DB_URL");
        String user = Config.get("DB_USER");
        String password = Config.get("DB_PASSWORD");

        String sql = "SELECT id, isbn, title, author, publication_year FROM books;";

        List<Book> books = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String isbn = rs.getString("isbn");
                String title = rs.getString("title");
                String author = rs.getString("author");
                int year = rs.getInt("publication_year");

                books.add(new Book(id, isbn, title, author, year));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    /**
     * Lists all books currently stored in the database.
     */
    public static void listBooks() {
        List<Book> books = getBooksFromDB();
        if (books.isEmpty()) {
            System.out.println("No books found in database.");
        } else {
            for (Book b : books) {
                System.out.println(b);
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanning = new Scanner(System.in);
        System.out.println("LibraryApp started. Type 'help' for commands.");

        while (true) {
            String userinput = scanning.nextLine().trim();
            if (userinput.isEmpty()) continue;

            String[] parts = userinput.split(" ", 2);
            String command = parts[0];

            switch (command) {
                case "quit" -> {
                    System.out.println("Goodbye!");
                    return;
                }
                case "help" -> System.out.println("Commands: quit, help, listBooks, importBooks <FILE_PATH>");
                case "listBooks" -> listBooks();
                case "importBooks" -> {
                    if (parts.length < 2) {
                        System.out.println("Usage: importBooks <FILE_PATH>");
                        continue;
                    }
                    String filePath = parts[1];
                    try {
                        List<String[]> rows = TsvReader.readFile(filePath);
                        List<Book> importBooks = new ArrayList<>();
                        for (String[] cols : rows) {
                            if (cols.length < 5) {
                                System.err.println("Skipping malformed row: " + String.join("\t", cols));
                                continue;
                            }
                            try {
                                int id = Integer.parseInt(cols[0].trim());
                                String isbn = cols[1].trim();
                                String title = cols[2].trim();
                                String author = cols[3].trim();
                                int year = Integer.parseInt(cols[4].trim());
                                importBooks.add(new Book(id, isbn, title, author, year));
                            } catch (NumberFormatException nfe) {
                                System.err.println("Skipping row with invalid number: " + String.join("\t", cols));
                            }
                        }
                        BookImporter.saveBooks(importBooks);
                        System.out.println("Imported " + importBooks.size() + " books from " + filePath);
                        for (Book b : importBooks) {
                            System.out.println(" -> " + b);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                default -> System.out.println("Command not found: " + userinput);
            }
        }
    }
}
