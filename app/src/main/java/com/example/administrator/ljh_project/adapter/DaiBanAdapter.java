package com.example.administrator.ljh_project.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.ljh_project.R;
import com.example.administrator.ljh_project.api.HttpManager;
import com.example.administrator.ljh_project.api.HttpRequestHandler;
import com.example.administrator.ljh_project.moudle.Evaluate;
import com.example.administrator.ljh_project.moudle.Food;
import com.example.administrator.ljh_project.ui.FoodInformationActivity;
import com.example.administrator.ljh_project.ui.FoodUpdateActivity;
import com.example.administrator.ljh_project.utils.AccountUtils;
import com.example.administrator.ljh_project.utils.MessageUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018\3\3 0003.
 */

public class DaiBanAdapter extends RecyclerView.Adapter<DaiBanAdapter.ViewHolder>  {
    Context context;
    ArrayList<Food> foods = new ArrayList<>();
    public DaiBanAdapter(Context context) {
        this.context = context;
    }
    @Override
    public DaiBanAdapter.ViewHolder onCreateViewHolder(ViewGroup holder, int viewType) {
        View view = LayoutInflater.from(holder.getContext()).inflate(R.layout.item_food,
                holder, false);
        return new DaiBanAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Food food = foods.get(position);
        holder.goodsCategoryName.setText(foods.get(position).getname());
        holder.tvGoodsDescription.setText(foods.get(position).getintroduce());
        if (foods.get(position).getgoodsImgUrl().contains("null")){
            ImageLoader.getInstance().displayImage("", holder.ivGoodsImage);
        }else {
            ImageLoader.getInstance().displayImage(foods.get(position).getgoodsImgUrl(), holder.ivGoodsImage);
        }
        holder.goodsInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FoodInformationActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("food", food);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        if (AccountUtils.getDisplayName(context).toString().equals("管理员")){
            holder.delete_update.setVisibility(View.VISIBLE);
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("确定删除吗？");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            String method = "24";
                            HttpManager.getDelete(context, method,foods.get(position).getid().toString(), new HttpRequestHandler<String>() {

                                @Override
                                public void onSuccess(String data) {
                                    if (data.toString().equals("true")){
                                        MessageUtils.showMiddleToast(context,"删除成功！");
                                    }else {
                                        MessageUtils.showMiddleToast(context,"删除失败！");
                                    }

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
                    //    设置一个NegativeButton
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {

                        }
                    });
                    //    显示出该对话框
                    builder.show();
                }
            });

            holder.update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, FoodUpdateActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("food", food);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivGoodsImage;
        TextView goodsCategoryName;
        TextView tvGoodsDescription;
        LinearLayout goodsInfo;
        LinearLayout delete_update;
        TextView update;
        TextView delete;


        public ViewHolder(View itemView) {
            super(itemView);
            ivGoodsImage = (ImageView) itemView.findViewById(R.id.ivGoodsImage);
            goodsCategoryName = (TextView) itemView.findViewById(R.id.goodsCategoryName);
            tvGoodsDescription = (TextView) itemView.findViewById(R.id.tvGoodsDescription);
            goodsInfo = (LinearLayout) itemView.findViewById(R.id.goodsInfo);
            delete_update = (LinearLayout) itemView.findViewById(R.id.delete_update);
            update = (TextView) itemView.findViewById(R.id.update);
            delete = (TextView) itemView.findViewById(R.id.delete);
        }
    }
    public void update(ArrayList<Food> data) {
        this.foods.clear();
        this.foods = data;
        notifyDataSetChanged();
    }
}

