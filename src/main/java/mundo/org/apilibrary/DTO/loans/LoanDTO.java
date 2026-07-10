package mundo.org.apilibrary.DTO.loans;

import mundo.org.apilibrary.DTO.users.UserDTO;
import mundo.org.apilibrary.entities.Book;
import mundo.org.apilibrary.enums.LoanStatus;

import java.time.LocalDateTime;

import java.util.List;
import java.util.UUID;

public record LoanDTO(
        UUID id,
        UserDTO approver,
        UserDTO borrower,
        List<Book> borrowedBooks,
        boolean active,
        LoanStatus status,
        LocalDateTime borrowDate,
        LocalDateTime returnDate
) {
}
