package club.williamleon.repo;

import club.williamleon.domain.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepo extends JpaRepository<CommentEntity, Long> {

    CommentEntity findByUserIdAndPhotoName(Long userId, String photoName);

    @Query("select c.content, c.time, u.username from CommentEntity c, UserEntity u" +
        " where c.photoName = ?1 and c.userId = u.id" +
        " order by c.time")
    List<Object[]> findAllByPhotoName(String photoName);
}
