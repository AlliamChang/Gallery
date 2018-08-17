package club.williamleon.domain;

import club.williamleon.util.val.GroupRole;

import javax.persistence.*;

/**
 * Created by 53068 on 2018/4/27 0027.
 */
@Entity
@Table(name = "user_in_group")
public class UserInGroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "group_id", nullable = false)
    private Long groupId;

    @Column(name = "role", nullable = false)
    private GroupRole role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public GroupRole getRole() {
        return role;
    }

    public void setRole(GroupRole role) {
        this.role = role;
    }
}
