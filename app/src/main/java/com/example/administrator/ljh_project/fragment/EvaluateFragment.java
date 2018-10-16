package com.example.administrator.ljh_project.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.eowise.recyclerview.stickyheaders.StickyHeadersItemDecoration;
import com.example.administrator.ljh_project.adapter.EvaluateAdapter;
import com.example.administrator.ljh_project.api.HttpManager;
import com.example.administrator.ljh_project.api.HttpRequestHandler;
import com.example.administrator.ljh_project.api.JsonUtils;
import com.example.administrator.ljh_project.moudle.Evaluate;
import com.example.administrator.ljh_project.R;
import com.example.administrator.ljh_project.utils.AccountUtils;
import com.example.administrator.ljh_project.utils.DataUtils;
import com.example.administrator.ljh_project.utils.MessageUtils;

import java.util.ArrayList;
import java.util.List;


public class EvaluateFragment extends Fragment {

    private String method="4";
    private StickyHeadersItemDecoration top;

    EvaluateAdapter evaluateAdapter;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    Button button;
    EditText search;

    String address;

    private ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_evaluate, null);
        geiIntentData();

        findViewById(view);
        mProgressDialog = ProgressDialog.show(getActivity(), null,
                "正在获取数据中...", true, true);

                initView();
        return view;
    }
    /**
     * 获取初始话数据*
     */
    private void geiIntentData() {
        Bundle bundle=this.getArguments();
        address= bundle.getString("address");
    }

    protected void findViewById(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleView);
//        button = (Button) view.findViewById(R.id.button);
//        search = (EditText) view.findViewById(R.id.search_edit);
    }

    protected void initView() {
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        evaluateAdapter = new EvaluateAdapter(getActivity());
        recyclerView.setAdapter(evaluateAdapter);
        getData();
//        button.setOnClickListener(buttononClickListener);
    }

    private void getData() {

        HttpManager.getEvaluate(getActivity(), method,address, new HttpRequestHandler<String>() {

            @Override
            public void onSuccess(String data) {
                mProgressDialog.dismiss();
                if (data.equals("[]")){
                    MessageUtils.showMiddleToast(getActivity(), "暂无相关数据");
                }
                ArrayList<Evaluate> items = JsonUtils.parsingEvaluate(data);
                if (items != null || items.size() != 0){
                    evaluateAdapter.update(items);
                }else {
                    mProgressDialog.dismiss();
                    MessageUtils.showMiddleToast(getActivity(),"暂无评价");
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
//    private View.OnClickListener buttononClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            method="5";
//            HttpManager.getEvaluates(getActivity(), method, AccountUtils.getUserName(getActivity()),search.getText().toString(), new HttpRequestHandler<String>() {
//
//                @Override
//                public void onSuccess(String data) {
//                    if (data.equals("true")){
//                        MessageUtils.showMiddleToast(getActivity(),"评论成功");
//                    }else {
//                        MessageUtils.showMiddleToast(getActivity(),"评论失败，请稍后评论哦");
//                    }
//                }
//
//                @Override
//                public void onSuccess(String data, int totalPages, int currentPage) {
//
//                }
//
//                @Override
//                public void onFailure(String error) {
//                }
//            });
//        }
//    };
}

