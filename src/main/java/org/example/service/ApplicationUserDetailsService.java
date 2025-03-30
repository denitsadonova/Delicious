package org.example.service;

import org.example.models.entity.RoleEntity;
import org.example.models.entity.UserEntity;
import org.example.models.userDetails.AppUserDetails;
import org.example.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class ApplicationUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public ApplicationUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return
                userRepository.findByUsername(username).map(this::map).
                        orElseThrow(() -> new UsernameNotFoundException("User with name " + username + " not found!"));
    }

    private UserDetails map(UserEntity userEntity) {
        return AppUserDetails.withUsername(userEntity.getUsername()).password(userEntity.getPassword())
                .authorities(userEntity.getRoles().stream().map(ApplicationUserDetailsService::map).toList()).build();
    }
private static GrantedAuthority map(RoleEntity roleEntity) {
        return  new SimpleGrantedAuthority("ROLE_" + roleEntity.getRole().name());
}
}


