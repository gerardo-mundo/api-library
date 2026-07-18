package mundo.org.apilibrary.repository;

import mundo.org.apilibrary.entities.Loan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LoanRepository extends JpaRepository<Loan, UUID> {
    List<Loan> findAllByBorrowerId(UUID borrowerId);

    List<Loan> findAllByApproverId(UUID approverId);

    @Query("SELECT b.id FROM Loan l JOIN l.borrowedBooks b WHERE l.active = true AND b.id IN :bookIds")
    List<UUID> findUnavailableBooks(@Param("bookIds") List<UUID> bookIds);
}
