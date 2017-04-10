package zeus.minhquan.drumpad;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private float count = 100 * .01f;
    private List<Drum> drumList;
    private List<PressKeyInfo> pressKeyInfoList;

    private class Drum {
        private ImageView ivDrum;
        private MediaPlayer mpDrum;

        public Drum(ImageView ivDrum, MediaPlayer mpDrum) {
            this.ivDrum = ivDrum;
            this.mpDrum = mpDrum;
        }

        public ImageView getIvDrum() {
            return ivDrum;
        }

        public void setIvDrum(ImageView ivDrum) {
            this.ivDrum = ivDrum;
        }

        public MediaPlayer getMpDrum() {
            return mpDrum;
        }

        public void setMpDrum(MediaPlayer mpDrum) {
            this.mpDrum = mpDrum;
        }
    }

    class PressKeyInfo {

        public Drum getDrum() {
            return drum;
        }

        public int getPointerID() {
            return pointerID;
        }

        private Drum drum;
        private int pointerID;

        public PressKeyInfo(Drum drum, int pointerID) {
            this.drum = drum;
            this.pointerID = pointerID;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drumList = new ArrayList<>();
        drumList.add(new Drum((ImageView) findViewById(R.id.drumPad_1), MediaPlayer.create(MainActivity.this, R.raw.drum_pad_1)));
        drumList.add(new Drum((ImageView) findViewById(R.id.drumPad_2), MediaPlayer.create(MainActivity.this, R.raw.drum_pad_1)));
        drumList.add(new Drum((ImageView) findViewById(R.id.drumPad_3), MediaPlayer.create(MainActivity.this, R.raw.drum_pad_1)));
        drumList.add(new Drum((ImageView) findViewById(R.id.drumPad_4), MediaPlayer.create(MainActivity.this, R.raw.drum_pad_1)));
        drumList.add(new Drum((ImageView) findViewById(R.id.drumPad_5), MediaPlayer.create(MainActivity.this, R.raw.drum_pad_1)));
        drumList.add(new Drum((ImageView) findViewById(R.id.drumPad_6), MediaPlayer.create(MainActivity.this, R.raw.drum_pad_1)));
        drumList.add(new Drum((ImageView) findViewById(R.id.drumPad_7), MediaPlayer.create(MainActivity.this, R.raw.drum_pad_1)));
        drumList.add(new Drum((ImageView) findViewById(R.id.drumPad_8), MediaPlayer.create(MainActivity.this, R.raw.drum_pad_1)));
        drumList.add(new Drum((ImageView) findViewById(R.id.drumPad_9), MediaPlayer.create(MainActivity.this, R.raw.drum_pad_1)));
        drumList.add(new Drum((ImageView) findViewById(R.id.drumPad_10), MediaPlayer.create(MainActivity.this, R.raw.drum_pad_1)));
        drumList.add(new Drum((ImageView) findViewById(R.id.drumPad_11), MediaPlayer.create(MainActivity.this, R.raw.drum_pad_1)));
        drumList.add(new Drum((ImageView) findViewById(R.id.drumPad_12), MediaPlayer.create(MainActivity.this, R.raw.drum_pad_1)));
        pressKeyInfoList = new ArrayList<>();


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        int pointerIndex = MotionEventCompat.getActionIndex(event);
//        int pointerId = event.getPointerId(pointerIndex);
//        float pointerX = event.getX(pointerIndex);
//        float pointerY = event.getY(pointerIndex);
//          int pointerAction = event.getActionMasked();
//        if (pointerAction == MotionEvent.ACTION_MOVE) {
//            for (pointerIndex = 0; pointerIndex < event.getPointerCount(); pointerIndex++) {
//               pointerId = event.getPointerId(pointerIndex);
//               pointerX = event.getX(pointerIndex);
//               pointerY = event.getY(pointerIndex);
//                for (int i = 0; i < pressKeyInfoList.size(); i++) {
//                    PressKeyInfo pressKeyInfo = pressKeyInfoList.get(i);
//                    if (pressKeyInfo.getPointerID() == pointerId && !isInside(pointerX, pointerY, pressKeyInfo.getDrum().getIvDrum())) {
//                        //touch moved outside view
//                        pressKeyInfoList.remove(i);
//                        setPress(pressKeyInfo.getDrum(), false);
//                    }
//
//                }
//            }
//        }
        for (int pointerIndex = 0; pointerIndex < event.getPointerCount(); pointerIndex++) {
            int pointerId = event.getPointerId(pointerIndex);
            float pointerX = event.getX(pointerIndex);
            float pointerY = event.getY(pointerIndex);
            int pointerAction = event.getActionMasked();
            if (pointerAction == MotionEvent.ACTION_MOVE) {
                for (int i = 0; i < pressKeyInfoList.size(); i++) {
                    PressKeyInfo pressKeyInfo = pressKeyInfoList.get(i);
                    if (pressKeyInfo.getPointerID() == pointerId && !isInside(pointerX, pointerY, pressKeyInfo.getDrum().getIvDrum())) {
                        //touch moved outside view
                        pressKeyInfoList.remove(i);
                        setPress(pressKeyInfo.getDrum(), false, false);
                    }else if(pressKeyInfo.getPointerID() == pointerId && isInside(pointerX, pointerY, pressKeyInfo.getDrum().getIvDrum())){
                        setPress(pressKeyInfo.getDrum(), true, false);
                    }


                }
            }

            Drum pressedKey = findPressKey(pointerX, pointerY);
            if (pressedKey != null) {
                if (pointerAction == MotionEvent.ACTION_DOWN || pointerAction == MotionEvent.ACTION_POINTER_DOWN || pointerAction == MotionEvent.ACTION_MOVE) {
                    if (!isContainsKeyInfoWith(pressedKey)) {
                        pressKeyInfoList.add(new PressKeyInfo(pressedKey, pointerId));
                        setPress(pressedKey, true, true);
                    }else{
                        setPress(pressedKey,true,false);
                    }

                }
                if (pointerAction == MotionEvent.ACTION_UP || pointerAction == MotionEvent.ACTION_POINTER_UP) {
                    for (int i = 0; i < pressKeyInfoList.size(); i++) {
                        PressKeyInfo pressKeyInfo = pressKeyInfoList.get(i);
                        if (pressKeyInfo.getPointerID() == pointerId) {
                            pressKeyInfoList.remove(i);
                        }
                    }
                    setPress(pressedKey, false,false);
                }
            }
        }
        return super.onTouchEvent(event);
    }


    private Drum findPressKey(float pointerX, float pointerY) {
        for (int i = 0; i < drumList.size(); i++) {
            if (isInside(pointerX, pointerY, drumList.get(i).getIvDrum())) {
                return drumList.get(i);
            }
        }
        return null;
    }


    private boolean isInside(float x, float y, View v) {

        int[] location = new int[2];
        v.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];

        int right = left + v.getWidth();
        int button = top + v.getHeight();
        return x > left && x < right && y < button && y > top;
    }


    private void setPress(Drum drum, boolean isPress, boolean isPlay) {
        if (isPress) {
            if (drumList.contains(drum)) {
                int index = drumList.indexOf(drum);
                drum.getIvDrum().setImageResource(R.drawable.green);
                if(isPlay) {
                    if (drum.getMpDrum() != null) {
                        drum.getMpDrum().stop();
                        drum.getMpDrum().reset();
                        drum.getMpDrum().release();
                    }
                    switch (index) {
                        case 0:
                            drum.setMpDrum(MediaPlayer.create(MainActivity.this, R.raw.drum_pad_1));
                            break;
                        case 1:
                            drum.setMpDrum(MediaPlayer.create(MainActivity.this, R.raw.drum_pad_2));
                            break;
                        case 2:
                            drum.setMpDrum(MediaPlayer.create(MainActivity.this, R.raw.drum_pad_3));
                            break;
                        case 3:
                            drum.setMpDrum(MediaPlayer.create(MainActivity.this, R.raw.drum_pad_4));
                            break;
                        case 4:
                            drum.setMpDrum(MediaPlayer.create(MainActivity.this, R.raw.drum_pad_5));
                            break;
                        case 5:
                            drum.setMpDrum(MediaPlayer.create(MainActivity.this, R.raw.drum_pad_6));
                            break;
                        case 6:
                            drum.setMpDrum(MediaPlayer.create(MainActivity.this, R.raw.drum_pad_7));
                            break;
                        case 7:
                            drum.setMpDrum(MediaPlayer.create(MainActivity.this, R.raw.drum_pad_8));
                            break;
                        case 8:
                            drum.setMpDrum(MediaPlayer.create(MainActivity.this, R.raw.drum_pad_9));
                            break;
                        case 9:
                            drum.setMpDrum(MediaPlayer.create(MainActivity.this, R.raw.drum_pad_10));
                            break;
                        case 10:
                            drum.setMpDrum(MediaPlayer.create(MainActivity.this, R.raw.drum_pad_11));
                            break;
                        case 11:
                            drum.setMpDrum(MediaPlayer.create(MainActivity.this, R.raw.drum_pad_12));
                            break;
                    }
                    drum.getMpDrum().setVolume(count, count);
                    drum.getMpDrum().start();
                }
            }
        } else {
            if (drumList.contains(drum)) {
                drum.getIvDrum().setImageResource(R.drawable.red);
            }
        }
    }

    private boolean isContainsKeyInfoWith(Drum drum) {
        for (PressKeyInfo pressKeyInfo : pressKeyInfoList) {
            if (pressKeyInfo.getDrum() == drum) {
                return true;
            }
        }
        return false;
    }


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
                                for (Drum drum : drumList) {
                                    drum.getMpDrum().stop();
                                    drum.getMpDrum().release();
                                }
                                MainActivity.this.finish();
                            }
                        }).setNegativeButton("No", null).show();
    }
}
