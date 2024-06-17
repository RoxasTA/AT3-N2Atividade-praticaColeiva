import java.util.List;

class BooksWrapper {
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