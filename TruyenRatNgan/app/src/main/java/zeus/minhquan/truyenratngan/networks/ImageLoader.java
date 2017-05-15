package zeus.minhquan.truyenratngan.networks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import zeus.minhquan.truyenratngan.R;

/**
 * Created by QuanT on 5/14/2017.
 */

public class ImageLoader extends AsyncTask<String,Void,Bitmap> {
    public ImageLoader() {
    }

    private ImageView view;
    private String urlString;
    private String imageTag;


    public ImageLoader setView(ImageView view) {
        this.view = view;
        this.imageTag = (view.getTag()==null)? "" : view.getTag().toString();
        return this;
    }
    public void loadImage(String urlString){
        if(!urlString.equals(imageTag)){
            view.setImageResource(R.drawable.progress_animation);
            execute(urlString);
        }
    }

//    @Override
//    protected void onPreExecute() {
//            view.setImageResource(R.drawable.ic_do_not_disturb_black_24dp);
//        //set image loading
//        super.onPreExecute();
//    }

    @Override
    protected Bitmap doInBackground(String... params) {
        urlString = params[0];
        //1: open connection
        if(urlString.equals(imageTag)){
            return null;
        }
        try{
            URL url = new URL(urlString);
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
            view.setImageBitmap(bitmap);
            view.setTag(urlString);
        }
    }
}
