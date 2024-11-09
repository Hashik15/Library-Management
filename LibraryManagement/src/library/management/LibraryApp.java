package library.management;

import java.sql.SQLException;
import java.util.Scanner;

public class LibraryApp {
    public static void main(String[] args) {
        DatabaseManagement dbManagement = new DatabaseManagement();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Choose an operation:");
            System.out.println("1. Add Book");
            System.out.println("2. Update Book");
            System.out.println("3. Delete Book");
            System.out.println("4. Get Book");
            System.out.println("5. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            try {
                switch (choice) {
                    case 1: // Add Book
                        System.out.println("Enter book details to add:");
                        System.out.print("ISBN (13 digits): ");
                        String isbnAdd = scanner.nextLine();
                        System.out.print("Title: ");
                        String titleAdd = scanner.nextLine();
                        System.out.print("Author: ");
                        String authorAdd = scanner.nextLine();
                        System.out.print("Price: ");
                        double priceAdd = scanner.nextDouble();

                        dbManagement.addBook(isbnAdd, titleAdd, authorAdd, priceAdd);
                        break;

                    case 2: // Update Book
                        System.out.println("Enter book details to update:");
                        System.out.print("ISBN (13 digits): ");
                        String isbnUpdate = scanner.nextLine();
                        System.out.print("New Title: ");
                        String titleUpdate = scanner.nextLine();
                        System.out.print("New Author: ");
                        String authorUpdate = scanner.nextLine();
                        System.out.print("New Price: ");
                        double priceUpdate = scanner.nextDouble();

                        dbManagement.updateBook(isbnUpdate, titleUpdate, authorUpdate, priceUpdate);
                        break;

                    case 3: // Delete Book
                        System.out.print("Enter ISBN to delete: ");
                        String isbnDelete = scanner.nextLine();
                        dbManagement.deleteBook(isbnDelete);
                        break;

                    case 4: // Get Book
                        System.out.print("Enter ISBN to get details: ");
                        String isbnGet = scanner.nextLine();
                        dbManagement.getBook(isbnGet);
                        break;

                    case 5: // Exit
                        System.out.println("Exiting...");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (BookInvalidException | BookNotFoundException e) {
                System.out.println("Custom Error: " + e.getMessage());
            } catch (SQLException e) {
                System.out.println("Database Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected Error: " + e.getMessage());
            }
        }
    }
}
