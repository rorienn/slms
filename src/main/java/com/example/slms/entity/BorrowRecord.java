package com.example.slms.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(indexes = @Index(name = "idx_borrow", columnList = "user_id, book_id"))
public record BorrowRecord(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) UUID id,
        @ManyToOne @JoinColumn(name = "user_id", nullable = false) User user,
        @ManyToOne @JoinColumn(name = "book_id", nullable = false) Book book,
        LocalDate borrowDate,
        LocalDate returnDate) {
}
