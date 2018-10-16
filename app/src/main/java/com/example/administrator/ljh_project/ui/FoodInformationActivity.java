package com.example.administrator.ljh_project.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.ljh_project.R;
import com.example.administrator.ljh_project.adapter.CommentAdapter;
import com.example.administrator.ljh_project.api.HttpManager;
import com.example.administrator.ljh_project.api.HttpRequestHandler;
import com.example.administrator.ljh_project.api.JsonUtils;
import com.example.administrator.ljh_project.manager.FullyLinearLayoutManager;
import com.example.administrator.ljh_project.moudle.Comment;
import com.example.administrator.ljh_project.moudle.Food;
import com.example.administrator.ljh_project.utils.BaseActivity;
import com.example.administrator.ljh_project.utils.MessageUtils;
import com.example.administrator.ljh_project.view.LazyViewPager;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018\1\14 0014.
 */

public class FoodInformationActivity extends BaseActivity {
    /**
     * 标题*
     */
    private TextView titleView;
    /**
     * 返回按钮*
     */
    private ImageView backImageView;

    @Bind(R.id.foodimage)
    ImageView foodimage;

    @Bind(R.id.prize)
    TextView foodprize;

    @Bind(R.id.foodname)
    TextView foodname;

    @Bind(R.id.buy)
    TextView foodbuy;

    @Bind(R.id.collect)
    ImageView collect;

    @Bind(R.id.number)
    TextView number;

    @Bind(R.id.address)
    TextView address;

    @Bind(R.id.telephone)
    ImageView telephone;

    /**
     * 暂无数据*
     */
    private LinearLayout nodatalayout;

    private Food food;

    RecyclerView recyclerView;
    FullyLinearLayoutManager layoutManager;
    CommentAdapter commentAdapter;
    String method="8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_food_information);
        ButterKnife.bind(this);
        initData();
        findViewById();
        initView();
    }

    /**
     * 获取初始话数据*
     */
    private void initData() {
        food = (Food) getIntent().getSerializableExtra("food");
    }

    @Override
    protected void findViewById() {
        titleView = (TextView) findViewById(R.id.title_name);
        backImageView = (ImageView) findViewById(R.id.title_back_id);
        nodatalayout = (LinearLayout) findViewById(R.id.have_not_data_id);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_id);
    }

    @Override
    protected void initView() {
        titleView.setText(getResources().getString(R.string.information));
        layoutManager = new FullyLinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);//禁止rcyc嵌套滑动
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        commentAdapter = new CommentAdapter(FoodInformationActivity.this);
        recyclerView.setAdapter(commentAdapter);
        HttpManager.getComment(this, method,food.getid(), new HttpRequestHandler<String>() {

            @Override
            public void onSuccess(String data) {
                ArrayList<Comment> items = JsonUtils.parsingComment(data);
                if (items != null || items.size() != 0){
                    commentAdapter.update(items);
                    if (items.size() == 0){
                        nodatalayout.setVisibility(View.VISIBLE);
                    }
                }else {
                    nodatalayout.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onSuccess(String data, int totalPages, int currentPage) {

            }

            @Override
            public void onFailure(String error) {
            }
        });

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (food != null) {
            foodprize.setText(food.getprize() == null ? "¥" :"¥" + food.getprize());
            foodname.setText(food.getname() == null ? "" :food.getname());
            number.setText(food.getnumber() == null ? "" :food.getnumber());
            address.setText(food.getaddress() == null ? "" :food.getaddress());
            ImageLoader.getInstance().displayImage(food.getgoodsImgUrl(), foodimage);
        }
        collect.setOnClickListener(collectOnClickListener);
        foodbuy.setOnClickListener(foodbuyOnClickListener);
        telephone.setOnClickListener(telephoneOnClickListener);
    }

    private View.OnClickListener telephoneOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            MessageUtils.showMiddleToast(FoodInformationActivity.this,"店家还未开通电话服务");
        }


    };

    private View.OnClickListener collectOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            collect.setImageResource(R.drawable.collects);
        }


    };

    private View.OnClickListener foodbuyOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(FoodInformationActivity.this, BuyActivity.class);
            intent.putExtra("name",food.getname());
            intent.putExtra("price", food.getprize());
            intent.putExtra("foodid", food.getid());
            intent.putExtra("address", food.getaddress());
            startActivity(intent);
        }
    };
}
