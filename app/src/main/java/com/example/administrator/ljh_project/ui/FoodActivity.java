package com.example.administrator.ljh_project.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eowise.recyclerview.stickyheaders.OnHeaderClickListener;
import com.eowise.recyclerview.stickyheaders.StickyHeadersBuilder;
import com.eowise.recyclerview.stickyheaders.StickyHeadersItemDecoration;
import com.example.administrator.ljh_project.R;
import com.example.administrator.ljh_project.adapter.BigramHeaderAdapter;
import com.example.administrator.ljh_project.adapter.PersonAdapter;
import com.example.administrator.ljh_project.adapter.RecycleGoodsCategoryListAdapter;
import com.example.administrator.ljh_project.adapter.TabFragmentAdapter;
import com.example.administrator.ljh_project.api.HttpManager;
import com.example.administrator.ljh_project.api.HttpRequestHandler;
import com.example.administrator.ljh_project.api.JsonUtils;
import com.example.administrator.ljh_project.fragment.EvaluateFragment;
import com.example.administrator.ljh_project.fragment.GoodsFragment;
import com.example.administrator.ljh_project.moudle.Food;
import com.example.administrator.ljh_project.utils.AnimationUtil;
import com.example.administrator.ljh_project.utils.BaseActivity;
import com.example.administrator.ljh_project.widget.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018\2\28 0028.
 */

public class FoodActivity extends BaseActivity {
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private AppBarLayout appBarLayout;
    private TabLayout slidingTabLayout;
    //fragment列表
    private List<Fragment> mFragments=new ArrayList<>();
    //tab名的列表
    private List<String> mTitles=new ArrayList<>();
    private ViewPager viewPager;
    private TabFragmentAdapter adapter;
    private TextView shopCartNum;
    private TextView totalPrice;
    private TextView noShop;
    private TextView goTOCheckOut;
    private TextView howMoneyToDelivery;
    private RelativeLayout shopCartMain;
    private ViewGroup anim_mask_layout;//动画层

    ArrayList<String> goods = new ArrayList<>();

