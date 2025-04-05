package org.example.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.example.models.binding.VitaminAddBindingModel;
import org.example.models.entity.VitaminEntity;
import org.example.repository.VitaminRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class VitaminServiceImplTest {

    @Mock
    private VitaminRepository vitaminRepository;

    @InjectMocks
    private VitaminServiceImpl vitaminService;

    private VitaminAddBindingModel vitaminAddBindingModel;
    private VitaminEntity vitaminEntity;

    @BeforeEach
    void setUp() {
        vitaminAddBindingModel = new VitaminAddBindingModel();
        vitaminAddBindingModel.setName("Vitamin C");
        vitaminAddBindingModel.setDescription("An essential vitamin for the body.");

        vitaminEntity = new VitaminEntity();
        vitaminEntity.setId(1L);
        vitaminEntity.setName("Vitamin C");
        vitaminEntity.setDescription("An essential vitamin for the body.");
    }

    @Test
    void addVitamin_Success() {
        // Arrange
        when(vitaminRepository.save(any(VitaminEntity.class))).thenReturn(vitaminEntity);

        // Act
        vitaminService.addVitamin(vitaminAddBindingModel);

        // Assert
        verify(vitaminRepository).save(any(VitaminEntity.class));
    }

    @Test
    void getAllVitamins_Success() {
        // Arrange
        List<VitaminEntity> vitamins = List.of(vitaminEntity);
        when(vitaminRepository.findAll()).thenReturn(vitamins);

        // Act
        List<VitaminEntity> result = vitaminService.getAllVitamins();

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Vitamin C", result.get(0).getName());
    }

    @Test
    void findAllById_Success() {
        // Arrange
        List<Long> ids = List.of(1L);
        when(vitaminRepository.findAllById(ids)).thenReturn(List.of(vitaminEntity));

        // Act
        List<VitaminEntity> result = vitaminService.findAllById(ids);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Vitamin C", result.get(0).getName());
    }

    @Test
    void findAllById_EmptyIds() {
        // Arrange
        List<Long> ids = List.of();
        when(vitaminRepository.findAllById(ids)).thenReturn(List.of());

        // Act
        List<VitaminEntity> result = vitaminService.findAllById(ids);

        // Assert
        assertTrue(result.isEmpty());
    }
}