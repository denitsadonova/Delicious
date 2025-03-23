package org.example.service;

import org.example.models.binding.MineralAddBindingModel;
import org.example.models.entity.MineralEntity;
import org.example.repository.MineralRepository;
import org.springframework.stereotype.Service;

import java.util.List;

public interface MineralService {

     List<MineralEntity> getAllMinerals();
    void addMineral(MineralAddBindingModel mineralAddBindingModel);
        List<MineralEntity> findAllById(List<Long> minerals);
}
