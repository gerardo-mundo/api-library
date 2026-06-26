package mundo.org.apilibrary.DTO.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserCredentialsDTO(
        @Email @NotBlank String email,
        @NotBlank String password
) {
}
