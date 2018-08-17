package club.williamleon.service;

import club.williamleon.model.PhotoDetail;
import club.williamleon.model.UploadInfo;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * Created by 53068 on 2018/4/16 0016.
 */
public interface ImageService {

    void init();

    Stream<Path> loadAll();

    Resource loadAsResource(String filename);

    Path load(String filename);

    void uploadPhoto(MultipartFile photo, UploadInfo info, Long id);

    void commentPhoto(String comment);

    PhotoDetail getPhotoDetail(String photoName);

//    void getPhotosByGroup();
}
