package com.example.administrator.ljh_project.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.administrator.ljh_project.R;
import com.example.administrator.ljh_project.adapter.CommentAdapter;
import com.example.administrator.ljh_project.adapter.NewsAdapter;
import com.example.administrator.ljh_project.api.HttpManager;
import com.example.administrator.ljh_project.api.HttpRequestHandler;
import com.example.administrator.ljh_project.api.JsonUtils;
import com.example.administrator.ljh_project.manager.FullyLinearLayoutManager;
import com.example.administrator.ljh_project.moudle.Comment;
import com.example.administrator.ljh_project.moudle.News;
import com.example.administrator.ljh_project.moudle.Person;
import com.example.administrator.ljh_project.ui.BuyActivity;
import com.example.administrator.ljh_project.ui.FoodActivity;
import com.example.administrator.ljh_project.ui.FoodInformationActivity;
import com.example.administrator.ljh_project.ui.NewsAddActivity;
import com.example.administrator.ljh_project.ui.NewsInformationActivity;
import com.example.administrator.ljh_project.ui.Personal;
import com.example.administrator.ljh_project.utils.AccountUtils;
import com.example.administrator.ljh_project.utils.DataUtils;
import com.example.administrator.ljh_project.utils.MessageUtils;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018\1\4 0004.
 */

public class ShouYeFragment  extends android.support.v4.app.Fragment {

    private GridView gridView;

    private List<Map<String, Object>> data_list;
    private SimpleAdapter sim_adapter;

    // 图片封装为一个数组
    private int[] icon = null;
    private String[] iconName = null;

    RecyclerView recyclerView;
    /**
     * 暂无数据*
     */
    private LinearLayout nodatalayout;

    FullyLinearLayoutManager layoutManager;
    NewsAdapter newsAdapter;

    private ImageView add;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_shoye, container, false);
        ButterKnife.bind(getActivity());
        findViewById(view);
        setListener();
        init();
        return view;
    }

    protected void findViewById(View view) {
        add= (ImageView) view.findViewById(R.id.add);
        gridView = (GridView) view.findViewById(R.id.noScrollgridview);
        nodatalayout = (LinearLayout) view.findViewById(R.id.have_not_data_id);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_id);
        Banner banner = (Banner) view.findViewById(R.id.banner);
        banner.setImageLoader(new BannerImageLoader());
        banner.setImages(Arrays.asList( R.drawable.one,
                R.drawable.two,
                R.drawable.three,
                R.drawable.four,
                R.drawable.five));
        banner.start();
    }
    private class BannerImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource((Integer)path);
        }
    }

    private void setListener() {
        gridView.setOnItemClickListener(gridViewOnItemClickListener);
    }

    protected void init() {
        if (AccountUtils.getDisplayName(getActivity()).equals("管理员")){
            add.setVisibility(View.VISIBLE);
            add.setOnClickListener(addOnClickListener);
        }
        layoutManager = new FullyLinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);//禁止rcyc嵌套滑动
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        newsAdapter = new NewsAdapter(getActivity());
        recyclerView.setAdapter(newsAdapter);
        getDate();

        isShowPage();

        //新建List
        data_list = new ArrayList<Map<String, Object>>();
        //获取数据
        getData();
        //初始化数据
//        getDataInit();
        //新建适配器
        String[] from = {"image", "text"};
        int[] to = {R.id.image, R.id.text};
        sim_adapter = new SimpleAdapter(getActivity(), data_list, R.layout.gridview_item, from, to);
        //配置适配器
        gridView.setAdapter(sim_adapter);
        gridView.setOnItemClickListener(gridViewOnItemClickListener);
    }

    public List<Map<String, Object>> getData() {
        //cion和iconName的长度是相同的，这里任选其一都可以
        for (int i = 0; i < icon.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon[i]);
            map.put("text", iconName[i]);
            data_list.add(map);
        }

        return data_list;
    }

    //判断需要显示的页面
    private void isShowPage() {

        icon = new int[]{R.drawable.icon_one,
                R.drawable.icon_two, R.drawable.icon_three, R.drawable.icon_four,
                R.drawable.icon_five, R.drawable.icon_six};
        iconName = new String[]{"兰竹苑", "兰桂苑", "兰菊苑", "兰梅苑", "兰馨苑", "兰青苑"
        };

    }

    public AdapterView.OnItemClickListener gridViewOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int postion, long l) {
            Intent intent = null;
            switch (postion) {

                case 0:
                    intent = new Intent(getActivity(), FoodActivity.class);
                    intent.putExtra("address",iconName[postion]);
                    startActivityForResult(intent, 0);
                    break;
                case 1:
                    intent = new Intent(getActivity(), FoodActivity.class);
                    intent.putExtra("address",iconName[postion]);
                    startActivityForResult(intent, 0);
                    break;
                case 2:
                    intent = new Intent(getActivity(), FoodActivity.class);
                    intent.putExtra("address",iconName[postion]);
                    startActivityForResult(intent, 0);
                    break;
                case 3:
                    intent = new Intent(getActivity(), FoodActivity.class);
                    intent.putExtra("address",iconName[postion]);
                    startActivityForResult(intent, 0);
                    break;
                case 4:
                    intent = new Intent(getActivity(), FoodActivity.class);
                    intent.putExtra("address",iconName[postion]);
                    startActivityForResult(intent, 0);
                    break;
                case 5:
                    intent = new Intent(getActivity(), FoodActivity.class);
                    intent.putExtra("address",iconName[postion]);
                    startActivityForResult(intent, 0);
                    break;

            }
        }
    };

    private void getDate(){
        String method="10";
        HttpManager.getNews(getActivity(), method, new HttpRequestHandler<String>() {

            @Override
            public void onSuccess(String data) {
                ArrayList<News> items = JsonUtils.parsingNews(data);
                if (items != null || items.size() != 0){
                    newsAdapter.update(items);
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

    }
    //点击新增按钮
    private View.OnClickListener addOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), NewsAddActivity.class);
            startActivity(intent);
        }
    };
}

