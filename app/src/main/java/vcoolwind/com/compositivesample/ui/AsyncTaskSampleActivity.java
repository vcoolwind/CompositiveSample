package vcoolwind.com.compositivesample.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import vcoolwind.com.compositivesample.R;
import vcoolwind.com.compositivesample.ui.task.AsyncTaskSample;

public class AsyncTaskSampleActivity extends AppCompatActivity {
    Button start = null;
    Button pause = null;

    TextView textView = null;
    ProgressBar progressBar = null;

    AsyncTaskSample asyncTaskSample = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task_sample);

        textView = (TextView) findViewById(R.id.textView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        start = (Button) findViewById(R.id.button_start);
        start.setText("START");
        start.setOnClickListener(startClick);

        pause = (Button) findViewById(R.id.button_pause);
        pause.setText("PAUSE");
        pause.setOnClickListener(pauseClick);
    }

    View.OnClickListener startClick = new View.OnClickListener() {


        @Override
        public void onClick(View view) {
            if (start.getText().equals("START")) {
                asyncTaskSample = new AsyncTaskSample(textView, progressBar);
                asyncTaskSample.execute(1000);
                start.setText("STOP");
            } else {
                start.setText("START");
                asyncTaskSample.stop();
                asyncTaskSample = null;
            }
        }
    };

    View.OnClickListener pauseClick = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            if (pause.getText().equals("PAUSE")) {
                if (asyncTaskSample != null) {
                    asyncTaskSample.pause();
                    pause.setText("RESUME");
                }
            } else {
                if (asyncTaskSample != null) {
                    pause.setText("PAUSE");
                    asyncTaskSample.resume();
                }
            }
        }
    };


}
