package mundo.org.apilibrary.controllers;

import jakarta.validation.Valid;
import mundo.org.apilibrary.DTO.loans.LoanCreationDTO;
import mundo.org.apilibrary.DTO.loans.LoanDTO;
import mundo.org.apilibrary.DTO.loans.LoanUpdateDTO;
import mundo.org.apilibrary.payload.ApiResponse;
import mundo.org.apilibrary.services.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/loans")
public class LoanController {
    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public ResponseEntity<ApiResponse<List<LoanDTO>>> getLoans() {
        return ResponseEntity.ok(ApiResponse.success(loanService.loanDTOList(), "Loan list retrieve"));
    }

    @GetMapping
    @RequestMapping("/approver/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public ResponseEntity<ApiResponse<List<LoanDTO>>> getLoansByApprover(@PathVariable @Valid UUID id) {
        return ResponseEntity
                .ok(ApiResponse.success(loanService.findByApprover(id), "Loan approvers list retrieve"));
    }

    @GetMapping
    @RequestMapping("/borrower/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public ResponseEntity<ApiResponse<List<LoanDTO>>> getLoansByBorrower(@PathVariable @Valid UUID id) {
        return ResponseEntity
                .ok(ApiResponse.success(loanService.findByBorrower(id), "Loan borrowers list retrieve"));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public ResponseEntity<ApiResponse<LoanDTO>> createLoan(@RequestBody @Valid LoanCreationDTO loanDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(loanService.createLoan(loanDTO), "Loan created successfully"));
    }

    @PutMapping
    @RequestMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public ResponseEntity<ApiResponse<LoanDTO>> updateLoan(@PathVariable @Valid UUID id,
                                                           @RequestBody @Valid LoanUpdateDTO loanDTO) {
        return ResponseEntity
                .ok(ApiResponse.success(loanService.updateLoan(id, loanDTO), "Loan updated successfully"));
    }
}
