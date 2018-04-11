package com.example.alpha.reader_materialdesign.Adapter;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alpha.reader_materialdesign.CommunityActivity;
import com.example.alpha.reader_materialdesign.Domain.Post;
import com.example.alpha.reader_materialdesign.Domain.User;
import com.example.alpha.reader_materialdesign.R;
import com.example.alpha.reader_materialdesign.Utils.UserUtil;

import java.util.ArrayList;

/**
 * Created by Alpha on 2018/1/9.
 */

public class MainCommunityAdapter extends RecyclerView.Adapter<MainCommunityAdapter.ViewHolder>{
    private ArrayList<Post> list;
    private ArrayList<User> userList;

    public MainCommunityAdapter(ArrayList<Post> list){
        this.list = list;
        LoadTask loadTask = new LoadTask();
        loadTask.execute();
        while(userList == null){

        }
    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_community_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();

                Intent intent = new Intent(parent.getContext(), CommunityActivity.class);
                intent.putExtra("parentId", list.get(position).getId());
                parent.getContext().startActivity(intent);

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String title = list.get(position).getTitle();
        //String writer = userList.get(position).getUsername();
        String writer = "";
        for(User user: userList){
            if(user.getId() == list.get(position).getUserId()){
                writer = user.getUsername();
                break;
            }
        }
            /*
            user = new User();
            user.setPassword("123");
            user.setName("lxd");
            user.setId(1);*/
        String time = list.get(position).getTime().toString();
        holder.title.setText(title);
        holder.writer.setText(writer);
        holder.time.setText(time);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        View view;
        TextView title;
        TextView writer;
        TextView time;
        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            title = itemView.findViewById(R.id.main_community_item_title);
            writer = itemView.findViewById(R.id.main_community_item_writer);
            time = itemView.findViewById(R.id.main_community_item_time);
        }
    }

    class LoadTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                userList = UserUtil.getAllUser();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

    }
}

