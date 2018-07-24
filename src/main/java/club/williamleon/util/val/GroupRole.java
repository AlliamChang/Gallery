package club.williamleon.util.val;

/**
 * Created by 53068 on 2018/5/7 0007.
 */

/**
 * 相册不同的管理权限
 *          删除  修改相册权限  增加用户    删除用户    查看评论
 *  创建者   √        √          √          √          √
 *  管理者   x         √          √          √          √
 *  观察者   x         x           √          x           √
 *  访客     x         x           x           x           x
 */
public enum  GroupRole {

    CREATOR,
    MANAGER,
    OBSERVER,
    PASSER

}
