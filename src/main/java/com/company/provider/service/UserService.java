package com.company.provider.service;

import com.company.provider.exeption.RestException;
import com.company.provider.dto.SubscriptionDto;
import com.company.provider.entity.User;
import com.company.provider.repository.RoleRepository;
import com.company.provider.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

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

    public User loadUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RestException("username_not_found_exception"));
    }

    public Page<User> loadNotActiveUsersPaginated(int page, int size) {
        Pageable paging = PageRequest.of(page, size);

        return userRepository.findByActive(paging,false);
    }

    public User createUser(SubscriptionDto subscriptionDto) {
        if (usernameExists(subscriptionDto.getUsername())) {
            throw new RestException("user_exist_exception");
        }

        User user = User.builder()
                .setUsername(subscriptionDto.getUsername())
                .setPassword((new BCryptPasswordEncoder().encode(subscriptionDto.getPassword())))
                .setActive(false)
                .setRole(roleRepository.findByName("ROLE_USER"))
                .build();

        return userRepository.save(user);
    }

    public User activateUser(User user) {
        user.setActive(true);

        return userRepository.save(user);
    }

    private boolean usernameExists(String username) {
        return userRepository.findByUsername(username) != null;
    }
}
