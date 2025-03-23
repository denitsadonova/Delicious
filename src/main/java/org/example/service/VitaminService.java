package org.example.service;

import org.example.models.binding.VitaminAddBindingModel;
import org.example.models.entity.VitaminEntity;

import java.util.List;
public interface VitaminService {


    public List<VitaminEntity> getAllVitamins();

    public void addVitamin(VitaminAddBindingModel vitaminAddBindingModel);

    public abstract List<VitaminEntity> findAllById(List<Long> vitamins);
}
