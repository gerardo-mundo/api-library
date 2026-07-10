package mundo.org.apilibrary.DTO.loans;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import mundo.org.apilibrary.enums.LoanStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record LoanUpdateDTO(
        @NotEmpty(message = "You should add at least one book and max of three books")
        List<UUID> borrowedBooks,
        @NotNull
        boolean active,
        @NotNull(message = "You should pick at least one status type")
        LoanStatus status,
        LocalDateTime returnDate
) {
}
