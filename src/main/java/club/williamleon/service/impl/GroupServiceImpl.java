package club.williamleon.service.impl;

import club.williamleon.config.SessionParam;
import club.williamleon.domain.GroupEntity;
import club.williamleon.domain.UserInGroupEntity;
import club.williamleon.model.GroupDetail;
import club.williamleon.model.GroupInfo;
import club.williamleon.model.InviteUser;
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
        newGroup.setDescription(groupInfo.getDescription().isEmpty() ?
            "" :
            groupInfo.getDescription());
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

    }

    @Override
    public void leaveGroup(Long groupId) {
        Long userId = sessionParam.getUserId();
        UserInGroupEntity entity = userInGroupRepo.findByUserId(userId);
        if (GroupRole.CREATOR.equals(entity.getRole())) {
            // TODO creator can't leave
            return;
        }

        UserInGroupEntity userInGroup = new UserInGroupEntity();
        userInGroup.setUserId(userId);
        userInGroup.setGroupId(groupId);
        userInGroupRepo.delete(userInGroup);

    }

    @Override
    public void inviteUsersToJoin(InviteUser inviteUser) {
        Long inviterId = sessionParam.getUserId();
        Long groupId = inviteUser.getGroupId();
        if (groupId == null) {
            // TODO defensive programming
            return;
        }
        Long inviteUserId = userRepo.findIdByUsername(inviteUser.getUsername());
        if (inviteUserId == null) {
            // user not exist
            return;
        }

        UserInGroupEntity inviter = userInGroupRepo.findByUserId(inviterId);
        if (!inviter.getRole().isAddUser()) {
            // TODO limited authority
            return;
        }

        if (GroupRole.compareLevel(inviter.getRole(), inviteUser.getRole()) >=
            0) {
            UserInGroupEntity entity = new UserInGroupEntity();
            entity.setGroupId(groupId);
            entity.setUserId(inviteUserId);
            entity.setRole(inviteUser.getRole());
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

        }
        return groups;
    }

    @Override
    public Long getDefaultList() {
        Long userId = sessionParam.getUserId();
        List<Long> groups = groupRepo.findDefaultGroup(userId);
        if (groups == null || groups.isEmpty()) {
            // TODO join no groups yet
            return null;
        }else {
            return groups.get(0);
        }
    }

    @Override
    public GroupRole getRoleInGroup(Long userId, Long groupId) {
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
            List<Object[]> photos = photoRepo.findPhotoInfo(groupId);
            if (GroupRole.PASSER.equals(roleInGroup.get().getRole())) {
                if(group.isPublic()){
                    for (Object[] photo : photos) {
                        detail.addPhoto((String) photo[0], "");
                    }
                }else {
                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                }
            }else {
                for (Object[] photo : photos) {
                    detail.addPhoto((String) photo[0], (String) photo[1]);
                }
            }
            return new ResponseEntity<>(detail, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
