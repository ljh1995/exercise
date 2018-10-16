package com.example.administrator.ljh_project.api;


import android.content.Context;
import android.util.Log;

import com.example.administrator.ljh_project.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;



/**
 * Created by Administrator on 2018\1\11.
 */
public class HttpManager {

    private static final String TAG = "HttpManager";


    /**
     * 使用用户名密码登录
     *

     * @param handler  返回结果处理
     */
    public static void loginWithUsername(final Context cxt,final String method, final String username,final String password,
                                         final HttpRequestHandler<String> handler) {

        String loginIp = "http://192.168.43.236:8080/work/demo?"+ "method=" + method + "&" + "username=" + username + "&" + "password=" + password;
        Log.i(TAG,"loginIp="+loginIp);

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("method", method);
        params.put("username", username);
        params.put("password", password);
        client.get(loginIp,params, new TextHttpResponseHandler() {


            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                SafeHandler.onFailure(handler, IMErrorType.errorMessage(cxt, IMErrorType.ErrorLoginFailure));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                if (statusCode == 200) {
                    String errmsg = JsonUtils.parsingAuthStr(cxt, responseString);
                    SafeHandler.onSuccess(handler, errmsg);
                }
            }
        });
    }

    //不分页返回结果
    public static void getData(final Context cxt,String method, String loginid,String birth,String sex, final HttpRequestHandler<String> handler) {
        String ip_adress =   "http://192.168.43.236:8080/work/demo?"+ "method=" + method + "&" + "sex=" + sex + "&" + "birth=" + birth + "&" + "loginid=" + loginid;
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("method", method);
        client.get(ip_adress, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                SafeHandler.onFailure(handler, cxt.getString(R.string.get_data_info_fail));
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {

                if (statusCode == 200) {
                    SafeHandler.onSuccess(handler, responseString);
                }

            }

        });
    }

    /**
     * 解析返回的结果--分页*
     */
    public static void getFood(final Context cxt, final String method, final String address, final HttpRequestHandler<String> handler) {
        String base_url = "http://192.168.43.236:8080/work/demo?"+ "method=" + method + "&address=" + address;
        Log.i(TAG, "base_url=" + base_url + ",method=" + method);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("method", method);
        client.get(base_url,params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                SafeHandler.onFailure(handler, cxt.getString(R.string.get_data_info_fail));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.i(TAG, "responseString=" + responseString);
                SafeHandler.onSuccess(handler,responseString);
            }
        });
    }
    /**
     * 解析评价
     */
    public static void getEvaluate(final Context cxt, final String method, final String address, final HttpRequestHandler<String> handler) {
        String base_url = "http://192.168.43.236:8080/work/demo?"+ "method=" + method+ "&address=" + address;
        Log.i(TAG, "base_url=" + base_url + ",method=" + method);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("method", method);
        client.get(base_url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                SafeHandler.onFailure(handler, cxt.getString(R.string.get_data_info_fail));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.i(TAG, "responseString=" + responseString);
                SafeHandler.onSuccess(handler,responseString);
            }
        });
    }
    /**
     * 解析发表评价
     */
    public static void getEvaluates(final Context cxt, final String method,
                                    final String user,final String content,final String address,final HttpRequestHandler<String> handler) {
        String base_url = "http://192.168.43.236:8080/work/demo?"+ "method=" + method + "&user=" + user + "&content=" + content+ "&address=" + address;
        Log.i(TAG, "base_url=" + base_url + ",method=" + method);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("method", method);
        client.get(base_url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                SafeHandler.onFailure(handler, cxt.getString(R.string.get_data_info_fail));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.i(TAG, "responseString=" + responseString);
                SafeHandler.onSuccess(handler,responseString);
            }
        });
    }
    /**
     * 解析发表评价
     */
    public static void getValuates(final Context cxt, final String method,final String foodid,
                                    final String user,final String content,final String time,final HttpRequestHandler<String> handler) {
        String base_url = "http://192.168.43.236:8080/work/demo?"+ "method=" + method + "&foodid=" + foodid + "&user=" + user + "&content=" + content+ "&time=" + time;
        Log.i(TAG, "base_url=" + base_url + ",method=" + method);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("method", method);
        client.get(base_url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                SafeHandler.onFailure(handler, cxt.getString(R.string.get_data_info_fail));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.i(TAG, "responseString=" + responseString);
                SafeHandler.onSuccess(handler,responseString);
            }
        });
    }
    /**
     * 解析推荐
     */
    public static void getFoods(final Context cxt, final String method, final HttpRequestHandler<String> handler) {
        String base_url = "http://192.168.43.236:8080/work/demo?"+ "method=" + method;
        Log.i(TAG, "base_url=" + base_url + ",method=" + method);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("method", method);
        client.get(base_url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                SafeHandler.onFailure(handler, cxt.getString(R.string.get_data_info_fail));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.i(TAG, "responseString=" + responseString);
                SafeHandler.onSuccess(handler,responseString);
            }
        });
    }
    /**
     * 解析搜索推荐
     */
    public static void getSearch(final Context cxt, final String method,
                                 final String name,final HttpRequestHandler<String> handler) {
        String base_url = "http://192.168.43.236:8080/work/demo?"+ "method=" + method + "&name=" + name;
        Log.i(TAG, "base_url=" + base_url + ",method=" + method);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("method", method);
        client.get(base_url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                SafeHandler.onFailure(handler, cxt.getString(R.string.get_data_info_fail));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.i(TAG, "responseString=" + responseString);
                SafeHandler.onSuccess(handler,responseString);
            }
        });
    }
    /**
     * 解析单个评价
     */
    public static void getComment(final Context cxt, final String method,
                                 final String foodid,final HttpRequestHandler<String> handler) {
        String base_url = "http://192.168.43.236:8080/work/demo?"+ "method=" + method + "&foodid=" + foodid;
        Log.i(TAG, "base_url=" + base_url + ",method=" + method);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("method", method);
        client.get(base_url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                SafeHandler.onFailure(handler, cxt.getString(R.string.get_data_info_fail));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.i(TAG, "responseString=" + responseString);
                SafeHandler.onSuccess(handler,responseString);
            }
        });
    }
    /**
     * 解析注册
     */
    public static void getzhuce(final Context cxt, final String method,
                                  final String username,final String password,final String displayname,final HttpRequestHandler<String> handler) {
        String base_url = "http://192.168.43.236:8080/work/demo?"+ "method=" + method + "&username=" + username + "&password=" + password + "&displayName=" + displayname;
        Log.i(TAG, "base_url=" + base_url + ",method=" + method);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("method", method);
        client.get(base_url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                SafeHandler.onFailure(handler, cxt.getString(R.string.get_data_info_fail));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.i(TAG, "responseString=" + responseString);
                SafeHandler.onSuccess(handler,responseString);
            }
        });
    }
    /**
     * 解析校园资讯*
     */
    public static void getNews(final Context cxt, final String method, final HttpRequestHandler<String> handler) {
        String base_url = "http://192.168.43.236:8080/work/demo?"+ "method=" + method;
        Log.i(TAG, "base_url=" + base_url + ",method=" + method);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("method", method);
        client.get(base_url,params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                SafeHandler.onFailure(handler, cxt.getString(R.string.get_data_info_fail));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.i(TAG, "responseString=" + responseString);
                SafeHandler.onSuccess(handler,responseString);
            }
        });
    }
    /**
     * 解析修改密码
     */
    public static void getPassword(final Context cxt, final String method,final String loginid,final String password, final HttpRequestHandler<String> handler) {
        String base_url = "http://192.168.43.236:8080/work/demo?"+ "method=" + method + "&loginid=" + loginid + "&password=" + password;
        Log.i(TAG, "base_url=" + base_url + ",method=" + method);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("method", method);
        client.get(base_url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                SafeHandler.onFailure(handler, cxt.getString(R.string.get_data_info_fail));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.i(TAG, "responseString=" + responseString);
                SafeHandler.onSuccess(handler,responseString);
            }
        });
    }

    /**
     * 解析删除
     */
    public static void getDelete(final Context cxt, final String method,final String id, final HttpRequestHandler<String> handler) {
        String base_url = "http://192.168.43.236:8080/work/demo?"+ "method=" + method + "&id=" + id;
        Log.i(TAG, "base_url=" + base_url + ",method=" + method);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("method", method);
        client.get(base_url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                SafeHandler.onFailure(handler, cxt.getString(R.string.get_data_info_fail));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.i(TAG, "responseString=" + responseString);
                SafeHandler.onSuccess(handler,responseString);
            }
        });
    }
    /**
     * 解析订单
     */
    public static void getBuy(final Context cxt, final String method,
                                  final String name,final String number, final String price,
                              final String telephone,final String content,final String username,
           final String taste,final String foodid,final String comment,final String address,final HttpRequestHandler<String> handler) {
        String base_url = "http://192.168.43.236:8080/work/demo?"+ "method=" + method +"&name=" + name + "&number=" + number + "&price=" + price +
                "&telephone=" + telephone + "&content=" + content+ "&username=" + username+ "&taste=" + taste+ "&foodid=" + foodid+ "&comment=" + comment+ "&address=" + address;
        Log.i(TAG, "base_url=" + base_url + ",method=" + method);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("method", method);
        client.get(base_url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                SafeHandler.onFailure(handler, cxt.getString(R.string.get_data_info_fail));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.i(TAG, "responseString=" + responseString);
                SafeHandler.onSuccess(handler,responseString);
            }
        });
    }
    /**
     * 解析订单
     */
    public static void getBuys(final Context cxt, final String method,
                              final String name,final String number, final String price,
                              final String telephone,final String content,final String username,
                              final String taste,final String comment,final String address,final HttpRequestHandler<String> handler) {
        String base_url = "http://192.168.43.236:8080/work/demo?"+ "method=" + method +"&name=" + name + "&number=" + number + "&price=" + price +
                "&telephone=" + telephone + "&content=" + content+ "&username=" + username+ "&taste=" + taste+ "&comment=" + comment+ "&address=" + address;
        Log.i(TAG, "base_url=" + base_url + ",method=" + method);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("method", method);
        client.get(base_url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                SafeHandler.onFailure(handler, cxt.getString(R.string.get_data_info_fail));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.i(TAG, "responseString=" + responseString);
                SafeHandler.onSuccess(handler,responseString);
            }
        });
    }
    /**
     * 管理员获取订单
     */
    public static void getOrder(final Context cxt, final String method, final String address, final HttpRequestHandler<String> handler) {
        String base_url = "http://192.168.43.236:8080/work/demo?"+ "method=" + method + "&address=" + address;
        Log.i(TAG, "base_url=" + base_url + ",method=" + method);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("method", method);
        client.get(base_url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                SafeHandler.onFailure(handler, cxt.getString(R.string.get_data_info_fail));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.i(TAG, "responseString=" + responseString);
                SafeHandler.onSuccess(handler,responseString);
            }
        });
    }
    /**
     * 解析订单
     */
    public static void getOrders(final Context cxt, final String method,final String username, final HttpRequestHandler<String> handler) {
        String base_url = "http://192.168.43.236:8080/work/demo?"+ "method=" + method + "&username=" + username;
        Log.i(TAG, "base_url=" + base_url + ",method=" + method);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("method", method);
        client.get(base_url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                SafeHandler.onFailure(handler, cxt.getString(R.string.get_data_info_fail));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.i(TAG, "responseString=" + responseString);
                SafeHandler.onSuccess(handler,responseString);
            }
        });
    }

    /**
     * 解析是否领用
     */
    public static void getTaste(final Context cxt, final String method, final String taste
            , final String tradeid, final HttpRequestHandler<String> handler) {
        String base_url = "http://192.168.43.236:8080/work/demo?"+ "method=" + method + "&taste=" + taste + "&tradeid=" + tradeid;
        Log.i(TAG, "base_url=" + base_url + ",method=" + method);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("method", method);
        client.get(base_url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                SafeHandler.onFailure(handler, cxt.getString(R.string.get_data_info_fail));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.i(TAG, "responseString=" + responseString);
                SafeHandler.onSuccess(handler,responseString);
            }
        });
    }
    /**
     * 修改评价状态
     */
    public static void getEvaluateStatus(final Context cxt, final String method,
                                    final String comment,final String tradeid,final HttpRequestHandler<String> handler) {
        String base_url = "http://192.168.43.236:8080/work/demo?"+ "method=" + method + "&comment=" + comment + "&tradeid=" + tradeid;
        Log.i(TAG, "base_url=" + base_url + ",method=" + method);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("method", method);
        client.get(base_url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                SafeHandler.onFailure(handler, cxt.getString(R.string.get_data_info_fail));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.i(TAG, "responseString=" + responseString);
                SafeHandler.onSuccess(handler,responseString);
            }
        });
    }
    /**
     * 新增推荐
     */
    public static void getAddFood(final Context cxt, final String method,
                                  final String name,final String introduce,final String address
            ,final String price,final String number,final HttpRequestHandler<String> handler) {
        String base_url = "http://192.168.43.236:8080/work/demo?"+ "method=" + method + "&name=" + name + "&introduce=" + introduce
                + "&address=" + address+ "&price=" + price+ "&number=" + number;
        Log.i(TAG, "base_url=" + base_url + ",method=" + method);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("method", method);
        client.get(base_url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                SafeHandler.onFailure(handler, cxt.getString(R.string.get_data_info_fail));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.i(TAG, "responseString=" + responseString);
                SafeHandler.onSuccess(handler,responseString);
            }
        });
    }
    /**
     * 新增校园资讯
     */
    public static void getAddNews(final Context cxt, final String method,
                                  final String tittle,final String name,final String content
            ,final String time,final HttpRequestHandler<String> handler) {
        String base_url = "http://192.168.43.236:8080/work/demo?"+ "method=" + method + "&tittle=" + tittle + "&name=" + name
                + "&content=" + content+ "&time=" + time;
        Log.i(TAG, "base_url=" + base_url + ",method=" + method);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("method", method);
        client.get(base_url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                SafeHandler.onFailure(handler, cxt.getString(R.string.get_data_info_fail));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.i(TAG, "responseString=" + responseString);
                SafeHandler.onSuccess(handler,responseString);
            }
        });
    }
    /**
     * 修改校园资讯
     */
    public static void getUpdateNews(final Context cxt,  final String method,final String newsid,
                                  final String tittle,final String name,final String content
            ,final String time,final HttpRequestHandler<String> handler) {
        String base_url = "http://192.168.43.236:8080/work/demo?"+ "method=" + method + "&newsid=" + newsid + "&tittle=" + tittle + "&name=" + name
                + "&content=" + content+ "&time=" + time;
        Log.i(TAG, "base_url=" + base_url + ",method=" + method);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("method", method);
        client.get(base_url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                SafeHandler.onFailure(handler, cxt.getString(R.string.get_data_info_fail));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.i(TAG, "responseString=" + responseString);
                SafeHandler.onSuccess(handler,responseString);
            }
        });
    }
    /**
     * 找回密码
     */
    public static void getFindPassword(final Context cxt, final String method,
                                  final String username,final String idnumber,final HttpRequestHandler<String> handler) {
        String base_url = "http://192.168.43.236:8080/work/demo?"+ "method=" + method + "&username=" + username + "&idnumber=" + idnumber;
        Log.i(TAG, "base_url=" + base_url + ",method=" + method);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("method", method);
        client.get(base_url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                SafeHandler.onFailure(handler, cxt.getString(R.string.get_data_info_fail));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.i(TAG, "responseString=" + responseString);
                SafeHandler.onSuccess(handler,responseString);
            }
        });
    }

    /**
     * 修改推荐
     */
    public static void getUpdateFood(final Context cxt, final String method, final String id,
                                     final String introduce,final String name,final String number,
                                     final String address
            ,final String price,final HttpRequestHandler<String> handler) {
        String base_url = "http://192.168.43.236:8080/work/demo?"+ "method=" + method + "&id=" + id + "&introduce=" + introduce + "&name=" + name
                + "&number=" + number + "&address=" + address+ "&price=" + price;
        Log.i(TAG, "base_url=" + base_url + ",method=" + method);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("method", method);
        client.get(base_url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                SafeHandler.onFailure(handler, cxt.getString(R.string.get_data_info_fail));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.i(TAG, "responseString=" + responseString);
                SafeHandler.onSuccess(handler,responseString);
            }
        });
    }
    /**
     * 解析删除
     */
    public static void getDeleteNews(final Context cxt, final String method,final String newsid, final HttpRequestHandler<String> handler) {
        String base_url = "http://192.168.43.236:8080/work/demo?"+ "method=" + method + "&newsid=" + newsid;
        Log.i(TAG, "base_url=" + base_url + ",method=" + method);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("method", method);
        client.get(base_url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                SafeHandler.onFailure(handler, cxt.getString(R.string.get_data_info_fail));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.i(TAG, "responseString=" + responseString);
                SafeHandler.onSuccess(handler,responseString);
            }
        });
    }
}
