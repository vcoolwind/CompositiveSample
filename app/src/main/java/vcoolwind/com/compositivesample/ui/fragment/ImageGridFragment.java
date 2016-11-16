package vcoolwind.com.compositivesample.ui.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.BuildConfig;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FilenameFilter;

import vcoolwind.com.compositivesample.R;
import vcoolwind.com.compositivesample.util.PicUtil;
import vcoolwind.com.compositivesample.util.Utils;

import static android.content.ContentValues.TAG;

/**
 * Created by BlackStone on 2016/11/10.
 */

public class ImageGridFragment extends Fragment implements AdapterView.OnItemClickListener {
    private File[] picFiles = null;
    private ImageAdapter imageAdapter = null;
    private int imageThumbSize = 0;
    private int imageThumbSpace = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        imageAdapter = new ImageAdapter(getActivity());
        imageThumbSize = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_size);
        imageThumbSpace = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_spacing);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_grid_image, container, false);
        final GridView gridView = (GridView) view.findViewById(R.id.grid_view_image);
        gridView.setOnItemClickListener(this);
        Log.i(getClass().getSimpleName(), "gridView.getWidth() -- " + gridView.getWidth());
        gridView.setAdapter(imageAdapter);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getContext(), "点我干啥-- " + i, Toast.LENGTH_SHORT).show();
    }

    class ImageAdapter extends BaseAdapter {
        private final Context mContext;
        private int mNumColumns = 0;
        private int mItemHeight;
        private GridView.LayoutParams mImageViewLayoutParams;


        public ImageAdapter(Context context) {
            super();
            mContext = context;
            mImageViewLayoutParams = new GridView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }

        @Override
        public int getCount() {
            return picFiles.length;
        }

        @Override
        public Object getItem(int position) {
            return picFiles[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup container) {
            ImageView imageView;
            if (convertView == null) { // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setLayoutParams(mImageViewLayoutParams);
            } else {
                imageView = (ImageView) convertView;
            }
            //请注意下面的代码
            //imageView.setImageBitmap(PicUtil.decodeSampledBitmapFromFile(picFiles[position], imageView.getWidth(), imageView.getHeight()));
            imageView.setImageBitmap(PicUtil.decodeSampledBitmapFromFile(picFiles[position], imageThumbSize, imageThumbSize));
            return imageView;
        }

        public int getNumColumns() {
            return mNumColumns;
        }

        public void setNumColumns(int mNumColumns) {
            this.mNumColumns = mNumColumns;
        }


        public void setItemHeight(int mItemHeight) {
            if (mItemHeight == this.mItemHeight) {
                return;
            }
            this.mItemHeight = mItemHeight;
            mImageViewLayoutParams = new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mItemHeight);
            notifyDataSetChanged();
        }
    }

    private void init() {
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
        if (picFiles == null) {
            picFiles = new File[]{};
        }
    }


}
