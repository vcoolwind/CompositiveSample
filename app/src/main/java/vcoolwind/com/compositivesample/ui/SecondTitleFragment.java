package vcoolwind.com.compositivesample.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import vcoolwind.com.compositivesample.R;
import vcoolwind.com.compositivesample.model.Article;
import vcoolwind.com.compositivesample.ui.common.LinearDividerItemDecoration;
import vcoolwind.com.compositivesample.ui.common.OnItemTouchListenerWithRecycleView;
import vcoolwind.com.compositivesample.util.ArticleLab;

/**
 * Created by BlackStone on 2016/11/4.
 */

public class SecondTitleFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.common_recycleview_container, container, false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new LinearDividerItemDecoration(getActivity(), LinearLayout.HORIZONTAL));
        recyclerView.setAdapter(new MyAdapter());
        recyclerView.addOnItemTouchListener(new OnItemTouchListenerWithRecycleView(recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder) {
                if (viewHolder instanceof MyViewHolder) {
                    ((MyViewHolder) viewHolder).onItemClick();
                }
            }
        });

        return recyclerView;
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_article_titles, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.bind(position);
        }

        @Override
        public int getItemCount() {
            return ArticleLab.Companion.getInstance().size();
        }


    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        private Article article = null;
        private TextView textViewId = null;
        private TextView textViewTitle = null;

        public MyViewHolder(View itemView) {
            super(itemView);
            textViewId = (TextView) itemView.findViewById(R.id.article_id);
            textViewTitle = (TextView) itemView.findViewById(R.id.article_title);
        }

        public void bind(int position) {
            this.article = ArticleLab.Companion.getInstance().get(position);
            textViewId.setText(String.valueOf(article.getId()));
            textViewTitle.setText(String.valueOf(article.getTitle()));
        }

        public void onItemClick() {
            Toast.makeText(getContext(), "U touched " + article.getId(), Toast.LENGTH_SHORT).show();
            Bundle bundle = new Bundle();
            bundle.putInt(ContentFragment.ARTICLE_ID, article.getId());
            ContentFragment contentFragment = new ContentFragment();
            contentFragment.setArguments(bundle);
            getFragmentManager().beginTransaction()
                    .replace(R.id.frameContainer, contentFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }
}
