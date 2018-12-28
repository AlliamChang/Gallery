package club.williamleon.controller;

import club.williamleon.model.*;
import club.williamleon.service.GroupService;
import club.williamleon.service.ImageService;
import club.williamleon.util.val.GroupRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by 53068 on 2018/5/16 0016.
 */
@RestController
@RequestMapping("/gallery")
public class GalleryController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ImageService imageService;

    private final GroupService groupService;

    @Autowired
    public GalleryController(ImageService imageService,
        GroupService groupService) {
        this.imageService = imageService;
        this.groupService = groupService;
    }

    @GetMapping("/all")
    public List getCategory() {
        return groupService.getGroupList();
    }

    @GetMapping("/")
    public ResponseEntity getDefaultGallery() {
        Long defaultGroup = groupService.getDefaultList();
        if (defaultGroup == null || defaultGroup <= 0) {
            // TODO
        }
        return this.getGalleryPhotos(defaultGroup);
    }

    @PostMapping("/")
    public ResponseEntity createGallery(GroupInfo groupInfo) {
        groupService.createGroup(groupInfo);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/{id}/invite")
    public ResponseEntity invite(@PathVariable Long id,
        @RequestParam String username, @RequestParam String role) {
        InviteUser inviteUser = new InviteUser();
        inviteUser.setGroupId(id);
        inviteUser.setUsername(username);
        inviteUser.setRole(GroupRole.resolve(role));
        groupService.inviteUsersToJoin(inviteUser);
        return null;
    }

    // TODO auto accept?
    @GetMapping("/{id}/accept")
    public ResponseEntity accept() {

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupDetail> getGalleryPhotos(@PathVariable Long id) {
        ResponseEntity<GroupDetail> groupDetail = groupService.enterGroup(id);
        
        return groupDetail;
    }

    @GetMapping("/photo/{filename}")
    public ResponseEntity getPhotoDetail(@PathVariable String filename, @RequestParam("group") Long groupId) {

        return imageService.getPhotoDetail(filename, groupId);
    }

    @GetMapping("/{id}/role")
    public ResponseEntity getRole() {

        return null;
    }

    @PostMapping("/{id}/photo")
    public ResponseEntity<?> uploadPhoto(@PathVariable Long id,
        @RequestParam("file") MultipartFile file,
        UploadInfo info) {

        return imageService.uploadPhoto(file, info, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteGallery(@PathVariable String id) {

        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity quitGallery() {

        return null;
    }

    @PostMapping("/comment/{photoName}")
    public ResponseEntity comment(@PathVariable String photoName,
        @RequestParam("comment") String comment, @RequestParam("group") Long groupId) {
        Comment entity = new Comment();
        entity.setPhotoName(photoName);
        entity.setComment(comment);
        entity.setGroupId(groupId);
        return imageService.commentPhoto(entity);
    }

}
