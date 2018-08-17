package club.williamleon.model;

import club.williamleon.util.val.GroupRole;

/**
 * @author I343702 SAP
 */
public class InviteUser {

    private Long groupId;

    private String username;

    private GroupRole role;

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public GroupRole getRole() {
        return role;
    }

    public void setRole(GroupRole role) {
        this.role = role;
    }
}
