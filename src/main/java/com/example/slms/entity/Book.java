package com.example.slms.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(indexes = @Index(name = "idx_title", columnList = "title"))
public record Book(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) UUID id,
        String title,
        String isbn,
        boolean available,
        @ManyToOne @JoinColumn(name = "author_id") Author author,
        @ManyToOne @JoinColumn(name = "category_id") Category category,
        @ManyToOne @JoinColumn(name = "publisher_id") Publisher publisher) {
}


