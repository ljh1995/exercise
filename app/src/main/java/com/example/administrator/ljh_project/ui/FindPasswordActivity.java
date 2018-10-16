package com.example.administrator.ljh_project.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
import com.example.administrator.ljh_project.moudle.Person;
import com.example.administrator.ljh_project.moudle.Persons;
import com.example.administrator.ljh_project.utils.BaseActivity;
import com.example.administrator.ljh_project.utils.MessageUtils;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018\4\17 0017.
 */

public class FindPasswordActivity extends BaseActivity{
    /**
     * 标题*
     */
    private TextView titleView;
    /**
     * 返回按钮*
     */
    private ImageView backImageView;

    @Bind(R.id.user)
    EditText user;

    @Bind(R.id.usernumber)
    EditText usernumber;

    @Bind(R.id.sure)
    Button sure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findppassword);
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
        titleView.setText("找回密码");
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        sure.setOnClickListener(sureClickListener);
    }
    private View.OnClickListener sureClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (user.getText().toString().equals("") || usernumber.getText().toString().equals("")){
                MessageUtils.showMiddleToast(FindPasswordActivity.this,"用户名或身份证号码不能为空");
            }else {
                String method="23";
                HttpManager.getFindPassword(FindPasswordActivity.this, method,user.getText().toString(),usernumber.getText().toString(),new HttpRequestHandler<String>() {

                    @Override
                    public void onSuccess(String data) {
                        if (data==null || data.equals("[]")){
                            MessageUtils.showMiddleToast(FindPasswordActivity.this,"用户名或身份证号码错误");
                        }else {
                            ArrayList<Persons> items = JsonUtils.parsingPerson(data);
                            //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
                            AlertDialog.Builder builder = new AlertDialog.Builder(FindPasswordActivity.this);
                            builder.setMessage("用户名:" + items.get(0).getusername() + "\n" + "\n"+ "密码:" + items.get(0).getpassword());
                            builder.setPositiveButton("返回登录", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent;
                                    intent = new Intent(FindPasswordActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                }
                            });
                            //    设置一个NegativeButton
                            builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {

                                }
                            });
                            //    显示出该对话框
                            builder.show();
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
        }
    };
}
