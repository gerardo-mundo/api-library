package mundo.org.apilibrary.controllers;

import jakarta.validation.Valid;
import mundo.org.apilibrary.DTO.auth.AuthResponseDTO;
import mundo.org.apilibrary.DTO.auth.UserCredentialsDTO;
import mundo.org.apilibrary.payload.ApiResponse;
import mundo.org.apilibrary.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/auth")
public class AuthController {
    public final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> login(@Valid @RequestBody UserCredentialsDTO credentials) {
        return ResponseEntity.ok(ApiResponse.success(authService.login(credentials), "Logged successfully"));
    }
}
