package com.Task.MiniProject_2.serviceTest;
import com.Task.MiniProject_2.entity.securityEntity.User;
import com.Task.MiniProject_2.repository.UserRepository;
import com.Task.MiniProject_2.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    public void testRegisterUser_UserAlreadyExists() {
        String username = "testUser";
        String email = "test@example.com";
        String password = "password";

        when(userRepository.findByUsernameOrEmail(username, email)).thenReturn(Optional.of(new User()));

        assertThrows(IllegalArgumentException.class, () -> userService.registerUser(username, email, password));
    }

    @Test
    public void testRegisterUser_Success() {
        String username = "testUser";
        String email = "test@example.com";
        String password = "password";
        String encodedPassword = "encodedPassword";

        when(userRepository.findByUsernameOrEmail(username, email)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);

        User savedUser = new User();
        savedUser.setUsername(username);
        savedUser.setEmail(email);
        savedUser.setPassword(encodedPassword);
        savedUser.setRole("ROLE_PATIENT");

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        userService.registerUser(username, email, password);

        verify(userRepository, times(1)).save(any(User.class));
        verify(passwordEncoder, times(1)).encode(password);
        assertEquals("ROLE_PATIENT", savedUser.getRole());
    }
}
