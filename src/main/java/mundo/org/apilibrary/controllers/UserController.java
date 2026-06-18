package mundo.org.apilibrary.controllers;

import jakarta.validation.Valid;

import mundo.org.apilibrary.DTO.users.UserCreationDTO;
import mundo.org.apilibrary.DTO.users.UserDTO;
import mundo.org.apilibrary.payload.ApiResponse;
import mundo.org.apilibrary.services.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDTO>>> findAllUsers() {
        return ResponseEntity.ok(ApiResponse
                .success(userService.findAllUsers(), "Users lists retrieved"));
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<ApiResponse<List<UserDTO>>> findAllUsersByRole(@PathVariable @Valid String role) {
        return ResponseEntity.ok(
                ApiResponse.success(userService.findUsersByRole(role), "Users roles retrieved"));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<UserDTO>> findUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(
                ApiResponse.success(userService.findUserByEmail(email), "User retrieved"));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<UserDTO>>> findUseByStatus(@PathVariable boolean status) {
        return ResponseEntity.ok(ApiResponse
                .success(userService.findUsersByIsActive(status), "User retrieved"));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserDTO>> createUser(@RequestBody @Valid UserCreationDTO userDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(userService.createUser(userDTO), "User created successfully"));
    }

    //TODO: fix the assignment of an enrollmentId to an Employee entity and vice-versa
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> updateUser(@RequestBody @Valid UserCreationDTO userDTO,
                                                           @PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success(userService
                .updateUser(userDTO, id), "User updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(ApiResponse
                .success(null, "User deleted successfully"));
    }
}
