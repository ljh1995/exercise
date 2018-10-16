package com.example.administrator.ljh_project.ui;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.ljh_project.R;
import com.example.administrator.ljh_project.api.HttpManager;
import com.example.administrator.ljh_project.api.HttpRequestHandler;
import com.example.administrator.ljh_project.api.JsonUtils;
import com.example.administrator.ljh_project.moudle.Person;
import com.example.administrator.ljh_project.utils.AccountUtils;
import com.example.administrator.ljh_project.utils.BaseActivity;
import com.example.administrator.ljh_project.utils.MessageUtils;
import com.example.administrator.ljh_project.utils.NetWorkHelper;
import com.flyco.animation.BaseAnimatorSet;
import com.flyco.dialog.entity.DialogMenuItem;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalListDialog;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018\1\5 0005.
 */

public class Personal extends BaseActivity{
    /**
     * 标题*
     */
    private TextView titleView;
    /**
     * 返回按钮*
     */
    private ImageView backImageView;

    @Bind(R.id.birth)
    TextView birth;
    @Bind(R.id.birthday)
    RelativeLayout birthday;
    @Bind(R.id.user_sex)
    RelativeLayout sex;
    @Bind(R.id.cache_size_id)
    TextView number;
    @Bind(R.id.sex_id)
    TextView sextext;

    private Person person;

    private String reviseresult;

    String method;

    /**
     * 性别
     */
    private ArrayList<DialogMenuItem> sexMenuItems = new ArrayList<>();

    /**
     * 时间控件
     */
    private DatePickerDialog datePickerDialog;

    private int layoutnum;
    StringBuffer sb;
    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        ButterKnife.bind(this);
        findViewById();
        initView();
        addSextypeData();
    }

    @Override
    protected void findViewById() {
        titleView = (TextView) findViewById(R.id.title_name);
        backImageView = (ImageView) findViewById(R.id.title_back_id);

//        birth.setEnabled(false);
        /*所有时间点击事件的监听*/
        setDataListener();
        birth.setOnClickListener(new MydateListener());
    }

    @Override
    protected void initView() {
        titleView.setText(getResources().getString(R.string.my_information));
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        birth.setText(AccountUtils.getBirth(Personal.this));
        sextext.setText(AccountUtils.getSex(Personal.this));
        number.setText(AccountUtils.getUserName(Personal.this));
        sex.setOnClickListener(sexOnClickListener);
    }

    /**
     * 添加数据*
     */
    private void addSextypeData() {
        String[] lctypes = getResources().getStringArray(R.array.sex);

        for (int i = 0; i < lctypes.length; i++)
            sexMenuItems.add(new DialogMenuItem(lctypes[i], 0));


    }

    /**
     * 性别
     */
    private View.OnClickListener sexOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            NormalListDialog1();
        }
    };
    /**
     * 性别*
     */
    private void NormalListDialog1() {
        final NormalListDialog dialog = new NormalListDialog(Personal.this, sexMenuItems);
        dialog.title("请选择")//
                .showAnim(mBasIn)//
                .dismissAnim(mBasOut)//
                .show();
        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {

                sextext.setText(sexMenuItems.get(position).mOperName);

                dialog.dismiss();
                startAsyncTask();
            }
        });
    }
    /**
     * 设置时间选择器*
     */
    private void setDataListener() {
        final Calendar objTime = Calendar.getInstance();
        int iYear = objTime.get(Calendar.YEAR);
        int iMonth = objTime.get(Calendar.MONTH);
        int iDay = objTime.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this, new datelistener(), iYear, iMonth, iDay);
    }

    private class MydateListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            sb = new StringBuffer();
            datePickerDialog.show();
        }
    }
    private class datelistener implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            sb = new StringBuffer();
            monthOfYear = monthOfYear + 1;
            if (dayOfMonth < 10) {
                sb.append(year % 10000 + "-" + monthOfYear + "-" + "0" + dayOfMonth);
            } else {
                sb.append(year % 10000 + "-" + monthOfYear + "-" + dayOfMonth);
            }
            birth.setText(sb);
            startAsyncTask();
        }
    }

    /*
   * 提交数据
   */
    private void startAsyncTask() {
        if (NetWorkHelper.isNetwork(Personal.this)) {
            MessageUtils.showMiddleToast(Personal.this, "暂无网络,现离线保存数据!");
            closeProgressDialog();
        } else {
            method="2";

            HttpManager.getData(Personal.this, method ,AccountUtils.getPersonId(Personal.this),birth.getText().toString(),sextext.getText().toString(), new HttpRequestHandler<String>() {
                @Override
                public void onSuccess(String data) {
                    if (data.equals("true")){
                        MessageUtils.showMiddleToast(Personal.this,"保存成功");
                    }else {
                        MessageUtils.showMiddleToast(Personal.this,"保存失败，请稍后再试哦");
                    }
                    Log.i(TAG, "onSucc" + data);
                }

                @Override
                public void onSuccess(String data, int totalPages, int currentPage) {

                }

                @Override
                public void onFailure(String error) {
                }
            });
        }

    }

}
