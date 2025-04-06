package org.example.web;

import org.example.models.binding.IngredientAddBindingModel;
import org.example.repository.IngredientRepository;
import org.example.service.IngredientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(IngredientController.class)
public class IngredientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IngredientService ingredientService;

    @MockBean
    private IngredientRepository ingredientRepository;

    @InjectMocks
    private IngredientController ingredientController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
    }

    @Test
    void testAddIngredientGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/add/ingredient"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-ingredient"));
    }

    @Test
    void testAddIngredientConfirmPost_BindingResultError() throws Exception {
        IngredientAddBindingModel ingredientAddBindingModel = new IngredientAddBindingModel();

        mockMvc.perform(MockMvcRequestBuilders.post("/add/ingredient")
                        .with(csrf())  // CSRF protection included
                        .flashAttr("ingredientAddBindingModel", ingredientAddBindingModel)
                        .param("name", "")
                        .param("type", "SPICE"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("add/ingredient"));
    }
}
