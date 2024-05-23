package com.example.slms.service;

import com.example.slms.entity.Author;
import com.example.slms.repository.AuthorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Transactional
    public Author save(final Author author) {
        return authorRepository.save(author);
    }
}


