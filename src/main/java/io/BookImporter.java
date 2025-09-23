package io;

import model.Book;
import db.Database;

import java.sql.*;
import java.util.List;

public class BookImporter {

    public static void saveBooks(List<Book> books) {
        String updateSql = "UPDATE books SET isbn=?, title=?, author=?, publication_year=? WHERE id=?";
        String insertSql = "INSERT INTO books (id, isbn, title, author, publication_year) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement update = conn.prepareStatement(updateSql);
             PreparedStatement insert = conn.prepareStatement(insertSql)) {

            conn.setAutoCommit(false);

            for (Book b : books) {
                // UPDATE versuchen
                update.setString(1, b.getIsbn());
                update.setString(2, b.getTitle());
                update.setString(3, b.getAuthor());
                update.setInt(4, b.getYear());
                update.setInt(5, b.getId());
                int updated = update.executeUpdate();

                if (updated == 0) {
                    // INSERT
                    insert.setInt(1, b.getId());
                    insert.setString(2, b.getIsbn());
                    insert.setString(3, b.getTitle());
                    insert.setString(4, b.getAuthor());
                    insert.setInt(5, b.getYear());
                    insert.executeUpdate();
                }
            }

            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
