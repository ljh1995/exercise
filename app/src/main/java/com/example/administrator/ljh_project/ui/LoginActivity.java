package com.example.administrator.ljh_project.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.ljh_project.R;
import com.example.administrator.ljh_project.api.HttpManager;
import com.example.administrator.ljh_project.api.HttpRequestHandler;
import com.example.administrator.ljh_project.manager.AppManager;
import com.example.administrator.ljh_project.utils.AccountUtils;
import com.example.administrator.ljh_project.utils.BaseActivity;
import com.example.administrator.ljh_project.utils.MessageUtils;
import com.nostra13.universalimageloader.utils.L;
import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018\1\2 0002.
 */

public class LoginActivity extends BaseActivity {

    @Bind(R.id.user_login_id) //用户名
            EditText mUsername;
    @Bind(R.id.user_login_password) //密码
            EditText mPassword;
    @Bind(R.id.user_login)
    Button mLogin;
    @Bind(R.id.zhuce_text)
    TextView zhuce;
    @Bind(R.id.forget_password)
    TextView forget_password;
    @Bind(R.id.isremenber_password)
    CheckBox checkBox; //记住密码
    private boolean isRemember; //记住密码

    private ProgressDialog mProgressDialog;

    String imei;
    String method;

    String personId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);
        //蒲公英版本更新
        updata();
        imei = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).getDeviceId();
        method = "1";
        findViewById();
        initView();

    }

    protected void findViewById() {
    }

    protected void initView() {
        zhuce.setVisibility(View.GONE);
        isRemember = AccountUtils.getIsChecked(LoginActivity.this);
        mUsername.setText(AccountUtils.getUserName(LoginActivity.this));
        if (isRemember) {

            mPassword.setText(AccountUtils.getUserPassword(LoginActivity.this));
            checkBox.setChecked(isRemember);
        }
        zhuce.setOnClickListener(zhuceOnClickListener);
        forget_password.setOnClickListener(forgetOnClickListener);
    }

    private View.OnClickListener zhuceOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent;
            intent = new Intent(LoginActivity.this, ZhuCeActivity.class);
            startActivity(intent);
        }


    };
    private View.OnClickListener forgetOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent;
            intent = new Intent(LoginActivity.this, FindPasswordActivity.class);
            startActivity(intent);
        }


    };

    @OnCheckedChanged(R.id.isremenber_password)
    void onChecked(boolean isChecked) {
        isRemember = isChecked;
    }

    //登陆按钮
    @OnClick(R.id.user_login)
    void setOnClick() {
        if (mUsername.getText().length() == 0) {
            mUsername.setError(getString(R.string.login_error_empty_user));
            mUsername.requestFocus();
        } else if (mPassword.getText().length() == 0) {
            mPassword.setError(getString(R.string.login_error_empty_passwd));
            mPassword.requestFocus();
        } else {
            login();
        }
    }

    /**
     * 登录界面
     */
    private void login() {
        mProgressDialog = ProgressDialog.show(LoginActivity.this, null,
                getString(R.string.login_loging), true, true);

        AccountUtils.setChecked(LoginActivity.this, isRemember);
        AccountUtils.setUserNameAndPassWord(LoginActivity.this, mUsername.getText().toString(),mPassword.getText().toString());

        HttpManager.loginWithUsername(LoginActivity.this,method,mUsername.getText().toString(), mPassword.getText().toString(),
                new HttpRequestHandler<String>() {
                    @Override
                    public void onSuccess(String data) {
                        mProgressDialog.dismiss();

                        AccountUtils.setUserName(LoginActivity.this, mUsername.getText().toString());

                        if (data != null) {
                            getBaseApplication().setUsername(mUsername.getText().toString());
                            try {//保存登录返回信息;
                                JSONObject object = new JSONObject(data);
                                getBaseApplication().setUsername(mUsername.getText().toString());
                                AccountUtils.setLoginDetails(LoginActivity.this, object.optString("sex"), object.optString("loginid"),
                                        object.optString("displayName"),object.optString("department"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            MessageUtils.showMiddleToast(LoginActivity.this, getString(R.string.login_successful_hint));
                            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                            startActivityForResult(intent, 0);
                            finish();
                        }else {
                            MessageUtils.showMiddleToast(LoginActivity.this,"用户名或密码错误");
                        }
                    }

                    @Override
                    public void onSuccess(String data, int totalPages, int currentPage) {
                        if (data != null) {
                            MessageUtils.showMiddleToast(LoginActivity.this, getString(R.string.login_successful_hint));
                            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                            startActivityForResult(intent, 0);

                        }
                    }

                    @Override
                    public void onFailure(String error) {
                        MessageUtils.showErrorMessage(LoginActivity.this, error);
                        mProgressDialog.dismiss();
                    }
                });
    }

    private long exitTime = 0;

    @Override
    public void onBackPressed() {


        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, getString(R.string.exit_text), Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            AppManager.AppExit(LoginActivity.this);
        }
    }

    private  void updata(){
        PgyUpdateManager.register(LoginActivity.this,null,
                new UpdateManagerListener() {

                    @Override
                    public void onUpdateAvailable(final String result) {

                        // 将新版本信息封装到AppBean中
                        final AppBean appBean = getAppBeanFromString(result);
                        new AlertDialog.Builder(LoginActivity.this)
                                .setTitle("更新")
                                .setMessage("新版本提示")
                                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();

                                    }
                                })
                                .setNegativeButton(
                                        "确定",
                                        new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(
                                                    DialogInterface dialog,
                                                    int which) {
                                                startDownloadTask(
                                                        LoginActivity.this,
                                                        appBean.getDownloadURL());
                                            }
                                        })
                                .show();
                    }

                    @Override
                    public void onNoUpdateAvailable() {
                    }
                });
    }

}
