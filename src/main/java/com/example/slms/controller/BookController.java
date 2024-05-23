package com.example.slms.controller;

import com.example.slms.exception.ResourceNotFoundException;
import com.example.slms.entity.Book;
import com.example.slms.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.findAll();
    }

    @PostMapping
    public Book addBook(@RequestBody final Book book) {
        return bookService.save(book);
    }

    @PutMapping("/{id}")
    public Book updateBook(@PathVariable final UUID id, @RequestBody final Book bookDetails) {
        //Could be implemented in a try/catch instead of using Optional<T>
        Book book = bookService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + id));

        Book updatedBook = bookService.updateBook(book, bookDetails);

        return bookService.save(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable UUID id) {
        bookService.delete(id);
        return ResponseEntity.ok().build();
    }
}

