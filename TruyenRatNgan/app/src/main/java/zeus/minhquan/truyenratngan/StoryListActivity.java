package zeus.minhquan.truyenratngan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import zeus.minhquan.truyenratngan.adapter.StoryAdapter;
import zeus.minhquan.truyenratngan.databases.StoryDatabase;
import zeus.minhquan.truyenratngan.model.Story;

public class StoryListActivity extends AppCompatActivity {

    private final String TAG = "StoryListActivity";
    private List<Story> storyList;
    private ArrayAdapter<Story> storyArrayAdapter;
    private ListView lvStory;
    private StoryAdapter storyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_list);

        lvStory = (ListView) findViewById(R.id.lv_story_list);
        loadData();
        setupUI();
    }

    private void setupUI() {
        //create adapter
        //    storyArrayAdapter = new ArrayAdapter<Story>(this,android.R.layout.simple_list_item_1,storyList);
        storyAdapter = new StoryAdapter(storyList);
        //connect adapter to listview and model
        lvStory.setAdapter(storyAdapter);
        //add event
        lvStory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(StoryListActivity.this, StoryDetailActivity.class);
                intent.putExtra("Story", storyList.get(position));

                startActivity(intent);
            }
        });
    }

    private void loadData() {
        StoryDatabase storyDatabase = StoryApplication.getInstance().getStoryDatabase();
        storyList = storyDatabase.loadAllStories();
    }
}
