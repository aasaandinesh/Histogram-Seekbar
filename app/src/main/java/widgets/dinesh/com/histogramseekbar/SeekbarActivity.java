package widgets.dinesh.com.histogramseekbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class SeekbarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seekbar);

        initView();
    }

    private void initView() {
        LinearLayout ll = findViewById(R.id.ll_base);
        SeekbarHistogramView view = new SeekbarHistogramView(this, getTestPoints());

        TextView textView = new TextView(this);
        textView.setText("hello world");
        ll.addView(textView);
        ll.addView(view);
    }

    private Map<Integer, Integer> getTestPoints() {
        Map<Integer, Integer> points = new HashMap<>();
        points.put(1,123);
        points.put(5,300);
        points.put(20,400);
        points.put(10,600);

        return points;
    }
}
