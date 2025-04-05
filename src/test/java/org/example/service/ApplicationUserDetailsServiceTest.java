package org.example.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.example.models.entity.RoleEntity;
import org.example.models.entity.UserEntity;
import org.example.models.enums.UserRoleEnum;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ApplicationUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ApplicationUserDetailsService applicationUserDetailsService;

    private UserEntity userEntity;
    private RoleEntity roleEntity;

    @BeforeEach
    void setUp() {
        roleEntity = new RoleEntity();
        roleEntity.setRole(UserRoleEnum.ADMIN);

        userEntity = new UserEntity();
        userEntity.setUsername("testUser");
        userEntity.setPassword("password123");
        userEntity.setRoles(List.of(roleEntity));
    }

    @Test
    void loadUserByUsername_UserExists() {
        // Arrange
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(userEntity));

        // Act
        UserDetails userDetails = applicationUserDetailsService.loadUserByUsername("testUser");

        // Assert
        assertNotNull(userDetails);
        assertEquals("testUser", userDetails.getUsername());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN")));
    }

    @Test
    void loadUserByUsername_UserDoesNotExist() {
        // Arrange
        when(userRepository.findByUsername("nonExistentUser")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () ->
                applicationUserDetailsService.loadUserByUsername("nonExistentUser"));
    }

    @Test
    void loadUserByUsername_UserWithMultipleRoles() {
        // Arrange
        RoleEntity userRole = new RoleEntity();
        userRole.setRole(UserRoleEnum.USER);

        userEntity.setRoles(List.of(roleEntity, userRole));
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(userEntity));

        // Act
        UserDetails userDetails = applicationUserDetailsService.loadUserByUsername("testUser");

        // Assert
        assertNotNull(userDetails);
        assertEquals("testUser", userDetails.getUsername());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN")));
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_USER")));
    }
}