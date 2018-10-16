package com.example.administrator.ljh_project.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;
import android.widget.Toast;

import com.eowise.recyclerview.stickyheaders.OnHeaderClickListener;
import com.eowise.recyclerview.stickyheaders.StickyHeadersBuilder;
import com.eowise.recyclerview.stickyheaders.StickyHeadersItemDecoration;
import com.example.administrator.ljh_project.adapter.BigramHeaderAdapter;
import com.example.administrator.ljh_project.adapter.PersonAdapter;
import com.example.administrator.ljh_project.adapter.RecycleGoodsCategoryListAdapter;
import com.example.administrator.ljh_project.api.HttpManager;
import com.example.administrator.ljh_project.api.HttpRequestHandler;
import com.example.administrator.ljh_project.moudle.GoodsListBean;
import com.example.administrator.ljh_project.ui.FoodActivity;
import com.example.administrator.ljh_project.utils.DataUtils;
import com.example.administrator.ljh_project.widget.GoodsListEvent;
import com.example.administrator.ljh_project.R;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


/**
 * 商品
 */
public class GoodsFragment extends BaseFragment implements PersonAdapter.OnShopCartGoodsChangeListener, OnHeaderClickListener {


    private RecyclerView mGoodsCateGoryList;
    private RecycleGoodsCategoryListAdapter mGoodsCategoryListAdapter;
    //商品类别列表
    private List<GoodsListBean.DataEntity.GoodscatrgoryEntity> goodscatrgoryEntities=new ArrayList<>();
    //商品列表
    private List<GoodsListBean.DataEntity.GoodscatrgoryEntity.GoodsitemEntity> goodsitemEntities=new ArrayList<>();

    //存储含有标题的第一个含有商品类别名称的条目的下表
    private List<Integer> titlePois = new ArrayList<>();
    //上一个标题的小标
    private int lastTitlePoi;
    private RecyclerView recyclerView;
    private PersonAdapter personAdapter;
    private StickyHeadersItemDecoration top;
    private BigramHeaderAdapter headerAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    String address;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_food, container, false);
        geiIntentData();
        initView(view);
        initData();
        return view;
    }
    /**
     * 获取初始话数据*
     */
    private void geiIntentData() {
        Bundle bundle=this.getArguments();
        address= bundle.getString("address");
    }

    private void initData() {
        String method="3";
        HttpManager.getFood(getActivity(), method,address, new HttpRequestHandler<String>() {

            @Override
            public void onSuccess(String data) {
                DataUtils.data = data;
                GoodsListBean dataList = DataUtils.GsonToBean(DataUtils.data, GoodsListBean.class);
                int i = 0;
                int j = 0;
                boolean isFirst;
                for (GoodsListBean.DataEntity dataItem : dataList.getData()) {
                    goodscatrgoryEntities.add(dataItem.getGoodscatrgory());
                    isFirst = true;
                    for (GoodsListBean.DataEntity.GoodscatrgoryEntity.GoodsitemEntity goodsitemEntity :
                            dataItem.getGoodscatrgory().getGoodsitem()
                            ) {
                        //菜品类别
                        if (isFirst) {
                            titlePois.add(j);
                            isFirst = false;
                        }
                        //具体菜名
                        j++;
                        goodsitemEntity.setId(i);
                        goodsitemEntities.add(goodsitemEntity);
                    }
                    i++;
                }

                mGoodsCategoryListAdapter = new RecycleGoodsCategoryListAdapter(goodscatrgoryEntities, getActivity());
                mGoodsCateGoryList.setLayoutManager(new LinearLayoutManager(getContext()));
                mGoodsCateGoryList.setAdapter(mGoodsCategoryListAdapter);
                mGoodsCategoryListAdapter.setOnItemClickListener(new RecycleGoodsCategoryListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        recyclerView.scrollToPosition(titlePois.get(position)+position+1);
                        mGoodsCategoryListAdapter.setCheckPosition(position);
                    }
                });

                mLinearLayoutManager =new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(mLinearLayoutManager);
                personAdapter = new PersonAdapter(getActivity(),goodsitemEntities,goodscatrgoryEntities);
                personAdapter.setmActivity(getActivity());
                headerAdapter=new BigramHeaderAdapter(getActivity(),goodsitemEntities,goodscatrgoryEntities);
                top = new StickyHeadersBuilder()
                        .setAdapter(personAdapter)
                        .setRecyclerView(recyclerView)
                        .setStickyHeadersAdapter(headerAdapter)
                        .setOnHeaderClickListener(GoodsFragment.this)
                        .build();
                recyclerView.addItemDecoration(top);
                recyclerView.setAdapter(personAdapter);
                recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        for (int i=0;i<titlePois.size();i++){
                            if(mLinearLayoutManager.findFirstVisibleItemPosition()>= titlePois.get(i)){
                                mGoodsCategoryListAdapter.setCheckPosition(i);
                            }
                        }

                    }
                });


            }

            @Override
            public void onSuccess(String data, int totalPages, int currentPage) {

            }

            @Override
            public void onFailure(String error) {
            }
        });

    }


    private void initView(View view) {
        mGoodsCateGoryList = (RecyclerView)view.findViewById(R.id.recyclerView1);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView2);
    }


    @Override
    public void onNumChange() {

    }

    @Override
    public void onHeaderClick(View header, long headerId) {
        TextView text = (TextView)header.findViewById(R.id.tvGoodsItemTitle);
        Toast.makeText(getActivity(), "Click on " + text.getText(), Toast.LENGTH_SHORT).show();
    }

    /**
     * 处理滑动 是两个ListView联动
     */
    private class MyOnGoodsScrollListener implements AbsListView.OnScrollListener {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (!(lastTitlePoi == goodsitemEntities
                    .get(firstVisibleItem)
                    .getId())) {
                lastTitlePoi = goodsitemEntities
                        .get(firstVisibleItem)
                        .getId();
                mGoodsCategoryListAdapter.setCheckPosition(goodsitemEntities
                        .get(firstVisibleItem)
                        .getId());
                mGoodsCategoryListAdapter.notifyDataSetChanged();
            }
        }
    }


    /**
     * 添加 或者  删除  商品发送的消息处理
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(GoodsListEvent event) {
        if(event.buyNums.length>0){
            for (int i=0;i<event.buyNums.length;i++){
                goodscatrgoryEntities.get(i).setBugNum(event.buyNums[i]);
            }
            mGoodsCategoryListAdapter.changeData(goodscatrgoryEntities);
        }

    }



}
