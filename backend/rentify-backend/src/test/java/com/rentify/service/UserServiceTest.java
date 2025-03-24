package com.rentify.service;

import com.rentify.model.Role;
import com.rentify.model.Users;
import com.rentify.repository.RoleRepo;
import com.rentify.repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private RoleRepo roleRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleService roleService;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterNewUser_AsOwner_Success() {
        // Arrange
        Users newUser = new Users();
        newUser.setUserEmail("test@example.com");
        newUser.setUserPassword("plaintext123");

        Role ownerRole = new Role();
        ownerRole.setRole("Owner");

        when(roleRepo.findById("Owner")).thenReturn(Optional.of(ownerRole));
        when(passwordEncoder.encode("plaintext123")).thenReturn("encoded_pass");
        when(userRepo.save(any(Users.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Users result = userService.registerNewUser(newUser);

        // Assert
        assertNotNull(result);
        assertEquals("encoded_pass", result.getUserPassword());
        assertTrue(result.getRoles().contains(ownerRole));
        verify(userRepo, times(1)).save(result);
    }
}
