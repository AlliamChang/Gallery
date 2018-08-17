package club.williamleon.repo;

import club.williamleon.domain.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<CommentEntity, Long> {

}
