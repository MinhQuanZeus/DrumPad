package zeus.minhquan.freemusic.networks;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by QuanT on 5/23/2017.
 */

public interface MusicTypeService {
    @GET("data/media-types.json")
    Call<List<MediaType>> getMediaType();
}
