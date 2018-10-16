package com.example.administrator.ljh_project.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.administrator.ljh_project.R;
import com.example.administrator.ljh_project.utils.BaseActivity;
import com.example.administrator.ljh_project.utils.PermissionsChecker;


public class LoadActivity extends BaseActivity {


    private static final int BAIDU_READ_PHONE_STATE = 0; // 请求码

    boolean isFlag=true;

    private static final int REQUEST_CODE =100;

    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA,
            Manifest.permission.INTERNET,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
    };

    private PermissionsChecker mPermissionsChecker; // 权限检测器
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
//        initView();
        mPermissionsChecker = new PermissionsChecker(this);
    }

    @Override
    protected void findViewById() {

    }

    @Override
    protected void initView() {
        if (Build.VERSION.SDK_INT>=23 && isFlag == true){
            showContacts();
        }

    }


    class splashhandler implements Runnable {

        public void run() {
            jumpLoginActivity();
        }

    }


    /**
     * 跳转至登录界面*
     */
    private void jumpLoginActivity() {
        Intent intent = new Intent();
        intent.setClass(this, LoginActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onResume() {
        super.onResume();
//        initGPS();

        // 缺少权限时, 进入权限配置页面
        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
            startPermissionsActivity();
        } else {
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            Handler x = new Handler();
            x.postDelayed(new splashhandler(), 2000);
        }
    }

    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            finish();
        }
    }

//     /* 检查gps状态并引导用户打开gps */
//
//    private void initGPS() {
//        LocationManager locationManager = (LocationManager) this
//                .getSystemService(Context.LOCATION_SERVICE);
//        // 判断GPS模块是否开启，如果没有则跳转至设置开启界面，设置完毕后返回到当前页面
//        if (!locationManager
//                .isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
//            AlertDialog.Builder da = new AlertDialog.Builder(this);
//            da.setTitle("提示：");
//            da.setMessage("为了更好的为您服务，请您打开您的GPS!");
//            da.setCancelable(false);
//            //设置左边按钮监听
//            da.setNeutralButton("确定",
//                    new android.content.DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface arg0, int arg1) {
//
//                            // 转到手机设置界面，用户设置GPS
//                            Intent intent = new Intent(
//                                    Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                            startActivityForResult(intent, 0); // 设置完成后返回到原来的界面
//
//                        }
//                    });
//            //设置右边按钮监听
//            da.setPositiveButton("取消",
//                    new android.content.DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface arg0, int arg1) {
//                            arg0.dismiss();
//                        }
//                    });
//            da.show();
//        } else {
//        }
//    }

    //Android6.0申请权限的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case BAIDU_READ_PHONE_STATE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）
                    isFlag = false;
                    initView();
                } else {
                    // 没有获取到权限，做特殊处理
                    Toast.makeText(getApplicationContext(), "获取位置权限失败，请手动开启", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    public void showContacts(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(),"没有权限,请手动开启定位权限", Toast.LENGTH_SHORT).show();
            // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
//            ActivityCompat.requestPermissions(MyLocationClient.this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE}, BAIDU_READ_PHONE_STATE);
            ActivityCompat.requestPermissions(LoadActivity.this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, BAIDU_READ_PHONE_STATE);
        }else{
            isFlag = false;
            initView();
        }
    }

}
