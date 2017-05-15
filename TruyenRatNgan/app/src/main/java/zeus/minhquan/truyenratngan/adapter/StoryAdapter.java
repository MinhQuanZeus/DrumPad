package zeus.minhquan.truyenratngan.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import zeus.minhquan.truyenratngan.R;
import zeus.minhquan.truyenratngan.StoryApplication;
import zeus.minhquan.truyenratngan.model.Story;
import zeus.minhquan.truyenratngan.networks.ImageLoader;
import zeus.minhquan.truyenratngan.utils.TextViewUtils;

/**
 * Created by QuanT on 5/7/2017.
 */

public class StoryAdapter extends BaseAdapter {

    private List<Story> storyList;

    public StoryAdapter(List<Story> storyList) {
        this.storyList = storyList;
    }

    @Override
    public int getCount() {
        return storyList.size();
    }

    @Override
    public Object getItem(int position) {
        return storyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //load item data(model)
        Story story = storyList.get(position);

        //create view if neccessary (view)
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            convertView = layoutInflater.inflate(R.layout.item_story, parent, false);
        }


        //config and return
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
        TextView tvDes = (TextView) convertView.findViewById(R.id.tv_description);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.iv_image_strory);
        tvTitle.setText(story.getTitle());
        TextViewUtils.makeFit(tvDes);
        tvDes.setText(story.getDescription());
        //TODO: Image loading: AsyTask + URL + Inputstream + BitmapFactory
        //Glide
        (new ImageLoader()).setView(imageView).loadImage(story.getImage());
    //    int chapterCount = StoryApplication.getInstance().getStoryDatabase().getChapterCount(story);
        return convertView;
    }
}
