package org.example.service;

import org.example.models.binding.UserLoginBindingModel;
import org.example.models.binding.UserRegisterBindingModel;
import org.example.models.entity.UserEntity;

import java.util.List;

public interface UserService {
    void registerUser(UserRegisterBindingModel userRegisterBindingModel);

    void assignAdminRole(String username);

    List<UserEntity> getAllUsers();
}
