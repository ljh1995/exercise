package com.example.administrator.ljh_project.ui;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.ljh_project.R;
import com.example.administrator.ljh_project.utils.BaseActivity;

/**
 * Created by Administrator on 2018\1\5 0005.
 */

public class About_us_Activity extends BaseActivity {


    /**
     * 标题*
     */
    private TextView titleView;
    /**
     * 返回按钮*
     */
    private ImageView backImageView;

    private TextView version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        findViewById();
        initView();
    }
    @Override
    protected void findViewById() {
        titleView = (TextView) findViewById(R.id.title_name);
        backImageView = (ImageView) findViewById(R.id.title_back_id);
        version = (TextView) findViewById(R.id.version);
    }

    @Override
    protected void initView() {
        titleView.setText(getResources().getString(R.string.my_version));
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        try {

            version.setText(this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName + "");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
