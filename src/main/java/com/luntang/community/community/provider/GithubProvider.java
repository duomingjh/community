package com.luntang.community.community.provider;

import com.alibaba.fastjson.JSON;
import com.luntang.community.community.dto.AccessTokenDTO;
import com.luntang.community.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component

/*
component
* 添加到容器
* */
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType medioJSON = MediaType.get("application/json; charset=utf-8");

            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(JSON.toJSONString(accessTokenDTO), medioJSON);
            Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {

                String string=response.body().string();
                String[] split=string.split("&");
                String tokenstr=split[0];
                String token=tokenstr.split("=")[1];
                System.out.println(token);
                return token;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
    }

    public GithubUser getUser(String accessToenk){
        OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://api.github.com/user?access_token="+accessToenk)
                    .build();
        try {
            Response  response = client.newCall(request).execute();
            String string=response.body().string();

            GithubUser githubUser=JSON.parseObject(string,GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
            return null;
    }

}

