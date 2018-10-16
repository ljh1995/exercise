package com.example.administrator.ljh_project.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.ljh_project.R;
import com.example.administrator.ljh_project.api.HttpManager;
import com.example.administrator.ljh_project.api.HttpRequestHandler;
import com.example.administrator.ljh_project.api.JsonUtils;
import com.example.administrator.ljh_project.moudle.Food;
import com.example.administrator.ljh_project.moudle.Trade;
import com.example.administrator.ljh_project.ui.FoodInformationActivity;
import com.example.administrator.ljh_project.ui.ValueActivity;
import com.example.administrator.ljh_project.utils.AccountUtils;
import com.example.administrator.ljh_project.utils.MessageUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018\3\20 0020.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>  {
    Context context;
    ArrayList<Trade> trades = new ArrayList<>();
    public OrderAdapter(Context context) {
        this.context = context;
    }
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(ViewGroup holder, int viewType) {
        View view = LayoutInflater.from(holder.getContext()).inflate(R.layout.item_order,
                holder, false);
        return new OrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final OrderAdapter.ViewHolder holder, final int position) {
        final Trade trade = trades.get(position);
        holder.goodsName.setText(trades.get(position).getname());
        holder.GoodsNumber.setText(trades.get(position).getnumber());
        holder.GoodsPrice.setText("¥" + trades.get(position).getprice());
        holder.user.setText(trades.get(position).gettelephone());
        holder.content.setText(trades.get(position).getcontent());
        if (AccountUtils.getDisplayName(context).equals("服务员") || AccountUtils.getDisplayName(context).equals("管理员")){
            holder.linearLayout.setVisibility(View.VISIBLE);
            holder.use.setText(trades.get(position).gettaste());
            holder.use.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.use.setText("已领取");
                    HttpManager.getTaste(context, "17","已领取",trades.get(position).gettradeid(), new HttpRequestHandler<String>() {

                        @Override
                        public void onSuccess(String data) {

                        }

                        @Override
                        public void onSuccess(String data, int totalPages, int currentPage) {

                        }

                        @Override
                        public void onFailure(String error) {
                        }
                    });
                }
            });
        }else {
            holder.linearLayout.setVisibility(View.VISIBLE);
            holder.use.setText(trades.get(position).getcomment());
            holder.use.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (trades.get(position).getcomment().equals("评价")){
                        Intent intent = new Intent(context, ValueActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("trade", trade);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }else {
                        MessageUtils.showMiddleToast(context,"亲，您已经评价过哦");
                    }

                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return trades.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView goodsName;
        TextView GoodsNumber;
        TextView GoodsPrice;
        TextView user;
        TextView content;
        LinearLayout linearLayout;
        TextView use;

        public ViewHolder(View itemView) {
            super(itemView);
            goodsName = (TextView) itemView.findViewById(R.id.goodsName);
            GoodsNumber = (TextView) itemView.findViewById(R.id.GoodsNumber);
            GoodsPrice = (TextView) itemView.findViewById(R.id.GoodsPrice);
            user = (TextView) itemView.findViewById(R.id.user);
            content = (TextView) itemView.findViewById(R.id.content);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.usage);
            use = (TextView) itemView.findViewById(R.id.use);
        }
    }
    public void update(ArrayList<Trade> data) {
        this.trades.clear();
        this.trades = data;
        notifyDataSetChanged();
    }
}
