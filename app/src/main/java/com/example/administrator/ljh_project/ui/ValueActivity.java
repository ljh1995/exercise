package com.example.administrator.ljh_project.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.ljh_project.R;
import com.example.administrator.ljh_project.api.HttpManager;
import com.example.administrator.ljh_project.api.HttpRequestHandler;
import com.example.administrator.ljh_project.moudle.Entity;
import com.example.administrator.ljh_project.moudle.News;
import com.example.administrator.ljh_project.moudle.Trade;
import com.example.administrator.ljh_project.utils.AccountUtils;
import com.example.administrator.ljh_project.utils.BaseActivity;
import com.example.administrator.ljh_project.utils.MessageUtils;

import java.sql.Date;
import java.text.SimpleDateFormat;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018\4\2 0002.
 */

public class ValueActivity extends BaseActivity {
    /**
     * 标题*
     */
    private TextView titleView;
    /**
     * 返回按钮*
     */
    private ImageView backImageView;

    /**
     * 提交按钮*
     */
    private Button submit;

    /**
     * 评价内容*
     */
    private EditText value;


    Trade trade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_value);
        ButterKnife.bind(this);
        initData();
        findViewById();
        initView();
    }
    /**
     * 获取初始话数据*
     */
    private void initData() {
        trade = (Trade) getIntent().getSerializableExtra("trade");
    }
    @Override
    protected void findViewById() {
        titleView = (TextView) findViewById(R.id.title_name);
        backImageView = (ImageView) findViewById(R.id.title_back_id);
        submit = (Button) findViewById(R.id.sbmit_id);
        value = (EditText) findViewById(R.id.value);
    }

    @Override
    protected void initView() {
        titleView.setText("评价");
        submit.setVisibility(View.VISIBLE);
        submit.setOnClickListener(submitonClickListener);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //点击提交按钮
    private View.OnClickListener submitonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (trade.getname().contains("、")){
                String method="5";
                HttpManager.getEvaluates(ValueActivity.this, method, AccountUtils.getUserName(ValueActivity.this),
                        value.getText().toString(),trade.getaddress().toString(),new HttpRequestHandler<String>() {

                    @Override
                    public void onSuccess(String data) {
                        if (data.equals("true")){
                            MessageUtils.showMiddleToast(ValueActivity.this,"评论成功");

                            String method="20";
                            HttpManager.getEvaluateStatus(ValueActivity.this, method, "已评价",trade.gettradeid(), new HttpRequestHandler<String>() {

                                @Override
                                public void onSuccess(String data) {

                                }

                                @Override
                                public void onSuccess(String data, int totalPages, int currentPage) {

                                }

                                @Override
                                public void onFailure(String error) {
                                }
                            });

                        }else {
                            MessageUtils.showMiddleToast(ValueActivity.this,"评论失败，请稍后评论哦");
                        }
                    }

                    @Override
                    public void onSuccess(String data, int totalPages, int currentPage) {

                    }

                    @Override
                    public void onFailure(String error) {
                    }
                });
            }else {
                String method="18";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss
                //获取当前时间
                Date date = new Date(System.currentTimeMillis());
                HttpManager.getValuates(ValueActivity.this, method,trade.getfoodid(),
                        AccountUtils.getUserName(ValueActivity.this),value.getText().toString(),simpleDateFormat.format(date), new HttpRequestHandler<String>() {

                    @Override
                    public void onSuccess(String data) {
                        if (data.equals("true")){
                            MessageUtils.showMiddleToast(ValueActivity.this,"评论成功");

                            String method="20";
                            HttpManager.getEvaluateStatus(ValueActivity.this, method, "已评价",trade.gettradeid(), new HttpRequestHandler<String>() {

                                @Override
                                public void onSuccess(String data) {

                                }

                                @Override
                                public void onSuccess(String data, int totalPages, int currentPage) {

                                }

                                @Override
                                public void onFailure(String error) {
                                }
                            });

                        }else {
                            MessageUtils.showMiddleToast(ValueActivity.this,"评论失败，请稍后评论哦");
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
        }
    };
}
