package com.example.administrator.ljh_project.adapter;

import android.animation.Animator;
import android.content.Context;
import android.content.DialogInterface;
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
import com.example.administrator.ljh_project.moudle.Comment;
import com.example.administrator.ljh_project.moudle.Evaluate;
import com.example.administrator.ljh_project.utils.AccountUtils;
import com.example.administrator.ljh_project.utils.MessageUtils;
import com.example.administrator.ljh_project.widget.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018\1\14 0014.
 */

public class CommentAdapter  extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{
    Context context;
    ArrayList<Comment> comments = new ArrayList<>();
    public CommentAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_comment,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentAdapter.ViewHolder holder, final int position) {
        holder.user.setText(comments.get(position).getuser());
        holder.content.setText(comments.get(position).getcontent());
        holder.time.setText(comments.get(position).gettime());
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
                            String method = "13";
                            HttpManager.getDelete(context, method,comments.get(position).getid().toString(), new HttpRequestHandler<String>() {

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
        return comments.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView user;
        TextView content;
        TextView time;
        LinearLayout delete;


        public ViewHolder(View itemView) {
            super(itemView);
            user = (TextView) itemView.findViewById(R.id.user_id);
            content = (TextView) itemView.findViewById(R.id.content);
            time = (TextView) itemView.findViewById(R.id.time);
            delete = (LinearLayout) itemView.findViewById(R.id.delete);
        }
    }
    public void update(ArrayList<Comment> data) {
        this.comments.clear();
        this.comments = data;
        notifyDataSetChanged();
    }
}
