package widgets.dinesh.com.histogramseekbar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ajmac1005 on 04/07/17.
 */

public class SalaryFilterView extends View {
    private  int filledColor=0xFFbad3e8;
    private int backgroundColor = 0xFFfafafa;

    private Paint filledPaint;
    private  Paint strokePaint;
    private Paint rectanglePaint;
    private  int screenHeight;
    private int screenWidth;
    private final List<PointF> points;
    private  float minX=0;

    private float maxX  ;

    public SalaryFilterView(Context context) {
        super(context);
        this.screenHeight = 0;
        this.screenWidth = 0;
        this.points = null;

    }
    public SalaryFilterView(Context context, List<PointF> points, int backgroundColor){
        super(context);
        this.backgroundColor = backgroundColor;
        this.points = points;
        init(context);
    }

    public SalaryFilterView(Context context, List<PointF> points, int backgroundColor, int filledColor){
        super(context);
        this.backgroundColor = backgroundColor;
        this.filledColor = filledColor;
        this.points = points;
        init(context);
    }


    public SalaryFilterView(Context context, List<PointF> points) {
        super(context);
        this.points = points;

        init(context);
    }

    private void init(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = getWidth();
        screenHeight = getHeight();
        maxX =  (float)screenWidth;

        rectanglePaint = new Paint();
        rectanglePaint.setAntiAlias(true);
        rectanglePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        rectanglePaint.setStrokeWidth(2);
        rectanglePaint.setColor(backgroundColor);

        strokePaint = new Paint();
        strokePaint.setAntiAlias(true);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(2);
        strokePaint.setColor(filledColor);

        filledPaint = new Paint();
        filledPaint.setAntiAlias(true);
        filledPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        filledPaint.setStrokeWidth(2);
        filledPaint.setColor(filledColor);

        setBackgroundColor(backgroundColor);
    }

    public void updateMarkers(float minX, float maxX){
        //A hack to overcome initialization bug
        if(maxX > 0) {
            this.maxX = maxX;
        }
        this.minX = minX;
        invalidate();
    }

    public SalaryFilterView(Context context, @Nullable AttributeSet attrs, List<PointF> points) {
        super(context, attrs);
        this.points = points;
        screenHeight = 0;
        screenWidth = 0;
    }

    public SalaryFilterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, List<PointF> points) {
        super(context, attrs, defStyleAttr);
        this.points = points;
        screenHeight = 0;
        screenWidth = 0;
    }



    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        screenWidth = getWidth();
        screenHeight = getHeight();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        List<PointF> normalizedPoints = normalizePoints(points);
        Path myPath1 = drawCurve(canvas, filledPaint, normalizedPoints);
        canvas.drawPath(myPath1, filledPaint);
        canvas.drawRect(0,0,minX,screenHeight, rectanglePaint);
        canvas.drawRect(maxX,0,screenWidth,screenHeight, rectanglePaint);
        canvas.drawPath(myPath1, strokePaint);

        PointF pointF = normalizedPoints.get(0);
        canvas.drawCircle(pointF.x, pointF.y, 10f, filledPaint);





    }

    @Override
    public boolean performClick() {
        return true;
    }

    private List<PointF> normalizePoints(List<PointF> points) {
        List<PointF> normalizedPoints = new ArrayList<>();
        for (PointF pointF: points){
            normalizedPoints.add(getNormalizedPoint(pointF));
        }
        return normalizedPoints;
    }

    private PointF getNormalizedPoint(PointF pointF) {
        Float x = getLeft() + pointF.x * getWidth();
        Float y = (1 -  pointF.y) * getHeight();
        return new PointF(x,y);
    }

    private Path drawCurve(Canvas canvas, Paint paint, List<PointF> points) {
        Path path = new Path();
        PointF endPoint = new PointF(getRight(), getBottom());
        PointF startPoint = new PointF(getLeft(),getBottom());
        points.add(0, startPoint);
        points.add(endPoint);
        path.moveTo(points.get(0).x, points.get(0).y);
        int i = 1;
        while (i<points.size() -1){
            path.quadTo( points.get(i).x, points.get(i).y, points.get(i+1).x, points.get(i+1).y);
            i+=1;
        }
        path.close();
        return path;
    }



}
