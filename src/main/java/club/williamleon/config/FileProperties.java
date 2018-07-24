package club.williamleon.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by 53068 on 2018/4/16 0016.
 */
@ConfigurationProperties("config")
//@Component
public class FileProperties {

    private String location = "img";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
