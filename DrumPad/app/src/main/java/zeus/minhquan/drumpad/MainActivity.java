package zeus.minhquan.drumpad;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.audiofx.Visualizer;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import zeus.minhquan.drumpad.touches.Touch;
import zeus.minhquan.drumpad.touches.TouchManager;

import static android.Manifest.permission.CAPTURE_AUDIO_OUTPUT;
import static android.Manifest.permission.MODIFY_AUDIO_SETTINGS;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.view.MotionEvent.ACTION_POINTER_DOWN;
import static android.view.MotionEvent.ACTION_POINTER_UP;

public class MainActivity extends AppCompatActivity {
    private float count = 100 * .01f;
    private List<ImageView> imageViewList;
    private List<PressKeyInfo> pressKeyInfoList;
    private static final int NUMBER_OF_MUSIC = 3;
    //Recoder
    private ImageView recoder, play_recorder, play, stop, next, pre;
    String AudioSavePathInDevice = null;
    MediaRecorder mediaRecorder;
    Random random;
    String RandomAudioFileName = "ABCDEFGHIJKLMNOP";
    public static final int RequestPermissionCode = 2;
    MediaPlayer mediaRecordPlayer = null, musicPlayer;
    private boolean isStartRecording = true;
    private boolean isPlayRecord = true;
    private boolean isRecording = true;
    private boolean isPlaying = false;
    private boolean isPlayingMusic = false;
    private int musicIndex = 2;

    //----------
    //Visualizer
    private Visualizer audioOutput = null;
    AudioTrack visualizedTrack = null;


    class PressKeyInfo {

        public ImageView getDrum() {
            return view;
        }

        public int getPointerID() {
            return pointerID;
        }

        private ImageView view;
        private int pointerID;

        public PressKeyInfo(ImageView view, int pointerID) {
            this.view = view;
            this.pointerID = pointerID;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        next = (ImageView) findViewById(R.id.iv_play_next);
        stop = (ImageView) findViewById(R.id.iv_stop);
        play = (ImageView) findViewById(R.id.iv_play);
        pre = (ImageView) findViewById(R.id.iv_pre);
        next.setOnClickListener(playMusic);
        stop.setOnClickListener(playMusic);
        play.setOnClickListener(playMusic);
        pre.setOnClickListener(playMusic);
        if (checkPermission()) {
            createVisualizer();
        } else {
            requestPermission();
        }


        imageViewList = new ArrayList<>();
        imageViewList.add((ImageView) findViewById(R.id.drumPad_1));
        imageViewList.add((ImageView) findViewById(R.id.drumPad_2));
        imageViewList.add((ImageView) findViewById(R.id.drumPad_3));
        imageViewList.add((ImageView) findViewById(R.id.drumPad_4));
        imageViewList.add((ImageView) findViewById(R.id.drumPad_5));
        imageViewList.add((ImageView) findViewById(R.id.drumPad_6));
        imageViewList.add((ImageView) findViewById(R.id.drumPad_7));
        imageViewList.add((ImageView) findViewById(R.id.drumPad_8));
        imageViewList.add((ImageView) findViewById(R.id.drumPad_9));
        imageViewList.add((ImageView) findViewById(R.id.drumPad_10));
        imageViewList.add((ImageView) findViewById(R.id.drumPad_11));
        imageViewList.add((ImageView) findViewById(R.id.drumPad_12));

        random = new Random();
        recoder = (ImageView) findViewById(R.id.iv_recoder);
        play_recorder = (ImageView) findViewById(R.id.iv_play_record);
        recoder.setOnClickListener(recording);
        play_recorder.setOnClickListener(playRecord);
        SoundManager.loadSoundIntoList(this);
        pressKeyInfoList = new ArrayList<>();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        List<Touch> touches = TouchManager.toTouches(event);
        //  Log.d(TAG, String.format("onTouches %s", touches));
        if (touches.size() > 0) {
            Touch firstTouch = touches.get(0);
            if (firstTouch.getAction() == MotionEvent.ACTION_DOWN || firstTouch.getAction() == ACTION_POINTER_DOWN) {
                ImageView pressedKey = findPressKey(firstTouch);
                if (pressedKey != null && !isContainsKeyInfoWith(pressedKey)) {
                    pressKeyInfoList.add(new PressKeyInfo(pressedKey, firstTouch.getId()));
                    setPress(pressedKey, true);
                    SoundManager.playSound(pressedKey.getTag().toString());
                }
            } else if (firstTouch.getAction() == MotionEvent.ACTION_UP || firstTouch.getAction() == ACTION_POINTER_UP) {
                Iterator<PressKeyInfo> inforIterator = pressKeyInfoList.iterator();
                while (inforIterator.hasNext()) {
                    PressKeyInfo pressKeyInfo = inforIterator.next();
                    if (pressKeyInfo.getPointerID() == firstTouch.getId()) {
                        inforIterator.remove();
                        setPress(pressKeyInfo.getDrum(), false);
                    }
                }
            } else if (firstTouch.getAction() == MotionEvent.ACTION_MOVE) {
                for (Touch touch : touches) {
                    ImageView pressedKey = findPressKey(touch);
                    if (pressedKey != null && !isContainsKeyInfoWith(pressedKey)) {
                        pressKeyInfoList.add(new PressKeyInfo(pressedKey, touch.getId()));
                        SoundManager.playSound(pressedKey.getTag().toString());
                        setPress(pressedKey, true);
                    }
                    Iterator<PressKeyInfo> inforIterator = pressKeyInfoList.iterator();
                    while (inforIterator.hasNext()) {
                        PressKeyInfo pressKeyInfo = inforIterator.next();
                        if (pressKeyInfo.getPointerID() == touch.getId() && !touch.isInside(pressKeyInfo.getDrum())) {
                            //touch moved outside view
                            inforIterator.remove();
                            setPress(pressKeyInfo.getDrum(), false);
                        }
                    }
                }
            }
        }


        return super.onTouchEvent(event);
    }


