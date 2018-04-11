package com.example.alpha.reader_materialdesign.Utils;

import android.util.Log;

import com.example.alpha.reader_materialdesign.Domain.Post;
import com.example.alpha.reader_materialdesign.Domain.Url;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;



import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Alpha on 2018/1/9.
 */

public class CommunityUtil {
    public static ArrayList<Post> getParentPosts() throws IOException, ParseException {
        String url = Url.baseUrl + "kevin/getAllParentPost.action";
        Log.d("lxd", url);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        String result = response.body().string();



        JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
        JsonArray jsonArray = jsonObject.getAsJsonArray("parentPostList");
        Gson gson = new Gson();
        ArrayList<Post> postBeanList = new ArrayList();
        for (JsonElement user : jsonArray) {
            Post postBean = gson.fromJson(user, new TypeToken<Post>() {}.getType());
            postBeanList.add(postBean);
        }

        return postBeanList;
    }


    public static ArrayList<Post> getPostListByParentId(int id) throws IOException {
        String url = Url.baseUrl + "kevin/getPostListByParentId.action?id=" + id;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        String result = response.body().string();
        JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
        JsonArray jsonArray = jsonObject.getAsJsonArray("postList");
        Gson gson = new Gson();
        ArrayList<Post> postBeanList = new ArrayList();
        for (JsonElement user : jsonArray) {

            Post postBean = gson.fromJson(user, new TypeToken<Post>() {}.getType());
            postBeanList.add(postBean);
        }
        return postBeanList;
    }

    public static Post getPostById(int id) throws IOException {
        String url = Url.baseUrl + "kevin/getPostById.action?id=" + id;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        String result = response.body().string();
        JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
        JsonElement jsonElement = jsonObject.get("post");
        Post post = new Gson().fromJson(jsonElement, new TypeToken<Post>(){}.getType());
        return post;
    }

    public static void setPost(Post post){



        OkHttpClient okHttpClient = new OkHttpClient();
        //Form表单格式的参数传递
        FormBody formBody = new FormBody
                .Builder()
                .add("post.content",post.getContent())//设置参数名称和参数值
                .add("post.time", post.getTime())
                .add("post.parentId", post.getParentId()+"")
                .add("post.userId", post.getUserId()+"")
                .add("post.referenceId", post.getReferenceId()+"")
                .build();
        Request request = new Request
                .Builder()
                .post(formBody)//Post请求的参数传递，此处是和Get请求相比，多出的一句代码
                .url(Url.baseUrl + "kevin/addPost.action")
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            String result = response.body().string();
            response.body().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
