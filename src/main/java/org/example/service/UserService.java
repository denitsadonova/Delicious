package org.example.service;

import org.example.models.binding.UserRegisterBindingModel;
import org.example.models.binding.UserUpdateBindingModel;
import org.example.models.entity.UserEntity;

import java.util.List;

public interface UserService {

    void registerUser(UserRegisterBindingModel userRegisterBindingModel);

    void assignAdminRole(String username);

    List<UserEntity> getAllUsers();

    void updateUserRole(Long userId, String roleName);

    void updateUserProfile(String username, UserUpdateBindingModel request);

    UserEntity findByUsername(String username);
}
