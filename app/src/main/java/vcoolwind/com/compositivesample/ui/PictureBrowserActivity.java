package vcoolwind.com.compositivesample.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;

import vcoolwind.com.compositivesample.R;
import vcoolwind.com.compositivesample.ui.adapter.PictureBrowserAdapter;
import vcoolwind.com.compositivesample.ui.common.GridDividerItemDecoration;
import vcoolwind.com.compositivesample.ui.common.OnItemTouchListenerWithRecycleView;

/**
 * Created by BlackStone on 2016/11/8.
 */

public class PictureBrowserActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_picture_browser);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_picture_browser);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.addItemDecoration(new GridDividerItemDecoration(this));
        PictureBrowserAdapter adapter = new PictureBrowserAdapter(this);
        WindowManager wm = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        int displayWidth = wm.getDefaultDisplay().getWidth();
        int displayHeight = wm.getDefaultDisplay().getHeight();

        int width = displayWidth /2;
        int height = displayHeight/2;


        adapter.setWidth(width);
        adapter.setHeight(height);

        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new OnItemTouchListenerWithRecycleView(recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder) {
                if(viewHolder instanceof  PictureBrowserAdapter.PictureViewHolder ){
                    ((PictureBrowserAdapter.PictureViewHolder) viewHolder).onItemClick();
                }
            }
        });
    }
}
