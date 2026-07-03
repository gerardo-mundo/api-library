package mundo.org.apilibrary.DTO.auth;

public record AuthResponseDTO(
        String token,
        Long expiresIn
) {
}
