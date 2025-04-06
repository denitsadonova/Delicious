package org.example.service.impl;

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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        when(mineralRepository.save(any(MineralEntity.class))).thenReturn(mineralEntity);

        mineralService.addMineral(mineralAddBindingModel);

        verify(mineralRepository).save(any(MineralEntity.class));
    }

    @Test
    void getAllMinerals_Success() {
        List<MineralEntity> minerals = List.of(mineralEntity);
        when(mineralRepository.findAll()).thenReturn(minerals);

        List<MineralEntity> result = mineralService.getAllMinerals();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Quartz", result.get(0).getName());
    }

    @Test
    void findAllById_Success() {
        List<Long> ids = List.of(1L);
        when(mineralRepository.findAllById(ids)).thenReturn(List.of(mineralEntity));

        List<MineralEntity> result = mineralService.findAllById(ids);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Quartz", result.get(0).getName());
    }

    @Test
    void findAllById_EmptyIds() {
        List<Long> ids = List.of();
        when(mineralRepository.findAllById(ids)).thenReturn(List.of());

        List<MineralEntity> result = mineralService.findAllById(ids);

        assertTrue(result.isEmpty());
    }
}