package vcoolwind.com.compositivesample.util;

import android.os.Environment;

/**
 * Created by BlackStone on 2016/11/8.
 */

public class StorageUtil {
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
}
