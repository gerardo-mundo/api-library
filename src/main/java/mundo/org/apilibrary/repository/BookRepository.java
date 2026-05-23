package mundo.org.apilibrary.repository;


import mundo.org.apilibrary.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
    Optional<Book> findByIsbn(String isbn);
    List<Book> findAllByAuthor(String author);
    boolean existsByIsbn(String isbn);

    boolean existsByIsbnAndIdNot(String isbn, UUID id);

    boolean existsByAcquisitionAndIdNot(int acquisition, UUID id);
}