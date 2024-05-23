package com.example.slms.integrationtest;

import com.example.slms.entity.Book;
import com.example.slms.entity.BorrowRecord;
import com.example.slms.entity.Role;
import com.example.slms.entity.User;
import com.example.slms.integrationtest.config.TestContainersConfig;
import com.example.slms.repository.BookRepository;
import com.example.slms.repository.UserRepository;
import com.example.slms.repository.BorrowRecordRepository;
import com.example.slms.service.BorrowService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Import(TestContainersConfig.class)
@Testcontainers
public class BorrowServiceIntegrationTest {

    @Autowired
    private BorrowService borrowService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BorrowRecordRepository borrowRecordRepository;

    private User user;
    private Book book;
    private UUID bookUUID;

    @BeforeEach
    void setUp() {
        bookUUID = UUID.randomUUID();
        user = new User(UUID.randomUUID(), "JohnDoe","johndoespassword", Role.CLIENT,null);
        book = new Book(bookUUID, "Test Book", "1234567890", true, null, null, null);

        user = userRepository.save(user);
        book = bookRepository.save(book);
    }

    @Test
    void borrowBook_successful() {
        BorrowRecord borrowRecord = borrowService.borrowBook(user, book.id());

        assertNotNull(borrowRecord);
        assertEquals(user, borrowRecord.user());
        assertEquals(book, borrowRecord.book());
        assertEquals(LocalDate.now(), borrowRecord.borrowDate());
        assertNull(borrowRecord.returnDate());

        Book borrowedBook = bookRepository.findById(book.id()).orElseThrow();
        assertFalse(borrowedBook.available());

        assertEquals(1, borrowRecordRepository.count());
    }
}

