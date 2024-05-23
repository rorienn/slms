package com.example.slms.repository;

import com.example.slms.entity.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, UUID> {}