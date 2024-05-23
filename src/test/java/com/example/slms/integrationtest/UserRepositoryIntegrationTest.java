package com.example.slms.integrationtest;

import com.example.slms.entity.Role;
import com.example.slms.entity.User;
import com.example.slms.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Testcontainers
public class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void testSaveUser() {
        // Given
        User user = new User(UUID.randomUUID(), "JohnDoe","johndoespassword", Role.CLIENT,null);

        // When
        userRepository.save(user);
        entityManager.flush();
        entityManager.clear();

        // Then
        User savedUser = userRepository.findByUsername(user.username()).orElse(null);
        assertEquals(user, savedUser);
    }
}

