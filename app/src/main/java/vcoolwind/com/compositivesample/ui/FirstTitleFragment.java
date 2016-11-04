package vcoolwind.com.compositivesample.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import vcoolwind.com.compositivesample.R;
import vcoolwind.com.compositivesample.ui.common.LinearDividerItemDecoration;
import vcoolwind.com.compositivesample.util.ArticleLab;

/**
 * Created by BlackStone on 2016/11/3.
 */

public class FirstTitleFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        RecyclerView view = (RecyclerView) inflater.inflate(R.layout.common_recycleview_container, container, false);
        view.setLayoutManager(new LinearLayoutManager(getActivity()));
        view.addItemDecoration(new LinearDividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL));
        view.setAdapter(new ArticleTitleAdapter());
        return view;
    }

    class ArticleTitleAdapter extends RecyclerView.Adapter<ArticleTitleViewHolder> {

        @Override
        public ArticleTitleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_article_titles, parent, false);
            return new ArticleTitleViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ArticleTitleViewHolder holder, int position) {
            holder.render(position);
        }

        @Override
        public int getItemCount() {
            return ArticleLab.Companion.getInstance().size();
        }


    }


    class ArticleTitleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textViewId = null;
        private TextView textViewTitle = null;
        private int articleId = -1;

        public ArticleTitleViewHolder(View itemView) {
            super(itemView);
            textViewId = (TextView) itemView.findViewById(R.id.article_id);
            textViewTitle = (TextView) itemView.findViewById(R.id.article_title);
            itemView.setOnClickListener(this);
        }

        public void render(int position) {
            articleId = ArticleLab.Companion.getInstance().get(position).getId();
            textViewId.setText(String.valueOf(articleId));
            textViewTitle.setText(ArticleLab.Companion.getInstance().get(position).getTitle());
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(getContext(), "U touched " + articleId, Toast.LENGTH_SHORT).show();
            Bundle bundle = new Bundle();
            bundle.putInt(ContentFragment.ARTICLE_ID, articleId);
            ContentFragment contentFragment = new ContentFragment();
            contentFragment.setArguments(bundle);
            getFragmentManager().beginTransaction()
                    .replace(R.id.frameContainer, contentFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }


}
