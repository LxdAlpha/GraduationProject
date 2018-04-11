package com.example.alpha.reader_materialdesign.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alpha.reader_materialdesign.Domain.Post;
import com.example.alpha.reader_materialdesign.Domain.User;
import com.example.alpha.reader_materialdesign.R;
import com.example.alpha.reader_materialdesign.Utils.CommunityUtil;
import com.example.alpha.reader_materialdesign.Utils.UserUtil;


import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Alpha on 2018/4/6.
 */

public class CommunityPostAdapter extends RecyclerView.Adapter<CommunityPostAdapter.ViewHolder>{

    ArrayList<Post> postList;

    ArrayList<User> userList;

    int width;

    Context context;
    public CommunityPostAdapter(ArrayList<Post> postList, ArrayList<User> userList, int width, Context context) {
        this.postList = postList;
        this.userList = userList;
        this.width = width;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        for(User user : userList){
            if(user.getId() == postList.get(position).getUserId()){
                holder.postItemName.setText(user.getUsername());
                break;
            }
        }
        holder.postItemTime.setText(postList.get(position).getTime());
        if(postList.get(position).getReferenceId() != 0){
            for(Post post : postList){
                if(post.getId() == postList.get(position).getReferenceId()){
                    for(User user : userList){
                        if(user.getId() == post.getUserId()){
                            holder.postItemReference.setText("引用：" + user.getUsername() + " 发表于 " + post.getTime() + "\n   " + post.getContent());
                            break;
                        }
                    }
                }
            }
        }else{
            holder.postItemReference.setVisibility(View.GONE);
        }
        holder.postItemContent.setText(postList.get(position).getContent());
        holder.postItemFloor.setText((position+1) + "#");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog build = new AlertDialog.Builder(context).create();
                LayoutInflater inflater = (LayoutInflater) holder.itemView.getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                View view23 = inflater.inflate(R.layout.dialog_reply, null);
                build.setView(view23, 0, 0, 0, 0);
                build.show();
                WindowManager.LayoutParams params = build.getWindow().getAttributes();
                params.width = width-(width/6);
                params.height =  WindowManager.LayoutParams.WRAP_CONTENT;
                params.gravity = Gravity.CENTER;
                build.getWindow().setAttributes(params);
                view23.findViewById(R.id.button_reply).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Post post = new Post();
                        SharedPreferences editor = context.getSharedPreferences("loginData", MODE_PRIVATE);
                        if(editor.getString("login", "false").equals("false")){
                            Toast.makeText(context, "请登录账户", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(position == 0){
                            post.setReferenceId(0);
                        }else{
                            post.setReferenceId(postList.get(position).getId());
                        }
                        post.setUserId(editor.getInt("userId", 1));
                        post.setParentId(postList.get(0).getId());
                        post.setContent(((EditText)view23.findViewById(R.id.edittext_reply)).getText().toString());
                        Date nowTime=new Date();
                        SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        post.setTime(time.format(nowTime));


                        Log.d("lxd", post.getTime() + " " + post.getUserId());


                        LoadTask loadTask = new LoadTask();
                        loadTask.setPost(post);
                        loadTask.execute();

                        build.cancel();

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView postItemName;
        TextView postItemTime;
        TextView postItemReference;
        TextView postItemContent;
        TextView postItemFloor;
        public ViewHolder(View itemView) {
            super(itemView);
            postItemName = itemView.findViewById(R.id.post_item_name);
            postItemTime = itemView.findViewById(R.id.post_item_time);
            postItemReference = itemView.findViewById(R.id.post_item_reference);
            postItemContent = itemView.findViewById(R.id.post_item_content);
            postItemFloor = itemView.findViewById(R.id.post_item_floor);
        }
    }



    class LoadTask extends AsyncTask{

        Post post;
        @Override
        protected Object doInBackground(Object[] objects) {
            CommunityUtil.setPost(post);
            return null;
        }

        public void setPost(Post post) {
            this.post = post;
        }
    }
}
