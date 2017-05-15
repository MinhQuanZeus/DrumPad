package zeus.minhquan.truyenratngan.model;

/**
 * Created by QuanT on 5/14/2017.
 */

public class Chapter {
    private int id;
    private String title;
    private String content;

    public Chapter(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
