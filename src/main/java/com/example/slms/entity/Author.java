package com.example.slms.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(indexes = @Index(name = "idx_name", columnList = "name"))
public record Author(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) UUID id,
        String name,
        String biography,
        @OneToMany(mappedBy = "author") List<Book> books) {
}
