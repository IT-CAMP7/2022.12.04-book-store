package pl.camp.it.book.store.model;

public class OrderPosition {
    private int id;
    private Book book;
    private int quantity;

    public OrderPosition(int id, Book book, int quantity) {
        this(book, quantity);
        this.id = id;
    }

    public OrderPosition(Book book, int quantity) {
        this.book = book;
        this.quantity = quantity;
    }

    public OrderPosition() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void incrementQuantity() {
        this.quantity++;
    }
}
