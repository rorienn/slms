package com.example.slms.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(indexes = @Index(name = "idx_name", columnList = "name"))
public record Publisher(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) UUID id,
        String name,
        String address,
        @OneToMany(mappedBy = "publisher") List<Book> books) {
}

