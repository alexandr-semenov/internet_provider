package com.company.provider.service;

import com.company.provider.config.RestException;
import com.company.provider.dto.SubscriptionDto;
import com.company.provider.entity.Role;
import com.company.provider.entity.User;
import com.company.provider.exeption.UserAlreadyExistException;
import com.company.provider.repository.RoleRepository;
import com.company.provider.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    final UserRepository userRepository;
    private RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new RestException("username_not_found_exception");
        }

        return user;
    }

    public User createUser(SubscriptionDto subscriptionDto) {
        if (usernameExists(subscriptionDto.getUsername())) {
            throw new RestException("user_exist_exception");
        }

        User user = new User();
        user.setUsername(subscriptionDto.getUsername());
        user.setPassword((new BCryptPasswordEncoder().encode(subscriptionDto.getPassword())));
        user.setActive(false);
        user.setRole(roleRepository.findByName("ROLE_USER"));

        return userRepository.save(user);
    }

    private boolean usernameExists(String username) {
        return userRepository.findByUsername(username) != null;
    }
}
