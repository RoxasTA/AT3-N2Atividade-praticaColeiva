import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ClientHandler implements Runnable {
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
        for (Book book : LibraryServer.books) {
            list.append(book.toString()).append("\n");
        }
        return list.toString();
    }

    private String rentBook(String bookTitle) {
        for (Book book : LibraryServer.books) {
            if (book.getTitle().equalsIgnoreCase(bookTitle)) {
                if (book.getSamples() > 0) {
                    book.setSamples(book.getSamples() - 1);
                    LibraryServer.saveBooks();
                    return "Book rented!";
                } else {
                    return "No copies of that book available!";
                }
            }
        }
        return "Book not found!";
    }

    private String returnBook(String bookTitle) {
        for (Book book : LibraryServer.books) {
            if (book.getTitle().equalsIgnoreCase(bookTitle)) {
                book.setSamples(book.getSamples() + 1);
                LibraryServer.saveBooks();
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
        LibraryServer.books.add(newBook);
        LibraryServer.saveBooks();
        return "Book registered!";
    }

    private String searchBooks(String keyword) {
        StringBuilder result = new StringBuilder();
        for (Book book : LibraryServer.books) {
            if (book.getTitle().contains(keyword) || book.getAuthor().contains(keyword)) {
                result.append(book.toString()).append("\n");
            }
        }
        return result.length() > 0 ? result.toString() : "No books found matching the keyword!";
    }

    private String updateBook(String bookTitle, String newDetails) {
        for (Book book : LibraryServer.books) {
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
                LibraryServer.saveBooks();
                return "Book updated!";
            }
        }
        return "Book not found!";
    }

    private String deleteBook(String bookTitle) {
        for (Book book : LibraryServer.books) {
            if (book.getTitle().equalsIgnoreCase(bookTitle)) {
                LibraryServer.books.remove(book);
                LibraryServer.saveBooks();
                return "Book deleted!";
            }
        }
        return "Book not found!";
    }
}