package vcoolwind.com.compositivesample.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.text.ICUCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vcoolwind.com.compositivesample.R;

public class GridSampleActivity extends AppCompatActivity {

    private GridView gview;
    private List<IconModel> data_list;
    private MyAdapter sim_adapter;
    // 图片封装为一个数组
    private int[] icon = {R.drawable.address_book, R.drawable.calendar,
            R.drawable.camera, R.drawable.clock, R.drawable.games_control,
            R.drawable.messenger, R.drawable.ringtone, R.drawable.settings,
            R.drawable.speech_balloon, R.drawable.weather, R.drawable.world,
            R.drawable.youtube};
    private String[] iconName = {"通讯录", "日历", "照相机", "时钟", "游戏", "短信", "铃声",
            "设置", "语音", "天气", "浏览器", "视频"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_sample);
        gview = (GridView) findViewById(R.id.grid_view_sample);
        //新建List
        data_list = new ArrayList<IconModel>();
        //获取数据
        getData();
        //新建适配器
        String[] from = {"image", "text"};
        int[] to = {R.id.image, R.id.text};
        //sim_adapter = new SimpleAdapter(this, data_list, R.layout.layout_grid_item_sample, from, to);
        sim_adapter = new MyAdapter(this);
        //配置适配器
        gview.setAdapter(sim_adapter);
    }


    public List<IconModel> getData() {
        //cion和iconName的长度是相同的，这里任选其一都可以
        for (int i = 0; i < icon.length; i++) {
            IconModel model = new IconModel(iconName[i], icon[i]);
            data_list.add(model);
        }
        return data_list;
    }

    class IconModel {
        private String name;
        private int icon;

        public IconModel(String name, int icon) {
            this.name = name;
            this.icon = icon;
        }

        public String getName() {
            return name;
        }

        public int getIcon() {
            return icon;
        }
    }

    class MyAdapter extends BaseAdapter {
        public Context context;

        public MyAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return data_list.size();
        }

        @Override
        public Object getItem(int i) {
            return data_list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.layout_grid_item_sample, viewGroup, false);
            }
            ImageView imageView = (ImageView) view.findViewById(R.id.image);
            TextView textView = (TextView) view.findViewById(R.id.text);
            IconModel model = data_list.get(i);
            imageView.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), model.getIcon()));
            textView.setText(model.getName());

            return view;
        }
    }


}
