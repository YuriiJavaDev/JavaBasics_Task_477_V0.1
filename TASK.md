### Imagine you're a diligent librarian tasked with organizing a vast digital catalog of books. To efficiently manage and find your books, you need a quick way to generate a unique "digital fingerprint" for each book based on its core identity.

#### - Your first step is to design a Book class. This class should clearly define two essential attributes: bookTitle (a string value) and bookAuthor (also a string value). The crux of this task is generating this unique fingerprint. You'll need to override the hashCode method within your Book class. Instead of creating complex hashing logic yourself, you'll use the powerful Objects.hash utility, passing it both bookTitle and bookAuthor to generate a combined hash code. This ensures that books with the same title and author will consistently produce the same fingerprint.

#### - Once your Book class is perfectly created, create a new Book object. Assign it a specific title and author. Finally, display the calculated hash code for this Book object on your screen. This will demonstrate how your digital catalog assigns a sequential identifier to each unique literary work.

```java

public class LibraryApp {
    public static void main(String[] args) {
        // Create a book with a specific title and author
        Book book = new Book("Clean Code", "Robert C. Martin");

        // Print the calculated hash code for the Book object
        System.out.println(book.hashCode());
    }
}
```
