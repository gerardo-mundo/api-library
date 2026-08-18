package mundo.org.apilibrary.services;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import mundo.org.apilibrary.DTO.users.UserCreationDTO;
import mundo.org.apilibrary.DTO.users.UserDTO;
import mundo.org.apilibrary.DTO.users.UserUpdateDTO;
import mundo.org.apilibrary.DTO.users.UserUpdatePasswordDTO;
import mundo.org.apilibrary.classes.User;
import mundo.org.apilibrary.entities.Employee;
import mundo.org.apilibrary.entities.Student;
import mundo.org.apilibrary.enums.Role;
import mundo.org.apilibrary.filters.SpecificationFilters;
import mundo.org.apilibrary.mapper.UserMapper;
import mundo.org.apilibrary.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private SpecificationFilters<User> specificationFilters;

    @InjectMocks
    private UserService userService;

    private UUID userId = UUID.randomUUID();
    private User mockUser;
    private Employee employee;
    private Student student;
    private UserCreationDTO userCreationDTO;
    private UserUpdateDTO userUpdateDTO;
    private UserUpdatePasswordDTO updatePasswordDTO;
    private UserDTO userDTO;
    private String email;
    private String password;

    @BeforeEach
    public void setUp() {
        mockUser = mock(User.class);
        userId = UUID.randomUUID();
        email = "test@mail.com";
        password = "Abcd@1234";
        employee = new Employee();
        student = new Student();
        updatePasswordDTO = new UserUpdatePasswordDTO(password);

        userCreationDTO = new UserCreationDTO(
                null,
                email,
                password,
                null,
                null,
                null
        );
        userUpdateDTO = new UserUpdateDTO(
                null,
                email,
                true,
                Role.ROLE_ADMIN,
                null,
                null
        );
        userDTO = new UserDTO(
                userId,
                "Admin Test",
                email,
                true,
                null,
                null,
                null
        );
    }

    @Test
    void findAllUsers_shouldReturnListOfUsers_whenSuccess() {
        List<User> users = List.of(employee, student);
        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.toListDto(users)).thenReturn(List.of(userDTO));

        List<UserDTO> userDTOs = userService.findAllUsers();

        assertNotNull(userDTOs);
        verify(userMapper, times(1)).toListDto(users);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void findUserByEmail_shouldReturnException_whenEmailNotExists() {
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> userService.findUserByEmail(email));

        assert (exception.getMessage().equals("User with email: " + email + " not found"));
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void createUser_shouldReturnException_whenEmailExists() {
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(employee));
        EntityExistsException exception = assertThrows(EntityExistsException.class,
                () -> userService.createUser(userCreationDTO));

        assert (exception.getMessage().equals("User with email: " + email + " already exists"));
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void createUser_shouldReturnUserDto_whenSuccess() {
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(userMapper.toEntity(userCreationDTO)).thenReturn(mockUser);
        when(passwordEncoder.encode(anyString())).thenReturn("hashed_password_123");
        when(userRepository.save(any(User.class))).thenReturn(mockUser);
        when(userMapper.toDto(any(User.class))).thenReturn(userDTO);

        UserDTO result = userService.createUser(userCreationDTO);

        assertNotNull(result);
        assertEquals(userDTO, result);
        verify(userMapper, times(1)).toEntity(userCreationDTO);
        verify(userRepository, times(1)).save(mockUser);
    }

    @Test
    void updateUser_shouldReturnException_whenUserNotExists() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> userService.updateUser(userUpdateDTO, userId));

        assertEquals("User with id: " + userId + " not found", exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).save(mockUser);
    }

    @Test
    void updateUser_shouldReturnException_whenUserEmailExists() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(userRepository.existsUserByEmailAndIdNot(email, userId)).thenReturn(true);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> userService.updateUser(userUpdateDTO, userId));

        assertEquals("User with ID: " + userId + " not found, and the email already exists",
                exception.getMessage());
        verify(userRepository, times(1)).existsUserByEmailAndIdNot(email, userId);
        verify(userRepository, never()).save(mockUser);
    }

    @Test
    void updateUser_shouldReturnUserDto_whenSuccess() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(userRepository.existsUserByEmailAndIdNot(email, userId)).thenReturn(false);
        doNothing().when(userMapper).updateEntity(userUpdateDTO, mockUser);
        when(userRepository.saveAndFlush(any(User.class))).thenReturn(mockUser);
        when(userMapper.toDto(any(User.class))).thenReturn(userDTO);

        UserDTO result = userService.updateUser(userUpdateDTO, userId);

        assertNotNull(result);
        assertEquals(userDTO, result);
        verify(userMapper, times(1)).toDto(mockUser);
        verify(userRepository, times(1)).saveAndFlush(mockUser);
    }

    @Test
    void updateUserPassword_shouldReturnException_whenUserNotExists() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> userService.updateUserPassword(updatePasswordDTO, userId));

        assertEquals("User with id: " + userId + " not found", exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).save(mockUser);
    }

    @Test
    void updatePassword_shouldReturnsUserDto_whenUserEmailExists() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.encode(password)).thenReturn("hashed_password_123");
        when(userRepository.save(any(User.class))).thenReturn(mockUser);
        when(userMapper.toDto(any(User.class))).thenReturn(userDTO);

        UserDTO result = userService.updateUserPassword(updatePasswordDTO, userId);

        assertNotNull(result);
        assertEquals(userDTO, result);
        verify(userMapper, times(1)).toDto(any(User.class));
        verify(userRepository, times(1)).save(any(User.class));

    }

    @Test
    void deleteUser_shouldReturnException_whenUserNotExists() {
        when(userRepository.existsById(userId)).thenReturn(false);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> userService.deleteUser(userId));

        assertEquals("User with ID: " + userId + " not found", exception.getMessage());
        verify(userRepository, never()).delete(mockUser);
    }

    @Test
    void deleteUser_shouldDeleteUser_whenSuccess() {
        when(userRepository.existsById(userId)).thenReturn(true);
        userService.deleteUser(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void findAllUsers_shouldReturnPageDto_whenSuccess() {
        Map<String, String> filters = Map.of("name", "Admin");
        List<String> allowedExpectedTerms = List.of("name", "email");
        Pageable pageable = PageRequest.of(0, 10);

        mockUser.setId(userId);
        mockUser.setName("Admin");
        Page<User> page = new PageImpl<>(List.of(mockUser));

        @SuppressWarnings("unchecked")
        Specification<User> mockSpec = mock(Specification.class);

        when(specificationFilters.buildSpecification(filters, allowedExpectedTerms)).thenReturn(mockSpec);
        when(userRepository.findAll(eq(mockSpec), eq(pageable))).thenReturn(page);
        when(userMapper.toDto(any(User.class))).thenReturn(userDTO);

        Page<UserDTO> result = userService.findUsers(filters, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Admin Test", result.getContent().get(0).name());

        verify(specificationFilters, times(1)).buildSpecification(filters, allowedExpectedTerms);
        verify(userRepository, times(1)).findAll(mockSpec, pageable);
    }

}
