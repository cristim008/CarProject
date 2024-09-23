package com.HomeworkCar.HomeworkCar.services;

import com.HomeworkCar.HomeworkCar.controller.dto.LoginRequestDto;
import com.HomeworkCar.HomeworkCar.controller.dto.UserDto;
import com.HomeworkCar.HomeworkCar.exceptions.EmailAlreadyUsedException;
import com.HomeworkCar.HomeworkCar.exceptions.InvalidPasswordException;
import com.HomeworkCar.HomeworkCar.exceptions.UserAlreadyExist;
import com.HomeworkCar.HomeworkCar.exceptions.UserNotFoundException;
import com.HomeworkCar.HomeworkCar.mappers.UserMapper;
import com.HomeworkCar.HomeworkCar.persistance.entities.User;
import com.HomeworkCar.HomeworkCar.repository.UserRepository;
import com.HomeworkCar.HomeworkCar.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class UserServiceTest {

    private UserService userService;
    private UserRepository mockedUserRepository;
    private UserMapper mockedUserMapper;

    @BeforeEach
    void setup() {
        mockedUserRepository = mock(UserRepository.class);
        mockedUserMapper = mock(UserMapper.class);
        userService = new UserService(mockedUserRepository, mockedUserMapper);
    }

    @Test
    void registerUser_UserDoesNotExist_SaveUser() {

        //Given
        User user = new User();
        user.setId(1L);
        user.setUsername("Cristi");
        user.setEmail("cristi123@gmail.com");
        user.setPassword("password123");

        UserDto userDto = new UserDto(1L, "Cristi", "cristi123@gmail.com", "password123", 0);

        //When
        when(mockedUserRepository.existsByUsername(userDto.getUsername())).thenReturn(false);
        when(mockedUserRepository.existsByEmail(userDto.getEmail())).thenReturn(false);
        when(mockedUserMapper.toUserEntity(userDto)).thenReturn(user);

        userService.registerUser(userDto);

        //Then

        verify(mockedUserRepository, times(1)).save(user);
    }

    @Test
    void registerUser_UserAlreadyExist_ThrowUserAlreadyExistException() {
        //Given
        UserDto userDto = new UserDto(1L,
                "Cristi",
                "cristi123@gmail.com",
                "password123",
                0);

        //When
        when(mockedUserRepository.existsByUsername(userDto.getUsername())).thenReturn(true);

        //Then
        assertThrows(UserAlreadyExist.class, () -> userService.registerUser(userDto));
    }

    @Test
    void registerUser_EmailAlreadyExist_ThrowEmailAlreadyExistException() {
        //Given
        UserDto userDto = new UserDto(1L,
                "Cristi",
                "cristi123@gmail.com",
                "password123",
                0);

        //When
        when(mockedUserRepository.existsByEmail(userDto.getEmail())).thenReturn(true);

        //Then
        assertThrows(EmailAlreadyUsedException.class, () -> userService.registerUser(userDto));
    }

    @Test
    void loginUser_ValidCredentials_ReturnUserDto() {
        //Given

        LoginRequestDto loginRequestDto = new LoginRequestDto("cristi233@gmail.com", "password123");

        User user = new User();
        user.setEmail("cristi113@gmail.com");
        user.setPassword("password123");

        UserDto userDto = new UserDto(1L,
                "Cristi",
                "cristi133@gmail.com",
                "password123",
                0);

        //When
        when(mockedUserRepository.findByEmail(loginRequestDto.getEmail())).thenReturn(Optional.of(user));
        when(mockedUserMapper.toUserDto(user)).thenReturn(userDto);

        //Then
        UserDto result = userService.loginUser(loginRequestDto);
        assertEquals(userDto, result);
        verify(mockedUserRepository).findByEmail(loginRequestDto.getEmail());
        verify(mockedUserMapper).toUserDto(user);
    }

    @Test
    void loginUser_UserNotFound_ThrowsUserNotFoundException() {
        //Given
        LoginRequestDto loginRequestDto = new LoginRequestDto("cristi233@gmail.com", "password123");

        //When
        when(mockedUserRepository.findByEmail(loginRequestDto.getEmail())).thenReturn(Optional.empty());

        //Then
        assertThrows(UserNotFoundException.class, () -> userService.loginUser(loginRequestDto));
        verify(mockedUserRepository).findByEmail(loginRequestDto.getEmail());
    }

    @Test
    void loginUser_InvalidPassword_ThrowsInvalidPasswordException() {
        //Given
        LoginRequestDto loginRequestDto = new LoginRequestDto("cristi113@gmail.com", "password1223");

        User user = new User();
        user.setEmail("cristi113@gmail.com");
        user.setPassword("password123");

        //When
        when(mockedUserRepository.findByEmail(loginRequestDto.getEmail())).thenReturn(Optional.of(user));

        //Then
        assertThrows(InvalidPasswordException.class, () -> userService.loginUser(loginRequestDto));
        verify(mockedUserRepository).findByEmail(loginRequestDto.getEmail());
    }

    @Test
    void getAllUsers() {
        // Given
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("User1");
        user1.setEmail("user1@example.com");
        user1.setPassword("password1");

        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("User2");
        user2.setEmail("user2@example.com");
        user2.setPassword("password2");

        // When
        List<User> mockUsers = List.of(user1, user2);
        when(mockedUserRepository.findAll()).thenReturn(mockUsers);

        UserDto userDto1 = new UserDto(1L,
                "User5",
                "user1@example.com",
                "password1",
                0);
        UserDto userDto2 = new UserDto(2L,
                "User2",
                "user2@example.com",
                "password2",
                0);
        when(mockedUserMapper.toUserDto(user1)).thenReturn(userDto1);
        when(mockedUserMapper.toUserDto(user2)).thenReturn(userDto2);

        List<UserDto> result = userService.getAllUsers(null);

        // Then
        assertEquals(2, result.size());
        assertEquals(userDto1, result.get(0));
        assertEquals(userDto2, result.get(1));
        verify(mockedUserRepository).findAll();
    }

    @Test
    void getUserEntityById() {
        //Given
        User user = new User();
        user.setId(1L);
        user.setUsername("Cristi");
        user.setEmail("cristi123@gmail.com");
        user.setPassword("password123");
        //When
        when(mockedUserRepository.findById(1L)).thenReturn(Optional.of(user));

        //Then
        User resultedUser = userService.getUserEntityById(1L);
        assertNotNull(resultedUser, "The list must have one element");
        assertEquals("Cristi", resultedUser.getUsername());
        assertEquals("cristi123@gmail.com", resultedUser.getEmail());
        assertEquals("password123", resultedUser.getPassword());
    }

    @Test
    void getUserById() {
        // Given
        long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setUsername("Cristi");
        user.setEmail("cristi123@gmail.com");
        user.setPassword("password123");

        UserDto userDto = new UserDto(userId,
                "Cristi",
                "cristi123@gmail.com",
                "password123",
                0);

        when(mockedUserRepository.findById(userId)).thenReturn(Optional.of(user));
        when(mockedUserMapper.toUserDto(user)).thenReturn(userDto);

        // When
        UserDto result = userService.getUserById(userId);

        // Then
        assertEquals(userDto, result);
        verify(mockedUserRepository).findById(userId);
    }

    @Test
    void getUserById_InvalidId_ThrowsUserNotFoundException() {
        // Given
        long invalidUserId = 999L;

        //When
        when(mockedUserRepository.findById(invalidUserId)).thenReturn(Optional.empty());

        // Then
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(invalidUserId));
        verify(mockedUserRepository).findById(invalidUserId);
    }

    @Test
    void findByUsername_ValidUsername_ReturnsUser() {
        // Given
        String username = "Cristi";
        User user = new User();
        user.setId(1L);
        user.setUsername(username);
        user.setEmail("cristi123@gmail.com");
        user.setPassword("password123");

        when(mockedUserRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // When
        User result = userService.findByUsername(username);

        // Then
        assertEquals(user, result);
        verify(mockedUserRepository).findByUsername(username);
    }

    @Test
    void findByUsername_InvalidUsername_ThrowsUserNotFoundException() {
        // Given
        String invalidUsername = "UnknownUser";
        when(mockedUserRepository.findByUsername(invalidUsername)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(UserNotFoundException.class, () -> userService.findByUsername(invalidUsername));
        verify(mockedUserRepository).findByUsername(invalidUsername);
    }
}
