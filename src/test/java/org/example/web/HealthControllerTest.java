package org.example.web;

import org.example.models.binding.MineralAddBindingModel;
import org.example.models.binding.VitaminAddBindingModel;
import org.example.repository.MineralRepository;
import org.example.repository.VitaminRepository;
import org.example.service.MineralService;
import org.example.service.VitaminService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HealthController.class)
@AutoConfigureMockMvc(addFilters = false)
class HealthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VitaminService vitaminService;

    @MockBean
    private MineralService mineralService;

    @MockBean
    private VitaminRepository vitaminRepository;

    @MockBean
    private MineralRepository mineralRepository;

    @Test
    @DisplayName("GET /health should return health view with vitamins and minerals")
    void showHealthPage_shouldReturnHealthView() throws Exception {
        when(vitaminService.getAllVitamins()).thenReturn(Collections.emptyList());
        when(mineralService.getAllMinerals()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/health"))
                .andExpect(status().isOk())
                .andExpect(view().name("health"))
                .andExpect(model().attributeExists("vitamins"))
                .andExpect(model().attributeExists("minerals"));
    }

    @Test
    @DisplayName("GET /add/vitamin should return add-vitamin view")
    void showAddVitaminPage_shouldReturnView() throws Exception {
        mockMvc.perform(get("/add/vitamin"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-vitamin"));
    }

    @Test
    @DisplayName("POST /add/vitamin with valid input should redirect to /health")
    void addVitaminConfirm_validData_shouldRedirect() throws Exception {
        when(vitaminRepository.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(post("/add/vitamin")
                        .param("name", "Vitamin C")
                        .param("description", "Good for immune system!")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/health"));

        verify(vitaminService).addVitamin(any(VitaminAddBindingModel.class));
    }

    @Test
    @DisplayName("POST /add/vitamin with invalid input should redirect to form with errors")
    void addVitaminConfirm_invalidData_shouldRedirectToForm() throws Exception {
        mockMvc.perform(post("/add/vitamin")
                        .param("name", "")
                        .param("description", "")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/add/vitamin"));
    }

    @Test
    @DisplayName("POST /add/mineral with valid input should redirect to /health")
    void addMineralConfirm_validData_shouldRedirect() throws Exception {
        when(mineralRepository.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(post("/add/mineral")
                        .param("name", "Iron")
                        .param("description", "Good for strength!")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/health"));

        verify(mineralService).addMineral(any(MineralAddBindingModel.class));
    }

    @Test
    @DisplayName("POST /add/mineral with invalid input should redirect to form with errors")
    void addMineralConfirm_invalidData_shouldRedirectToForm() throws Exception {
        mockMvc.perform(post("/add/mineral")
                        .param("name", "")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("addMineral"));
    }
}
