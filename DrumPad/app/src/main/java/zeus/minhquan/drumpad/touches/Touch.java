package zeus.minhquan.drumpad.touches;

import android.view.View;

/**
 * Created by QuanT on 4/14/2017.
 */

public class Touch {
    private float x;
    private float y;
    private int id;
    private int action;

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getId() {
        return id;
    }

    public int getAction() {
        return action;
    }

    public Touch(float x, float y, int id, int action) {

        this.x = x;
        this.y = y;
        this.id = id;
        this.action = action;
    }
    public boolean isInside(View view){
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];

        int right = left + view.getWidth();
        int button = top + view.getHeight();
        return x > left && x < right && y < button && y > top;
    }
}
