package club.williamleon.service;

import club.williamleon.model.GroupDetail;
import club.williamleon.model.GroupInfo;
import club.williamleon.model.Invitee;
import club.williamleon.util.val.GroupRole;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GroupService {

    void createGroup(GroupInfo groupInfo);

    void joinGroup();

    void leaveGroup(Long groupId);

    void inviteUsersToJoin(Invitee invitee);

    List<GroupInfo> getGroupList();

    Long getDefaultGallery();

    GroupRole getRoleInGroup(Long groupId);

    ResponseEntity<GroupDetail> enterGroup(Long id);
}
