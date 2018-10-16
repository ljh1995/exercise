package com.example.administrator.ljh_project.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.ljh_project.R;
import com.example.administrator.ljh_project.api.HttpManager;
import com.example.administrator.ljh_project.api.HttpRequestHandler;
import com.example.administrator.ljh_project.api.JsonUtils;
import com.example.administrator.ljh_project.moudle.Food;
import com.example.administrator.ljh_project.utils.BaseActivity;
import com.example.administrator.ljh_project.utils.MessageUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018\4\6 0006.
 */

public class FoodAddActivity extends BaseActivity {
    /**
     * 标题*
     */
    private TextView titleView;
    /**
     * 返回按钮*
     */
    private ImageView backImageView;

    @Bind(R.id.introduce)
    EditText introduce;

    @Bind(R.id.name)
    EditText name;

    @Bind(R.id.address)
    EditText address;

    @Bind(R.id.price)
    EditText price;

    @Bind(R.id.number)
    EditText number;

    @Bind(R.id.submit)
    TextView submit;

//    @Bind(R.id.iv_img)
//    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_add);
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
        titleView.setText("新增推荐");
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
            String method="21";
            HttpManager.getAddFood(FoodAddActivity.this, method,name.getText().toString(),introduce.getText().toString(),
                    address.getText().toString(),price.getText().toString(),number.getText().toString(),new HttpRequestHandler<String>() {

                @Override
                public void onSuccess(String data) {
                    if (data.toString().equals("true")){
                        MessageUtils.showMiddleToast(FoodAddActivity.this,"新增成功！");
                    }else {
                        MessageUtils.showMiddleToast(FoodAddActivity.this,"新增失败！");
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
