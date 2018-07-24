package club.williamleon.service.impl;

import club.williamleon.config.FileProperties;
import club.williamleon.dao.PhotoDao;
import club.williamleon.domain.Photo;
import club.williamleon.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by 53068 on 2018/4/16 0016.
 */
@Service
public class ImageServiceImpl implements ImageService {

    private final Path rootLocation;
    @Autowired
    private PhotoDao photoDao;

    @Autowired
    public ImageServiceImpl(FileProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
//            TODO: throw new
        }
    }

    @Override
    public Stream<Path> loadAll() {
//        try {

        List<String> names = photoDao.getPhotoNames();
        List<Path> paths = new ArrayList<>();
        if (names != null) {
            names.forEach(name -> paths.add(rootLocation.resolve(name)));
        }
        return paths.stream();

        // 官方方法
//            return Files.walk(this.rootLocation, 1)
//                    .filter(path -> !path.equals(this.rootLocation))
//                    .map(this.rootLocation::relativize);
//        } catch (IOException e) {
////            TODO: throw new
//            System.out.println("load fail");
//            return null;
//        }
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
//            TODO: throw new
                return null;
            }
        } catch (MalformedURLException e) {
//            TODO: throw new
            return null;
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public void store(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (filename.isEmpty()) {

            }
            if (filename.contains("..")) {

            }
            try (InputStream inputStream = file.getInputStream()) {
                Photo newPhoto = new Photo();
                newPhoto.setName(filename);
                photoDao.save(newPhoto);
                Files.copy(inputStream, this.rootLocation.resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
//            TODO: throw new
            e.printStackTrace();
        }
    }
}
