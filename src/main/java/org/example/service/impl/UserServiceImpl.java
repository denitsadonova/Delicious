package org.example.service.impl;

import org.example.models.entity.UserEntity;
import org.example.models.binding.UserRegisterBindingModel;
import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
//    private final UserDetailsService userDetailsService;


    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
//        this.userDetailsService = userDetailsService;
    }

    @Override
    public void registerUser(UserRegisterBindingModel userRegisterBindingModel) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userRegisterBindingModel.getUsername());
        userEntity.setEmail(userRegisterBindingModel.getEmail());
        userEntity.setPassword(passwordEncoder.encode(userRegisterBindingModel.getPassword()));

    userRepository.save(userEntity);
    }

}
