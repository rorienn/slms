package com.example.slms.service;

import com.example.slms.entity.Book;
import com.example.slms.entity.BorrowRecord;
import com.example.slms.entity.User;
import com.example.slms.repository.BookRepository;
import com.example.slms.repository.BorrowRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class BorrowService {

    @Autowired
    private BorrowRecordRepository borrowRecordRepository;

    @Autowired
    private BookRepository bookRepository;

    public BorrowRecord borrowBook(final User user, final UUID bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        if (!book.available()) {
            throw new IllegalStateException("Book is not available");
        }

        Book updatedBook = new Book(
                book.id(),
                book.title(),
                book.isbn(),
                false, // Book is not available
                book.author(),
                book.category(),
                book.publisher());

        bookRepository.save(updatedBook);

        BorrowRecord borrowRecord = new BorrowRecord(null, user, book, LocalDate.now(), null);
        return borrowRecordRepository.save(borrowRecord);
    }
}

