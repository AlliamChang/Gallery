package club.williamleon.model;

/**
 * @author I343702 SAP
 */
public class UploadInfo {

    private String description;

    private String originalTime;

    private int rotate;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOriginalTime() {
        return originalTime;
    }

    public void setOriginalTime(String originalTime) {
        this.originalTime = originalTime;
    }

    public int getRotate() {
        return rotate;
    }

    public void setRotate(int rotate) {
        this.rotate = rotate;
    }
}
