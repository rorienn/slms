package com.example.slms.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
public record Category(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) UUID id,
        String name,
        @OneToMany(mappedBy = "category") List<Book> books) {
}
