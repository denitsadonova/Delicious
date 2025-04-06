package org.example.config;

import org.example.repository.*;
import org.example.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc
@Import({SecurityConfiguration.class})
class SecurityConfigurationTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private VitaminRepository vitaminRepository;
    @MockBean
    private MineralRepository mineralRepository;
    @MockBean
    private UserService userService;
    @MockBean
    private RecipeService recipeService;
    @MockBean
    private IngredientService ingredientService;
    @MockBean
    private IngredientRepository ingredientRepository;
    @MockBean
    private MenuService menuService;
    @MockBean
    private MenuRepository menuRepository;

    @MockBean
    private ApplicationUserDetailsService applicationUserDetailsService;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private VitaminService vitaminService;
    @MockBean
    private MineralService mineralService;

    @Test
    void testPublicEndpointsAccessible() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/users/login"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/users/register"))
                .andExpect(status().isOk());
    }

    @Test
    void testSecuredEndpointRedirectsToLoginWhenUnauthenticated() throws Exception {
        mockMvc.perform(get("/add/recipe"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/users/login"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testAuthenticatedUserCanAccessProtectedEndpoint() throws Exception {
        mockMvc.perform(get("/add/recipe"))
                .andExpect(status().isOk());
    }

    @Test
    void testAdminEndpointForbiddenWithoutAuth() throws Exception {
        mockMvc.perform(get("/admin/dashboard"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/users/login"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testAdminEndpointForbiddenForNonAdminUser() throws Exception {
        mockMvc.perform(get("/admin/dashboard"))
                .andExpect(status().isForbidden());
    }

}
