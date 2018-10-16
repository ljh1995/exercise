package com.example.administrator.ljh_project.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.ljh_project.R;
import com.example.administrator.ljh_project.manager.AppManager;
import com.example.administrator.ljh_project.ui.About_us_Activity;
import com.example.administrator.ljh_project.ui.My_danwei_Activity;
import com.example.administrator.ljh_project.ui.My_us_Activity;
import com.example.administrator.ljh_project.ui.SheZhi_Activity;
import com.example.administrator.ljh_project.utils.AccountUtils;

/**
 * Created by Administrator on 2018\1\4 0004.
 */

public class MyFragment extends android.support.v4.app.Fragment {

    private TextView nameText; //姓名

    private LinearLayout danweiText; //单位信息

    private LinearLayout versionView; //版本信息

    private LinearLayout weText; //关于我们

    private LinearLayout shezhiView; //设置

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my, container, false);
        findByIdView(view);
        setListener();
        initView();
        return view;
    }

    protected void initView() {
        nameText.setText(AccountUtils.getDisplayName(getActivity()));
    }
    /**
     * 初始化界面组件*
     */
    private void findByIdView(View view) {
        nameText = (TextView) view.findViewById(R.id.name);
        danweiText = (LinearLayout) view.findViewById(R.id.my_dwxx);
        versionView = (LinearLayout) view.findViewById(R.id.my_bbxx);
        weText = (LinearLayout) view.findViewById(R.id.my_gywm);
        shezhiView = (LinearLayout) view.findViewById(R.id.my_sz);

    }

    /**
     * 设置跳转监听
     */
    private void setListener() {
        String personId = AccountUtils.getPersonId(getActivity());
//        getPersion(personId);
        danweiText.setOnClickListener(danweiTextOnClickListener);
        versionView.setOnClickListener(versionViewOnClickListener);
        weText.setOnClickListener(weTextOnClickListener);
        shezhiView.setOnClickListener(shezhiViewOnClickListener);
    }

    private View.OnClickListener danweiTextOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(getActivity(), My_danwei_Activity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener versionViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(getActivity(), About_us_Activity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener weTextOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(getActivity(), My_us_Activity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener shezhiViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(getActivity(), SheZhi_Activity.class);
            startActivity(intent);
        }
    };


}
