package ha.thanh.pikerfree.activities.augmentedReality;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.customviews.ArDisplayView;
import ha.thanh.pikerfree.customviews.OverlayView;

public class ARActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar);

        FrameLayout arViewPane = (FrameLayout) findViewById(R.id.ar_view_pane);

        //ArDisplayView arDisplay = new ArDisplayView(this, this);
        //arViewPane.addView(arDisplay);

        OverlayView arContent = new OverlayView(getApplicationContext());
        arViewPane.addView(arContent);
    }
}
