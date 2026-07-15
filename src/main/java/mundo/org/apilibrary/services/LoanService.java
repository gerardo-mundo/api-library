package mundo.org.apilibrary.services;

import jakarta.persistence.EntityNotFoundException;

import mundo.org.apilibrary.DTO.loans.LoanCreationDTO;
import mundo.org.apilibrary.DTO.loans.LoanDTO;
import mundo.org.apilibrary.DTO.loans.LoanUpdateDTO;
import mundo.org.apilibrary.classes.User;
import mundo.org.apilibrary.entities.Book;
import mundo.org.apilibrary.entities.Employee;
import mundo.org.apilibrary.entities.Loan;
import mundo.org.apilibrary.entities.Student;
import mundo.org.apilibrary.enums.LoanStatus;
import mundo.org.apilibrary.mapper.LoanMapper;
import mundo.org.apilibrary.repository.BookRepository;
import mundo.org.apilibrary.repository.LoanRepository;
import mundo.org.apilibrary.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class LoanService {
    private final LoanRepository loanRepository;
    private final LoanMapper loanMapper;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public LoanService(LoanRepository loanRepository, LoanMapper loanMapper, UserRepository userRepository, BookRepository bookRepository) {
        this.loanRepository = loanRepository;
        this.loanMapper = loanMapper;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    private static Loan getLoan(User approverUser, User borrowerUser, List<Book> borrowedBooks) {
        if (!(approverUser instanceof Employee approver))
            throw new IllegalArgumentException("The approver account is not valid for the operation.");
        if (!(borrowerUser instanceof Student borrower))
            throw new IllegalArgumentException("The borrower account is not valid for the operation.");

        Loan loan = new Loan();
        loan.setApprover(approver);
        loan.setBorrower(borrower);
        loan.setBorrowedBooks(borrowedBooks);
        loan.setStatus(LoanStatus.APPROVED);
        return loan;
    }

    public List<LoanDTO> loanDTOList() {
        List<Loan> loans = loanRepository.findAll();

        if (loans.isEmpty()) throw new EntityNotFoundException("No loans found");

        return loanMapper.toListDTO(loans);
    }

    public List<LoanDTO> findByApprover(UUID id) {
        return loanMapper.toListDTO(loanRepository.findAllByApproverId(id).stream().toList());
    }

    public List<LoanDTO> findByBorrower(UUID id) {
        return loanMapper.toListDTO(loanRepository.findAllByBorrowerId(id).stream().toList());
    }

    @Transactional
    public LoanDTO createLoan(LoanCreationDTO dto) {
        User approverUser = userRepository.findById(dto.approver())
                .orElseThrow(() -> new EntityNotFoundException("Borrower with ID: " + dto.approver() + " not found"));
        User borrowerUser = userRepository.findById(dto.borrower())
                .orElseThrow(() -> new EntityNotFoundException("Borrower with ID: " + dto.borrower() + " not found"));
        List<Book> borrowedBooks = bookRepository.findAllById(dto.borrowedBooks());

        if (borrowedBooks.size() != dto.borrowedBooks().size() || borrowedBooks.isEmpty())
            throw new EntityNotFoundException("One or more books doesn't exists");

        List<UUID> unavailableBooks = loanRepository.findUnavailableBooks(dto.borrowedBooks());

        if (!unavailableBooks.isEmpty())
            throw new IllegalStateException("The next books are unavailable: " + unavailableBooks);

        Loan loan = getLoan(approverUser, borrowerUser, borrowedBooks);

        return loanMapper.toDTO(loanRepository.save(loan));
    }

    @Transactional
    public LoanDTO updateLoan(UUID id, LoanUpdateDTO dto) {
        Loan loanExists = loanRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Loan with ID: " + id + " not found"));

        loanMapper.updateEntity(dto, loanExists);
        return loanMapper.toDTO(loanRepository.saveAndFlush(loanExists));
    }
}
