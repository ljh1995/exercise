package com.example.administrator.ljh_project.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.ljh_project.R;
import com.example.administrator.ljh_project.moudle.Food;
import com.example.administrator.ljh_project.moudle.News;
import com.example.administrator.ljh_project.utils.BaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018\3\9 0009.
 */

public class NewsInformationActivity extends BaseActivity {
    /**
     * 标题*
     */
    private TextView titleView;
    /**
     * 返回按钮*
     */
    private ImageView backImageView;

    @Bind(R.id.tittle)
    TextView tittle;

    @Bind(R.id.name)
    TextView name;

    @Bind(R.id.time)
    TextView time;

    @Bind(R.id.content)
    TextView content;

    News news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsinformation);
        ButterKnife.bind(this);
        initData();
        findViewById();
        initView();
    }
    /**
     * 获取初始话数据*
     */
    private void initData() {
        news = (News) getIntent().getSerializableExtra("news");
    }


    @Override
    protected void findViewById() {
        titleView = (TextView) findViewById(R.id.title_name);
        backImageView = (ImageView) findViewById(R.id.title_back_id);
    }

    @Override
    protected void initView() {
        titleView.setText("校园资讯");
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (news != null) {
            tittle.setText(news.gettittle() == null ? "" :news.gettittle());
            name.setText(news.getname() == null ? "" :news.getname());
            time.setText(news.gettime() == null ? "" :news.gettime());
            content.setText(news.getcontent() == null ? "" :news.getcontent());
        }
    }
}
