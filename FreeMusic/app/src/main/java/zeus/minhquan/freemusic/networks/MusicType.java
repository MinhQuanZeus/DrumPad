package zeus.minhquan.freemusic.networks;

import com.google.gson.annotations.SerializedName;

/**
 * Created by QuanT on 5/23/2017.
 */

public class MusicType {
    private String id;

    @SerializedName("translation_key")
    private String key;

    public MusicType(String id, String key) {
        this.id = id;
        this.key = key;
    }

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "MusicType{" +
                "id='" + id + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
