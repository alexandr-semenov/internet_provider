package com.company.provider;

import com.company.provider.dto.SubscriptionDto;
import com.company.provider.entity.User;
import com.company.provider.repository.RoleRepository;
import com.company.provider.repository.UserRepository;
import com.company.provider.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTests {
    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    RoleRepository roleRepository;

    @Test
    public void whenValidUsernameThenUserShouldBeFound() {
        String username = "test username";
        User mockUser = User.builder().setUsername(username).build();

        when(userRepository.findByUsername(username)).thenReturn(mockUser);
        UserDetails foundUser = userService.loadUserByUsername(username);

        assertEquals(username, foundUser.getUsername());
    }

    @Test
    public void whenValidIfThenUserShouldBeFound() {
        Long id = 1L;
        User mockUser = User.builder().setId(id).build();

        when(userRepository.findById(id)).thenReturn(java.util.Optional.ofNullable(mockUser));
        User foundUser = userService.loadUserById(id);

        assertEquals(id, foundUser.getId());
    }

    @Test
    public void whenPageableThenPageUserShouldBeFound() {
        int page = 1;
        int size = 5;
        Long id = 1L;
        int foundUsersSize = 1;

        Pageable paging = PageRequest.of(page, size);
        List<User> mockUsers = Collections.singletonList(User.builder().setId(id).build());
        Page<User> mockPageUser = new PageImpl<>(mockUsers);

        when(userRepository.findByActive(paging, false)).thenReturn(mockPageUser);
        Page<User> userPage = userService.loadNotActiveUsersPaginated(page, size);
        List<User> foundUsers = userPage.getContent();

        assertEquals(foundUsersSize, foundUsers.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSaveUserWithOutPasswordThenTrowException() {
        SubscriptionDto subscriptionDto = new SubscriptionDto();
        subscriptionDto.setUsername("test username");

        userService.createUser(subscriptionDto);
    }

    @Test
    public void whenSaveUserThenReturnUser() {
        SubscriptionDto subscriptionDto = new SubscriptionDto();
        subscriptionDto.setUsername("test username");
        subscriptionDto.setPassword("secret");

        User mockUser = User.builder().setUsername("test username").build();

        when(userRepository.save(any(User.class))).thenReturn(mockUser);
        User created = userService.createUser(subscriptionDto);

        assertEquals(subscriptionDto.getUsername(), created.getUsername());
    }

    @Test
    public void whenActivateThenReturnActivatedUser() {
        User mockUser = User.builder().setActive(true).build();

        when(userRepository.save(any(User.class))).thenReturn(mockUser);
        User activated = userService.activateUser(mockUser);

        assertTrue(activated.getActive());
    }
}
