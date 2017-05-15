package zeus.minhquan.truyenratngan.model;

import java.io.Serializable;

/**
 * Created by QuanT on 4/18/2017.
 */

public class Story implements Serializable {
    private int id;
    private String image;
    private String title;
    private String description;
    private boolean isFavorite;
    private int lastChapterNo;

    public void setLastChapterNo(int lastChapterNo) {
        this.lastChapterNo = lastChapterNo;
    }

    public Story(int id, String image, String title, String description, boolean isFavorite, int lastChapterNo) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.description = description;
        this.isFavorite = isFavorite;
        this.lastChapterNo = lastChapterNo;
    }

    public int getLastChapterNo() {
        return lastChapterNo;
    }

    public int getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    @Override
    public String toString() {
        return "Story{" +
                "id=" + id +
                ", image='" + image + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", isFavorite=" + isFavorite +
                ", lastChapterNo=" + lastChapterNo +
                '}';
    }
}
