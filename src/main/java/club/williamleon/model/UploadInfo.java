package club.williamleon.model;

/**
 * @author I343702 SAP
 */
public class UploadInfo {

    private String photoType;

    private String description;

    private String originalTime;

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

    public String getPhotoType() {
        return photoType;
    }

    public void setPhotoType(String photoType) {
        this.photoType = photoType;
    }
}
