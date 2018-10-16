package com.example.administrator.ljh_project.moudle;

import android.widget.EditText;

/**
 * Created by Administrator on 2018\3\20 0020.
 */

public class Trade extends Entity {

    private String content;
    private String name;
    private String number;
    private String  price;
    private String telephone;
    private String tradeid;
    private String username;
    private String taste;
    private String foodid;
    private String comment;
    private String address;

    public String getaddress() {
        return address;
    }

    public void setaddress(String address) {
        this.address = address;
    }

    public String getcomment() {
        return comment;
    }

    public void setcomment(String comment) {
        this.comment = comment;
    }

    public String getfoodid() {
        return foodid;
    }

    public void setfoodid(String foodid) {
        this.foodid = foodid;
    }

    public String gettaste() {
        return taste;
    }

    public void settaste(String taste) {
        this.taste = taste;
    }

    public String getcontent() {
        return content;
    }

    public void setcontent(String content) {
        this.content = content;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getnumber() {
        return number;
    }

    public void setnumber(String number) {
        this.number = number;
    }

    public String getprice() {
        return price;
    }

    public void setprice(String price) {
        this.price = price;
    }

    public String gettelephone() {
        return telephone;
    }

    public void settelephone(String telephone) {
        this.telephone = telephone;
    }

    public String gettradeid() {
        return tradeid;
    }

    public void settradeid(String tradeid) {
        this.tradeid = tradeid;
    }

    public String getusername() {
        return username;
    }

    public void setusername(String username) {
        this.username = username;
    }
}
