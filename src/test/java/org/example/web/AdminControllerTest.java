package org.example.web;


import org.example.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("GET /admin should show admin page with users")
    void showUserManagementPage_shouldReturnAdminView() throws Exception {
        when(userService.getAllUsers()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin"))
                .andExpect(model().attributeExists("users"));

        verify(userService).getAllUsers();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("POST /admin/users/{username}/make-admin should call service and redirect")
    void makeAdmin_shouldCallServiceAndRedirect() throws Exception {
        String username = "johndoe";

        mockMvc.perform(post("/admin/users/" + username + "/make-admin")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(userService).assignAdminRole(username);
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("POST /admin/updateRole/{userId} should call updateRole and redirect")
    void updateUserRole_shouldUpdateAndRedirect() throws Exception {
        Long userId = 1L;
        String role = "USER";

        mockMvc.perform(post("/admin/updateRole/" + userId)
                        .param("role", role)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(userService).updateUserRole(userId, role);
    }
}
