package vcoolwind.com.compositivesample.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
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
import vcoolwind.com.compositivesample.util.PermissionUtil;
import vcoolwind.com.compositivesample.util.StorageUtil;

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
                PermissionUtil.verifyStoragePermissions(this);
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
                    setPic();

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

        if (!StorageUtil.isExternalStorageWritable()) {
            Log.i(getClass().getSimpleName(), "外部存储不可写入！");
            return null;
        }

        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        //File storageDir =  getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        //File storageDirP =  Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        //File storageDir =  Environment.getExternalStorageDirectory();
        //File storageDir = new File(storageDirP,"wangyf");

        if (!storageDir.exists()) {
            storageDir.mkdirs();
            Log.i(getClass().getSimpleName(), "外部存储不存在！");
        } else {
            Log.i(getClass().getSimpleName(), "外部存储存在！");

        }
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
                Uri photoURI = null;
                if (Build.VERSION.SDK_INT >= 24) {
                    //API 24后不再支持file://访问，要用FileProvider替换。
                    photoURI = FileProvider.getUriForFile(
                            this,
                            "vcoolwind.com.compositivesample.fileprovider",
                            takeFile);
                } else {
                    // API 24前继续，貌似用FileProvider会有问题。
                    photoURI = Uri.fromFile(takeFile);
                }

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


    private void setPic() {
        //缩放图片

        // Get the dimensions of the View
        int targetW = imageView_camera.getWidth();
        int targetH = imageView_camera.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentTmpPicFile, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentTmpPicFile, bmOptions);
        imageView_camera.setImageBitmap(bitmap);
    }

}
