package mundo.org.apilibrary.DTO.users;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserUpdatePasswordDTO(
        @NotBlank(message = "A password is required")
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$#!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = "Password must be at least 8 characters long, contain an uppercase letter, a number, and a special character"
        )
        String newPassword
) {
}
