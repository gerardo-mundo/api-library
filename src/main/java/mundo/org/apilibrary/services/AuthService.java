package mundo.org.apilibrary.services;

import jakarta.persistence.EntityNotFoundException;

import mundo.org.apilibrary.DTO.auth.AuthResponseDTO;
import mundo.org.apilibrary.DTO.auth.UserCredentialsDTO;
import mundo.org.apilibrary.classes.User;
import mundo.org.apilibrary.repository.UserRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AuthService {
    @Value("${security.jwt.expiration-time}")
    private static Long EXPIRATION_DATE;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;


    public AuthService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponseDTO login(UserCredentialsDTO credentials) {
        Optional<User> user = userRepository.findByEmail(credentials.email());
        if (user.isEmpty()) throw new EntityNotFoundException("User with email " + credentials.email() + " not found");


        if (!areValidUserCredentials(user, credentials))
            throw new IllegalArgumentException("Wrong email or password");

        String userToken = jwtService.generateToken(user.get());
        return new AuthResponseDTO(userToken, EXPIRATION_DATE);
    }

    private boolean areValidUserCredentials(Optional<User> user, UserCredentialsDTO credentials) {
        return passwordEncoder.matches(credentials.password(), user.get().getPassword())
                && user.get().getEmail().equals(credentials.email());
    }
}
