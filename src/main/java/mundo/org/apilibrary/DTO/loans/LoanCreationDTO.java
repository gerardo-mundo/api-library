package mundo.org.apilibrary.DTO.loans;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;


public record LoanCreationDTO(
        @NotNull(message = "The approver Id is required")
        UUID approver,
        @NotNull(message = "The borrower Id is required")
        UUID borrower,
        @NotEmpty(message = "You should add at least one book")
        @Size(min = 1, max = 3, message = "The numbers of books must be between 1 and 3 books") List<UUID> borrowedBooks
) {
}
