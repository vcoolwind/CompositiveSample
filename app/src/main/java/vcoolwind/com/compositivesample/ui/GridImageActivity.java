package vcoolwind.com.compositivesample.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import vcoolwind.com.compositivesample.R;
import vcoolwind.com.compositivesample.ui.fragment.ImageGridFragment;

public class GridImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_framlayout);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.frameContainer);
        if (fragment == null) {
            fm.beginTransaction()
                    .add(R.id.frameContainer, new ImageGridFragment())
                    .commit();
        }
    }
}
