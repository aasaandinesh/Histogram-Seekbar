package widgets.dinesh.com.histogramseekbar;

import android.content.Context;
import android.graphics.PointF;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by ajmac1005 on 23/07/17.
 */

public class SeekbarHistogramView extends LinearLayout implements OnRangeSeekbarChangeListener {

    private final Map<Integer, Integer> mItems;
    private SalaryFilterView salaryFilterView;
    private DoubleSeekbar seekbar;



    public SeekbarHistogramView(Context context, Map<Integer, Integer> items) {
        super(context);
        this.mItems = items;

        setMinimumHeight(WRAP_CONTENT);
        setMinimumWidth(MATCH_PARENT);
        setOrientation(VERTICAL);
        initViews();
    }

    private void initViews() {
        List<PointF> points = generatePoints(mItems);
        salaryFilterView = new SalaryFilterView(getContext(), points );
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200);


         seekbar = new DoubleSeekbar(getContext());
         LinearLayout.LayoutParams seekbarParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

         //Setting up -ve top margin to overlap exactly with graph
         seekbarParams.setMargins(0 ,0-(int)((seekbar.getHeight() + seekbar.getThumbHeight())/2),0,0);
        seekbar.setLayoutParams(seekbarParams);

        seekbar.setOnRangeSeekbarChangeListener(this);
        params.setMargins( (int)( seekbar.getThumbHeight()/2) , 0, (int)( seekbar.getThumbHeight()/2), 0);
        salaryFilterView.setLayoutParams(params);
        this.addView(salaryFilterView);
        this.addView(seekbar);


    }

    private List<PointF> generatePoints(Map<Integer, Integer> items) {
        List<SortablePointF> points = new ArrayList<>();
        float rangeX = getRangeX(items);
        float rangeY = getRangeY(items);
        Iterator it = items.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            points.add(new SortablePointF((int)pair.getKey()/(rangeX*1.25f), (int)pair.getValue()/(rangeY*1.25f)));
        }
        Collections.sort(points);

        return convertToPoints(points) ;

    }

    private List<PointF> convertToPoints(List<SortablePointF> points) {
        List<PointF> pointFS = new ArrayList<>();
        for(SortablePointF sortablePointF: points){
            pointFS.add(new PointF(sortablePointF.x, sortablePointF.y));
        }
        return pointFS;
    }


    private float getRangeY(Map<Integer, Integer> items) {
        Iterator it = items.entrySet().iterator();
        int min = -1;
        int max = -1;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if((int)pair.getValue() < min || min == -1){
                min = (int) pair.getValue();
            }

            if((int)pair.getValue() > max || max == -1){
                max = (int) pair.getValue();
            }
        }

        return max ;
    }

    private float getRangeX(Map<Integer, Integer> items) {
        Iterator it = items.entrySet().iterator();
        int min = -1;
        int max = -1;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if((int)pair.getKey() < min || min == -1){
                min = (int) pair.getKey();
            }

            if((int)pair.getKey() > max || max == -1){
                max = (int) pair.getKey();
            }
        }

        return max ;

    }


    @Override
    public void valueChanged(Number min, Number max) {
        Float minX = seekbar.getLeftThumbRect().centerX();
        Float maxX = seekbar.getRightThumbRect().centerX();
        salaryFilterView.updateMarkers(minX, maxX);
    }
}
