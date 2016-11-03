package vcoolwind.com.compositivesample.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import vcoolwind.com.compositivesample.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.frameContainer);
        if (fragment == null) {
            fragment = new TitleFragment();
            fm.beginTransaction()
                    .add(R.id.frameContainer,fragment)
                    .commit();
        }
    }
}
