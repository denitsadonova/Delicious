package org.example.service.impl;

import org.example.models.binding.UserUpdateBindingModel;
import org.example.models.entity.RoleEntity;
import org.example.models.entity.UserEntity;
import org.example.models.binding.UserRegisterBindingModel;
import org.example.models.enums.UserRoleEnum;
import org.example.repository.RoleRepository;
import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserDetailsService userDetailsService;


    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
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
    @Override
    public void updateUserRole(Long userId, String roleName) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        RoleEntity role = roleRepository.findByRole(UserRoleEnum.valueOf(roleName))
                .orElseThrow(() -> new RuntimeException("Role not found"));
        if (user.getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
            refreshUserSession(user.getUsername(), roleName);
        }
        user.setRoles(List.of(role));
        userRepository.save(user);
    }
    @Override
    public void updateUserProfile(String username, UserUpdateBindingModel request) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        userRepository.save(user);
    }
    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    private void refreshUserSession(String username, String newRole) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        List<GrantedAuthority> updatedAuthorities = List.of(new SimpleGrantedAuthority(newRole));

        Authentication newAuth = new UsernamePasswordAuthenticationToken(
                userDetails, userDetails.getPassword(), updatedAuthorities);

        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }
}
