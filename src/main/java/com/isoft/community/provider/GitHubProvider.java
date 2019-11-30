package com.isoft.community.provider;

import com.alibaba.fastjson.JSON;
import com.isoft.community.dto.AccessTokenDTO;
import com.isoft.community.dto.GitHubUser;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
@Slf4j
public class GitHubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");     //用okHttp的post方式请求调用https://github.com/login/oauth/access_token得到access_token

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()                                     //注意导okHttp3的包
                .url("https://github.com/login/oauth/access_token")                 //POST https://github.com/login/oauth/access_token(API文档)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String token = string.split("&")[0].split("=")[1];          //通过分割得到access_token中的具体的值
            return token;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("getAccessToken error,{}", accessTokenDTO, e);
        }
        return null;
    }

    public GitHubUser getUser(String accessToken) throws IOException {
        OkHttpClient client = new OkHttpClient();                                  //利用okHttp的get方式请求调用https://api.github.com/user通过accessToken获取user的信息

        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)      //GET https://api.github.com/user(API文档)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();                                  //string中得到的是JSON格式的数据
            GitHubUser gitHubUser = JSON.parseObject(string, GitHubUser.class);       //将返回的User对象中通过GitHubUser获取其中需要的信息
            //JSON.parseObject能将JSON格式的数据自动转化成java新方式的类对象(fastJson依赖项)
            return gitHubUser;
        }catch (Exception e){
            log.error("getUser error,{}", accessToken, e);
        }
        return null;
    }
}
