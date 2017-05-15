package zeus.minhquan.truyenratngan;

import android.app.Application;

import zeus.minhquan.truyenratngan.databases.StoryDatabase;

/**
 * Created by QuanT on 4/18/2017.
 */

public class StoryApplication extends Application {
    private static StoryApplication instance;
    private String TAG = "StoryApplication";
    private StoryDatabase storyDatabase;

    public static StoryApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        storyDatabase = new StoryDatabase(this);
    }

    public StoryDatabase getStoryDatabase() {
        return storyDatabase;
    }
}
