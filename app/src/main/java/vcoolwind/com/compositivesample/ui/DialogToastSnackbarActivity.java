package vcoolwind.com.compositivesample.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import vcoolwind.com.compositivesample.R;
import vcoolwind.com.compositivesample.util.ToastUtils;

public class DialogToastSnackbarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_toast_snackbar);
        findViewById(R.id.button_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(DialogToastSnackbarActivity.this)
                        .setIcon(R.drawable.clock)
                        .setTitle("这里是标题")
                        .setMessage("这里是内容，说点啥")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ToastUtils.showToast(getApplicationContext(), "你点击了确定" + i);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ToastUtils.showToast(getApplicationContext(), "你点击了取消" + i);
                            }
                        })
                        .create()
                        .show();
            }
        });

        findViewById(R.id.button_snackbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(findViewById(android.R.id.content), "发生了什么事情。。。", Snackbar.LENGTH_SHORT)
                        .setAction("动作描述", new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {
                                ToastUtils.showToast(getApplicationContext(), "u click me");
                            }
                        })
                        .show();
            }
        });


    }
}
