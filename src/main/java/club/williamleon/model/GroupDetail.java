package club.williamleon.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author I343702 SAP
 */
public class GroupDetail {

    private Long groupId;

    private String groupName;

    private String roleInGroup;

    private boolean canAddUser;

    private boolean deletable;

    private boolean canRemoveUser;

    private boolean modifiable;

    private List<PhotoDetail> photos = new ArrayList<>();

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public boolean isCanAddUser() {
        return canAddUser;
    }

    public void setCanAddUser(boolean canAddUser) {
        this.canAddUser = canAddUser;
    }

    public boolean isDeletable() {
        return deletable;
    }

    public void setDeletable(boolean deletable) {
        this.deletable = deletable;
    }

    public boolean isCanRemoveUser() {
        return canRemoveUser;
    }

    public void setCanRemoveUser(boolean canRemoveUser) {
        this.canRemoveUser = canRemoveUser;
    }

    public boolean isModifiable() {
        return modifiable;
    }

    public void setModifiable(boolean modifiable) {
        this.modifiable = modifiable;
    }

    public List<PhotoDetail> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoDetail> photos) {
        this.photos = photos;
    }

    public void addPhoto(String name, String description, Double ratioWH, int rotate) {
        PhotoDetail photo = new PhotoDetail();
        photo.setFilename(name);
        photo.setTitle(description);
        photo.setRatioWH(ratioWH);
        photo.setRotate(rotate);
        photos.add(photo);
    }

    public String getRoleInGroup() {
        return roleInGroup;
    }

    public void setRoleInGroup(String roleInGroup) {
        this.roleInGroup = roleInGroup;
    }
}
