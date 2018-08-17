package club.williamleon.repo;

import club.williamleon.domain.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GroupRepo extends JpaRepository<GroupEntity, Long> {

    @Query("select g.isPublic from GroupEntity g where g.id = ?1")
    boolean isPublic(Long groupId);
}
