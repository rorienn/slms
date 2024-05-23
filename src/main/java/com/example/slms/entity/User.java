package com.example.slms.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(indexes = @Index(name = "idx_username", columnList = "username"))
public record User(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) UUID id,
        String username,
        String password,
        @Enumerated(EnumType.STRING) Role role,
        @OneToMany(mappedBy = "user") List<BorrowRecord> borrowRecords) {
}
