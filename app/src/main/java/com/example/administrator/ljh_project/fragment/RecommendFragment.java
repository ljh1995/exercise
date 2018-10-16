package com.example.administrator.ljh_project.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.ljh_project.R;
import com.example.administrator.ljh_project.adapter.DaiBanAdapter;
import com.example.administrator.ljh_project.api.HttpManager;
import com.example.administrator.ljh_project.api.HttpRequestHandler;
import com.example.administrator.ljh_project.api.JsonUtils;
import com.example.administrator.ljh_project.moudle.Food;
import com.example.administrator.ljh_project.ui.FoodAddActivity;
import com.example.administrator.ljh_project.ui.LoginActivity;
import com.example.administrator.ljh_project.utils.AccountUtils;
import com.example.administrator.ljh_project.utils.MessageUtils;
import com.example.administrator.ljh_project.widget.SwipeRefreshLayout;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018\1\4 0004.
 */

public class RecommendFragment extends android.support.v4.app.Fragment implements SwipeRefreshLayout.OnLoadListener, SwipeRefreshLayout.OnRefreshListener {

    /**
     * 标题*
     */
    private TextView titleView;
    /**
     * 返回按钮*
     */
    private ImageView backImageView;

    /**
     * 新增按钮*
     */
    private ImageView addView;

    LinearLayoutManager layoutManager;

    /**
     * RecyclerView*
     */
    public RecyclerView recyclerView;
    /**
     * 暂无数据*
     */
    private LinearLayout nodatalayout;
    /**
     * 界面刷新*
     */
    private SwipeRefreshLayout refresh_layout = null;
    /**
     * 适配器*
     */
    private DaiBanAdapter daiBanAdapter;

    private String method="6";

    Button button;
    EditText search;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_recommend, container, false);
        ButterKnife.bind(getActivity());
        findByIdView(view);
        initView();
        return view;
    }


    private void findByIdView(View view) {
        titleView = (TextView) view.findViewById(R.id.title_name);
        backImageView = (ImageView) view.findViewById(R.id.title_back_id);
        addView = (ImageView) view.findViewById(R.id.title_add);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_id);
        refresh_layout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        nodatalayout = (LinearLayout) view.findViewById(R.id.have_not_data_id);
        button = (Button) view.findViewById(R.id.button);
        search = (EditText) view.findViewById(R.id.search_edit);
    }
    protected void initView() {
        titleView.setText(getResources().getString(R.string.today));
        button.setText("搜索");
        if (AccountUtils.getDisplayName(getActivity()).equals("管理员")){
            addView.setVisibility(View.VISIBLE);
        }
        button.setOnClickListener(buttononClickListener);
        addView.setOnClickListener(addViewClickListener);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        daiBanAdapter = new DaiBanAdapter(getActivity());
        recyclerView.setAdapter(daiBanAdapter);
        refresh_layout.setColor(R.color.holo_blue_bright,
                R.color.holo_green_light,
                R.color.holo_orange_light,
                R.color.holo_red_light);
        refresh_layout.setRefreshing(true);
        getData();

        refresh_layout.setOnRefreshListener(this);
        refresh_layout.setOnLoadListener(this);
        refresh_layout.setRefreshing(true);
    }

    @Override
    public void onLoad() {
        getData();
    }

    @Override
    public void onRefresh() {
        getData();
    }
    private void getData() {

        HttpManager.getFoods(getActivity(), method, new HttpRequestHandler<String>() {

            @Override
            public void onSuccess(String data) {
                ArrayList<Food> items = JsonUtils.parsingFood(data);
                refresh_layout.setRefreshing(false);
                refresh_layout.setLoading(false);
                if (items != null || items.size() != 0){
                    daiBanAdapter.update(items);
                }else {
                    nodatalayout.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onSuccess(String data, int totalPages, int currentPage) {

            }

            @Override
            public void onFailure(String error) {
                refresh_layout.setRefreshing(false);
                nodatalayout.setVisibility(View.VISIBLE);
            }
        });
    }

    private View.OnClickListener addViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent;
            intent = new Intent(getActivity(), FoodAddActivity.class);
            startActivity(intent);
        }
    };
    private View.OnClickListener buttononClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            method="7";
            HttpManager.getSearch(getActivity(), method,search.getText().toString(), new HttpRequestHandler<String>() {

                @Override
                public void onSuccess(String data) {
                    if (data.equals("[]")){
                        MessageUtils.showMiddleToast(getActivity(), "暂无相关数据");
                    }
                    ArrayList<Food> items = JsonUtils.parsingFood(data);
                    if (items != null || items.size() != 0){
                        daiBanAdapter.update(items);
                    }else {
                        MessageUtils.showMiddleToast(getActivity(),  "暂无相关数据");
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
    };
}
