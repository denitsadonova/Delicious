package org.example.service.impl;

import org.example.models.binding.VitaminAddBindingModel;
import org.example.models.entity.VitaminEntity;
import org.example.repository.VitaminRepository;
import org.example.service.VitaminService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VitaminServiceImpl implements VitaminService {
    private final VitaminRepository vitaminRepository;
    private final ModelMapper modelMapper;

    public VitaminServiceImpl(VitaminRepository vitaminRepository, ModelMapper modelMapper) {
        this.vitaminRepository = vitaminRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public void addVitamin(VitaminAddBindingModel vitaminAddBindingModel) {
        VitaminEntity vitamin = new VitaminEntity();
        vitamin.setName(vitaminAddBindingModel.getName());
        vitamin.setDescription(vitaminAddBindingModel.getDescription());

        vitaminRepository.save(vitamin);

    }
@Override
    public List<VitaminEntity> getAllVitamins() {
        return vitaminRepository.findAll();
    }

    @Override
    public List<VitaminEntity> findAllById(List<Long> vitamins) {
        return vitaminRepository.findAllById(vitamins);
    }

}
