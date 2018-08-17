package club.williamleon.service.impl;

import club.williamleon.config.FileProperties;
import club.williamleon.config.SessionParam;
import club.williamleon.model.PhotoDetail;
import club.williamleon.model.UploadInfo;
import club.williamleon.repo.PhotoRepo;
import club.williamleon.domain.PhotoEntity;
import club.williamleon.service.ImageService;
import club.williamleon.util.MD5;
import club.williamleon.util.StringUtil;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by 53068 on 2018/4/16 0016.
 */
@Service
public class ImageServiceImpl implements ImageService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Path rootLocation;
    @Autowired
    private PhotoRepo photoRepo;

    @Autowired
    private SessionParam sessionParam;

    @Autowired
    public ImageServiceImpl(FileProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public void init() {
        try {
            if(!Files.exists(rootLocation)) {
                Files.createDirectories(rootLocation);
            }
            logger.info("file service init successfully!");
        } catch (IOException e) {
            logger.error("file service failed to init. Reason: {}", e.getMessage());
        }
    }

    @Override
    @Transactional
    public void uploadPhoto(MultipartFile photo, UploadInfo info, Long groupId) {
        Long userId = sessionParam.getUserId();

        // generate a name for photo
        String date = StringUtil.formatDate(new Date());
        String uploaderMd5 = MD5.digest("uploader");
        String filename = uploaderMd5.concat(date).concat(".").concat(info.getPhotoType());
        if(this.store(photo, filename)){
            PhotoEntity entity = new PhotoEntity();
            entity.setGroupId(groupId);
            entity.setUpTime(new Date());
            entity.setUpId(userId);
            entity.setDescription(info.getDescription());
            entity.setClick(0L);
            entity.setName(filename);
        }
    }

    @Override
    @Transactional
    public void commentPhoto(String comment) {

    }

    @Override
    public PhotoDetail getPhotoDetail(String photoName) {
        Long userId = sessionParam.getUserId();

        return null;
    }

    @Override
    public Stream<Path> loadAll() {
//        try {

        List<String> names = photoRepo.findPhotoNames();
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
                logger.warn("File: {} doesn't exist.");
                return null;
            }
        } catch (MalformedURLException e) {
            logger.error("Resource loaded failed. Reason: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    private boolean store(MultipartFile file, String filename) {
        try {
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, this.rootLocation.resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);
            }
            return true;
        } catch (IOException e) {
            logger.error("failed to store photo! Reason: {}", e.getMessage());
            return false;
        }
    }


}
