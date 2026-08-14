package mundo.org.apilibrary.controllers.v1;

import jakarta.validation.Valid;
import mundo.org.apilibrary.DTO.thesis.ThesisCreationDTO;
import mundo.org.apilibrary.DTO.thesis.ThesisDTO;
import mundo.org.apilibrary.payload.ApiResponse;
import mundo.org.apilibrary.services.ThesisService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController()
@RequestMapping("/api/thesis")
public class ThesisController {
    private final ThesisService thesisService;

    public ThesisController(ThesisService thesisService) {
        this.thesisService = thesisService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ThesisDTO>>> getAllThesis() {
        List<ThesisDTO> thesisList = thesisService.findAll();
        return ResponseEntity.ok(ApiResponse.success(thesisList, "List of thesis retrieved successfully"));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<ThesisDTO>>> listThesisBySearch(
            @RequestParam Map<String, String> params,
            @PageableDefault(size = 12, sort = "title") Pageable pageable) {
        return ResponseEntity.ok(ApiResponse
                .success(thesisService.findAllThesis(params, pageable), "Thesis retrieved successfully"));
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<ThesisDTO>> getThesisById(@PathVariable UUID id) {
        ThesisDTO thesis = thesisService.findById(id);
        return ResponseEntity.ok(ApiResponse.success(thesis, "Thesis retrieved successfully"));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    @PostMapping(path = "/create")
    public ResponseEntity<ApiResponse<ThesisDTO>> createThesis(@RequestBody @Valid ThesisCreationDTO thesisCreationDTO) {
        ThesisDTO thesisCreated = thesisService.createThesis(thesisCreationDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(thesisCreated, "Thesis created successfully"));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<ApiResponse<ThesisDTO>> updateThesis(@PathVariable UUID id,
                                                               @RequestBody @Valid ThesisCreationDTO thesisCreationDTO) {
        ThesisDTO thesisUpdated = thesisService.updateThesis(id,  thesisCreationDTO);
        return ResponseEntity.ok(ApiResponse.success(thesisUpdated, "Thesis updated successfully"));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteThesis(@PathVariable UUID id) {
        thesisService.deleteThesis(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Thesis removed successfully"));
    }
}
