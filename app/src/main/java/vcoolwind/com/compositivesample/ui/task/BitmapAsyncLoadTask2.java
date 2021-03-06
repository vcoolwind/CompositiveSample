package vcoolwind.com.compositivesample.ui.task;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

import vcoolwind.com.compositivesample.util.BitmapLruCache;
import vcoolwind.com.compositivesample.util.PicUtil;

/**
 * Created by BlackStone on 2016/11/9.
 */

public class BitmapAsyncLoadTask2 extends AsyncTask<String, Integer, Bitmap> {
    private WeakReference<ImageView> imageViewReference = null;

    public String getImgFile() {
        return imgFile;
    }

    private String imgFile = null;
    private int targetW = 0;
    private int targetH = 0;

    public BitmapAsyncLoadTask2(ImageView imageView, int targetW, int targetH) {
        imageViewReference = new WeakReference(imageView);
        this.targetW = targetW;
        this.targetH = targetH;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        if (strings != null && strings.length > 0) {
            imgFile = strings[0];

            Bitmap bitmap = BitmapLruCache.instance.get(imgFile);
            if (bitmap == null) {
                bitmap = PicUtil.decodeSampledBitmapFromFile(imgFile, targetW, targetH);
                BitmapLruCache.instance.put(imgFile, bitmap);
            }


            return bitmap;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (isCancelled()) {
            bitmap = null;
        }

        if (bitmap != null && imageViewReference != null) {
            ImageView imageView = imageViewReference.get();
            final BitmapAsyncLoadTask2 bitmapWorkerTask =
                    getBitmapWorkerTask(imageView);

            if (this == bitmapWorkerTask && imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    private static BitmapAsyncLoadTask2 getBitmapWorkerTask(ImageView imageView) {
        if (imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncBitmapDrawable) {
                final AsyncBitmapDrawable asyncDrawable = (AsyncBitmapDrawable) drawable;
                return asyncDrawable.getImageAsyncTask();
            }
        }
        return null;
    }
}
