package club.williamleon.service;

import club.williamleon.model.Comment;
import club.williamleon.model.PhotoDetail;
import club.williamleon.model.UploadInfo;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by 53068 on 2018/4/16 0016.
 */
public interface ImageService {

    void init();

    Stream<Path> loadAll();

    Resource loadAsResource(String filename);

    Path load(String filename);

    ResponseEntity<String> uploadPhoto(MultipartFile photo, UploadInfo info, Long id);

    ResponseEntity<List<Comment>> commentPhoto(Comment comment);

    ResponseEntity<PhotoDetail> getPhotoDetail(String photoName, Long groupId);

//    void getPhotosByGroup();
}
