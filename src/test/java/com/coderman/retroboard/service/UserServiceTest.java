package com.coderman.retroboard.service;

import com.coderman.retroboard.domain.User;
import com.coderman.retroboard.repo.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.*;

/**
 * @author Shazin Sadakath
 */
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    private UserService userService;

    @Before
    public void init() {
        this.userService = new UserService(userRepository);
    }

    @Test
    public void getAllCommentsForToday_HappyPath_ShouldReturn1Comment() {
        // Given
        User user = new User();
        user.setUsername("bekir");
        user.setPassword("bekir");
        user.setRole("USER");

        // userRepository.findByUsername method is mocked to return a given user and finally verify whether that method is invoked exactly once
        when(userRepository.findByUsername("bekir")).thenReturn(user);

        // When
        UserDetails actual = userService.loadUserByUsername("bekir");

        // Then
        verify(userRepository, times(1)).findByUsername("bekir");
    }

}