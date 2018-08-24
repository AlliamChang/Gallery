package club.williamleon.repo;

import club.williamleon.domain.UserInGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInGroupRepo extends JpaRepository<UserInGroupEntity, Long> {

    UserInGroupEntity findByUserId(Long userId);

    UserInGroupEntity findByUserIdAndGroupId(Long userId, Long groupId);
}
