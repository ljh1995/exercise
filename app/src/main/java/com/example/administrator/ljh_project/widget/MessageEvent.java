package com.example.administrator.ljh_project.widget;


import com.example.administrator.ljh_project.moudle.GoodsListBean;

import java.util.List;

/**
 * Created by Administrator on 2017\12\28.
 */

public class MessageEvent {
    public int  num;
    public int  price;
    public List<GoodsListBean.DataEntity.GoodscatrgoryEntity.GoodsitemEntity> goods;

    public MessageEvent(int totalNum, int price, List<GoodsListBean.DataEntity.GoodscatrgoryEntity.GoodsitemEntity> goods) {
        this.num = totalNum;
        this.price = price;
        this.goods = goods;
    }
}
