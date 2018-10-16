package com.example.administrator.ljh_project.fragment;

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
import com.example.administrator.ljh_project.adapter.OrderAdapter;
import com.example.administrator.ljh_project.api.HttpManager;
import com.example.administrator.ljh_project.api.HttpRequestHandler;
import com.example.administrator.ljh_project.api.JsonUtils;
import com.example.administrator.ljh_project.moudle.Food;
import com.example.administrator.ljh_project.moudle.Trade;
import com.example.administrator.ljh_project.utils.AccountUtils;
import com.example.administrator.ljh_project.widget.SwipeRefreshLayout;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018\3\20 0020.
 */

public class OrderFragment extends android.support.v4.app.Fragment implements SwipeRefreshLayout.OnLoadListener, SwipeRefreshLayout.OnRefreshListener {
    /**
     * 标题*
     */
    private TextView titleView;
    /**
     * 返回按钮*
     */
    private ImageView backImageView;

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

    LinearLayoutManager layoutManager;
    /**
     * 适配器*
     */
    private OrderAdapter orderAdapter;

    private TextView order;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_list, container, false);
        ButterKnife.bind(getActivity());
        findByIdView(view);
        initView();
        return view;
    }
    private void findByIdView(View view) {
        titleView = (TextView) view.findViewById(R.id.title_name);
        backImageView = (ImageView) view.findViewById(R.id.title_back_id);
        order = (TextView) view.findViewById(R.id.order);
        if (AccountUtils.getDisplayName(getActivity()).equals("管理员")){
            order.setText("全部订单");
        }else {
            order.setText("我的订单");
        }

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_id);
        refresh_layout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        nodatalayout = (LinearLayout) view.findViewById(R.id.have_not_data_id);
    }

    protected void initView() {
        titleView.setText(getResources().getString(R.string.order));
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
        orderAdapter = new OrderAdapter(getActivity());
        recyclerView.setAdapter(orderAdapter);
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

    private void getData() {
        if (AccountUtils.getDisplayName(getActivity()).equals("管理员") || AccountUtils.getDisplayName(getActivity()).equals("服务员")){
            String method="15";
            HttpManager.getOrder(getActivity(), method,AccountUtils.getDepartment(getActivity()), new HttpRequestHandler<String>() {

                @Override
                public void onSuccess(String data) {
                    ArrayList<Trade> items = JsonUtils.parsingTrade(data);
                    refresh_layout.setRefreshing(false);
                    refresh_layout.setLoading(false);
                    if (items.size() == 0){
                        nodatalayout.setVisibility(View.VISIBLE);
                    }
                    if (items != null || items.size() != 0){
                        orderAdapter.update(items);
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
        }else {
            String method="16";
            HttpManager.getOrders(getActivity(), method,AccountUtils.getUserName(getActivity()), new HttpRequestHandler<String>() {

                @Override
                public void onSuccess(String data) {
                    ArrayList<Trade> items = JsonUtils.parsingTrade(data);
                    if (items.size() == 0){
                        nodatalayout.setVisibility(View.VISIBLE);
                    }
                    refresh_layout.setRefreshing(false);
                    refresh_layout.setLoading(false);
                    if (items != null || items.size() != 0){
                        orderAdapter.update(items);
                    }else {
//                        nodatalayout.setVisibility(View.VISIBLE);
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

    }

    @Override
    public void onLoad() {
        getData();
    }

    @Override
    public void onRefresh() {
        getData();
    }
}
