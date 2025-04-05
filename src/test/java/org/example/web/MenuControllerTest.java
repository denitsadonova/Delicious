package org.example.web;

import org.example.models.entity.MenuEntity;
import org.example.models.entity.UserEntity;
import org.example.repository.UserRepository;
import org.example.service.MenuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MenuController.class)
@AutoConfigureMockMvc(addFilters = false)
class MenuControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MenuService menuService;

    @MockBean
    private UserRepository userRepository;

    @Mock
    private Authentication authentication;

    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        userEntity = new UserEntity();
        userEntity.setUsername("testuser");

        // Mock authentication
        authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("testuser");
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void menuPage_shouldReturnMenuPageWithMenus() throws Exception {
        // Mock the service method
        List<MenuEntity> menuEntities = List.of(new MenuEntity(), new MenuEntity());  // mock your actual MenuEntity list
        when(menuService.getGroupedMenus()).thenReturn(menuEntities);

        mockMvc.perform(MockMvcRequestBuilders.get("/menu")
                        .with(csrf())) // CSRF token for security
                .andExpect(status().isOk())
                .andExpect(view().name("menu"))
                .andExpect(model().attributeExists("menus"))
                .andExpect(model().attribute("menus", menuEntities));
    }

    @Test
    void addRecipeToMenu_shouldReturnSuccessView() throws Exception {
        Long recipeId = 1L;
        String dayOfWeek = "MONDAY";  // assuming you are using string for testing

        // Mock user repository
        when(userRepository.findByUsername(anyString())).thenReturn(java.util.Optional.of(userEntity));

        // Mock the service method
        doNothing().when(menuService).createMenu(any(UserEntity.class), any(), eq(recipeId));

        mockMvc.perform(MockMvcRequestBuilders.post("/menus/recipes/{recipeId}/add", recipeId)
                        .param("dayOfWeek", dayOfWeek) // assuming it's a string for testing
                        .with(csrf())) // CSRF token for security
                .andExpect(status().isOk())
                .andExpect(view().name("success-add-menu"));

        verify(menuService, times(1)).createMenu(any(UserEntity.class), any(), eq(recipeId));
    }

    @Test
    void removeRecipeFromMenu_shouldRedirectToMenuPage() throws Exception {
        Long recipeId = 1L;
        String dayOfWeek = "MONDAY";  // assuming you are using string for testing

        // Mock user repository
        when(userRepository.findByUsername(anyString())).thenReturn(java.util.Optional.of(userEntity));

        // Mock the service method
        doNothing().when(menuService).removeRecipeFromMenu(any(UserEntity.class), any(), eq(recipeId));

        mockMvc.perform(MockMvcRequestBuilders.post("/menus/recipes/{recipeId}/remove", recipeId)
                        .param("dayOfWeek", dayOfWeek) // assuming it's a string for testing
                        .with(csrf())) // CSRF token for security
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/menu"));

        verify(menuService, times(1)).removeRecipeFromMenu(any(UserEntity.class), any(), eq(recipeId));
    }

    @Test
    void removeRecipeFromMenu_userNotFound_shouldThrowException() throws Exception {
        Long recipeId = 1L;
        String dayOfWeek = "MONDAY";  // assuming you are using string for testing

        // Mock the userRepository to return an empty Optional (simulating a user not found)
        when(userRepository.findByUsername(anyString())).thenReturn(java.util.Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.post("/menus/recipes/{recipeId}/remove", recipeId)
                        .param("dayOfWeek", dayOfWeek) // assuming it's a string for testing
                        .with(csrf())) // CSRF token for security
                .andExpect(status().isInternalServerError()) // Expecting a 500 error due to RuntimeException
                .andExpect(content().string("Something went wrong: User not found"));
    }
    @Test
    void removeRecipeFromMenu_userFound_shouldRemoveRecipe() throws Exception {
        Long recipeId = 1L;
        String dayOfWeek = "MONDAY";  // assuming you are using string for testing

        // Mock the userRepository to return a user
        when(userRepository.findByUsername(anyString())).thenReturn(java.util.Optional.of(userEntity));

        // Mock the menuService remove method
        doNothing().when(menuService).removeRecipeFromMenu(any(UserEntity.class), any(), eq(recipeId));

        mockMvc.perform(MockMvcRequestBuilders.post("/menus/recipes/{recipeId}/remove", recipeId)
                        .param("dayOfWeek", dayOfWeek) // assuming it's a string for testing
                        .with(csrf())) // CSRF token for security
                .andExpect(status().is3xxRedirection()) // Expecting a redirect after successful removal
                .andExpect(header().string("Location", "/menu")); // Expecting redirect to /menu
    }
}
