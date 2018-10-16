package com.example.administrator.ljh_project.moudle;

/**
 * Created by Administrator on 2018\3\3 0003.
 */

public class Food extends Entity{
    private String goodsImgUrl;
    private String id;
    private String introduce;
    private String number;
    private String name;
    private String prize;
    private String address;

    public String getaddress() {
        return address;
    }

    public void setaddress(String address) {
        this.address = address;
    }

    public String getgoodsImgUrl() {
        return goodsImgUrl;
    }

    public void setgoodsImgUrl(String goodsImgUrl) {
        this.goodsImgUrl = goodsImgUrl;
    }

    public String getintroduce() {
        return introduce;
    }

    public void setintroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getid() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
    }

    public String getnumber() {
        return number;
    }

    public void setnumber(String number) {
        this.number = number;
    }

    public String getprize() {
        return prize;
    }

    public void setprize(String prize) {
        this.prize = prize;
    }
}
