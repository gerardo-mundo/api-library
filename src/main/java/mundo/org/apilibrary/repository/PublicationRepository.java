package mundo.org.apilibrary.repository;

import mundo.org.apilibrary.classes.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PublicationRepository extends JpaRepository<Publication, UUID>, JpaSpecificationExecutor<Publication> {
    Optional<Publication> findPublicationByIssn(String issn);

    List<Publication> getAllByPublisherIgnoreCase(String publisher);

    boolean existsByIssnAndIdNot(String issn, UUID id);

    boolean existsByIssn(String issn);
}
