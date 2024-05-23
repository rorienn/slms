package com.example.slms.unittest;

import com.example.slms.entity.Book;
import com.example.slms.entity.BorrowRecord;
import com.example.slms.entity.Role;
import com.example.slms.entity.User;
import com.example.slms.repository.BookRepository;
import com.example.slms.repository.BorrowRecordRepository;
import com.example.slms.service.BorrowService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BorrowServiceTest {

    @Mock
    private BorrowRecordRepository borrowRecordRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BorrowService borrowService;

    @Captor
    private ArgumentCaptor<Book> bookCaptor;

    private User user;
    private Book book;
    private UUID userUUID, bookUUID;

    @BeforeEach
    void setUp() {
        userUUID = UUID.randomUUID();
        bookUUID = UUID.randomUUID();
        user = new User(userUUID, "JohnDoe","johndoespassword", Role.CLIENT,null);
        book = new Book(bookUUID, "Test Book", "1234567890", true, null, null, null);
    }

    @Test
    void borrowBook_successful() {
        // Arrange
        when(bookRepository.findById(bookUUID)).thenReturn(Optional.of(book));
        when(borrowRecordRepository.save(any(BorrowRecord.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        BorrowRecord result = borrowService.borrowBook(user, bookUUID);

        // Assert
        assertNotNull(result);
        assertEquals(user, result.user());
        assertEquals(book, result.book());
        assertEquals(LocalDate.now(), result.borrowDate());
        assertNull(result.returnDate());

        verify(bookRepository).save(bookCaptor.capture());
        Book updatedBook = bookCaptor.getValue();
        assertFalse(updatedBook.available());
        assertEquals(book.id(), updatedBook.id());
    }

    @Test
    void borrowBook_bookNotFound() {
        // Arrange
        when(bookRepository.findById(bookUUID)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> borrowService.borrowBook(user, bookUUID));
        verify(bookRepository, never()).save(any(Book.class));
        verify(borrowRecordRepository, never()).save(any(BorrowRecord.class));
    }

    @Test
    void borrowBook_bookNotAvailable() {
        // Arrange
        Book unavailableBook = new Book(UUID.randomUUID(), "Test Book", "1234567890", false, null, null, null);
        when(bookRepository.findById(bookUUID)).thenReturn(Optional.of(unavailableBook));

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> borrowService.borrowBook(user, bookUUID));
        verify(bookRepository, never()).save(any(Book.class));
        verify(borrowRecordRepository, never()).save(any(BorrowRecord.class));
    }
}

