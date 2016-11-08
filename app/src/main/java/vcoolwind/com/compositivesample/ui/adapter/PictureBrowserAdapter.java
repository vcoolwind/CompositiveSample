package vcoolwind.com.compositivesample.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
            //Bitmap bitmap = PicUtil.decodeSampledBitmapFromFile(currFile.getAbsolutePath(), imgView.getWidth(), imgView.getHeight());
            Bitmap bitmap = PicUtil.decodeSampledBitmapFromFile(currFile.getAbsolutePath(), getWidth(), getHeight());

            imgView.setImageBitmap(bitmap);
            //nameView.setText(currFile.getName());
        }

        public void onItemClick() {
            Intent intent = new Intent(context, SinglePicActivity.class);
            intent.putExtra(SinglePicActivity.IMG_FILE_PATH, picFiles[currPoint].getAbsolutePath());
            context.startActivity(intent);

        }
    }
}
