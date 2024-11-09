package library.management;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class BookInvalidException extends Exception {
    public BookInvalidException(String message) {
        super(message);
    }
}

class BookNotFoundException extends Exception {
    public BookNotFoundException(String message) {
        super(message);
    }
}

public class DatabaseManagement {

    private static final String URL = "jdbc:mysql://localhost:3306/BookDetails";
    private static final String USER = "root";
    private static final String PASSWORD = "root"; 

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void addBook(String isbn, String title, String author, double price) throws BookInvalidException, SQLException {
        // Validate ISBN length
        if (isbn.length() != 13) {
            throw new BookInvalidException("ISBN must be exactly 13 characters.");
        }

        String sql = "INSERT INTO Books (isbn, title, author, price) VALUES (?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Set the values for each placeholder in the SQL statement
            preparedStatement.setString(1, isbn);
            preparedStatement.setString(2, title);
            preparedStatement.setString(3, author);
            preparedStatement.setDouble(4, price);

            // Execute the insert
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new book was added successfully!");
            }
        }
    }

    public void updateBook(String isbn, String title, String author, double price) throws SQLException, BookNotFoundException {
        String sql = "UPDATE Books SET title = ?, author = ?, price = ? WHERE isbn = ?";
        
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
             
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, author);
            preparedStatement.setDouble(3, price);
            preparedStatement.setString(4, isbn);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new BookNotFoundException("No book found with the given ISBN: " + isbn);
            }
        }
    }

    public void deleteBook(String isbn) throws SQLException, BookNotFoundException {
        String sql = "DELETE FROM Books WHERE isbn = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, isbn);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new BookNotFoundException("No book found with the given ISBN: " + isbn);
            } else {
                System.out.println("A book was deleted successfully!");
            }
        }
    }

    public void getBook(String isbn) throws SQLException, BookNotFoundException {
        String sql = "SELECT title, author, price FROM Books WHERE isbn = ?";
        
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
             
            preparedStatement.setString(1, isbn);
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Book found; retrieve and print details
                    String title = resultSet.getString("title");
                    String author = resultSet.getString("author");
                    double price = resultSet.getDouble("price");
                    System.out.println("Book Details:");
                    System.out.println("ISBN: " + isbn);
                    System.out.println("Title: " + title);
                    System.out.println("Author: " + author);
                    System.out.println("Price: " + price);
                } else {
                    throw new BookNotFoundException("No book found with the given ISBN: " + isbn);
                }
            }
        }
    }
}

