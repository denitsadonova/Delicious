package org.example.service;

import org.example.models.binding.VitaminAddBindingModel;
import org.example.models.entity.VitaminEntity;

import java.util.List;

public interface VitaminService {


     List<VitaminEntity> getAllVitamins();

     void addVitamin(VitaminAddBindingModel vitaminAddBindingModel);

     List<VitaminEntity> findAllById(List<Long> vitamins);
}
