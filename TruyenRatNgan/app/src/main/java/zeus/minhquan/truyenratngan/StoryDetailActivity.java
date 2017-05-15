package zeus.minhquan.truyenratngan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import zeus.minhquan.truyenratngan.adapter.ChapterAdapter;
import zeus.minhquan.truyenratngan.model.Story;

public class StoryDetailActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private Story story;
    ChapterAdapter chapterAdapter;
    private static final float MIN_SCALE = 0.85f;
    private static final float MIN_ALPHA = 0.5f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_detail);

        viewPager = (ViewPager) findViewById(R.id.vp_chapter);
        getStory();
        setUpUI();
    }

    private void setUpUI() {
        viewPager.setAdapter(new ChapterAdapter(getSupportFragmentManager()).setStory(this.story));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                StoryApplication.getInstance().getStoryDatabase().updateChapterNo(story.getId(), position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View view, float position) {
                // do transformation here
                int pageWidth = view.getWidth();
                int pageHeight = view.getHeight();

                if (position < -1) { // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    view.setAlpha(0);

                } else if (position <= 1) { // [-1,1]
                    // Modify the default slide transition to shrink the page as well
                    float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                    float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                    float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                    if (position < 0) {
                        view.setTranslationX(horzMargin - vertMargin / 2);
                    } else {
                        view.setTranslationX(-horzMargin + vertMargin / 2);
                    }

                    // Scale the page down (between MIN_SCALE and 1)
                    view.setScaleX(scaleFactor);
                    view.setScaleY(scaleFactor);

                    // Fade the page relative to its size.
                    view.setAlpha(MIN_ALPHA +
                            (scaleFactor - MIN_SCALE) /
                                    (1 - MIN_SCALE) * (1 - MIN_ALPHA));

                } else { // (1,+Infinity]
                    // This page is way off-screen to the right.
                    view.setAlpha(0);
                }
            }
        });
        Story st = StoryApplication.getInstance().getStoryDatabase().getStoryByID(story.getId());
        if (st.getLastChapterNo() != -1) {
            viewPager.setCurrentItem(st.getLastChapterNo());
        }
    }

    public void getStory() {
        Intent intent = getIntent();
        this.story = (Story) intent.getSerializableExtra("Story");
        Log.d("Inten", story.toString());
    }
}
