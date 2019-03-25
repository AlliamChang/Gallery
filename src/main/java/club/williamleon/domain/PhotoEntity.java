package club.williamleon.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by 53068 on 2018/4/26 0026.
 */
@Entity
@Table(name = "photo")
public class PhotoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "group_id", nullable = false)
    private Long groupId;

    @Column(name = "up_id", nullable = false)
    private Long upId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "ratio_wh", nullable = false)
    private Double rationWH;

    @Column(name = "rotate")
    private int rotate;

    @Column(name = "up_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date upTime;

    @Column(name = "origin_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date originTime;

    @Column(name = "description")
    private String description;

    @Column(name = "is_public")
    private boolean isPublic;

    @Column(name = "click", nullable = false)
    private Long click;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getUpId() {
        return upId;
    }

    public void setUpId(Long upId) {
        this.upId = upId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRationWH() {
        return rationWH;
    }

    public void setRationWH(Double rationWH) {
        this.rationWH = rationWH;
    }

    public Date getUpTime() {
        return upTime;
    }

    public void setUpTime(Date upTime) {
        this.upTime = upTime;
    }

    public Date getOriginTime() {
        return originTime;
    }

    public void setOriginTime(Date originTime) {
        this.originTime = originTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public Long getClick() {
        return click;
    }

    public void setClick(Long click) {
        this.click = click;
    }

    public int getRotate() {
        return rotate;
    }

    public void setRotate(int rotate) {
        this.rotate = rotate;
    }
}
