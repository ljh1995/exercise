package com.example.administrator.ljh_project.moudle;

/**
 * Created by Administrator on 2018\3\7 0007.
 */

public class News extends Entity{
    private String newsid;
    private String name;
    private String tittle;
    private String content;
    private String time;

    public String getnewsid() {
        return newsid;
    }

    public void setnewsid(String newsid) {
        this.newsid = newsid;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String gettittle() {
        return tittle;
    }

    public void settittle(String tittle) {
        this.tittle = tittle;
    }

    public String getcontent() {
        return content;
    }

    public void setcontent(String content) {
        this.content = content;
    }

    public String gettime() {
        return time;
    }

    public void settime(String time) {
        this.time = time;
    }
}
