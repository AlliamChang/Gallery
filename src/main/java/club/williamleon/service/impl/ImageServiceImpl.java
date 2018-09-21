package club.williamleon.service.impl;

import club.williamleon.config.FileProperties;
import club.williamleon.config.SessionParam;
import club.williamleon.domain.CommentEntity;
import club.williamleon.model.Comment;
import club.williamleon.model.PhotoDetail;
import club.williamleon.model.UploadInfo;
import club.williamleon.repo.CommentRepo;
import club.williamleon.repo.PhotoRepo;
import club.williamleon.domain.PhotoEntity;
import club.williamleon.service.GroupService;
import club.williamleon.service.ImageService;
import club.williamleon.util.MD5;
import club.williamleon.util.StringUtil;
import club.williamleon.util.val.GroupRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
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
    private GroupService groupService;

    @Autowired
    private CommentRepo commentRepo;

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
    public ResponseEntity<String> uploadPhoto(MultipartFile photo, UploadInfo info, Long groupId) {
        Long userId = sessionParam.getUserId();

        // generate a name for photo
        String filename = photo.getOriginalFilename();
        String[] temp= filename.split("\\.");
        String photoType = temp[temp.length - 1];
        if (!"jpg".equals(photoType)) {
            // TODO check the type of file
        }
        String date = StringUtil.formatDate(new Date());
        String uploaderMd5 = MD5.digest("uploader");
        filename = uploaderMd5.concat(date).concat(".").concat(photoType);
        // store
        if(this.store(photo, filename)){
            Date originalTime = null;
            try {
                originalTime = StringUtil
                    .parseExifDate(info.getOriginalTime());
            } catch (ParseException e) {
                originalTime = new Date();
            }
            PhotoEntity entity = new PhotoEntity();
            entity.setGroupId(groupId);
            entity.setUpTime(new Date());
            entity.setUpId(userId);
            entity.setOriginTime(originalTime);
            entity.setDescription(info.getDescription());
            entity.setClick(0L);
            entity.setName(filename);
            photoRepo.save(entity);

            if (entity.getId() != null) {
                return new ResponseEntity<>("Upload success!", HttpStatus.OK);
            }else {
                return new ResponseEntity<>("Record save failed",
                    HttpStatus.ACCEPTED);
            }
        }else {
            return new ResponseEntity<>("Store failed",
                HttpStatus.ACCEPTED);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<List<Comment>> commentPhoto(Comment comment) {
        Long userId = sessionParam.getUserId();
        GroupRole role = groupService
            .getRoleInGroup(userId, comment.getGroupId());
        if (GroupRole.PASSER.equals(role)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        CommentEntity entity = commentRepo
            .findByUserIdAndPhotoName(userId, comment.getPhotoName());
        if (entity == null) {
            entity = new CommentEntity();
            entity.setPhotoName(comment.getPhotoName());
            entity.setContent(comment.getComment());
            entity.setTime(new Date());
            entity.setUserId(userId);
        }else {
            entity.setTime(new Date());
            entity.setContent(comment.getComment());
        }

        if (commentRepo.save(entity).getId() != null) {
            List<Comment> newList = buildComment(comment.getPhotoName());
            return new ResponseEntity<>(newList, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
    }

    @Override
    public ResponseEntity<PhotoDetail> getPhotoDetail(String photoName, Long groupId) {
        Long userId = sessionParam.getUserId();
        GroupRole role = groupService.getRoleInGroup(userId, groupId);
        if (GroupRole.PASSER.equals(role)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        PhotoDetail detail = new PhotoDetail();
        PhotoEntity entity = photoRepo.findByName(photoName);
        if (entity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        detail.setFilename(entity.getName());
        detail.setTitle(entity.getDescription());
        List<Comment> comments = buildComment(photoName);
        detail.setComments(comments);

        return new ResponseEntity<>(detail, HttpStatus.OK);
    }

    @Override
    public Stream<Path> loadAll() {
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

    private List<Comment> buildComment(String photoName) {
        List<Object[]> list = commentRepo
            .findAllByPhotoName(photoName);
        List<Comment> newList = new ArrayList<>();
        for (Object[] commentEntity : list) {
            Comment one = new Comment();
            one.setComment((String) commentEntity[0]);
            one.setCommentTime(StringUtil.formatDate((Date)commentEntity[1]));
            one.setUsername((String)commentEntity[2]);
            newList.add(one);
        }
        return newList;
    }
}
