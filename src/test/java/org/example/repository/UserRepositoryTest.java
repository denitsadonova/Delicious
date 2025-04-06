package org.example.repository;

import org.example.models.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(userEntity));

        Optional<UserEntity> result = userRepository.findByUsername("testUser");

        assertTrue(result.isPresent());
        assertEquals("testUser", result.get().getUsername());
    }

    @Test
    void findByUsername_NotFound() {
        when(userRepository.findByUsername("nonExistent")).thenReturn(Optional.empty());

        Optional<UserEntity> result = userRepository.findByUsername("nonExistent");

        assertFalse(result.isPresent());
    }
}