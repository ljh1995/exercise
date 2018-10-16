package com.example.administrator.ljh_project.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.ljh_project.R;
import com.example.administrator.ljh_project.api.HttpManager;
import com.example.administrator.ljh_project.api.HttpRequestHandler;
import com.example.administrator.ljh_project.utils.BaseActivity;
import com.example.administrator.ljh_project.utils.MessageUtils;

import java.sql.Date;
import java.text.SimpleDateFormat;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018\4\17 0017.
 */

public class NewsAddActivity extends BaseActivity {
    /**
     * 标题*
     */
    private TextView titleView;
    /**
     * 返回按钮*
     */
    private ImageView backImageView;

    @Bind(R.id.tittle)
    EditText tittle;

    @Bind(R.id.author)
    EditText author;

    @Bind(R.id.content)
    EditText content;

    @Bind(R.id.submit)
    TextView submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_add);
        ButterKnife.bind(this);
        findViewById();
        initView();
    }
    @Override
    protected void findViewById() {
        titleView = (TextView) findViewById(R.id.title_name);
        backImageView = (ImageView) findViewById(R.id.title_back_id);
    }

    @Override
    protected void initView() {
        titleView.setText("新增校园资讯");
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        submit.setOnClickListener(submitClickListener);
    }

    private View.OnClickListener submitClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String method="22";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss
            //获取当前时间
            Date date = new Date(System.currentTimeMillis());
            HttpManager.getAddNews(NewsAddActivity.this, method,tittle.getText().toString(),author.getText().toString(),
                    content.getText().toString(),simpleDateFormat.format(date),new HttpRequestHandler<String>() {

                        @Override
                        public void onSuccess(String data) {
                            if (data.toString().equals("true")){
                                MessageUtils.showMiddleToast(NewsAddActivity.this,"新增成功！");
                            }else {
                                MessageUtils.showMiddleToast(NewsAddActivity.this,"新增失败！");
                            }
                        }

                        @Override
                        public void onSuccess(String data, int totalPages, int currentPage) {

                        }

                        @Override
                        public void onFailure(String error) {
                        }
                    });
        }
    };
}

