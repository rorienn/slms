package com.example.slms.service;

import com.example.slms.entity.User;
import com.example.slms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> findByUsername(final String username) {
        return userRepository.findByUsername(username);
    }

    public User save(final User user) {
        String encodedPassword = new BCryptPasswordEncoder().encode(user.password());
        User encodedUser = new User(user.id(), user.username(), encodedPassword, user.role(), user.borrowRecords());
        return userRepository.save(encodedUser);
    }
}

