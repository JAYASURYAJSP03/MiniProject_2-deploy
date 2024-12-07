package com.Task.MiniProject_2.controllerTest;

import com.Task.MiniProject_2.service.UserService;
import com.Task.MiniProject_2.controller.UserController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @InjectMocks
    private UserController userController;

    @Test
    public void testRegisterUser_Success() {
        // Arrange
        String username = "testUser";
        String email = "test@example.com";
        String password = "password";

        // Act
        String viewName = userController.registerUser(username, email, password, model);

        // Assert
        verify(userService, times(1)).registerUser(username, email, password);
        assertEquals("redirect:/login", viewName);
    }

    @Test
    public void testRegisterUser_UserAlreadyExists() {
        // Arrange
        String username = "testUser";
        String email = "test@example.com";
        String password = "password";

        // Simulate exception thrown by userService when user already exists
        doThrow(new IllegalArgumentException("Username or Email already exists"))
                .when(userService).registerUser(username, email, password);

        // Act
        String viewName = userController.registerUser(username, email, password, model);

        // Assert
        verify(userService, times(1)).registerUser(username, email, password);
        verify(model, times(1)).addAttribute("error", "Username or Email already exists");
        assertEquals("register", viewName);
    }
}
