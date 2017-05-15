package zeus.minhquan.truyenratngan.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import zeus.minhquan.truyenratngan.R;
import zeus.minhquan.truyenratngan.StoryApplication;
import zeus.minhquan.truyenratngan.databases.StoryDatabase;
import zeus.minhquan.truyenratngan.model.Chapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChapterFragment extends Fragment {

    private TextView textView;
    private WebView webView;
    private int chapterId;
    private Chapter chapter;

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public ChapterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chapter, container, false);
        getChapter();
        findReferences(view);
        setupUI();

        return view;
    }

    private void setupUI() {
        webView.setFocusable(false);
        textView.setText(chapter.getTitle());
        webView.loadData(chapter.getContent(),"text/html; charset=utf-8","UTF-8");

    }

    private void findReferences(View view) {
        textView = (TextView)view.findViewById(R.id.tv_title);
        webView = (WebView)view.findViewById(R.id.wv_content);
    }
    public void getChapter() {
        StoryDatabase storyDatabase = StoryApplication.getInstance().getStoryDatabase();
        chapter = storyDatabase.getChapter(chapterId);
    }
}
