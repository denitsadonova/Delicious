package org.example.service;

import org.example.models.binding.UserLoginBindingModel;
import org.example.models.binding.UserRegisterBindingModel;

public interface UserService {
    void registerUser(UserRegisterBindingModel userRegisterBindingModel);
}
