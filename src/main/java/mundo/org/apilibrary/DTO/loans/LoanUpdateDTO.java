package mundo.org.apilibrary.DTO.loans;

import jakarta.validation.constraints.NotNull;
import mundo.org.apilibrary.enums.LoanStatus;

import java.time.LocalDateTime;

public record LoanUpdateDTO(
        @NotNull
        boolean active,
        @NotNull(message = "You should pick at least one status type")
        LoanStatus status,
        LocalDateTime returnDate
) {
}
