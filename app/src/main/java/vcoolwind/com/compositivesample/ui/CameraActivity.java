package vcoolwind.com.compositivesample.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import vcoolwind.com.compositivesample.R;

/**
 * Created by BlackStone on 2016/11/7.
 */

public class CameraActivity extends AppCompatActivity {
    ImageView imageView_camera = null;
    String mCurrentTmpPicFile = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_camera_simple);
        imageView_camera = (ImageView) findViewById(R.id.imageView_camera);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_camera_sample, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.take_photo:
                dispatchTakePictureIntent();
                return true;
            case R.id.save_photo:
                verifyStoragePermissions(this);
                doTakePictureAndSave();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_IMAGE_CAPTURE:
                if (resultCode == RESULT_OK) {
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    imageView_camera.setImageBitmap(imageBitmap);
                } else {
                    Toast.makeText(this, "照相失败！", Toast.LENGTH_SHORT).show();
                }
            case REQUEST_IMAGE_CAPTURE_SAVE:
                if (resultCode == RESULT_OK) {
                    Log.i(getClass().getSimpleName(), "通知：" + mCurrentTmpPicFile);
                    galleryAddPic(mCurrentTmpPicFile);

                } else {
                    Toast.makeText(this, "照相失败！", Toast.LENGTH_SHORT).show();
                }


            default:
                super.onActivityResult(requestCode, resultCode, data);
        }

    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } else {
            Toast.makeText(this, "没有相机应用！", Toast.LENGTH_SHORT).show();
        }
    }

    static final int REQUEST_IMAGE_CAPTURE_SAVE = 2;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        //File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);


        Log.i(getClass().getSimpleName(), storageDir.getAbsolutePath());


        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentTmpPicFile = image.getAbsolutePath();

        Log.i(getClass().getSimpleName(), image.getAbsolutePath());

        return image;
    }

    private void doTakePictureAndSave() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePicture.resolveActivity(getPackageManager()) != null) {
            File takeFile = null;
            try {
                takeFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (takeFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "vcoolwind.com.compositivesample.fileprovider",
                        takeFile);
                takePicture.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePicture, REQUEST_IMAGE_CAPTURE_SAVE);
            }

        }
    }

    private void galleryAddPic(String file) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(file);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * Checks if the app has permission to write to device storage
     * <p>
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

}
