package club.williamleon.service.impl;

import club.williamleon.config.SessionParam;
import club.williamleon.domain.GroupEntity;
import club.williamleon.domain.PhotoEntity;
import club.williamleon.domain.UserInGroupEntity;
import club.williamleon.model.GroupDetail;
import club.williamleon.model.GroupInfo;
import club.williamleon.model.Invitee;
import club.williamleon.repo.GroupRepo;
import club.williamleon.repo.PhotoRepo;
import club.williamleon.repo.UserInGroupRepo;
import club.williamleon.repo.UserRepo;
import club.williamleon.service.GroupService;
import club.williamleon.util.val.GroupRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author I343702 SAP
 */
@Service
public class GroupServiceImpl implements GroupService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserInGroupRepo userInGroupRepo;

    @Autowired
    private GroupRepo groupRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PhotoRepo photoRepo;

    @Autowired
    private SessionParam sessionParam;

    @Transactional
    @Override
    public void createGroup(GroupInfo groupInfo) {
        Long creatorId = sessionParam.getUserId();
        GroupEntity newGroup = new GroupEntity();
        newGroup.setCreator(creatorId);
        newGroup.setName(groupInfo.getName());
        newGroup.setDescription(StringUtils.isEmpty(groupInfo.getDescription()) ?
            "" : groupInfo.getDescription());
        newGroup.setPublic(groupInfo.isPublic());
        newGroup = groupRepo.save(newGroup);

        UserInGroupEntity inGroup = new UserInGroupEntity();
        inGroup.setGroupId(newGroup.getId());
        inGroup.setRole(GroupRole.CREATOR);
        inGroup.setUserId(creatorId);
        userInGroupRepo.save(inGroup);
        logger.info("{} was created successfully by {}", groupInfo.getName(),
            sessionParam.getUsername());
    }

    @Override
    public void joinGroup() {
        // TODO future feature
    }

    @Override
    public void leaveGroup(Long groupId) {
        Long userId = sessionParam.getUserId();
        UserInGroupEntity entity = userInGroupRepo.findByUserId(userId);
        if (GroupRole.CREATOR.equals(entity.getRole())) {
            // creator can't leave
            return;
        }

        UserInGroupEntity userInGroup = new UserInGroupEntity();
        userInGroup.setUserId(userId);
        userInGroup.setGroupId(groupId);
        userInGroupRepo.delete(userInGroup);

    }

    @Override
    public void inviteUsersToJoin(Invitee invitee) {
        Long inviterId = sessionParam.getUserId();
        Long groupId = invitee.getGroupId();
        if (groupId == null) {
            // defensive programming
            return;
        }
        Long inviteUserId = userRepo.findIdByUsername(invitee.getUsername());
        if (inviteUserId == null) {
            // user not exist
            return;
        }

        UserInGroupEntity inviter = userInGroupRepo.findByUserId(inviterId);
        if (!inviter.getRole().isAddUser()) {
            // limited authority
            return;
        }

        if (GroupRole.compareLevel(inviter.getRole(), invitee.getRole()) >= 0) {
            UserInGroupEntity entity = new UserInGroupEntity();
            entity.setGroupId(groupId);
            entity.setUserId(inviteUserId);
            entity.setRole(invitee.getRole());
            userInGroupRepo.save(entity);
        } else {
            // authority is not enough
        }

    }

    @Override
    public List<GroupInfo> getGroupList() {
        Long userId = sessionParam.getUserId();
        List<GroupInfo> groups = new ArrayList<>();
        if (userId != null) {
            List<GroupEntity> entities = groupRepo.findGroups(userId);
            if (entities != null) {
                for (GroupEntity entity : entities) {
                    GroupInfo info = new GroupInfo();
                    info.setId(entity.getId());
                    info.setDescription(entity.getDescription());
                    info.setName(entity.getName());
                    if (!StringUtils.isEmpty(entity.getCover())) {
                        info.setCover(entity.getCover());
                        info.setRotate(entity.getRotate());
                    } else {
                        List<Object[]> photos = photoRepo.findGalleryCover(info.getId());
                        // the oldest one is the cover when the cover is not set
                        if (photos != null && photos.size() > 0) {
                            info.setCover((String) photos.get(0)[0]);
                            info.setRotate((int) photos.get(0)[1]);
                        } else {
                            // default logo when no photo in gallery
                            info.setCover("");
                        }
                    }
                    groups.add(info);
                }
            }
            // TODO future feature: Add the public galleries
        }
        return groups;
    }

    @Override
    // TODO Deprecated when implement getGroupList()
    public Long getDefaultGallery() {
        Long userId = sessionParam.getUserId();
        List<Long> groups = groupRepo.findDefaultGroup(userId);
        if (groups == null || groups.isEmpty()) {
            // return public default gallery
            Long defaultPublicGallery = 1L;
            return defaultPublicGallery;
        }else {
            return groups.get(0);
        }
    }

    @Override
    public GroupRole getRoleInGroup(Long groupId) {
        Long userId = sessionParam.getUserId();
        if (userId == null) {
            return GroupRole.PASSER;
        }
        UserInGroupEntity role = userInGroupRepo.findByUserIdAndGroupId(userId, groupId);
        if (role == null) {
            return GroupRole.PASSER;
        }

        return role.getRole();
    }

    @Override
    public ResponseEntity<GroupDetail> enterGroup(Long groupId) {
        Long userId = sessionParam.getUserId();
        UserInGroupEntity userInGroup = new UserInGroupEntity();
        userInGroup.setUserId(userId);
        userInGroup.setGroupId(groupId);
        Optional<UserInGroupEntity> roleInGroup = userInGroupRepo
            .findOne(Example.of(userInGroup));
        if (roleInGroup.isPresent()) {
            GroupEntity group = groupRepo.findById(groupId).get();
            GroupDetail detail = new GroupDetail();
            detail.setGroupName(group.getName());
            detail.setGroupId(groupId);
            detail.setRoleInGroup(roleInGroup.get().getRole().label());
            List<PhotoEntity> photos = photoRepo.findPhotoInfo(groupId);
            if (GroupRole.PASSER.equals(roleInGroup.get().getRole())) {
                if(group.isPublic()){
                    for (PhotoEntity photo : photos) {
                        detail.addPhoto(photo.getName(), "", photo.getRationWH(), photo.getRotate());
                    }
                }else {
                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                }
            }else {
                for (PhotoEntity photo : photos) {
                    detail.addPhoto(photo.getName(), photo.getDescription(), photo.getRationWH(), photo.getRotate());
                }
            }
            return new ResponseEntity<>(detail, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
