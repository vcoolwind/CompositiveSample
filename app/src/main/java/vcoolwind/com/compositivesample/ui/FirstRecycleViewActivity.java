package vcoolwind.com.compositivesample.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import vcoolwind.com.compositivesample.R;

public class FirstRecycleViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_framlayout);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.frameContainer);
        if (fragment == null) {
            fragment = new FirstTitleFragment();
            fm.beginTransaction()
                    .add(R.id.frameContainer,fragment)
                    .commit();
        }
    }
}
