package vcoolwind.com.compositivesample.ui;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import vcoolwind.com.compositivesample.R;
import vcoolwind.com.compositivesample.util.ToastUtils;

public class TextInputLayoutActivity extends AppCompatActivity {

    private TextInputLayout id_layout;
    private TextInputLayout passwd_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_input_layout);
        id_layout = (TextInputLayout)findViewById(R.id.text_layout_id);
        passwd_layout = (TextInputLayout)findViewById(R.id.text_layout_password);

        final TextInputEditText edit_text_id = (TextInputEditText) findViewById(R.id.edit_text_id);
        edit_text_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ToastUtils.showToast(getApplicationContext(),charSequence+"--"+i+"--"+i1+"--"+i2);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        final TextInputEditText edit_text_password = (TextInputEditText) findViewById(R.id.edit_text_password);
        Button button_submit = (Button) findViewById(R.id.button_submit);
        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),
                        "id:" + edit_text_id.getText() + " passwd:" + edit_text_password.getText(),
                        Toast.LENGTH_LONG)
                        .show();
                id_layout.setErrorEnabled(true);
                id_layout.setError("身份证输入错误");
            }
        });


    }
}
