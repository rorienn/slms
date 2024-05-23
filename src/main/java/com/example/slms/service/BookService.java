package com.example.slms.service;

import com.example.slms.entity.Book;
import com.example.slms.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public List<Book> findAvailableBooks() {
        return bookRepository.findByAvailable(true);
    }

    @Transactional
    public Book save(final Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    public void delete(final UUID id) {
        bookRepository.deleteById(id);
    }

    public Optional<Book> findById(final UUID id) {
        return bookRepository.findById(id);
    }

    public Book updateBook(final Book book, final Book bookDetails) {
        return new Book(
                book.id(),
                bookDetails.title(),
                bookDetails.isbn(),
                bookDetails.available(),
                bookDetails.author(),
                bookDetails.category(),
                bookDetails.publisher());
    }
}
