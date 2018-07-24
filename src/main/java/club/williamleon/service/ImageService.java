package club.williamleon.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * Created by 53068 on 2018/4/16 0016.
 */
public interface ImageService {

    Stream<Path> loadAll();

    Resource loadAsResource(String filename);

    Path load(String filename);

    void store(MultipartFile file);

    void init();
}
