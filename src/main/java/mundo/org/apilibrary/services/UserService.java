package mundo.org.apilibrary.services;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import mundo.org.apilibrary.DTO.users.UserCreationDTO;
import mundo.org.apilibrary.DTO.users.UserDTO;
import mundo.org.apilibrary.DTO.users.UserUpdateDTO;
import mundo.org.apilibrary.DTO.users.UserUpdatePasswordDTO;
import mundo.org.apilibrary.classes.User;
import mundo.org.apilibrary.enums.Role;
import mundo.org.apilibrary.mapper.UserMapper;
import mundo.org.apilibrary.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserDTO> findAllUsers() {
        List<User> users = userRepository.findAll();

        if (users.isEmpty()) throw new EntityNotFoundException("List of users not found");

        return userMapper.toListDto(users);
    }

    public UserDTO findUserByEmail(String email) {
        return userRepository.findByEmail(email).map(userMapper::toDto).orElseThrow(
                () -> new EntityNotFoundException("User with email: " + email + " not found"));
    }

    public List<UserDTO> findUsersByRole(String role) {
        Role parsedRole = Role.valueOf("ROLE_" + role.trim().toUpperCase());
        return userRepository.findByRole(parsedRole).stream().map(userMapper::toDto).toList();
    }

    public List<UserDTO> findUsersByIsActive(boolean isActive) {
        return userRepository.findByIsActive(isActive).stream().map(userMapper::toDto).toList();
    }

    @Transactional
    public UserDTO createUser(@NotNull(message = "A body is required") UserCreationDTO creationDTO) {
        if (userRepository.findByEmail(creationDTO.email()).isPresent()) throw new
                EntityExistsException("User with email: " + creationDTO.email() + " already exists");

        User user = userMapper.toEntity(creationDTO);
        user.setPassword(passwordEncoder.encode(creationDTO.password()));
        return userMapper.toDto(userRepository.save(user));
    }

    @Transactional
    public UserDTO updateUser(UserUpdateDTO creationDTO, UUID id) {
        User existingUser = userRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("User with id: " + id + " not found"));

        if (userRepository.existsUserByEmailAndIdNot(creationDTO.email(), id))
            throw new EntityNotFoundException("User with ID: " + id + " not found, and the email already exists");

        userMapper.updateEntity(creationDTO, existingUser);
        return userMapper.toDto(userRepository.saveAndFlush(existingUser));
    }

    @Transactional
    public UserDTO updateUserPassword(@NotNull(message = "A body is required") UserUpdatePasswordDTO dto, UUID id) {
        User existingUser = userRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("User with id: " + id + " not found"));

        existingUser.setPassword(passwordEncoder.encode(dto.newPassword()));
        return userMapper.toDto(userRepository.save(existingUser));
    }

    @Transactional
    public UserDTO updateUserRole(UUID id, Role role) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty())
            throw new EntityNotFoundException("User with ID: " + id + " not found");

        user.get().setRole(role);
        return userMapper.toDto(userRepository.save(user.get()));
    }

    @Transactional
    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id))
            throw new EntityNotFoundException("User with ID: " + id + " not found");

        userRepository.deleteById(id);
    }
}
