package com.example.administrator.ljh_project.api;

import android.content.Context;

import com.example.administrator.ljh_project.moudle.Comment;
import com.example.administrator.ljh_project.moudle.Evaluate;
import com.example.administrator.ljh_project.moudle.Food;
import com.example.administrator.ljh_project.moudle.News;
import com.example.administrator.ljh_project.moudle.Person;
import com.example.administrator.ljh_project.moudle.Persons;
import com.example.administrator.ljh_project.moudle.Trade;
import com.example.administrator.ljh_project.utils.AccountUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;


/**
 * Json数据解析封装类
 */
public class JsonUtils {
    private static final String TAG = "JsonUtils";


    /**
     * 解析登录信息*
     */
    public static String parsingAuthStr(final Context cxt, String data) {
        String isSuccess = null;
        String errmsg = null;
        try {
            JSONArray jsonArray =new JSONArray(data);
            JSONObject json = jsonArray.getJSONObject(0);
            String jsonString = json.getString("loginid");

            if (jsonString != null) {

                String displayName = json.getString("displayName");
                String personid = json.getString("loginid");
                String birth = json.getString("birth");
                String sex = json.getString("sex");
                String department = json.getString("department");
                AccountUtils.setDisplayName(cxt, displayName);
                AccountUtils.setPersonId(cxt, personid);
                AccountUtils.setBirth(cxt, birth);
                AccountUtils.setSex(cxt, sex);
                AccountUtils.setDepartment(cxt, department);
                errmsg = "登录成功";
            } else {
                errmsg = "用户名或密码错误";
            }


            return errmsg;


        } catch (JSONException e) {
            e.printStackTrace();
            return isSuccess;
        }
    }

    //评价
    public static ArrayList<Evaluate> parsingEvaluate(String data) {
        ArrayList<Evaluate> list = null;
        Evaluate purchase = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<Evaluate>();
            for (int i = 0; i < jsonArray.length(); i++) {
                purchase = new Evaluate();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = purchase.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = purchase.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(purchase);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = purchase.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(purchase, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(purchase);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    //食物
    public static ArrayList<Food> parsingFood(String data) {
        ArrayList<Food> list = null;
        Food purchase = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<Food>();
            for (int i = 0; i < jsonArray.length(); i++) {
                purchase = new Food();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = purchase.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = purchase.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(purchase);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = purchase.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(purchase, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(purchase);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    //食物
    public static ArrayList<Comment> parsingComment(String data) {
        ArrayList<Comment> list = null;
        Comment purchase = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<Comment>();
            for (int i = 0; i < jsonArray.length(); i++) {
                purchase = new Comment();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = purchase.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = purchase.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(purchase);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = purchase.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(purchase, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(purchase);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    //资讯
    public static ArrayList<News> parsingNews(String data) {
        ArrayList<News> list = null;
        News purchase = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<News>();
            for (int i = 0; i < jsonArray.length(); i++) {
                purchase = new News();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = purchase.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = purchase.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(purchase);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = purchase.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(purchase, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(purchase);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    //订单
    public static ArrayList<Trade> parsingTrade(String data) {
        ArrayList<Trade> list = null;
        Trade purchase = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<Trade>();
            for (int i = 0; i < jsonArray.length(); i++) {
                purchase = new Trade();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = purchase.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = purchase.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(purchase);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = purchase.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(purchase, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(purchase);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    //登录人
    public static ArrayList<Persons> parsingPerson(String data) {
        ArrayList<Persons> list = null;
        Persons purchase = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<Persons>();
            for (int i = 0; i < jsonArray.length(); i++) {
                purchase = new Persons();
                jsonObject = jsonArray.getJSONObject(i);
                Field[] field = purchase.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
                for (int j = 0; j < field.length; j++) {     //遍历所有属性
                    field[j].setAccessible(true);
                    String name = field[j].getName();    //获取属性的名字
                    if (jsonObject.has(name) && jsonObject.getString(name) != null && !jsonObject.getString(name).equals("")) {
                        try {
                            // 调用getter方法获取属性值
                            Method getOrSet = purchase.getClass().getMethod("get" + name);
                            Object value = getOrSet.invoke(purchase);
                            if (value == null) {
                                //调用setter方法设属性值
                                Class[] parameterTypes = new Class[1];
                                parameterTypes[0] = field[j].getType();
                                getOrSet = purchase.getClass().getDeclaredMethod("set" + name, parameterTypes);
                                getOrSet.invoke(purchase, jsonObject.getString(name));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                list.add(purchase);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}