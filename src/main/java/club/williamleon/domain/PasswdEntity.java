package club.williamleon.domain;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by 53068 on 2018/4/27 0027.
 */
@Entity
@Table(name = "passwd")
@EntityListeners(AuditingEntityListener.class)
public class PasswdEntity {

    @Id
    @Column(name = "username", length = 20)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "token")
    private String token;

    @Column(name = "latest_time")
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date latestTime;

    public PasswdEntity() {

    }

    public PasswdEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getLatestTime() {
        return latestTime;
    }

    public void setLatestTime(Date latestTime) {
        this.latestTime = latestTime;
    }
}
