import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

class LibraryServer {
    private static final String JSON_FILE = "books.json";
    private static List<Book> books;

    public static void main(String[] args) {

        final int PORT = 1223;

        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started successfully!");
            loadBooks();
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket);

                Thread clientThread = new Thread(new ClientHandler(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static synchronized void loadBooks() {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(JSON_FILE);
        if (file.exists()) {
            try {
                JsonNode rootNode = mapper.readTree(file);
                JsonNode booksNode = rootNode.path("books");
                CollectionType listType = mapper.getTypeFactory().constructCollectionType(List.class, Book.class);
                books = mapper.convertValue(booksNode, listType);
            } catch (IOException e) {
                e.printStackTrace();
                books = new ArrayList<>();
            }
        } else {
            System.out.println("No books found, initializing empty list.");
            books = new ArrayList<>();
            saveBooks();
        }
    }

    private static synchronized void saveBooks() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(JSON_FILE), new BooksWrapper(books));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ClientHandler implements Runnable {
        private Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

                String clientRequest;
                while ((clientRequest = input.readLine()) != null) {
                    String response = processInput(clientRequest);
                    output.println(response);
                }

                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private String processInput(String input) {
            String[] tokens = input.split("#");
            String command = tokens[0];

            switch (command) {
                case "list":
                    return listBooks();
                case "rent":
                    return rentBook(tokens[1]);
                case "return":
                    return returnBook(tokens[1]);
                case "register":
                    return registerBook(tokens[1]);
                case "search":
                    return searchBooks(tokens[1]);
                case "update":
                    return updateBook(tokens[1], tokens[2]);
                case "delete":
                    return deleteBook(tokens[1]);
                case "exit":
                    return "exit";
                default:
                    return "Invalid command";
            }
        }

        private String listBooks() {
            StringBuilder list = new StringBuilder();
            for (Book book : books) {
                list.append(book.toString()).append("\n");
            }
            return list.toString();
        }

        private String rentBook(String bookTitle) {
            for (Book book : books) {
                if (book.getTitle().equalsIgnoreCase(bookTitle)) {
                    if (book.getSamples() > 0) {
                        book.setSamples(book.getSamples() - 1);
                        saveBooks();
                        return "Book rented!";
                    } else {
                        return "No copies of that book available!";
                    }
                }
            }
            return "Book not found!";
        }

        private String returnBook(String bookTitle) {
            for (Book book : books) {
                if (book.getTitle().equalsIgnoreCase(bookTitle)) {
                    book.setSamples(book.getSamples() + 1);
                    saveBooks();
                    return "Book returned!";
                }
            }
            return "Book not found!";
        }

        private String registerBook(String bookData) {
            String[] attributes = bookData.split("#");
            String author = attributes[0];
            String title = attributes[1];
            String genre = attributes[2];
            int samples = Integer.parseInt(attributes[3].trim());
            Book newBook = new Book(author, title, genre, samples);
            books.add(newBook);
            saveBooks();
            return "Book registered!";
        }

        private String searchBooks(String keyword) {
            StringBuilder result = new StringBuilder();
            for (Book book : books) {
                if (book.getTitle().contains(keyword) || book.getAuthor().contains(keyword)) {
                    result.append(book.toString()).append("\n");
                }
            }
            return result.length() > 0 ? result.toString() : "No books found matching the keyword!";
        }

        private String updateBook(String bookTitle, String newDetails) {
            for (Book book : books) {
                if (book.getTitle().equalsIgnoreCase(bookTitle)) {
                    String[] attributes = newDetails.split("#");
                    String author = attributes[0];
                    String title = attributes[1];
                    String genre = attributes[2];
                    int samples = Integer.parseInt(attributes[3].trim());
                    book.setAuthor(author);
                    book.setTitle(title);
                    book.setGenre(genre);
                    book.setSamples(samples);
                    saveBooks();
                    return "Book updated!";
                }
            }
            return "Book not found!";
        }

        private String deleteBook(String bookTitle) {
            for (Book book : books) {
                if (book.getTitle().equalsIgnoreCase(bookTitle)) {
                    books.remove(book);
                    saveBooks();
                    return "Book deleted!";
                }
            }
            return "Book not found!";
        }
    }

    static class BooksWrapper {
        private List<Book> books;

        public BooksWrapper() {
        }

        public BooksWrapper(List<Book> books) {
            this.books = books;
        }

        public List<Book> getBooks() {
            return books;
        }

        public void setBooks(List<Book> books) {
            this.books = books;
        }
    }
}
