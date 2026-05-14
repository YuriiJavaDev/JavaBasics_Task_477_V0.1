package com.yurii.pavlenko.library.models;

import java.util.Objects;

/**
 * Represents a book with a unique digital fingerprint based on its title and author.
 */
public class Book {
    private final String bookTitle;
    private final String bookAuthor;

    public Book(String bookTitle, String bookAuthor) {
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
    }

    /**
     * Generates a hash code using the title and author fields.
     * @return a unique integer fingerprint for the book.
     */
    @Override
    public int hashCode() {
        // Using the standard utility for a combined hash code
        return Objects.hash(bookTitle, bookAuthor);
    }

    @Override
    public String toString() {
        return "'" + bookTitle + "' by " + bookAuthor;
    }
}