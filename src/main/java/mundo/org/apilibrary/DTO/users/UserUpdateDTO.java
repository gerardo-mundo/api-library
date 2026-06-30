package mundo.org.apilibrary.DTO.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import mundo.org.apilibrary.enums.Role;

public record UserUpdateDTO(
        @NotBlank(message = "Value name is required")
        String name,
        @Email(message = "The value is not a valid email")
        String email,
        @NotNull(message = "The active value is required")
        boolean isActive,
        @NotNull(message = "A valid role value is required")
        Role role,
        String enrollmentId,
        String employeeKey
) {
}
