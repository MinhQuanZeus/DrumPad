package zeus.minhquan.randomquote.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;

import zeus.minhquan.randomquote.R;
import zeus.minhquan.randomquote.fragments.LoginFragment;
import zeus.minhquan.randomquote.fragments.QuoteFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayStartScreen();
    }

    private void displayStartScreen() {
        changeFragment(new LoginFragment(),false);
    }

    public void changeFragment(Fragment fragment, boolean addToBackStack) {
        if(addToBackStack){
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.push_left_in, R.anim.push_left_out)
                    .replace(R.id.fl_main, fragment)
                    .addToBackStack(null).commit();
        }else{
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.push_left_in, R.anim.push_left_out)
                    .replace(R.id.fl_main, fragment)
                    .commit();
        }
    }
}
