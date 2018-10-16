package com.example.administrator.ljh_project.moudle;

/**
 * Created by Administrator on 2018\3\4 0004.
 */

public class Comment extends Entity{
    private String id ;
    private String content;
    private String foodid;
    private String time;
    private String  user;

    public String getid() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
    }

    public String getcontent() {
        return content;
    }

    public void setcontent(String content) {
        this.content = content;
    }

    public String getfoodid() {
        return foodid;
    }

    public void setfoodid(String foodid) {
        this.foodid = foodid;
    }

    public String gettime() {
        return time;
    }

    public void settime(String time) {
        this.time = time;
    }

    public String getuser() {
        return user;
    }

    public void setuser(String user) {
        this.user = user;
    }
}
