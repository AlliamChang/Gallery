package club.williamleon.domain;

import club.williamleon.util.val.GroupRole;

import javax.persistence.Entity;

/**
 * Created by 53068 on 2018/4/27 0027.
 */
@Entity
public class UserInGroup {

    private Long userId;

    private Long groupId;

    private GroupRole role;



}
