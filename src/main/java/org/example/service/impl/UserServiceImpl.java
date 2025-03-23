package org.example.service.impl;

import org.example.models.entity.RoleEntity;
import org.example.models.entity.UserEntity;
import org.example.models.binding.UserRegisterBindingModel;
import org.example.models.enums.UserRoleEnum;
import org.example.repository.RoleRepository;
import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
//    private final UserDetailsService userDetailsService;


    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
//        this.userDetailsService = userDetailsService;
        this.roleRepository = roleRepository;
    }

    @Override
    public void registerUser(UserRegisterBindingModel userRegisterBindingModel) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userRegisterBindingModel.getUsername());
        userEntity.setEmail(userRegisterBindingModel.getEmail());
        userEntity.setPassword(passwordEncoder.encode(userRegisterBindingModel.getPassword()));

        RoleEntity userRole = roleRepository.findByRole(UserRoleEnum.ADMIN)
                .orElseThrow(() -> new RuntimeException("User role not found"));

        List<RoleEntity> roles = new ArrayList<>();
        roles.add(userRole);
        userEntity.setRoles(roles);

        userRepository.save(userEntity);
    }
    @Override
    public void assignAdminRole(String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        RoleEntity adminRole = roleRepository.findByRole(UserRoleEnum.ADMIN)
                .orElseThrow(() -> new RuntimeException("Admin role not found"));

        user.getRoles().add(adminRole);
        userRepository.save(user);
    }
    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }
}
