package mundo.org.apilibrary.repository;

import mundo.org.apilibrary.entities.Thesis;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ThesisRepository extends JpaRepository<Thesis, UUID> {
    Optional<Thesis> findByBachelorDegree(String bachelorDegree, Limit limit);
    List<Thesis> findThesisByThesisAdvisor(String thesisAdvisor, Limit limit);
}
