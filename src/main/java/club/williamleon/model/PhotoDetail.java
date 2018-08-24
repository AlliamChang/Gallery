package club.williamleon.model;

import java.util.List;

/**
 * Created by 53068 on 2018/6/25 0025.
 */
public class PhotoDetail {

    private String filename;
    private String title;
    private List<Comment> comments;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