    String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_tab);

        setCollsapsing();
        geiIntentData();
        initView();
        setViewPager();
    }

    @Override
    protected void findViewById() {

    }

    /**
     * 获取初始话数据*
     */
    private void geiIntentData() {
        address= getIntent().getExtras().getString("address");
    }

    protected void initView() {
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        slidingTabLayout = (TabLayout) findViewById(R.id.slidinglayout);
        viewPager = (ViewPager) findViewById(R.id.vp);
        shopCartMain=(RelativeLayout)findViewById(R.id.shopCartMain);
        shopCartNum=(TextView)findViewById(R.id.shopCartNum);
        totalPrice=(TextView)findViewById(R.id.totalPrice);
        noShop=(TextView)findViewById(R.id.noShop);
        goTOCheckOut=(TextView)findViewById(R.id.goTOCheckOut);
        howMoneyToDelivery=(TextView)findViewById(R.id.howMoneyToDelivery);
    }

    private void setViewPager() {

        GoodsFragment goodsFragment=new GoodsFragment();
        EvaluateFragment evaluateFragment=new EvaluateFragment();
        Bundle bundle = new Bundle();     //创建bundle来封装传递给fragment的参数
        bundle.putString("address", address);
        evaluateFragment.setArguments(bundle);         //设置传递的对象
        goodsFragment.setArguments(bundle);
        mFragments.add(goodsFragment);
        mFragments.add(evaluateFragment);

        mTitles.add("商品");
        mTitles.add("评价");

        adapter=new TabFragmentAdapter(getSupportFragmentManager(),mFragments,mTitles);
        viewPager.setAdapter(adapter);
        slidingTabLayout.setupWithViewPager(viewPager);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        shopCartMain.startAnimation(
                                AnimationUtil.createInAnimation(FoodActivity.this, shopCartMain.getMeasuredHeight()));
                        break;
                    case 1:
                        shopCartMain.startAnimation(
                                AnimationUtil.createOutAnimation(FoodActivity.this, shopCartMain.getMeasuredHeight()));
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void setCollsapsing() {
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);
        collapsingToolbarLayout.setStatusBarScrimColor(getResources().getColor(R.color.touming));
        collapsingToolbarLayout.setContentScrim(getResources().getDrawable(R.mipmap.background));
    }


    @Override
    protected void setStatusBar() {
        super.setStatusBar();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }




    /**
     * 添加 或者  删除  商品发送的消息处理
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(final MessageEvent event) {
        if(event!=null){
            if(event.num>0){
                shopCartNum.setText(String.valueOf(event.num));
                shopCartNum.setVisibility(View.VISIBLE);
                totalPrice.setVisibility(View.VISIBLE);
                noShop.setVisibility(View.GONE);
                goTOCheckOut.setVisibility(View.VISIBLE);
            }else{
                shopCartNum.setVisibility(View.GONE);
                totalPrice.setVisibility(View.GONE);
                noShop.setVisibility(View.VISIBLE);
                goTOCheckOut.setVisibility(View.GONE);
            }
            totalPrice.setText("¥"+String.valueOf(event.price));
            goTOCheckOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(FoodActivity.this, SpendActivity.class);
                    for (int i=0;i<event.goods.size();i++){
                        goods.add(event.goods.get(i).getName());
                    }
                    intent.putExtra("price", String.valueOf(event.price));
                    intent.putExtra("num", String.valueOf(event.num));
                    intent.putExtra("address", address);
                    intent.putStringArrayListExtra("goods",goods);
                    startActivity(intent);
                }
            });
        }

    }


    /**
     * 设置动画（点击添加商品）
     * @param v
     * @param startLocation
     */
    public void setAnim(final View v, int[] startLocation) {
        anim_mask_layout = null;
        anim_mask_layout = createAnimLayout();
        anim_mask_layout.addView(v);//把动画小球添加到动画层
        final View view = addViewToAnimLayout(anim_mask_layout, v, startLocation);
        int[] endLocation = new int[2];// 存储动画结束位置的X、Y坐标
        shopCartNum.getLocationInWindow(endLocation);
        // 计算位移
        int endX = 0 - startLocation[0] + 40;// 动画位移的X坐标
        int endY = endLocation[1] - startLocation[1];// 动画位移的y坐标

        TranslateAnimation translateAnimationX = new TranslateAnimation(0,endX, 0, 0);
        translateAnimationX.setInterpolator(new LinearInterpolator());
        translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);

        TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0, 0, endY);
        translateAnimationY.setInterpolator(new AccelerateInterpolator());
        translateAnimationY.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationY.setFillAfter(true);

        AnimationSet set = new AnimationSet(false);
        set.setFillAfter(false);
        set.addAnimation(translateAnimationY);
        set.addAnimation(translateAnimationX);
        set.setDuration(400);// 动画的执行时间
        view.startAnimation(set);
        // 动画监听事件
        set.setAnimationListener(new Animation.AnimationListener() {
            // 动画的开始
            @Override
            public void onAnimationStart(Animation animation) {
                v.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            // 动画的结束
            @Override
            public void onAnimationEnd(Animation animation) {
                v.setVisibility(View.GONE);
            }
        });

    }

    /**
     * 初始化动画图层
     * @return
     */
    private ViewGroup createAnimLayout() {
        ViewGroup rootView = (ViewGroup) this.getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setId(Integer.MAX_VALUE-1);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }

    /**
     * 将View添加到动画图层
     * @param parent
     * @param view
     * @param location
     * @return
     */
    private View addViewToAnimLayout(final ViewGroup parent, final View view,
                                     int[] location) {
        int x = location[0];
        int y = location[1];
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setLayoutParams(lp);
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
