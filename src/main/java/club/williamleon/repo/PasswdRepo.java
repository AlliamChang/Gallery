package club.williamleon.repo;

import club.williamleon.domain.PasswdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PasswdRepo extends JpaRepository<PasswdEntity, Long> {

    PasswdEntity findByUsername(String username);

    boolean existsByUsernameAndToken(String username, String token);
}
