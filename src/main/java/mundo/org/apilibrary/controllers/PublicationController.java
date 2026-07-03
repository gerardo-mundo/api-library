package mundo.org.apilibrary.controllers;

import jakarta.validation.Valid;

import mundo.org.apilibrary.DTO.publications.PublicationCreationDTO;
import mundo.org.apilibrary.DTO.publications.PublicationDTO;
import mundo.org.apilibrary.payload.ApiResponse;
import mundo.org.apilibrary.services.PublicationService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/publications")
public class PublicationController {
    private final PublicationService publicationService;


    public PublicationController(PublicationService publicationService) {
        this.publicationService = publicationService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PublicationDTO>>> findAll() {
        return ResponseEntity.ok(
                ApiResponse.success(
                        publicationService.findAllPublications(),
                        "List retrieved"
                )
        );
    }

    @GetMapping("/issn/{issn}")
    public ResponseEntity<ApiResponse<PublicationDTO>> findOne(@PathVariable String issn) {
        return ResponseEntity.ok(
                ApiResponse.success(publicationService.getPublicationByIssn(issn), "Publication retrieved successfully")
        );
    }

    @GetMapping("/publisher/{publisher}")
    public ResponseEntity<ApiResponse<List<PublicationDTO>>> findByPublisher(@PathVariable String publisher) {
        return ResponseEntity.ok(
                ApiResponse.success(publicationService.findByPublisher(publisher), "List retrieved successfully")
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    @PostMapping
    public ResponseEntity<ApiResponse<PublicationDTO>> save(@RequestBody @Valid PublicationCreationDTO publicationCreationDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        publicationService.createPublication(publicationCreationDTO), "Publication created successfully")
                );
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PublicationDTO>> update(
            @RequestBody @Valid PublicationCreationDTO publicationCreationDTO, @PathVariable UUID id) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        publicationService.updatePublication(publicationCreationDTO, id), "Publication updated successfully"));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
        publicationService.deletePublication(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Publication deleted successfully"));
    }
}
