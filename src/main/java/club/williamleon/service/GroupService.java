package club.williamleon.service;

import club.williamleon.model.GroupDetail;
import club.williamleon.model.GroupInfo;
import club.williamleon.model.InviteUser;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GroupService {

    void createGroup(GroupInfo groupInfo);

    void joinGroup();

    void leaveGroup(Long groupId);

    void inviteUsersToJoin(InviteUser inviteUser);

    List<GroupInfo> getGroupList();

    ResponseEntity<GroupDetail> enterGroup(Long id);
}