    private ImageView findPressKey(Touch touch) {
        for (int i = 0; i < imageViewList.size(); i++) {
            if (touch.isInside(imageViewList.get(i))) {
                return imageViewList.get(i);
            }
        }
        return null;
    }


    private void setPress(ImageView vKey, boolean isPress) {
        if (isPress) {
            if (imageViewList.contains(vKey)) {
                vKey.setImageResource(R.drawable.green);
            }
        } else {
            if (imageViewList.contains(vKey)) {
                vKey.setImageResource(R.drawable.red);
            }
        }
    }


    private boolean isContainsKeyInfoWith(ImageView view) {
        for (PressKeyInfo pressKeyInfo : pressKeyInfoList) {
            if (pressKeyInfo.getDrum() == view) {
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
//                                if(mediaRecordPlayer!=null) {
//                                    mediaRecordPlayer.stop();
//                                    mediaRecordPlayer.release();
//                                }
                                if(musicPlayer!=null) {
                                    musicPlayer.stop();
                                    musicPlayer.release();
                                }
                                MainActivity.this.finish();
                            }
                        }).setNegativeButton("No", null).show();
    }

//    ImageView.OnClickListener playRecord = new ImageView.OnClickListener() {
//        @Override
//        public void onClick(View v) throws IllegalArgumentException,
//                SecurityException, IllegalStateException {
//            if (isRecording) {
//                Toast.makeText(MainActivity.this, "Please stop recorder to play",
//                        Toast.LENGTH_LONG).show();
//                return;
//            }
//            if (isPlayRecord) {
//                mediaRecordPlayer = new MediaPlayer();
//                try {
//                    mediaRecordPlayer.setDataSource(AudioSavePathInDevice);
//                    mediaRecordPlayer.prepare();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                isPlayRecord = false;
//                isStartRecording = false;
//                isPlaying = true;
//                mediaRecordPlayer.start();
//                play_recorder.setImageResource(R.drawable.stop_play_record);
//                Toast.makeText(MainActivity.this, "Recording Playing",
//                        Toast.LENGTH_LONG).show();
//            } else {
//                if (mediaRecordPlayer != null) {
//                    play_recorder.setImageResource(R.drawable.play_record);
//                    isPlayRecord = true;
//                    isStartRecording = true;
//                    isPlaying = false;
//                    mediaRecordPlayer.stop();
//                    mediaRecordPlayer.release();
//                    MediaRecorderReady();
//                }
//            }
//            // isPlayRecord = !isPlayRecord;
//
//        }
//    };

//    ImageView.OnClickListener recording = new ImageView.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            if (isPlaying) {
//                Toast.makeText(MainActivity.this, "Please stop player record to recording",
//                        Toast.LENGTH_LONG).show();
//                return;
//            }
//            if (isStartRecording) {
//                if (checkPermission()) {
//                    AudioSavePathInDevice = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
//                            CreateRandomAudioFileName(5) + "AudioRecording.3gp";
//
//                    MediaRecorderReady();
//                    isPlayRecord = false;
//                    isStartRecording = false;
//                    isRecording = true;
//
//                    try {
//                        mediaRecorder.prepare();
//                        mediaRecorder.start();
//
//                    } catch (IllegalStateException e) {
//                        // TODO Auto-generated catch block
//                        Toast.makeText(MainActivity.this, "Recording error",
//                                Toast.LENGTH_LONG).show();
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        // TODO Auto-generated catch block
//                        Toast.makeText(MainActivity.this, "Recording error",
//                                Toast.LENGTH_LONG).show();
//                        e.printStackTrace();
//                    }
//                    recoder.setImageResource(R.drawable.stop_recording);
//                    Toast.makeText(MainActivity.this, "Recording started",
//                            Toast.LENGTH_LONG).show();
//                } else {
//                    requestPermission();
//                }
//            } else {
//                if (mediaRecorder != null) {
//                    recoder.setImageResource(R.drawable.start_recording);
//                    isStartRecording = true;
//                    isPlayRecord = true;
//                    isRecording = false;
//                    try {
//                        mediaRecorder.stop();
//                    }catch (Exception e){
//
//                    }
//
//                    Toast.makeText(MainActivity.this, "Recording Completed",
//                            Toast.LENGTH_LONG).show();
//                }
//            }
//            //  isStartRecording = !isStartRecording;
//
//        }
//    };

//    public void MediaRecorderReady() {
//        mediaRecorder = new MediaRecorder();
//        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
//        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
//        mediaRecorder.setOutputFile(AudioSavePathInDevice);
//    }

