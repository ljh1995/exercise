package com.example.administrator.ljh_project.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.ljh_project.R;
import com.example.administrator.ljh_project.api.HttpManager;
import com.example.administrator.ljh_project.api.HttpRequestHandler;
import com.example.administrator.ljh_project.api.JsonUtils;
import com.example.administrator.ljh_project.moudle.Food;
import com.example.administrator.ljh_project.utils.AccountUtils;
import com.example.administrator.ljh_project.utils.BaseActivity;
import com.example.administrator.ljh_project.utils.MessageUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018\3\9 0009.
 */

public class PasswordActivity extends BaseActivity{
    /**
     * 标题*
     */
    private TextView titleView;
    /**
     * 返回按钮*
     */
    private ImageView backImageView;
    @Bind(R.id.oldpassword)
    EditText oldpassword;//旧密码
    @Bind(R.id.newpassword)
    EditText newpassword;//新密码
    @Bind(R.id.user_sure)
    Button sure;//确定


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatepassword);
        ButterKnife.bind(this);
        findViewById();
        initView();
    }
    @Override
    protected void findViewById() {
        titleView = (TextView) findViewById(R.id.title_name);
        backImageView = (ImageView) findViewById(R.id.title_back_id);
    }

    @Override
    protected void initView() {
        titleView.setText(getResources().getString(R.string.updatepassword));
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        sure.setOnClickListener(sureonClickListener);
    }
    private View.OnClickListener sureonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if ((!newpassword.getText().toString().equals("")) && (!oldpassword.getText().toString().equals(""))){
                String method="11";
                HttpManager.getPassword(PasswordActivity.this, method, AccountUtils.getPersonId(PasswordActivity.this),newpassword.getText().toString(), new HttpRequestHandler<String>() {

                    @Override
                    public void onSuccess(String data) {
                        if (data.equals("true")){
                            MessageUtils.showMiddleToast(PasswordActivity.this,"修改成功");
                        }else {
                            MessageUtils.showMiddleToast(PasswordActivity.this,"修改失败，请重新输入");
                        }

                    }

                    @Override
                    public void onSuccess(String data, int totalPages, int currentPage) {

                    }

                    @Override
                    public void onFailure(String error) {
                    }
                });
            }else {
                MessageUtils.showMiddleToast(PasswordActivity.this,"请输入密码");
            }
        }
    };
}
