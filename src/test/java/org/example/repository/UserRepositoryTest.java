package org.example.repository;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.example.models.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        userEntity = new UserEntity();
        userEntity.setUsername("testUser");
        userEntity.setPassword("password123");
    }

    @Test
    void findByUsername_Found() {
        // Arrange
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(userEntity));

        // Act
        Optional<UserEntity> result = userRepository.findByUsername("testUser");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("testUser", result.get().getUsername());
    }

    @Test
    void findByUsername_NotFound() {
        // Arrange
        when(userRepository.findByUsername("nonExistent")).thenReturn(Optional.empty());

        // Act
        Optional<UserEntity> result = userRepository.findByUsername("nonExistent");

        // Assert
        assertFalse(result.isPresent());
    }
}