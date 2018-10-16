package com.example.administrator.ljh_project.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.ljh_project.R;
import com.example.administrator.ljh_project.utils.BaseActivity;
import com.example.administrator.ljh_project.utils.MessageUtils;
import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018\1\5 0005.
 */

public class SheZhi_Activity extends BaseActivity{
    /**
     * 标题*
     */
    private TextView titleView;
    /**
     * 返回按钮*
     */
    private ImageView backImageView;

    @Bind(R.id.down_text_id)
    TextView downlayout;//数据下载
    @Bind(R.id.setting_data_clear)
    RelativeLayout clearlayout;//清楚缓存
    @Bind(R.id.update)
    TextView update;//检查更新
    @Bind(R.id.number)
    TextView number;//切换账号
    @Bind(R.id.updatepassword)
    TextView updatepassword;//修改密码

    private ProgressDialog mProgressDialog;

    Intent intent;
//    @Bind(R.id.version_text_id)
//    TextView versionName;//版本名称

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
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
        titleView.setText(getResources().getString(R.string.my_shezhi));
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        downlayout.setOnClickListener(onClickListener);
        clearlayout.setOnClickListener(onClickListener);
//        about.setOnClickListener(onClickListener);
        update.setOnClickListener(onClickListener);
        number.setOnClickListener(onClickListener);
        updatepassword.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.down_text_id: //个人信息
                    intent = new Intent(SheZhi_Activity.this, Personal.class);
                    startActivity(intent);
                    break;
                case R.id.number: //切换账号
                    intent = new Intent(SheZhi_Activity.this, LoginActivity.class);
                    startActivity(intent);
                    break;
                case R.id.updatepassword: //修改密码
                    intent = new Intent(SheZhi_Activity.this, PasswordActivity.class);
                    startActivity(intent);
                    break;
                case R.id.setting_data_clear: //清除缓存
//                    clearData();
                    break;
                case R.id.update://检查更新
//                    mProgressDialog = ProgressDialog.show(SheZhi_Activity.this, null,
//                            "正在检测更新，请耐心等候...", true, true);
//                    mProgressDialog.setCanceledOnTouchOutside(false);
//                    mProgressDialog.setCancelable(false);
//                    getVerseronInfo();
                    MessageUtils.showMiddleToast(SheZhi_Activity.this, "已是最新版本");
                    break;
            }
        }
    };


    //版本更新检查
    private void getVerseronInfo() {

        // 版本检测方式2：带更新回调监听
        PgyUpdateManager.register(SheZhi_Activity.this, getString(R.string.file_provider),
                new UpdateManagerListener() {
                    @Override
                    public void onUpdateAvailable(final String result) {
                        Log.i(TAG, "result=" + result);
                        // 将新版本信息封装到AppBean中
                        final AppBean appBean = getAppBeanFromString(result);
                        String newcode = appBean.getVersionCode();//新版本
                        int dqcode = 0;
                        try {
                            dqcode = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                        }

                        if (Integer.valueOf(newcode) > dqcode) {
                            new AlertDialog.Builder(SheZhi_Activity.this)
                                    .setTitle("新版本为" + appBean.getVersionName())
                                    .setMessage(appBean.getReleaseNote())
                                    .setNegativeButton(
                                            "确定",
                                            new DialogInterface.OnClickListener() {

                                                @Override
                                                public void onClick(
                                                        DialogInterface dialog,
                                                        int which) {
                                                    startDownloadTask(
                                                            SheZhi_Activity.this,
                                                            appBean.getDownloadURL());
                                                }
                                            }).show();
                        } else {
                            MessageUtils.showMiddleToast(SheZhi_Activity.this, "当前版本为最新版本");
                        }
                    }

                    @Override
                    public void onNoUpdateAvailable() {
                    }
                });

    }

}
