package club.williamleon.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author I343702 SAP
 */
public class GroupDetail {

    private Long groupId;

    private String groupName;

    private boolean canAddUser;

    private boolean deletable;

    private boolean canRemoveUser;

    private boolean modifiable;

    // <name, description>
    private Map<String, String> photos = new HashMap<>();

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

    public Map<String, String> getPhotos() {
        return photos;
    }

    public void setPhotos(Map<String, String> photos) {
        this.photos = photos;
    }

    public void addPhoto(String name, String description) {
        photos.put(name, description);
    }

}
