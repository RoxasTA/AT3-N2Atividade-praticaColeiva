import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

    private static final String HOST = "localhost";
    private static final int PORT = 1337;

    private static final String OPTION_LIST_BOOKS = "1";
    private static final String OPTION_RENT_BOOK = "2";
    private static final String OPTION_RETURN_BOOK = "3";
    private static final String OPTION_REGISTER_BOOK = "4";
    private static final String OPTION_EXIT = "5";

    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_RENT = "rent";
    private static final String COMMAND_RETURN = "return";
    private static final String COMMAND_REGISTER = "register";
    private static final String COMMAND_EXIT = "exit";

    // Main
    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PORT);
             BufferedReader serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter serverWriter = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            String userOption;
            do {
                displayMenu();
                userOption = scanner.nextLine().trim();

                switch (userOption) {
                    case OPTION_LIST_BOOKS:
                        listBooks(serverWriter, serverReader);
                        break;
                    case OPTION_RENT_BOOK:
                        rentBook(serverWriter, serverReader, scanner);
                        break;
                    case OPTION_RETURN_BOOK:
                        returnBook(serverWriter, serverReader, scanner);
                        break;
                    case OPTION_REGISTER_BOOK:
                        registerBook(serverWriter, serverReader, scanner);
                        break;
                    case OPTION_EXIT:
                        serverWriter.println(COMMAND_EXIT);
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid option.");
                }
            } while (!userOption.equals(OPTION_EXIT));

        } catch (IOException e) {
            System.err.println("I/O Error: " + e.getMessage());
        }
    }

    // Method to display the menu
    private static void displayMenu() {
        System.out.println("Library:");
        System.out.println("1. List books");
        System.out.println("2. Rent book");
        System.out.println("3. Return book");
        System.out.println("4. Register book");
        System.out.println("5. Exit");
        System.out.print("Choose an option: ");
    }

    private static void listBooks(PrintWriter serverWriter, BufferedReader serverReader) throws IOException {
        serverWriter.println(COMMAND_LIST);
        String serverResponse;
        while ((serverResponse = serverReader.readLine()) != null) {
            if (serverResponse.isEmpty()) break;
            System.out.println(serverResponse);
        }
    }

    private static void rentBook(PrintWriter serverWriter, BufferedReader serverReader, Scanner scanner) throws IOException {
        System.out.print("Book name: ");
        String bookName = scanner.nextLine().trim();
        if (bookName.isEmpty()) {
            System.out.println("Book name cannot be empty.");
            return;
        }
        serverWriter.println(COMMAND_RENT + "#" + bookName);
        String serverResponse = serverReader.readLine();
        System.out.println(serverResponse);
    }

    private static void returnBook(PrintWriter serverWriter, BufferedReader serverReader, Scanner scanner) throws IOException {
        System.out.print("Book name: ");
        String bookName = scanner.nextLine().trim();
        if (bookName.isEmpty()) {
            System.out.println("Book name cannot be empty.");
            return;
        }
        serverWriter.println(COMMAND_RETURN + "#" + bookName);
        String serverResponse = serverReader.readLine();
        System.out.println(serverResponse);
    }

    private static void registerBook(PrintWriter serverWriter, BufferedReader serverReader, Scanner scanner) throws IOException {
        System.out.print("Author: ");
        String bookAuthor = scanner.nextLine().trim();
        if (bookAuthor.isEmpty()) {
            System.out.println("Author cannot be empty.");
            return;
        }

        System.out.print("Name: ");
        String bookName = scanner.nextLine().trim();
        if (bookName.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        System.out.print("Genre: ");
        String bookGenre = scanner.nextLine().trim();
        if (bookGenre.isEmpty()) {
            System.out.println("Genre cannot be empty.");
            return;
        }

        System.out.print("Number of copies: ");
        int numberOfCopies;
        try {
            numberOfCopies = Integer.parseInt(scanner.nextLine().trim());
            if (numberOfCopies < 0) {
                System.out.println("Number of copies cannot be negative.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number of copies.");
            return;
        }

        String bookInfo = bookAuthor + "," + bookName + "," + bookGenre + "," + numberOfCopies;
        serverWriter.println(COMMAND_REGISTER + "#" + bookInfo);
        String serverResponse = serverReader.readLine();
        System.out.println(serverResponse);
    }
}

