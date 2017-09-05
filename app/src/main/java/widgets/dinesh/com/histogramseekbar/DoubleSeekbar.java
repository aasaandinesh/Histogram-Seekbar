package widgets.dinesh.com.histogramseekbar;

import android.content.Context;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.crystal.crystalrangeseekbar.widgets.BubbleThumbRangeSeekbar;


/**
 * Created by ajmac1005 on 05/07/17.
 */

public class DoubleSeekbar extends BubbleThumbRangeSeekbar {
    public DoubleSeekbar(Context context) {
        super(context);
    }

    public DoubleSeekbar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DoubleSeekbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RectF getLeftThumbRect(){
        return super.getLeftThumbRect();
    }

    public RectF getRightThumbRect(){
        return super.getRightThumbRect();
    }

    public float getThumbHeight(){
        return super.getThumbHeight();
    }

    public float getBarHeight(){
        return super.getBarHeight();
    }

    @Override
    protected void touchDown(float x, float y) {

    }

    @Override
    protected void touchUp(float x, float y) {

    }
}
