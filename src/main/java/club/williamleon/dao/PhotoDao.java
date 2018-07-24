package club.williamleon.dao;

import club.williamleon.domain.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by 53068 on 2018/4/24 0024.
 */
public interface PhotoDao extends JpaRepository<Photo, Long>{

    List<String> getPhotoNames();
}
