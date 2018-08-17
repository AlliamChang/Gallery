package club.williamleon.repo;

import club.williamleon.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepo extends JpaRepository<UserEntity, Long> {

    UserEntity findByEmail(String email);

    @Query("select u.id from UserEntity u where u.username = ?1")
    Long findIdByUsername(String username);
}
