package mundo.org.apilibrary.entities;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import mundo.org.apilibrary.classes.User;
import mundo.org.apilibrary.enums.LoanStatus;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "book_loans")
@Getter @Setter
public class BookLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private boolean isActive = true;
    @CreationTimestamp(source = SourceType.VM)
    @Column(updatable = false, nullable = false)
    private LocalDateTime loanDate;
    private LocalDateTime returnDate;
    @NotNull
    private LocalDateTime dueDate;
    private LoanStatus status = LoanStatus.APPROVED;
    // Relationships start here
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lender_id", nullable = false)
    private User lender;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_authorizer_id", nullable = false)
    private Employee employeeAuthorizer;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "loan_books",
            joinColumns = @JoinColumn(name = "loan_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> books;
}
