package zeus.minhquan.drumpad;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ImageView pad_1, pad_2, pad_3, pad_4, pad_5, pad_6, pad_7,
            pad_8, pad_9, pad_10, pad_11, pad_12;
    private MediaPlayer pad_1_mp, pad_2_mp, pad_3_mp, pad_4_mp,
            pad_5_mp, pad_6_mp, pad_7_mp, pad_8_mp, pad_9_mp, pad_10_mp, pad_11_mp, pad_12_mp;
    private float count = 100 * .01f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pad_1 = (ImageView) findViewById(R.id.drumPad_1);
        pad_2 = (ImageView) findViewById(R.id.drumPad_2);
        pad_3 = (ImageView) findViewById(R.id.drumPad_3);
        pad_4 = (ImageView) findViewById(R.id.drumPad_4);
        pad_5 = (ImageView) findViewById(R.id.drumPad_5);
        pad_6 = (ImageView) findViewById(R.id.drumPad_6);
        pad_7 = (ImageView) findViewById(R.id.drumPad_7);
        pad_8 = (ImageView) findViewById(R.id.drumPad_8);
        pad_9 = (ImageView) findViewById(R.id.drumPad_9);
        pad_10 = (ImageView) findViewById(R.id.drumPad_10);
        pad_11 = (ImageView) findViewById(R.id.drumPad_11);
        pad_12 = (ImageView) findViewById(R.id.drumPad_12);

        pad_1_mp = MediaPlayer.create(MainActivity.this, R.raw.drum_pad_1);
        pad_2_mp = MediaPlayer.create(MainActivity.this, R.raw.drum_pad_2);
        pad_3_mp = MediaPlayer.create(MainActivity.this, R.raw.drum_pad_3);
        pad_4_mp = MediaPlayer.create(MainActivity.this, R.raw.drum_pad_4);
        pad_5_mp = MediaPlayer.create(MainActivity.this, R.raw.drum_pad_5);
        pad_6_mp = MediaPlayer.create(MainActivity.this, R.raw.drum_pad_6);
        pad_7_mp = MediaPlayer.create(MainActivity.this, R.raw.drum_pad_7);
        pad_8_mp = MediaPlayer.create(MainActivity.this, R.raw.drum_pad_8);
        pad_9_mp = MediaPlayer.create(MainActivity.this, R.raw.drum_pad_9);
        pad_10_mp = MediaPlayer.create(MainActivity.this, R.raw.drum_pad_10);
        pad_11_mp = MediaPlayer.create(MainActivity.this, R.raw.drum_pad_11);
        pad_12_mp = MediaPlayer.create(MainActivity.this, R.raw.drum_pad_12);

        pad_1.setOnClickListener(playSound);
        pad_2.setOnClickListener(playSound);
        pad_3.setOnClickListener(playSound);
        pad_4.setOnClickListener(playSound);
        pad_5.setOnClickListener(playSound);
        pad_6.setOnClickListener(playSound);
        pad_7.setOnClickListener(playSound);
        pad_8.setOnClickListener(playSound);
        pad_9.setOnClickListener(playSound);
        pad_10.setOnClickListener(playSound);
        pad_11.setOnClickListener(playSound);
        pad_12.setOnClickListener(playSound);

    }

    ImageView.OnClickListener playSound = new ImageView.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.drumPad_1:
                    if (pad_1_mp != null) {
                        pad_1_mp.stop();
                        pad_1_mp.release();
                    }
                    pad_1_mp = MediaPlayer.create(MainActivity.this, R.raw.drum_pad_1);
                    pad_1_mp.setVolume(count, count);
                    pad_1_mp.start();
                    break;
                case R.id.drumPad_2:
                    if (pad_2_mp != null) {
                        pad_2_mp.stop();
                        pad_2_mp.release();
                    }
                    pad_2_mp = MediaPlayer.create(MainActivity.this, R.raw.drum_pad_2);
                    pad_2_mp.setVolume(count, count);
                    pad_2_mp.start();
                    break;
                case R.id.drumPad_3:
                    if (pad_3_mp != null) {
                        pad_3_mp.stop();
                        pad_3_mp.release();
                    }
                    pad_3_mp = MediaPlayer.create(MainActivity.this, R.raw.drum_pad_3);
                    pad_3_mp.setVolume(count, count);
                    pad_3_mp.start();
                    break;
                case R.id.drumPad_4:
                    if (pad_4_mp != null) {
                        pad_4_mp.stop();
                        pad_4_mp.release();
                    }
                    pad_4_mp = MediaPlayer.create(MainActivity.this, R.raw.drum_pad_4);
                    pad_4_mp.setVolume(count, count);
                    pad_4_mp.start();
                    break;
                case R.id.drumPad_5:
                    if (pad_5_mp != null) {
                        pad_5_mp.stop();
                        pad_5_mp.release();
                    }
                    pad_5_mp = MediaPlayer.create(MainActivity.this, R.raw.drum_pad_5);
                    pad_5_mp.setVolume(count, count);
                    pad_5_mp.start();
                    break;
                case R.id.drumPad_6:
                    if (pad_6_mp != null) {
                        pad_6_mp.stop();
                        pad_6_mp.release();
                    }
                    pad_6_mp = MediaPlayer.create(MainActivity.this, R.raw.drum_pad_6);
                    pad_6_mp.setVolume(count, count);
                    pad_6_mp.start();
                    break;
                case R.id.drumPad_7:
                    if (pad_7_mp != null) {
                        pad_7_mp.stop();
                        pad_7_mp.release();
                    }
                    pad_7_mp = MediaPlayer.create(MainActivity.this, R.raw.drum_pad_7);
                    pad_7_mp.setVolume(count, count);
                    pad_7_mp.start();
                    break;
                case R.id.drumPad_8:
                    if (pad_8_mp != null) {
                        pad_8_mp.stop();
                        pad_8_mp.release();
                    }
                    pad_8_mp = MediaPlayer.create(MainActivity.this, R.raw.drum_pad_8);
                    pad_8_mp.setVolume(count, count);
                    pad_8_mp.start();
                    break;
                case R.id.drumPad_9:
                    if (pad_9_mp != null) {
                        pad_9_mp.stop();
                        pad_9_mp.release();
                    }
                    pad_9_mp = MediaPlayer.create(MainActivity.this, R.raw.drum_pad_9);
                    pad_9_mp.setVolume(count, count);
                    pad_9_mp.start();
                    break;
                case R.id.drumPad_10:
                    if (pad_10_mp != null) {
                        pad_10_mp.stop();
                        pad_10_mp.release();
                    }
                    pad_10_mp = MediaPlayer.create(MainActivity.this, R.raw.drum_pad_10);
                    pad_10_mp.setVolume(count, count);
                    pad_10_mp.start();
                    break;
                case R.id.drumPad_11:
                    if (pad_11_mp != null) {
                        pad_11_mp.stop();
                        pad_11_mp.release();
                    }
                    pad_11_mp = MediaPlayer.create(MainActivity.this, R.raw.drum_pad_11);
                    pad_11_mp.setVolume(count, count);
                    pad_11_mp.start();
                    break;
                case R.id.drumPad_12:
                    if (pad_12_mp != null) {
                        pad_12_mp.stop();
                        pad_12_mp.release();
                    }
                    pad_12_mp = MediaPlayer.create(MainActivity.this, R.raw.drum_pad_12);
                    pad_12_mp.setVolume(count, count);
                    pad_12_mp.start();
                    break;
                default:
                    break;
            }
        }
    };

    // event handler for back button press
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                pad_1_mp.stop();
                                pad_1_mp.release();
                                pad_2_mp.stop();
                                pad_2_mp.release();
                                pad_3_mp.stop();
                                pad_3_mp.release();
                                pad_4_mp.stop();
                                pad_4_mp.release();
                                pad_5_mp.stop();
                                pad_5_mp.release();
                                pad_6_mp.stop();
                                pad_6_mp.release();
                                pad_7_mp.stop();
                                pad_7_mp.release();
                                pad_8_mp.stop();
                                pad_8_mp.release();
                                pad_9_mp.stop();
                                pad_9_mp.release();
                                pad_10_mp.stop();
                                pad_10_mp.release();
                                pad_11_mp.stop();
                                pad_11_mp.release();
                                pad_12_mp.stop();
                                pad_12_mp.release();
                                MainActivity.this.finish();
                            }
                        }).setNegativeButton("No", null).show();
    }
}
