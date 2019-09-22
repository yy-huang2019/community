package com.isoft.community.controller;

import com.isoft.community.dto.AccessTokenDTO;
import com.isoft.community.dto.GitHubUser;
import com.isoft.community.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class AuthorizeController {
    @Autowired                                        //自动注入
    private GitHubProvider gitHubProvider;

    @Value("${Github.client.id}")                    //通过@Value注解将properties中的参数注入到需要的变量中
    private String clientID;
    @Value("${Github.client.secret}")
    private String client_secret;
    @Value("${Github.Redirect.uri}")
    private String Redirect_uri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code")String code,
                           @RequestParam(name="state")String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientID);
        accessTokenDTO.setClient_secret(client_secret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(Redirect_uri);                                            //传递accessTokenDTO的五个变量值
        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);                      //获取access_token中的值
        GitHubUser user = null;                                                                  //将access_token传入返回得到的user信息
        try {
            user = gitHubProvider.getUser(accessToken);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(user.getName());
        return  "index";
    }


}
