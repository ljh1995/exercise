package com.example.administrator.ljh_project.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.ljh_project.R;
import com.example.administrator.ljh_project.api.HttpManager;
import com.example.administrator.ljh_project.api.HttpRequestHandler;
import com.example.administrator.ljh_project.moudle.News;
import com.example.administrator.ljh_project.utils.BaseActivity;
import com.example.administrator.ljh_project.utils.MessageUtils;
import com.flyco.animation.BaseAnimatorSet;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018\4\28 0028.
 */

public class NewsUpdateActivity extends BaseActivity {
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

    @Bind(R.id.time)
    TextView time;

    @Bind(R.id.content)
    EditText content;

    @Bind(R.id.submit)
    TextView submit;

    News news;

    /**
     * 时间控件
     */
    private DatePickerDialog datePickerDialog;

    private int layoutnum;
    StringBuffer sb;
    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_update);
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
         /*所有时间点击事件的监听*/
        setDataListener();
        time.setOnClickListener(new MydateListener());
    }

    @Override
    protected void initView() {
        titleView.setText("修改校园资讯");
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        submit.setOnClickListener(submitClickListener);

        if (news != null) {
            tittle.setText(news.gettittle() == null ? "" :news.gettittle());
            author.setText(news.getname() == null ? "" :news.getname());
            time.setText(news.gettime() == null ? "" :news.gettime());
            content.setText(news.getcontent() == null ? "" :news.getcontent());
        }
    }

    /**
     * 设置时间选择器*
     */
    private void setDataListener() {
        final Calendar objTime = Calendar.getInstance();
        int iYear = objTime.get(Calendar.YEAR);
        int iMonth = objTime.get(Calendar.MONTH);
        int iDay = objTime.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this, new datelistener(), iYear, iMonth, iDay);
    }

    private class MydateListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            sb = new StringBuffer();
            datePickerDialog.show();
        }
    }
    private class datelistener implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            sb = new StringBuffer();
            monthOfYear = monthOfYear + 1;
            if (dayOfMonth < 10) {
                sb.append(year % 10000 + "-" + monthOfYear + "-" + "0" + dayOfMonth);
            } else {
                sb.append(year % 10000 + "-" + monthOfYear + "-" + dayOfMonth);
            }
            time.setText(sb);
        }
    }

    private View.OnClickListener submitClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String method="27";
            HttpManager.getUpdateNews(NewsUpdateActivity.this, method,news.getnewsid().toString(),tittle.getText().toString(),author.getText().toString(),
                    content.getText().toString(),time.getText().toString(),new HttpRequestHandler<String>() {

                        @Override
                        public void onSuccess(String data) {
                            if (data.toString().equals("true")){
                                MessageUtils.showMiddleToast(NewsUpdateActivity.this,"修改成功！");
                            }else {
                                MessageUtils.showMiddleToast(NewsUpdateActivity.this,"修改失败！");
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


