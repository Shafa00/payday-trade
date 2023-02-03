package com.task.paydaytrade.service.impl;

import com.task.paydaytrade.entity.Role;
import com.task.paydaytrade.exception.DataNotFoundException;
import com.task.paydaytrade.exception.DuplicateUserException;
import com.task.paydaytrade.repository.RoleRepository;
import com.task.paydaytrade.repository.UserRepository;
import com.task.paydaytrade.utility.MailSenderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.mail.MessagingException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplTest {
    @Mock
    UserRepository userRepository;

    @Mock
    RoleRepository roleRepository;

    @Mock
    MailSenderService mailSenderService;

    @Mock
    BCryptPasswordEncoder encoder;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    void shouldThrowExceptionDuplicateUser() {
        when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(MockData.getUser()));

        assertThrows(DuplicateUserException.class, () -> userService.signup(MockData.getSignupRqModel()));
        verify(userRepository, times(1)).findByEmail(any());
    }

    @Test
    void shouldThrowExceptionRoleNotFound() {
        when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.empty());
        when(roleRepository.findByName(any())).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> userService.signup(MockData.getSignupRqModel()));
        verify(userRepository, times(1)).findByEmail(any());
        verify(roleRepository, times(1)).findByName(any());
    }

    @Test
    void shouldBeSuccessfullySignup() throws MessagingException {
        when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.empty());
        when(roleRepository.findByName(any())).thenReturn(Optional.of(Role.builder().build()));

        var signupRsModel = userService.signup(MockData.getSignupRqModel());
        Assertions.assertEquals(signupRsModel.email(), MockData.getSignupRqModel().email());
        verify(userRepository, times(1)).findByEmail(any());
        verify(roleRepository, times(1)).findByName(any());
        verify(userRepository, times(1)).save(any());
        verify(mailSenderService, times(1)).sendEmail(any(), any(), any());
    }

    @Test
    void shouldFindAuthModelByEmail() {
        when(userRepository.findByEmailAndIsActiveTrue("test@gmail.com")).thenReturn(Optional.of(MockData.getUser()));

        userService.findAuthModelByEmail("test");
        verify(userRepository, times(1)).findByEmailAndIsActiveTrue(any());
    }

    @Test
    void shouldFindById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(MockData.getUser()));

        userService.findById(1L);
        verify(userRepository, times(1)).findById(any());
    }

    @Test
    void shouldFindByEmail() {
        when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(MockData.getUser()));

        userService.findByEmail("test@gmail.com");
        verify(userRepository, times(1)).findByEmail(any());
    }

    @Test
    void shouldFindByEmailThrowException() {
        when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> userService.findByEmail("test@gmail.com"));
        verify(userRepository, times(1)).findByEmail(any());
    }

    @Test
    void shouldActivateUser() {
        when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(MockData.getUser()));

        userService.activateUser("test@gmail.com");
        verify(userRepository, times(1)).findByEmail(any());
        verify(userRepository, times(1)).save(any());
    }

}