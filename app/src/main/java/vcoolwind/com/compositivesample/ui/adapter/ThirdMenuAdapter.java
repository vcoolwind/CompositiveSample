package vcoolwind.com.compositivesample.ui.adapter;

/**
 * Created by BlackStone on 2016/11/4.
 */


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import vcoolwind.com.compositivesample.R;
import vcoolwind.com.compositivesample.model.Article;
import vcoolwind.com.compositivesample.ui.listener.OnItemClickListener;
import vcoolwind.com.compositivesample.util.ArticleLab;

/**
 * Created by YOLANDA on 2016/7/22.
 */
public class ThirdMenuAdapter extends SwipeMenuAdapter<ThirdMenuAdapter.DefaultViewHolder> {


    private OnItemClickListener mOnItemClickListener;


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return ArticleLab.Companion.getInstance().size();
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_article_titles, parent, false);
    }



    @Override
    public ThirdMenuAdapter.DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new DefaultViewHolder(realContentView);
    }

    @Override
    public void onBindViewHolder(ThirdMenuAdapter.DefaultViewHolder holder, int position) {
        holder.bindData(position);
        holder.setOnItemClickListener(mOnItemClickListener);
    }

    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Article article = null;
        private TextView textViewId = null;
        private TextView textViewTitle = null;
        OnItemClickListener mOnItemClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textViewId = (TextView) itemView.findViewById(R.id.article_id);
            textViewTitle = (TextView) itemView.findViewById(R.id.article_title);
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

        public void bindData(int position) {
            this.article = ArticleLab.Companion.getInstance().get(position);
            textViewId.setText(String.valueOf(article.getId()));
            textViewTitle.setText(String.valueOf(article.getTitle()));
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }

}
