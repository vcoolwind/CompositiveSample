package vcoolwind.com.compositivesample.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import vcoolwind.com.compositivesample.R;
import vcoolwind.com.compositivesample.model.Article;
import vcoolwind.com.compositivesample.util.ArticleLab;

/**
 * Created by BlackStone on 2016/11/3.
 */

public class ContentFragment extends Fragment {
    public static final String ARTICLE_ID = "vcoolwind.com.compositivesample.ui.ContentFragment.article_id";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_article_content, container, false);
        int articleId = getArguments().getInt(ARTICLE_ID, -1);
        if (articleId != -1) {
            Article article = ArticleLab.Companion.getInstance().getById(articleId);
            TextView textView_title = (TextView) view.findViewById(R.id.textView_article_id);
            textView_title.setText(article.getTitle());
            TextView textView_content = (TextView) view.findViewById(R.id.textView_article_content);
            textView_content.setText(article.getContent());
        }

        return view;
    }
}
