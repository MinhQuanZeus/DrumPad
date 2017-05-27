package zeus.minhquan.freemusic.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zeus.minhquan.freemusic.R;
import zeus.minhquan.freemusic.networks.MediaType;
import zeus.minhquan.freemusic.networks.MusicType;
import zeus.minhquan.freemusic.networks.MusicTypeService;
import zeus.minhquan.freemusic.networks.RetrofitFactory;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MusicTypeService musicTypeService = RetrofitFactory.getInstence().createService(MusicTypeService.class);
        musicTypeService.getMediaType().enqueue(new Callback<List<MediaType>>() {
            @Override
            public void onResponse(Call<List<MediaType>> call, Response<List<MediaType>> response) {
                MediaType mediaType = response.body().get(3);
                List<MusicType> musicTypes = mediaType.getSubgenres();
                for (MusicType musicType : musicTypes) {
                    Log.d(TAG, "onResponse: " + musicType.toString());
                }
            }

            @Override
            public void onFailure(Call<List<MediaType>> call, Throwable t) {

            }
        });

    }
}
