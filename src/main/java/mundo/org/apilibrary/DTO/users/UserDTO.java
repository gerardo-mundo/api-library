package mundo.org.apilibrary.DTO.users;

import mundo.org.apilibrary.enums.Role;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserDTO(
        UUID id,
        String name,
        String email,
        String password,
        Role role,
        boolean isActive,
        LocalDateTime lastLogin
) {
}
