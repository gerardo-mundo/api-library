package mundo.org.apilibrary.DTO.users;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserDTO(
        UUID id,
        String name,
        String email,
        boolean isActive,
        LocalDateTime lastLogin,
        String enrollmentId,
        String employeeKey
) {
}
