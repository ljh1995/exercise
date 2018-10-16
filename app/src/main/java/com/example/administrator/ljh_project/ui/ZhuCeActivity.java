package com.example.administrator.ljh_project.ui;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.ljh_project.R;
import com.example.administrator.ljh_project.api.HttpManager;
import com.example.administrator.ljh_project.api.HttpRequestHandler;
import com.example.administrator.ljh_project.api.JsonUtils;
import com.example.administrator.ljh_project.moudle.Evaluate;
import com.example.administrator.ljh_project.utils.BaseActivity;
import com.example.administrator.ljh_project.utils.MessageUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018\3\7 0007.
 */

public class ZhuCeActivity extends BaseActivity {
    @Bind(R.id.user_login_id) //用户名
            EditText mUsername;
    @Bind(R.id.user_login_password) //密码
            EditText mPassword;
    @Bind(R.id.user_zhuce)
    Button mLogin;
    @Bind(R.id.displayName) //角色
            EditText displayName;

    private String method="9";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuce);
        ButterKnife.bind(this);
        findViewById();
        initView();

    }

    @Override
    protected void findViewById() {

    }

    @Override
    protected void initView() {
      mLogin.setOnClickListener(zhuceOnClickListener);

    }
    private View.OnClickListener zhuceOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if ((!mUsername.getText().toString().equals("")) && (!mPassword.getText().toString().equals(""))
                    &&(!displayName.getText().toString().equals(""))){
                HttpManager.getzhuce(ZhuCeActivity.this, method,mUsername.getText().toString(),mPassword.getText().toString(),displayName.getText().toString(),new HttpRequestHandler<String>() {

                    @Override
                    public void onSuccess(String data) {
                        if (data.equals("true")){
                            MessageUtils.showMiddleToast(ZhuCeActivity.this,"注册成功");
                            Intent intent;
                            intent = new Intent(ZhuCeActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }else {
                            MessageUtils.showMiddleToast(ZhuCeActivity.this,"服务出小差了，请稍后注册");
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
                MessageUtils.showMiddleToast(ZhuCeActivity.this,"注册信息不能为空");
            }

        }
    };

}
