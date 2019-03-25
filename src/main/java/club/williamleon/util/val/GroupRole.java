package club.williamleon.util.val;

/**
 * Created by 53068 on 2018/5/7 0007.
 */

import java.util.HashMap;
import java.util.Map;

/**
 * 相册不同的管理权限
 * 删除  修改相册权限  增加用户    删除用户    查看评论     操作照片
 * 创建者   √        √          √          √          √        √
 * 管理者   x         √          √          √          √       √
 * 观察者   x         x           x          x           √     x
 * 访客     x         x           x           x           x       x
 */
public enum GroupRole {

    CREATOR("Creator", true, true, true, true, true, true),
    MANAGER("Manager", true, false, true, true, true, true),
    OBSERVER("Observer", false, false, false, false, false, true),
    PASSER("Passer", false, false, false, false, false, false);

    private final String label;

    private final boolean uploadable;

    private final boolean deletable;

    private final boolean modifiable;

    private final boolean addUser;

    private final boolean removeUser;

    private final boolean reviewComment;

    GroupRole(String label, boolean uploadable, boolean deletable, boolean modifiable,
        boolean addUser,
        boolean removeUser, boolean reviewComment) {
        this.label = label;
        this.uploadable = uploadable;
        this.deletable = deletable;
        this.modifiable = modifiable;
        this.addUser = addUser;
        this.removeUser = removeUser;
        this.reviewComment = reviewComment;
    }

    private static final Map<String, GroupRole> mappings = new HashMap<>(4);

    static {
        for (GroupRole groupRole : values()) {
            mappings.put(groupRole.label, groupRole);
        }
    }

    public String label() {
        return label;
    }

    public boolean isUploadable() {
        return uploadable;
    }

    public boolean isDeletable() {
        return deletable;
    }

    public boolean isModifiable() {
        return modifiable;
    }

    public boolean isAddUser() {
        return addUser;
    }

    public boolean isRemoveUser() {
        return removeUser;
    }

    public boolean isReviewComment() {
        return reviewComment;
    }

    public static GroupRole resolve(String label) {
        return (label != null ? mappings.get(label) : null);
    }

    public static int compareLevel(GroupRole role1, GroupRole role2) {
        if (role1.ordinal() < role2.ordinal()) {
            return 1;
        } else if (role1.ordinal() > role2.ordinal()) {
            return -1;
        } else {
            return 0;
        }
    }
}
