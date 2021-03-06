package vcoolwind.com.compositivesample.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import vcoolwind.com.compositivesample.R;

public class NavigateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigater);
        Button button_first_recycleview = (Button) findViewById(R.id.button_first_recycleview);
        button_first_recycleview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), FirstRecycleViewActivity.class));
            }
        });

        Button button_second_recycleview = (Button) findViewById(R.id.button_second_recycleview);
        button_second_recycleview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SecondRecycleViewActivity.class));
            }
        });

        Button button_third_recycleview = (Button) findViewById(R.id.button_third_recycleview);
        button_third_recycleview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ThirdRecycleViewActivity.class));
            }
        });


        Button button_camera = (Button) findViewById(R.id.button_camera);
        button_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CameraActivity.class));
            }
        });

        Button button_pic_browser = (Button) findViewById(R.id.button_pic_browser);
        button_pic_browser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PictureBrowserActivity.class));
            }
        });

        Button button_progress_bar = (Button) findViewById(R.id.button_progress_bar);
        button_progress_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AsyncTaskSampleActivity.class));
            }
        });

        Button button_grid_img = (Button) findViewById(R.id.button_grid_img);
        button_grid_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), GridImageActivity.class));
            }
        });

        Button button_grid_sample = (Button) findViewById(R.id.button_grid_sample);
        button_grid_sample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), GridSampleActivity.class));
            }
        });

        Button button_text_input_layout = (Button) findViewById(R.id.button_text_input_layout);
        button_text_input_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), TextInputLayoutActivity.class));
            }
        });


        Button button_dialog_toast_snackbar = (Button)findViewById(R.id.button_dialog_toast_snackbar);
        button_dialog_toast_snackbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DialogToastSnackbarActivity.class));
            }
        });

    }
}
