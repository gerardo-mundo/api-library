package mundo.org.apilibrary.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import mundo.org.apilibrary.DTO.users.UserCreationDTO;
import mundo.org.apilibrary.DTO.users.UserDTO;
import mundo.org.apilibrary.classes.User;
import mundo.org.apilibrary.enums.Role;
import mundo.org.apilibrary.mapper.UserMapper;
import mundo.org.apilibrary.repository.UserRepository;
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

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
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
        return userMapper.toDto(userRepository.save(userMapper.toEntity(creationDTO)));
    }

    @Transactional
    public UserDTO updateUser(@NotNull(message = "A body is required") UserCreationDTO creationDTO, UUID id) {
        User existingUser = userRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("User with id: " + id + " not found"));

        if (userRepository.existsUserByEmailAndIdNot(creationDTO.email(), id))
            throw new EntityNotFoundException("User with ID: " + id + " not found, and the email already exists");

        userMapper.updateEntity(creationDTO, existingUser);
        User updatedUser = userRepository.saveAndFlush(existingUser);

        return userMapper.toDto(updatedUser);
    }

    @Transactional
    public UserDTO updateUserRola(UUID id, Role role) {
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
