package org.example.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.example.models.binding.UserRegisterBindingModel;
import org.example.models.binding.UserUpdateBindingModel;
import org.example.models.entity.RoleEntity;
import org.example.models.entity.UserEntity;
import org.example.models.enums.UserRoleEnum;
import org.example.repository.RoleRepository;
import org.example.repository.UserRepository;
import org.example.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserDetailsService userDetailsService;

    @InjectMocks
    private UserServiceImpl userService;

    private UserEntity testUser;
    private RoleEntity adminRole;

    @BeforeEach
    void setUp() {
        testUser = new UserEntity();
        testUser.setId(1L);
        testUser.setUsername("testUser");
        testUser.setEmail("test@example.com");
        testUser.setPassword("encodedPassword");

        adminRole = new RoleEntity();
        adminRole.setRole(UserRoleEnum.ADMIN);
    }

    @Test
    void registerUser_Success() {
        UserRegisterBindingModel userModel = new UserRegisterBindingModel();
        userModel.setUsername("newUser");
        userModel.setEmail("new@example.com");
        userModel.setPassword("password123");

        when(roleRepository.findByRole(UserRoleEnum.ADMIN)).thenReturn(Optional.of(adminRole));
        when(passwordEncoder.encode("password123")).thenReturn("hashedPassword");

        userService.registerUser(userModel);

        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void registerUser_RoleNotFound_ThrowsException() {
        UserRegisterBindingModel userModel = new UserRegisterBindingModel();
        when(roleRepository.findByRole(UserRoleEnum.ADMIN)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> userService.registerUser(userModel));
        assertEquals("User role not found", exception.getMessage());
    }

    @Test
    void assignAdminRole_Success() {
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(testUser));
        when(roleRepository.findByRole(UserRoleEnum.ADMIN)).thenReturn(Optional.of(adminRole));

        userService.assignAdminRole("testUser");

        assertTrue(testUser.getRoles().contains(adminRole));
        verify(userRepository).save(testUser);
    }

    @Test
    void assignAdminRole_UserNotFound_ThrowsException() {
        when(userRepository.findByUsername("unknownUser")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> userService.assignAdminRole("unknownUser"));
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void getAllUsers_ReturnsUsers() {
        when(userRepository.findAll()).thenReturn(List.of(testUser));

        List<UserEntity> users = userService.getAllUsers();
        assertFalse(users.isEmpty());
        assertEquals(1, users.size());
    }

    @Test
    void updateUserProfile_Success() {
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(testUser));
        UserUpdateBindingModel updateRequest = new UserUpdateBindingModel();
        updateRequest.setUsername("updatedUser");
        updateRequest.setEmail("updated@example.com");

        userService.updateUserProfile("testUser", updateRequest);

        assertEquals("updatedUser", testUser.getUsername());
        assertEquals("updated@example.com", testUser.getEmail());
        verify(userRepository).save(testUser);
    }
}