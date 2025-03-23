package org.example.service.impl;

import org.example.models.binding.MineralAddBindingModel;
import org.example.models.entity.MineralEntity;
import org.example.repository.MineralRepository;
import org.example.service.MineralService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MineralServiceImpl implements MineralService {
    private final MineralRepository mineralRepository;
    private final ModelMapper modelMapper;

    public MineralServiceImpl(MineralRepository mineralRepository, ModelMapper modelMapper) {
        this.mineralRepository = mineralRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public void addMineral(MineralAddBindingModel mineralAddBindingModel) {
        MineralEntity mineral = new MineralEntity();
        mineral.setName(mineralAddBindingModel.getName());
        mineral.setDescription(mineralAddBindingModel.getDescription());

        mineralRepository.save(mineral);

    }
@Override
    public List<MineralEntity> getAllMinerals() {
        return mineralRepository.findAll();
    }

    @Override
    public List<MineralEntity> findAllById(List<Long> minerals) {
        return mineralRepository.findAllById(minerals);
    }

}
