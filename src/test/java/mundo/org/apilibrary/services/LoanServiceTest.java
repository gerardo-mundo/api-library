package mundo.org.apilibrary.services;

import jakarta.persistence.EntityNotFoundException;
import mundo.org.apilibrary.DTO.loans.LoanCreationDTO;
import mundo.org.apilibrary.DTO.loans.LoanDTO;
import mundo.org.apilibrary.DTO.loans.LoanUpdateDTO;
import mundo.org.apilibrary.entities.Book;
import mundo.org.apilibrary.entities.Employee;
import mundo.org.apilibrary.entities.Loan;
import mundo.org.apilibrary.entities.Student;
import mundo.org.apilibrary.enums.LoanStatus;
import mundo.org.apilibrary.mapper.LoanMapper;
import mundo.org.apilibrary.repository.BookRepository;
import mundo.org.apilibrary.repository.LoanRepository;
import mundo.org.apilibrary.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoanServiceTest {
    @Mock
    private LoanRepository loanRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private LoanMapper loanMapper;
    @InjectMocks
    private LoanService loanService;

    private UUID bookId;
    private UUID approver;
    private UUID borrower;
    private Student student;
    private Employee employee;
    private Book book;
    private Loan loan;
    private LoanDTO loanDto;
    private LoanCreationDTO loanCreation;
    private LoanUpdateDTO loanUpdate;

    @BeforeEach
    void setup() {
        approver = UUID.randomUUID();
        borrower = UUID.randomUUID();
        bookId = UUID.randomUUID();
        student = new Student();
        employee = new Employee();
        book = new Book();
        loan = new Loan();

        loanCreation = new LoanCreationDTO(
                approver,
                borrower,
                List.of(bookId)
        );

        loanDto = new LoanDTO(
                UUID.randomUUID(),
                null,
                null,
                null,
                true,
                null,
                null,
                null
        );

        loanUpdate = new LoanUpdateDTO(
                true,
                LoanStatus.APPROVED,
                LocalDateTime.now()
        );
    }

    @Test
    void saveLoan_shouldThrownAnException_whenBookIsUnavailable() {
        when(userRepository.findById(approver)).thenReturn(Optional.of(employee));
        when(userRepository.findById(borrower)).thenReturn(Optional.of(student));
        when(bookRepository.findAllById(any())).thenReturn(List.of(book));
        when(loanRepository.findUnavailableBooks(any())).thenReturn(List.of(bookId));

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> loanService.createLoan(loanCreation));

        assertEquals("The next books are unavailable: " + List.of(bookId), exception.getMessage());
        verify(loanRepository, never()).save(any(Loan.class));
    }

    @Test
    void saveLoan_shouldReturnLoanDTO_whenSuccessful() {
        when(userRepository.findById(approver)).thenReturn(Optional.of(employee));
        when(userRepository.findById(borrower)).thenReturn(Optional.of(student));
        when(bookRepository.findAllById(any())).thenReturn(List.of(book));
        when(loanRepository.findUnavailableBooks(any())).thenReturn(java.util.Collections.emptyList());
        when(loanRepository.saveAndFlush(any(Loan.class))).thenReturn(loan);
        when(loanMapper.toDTO(any(Loan.class))).thenReturn(loanDto);

        LoanDTO result = loanService.createLoan(loanCreation);

        assertNotNull(result);
        assertEquals(loanDto, result);
        verify(loanRepository, times(1)).saveAndFlush(any(Loan.class));
    }

    @Test
    void updateLoan_shouldThrownAnException_whenLoanIsNotFound() {
        UUID loanId = UUID.randomUUID();

        when(loanRepository.findById(loanId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> loanService.updateLoan(loanId, loanUpdate));

        assertEquals("Loan with ID: " + loanId + " not found", exception.getMessage());
        verify(loanRepository, never()).saveAndFlush(any(Loan.class));
    }
}
