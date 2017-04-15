package zeus.minhquan.drumpad.touches;

import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

import static android.view.MotionEvent.*;

/**
 * Created by QuanT on 4/14/2017.
 */

public class TouchManager {
    public static List<Touch> toTouches(MotionEvent event){
        int action = event.getActionMasked();
        ArrayList<Touch> touches = new ArrayList<>();
        if(action == ACTION_DOWN||action==ACTION_POINTER_DOWN||action==ACTION_UP
                ||action==ACTION_POINTER_UP){
            int pointerIndex = event.getActionIndex();
            float pointerX = event.getX(pointerIndex);
            float pointerY = event.getY(pointerIndex);
            int pointerID = event.getPointerId(pointerIndex);
            touches.add(new Touch(pointerX,pointerY,pointerID,action));
        }else if(action == ACTION_MOVE){
            for(int pointerIndex = 0;pointerIndex<event.getPointerCount();pointerIndex++){
                float pointerX = event.getX(pointerIndex);
                float pointerY = event.getY(pointerIndex);
                int pointerID = event.getPointerId(pointerIndex);
                touches.add(new Touch(pointerX,pointerY,pointerID,action));
            }
        }



        return touches;
    }
}
