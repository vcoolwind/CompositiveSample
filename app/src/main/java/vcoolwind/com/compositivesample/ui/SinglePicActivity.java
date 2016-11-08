package vcoolwind.com.compositivesample.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import vcoolwind.com.compositivesample.R;

public class SinglePicActivity extends AppCompatActivity {
    public static final String IMG_FILE_PATH = "img_file_path";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String imgFile = getIntent().getStringExtra(IMG_FILE_PATH);
        setContentView(R.layout.layout_single_picture);
        ImageView imgView = (ImageView) findViewById(R.id.imageView_pic);
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setPic(imgView, imgFile);
    }


    private void setPic(ImageView imgView, String imgFile) {
        if (imgFile != null) {
            int targetW = imgView.getWidth();
            int targetH = imgView.getHeight();
            if (targetH == 0 || targetW == 0) {
                WindowManager wm = (WindowManager) this
                        .getSystemService(Context.WINDOW_SERVICE);
                targetW = wm.getDefaultDisplay().getWidth();
                targetH = wm.getDefaultDisplay().getHeight();
            }
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(imgFile, options);

            int picW = options.outWidth;
            int picH = options.outHeight;

            int scale = Math.min(picW / targetW, picH / targetH);
            options.inJustDecodeBounds = false;
            options.inSampleSize = scale;
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile, options);
            imgView.setImageBitmap(bitmap);
        }

    }

}
