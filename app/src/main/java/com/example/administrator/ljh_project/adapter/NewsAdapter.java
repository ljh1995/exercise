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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.ljh_project.R;
import com.example.administrator.ljh_project.api.HttpManager;
import com.example.administrator.ljh_project.api.HttpRequestHandler;
import com.example.administrator.ljh_project.moudle.Comment;
import com.example.administrator.ljh_project.moudle.Food;
import com.example.administrator.ljh_project.moudle.News;
import com.example.administrator.ljh_project.ui.FoodInformationActivity;
import com.example.administrator.ljh_project.ui.FoodUpdateActivity;
import com.example.administrator.ljh_project.ui.NewsInformationActivity;
import com.example.administrator.ljh_project.ui.NewsUpdateActivity;
import com.example.administrator.ljh_project.utils.AccountUtils;
import com.example.administrator.ljh_project.utils.MessageUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018\3\7 0007.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>{
    Context context;
    ArrayList<News> newses = new ArrayList<>();
    public NewsAdapter(Context context) {
        this.context = context;
    }
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_news,
                parent, false);
        return new NewsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsAdapter.ViewHolder holder, final int position) {
        final News news = newses.get(position);
        holder.name.setText(newses.get(position).getname());
        holder.tittle.setText(newses.get(position).gettittle());
        holder.time.setText(newses.get(position).gettime());
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NewsInformationActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("news", news);
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
                            String method = "26";
                            HttpManager.getDeleteNews(context, method,newses.get(position).getnewsid(), new HttpRequestHandler<String>() {

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
                    Intent intent = new Intent(context, NewsUpdateActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("news", news);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return newses.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView tittle;
        TextView time;
        LinearLayout item;
        LinearLayout delete_update;
        TextView update;
        TextView delete;


        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            tittle = (TextView) itemView.findViewById(R.id.tittle);
            time = (TextView) itemView.findViewById(R.id.time);
            item = (LinearLayout) itemView.findViewById(R.id.item);
            delete_update = (LinearLayout) itemView.findViewById(R.id.update_detele);
            update = (TextView) itemView.findViewById(R.id.update);
            delete = (TextView) itemView.findViewById(R.id.delete);
        }
    }
    public void update(ArrayList<News> data) {
        this.newses.clear();
        this.newses = data;
        notifyDataSetChanged();
    }
}
