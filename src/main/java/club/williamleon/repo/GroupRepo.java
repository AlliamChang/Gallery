package club.williamleon.repo;

import club.williamleon.domain.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupRepo extends JpaRepository<GroupEntity, Long> {

    @Query("select g.isPublic from GroupEntity g where g.id = ?1")
    boolean isPublic(Long groupId);

    @Query("select g.id from GroupEntity g, UserInGroupEntity u" +
        " where u.userId = ?1 and u.groupId = g.id" +
        " order by u.role")
    List<Long> findDefaultGroup(Long userId);

    @Query("select g from GroupEntity g, UserInGroupEntity u" +
            " where u.userId = ?1 and u.groupId = g.id" +
            " order by g.createTime")
    List<GroupEntity> findGroups(Long userId);
}