    public String CreateRandomAudioFileName(int string) {
        StringBuilder stringBuilder = new StringBuilder(string);
        int i = 0;
        while (i < string) {
            stringBuilder.append(RandomAudioFileName.
                    charAt(random.nextInt(RandomAudioFileName.length())));

            i++;
        }
        return stringBuilder.toString();
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO, MODIFY_AUDIO_SETTINGS}, RequestPermissionCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length > 0) {
                    boolean StoragePermission = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission = grantResults[1] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean ModifierPermission = grantResults[2] ==
                            PackageManager.PERMISSION_GRANTED;

                    if (StoragePermission && RecordPermission && ModifierPermission) {
                        Toast.makeText(MainActivity.this, "Permission Granted",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),
                WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),
                RECORD_AUDIO);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(),
                MODIFY_AUDIO_SETTINGS);
        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED;
    }

    ImageView.OnClickListener playMusic = new ImageView.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getTag().toString()) {
                case "next":
                    stopMusic();
                    if(musicIndex<NUMBER_OF_MUSIC){
                        musicIndex++;
                    }else{
                        musicIndex=1;
                    }
                    playMusic();
                    break;
                case "stop":
                    stopMusic();
                    break;
                case "play":
                    playMusic();
                    break;
                case "pre":
                    stopMusic();
                    if(musicIndex>1){
                        musicIndex--;
                    }else{
                        musicIndex=3;
                    }
                    playMusic();
                    break;
            }
        }
    };

    public void stopMusic() {
        if (musicPlayer != null) {
            play.setImageResource(R.drawable.play);
            isPlayingMusic = false;
            musicPlayer.stop();
            musicPlayer.release();
            musicPlayer = null;
        }
    }

    public void playMusic() {
        if (!isPlayingMusic) {
            if(musicPlayer==null) {
                musicPlayer = new MediaPlayer();
                try {
                    AssetFileDescriptor afd = getAssets().openFd("music_"+musicIndex+".mp3");
                    musicPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                    musicPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                musicPlayer.start();
                play.setImageResource(R.drawable.pause);
            }else{
                play.setImageResource(R.drawable.pause);
                musicPlayer.start();
            }
            isPlayingMusic = true;

        }else{
            if(musicPlayer!=null){
                play.setImageResource(R.drawable.play);
                musicPlayer.pause();
                isPlayingMusic = false;
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        destroyVisualizer();
    }
        ImageView.OnClickListener playRecord = new ImageView.OnClickListener() {
        @Override
        public void onClick(View v) throws IllegalArgumentException,
                SecurityException, IllegalStateException {
            if (isRecording) {
                Toast.makeText(MainActivity.this, "Please stop recorder to play",
                        Toast.LENGTH_LONG).show();
                return;
            }
            if (isPlayRecord) {
                isPlayRecord = false;
                isStartRecording = false;
                isPlaying = true;
                play_recorder.setImageResource(R.drawable.stop_play_record);
                Toast.makeText(MainActivity.this, "Recording Playing",
                        Toast.LENGTH_LONG).show();
            } else {
                if (visualizedTrack != null) {
                    play_recorder.setImageResource(R.drawable.play_record);
                    isPlayRecord = true;
                    isStartRecording = true;
                    isPlaying = false;
            //        visualizedTrack.pause();
 //                   MediaRecorderReady();
                }
            }
            // isPlayRecord = !isPlayRecord;

        }
    };

    ImageView.OnClickListener recording = new ImageView.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isPlaying) {
                Toast.makeText(MainActivity.this, "Please stop player record to recording",
                        Toast.LENGTH_LONG).show();
                return;
            }
            if (isStartRecording) {
                if (checkPermission()) {
                    isPlayRecord = false;
                    isStartRecording = false;
                    isRecording = true;
                    startVisualizer();
                    recoder.setImageResource(R.drawable.stop_recording);
                    Toast.makeText(MainActivity.this, "Recording started",
                            Toast.LENGTH_LONG).show();
                } else {
                    requestPermission();
                }
            } else {
                    recoder.setImageResource(R.drawable.start_recording);
                    isStartRecording = true;
                    isPlayRecord = true;
                    isRecording = false;
                    stopVisualizer();
                    destroyVisualizer();
                    Toast.makeText(MainActivity.this, "Recording Completed",
                            Toast.LENGTH_LONG).show();
            }
            //  isStartRecording = !isStartRecording;
        }
    };

    private void createVisualizer(){
        final int minBufferSize = AudioTrack.getMinBufferSize(Visualizer.getMaxCaptureRate(), AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_8BIT);
        visualizedTrack = new AudioTrack(AudioManager.STREAM_MUSIC, Visualizer.getMaxCaptureRate(), AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_8BIT, minBufferSize, AudioTrack.MODE_STREAM);
  //      visualizedTrack.play();
        audioOutput = new Visualizer(0); // get output audio stream

        audioOutput.setDataCaptureListener(new Visualizer.OnDataCaptureListener() {
            @Override
            public void onWaveFormDataCapture(Visualizer visualizer, byte[] waveform, int samplingRate) {
                Log.d("Test: ",waveform.length+"");

                visualizedTrack.write(waveform, 0, waveform.length);
            }

            @Override
            public void onFftDataCapture(Visualizer visualizer, byte[] fft, int samplingRate) {

            }
        }, Visualizer.getMaxCaptureRate(), true, false); // waveform not freq data


    }

    private void startVisualizer(){
        audioOutput.setEnabled(true);
    }

    private void stopVisualizer() {
        audioOutput.setEnabled(false);
    }

    private void destroyVisualizer(){
        visualizedTrack.stop();
//        visualizedTrack.release();
        writeToFile();
        audioOutput.release();
    }

    private void writeToFile(){
      //  String filepath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_record");
        myDir.mkdirs();
        String fname = "REFERENCE.wav";
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete ();
        try {
            file.createNewFile();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        int i = 0;
        int bufferSize = 512;
        byte[] s = new byte[bufferSize];
        try {
            FileInputStream fin = new FileInputStream(file);
            DataInputStream dis = new DataInputStream(fin);
            visualizedTrack.play();
       //
            do{
                visualizedTrack.write(s, 0, i);

            }while((i = dis.read(s, 0, bufferSize)) > -1);
            visualizedTrack.stop();
            visualizedTrack.release();
            dis.close();
            fin.close();

        } catch (FileNotFoundException e) {
            // TODO
            e.printStackTrace();
        } catch (IOException e) {
            // TODO
            e.printStackTrace();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }



}
