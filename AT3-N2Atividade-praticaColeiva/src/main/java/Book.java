public class Book {
    private String author;
    private String title;
    private String genre;
    private int samples;

    public Book() {
    }

    public Book(String author, String title, String genre, int samples) {
        this.author = author;
        this.title = title;
        this.genre = genre;
        this.samples = samples;
    }

    // Getters e Setters
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getSamples() {
        return samples;
    }

    public void setSamples(int samples) {
        this.samples = samples;
    }

    // Método para retornar uma representação em string do livro
    @Override
    public String toString() {
        return "Book{" +
                "author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", samples=" + samples +
                '}';
    }
}
