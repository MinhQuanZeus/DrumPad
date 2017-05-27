package zeus.minhquan.randomquote.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

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
    private TextView tvGood;
    private TextView tv_ask;
    private TextView tv_todomain;
    private EditText ed_todo;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String username;
    private static String todo = "";


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
        tvGood = (TextView) view.findViewById(R.id.tv_good);
        tv_ask = (TextView) view.findViewById(R.id.tv_ask);
        ed_todo = (EditText) view.findViewById(R.id.ed_todo);
        tv_todomain = (TextView) view.findViewById(R.id.tv_todo);
        new QuoteLoader(tvQuote).execute();
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        new ImageLoader(ivQuote).loadImage();
        tvGood.setText(getGood(username));
        updateUI();
        ed_todo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // do your stuff here
                    updateUI();
                }
                return false;
            }
        });

        return view;
    }

    public QuoteFragment setUsername(String username) {
        this.username = username;
        return this;
    }
    private String getGood(String username){
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        String good = "";
        if(timeOfDay >= 0 && timeOfDay < 12){
            good = "Good Morning, "+username+"!";
        }else if(timeOfDay >= 12 && timeOfDay < 16){
            good = "Good Afternoon, "+ username+"!";
        }else if(timeOfDay >= 16 && timeOfDay < 21){
            good = "Good Evening, "+username+"!";
        }else if(timeOfDay >= 21 && timeOfDay < 24){
            good = "Good Night, "+username+"!";
        }
        return good;
    }

    public void updateUI(){
        todo=ed_todo.getText().toString().trim();
        if(todo.length()>0){
            tv_todomain.setVisibility(View.VISIBLE);
            tv_todomain.setText(todo);
            tv_ask.setText("Today");
            ed_todo.setVisibility(View.INVISIBLE);
        }else{
            tv_ask.setText(R.string.ask);
            ed_todo.setText("");
            ed_todo.setVisibility(View.VISIBLE);
        }
    }
}
