package widgets.dinesh.com.histogramseekbar;

import android.graphics.PointF;
import android.support.annotation.NonNull;

/**
 * Created by ajmac1005 on 23/07/17.
 */

public class SortablePointF extends PointF implements Comparable<SortablePointF>{
    public SortablePointF(float x, float y) {
        super(x, y);
    }

    @Override
    public int compareTo(@NonNull SortablePointF point) {
        if(this.x < point.x)
            return -1;
        if(this.x > point.x)
            return 1;
        return 0;
    }
}
