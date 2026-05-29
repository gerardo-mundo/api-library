package mundo.org.apilibrary.repository;

import mundo.org.apilibrary.classes.Publication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PublicationRepository extends JpaRepository<Publication, UUID> {
    Optional<Publication> findPublicationByIssn(String issn);
    List<Publication> getAllByPublisher(String publisher);
}
