package vcoolwind.com.compositivesample.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.io.FilenameFilter;

import vcoolwind.com.compositivesample.R;
import vcoolwind.com.compositivesample.ui.SinglePicActivity;
import vcoolwind.com.compositivesample.ui.task.AsyncBitmapDrawable;
import vcoolwind.com.compositivesample.ui.task.BitmapAsyncLoadTask1;
import vcoolwind.com.compositivesample.ui.task.BitmapAsyncLoadTask2;
import vcoolwind.com.compositivesample.util.BitmapLruCache;
import vcoolwind.com.compositivesample.util.PicUtil;

/**
 * Created by BlackStone on 2016/11/8.
 */

public class PictureBrowserAdapter extends RecyclerView.Adapter<PictureBrowserAdapter.PictureViewHolder> {
    File[] picFiles = null;
    Context context = null;

    private int width = 0;
    private int height = 0;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public PictureBrowserAdapter(Context context) {
        this.context = context;
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        picFiles = storageDir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                File tmp = new File(file, s);
                if (tmp.isDirectory() || tmp.length() == 0) {
                    return false;
                }
                return true;
            }
        });


        Log.i(getClass().getSimpleName(), picFiles.toString());

    }

    @Override
    public PictureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_single_picture, parent, false);
        return new PictureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PictureViewHolder holder, int position) {
        holder.render(position);
    }

    @Override
    public int getItemCount() {
        return picFiles.length;
    }

    public class PictureViewHolder extends RecyclerView.ViewHolder {
        ImageView imgView = null;
        int currPoint = -1;
        //TextView nameView = null;


        public PictureViewHolder(View itemView) {
            super(itemView);
            imgView = (ImageView) itemView.findViewById(R.id.imageView_pic);
            // nameView = (TextView) itemView.findViewById(R.id.textView_pic);
        }

        public void render(int position) {
            this.currPoint = position;
            File currFile = picFiles[position];

            // 同步加载图片，在UI主线程执行。
            //loadBitmap1(currFile.getAbsolutePath(), imgView);

            //优化为使用AsyncTask
             loadBitmap2(currFile.getAbsolutePath(), imgView);

            // 优化为防并发
           // loadBitmap3(currFile.getAbsolutePath(), imgView);
        }

        private void loadBitmap1(String imgFile, ImageView imgView) {
            //Bitmap bitmap = PicUtil.decodeSampledBitmapFromFile(imgFile, imgView.getWidth(), imgView.getHeight());
            // 这段代码也能执行，但是同步的，如果是从网络获取，就不可行了。
            Bitmap bitmap = PicUtil.decodeSampledBitmapFromFile(imgFile, getWidth(), getHeight());
            imgView.setImageBitmap(bitmap);
        }

        private void loadBitmap2(String imgFile, ImageView imgView) {
            BitmapAsyncLoadTask1 imageAsyncTask = new BitmapAsyncLoadTask1(imgView, getWidth(), getHeight());
            imageAsyncTask.execute(imgFile);
        }

        private void loadBitmap3(String imgFile, ImageView imgView) {
            if (cancelPotentialWork(imgFile, imgView)) {
                BitmapAsyncLoadTask2 imageAsyncTask = new BitmapAsyncLoadTask2(imgView, getWidth(), getHeight());
                final AsyncBitmapDrawable asyncDrawable =
                        new AsyncBitmapDrawable(
                                context.getResources(),
                                getLoadingImg()
                                , imageAsyncTask
                        );
                imgView.setImageDrawable(asyncDrawable);
                imageAsyncTask.execute(imgFile);
            }
        }

        public void onItemClick() {
            Intent intent = new Intent(context, SinglePicActivity.class);
            intent.putExtra(SinglePicActivity.IMG_FILE_PATH, picFiles[currPoint].getAbsolutePath());
            context.startActivity(intent);

        }
    }

    public static boolean cancelPotentialWork(String imgFile, ImageView imageView) {
        final BitmapAsyncLoadTask2 bitmapWorkerTask = getBitmapWorkerTask(imageView);
        if (bitmapWorkerTask != null) {
            final String oriImgFile = bitmapWorkerTask.getImgFile();
            if (oriImgFile == null || !oriImgFile.equals(imgFile)) {
                // Cancel previous task
                bitmapWorkerTask.cancel(true);
            } else {
                // The same work is already in progress
                return false;
            }
        }
        // No task associated with the ImageView, or an existing task was cancelled
        return true;
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


    private Bitmap getLoadingImg() {
        Bitmap loadingImg = BitmapLruCache.instance.get("loading_placeholder");
        if (loadingImg == null) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;
            loadingImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.loading, options);
            BitmapLruCache.instance.put("loading_placeholder", loadingImg);
        }
        return loadingImg;

    }

}
