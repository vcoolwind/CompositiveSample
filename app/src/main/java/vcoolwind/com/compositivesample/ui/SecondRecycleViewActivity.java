package vcoolwind.com.compositivesample.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import vcoolwind.com.compositivesample.R;

/**
 * Created by BlackStone on 2016/11/4.
 */

public class SecondRecycleViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_framlayout);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.frameContainer);
        if (fragment == null) {
            fragment = new SecondTitleFragment();
            fm.beginTransaction()
                    .add(R.id.frameContainer, fragment)
                    .commit();
        }
    }
}
