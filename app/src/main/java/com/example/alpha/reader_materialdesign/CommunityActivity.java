package com.example.alpha.reader_materialdesign;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.alpha.reader_materialdesign.Adapter.CommunityPostAdapter;
import com.example.alpha.reader_materialdesign.Domain.Post;
import com.example.alpha.reader_materialdesign.Domain.User;
import com.example.alpha.reader_materialdesign.Utils.CommunityUtil;
import com.example.alpha.reader_materialdesign.Utils.UserUtil;

import java.io.IOException;
import java.util.ArrayList;

public class CommunityActivity extends AppCompatActivity {

    ArrayList<Post> postList;
    Post parentPost;
    ArrayList<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);
        Intent intent = getIntent();
        int parentId = intent.getIntExtra("parentId", -1);
        LoadTask loadTask = new LoadTask();
        loadTask.setId(parentId);
        loadTask.execute();
        while(postList == null || parentPost == null || userList == null){

        }
        postList.add(0, parentPost);

        RecyclerView recyclerView = findViewById(R.id.community_post_recy);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration mDivider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(mDivider);

        View view = getLayoutInflater().inflate(R.layout.dialog_reply, null);
        int width = getWindowManager().getDefaultDisplay().getWidth();
        CommunityPostAdapter adapter = new CommunityPostAdapter(postList, userList,  width, this);
        recyclerView.setAdapter(adapter);
    }

    class LoadTask extends AsyncTask{
        int id;

        public void setId(int id) {
            this.id = id;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                postList = CommunityUtil.getPostListByParentId(id);
                parentPost = CommunityUtil.getPostById(id);
                userList = UserUtil.getAllUser();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


}
