# Digital Library: Hashing & Identity (JavaBasics_Task_477_V0.1)

## 📖 Description
In large-scale digital catalogs, comparing full strings or entire objects is computationally expensive. This project introduces the concept of **Hashing**, where complex object data is reduced to a single integer "fingerprint". By overriding the `hashCode()` method and utilizing the `Objects.hash` utility, we ensure that any `Book` with the same title and author consistently generates the same identifier. This is a fundamental requirement for storing objects in high-performance data structures like `HashMap` or `HashSet`.

## 📋 Requirements Compliance
- **Identity Logic**: Used both `bookTitle` and `bookAuthor` to generate the hash code.
- **Standard Utilities**: Leveraged `java.util.Objects.hash` for robust and clean implementation.
- **Encapsulation**: Protected book attributes within a public class structure.

## 🚀 Architectural Stack
- Java 8+ (Objects Utility, Hashing)

## 🏗️ Implementation Details
- **Book**: The core model class with hashing capabilities.
- **LibraryApp**: Entry point.

## 📋 Expected result
```text
Processing book: 'Clean Code' by Robert C. Martin
The digital fingerprint for the book is: -1425224273
```

## 💻 Code Example

Project Structure:

    JavaBasics_Task_477/
    ├── src/
    │   └── com/yurii/pavlenko/
    │                 ├── app/
    │                 │   └── LibraryApp.java
    │                 └── library/
    │                     └── models/
    │                         └── Book.java
    ├── LICENSE
    ├── TASK.md
    ├── THEORY.md
    └── README.md

Code
```java
package com.yurii.pavlenko.app;

import com.yurii.pavlenko.library.models.Book;

public class LibraryApp {

    public static void main(String[] args) {

        Book book = new Book("Clean Code", "Robert C. Martin");

        System.out.println("Processing book: " + book);

        int fingerprint = book.hashCode();
        System.out.println("The digital fingerprint for the book is: " + fingerprint);
    }
}
```
```java
package com.yurii.pavlenko.library.models;

import java.util.Objects;

public class Book {
    private final String bookTitle;
    private final String bookAuthor;

    public Book(String bookTitle, String bookAuthor) {
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookTitle, bookAuthor);
    }

    @Override
    public String toString() {
        return "'" + bookTitle + "' by " + bookAuthor;
    }
}
```

## ⚖️ License
This project is licensed under the **MIT License**.

Copyright (c) 2026 Yurii Pavlenko

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files...

License: [MIT](LICENSE)
