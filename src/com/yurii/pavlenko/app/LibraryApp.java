package com.yurii.pavlenko.app;

import com.yurii.pavlenko.library.models.Book;

/**
 * Application to demonstrate and display the digital fingerprint of a book.
 */
public class LibraryApp {

    public static void main(String[] args) {
        // Create a book with specific details
        Book book = new Book("Clean Code", "Robert C. Martin");

        // Display the book details and its fingerprint with context
        System.out.println("Processing book: " + book);

        int fingerprint = book.hashCode();
        System.out.println("The digital fingerprint for the book is: " + fingerprint);
    }
}