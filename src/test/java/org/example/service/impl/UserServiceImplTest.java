package org.example.service.impl;

import org.example.models.binding.UserRegisterBindingModel;
import org.example.models.binding.UserUpdateBindingModel;
import org.example.models.entity.RoleEntity;
import org.example.models.entity.UserEntity;
import org.example.models.enums.UserRoleEnum;
import org.example.repository.RoleRepository;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

    @Test
    void testFindByUsername_found() {
        String username = "john";
        UserEntity mockUser = new UserEntity();
        mockUser.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(mockUser));

        UserEntity result = userService.findByUsername(username);
        assertNotNull(result);
        assertEquals(username, result.getUsername());
    }

    @Test
    void testFindByUsername_notFound() {
        String username = "missingUser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> userService.findByUsername(username));

        assertEquals("User not found", ex.getMessage());
    }

    @Test
    void testUpdateUserRole_selfUser_refreshSessionAndSave() {
        Long userId = 1L;
        String username = "john";
        String roleName = "ADMIN";

        UserEntity user = new UserEntity();
        user.setId(userId);
        user.setUsername(username);

        RoleEntity role = new RoleEntity();
        role.setRole(UserRoleEnum.ADMIN);

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password("password")
                .authorities("USER")
                .build();

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(username, "password", List.of());
        SecurityContextHolder.getContext().setAuthentication(auth);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(roleRepository.findByRole(UserRoleEnum.valueOf(roleName))).thenReturn(Optional.of(role));
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);

        userService.updateUserRole(userId, roleName);

        assertEquals(List.of(role), user.getRoles());
        verify(userRepository).save(user);

        var updatedAuth = SecurityContextHolder.getContext().getAuthentication();
        assertEquals(username, updatedAuth.getName());
        assertTrue(updatedAuth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(roleName)));
    }

    @Test
    void testUpdateUserRole_userNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> userService.updateUserRole(1L, "ADMIN"));

        assertEquals("User not found", ex.getMessage());
    }

    @Test
    void testUpdateUserRole_roleNotFound() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setUsername("john");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(roleRepository.findByRole(UserRoleEnum.ADMIN)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> userService.updateUserRole(1L, "ADMIN"));

        assertEquals("Role not found", ex.getMessage());
    }
}