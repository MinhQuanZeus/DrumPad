package zeus.minhquan.randomquote.networks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import zeus.minhquan.randomquote.R;

/**
 * Created by QuanT on 5/20/2017.
 */

public class ImageLoader extends AsyncTask<Void,Void,Bitmap> {

    private final String IMAGE_URL = "https://source.unsplash.com/random/545x930";
    private ImageView imageView;
    private String imageTag;

    public ImageLoader(ImageView imageView){
        this.imageView = imageView;
        this.imageTag = (imageView.getTag()==null)? "" : imageView.getTag().toString();
    }

    public void loadImage(){
        if(!IMAGE_URL.equals(imageTag)){
            imageView.setImageResource(R.drawable.progress_animation);
            execute();
        }
    }
    @Override
    protected Bitmap doInBackground(Void... params) {
        //1: open connection
        if(IMAGE_URL.equals(imageTag)){
            return null;
        }
        try{
            URL url = new URL(IMAGE_URL);
            //2Get Stream
            InputStream inputStream = url.openStream();

            //3: get bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        //4: display bitmap
        if(bitmap!=null) {
            imageView.setImageBitmap(bitmap);
            imageView.setTag(IMAGE_URL);
        }
    }
}
