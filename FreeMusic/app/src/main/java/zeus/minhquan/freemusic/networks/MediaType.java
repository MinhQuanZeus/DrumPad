package zeus.minhquan.freemusic.networks;

import java.util.List;

/**
 * Created by QuanT on 5/23/2017.
 */

public class MediaType {
    private List<MusicType> subgenres;

    public MediaType(List<MusicType> subgenres) {
        this.subgenres = subgenres;
    }

    public List<MusicType> getSubgenres() {
        return subgenres;
    }
}
