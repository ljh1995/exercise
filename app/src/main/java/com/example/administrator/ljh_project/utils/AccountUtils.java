package com.example.administrator.ljh_project.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.administrator.ljh_project.R;
import com.example.administrator.ljh_project.moudle.Person;


/**
 * 登录帐号管理Created by Administrator on 2018\3\10.
 */
public class AccountUtils {

    public static final int REQUEST_LOGIN = 0;

    private static final String key_login_member = "logined@member";
    private static final String key_fav_nodes = "logined@fav_nodes";


    /**
     * 记录是否记住密码
     *
     * @param cxt
     * @param isChecked *
     */

    public static void setChecked(Context cxt, boolean isChecked) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(cxt);
        sharedPreferences.edit().putBoolean(cxt.getString(R.string.logined_member_ischeck), isChecked).commit();

    }

    ;


    /**
     * 读取记住状态*
     */
    public static boolean getIsChecked(Context cxt) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(cxt);
        return sharedPreferences.getBoolean(cxt.getString(R.string.logined_member_ischeck), false);
    }

    /**
     * 记录登录人名称
     *
     * @param cxt
     * @param displayName *
     */

    public static void setDisplayName(Context cxt, String displayName) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(cxt);
        sharedPreferences.edit().putString(cxt.getString(R.string.logined_member_displayName), displayName).commit();

    }
    public static String getDisplayName(Context cxt){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(cxt);
        return sharedPreferences.getString(cxt.getString(R.string.logined_member_displayName), "");
    }

    /**
     * 记录登录返回信息
     * @param cxt
     *
     */
    public static void setLoginDetails(Context cxt,String sex,String loginid,String displayName,String departmrnt){

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(cxt);
        sharedPreferences.edit()
                .putString(cxt.getString(R.string.login_sex), sex)
                .putString(cxt.getString(R.string.login_loginid), loginid)
                .putString(cxt.getString(R.string.login_displayName), displayName)
                .putString(cxt.getString(R.string.login_departmrnt), departmrnt)
                .commit();
    }

    /**
     * 记录用户名与密码
     *
     * @param cxt
     * @param userName
     * @param password
     */

    public static void setUserNameAndPassWord(Context cxt, String userName, String password) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(cxt);
        sharedPreferences.edit().putString(cxt.getString(R.string.logined_member_username), userName).putString(cxt.getString(R.string.logined_member_password), password).commit();
    }


    /**
     * 记录用户名
     *
     * @param cxt
     * @param userName
     */

    public static void setUserName(Context cxt, String userName) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(cxt);
        sharedPreferences.edit().putString(cxt.getString(R.string.logined_member_username), userName).commit();
    }

    public static String getUserName(Context cxt) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(cxt);
        return sharedPreferences.getString(cxt.getString(R.string.logined_member_username), "");
    }

    /**
     * 获取记住的密码
     */


    public static String getUserPassword(Context cxt) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(cxt);
        return sharedPreferences.getString(cxt.getString(R.string.logined_member_password), "");
    }

    /**
     * 记录登录人id
     *
     * @param cxt
     * @param displayName *
     */

    public static void setPersonId(Context cxt, String displayName) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(cxt);
        sharedPreferences.edit().putString(cxt.getString(R.string.logined_member_personId), displayName).commit();

    }
    /**
     * 获取登录人id
     */
    public static String getPersonId(Context cxt) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(cxt);
        return sharedPreferences.getString(cxt.getString(R.string.logined_member_personId), "");
    }
    /**
     * 记录登录人生日
     *
     * @param cxt
     * @param birth *
     */

    public static void setBirth(Context cxt, String birth) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(cxt);
        sharedPreferences.edit().putString(cxt.getString(R.string.logined_member_birth), birth).commit();

    }
    /**
     * 获取登录人生日
     */
    public static String getBirth(Context cxt) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(cxt);
        return sharedPreferences.getString(cxt.getString(R.string.logined_member_birth), "");
    }
    /**
     * 记录登录人性别
     *
     * @param cxt
     * @param sex *
     */

    public static void setSex(Context cxt, String sex) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(cxt);
        sharedPreferences.edit().putString(cxt.getString(R.string.logined_member_sex), sex).commit();

    }
    /**
     * 获取登录人性别
     */
    public static String getSex(Context cxt) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(cxt);
        return sharedPreferences.getString(cxt.getString(R.string.logined_member_sex), "");
    }
    /**
     * 记录登录人部门
     *
     * @param cxt
     * @param department *
     */

    public static void setDepartment(Context cxt, String department) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(cxt);
        sharedPreferences.edit().putString(cxt.getString(R.string.logined_member_department), department).commit();

    }
    /**
     * 获取登录人性别
     */
    public static String getDepartment(Context cxt) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(cxt);
        return sharedPreferences.getString(cxt.getString(R.string.logined_member_department), "");
    }
}
