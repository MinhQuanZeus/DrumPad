package zeus.minhquan.screentransactiondemo;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CountDownTimer countDownTimer = new CountDownTimer(3000,1000) {


            @Override
            public void onTick(long millisUntilFinished) {
                Log.d(TAG,"onTick: ");
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        };
        countDownTimer.start();
    }
}
