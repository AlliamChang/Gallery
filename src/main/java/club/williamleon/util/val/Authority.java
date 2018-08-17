package club.williamleon.util.val;

/**
 * @author I343702 SAP
 */
public enum Authority {
    DELETABLE(GroupRole.CREATOR.ordinal()),
    MODIFIABLE(GroupRole.MANAGER.ordinal()),
    ADD_USER(GroupRole.MANAGER.ordinal()),
    REMOVE_USER(GroupRole.MANAGER.ordinal()),
    REVIEW_COMMENT(GroupRole.OBSERVER.ordinal());

    private int level;

    Authority(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
