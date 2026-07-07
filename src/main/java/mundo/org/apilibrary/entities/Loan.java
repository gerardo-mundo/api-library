package mundo.org.apilibrary.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import mundo.org.apilibrary.enums.LoanStatus;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "loans")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approver_id", nullable = false)
    private Employee approver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "borrower_id", nullable = false)
    private Student borrower;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "loan_books",
            joinColumns = @JoinColumn(name = "loan_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    @Size(min = 1, max = 4, message = "You should add at least one book")
    private List<Book> borrowedBooks;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LoanStatus status;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime borrowDate;

    @Column(nullable = false)
    private LocalDateTime returnDate;
}
