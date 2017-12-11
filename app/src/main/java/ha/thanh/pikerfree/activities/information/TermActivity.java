package ha.thanh.pikerfree.activities.information;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ha.thanh.pikerfree.R;

public class TermActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.img_back)
    public void goback() {
        onBackPressed();
    }
}
