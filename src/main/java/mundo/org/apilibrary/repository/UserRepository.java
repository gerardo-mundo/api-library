package mundo.org.apilibrary.repository;

import mundo.org.apilibrary.classes.User;
import mundo.org.apilibrary.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {
    Optional<User> findByEmail(String email);
    List<User> findByIsActive(boolean isActive);

    List<User> findByRole(Role role);

    boolean existsUserByEmailAndIdNot(String email, UUID id);
}
