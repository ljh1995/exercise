package com.example.administrator.ljh_project.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.ljh_project.R;
import com.example.administrator.ljh_project.api.HttpManager;
import com.example.administrator.ljh_project.api.HttpRequestHandler;
import com.example.administrator.ljh_project.api.JsonUtils;
import com.example.administrator.ljh_project.moudle.Comment;
import com.example.administrator.ljh_project.utils.AccountUtils;
import com.example.administrator.ljh_project.utils.BaseActivity;
import com.example.administrator.ljh_project.utils.MessageUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018\3\12 0012.
 */

public class BuyActivity extends BaseActivity {
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
    @Bind(R.id.ivGoodsAdd)
    ImageView ivGoodsAdd;
    @Bind(R.id.ivGoodsMinus)
    ImageView ivGoodsMinus;

    private String foodid;
    private String type;
    private String totalprice;
    private String address;

    int number = 1;
    int prize;

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
        address= getIntent().getExtras().getString("address");
        foodid= getIntent().getExtras().getString("foodid");
        type = getIntent().getExtras().getString("name");
        totalprice = getIntent().getExtras().getString("price");
        prize = Integer.parseInt(totalprice);
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
        name.setText(type);
        tvGoodsSelectNum.setText(String.valueOf(number));
        price.setText("¥"+prize);

        ivGoodsMinus.setOnClickListener(ivGoodsMinusOnClickListener);
        ivGoodsAdd.setOnClickListener(ivGoodsAddOnClickListener);
        submit.setOnClickListener(submitOnClickListener);
    }

    //加号按钮点击
    private View.OnClickListener ivGoodsAddOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            number = Integer.parseInt(tvGoodsSelectNum.getText().toString());
            number++;
            prize = prize + Integer.parseInt(totalprice);
            tvGoodsSelectNum.setText(String.valueOf(number));
            price.setText("¥"+prize);
        }
    };
    //减号点击按钮
    private View.OnClickListener ivGoodsMinusOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            number = Integer.parseInt(tvGoodsSelectNum.getText().toString());
            if (number == 0){
                Toast.makeText(BuyActivity.this, "购买数量不可以小于0哦",
                        Toast.LENGTH_SHORT).show();

            }else {
                number--;
                tvGoodsSelectNum.setText(String.valueOf(number));
                prize = prize - Integer.parseInt(totalprice);
                price.setText("¥"+prize);
            }
        }
    };
    //支付点击按钮
    private View.OnClickListener submitOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (telephone.getText().toString().equals("")){
                MessageUtils.showMiddleToast(BuyActivity.this,"联系电话不能为空！");
            }
            String method = "14";
            HttpManager.getBuy(BuyActivity.this, method,name.getText().toString(),tvGoodsSelectNum.getText().toString(),
                    String.valueOf(prize),telephone.getText().toString(),say.getText().toString(), AccountUtils.getUserName(BuyActivity.this),"未领用",
                    foodid,"评价",address,new HttpRequestHandler<String>() {

                @Override
                public void onSuccess(String data) {
                    if (data.toString().equals("true")){
                        MessageUtils.showMiddleToast(BuyActivity.this,"下单成功");
                    }else {
                        MessageUtils.showMiddleToast(BuyActivity.this,"下单失败");
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
