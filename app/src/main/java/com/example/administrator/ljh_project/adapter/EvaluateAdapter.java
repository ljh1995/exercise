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

import com.example.administrator.ljh_project.R;
import com.example.administrator.ljh_project.api.HttpManager;
import com.example.administrator.ljh_project.api.HttpRequestHandler;
import com.example.administrator.ljh_project.api.JsonUtils;
import com.example.administrator.ljh_project.fragment.EvaluateFragment;
import com.example.administrator.ljh_project.moudle.Evaluate;
import com.example.administrator.ljh_project.ui.FoodInformationActivity;
import com.example.administrator.ljh_project.utils.AccountUtils;
import com.example.administrator.ljh_project.utils.MessageUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018\3\3 0003.
 */

public class EvaluateAdapter extends RecyclerView.Adapter<EvaluateAdapter.ViewHolder>  {
    Context context;
    ArrayList<Evaluate> Evaluate = new ArrayList<>();
    public EvaluateAdapter(Context context) {
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup holder, int viewType) {
        View view = LayoutInflater.from(holder.getContext()).inflate(R.layout.item,
                holder, false);
        return new EvaluateAdapter.ViewHolder(view);
    }

    @Override
     public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.textView.setText("用户:" + Evaluate.get(position).getuser());
        holder.content.setText(Evaluate.get(position).getcontent());
        if (AccountUtils.getDisplayName(context).toString().equals("管理员")){
            holder.delete.setVisibility(View.VISIBLE);
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
                            String method = "12";
                            HttpManager.getDelete(context, method,Evaluate.get(position).getid().toString(), new HttpRequestHandler<String>() {

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
        }
    }

    @Override
    public int getItemCount() {
        return Evaluate.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        TextView content;
        LinearLayout delete;


        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv);
            content = (TextView) itemView.findViewById(R.id.content);
            delete = (LinearLayout) itemView.findViewById(R.id.delete);
        }
    }
    public void update(ArrayList<Evaluate> data) {
        this.Evaluate.clear();
        this.Evaluate = data;
        notifyDataSetChanged();
    }
}
