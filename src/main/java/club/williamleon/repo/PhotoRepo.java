package club.williamleon.repo;

import club.williamleon.domain.PhotoEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by 53068 on 2018/4/24 0024.
 */
public interface PhotoRepo extends JpaRepository<PhotoEntity, Long> {

    @Query("select p" +
        " from PhotoEntity p" +
        " where p.groupId = ?1 order by p.originTime")
    List<PhotoEntity> findPhotoInfo(Long groupId);

    @Query("select p.name, p.rotate" +
            " from PhotoEntity p" +
            " where p.groupId = ?1 and p.rotate = 0 order by p.originTime")
    List<Object[]> findGalleryCover(Long groupId);

    PhotoEntity findByName(String name);

    void deleteByNameAndGroupId(String name, Long groupId);
}
