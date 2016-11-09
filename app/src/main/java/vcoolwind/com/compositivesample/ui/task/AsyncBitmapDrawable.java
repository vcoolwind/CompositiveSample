package vcoolwind.com.compositivesample.ui.task;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import java.lang.ref.WeakReference;

/**
 * Created by BlackStone on 2016/11/9.
 */

public class AsyncBitmapDrawable extends BitmapDrawable {
    private WeakReference<BitmapAsyncLoadTask2> bitmapWorkerTaskReference;

    public AsyncBitmapDrawable(Resources res, Bitmap bitmap, BitmapAsyncLoadTask2 asyncTask) {
        super(res, bitmap);
        bitmapWorkerTaskReference = new WeakReference(asyncTask);
    }

    public BitmapAsyncLoadTask2 getImageAsyncTask() {
        return bitmapWorkerTaskReference.get();
    }


}
