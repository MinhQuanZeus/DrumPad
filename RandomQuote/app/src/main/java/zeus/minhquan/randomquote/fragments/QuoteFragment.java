package zeus.minhquan.randomquote.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import zeus.minhquan.randomquote.R;
import zeus.minhquan.randomquote.networks.ImageLoader;
import zeus.minhquan.randomquote.networks.QuoteLoader;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuoteFragment extends Fragment {

    private final String QUOTE_URL = "http://quotesondesign.com/wp-json/posts?filter[orderby]=rand";
    private TextView tvQuote;
    private ImageView ivQuote;
    private SwipeRefreshLayout swipeRefreshLayout;


    public QuoteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quote, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_to_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFragmentManager().beginTransaction().detach(QuoteFragment.this).attach(QuoteFragment.this).commit();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, R.color.colorAccent, R.color.colorPrimaryDark);
        tvQuote = (TextView) view.findViewById(R.id.tv_quote);
        ivQuote = (ImageView) view.findViewById(R.id.iv_background);
        new ImageLoader(ivQuote).loadImage();
        new QuoteLoader(tvQuote).execute();
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }

        return view;
    }

}
