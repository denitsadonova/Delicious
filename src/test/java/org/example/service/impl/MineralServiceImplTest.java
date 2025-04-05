package org.example.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.example.models.binding.MineralAddBindingModel;
import org.example.models.entity.MineralEntity;
import org.example.repository.MineralRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class MineralServiceImplTest {

    @Mock
    private MineralRepository mineralRepository;

    @InjectMocks
    private MineralServiceImpl mineralService;

    private MineralAddBindingModel mineralAddBindingModel;
    private MineralEntity mineralEntity;

    @BeforeEach
    void setUp() {
        mineralAddBindingModel = new MineralAddBindingModel();
        mineralAddBindingModel.setName("Quartz");
        mineralAddBindingModel.setDescription("A hard, crystalline mineral composed of silicon and oxygen.");

        mineralEntity = new MineralEntity();
        mineralEntity.setId(1L);
        mineralEntity.setName("Quartz");
        mineralEntity.setDescription("A hard, crystalline mineral composed of silicon and oxygen.");
    }

    @Test
    void addMineral_Success() {
        // Arrange
        when(mineralRepository.save(any(MineralEntity.class))).thenReturn(mineralEntity);

        // Act
        mineralService.addMineral(mineralAddBindingModel);

        // Assert
        verify(mineralRepository).save(any(MineralEntity.class));
    }

    @Test
    void getAllMinerals_Success() {
        // Arrange
        List<MineralEntity> minerals = List.of(mineralEntity);
        when(mineralRepository.findAll()).thenReturn(minerals);

        // Act
        List<MineralEntity> result = mineralService.getAllMinerals();

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Quartz", result.get(0).getName());
    }

    @Test
    void findAllById_Success() {
        // Arrange
        List<Long> ids = List.of(1L);
        when(mineralRepository.findAllById(ids)).thenReturn(List.of(mineralEntity));

        // Act
        List<MineralEntity> result = mineralService.findAllById(ids);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Quartz", result.get(0).getName());
    }

    @Test
    void findAllById_EmptyIds() {
        // Arrange
        List<Long> ids = List.of();
        when(mineralRepository.findAllById(ids)).thenReturn(List.of());

        // Act
        List<MineralEntity> result = mineralService.findAllById(ids);

        // Assert
        assertTrue(result.isEmpty());
    }
}