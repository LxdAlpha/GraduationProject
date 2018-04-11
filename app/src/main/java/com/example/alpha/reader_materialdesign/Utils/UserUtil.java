package com.example.alpha.reader_materialdesign.Utils;

import android.util.Log;

import com.example.alpha.reader_materialdesign.Domain.Post;
import com.example.alpha.reader_materialdesign.Domain.Url;
import com.example.alpha.reader_materialdesign.Domain.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Alpha on 2018/4/1.
 */

public class UserUtil {
    public static User getUserById(int id) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(Url.baseUrl + "kevin/getUserById.action?id=" + id).build();
        Response response = client.newCall(request).execute();
        String responseData = response.body().string();


        JsonObject jsonObject = new JsonParser().parse(responseData).getAsJsonObject();


        Gson gson = new Gson();


        User userBean = gson.fromJson(jsonObject.get("user"), User.class);

        return userBean;
    }

    public static ArrayList<User> getAllUser() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(Url.baseUrl + "kevin/getAllUser.action").build();
        Response response = client.newCall(request).execute();
        String responseData = response.body().string();
        JsonObject jsonObject = new JsonParser().parse(responseData).getAsJsonObject();
        Gson gson = new Gson();
        ArrayList<User> list = gson.fromJson(jsonObject.get("allUser"), new TypeToken<List<User>>(){}.getType());
        return list;
    }
}
