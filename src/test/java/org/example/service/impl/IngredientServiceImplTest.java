package org.example.service.impl;

import org.example.models.binding.IngredientAddBindingModel;
import org.example.models.entity.IngredientEntity;
import org.example.models.enums.ClassOfFood;
import org.example.models.enums.IngredientType;
import org.example.repository.IngredientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {

    @Mock
    private IngredientRepository ingredientRepository;

    @InjectMocks
    private IngredientServiceImpl ingredientService;

    private IngredientAddBindingModel ingredientAddBindingModel;
    private IngredientEntity ingredientEntity;

    @BeforeEach
    void setUp() {
        ingredientAddBindingModel = new IngredientAddBindingModel();
        ingredientAddBindingModel.setName("Sugar");
        ingredientAddBindingModel.setType(IngredientType.FRUIT.name());
        ingredientAddBindingModel.setClassOfFood(ClassOfFood.FAT.name());

        ingredientEntity = new IngredientEntity();
        ingredientEntity.setId(1L);
        ingredientEntity.setName("Sugar");
        ingredientEntity.setType(IngredientType.FRUIT);
        ingredientEntity.setClassOfFood(ClassOfFood.FAT);
    }

    @Test
    void addIngredient_Success() {
        when(ingredientRepository.save(any(IngredientEntity.class))).thenReturn(ingredientEntity);

        ingredientService.addIngredient(ingredientAddBindingModel);

        verify(ingredientRepository).save(any(IngredientEntity.class));
    }

    @Test
    void getAllIngredients_Success() {
        List<IngredientEntity> ingredients = List.of(ingredientEntity);
        when(ingredientRepository.findAll()).thenReturn(ingredients);

        List<IngredientEntity> result = ingredientService.getAllIngredients();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Sugar", result.get(0).getName());
    }

    @Test
    void findAllById_Success() {
        List<Long> ids = List.of(1L);
        when(ingredientRepository.findAllById(ids)).thenReturn(List.of(ingredientEntity));

        List<IngredientEntity> result = ingredientService.findAllById(ids);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Sugar", result.get(0).getName());
    }

    @Test
    void findAllById_EmptyIds() {
        List<Long> ids = List.of();
        when(ingredientRepository.findAllById(ids)).thenReturn(List.of());

        List<IngredientEntity> result = ingredientService.findAllById(ids);

        assertTrue(result.isEmpty());
    }
}