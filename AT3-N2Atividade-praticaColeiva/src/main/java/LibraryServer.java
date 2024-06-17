import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

class LibraryServer {
    private static final String JSON_FILE = "books.json";
    static List<Book> books;

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

    static synchronized void saveBooks() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(JSON_FILE), new BooksWrapper(books));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
