package step.learning.oop;

public class Book  extends Literature implements Copiable{
    public String getAuthor() {
        return author;
    }

    public Book(String title,String author) {
        super.setTitle(title);
        this.author = author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    private String author;

    @Override
    public String getCard() {
        return String.format("Book: %s '%s'",this.getAuthor(),super.getTitle());
    }
}
