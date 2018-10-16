package com.example.administrator.ljh_project.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.ljh_project.R;
import com.example.administrator.ljh_project.api.HttpManager;
import com.example.administrator.ljh_project.api.HttpRequestHandler;
import com.example.administrator.ljh_project.moudle.GoodsListBean;
import com.example.administrator.ljh_project.utils.AccountUtils;
import com.example.administrator.ljh_project.utils.BaseActivity;
import com.example.administrator.ljh_project.utils.MessageUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018\3\11 0011.
 */

public class SpendActivity extends BaseActivity {
    /**
     * 标题*
     */
    private TextView titleView;
    /**
     * 返回按钮*
     */
    private ImageView backImageView;

    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.tvGoodsSelectNum)
    TextView tvGoodsSelectNum;
    @Bind(R.id.price)
    TextView price;
    @Bind(R.id.number)
    EditText telephone;
    @Bind(R.id.say)
    EditText say;
    @Bind(R.id.submit)
    TextView submit;

    private String totalprice;
    private String num;
    private String address;
    ArrayList<String> goods = new ArrayList<>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spend);
        ButterKnife.bind(this);
        geiIntentData();
        findViewById();
        initView();
    }
        /**
         * 获取初始话数据*
         */
    private void geiIntentData() {
        totalprice = getIntent().getExtras().getString("price");
        num = getIntent().getExtras().getString("num");
        address = getIntent().getExtras().getString("address");
        goods = getIntent().getStringArrayListExtra("goods");
    }
    @Override
    protected void findViewById() {
        titleView = (TextView) findViewById(R.id.title_name);
        backImageView = (ImageView) findViewById(R.id.title_back_id);
    }

    @Override
    protected void initView() {
        titleView.setText("支付订单");
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        submit.setOnClickListener(submitOnClickListener);
        tvGoodsSelectNum.setText(num);
        price.setText("¥"+totalprice);
        StringBuffer buf = new StringBuffer() ;
        for (int i = 0;i < goods.size();i++){
            buf.append(goods.get(i)+ "、") ;
        }
        name.setText(buf);
    }

    //支付点击按钮
    private View.OnClickListener submitOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String method = "19";
            HttpManager.getBuys(SpendActivity.this, method,name.getText().toString(),tvGoodsSelectNum.getText().toString(),
                    totalprice,telephone.getText().toString(),say.getText().toString(), AccountUtils.getUserName(SpendActivity.this),
                    "未领用","评价",address,new HttpRequestHandler<String>() {

                        @Override
                        public void onSuccess(String data) {
                            if (data.toString().equals("true")){
                                MessageUtils.showMiddleToast(SpendActivity.this,"下单成功");
                            }else {
                                MessageUtils.showMiddleToast(SpendActivity.this,"下单失败");
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
