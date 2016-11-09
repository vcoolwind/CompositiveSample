package vcoolwind.com.compositivesample.util;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * Created by BlackStone on 2016/11/9.
 */

public class BitmapLruCache extends LruCache<String, Bitmap> {
    public static final BitmapLruCache instance = new BitmapLruCache(50);

    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */
    private BitmapLruCache(int maxSize) {
        super(maxSize);
    }
}
