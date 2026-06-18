package mundo.org.apilibrary.DTO.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import mundo.org.apilibrary.enums.Role;

public record UserCreationDTO(
        @NotBlank(message = "Value name is required")
        String name,
        @Email(message = "The value is not a valid email")
        String email,
        @NotBlank(message = "A password is required")
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$#!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = "Password must be at least 8 characters long, contain an uppercase letter, a number, and a special character"
        )
        String password,
        @NotNull(message = "A valid role value is required")
        Role role,
        String enrollmentId,
        String employeeKey
) {
}
