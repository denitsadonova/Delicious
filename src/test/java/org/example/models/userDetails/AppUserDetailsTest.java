package org.example.models.userDetails;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AppUserDetailsTest {

    @Mock
    private AppUserDetails appUserDetails;
    private String username;
    private String password;
    @Mock
    private GrantedAuthority authority;

    @BeforeEach
    void setUp() {
        // Setting up data for each test
        username = "testUser";
        password = "testPassword";
        authority = new SimpleGrantedAuthority("USER");

        // Create an AppUserDetails instance using the constructor
        appUserDetails = new AppUserDetails(username, password, Collections.singletonList(authority));
    }

    @Test
    void testSetUsername() {
        String newUsername = "newUsername";
        appUserDetails.setUsername(newUsername);
        assertEquals(newUsername, appUserDetails.getUsername());
    }


    @Test
    void testSetPassword() {
        String newPassword = "newPassword";
        appUserDetails.setPassword(newPassword);
        assertEquals(newPassword, appUserDetails.getPassword());
    }


    @Test
    void testEmptyAuthorities() {
        // Create a user with no authorities
        AppUserDetails userWithNoAuthorities = new AppUserDetails("testNoAuthorities", "password", Collections.emptyList());
        assertTrue(userWithNoAuthorities.getAuthorities().isEmpty());
    }

    @Test
    void testEqualsAndHashCode() {
        // Verifying equality based on username, password, and authorities
        AppUserDetails user1 = new AppUserDetails(username, password, Collections.singletonList(authority));
        AppUserDetails user2 = new AppUserDetails(username, password, Collections.singletonList(authority));

        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());

        // Testing inequality
        AppUserDetails user3 = new AppUserDetails("anotherUser", "password", Collections.singletonList(authority));
        assertNotEquals(user1, user3);
    }
}
